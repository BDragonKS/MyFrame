package com.dragon.mobile.baseframe.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.dragon.mobile.baseframe.constant.Constant;

import java.util.List;
import java.util.Objects;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * <dl>  Class Description
 * <dd> 项目名称：LRMEA
 * <dd> 类名称：
 * <dd> 类描述：
 * <dd> 创建时间：2017/10/13
 * <dd> 修改人：无
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 *
 * @author Jing Lu
 * @version 1.0
 */

public class AppUtil {

    private static class AppUtilHolder {
        static final AppUtil INSTANCE = new AppUtil();
    }

    private AppUtil() {
    }

    public static AppUtil getInstance() {

        return AppUtilHolder.INSTANCE;
    }

    @SuppressLint("HardwareIds")
    public String getDeviceID(Context context) {

        if (!TextUtils.isEmpty(Constant.deviceID)) {
            return Constant.deviceID;
        }

        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Constant.deviceID = Objects.requireNonNull(TelephonyMgr).getDeviceId();
        if (!TextUtils.isEmpty(Constant.deviceID)) {
            Constant.deviceID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return Constant.deviceID;
    }

    /**
     * 获取登录设备mac地址
     *
     * @return Mac地址
     */
    @SuppressLint("HardwareIds")
    public String getMacAddress(Context context) {
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wm != null;
        WifiInfo connectionInfo = wm.getConnectionInfo();
        String mac = connectionInfo.getMacAddress();
        return mac == null ? "" : mac;
    }


    /**
     * 获取当前版本号
     *
     * @param context 上下文对象
     * @return 版本号
     */
    public String getVersionName(Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /***
     * 判断app是否处在前台
     *
     * @return boolean true:在前台；false：不在前台。
     */
    public boolean isAppOnForeground(Context context) {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) context.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = Objects.requireNonNull(activityManager)
                .getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取项目名称
     * @param context 上下文对象
     * @return 名称字符串
     */
    public String getProjectName(Context context) {
        PackageManager pm = context.getPackageManager();
        return context.getApplicationInfo().loadLabel(pm).toString();
    }
}
