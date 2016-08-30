package com.example.basetest.ui;

import android.content.Intent;
import android.widget.TextView;

import com.example.basetest.R;
import com.example.basetest.base.EventMessage;
import com.example.basetest.projectmanage.VersionUpdateManager;
import com.example.basetest.ui.base.BaseActivity;
import com.example.basetest.util.LogUtil;
import com.example.basetest.util.SharePreferencesUtils;
import com.example.basetest.util.Utils;

import butterknife.Bind;

/**
 * 引导活动
 * Created by YinZZ on 2016/8/26.
 */
public class InitActivity extends BaseActivity {
    private static final String TAG = "InitActivity";

    @Bind(R.id.txt_version)
    TextView txtVersion;

    @Override
    protected boolean initCreate() {
        super.initCreate();
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            InitActivity.this.finish();
            return true;
        }
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_init;
    }

    @Override
    protected void initData() {
        txtVersion.setText("当前版本:" + Utils.getAppVersionName(this));
    }

    @Override
    protected void initView() {
  /*控制是否在初始页面进行版本更新监测*/
        VersionUpdateManager.getInstance(this, false, "").checkIsUpdate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == 0) {
                SharePreferencesUtils.putBoolean(this,
                        SharePreferencesUtils.KEY_UNINSTALL_APK, false);
                onDataLoaded();
            }
        }
    }

    private void onDataLoaded() {
        goToActivity(LoginActivity.class, null);
        InitActivity.this.finish();
    }


    @Override
    public void onEvent(EventMessage message) {
        super.onEvent(message);
        switch (message.getType()) {
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("", "InitActivity  onDestroy");
    }
}
