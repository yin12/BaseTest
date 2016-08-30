package com.example.basetest.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import com.example.basetest.R;
import com.example.basetest.base.App;
import com.example.basetest.base.Constants;
import com.example.basetest.base.EventMessage;
import com.github.mrengineer13.snackbar.SnackBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/26.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static String TAG = "BaseActivity";

    protected Context mContext;
    protected App app;
    protected String httpUrl;
    protected HashMap<String, Object> fieldParams;

    /*支持vector矢量图标（selector）针对Android 5.0 以下版本*/
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (initCreate()) {
            return;
        }
        setContentView(getContentView());
        mContext = this;
        app = (App) getApplicationContext();
        fieldParams = new HashMap<>();

        /*初始化LIBS*/
        initLibs();
        /*初始化DATA*/
        initData();
        /*初始化VIEW*/
        initView();
        /*加载数据*/
        loadData();
    }

    protected boolean initCreate() {
        return false;
    }

    protected void initLibs() {
        ButterKnife.bind(this);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    protected abstract int getContentView();

    protected abstract void initData();

    protected abstract void initView();

    /**
     * 加载数据
     */
    public void loadData() {
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 获取页面传递的值，关键字Constants.BUNDLE
     *
     * @return Object
     */
    protected Object getPassValue() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Object object = bundle.getSerializable(Constants.BUNDLE);
        return object;
    }

    /**
     * 获取页面传递的值
     *
     * @return Object
     */
    protected Object getPassValueByKey(String bundle_key) {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Object object = bundle.getSerializable(bundle_key);
        return object;
    }

    /**
     * 获取页面传递的值
     *
     * @return Bundle
     */
    protected Bundle getBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return bundle;
    }

    /**
     * 页面跳转
     *
     * @param cls
     * @param bundle
     * @param <T>
     */
    protected <T> void goToActivity(T cls, Bundle bundle) {
        try {
            Intent intent = new Intent();
            intent.setClass(this, (Class<?>) cls);
            intent.putExtras(bundle == null ? new Bundle() : bundle);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 页面跳转带返回值
     *
     * @param cls
     * @param bundle
     * @param requestCode
     * @param <T>
     */
    public <T> void goToActivity(T cls, Bundle bundle, int requestCode) {
        try {
            Intent intent = new Intent();
            intent.setClass(this, (Class<?>) cls);
            intent.putExtras(bundle == null ? new Bundle() : bundle);
            startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 消息提醒
     *
     * @param msg
     */
    protected void notice(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 消息提醒
     *
     * @param msg
     */
    protected void notice(int msg) {
        Toast.makeText(this, getString(msg), Toast.LENGTH_SHORT).show();
    }

    /**
     * 创建SnackBar.Builder
     *
     * @param msg
     * @param color
     * @return
     */
    public SnackBar.Builder snackBar(String msg, @ColorRes int color) {
        return new SnackBar.Builder(this)
                .withMessage(msg)
                .withTextColorId(R.color.color_text_white)
                .withBackgroundColorId(color)
                .withDuration(SnackBar.SHORT_SNACK);
    }


    /**
     * 显示SnackBar
     *
     * @param msg
     * @param color
     */
    public void showSnackBar(String msg, @ColorRes int color) {
        new SnackBar.Builder(this)
                .withMessage(msg)
                .withTextColorId(R.color.color_text_white)
                .withBackgroundColorId(color)
                .withDuration(SnackBar.SHORT_SNACK)
                .show();
    }

    /**
     * 显示SnackBar
     *
     * @param msg
     * @param error
     * @param color
     */
    public void showSnackBar(String msg, String error, @ColorRes int color) {
        if (!"".equals(error))
            msg = msg + "\n" + error;

        showSnackBar(msg, color);
    }


    public void setTAG(String tag) {
        this.TAG = tag;
    }

    @Subscribe
    public void onEvent(EventMessage message) {

    }
}
