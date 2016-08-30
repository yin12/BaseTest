package com.example.basetest.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.basetest.base.Constants;
import com.example.basetest.base.Version;
import com.example.basetest.projectmanage.XML;
import com.example.basetest.projectmanage.XmlReader;

import net.bither.util.NativeUtil;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.icss.monopoly.phone.base.App;
//import com.icss.monopoly.phone.base.Constants;
//import com.icss.monopoly.phone.base.Version;
//import com.icss.monopoly.phone.bean.CodeDirectory;
//import com.icss.monopoly.phone.bean.EntranceCode;
//import com.icss.monopoly.phone.projectmanage.XML;
//import com.icss.monopoly.phone.projectmanage.XmlReader;
//import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
//
//import net.bither.util.NativeUtil;

/**
 * Created by john on 2015/11/20.
 */
public class Utils {

    protected static final String TAG = "Utils";

    /**
     * INT 值检查
     *
     * @param value
     * @return结果值
     */
    public static int checkInteger(String value) {
        if (checkValue(value).trim().equals("")) {
            return 0;
        } else {
            return Integer.valueOf(value.trim());
        }
    }

    /**
     * DOUBLE 值检查
     *
     * @param value
     * @return结果值
     */
    public static double checkDouble(String value) {
        if (checkValue(value).equals("")) {
            return 0.0;
        } else {
            return Double.valueOf(value);
        }
    }

    /**
     * LONG 值检查
     *
     * @param value
     * @return结果值
     */
    public static long checkLong(String value) {
        if (checkValue(value).equals("")) {
            return 0;
        } else {
            return Long.valueOf(value);
        }
    }

    /**
     * STRING 值检查
     *
     * @param value
     * @return结果值
     */
    public static String checkValue(String value) {
        if (value == null) {
            return "";
        } else {
            return value;
        }
    }

    /**
     * STRING 值检查
     *
     * @param value
     * @return结果值
     */
    public static String checkPhone(String value) {
        if (value == null) {
            return "";
        } else {
            return value.replace("+86", "");
        }
    }

    /**
     * 获取字符串转换为拼音（大写）后的第一个字符
     *
     * @param value
     * @return
     */
    public static String getFormattedNumber(String value) {
        String c = "";
        if (checkValue(value).length() > 0) {
            c = PinyinUtil.getNameNum(value.trim()) + "";
        }
        LogUtil.e(TAG, TAG + value + "=" + c);
        return c;
    }

    /**
     * 获取字符串转换为拼音（大写）后的第一个字符
     *
     * @param value
     * @return
     */
    public static String getPinyin(String value) {
        String c = "";
        if (checkValue(value).length() > 0) {
            c = PinyinUtil.getPinyin(value.trim()) + "";
        }
        return c;
    }

    /**
     * 获取字符串转换为拼音（大写）后的第一个字符
     *
     * @param value
     * @return
     */
    public static String getFirstChar(String value) {
        String c = "";
        if (checkValue(value).length() > 0) {
            c = PinyinUtil.getPinyin(getFirstString(value.trim())).charAt(0) + "";
        }
        return c;
    }

    public static String getFirstString(String value) {
        String s = "";
        if (checkValue(value).length() > 0) {
            s = value.charAt(0) + "";
        }
        return s;
    }

    public static String getFirstCharString(String value) {
        String s = "";
        if (checkValue(value).length() > 0) {
            s = value.charAt(0) + "";
            if (!s.matches("[a-zA-Z]")) {
                return "#";
            }
        }
        return s;
    }

