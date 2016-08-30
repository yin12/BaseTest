package com.example.basetest.projectmanage;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

import com.example.basetest.R;


public class Config {

    public static final String UPDATE_SAVENAME = "版本更新";

    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(
                    "com.icss.zhuanmai", 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    "com.icss.momopoly", 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;

    }

    public static String getAppName(Context context) {
        String verName = context.getResources().getText(R.string.app_name)
                .toString();
        return verName;
    }
}
