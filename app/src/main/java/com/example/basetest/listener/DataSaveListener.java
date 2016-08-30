package com.example.basetest.listener;

/**
 * Created by Administrator on 2016/8/26.
 */
public interface DataSaveListener {

    public static final int QUIT_OUT = 7;
    public static final int QUIT_CAN = 8;

    public static final int UPDATE_NOT = 111;
    public static final int UPDATE_YES = 112;
    public static final int UPDATE_SUCCESS = 113;
    public static final int UPDATE_FAILD = 114;

    public void onEvent(int type, Object object);
}
