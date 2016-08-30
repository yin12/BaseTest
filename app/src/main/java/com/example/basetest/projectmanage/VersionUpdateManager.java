package com.example.basetest.projectmanage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.example.basetest.R;
import com.example.basetest.base.App;
import com.example.basetest.base.Version;
import com.example.basetest.dialog.UpdateDialog;
import com.example.basetest.listener.DataSaveListener;
import com.example.basetest.ui.LoginActivity;
import com.example.basetest.util.LogUtil;
import com.example.basetest.util.SharePreferencesUtils;
import com.example.basetest.util.ToastUtils;
import com.example.basetest.util.Utils;

import java.io.File;

/**
 * TODO 版本更新类
 * 
 * @author 杨宜春
 */
public class VersionUpdateManager implements DataSaveListener {

	private Context mContext;
	private static VersionUpdateManager instance;
	private ProgressDialog progressDialog;
	Handler handler = new Handler();
	private String newVerName = "";

	private int index = 0;
	private Message message = null;
	private int hasRead = 0;
	private MyHandler myhandler = null;
	private boolean isAutoLogin;
	private String roleType;
	private App application;

	private VersionUpdateManager(Context context, boolean autologin,
			String roletype) {
		this.mContext = context;
		this.isAutoLogin = autologin;
		this.roleType = roletype;
		application = (App) context.getApplicationContext();
	}

	public static VersionUpdateManager getInstance(Activity activity,
			boolean autologin, String roletype) {
		if (instance == null)
			instance = new VersionUpdateManager(activity, autologin, roletype);
		return instance;
	}

	class CustomerHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				Bundle bundle = msg.getData();
				String[] updateInfos = bundle.getStringArray("UPDATEINFO");
				if (updateInfos[0].equals("1")) {// 有版本更新
				// if (updateInfos[1].equals("1")) {// 强制更新
				// LogUtil.e("", "force update");
				// forceUpdate(updateInfos[2]);
				// } else {
					LogUtil.e("", "normal update");
					// findUpdate(updateInfos[2],updateInfos[3]);
					String verName = Utils.getVerName(mContext);
					newVerName = updateInfos[4];
					StringBuffer sb = new StringBuffer();
					sb.append("当前版本:");
					sb.append(verName);
					sb.append(" \n最新版本:");
					sb.append(updateInfos[4]);
					sb.append("\n");
					sb.append(" \n更新内容:");
					sb.append("\n" + updateInfos[3]);
					UpdateDialog updateDialog = new UpdateDialog(mContext,
							R.style.version_update, sb.toString(), updateInfos[4],
							updateInfos[1], updateInfos[2],
							VersionUpdateManager.this);
					updateDialog.setCancelable(false);
					updateDialog.show();
					// }
				} else if (updateInfos[0].equals("0")
						|| updateInfos[0].equals("")) {
					loadLoginActivity();
				} else {
					ToastUtils.showToast(mContext, "版本更新服务连接失败！");
					loadLoginActivity();
				}
			}
		}

	}

	public void checkIsUpdate() {
		String name = Utils.getVerName(mContext);
		LogUtil.e("", "版本号  "+name);
		CustomerHandler customerHandler = new CustomerHandler();
		WebServiceClient.getInstance().getUpdateInfo(Version.CHANNEL, name, customerHandler);
	}

	private void loadLoginActivity() {
		instance = null;//释放实例化对象，否则InitActivity不会执行onCreate方法
		Intent intent = new Intent();
		intent.setClass(mContext, LoginActivity.class);
		mContext.startActivity(intent);
		((Activity) mContext).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
		((Activity) mContext).finish();
	}

	private void downResult(final String result, final boolean isforce) {
		if (!result.equals("success")) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					if (progressDialog != null && progressDialog.isShowing())
						progressDialog.cancel();
					ToastUtils.showToast(mContext, "下载失败：" + result);
					loadLoginActivity();
				}
			});

		} else {
			LogUtil.e("", "down file success");
			SharePreferencesUtils.putString(mContext, SharePreferencesUtils.KEY_DOWNLOADED_VERSION, "1");
			handler.post(new Runnable() {
				@Override
				public void run() {
					if (progressDialog != null && progressDialog.isShowing())
						progressDialog.cancel();
					if (isforce) {
						update();
						return;
					}
					AlertDialog alertDialog = new AlertDialog.Builder(mContext)
							.setTitle("系统升级")
							.setMessage("下载完成，是否立即安装更新？")
							.setCancelable(true)
							.setPositiveButton("安装",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											update();
										}
									})
							.setNegativeButton("取消",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											loadLoginActivity();
										}
									}).create();
					alertDialog.setCanceledOnTouchOutside(false);
					alertDialog.show();
				}
			});
		}
	}

	private void update() {
		LogUtil.e("", " prepare install update");
		SharePreferencesUtils.putString(mContext,SharePreferencesUtils.KEY_DOWNLOADED_VERSION,"");
		SharePreferencesUtils.putString(mContext,SharePreferencesUtils.KEY_NEWSYSTEM,"1");
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
		intent.setDataAndType(Uri.fromFile(new File(Environment
						.getExternalStorageDirectory(), Config.UPDATE_SAVENAME
						+ newVerName + ".apk")),
				"application/vnd.android.package-archive");
		((Activity)mContext).startActivityForResult(intent, 1);
	}

	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				progressDialog.setProgress(index);
			}
			super.handleMessage(msg);
		}

	}

	@Override
	public void onEvent(int type, Object object) {
		switch (type) {
		case UPDATE_NOT:
			loadLoginActivity();
			break;
		case UPDATE_SUCCESS:
			// MessageDialog messageDialog = new MessageDialog(
			// activity, R.style.smsDialog, "版本更新",
			// "下载完成，是否立即安装？", this);
			// messageDialog.show();
			update();
			break;
		case UPDATE_FAILD:
			ToastUtils.showToast(mContext, "下载失败");
			loadLoginActivity();
			break;
		case QUIT_OUT:
			((Activity)mContext).finish();
			System.exit(0);
			break;
		default:
			break;
		}

	}
}