    /**
     * 英文字母转大写
     *
     * @param value
     * @return
     */
    public static String toUpperCase(String value) {
        StringBuffer sb = new StringBuffer();
        if (value != null) {
            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                if (!Character.isUpperCase(c)) {
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    public static String toLowerCase(String value) {
        StringBuffer sb = new StringBuffer();
        if (value != null) {
            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                if (!Character.isLowerCase(c)) {
                    sb.append(Character.toLowerCase(c));
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }


    /**
     * 进入拨打电话页面
     *
     * @param context
     * @param telPhone
     */
    public static void callTel(Context context, String telPhone) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + telPhone));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 进入拨打电话页面
     *
     * @param context
     * @param telPhone
     */
    public static void editTel(Context context, String telPhone) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + telPhone));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 进入短信编辑页面
     *
     * @param context
     * @param telPhone
     */
    public static void sendMsg(Context context, String telPhone) {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:" + telPhone));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> boolean collectionIsEmpty(Collection<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }


    /**
     * 文本控件赋值
     *
     * @param txtValue
     * @param value
     */
    public static void setTextView(TextView txtValue, String value) {
        txtValue.setText(checkValue(value));
    }


    /**
     * 文本控件赋值且根据值是否为空显示与隐藏
     *
     * @param txtValue
     * @param value
     */
    public static void setTextViewShow(TextView txtValue, String value) {
        txtValue.setText(checkValue(value));
        txtValue.setVisibility(checkValue(value).equals("") ? View.GONE : View.VISIBLE);
    }

    /**
     * 文本控件赋值且根据值是否为空显示与隐藏
     *
     * @param txtValue
     * @param value
     */
    public static void setTextViewColor(TextView txtValue, String value, String sValue, int Color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(
                checkValue(value));
        if (checkValue(value).length() >= checkValue(sValue).length()
                && checkValue(value).contains(checkValue(sValue))) {
            int str = value.indexOf(sValue);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(
                    Color);
            builder.setSpan(redSpan, str, str + sValue.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        txtValue.setText(builder);
        txtValue.setVisibility(View.VISIBLE);
    }

    /**
     * A中是否包含B
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean contains(String a, String b) {
        if (checkValue(a).equals(""))
            return false;
        return checkValue(a).contains(checkValue(b));
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void backgroundAlpha(Context context, float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
    }

    /*
      检测服务是否在运行
     */
    public static boolean isServiceRunning(Context context, String service_Name) {
        ActivityManager manager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (service_Name.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取版本编号
     *
     * @param context
     * @return
     */
    public static String getVerCode(Context context) {
        String verCode = "1";
        try {
            verCode = String.valueOf(context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = String.valueOf(context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;

    }

    /*
        * App 进程是否存在
        */

    public static boolean isAction(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> appList3 = am
                .getRunningTasks(1000);
        for (ActivityManager.RunningTaskInfo running : appList3) {
            if (packageName.equals(running.baseActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /*
     * 当前Activity是否是className
     */
    public static boolean isTopActivity(Context context, String className) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            LogUtil.d("fff", "isTopActivity-----=" + topActivity.getClassName());
            if (className.equals(topActivity.getClassName())) {
                return true;
            }
        }
        return false;
    }


    /*
     * 当前App是否在顶部
     */
    public static boolean isTopApp(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            LogUtil.d("fff", "getPackageName-----=" + topActivity.getPackageName());
            if (packageName.equals(topActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断当前是否为飞行模式
     *
     * @param context
     * @return
     */
    public static boolean IsAirModeOn(Context context) {
        return (Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) == 1 ? true : false);
    }

    // 关闭软键盘
    public static void closeInputIM(Context context, EditText edt_input) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_input.getWindowToken(), 0);
    }


    public static void savePicture(final Bitmap bitmap, final String path) {
        if (bitmap == null)
            return;
        LogUtil.i("Util", "savePicture" + path);
        new Thread(new Runnable() {
            public void run() {
                try {
                    File file = new File(path);
                    if (file.exists()) {
                        file.delete();
                    }
                    int quality = 90;// original
                    NativeUtil.compressBitmap(bitmap, quality, path, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static boolean sdcardAvailable;
    private static boolean sdcardAvailabilityDetected;

    public static boolean isSDCardAvailable() {
        if (!sdcardAvailabilityDetected) {
            sdcardAvailable = detectSDCardAvailability();
            sdcardAvailabilityDetected = true;
        }
        return sdcardAvailable;
    }

    public static synchronized boolean detectSDCardAvailability() {
        boolean result = false;
        try {
            Date now = new Date();
            long times = now.getTime();
            String fileName = "/sdcard/" + times + ".test";
            File file = new File(fileName);
            result = file.createNewFile();
            file.delete();
        } catch (Exception e) {
            // Can't create file, SD Card is not available
            e.printStackTrace();
        } finally {
            sdcardAvailabilityDetected = true;
            sdcardAvailable = result;
        }
        return result;
    }

    public static long getSDCardSize() {
        long size = 0;
        File sdcardDir = Environment.getExternalStorageDirectory();
        if (sdcardDir != null) {
            StatFs statFs = new StatFs(sdcardDir.getPath());
            long blockSize = statFs.getBlockSize();
            long availableBlocks = statFs.getBlockCount();
            size = availableBlocks * blockSize;
        }
        return size;
    }

    // 获取SD卡剩余存储空间
    public static long getSDAvailaleSize() {
        long size = 0;
        File sdcardDir = Environment.getExternalStorageDirectory();
        if (sdcardDir != null) {
            StatFs statFs = new StatFs(sdcardDir.getPath());
            long blockSize = statFs.getBlockSize();
            long availableBlocks = statFs.getAvailableBlocks();
            size = availableBlocks * blockSize;
        }
        return size;
    }


    /**
     * 得到保留两位小数的字符串
     *
     * @param s
     * @return
     */
    public static String getRetainTwoDecimalDigitsStringFromString(String s) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String result = "";
        try {
            Double d = Double.parseDouble(s);
            result = decimalFormat.format(d);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }

    /**
     * 得到保留0位小数的字符串
     *
     * @param s
     * @return
     */
    public static String getRetainZeroDecimalDigitsStringFromString(String s) {
        DecimalFormat decimalFormat = new DecimalFormat("0");
        String result = "";
        try {
            Double d = Double.parseDouble(s);
            result = decimalFormat.format(d);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
        return result;
    }

    public static boolean openGPSBoolean(Context context) {
        LocationManager alm = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * URL检查<br>
     * <br>
     *
     * @param pInput 要检查的字符串<br>
     * @return boolean   返回检查结果<br>
     */
    public static boolean isUrl(String pInput) {
        if (pInput == null) {
            return false;
        }
        String regEx = "^(http|https|ftp)//://([a-zA-Z0-9//.//-]+(//:[a-zA-"
                + "Z0-9//.&%//$//-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
                + "2}|[1-9]{1}[0-9]{1}|[1-9])//.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
                + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-4][0-9]|"
                + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-"
                + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
                + "-9//-]+//.)*[a-zA-Z0-9//-]+//.[a-zA-Z]{2,4})(//:[0-9]+)?(/"
                + "[^/][a-zA-Z0-9//.//,//?//'///////+&%//$//=~_//-@]*)*$";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(pInput);
        return matcher.matches();
    }

    /**
     * 返回当前程序版本号
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            } else {
                int index = versionName.indexOf(",");
                versionName = versionName.substring(index + 1,
                        versionName.length());
            }
        } catch (Exception e) {
            LogUtil.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * @param xmlString
     * @throws Exception
     */
    public static XML getXML(String xmlString) throws Exception {
        XmlReader reader = new XmlReader(xmlString, true);
        int tag = reader.next();
        while (tag != XmlReader.END_DOCUMENT) {
            tag = reader.next();
        }
        return reader.getXML();
    }

    public static String getApkSizeString(long length) {
        String result = "";
        if (length < 1024) {
            result = String.format("%.2fB", length / 1.0);
        } else if (length < 1024 * 1024) {
            result = String.format("%.2fK", length / 1024.0);
        } else if (length < 1024 * 1024 * 1024) {
            result = String.format("%.2fM", length / 1024.0 / 1024);
        } else {
            result = String.format("%.2fG", length / 1024.0 / 1024 / 1024);
        }
        return result;
    }

    /**
     * 校验扫描的二维码
     */
    public static String checkScanCode(String scancode) {
        if (!Utils.checkValue(scancode).equals("")) {
            if (Version.UMENG_CHANNEL.equals(Constants.APP_SHANXI)) {
                /**山西的二维码需要特殊处理**/
                String[] codeArr = scancode.split("!");
                if (codeArr != null && codeArr.length > 0) {
                    scancode = codeArr[codeArr.length - 1];
                }

            } else {
                if (scancode.contains("编码:")) {// 六安的二维码
                    // 提取客户简码
                    int start = scancode.indexOf(":");
                    int end = scancode.indexOf(" ");
                    scancode = scancode.substring(start + 1, end);
                }
            }
        }
        return scancode.trim();
    }

}

