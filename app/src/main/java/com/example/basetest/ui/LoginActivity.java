package com.example.basetest.ui;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.basetest.R;
import com.example.basetest.ui.base.BaseActivity;
import com.flyco.roundview.RoundTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.Bind;

/**
 * 登录
 * Created by YinZZ on 2016/8/26.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.met_name)
    MaterialEditText metName;
    @Bind(R.id.met_pass)
    MaterialEditText metPass;
    @Bind(R.id.rtv_login)
    RoundTextView rtvLogin;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        setSupportActionBar(toolbar);
        /*  返回键可用*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        /*  返回事件*/
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });

        rtvLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rtv_login://登录
                setLogin();

                break;

            default:
                break;
        }
    }

    private void setLogin() {

        if (metName.getText().toString().trim().equals("")) {
            metName.setError(getResources().getString(R.string.account_is_mistake));
            return;
        } else {
            metName.setError(null);
        }

        if (metPass.getText().toString().trim().equals("")) {
            metPass.setError(getResources().getString(R.string.password_is_mistake));
            return;
        } else {
            metPass.setError(null);
        }

        isLogin();

    }

    /*TODO 登录*/
    private void isLogin() {
        goToActivity(MainActivity.class, null);
    }


}
