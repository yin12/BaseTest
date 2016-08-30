package com.example.basetest.projectmanage;


import com.example.basetest.util.LogUtil;

import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;

import java.io.IOException;

public class MyAndroidHttpTransport extends HttpTransportSE {

    private int timeout = 30000; // 默认超时时间为30s

    public MyAndroidHttpTransport(String url) {
        super(url);
    }

    public MyAndroidHttpTransport(String url, int timeout) {
        super(url);
        this.timeout = timeout;
    }

    @Override
    protected ServiceConnection getServiceConnection() throws IOException {
        ServiceConnectionSE serviceConnection = new ServiceConnectionSE(url);
        LogUtil.e("set timeout", "--------------set time out");
        serviceConnection.setConnectionTimeOut(timeout);
        return serviceConnection;
    }

}
