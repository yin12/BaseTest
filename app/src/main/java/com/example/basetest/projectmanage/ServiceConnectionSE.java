package com.example.basetest.projectmanage;

import org.ksoap2.transport.ServiceConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceConnectionSE implements ServiceConnection {
	private HttpURLConnection connection;

	public ServiceConnectionSE(String url) throws IOException {
		this.connection = ((HttpURLConnection) new URL(url).openConnection());
		this.connection.setDoOutput(true);
		this.connection.setDoInput(true);
	}

	@Override
	public void connect() throws IOException {
		this.connection.connect();
	}

	@Override
	public void disconnect() {
		this.connection.disconnect();
	}

	@Override
	public void setRequestProperty(String string, String soapAction) {
		this.connection.setRequestProperty(string, soapAction);
	}

	@Override
	public void setRequestMethod(String requestMethod) throws IOException {
		this.connection.setRequestMethod(requestMethod);
	}

	@Override
	public OutputStream openOutputStream() throws IOException {
		return this.connection.getOutputStream();
	}

	@Override
	public InputStream openInputStream() throws IOException {
		return this.connection.getInputStream();
	}

	@Override
	public InputStream getErrorStream() {
		return this.connection.getErrorStream();
	}

	// 设置连接服务器的超时时间,毫秒级,此为自己添加的方法
	public void setConnectionTimeOut(int timeout) {
		this.connection.setConnectTimeout(timeout);
	}
}