package com.example.basetest.projectmanage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;

import com.example.basetest.util.LogUtil;
import com.example.basetest.util.Utils;

import java.io.File;
import java.util.Vector;

/**
 * 客户端版本日志接口服务类
 *
 * @author 张磊
 */
public class WebServiceClient {
    private static WebServiceClient ahtobaccoMobileService;

    private WebServiceClient() {
    }

    public static WebServiceClient getInstance() {
        if (ahtobaccoMobileService == null) {
            ahtobaccoMobileService = new WebServiceClient();
        }
        return ahtobaccoMobileService;

    }

    /**
     * 上传客户端日志
     *
     * @param version
     * @param appId
     * @param area
     * @param mobile
     * @param OsVersion
     * @param errorMessage
     * @throws RemoteException
     */
    public void sendLogToServer(final String version, final String appId,
                                final String area, final String mobile, final String OsVersion,
                                final String errorMessage, final File file) {
        XML xml;
        try {
            String xmlString = MobileService.getInstance().sendLogToServer(
                    version, appId, area, mobile, OsVersion, errorMessage);
            xml = Utils.getXML(xmlString);
            // LogUtil.i("Result XML", xml.toString());
            Node root = xml.getRoot();
            String result = root.getAttribute("result");
            if ("ok".equals(result)) {
                file.delete();// 删除日志文件
                LogUtil.i("send error", "send error success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getUpdateInfo(final String versionCode,
                              final String versionName, final Handler handler) {

        final String updateInfos[] = new String[]{"", "", "", "", "", ""};
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String xmlResult = MobileService.getInstance()
                            .getUpdateInfo(versionCode, versionName);
                    if (xmlResult == null) {
                        updateInfos[0] = "-1";
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        Bundle b = new Bundle();
                        b.putStringArray("UPDATEINFO", updateInfos);
                        message.setData(b);
                        handler.sendMessage(message);
                        return;
                    }
                    XML xml = Utils.getXML(xmlResult.replaceAll("<br>", "\n"));
                    Node root = xml.getRoot();
                    String result = root.getAttribute("result");
                    if ("ok".equals(result.trim())) {
                        Vector tasks = new Vector();
                        xml.getRoot().getAllTagsByName("DATASET", tasks);
                        if (tasks.size() > 0) {
                            Node datasetNode = (Node) tasks.elementAt(0);
                            Vector needNodes = datasetNode
                                    .getChildren("STATUS");
                            Node needNode = (Node) needNodes.elementAt(0);
                            String needValue = "";
                            if (needNode.getChildText() != null) {
                                needValue = needNode.getChildText();
                            }
                            updateInfos[0] = needValue;
                            Vector forceNodes = datasetNode
                                    .getChildren("UPDATESTATUS");
                            Node forceNode = (Node) forceNodes.elementAt(0);
                            String forceValue = "";
                            if (forceNode.getChildText() != null) {
                                forceValue = forceNode.getChildText();
                            }
                            updateInfos[1] = forceValue;

                            Vector urlNodes = datasetNode.getChildren("URL");
                            Node urlNode = (Node) urlNodes.elementAt(0);
                            String urlValue = "";
                            if (urlNode.getChildText() != null) {
                                urlValue = urlNode.getChildText();
                            }
                            updateInfos[2] = urlValue;

                            Vector desNodes = datasetNode.getChildren("DES");
                            Node desNode = (Node) desNodes.elementAt(0);
                            String desValue = "";
                            if (desNode.getChildText() != null) {
                                desValue = desNode.getChildText();
                            }
                            updateInfos[3] = desValue;

                            Vector verNodes = datasetNode
                                    .getChildren("VERSION");
                            Node verNode = (Node) verNodes.elementAt(0);
                            String verValue = "";
                            if (verNode.getChildText() != null) {
                                verValue = verNode.getChildText();
                            }
                            updateInfos[4] = verValue;
                        }
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        Bundle b = new Bundle();
                        b.putStringArray("UPDATEINFO", updateInfos);
                        message.setData(b);
                        handler.sendMessage(message);
                    } else if ("error".equals(result.trim())) {
                        Message message = handler.obtainMessage();
                        message.what = 1;
                        Bundle b = new Bundle();
                        b.putStringArray("UPDATEINFO", updateInfos);
                        message.setData(b);
                        handler.sendMessage(message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();
    }
}
