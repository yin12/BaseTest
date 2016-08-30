package com.example.basetest.util;

import com.example.basetest.base.Version;
import com.orhanobut.logger.Logger;


/**
 * Created by Administrator on 2016/8/26.
 */
public class LogUtil {
    private static final String TAG = "LogUtil";

    /* 标记是否调试阶段，上线时置为false*/
    protected static boolean DEBUG = Version.LOG_DEBUG;
    protected static LogUtil logUtil;

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Logger.t(tag).v(msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Logger.t(tag).i(msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            Logger.t(tag).d(msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Logger.t(tag).w(msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Logger.t(tag).e(msg);
        }
    }

    public static void e(String tag, String msg, Throwable t) {
        if (DEBUG)
            Logger.t(tag).e(t, msg);
    }

    public static void wtf(String tag, String msg) {
        if (DEBUG)
            Logger.t(tag).wtf(msg);
    }

    public static void json(String tag, String json) {
        if (DEBUG)
            Logger.t(tag).json(json);
    }

    public static void xml(String tag, String xml) {
        if (DEBUG)
            Logger.t(tag).xml(xml);
    }

    public static void systemLog(String msg) {
        Logger.t(TAG).e(msg);
    }

    public static void d(String msg) {
        if (DEBUG)
            Logger.t(TAG).d(msg);
    }

    public static void plainLog(String msg) {
        if (DEBUG) {
            Logger.t(TAG).i(msg);
        }
    }

}
