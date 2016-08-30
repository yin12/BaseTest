package com.example.basetest.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.basetest.R;
import com.example.basetest.base.App;
import com.example.basetest.listener.DataSaveListener;
import com.example.basetest.projectmanage.Config;
import com.example.basetest.util.FileUtils;
import com.example.basetest.util.LogUtil;
import com.example.basetest.util.SharePreferencesUtils;
import com.example.basetest.util.ToastUtils;
import com.example.basetest.util.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UpdateDialog extends Dialog implements
        View.OnClickListener, DialogInterface.OnDismissListener {
    private Context context;
    private DataSaveListener listener;
    private String value;
    private String newVerName;
    private String verTable;
    private String url;
    private RelativeLayout down_text;
    private FrameLayout down_load;
    private ProgressBar progressBar;
    private TextView status;
    private TextView txt_version;
    private LinearLayout btn_update, btn_ignore, btn_cancel;
    private TextView txt_cancel;

    private App application;

    private downloadFile2 downTread;

    private int downloadedSize = 0;
    private int fileSize = 0;

    private boolean running = false;

    public UpdateDialog(Context context, int theme, String value,
                        String newVerName, String verTable, String url,
                        DataSaveListener listener) {
        super(context, theme);
        this.context = context;
        this.listener = listener;
        this.value = value;
        this.newVerName = newVerName;
        this.verTable = verTable;
        this.url = url;
    }

    public UpdateDialog(Context context, String s, boolean forcedUpdate) {
        super(context, R.style.version_update);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_update);

//		getWindow().setWindowAnimations(R.style.dialogWindowSpecialAnim);

        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (d.getWidth() * 0.4);
        lp.dimAmount = 0.4f;
        lp.alpha = 0.95f;
        this.getWindow().setAttributes(lp);

        application = (App) ((Activity) context).getApplication();

        down_text = (RelativeLayout) findViewById(R.id.down_text);
        down_load = (FrameLayout) findViewById(R.id.down_load);
        down_text.setVisibility(View.VISIBLE);
        down_load.setVisibility(View.GONE);

        txt_version = (TextView) findViewById(R.id.versioncode);
        txt_version.setText(value);
        status = (TextView) findViewById(R.id.ziliao_statue);
        progressBar = (ProgressBar) findViewById(R.id.ziliao_load);
        progressBar.setMax(100);

        btn_update = (LinearLayout) findViewById(R.id.btnexit);
        btn_ignore = (LinearLayout) findViewById(R.id.btncan);
        btn_cancel = (LinearLayout) findViewById(R.id.btndel);
        txt_cancel = (TextView) findViewById(R.id.btn_txt_can);
        if (verTable.equals("1")) {
            btn_ignore.setBackgroundResource(R.drawable.btn_delete);
            txt_cancel.setText("退出");
        } else {
            btn_ignore.setBackgroundResource(R.drawable.btn_cancel);
            txt_cancel.setText("忽略");
        }

        btn_update.setOnClickListener(this);
        btn_ignore.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        this.setOnDismissListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnexit:
                btn_update.setVisibility(View.GONE);
                btn_ignore.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.VISIBLE);
                txt_version.setText("正在下载，请稍后...");
                down_load.setVisibility(View.VISIBLE);
                String downloaded_version = SharePreferencesUtils.getString(context,
                        SharePreferencesUtils.KEY_DOWNLOADED_VERSION, "");
                LogUtil.e("", "zwz" + url);
                if (!downloaded_version.equals("")) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        ToastUtils.showToast(context, "SD卡不存在或SD卡已损坏,系统强制更新失败！");
                        return;
                    }
                    File file = new File(Environment.getExternalStorageDirectory(),
                            Config.UPDATE_SAVENAME + newVerName + ".apk");
                    if (file.isFile()) {
                        listener.onEvent(DataSaveListener.UPDATE_SUCCESS, null);
                        this.dismiss();
                    } else {
                        progressBar.setProgress(0);
                        status.setText("0%");
                        downTread = new downloadFile2(url);
                        downTread.start();
                    }
                } else {
                    progressBar.setProgress(0);
                    status.setText("0%");
                    downTread = new downloadFile2(url);
                    downTread.start();
                }
                break;
            case R.id.btncan:
                if (verTable.equals("1")) {
                    listener.onEvent(DataSaveListener.QUIT_OUT, null);
                } else {
                    listener.onEvent(DataSaveListener.UPDATE_NOT, null);
                }

                this.dismiss();
                break;
            case R.id.btndel:
                if (downTread != null) {
                    downTread.interrupt();
                }
                if (downloadedSize != fileSize || status.getText().equals("0%")) {
                    FileUtils.deleteDirectory(new File(Environment
                            .getExternalStorageDirectory(), Config.UPDATE_SAVENAME
                            + newVerName + ".apk"));
                }
                if (verTable.equals("1")) {
                    listener.onEvent(DataSaveListener.QUIT_OUT, null);
                } else {
                    listener.onEvent(DataSaveListener.UPDATE_NOT, null);
                }
                this.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        running = false;
        this.dismiss();
    }

    /**
     * @author ideasandroid 主下载线程
     */
    public class downloadFile2 extends Thread {
        String baseUrl;

        public downloadFile2(String url) {
            this.baseUrl = url;
            running = true;
        }

        @Override
        public void run() {
            try {
                HttpClient client = new DefaultHttpClient();
                client.getParams().setIntParameter(
                        CoreConnectionPNames.SO_TIMEOUT, 10000);
                client.getParams().setIntParameter(
                        CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);// 连接超时

                HttpGet get = new HttpGet(url);
                HttpResponse response;
                try {
                    response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    fileSize = (int) entity.getContentLength();
                    long sdavailableLength = Utils.getSDAvailaleSize();
                    LogUtil.e(getClass().getName(), "SDAvailaleSize is  "
                            + sdavailableLength);
                    if (fileSize > sdavailableLength) {
                        // downResult("SD卡空间不足，请清理后再更新！", isforce);
                        return;
                    }
                    InputStream is = entity.getContent();
                    OutputStream fileOutputStream = null;
                    if (is != null) {
                        File file = new File(
                                Environment.getExternalStorageDirectory(),
                                Config.UPDATE_SAVENAME + newVerName + ".apk");
                        fileOutputStream = new FileOutputStream(file);
                        byte[] buf = new byte[10240];
                        int ch = -1;
                        while ((ch = is.read(buf)) != -1 && running) {
                            fileOutputStream.write(buf, 0, ch);
                            downloadedSize += ch;
                            handler.sendEmptyMessage(1);
                        }
                    }
                    fileOutputStream.flush();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (running)
                        handler.sendEmptyMessage(2);
                    running = false;

                } catch (HttpHostConnectException e) {
                    handler.sendEmptyMessage(3);
                    e.printStackTrace();
                } catch (ConnectTimeoutException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    handler.sendEmptyMessage(4);
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // 当收到更新视图消息时，计算已完成下载百分比，同时更新进度条信息
                    int progress = (Double
                            .valueOf((downloadedSize * 1.0 / fileSize * 100)))
                            .intValue();
                    status.setText(progress + "%");
                    progressBar.setProgress(progress);
                    break;
                case 2:
                    status.setText("100%");
                    SharePreferencesUtils.putString(context,
                            SharePreferencesUtils.KEY_DOWNLOADED_VERSION, "1");
                    listener.onEvent(DataSaveListener.UPDATE_SUCCESS, null);
                    UpdateDialog.this.dismiss();
                    break;
                case 3:
                    ToastUtils.showToast(context, "连接服务器失败！");
                    listener.onEvent(DataSaveListener.UPDATE_NOT, null);
                    UpdateDialog.this.dismiss();
                    break;
                case 4:
                    ToastUtils.showToast(context, "网络连接失败！");
                    listener.onEvent(DataSaveListener.UPDATE_NOT, null);
                    UpdateDialog.this.dismiss();
                    break;
                default:
                    break;
            }
        }

    };

}

