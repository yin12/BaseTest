package com.example.basetest.projectmanage;


import com.example.basetest.util.LogUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


/**
 * 服务接口
 *
 * @author 张磊
 */
public class MobileService {
    public static MobileService mobileService;
    public static final String SOAP_SERVER_URL = "http://218.22.168.4:9081/mms/services/MServer?wsdl";
    public static final String SOAP_SERVER_INTERFACE = "http://218.22.168.4:9081/mms/services/MServer";

    private MobileService() {

    }

    public synchronized static MobileService getInstance() {
        if (mobileService == null) {
            mobileService = new MobileService();
        }
        return mobileService;
    }

    /**
     * 上传客户端日志接口
     *
     * @param version
     * @param appId
     * @param area
     * @param mobile
     * @param OsVersion
     * @param errorMessage
     * @return
     */
    public String sendLogToServer(String version, String appId, String area,
                                  String mobile, String OsVersion, String errorMessage) {
        SoapObject request = new SoapObject(
                MobileService.SOAP_SERVER_INTERFACE, "submitErrorLog");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        StringBuffer reqValue = new StringBuffer();
        reqValue.append("<?xml version='1.0' encoding='utf-8'?>");
        reqValue.append("<DATASETS>");
        reqValue.append("<DATASET>");
        reqValue.append("<VERSION>" + version + "</VERSION>");
        reqValue.append("<APPID>" + appId + "</APPID>");
        reqValue.append("<AREA>" + area + "</AREA>");
        reqValue.append("<MODEL>" + mobile + "</MODEL>");
        reqValue.append("<SYSTEMVERSION>" + OsVersion + "</SYSTEMVERSION>");
        reqValue.append("<ERRORMESSAGE>" + errorMessage + "</ERRORMESSAGE>");
        reqValue.append("</DATASET>");
        reqValue.append("</DATASETS>");
        request.addProperty("condition", reqValue.toString());
        LogUtil.i("sendLogToServer reqValue", reqValue.toString());
        envelope.bodyOut = request;
        HttpTransportSE ht = new HttpTransportSE(MobileService.SOAP_SERVER_URL);
        try {
            ht.call(null, envelope);
            if (envelope.getResponse() != null) {
                SoapObject result = (SoapObject) envelope.bodyIn;
                Object object = result.toString();
                if (object != null) {
                    String xmlString = envelope.getResponse().toString();
                    LogUtil.i("sendLogToServer  xmlString=", xmlString);
                    return xmlString;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本更新信息
     *
     * @param versionCode
     * @param versionName
     * @return
     */
    public String getUpdateInfo(String versionCode, String versionName) {
        SoapObject request = new SoapObject(
                MobileService.SOAP_SERVER_INTERFACE, "checkUpdate");
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        StringBuffer reqValue = new StringBuffer();
        reqValue.append("<?xml version='1.0' encoding='utf-8'?>");
        reqValue.append("<DATASETS>");
        reqValue.append("<DATASET>");
        reqValue.append("<APPID>" + versionCode + "</APPID>");
        reqValue.append("<VERSION>" + versionName + "</VERSION>");
        reqValue.append("</DATASET>");
        reqValue.append("</DATASETS>");
        request.addProperty("condition", reqValue.toString());
        LogUtil.e("getUpdateInfo reqValue", reqValue.toString());
        envelope.bodyOut = request;
        // HttpTransportSE ht = new
        // HttpTransportSE(MobileService.SOAP_SERVER_URL);
        MyAndroidHttpTransport ht = new MyAndroidHttpTransport(
                MobileService.SOAP_SERVER_URL, 10000);
        ht.debug = true;
        try {
            ht.call(null, envelope);
            if (envelope.getResponse() != null) {
                SoapObject result = (SoapObject) envelope.bodyIn;
                Object object = result.toString();
                if (object != null) {
                    String xmlString = envelope.getResponse().toString();
                    LogUtil.e("getUpdateInfo  xmlString=", xmlString);
                    return xmlString;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

}
