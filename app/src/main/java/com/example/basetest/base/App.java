package com.example.basetest.base;

import android.app.Application;

/**
 * Created by Administrator on 2016/8/26.
 */
public class App extends Application {
    private static final String TAG = "App";

    protected App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
