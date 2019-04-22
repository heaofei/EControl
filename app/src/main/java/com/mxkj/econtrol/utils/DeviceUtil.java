package com.mxkj.econtrol.utils;

import java.util.List;
import java.util.UUID;

import com.mxkj.econtrol.app.APP;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author cjl
 * @version V1.0
 * @Title: PhoneUtil.java
 * @Package com.flygift.framework.util
 * @Description: 手机工具类
 * @date 2015年1月1日 上午11:16:10
 */

public class DeviceUtil {

    /**
     * @Title: getDeviceID @Description: 获取手机唯一id @param context @return String
     * 返回类型 @throws
     */
    public static String getDeviceID(Context context) {
        String deviceID = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (deviceID == null || deviceID.length() == 0) {
            deviceID = UUID.randomUUID().toString();
        }
        return deviceID;
    }


    //获取UUID
    public static String getDeviceUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString().replaceAll("-", "");
        Log.d("debug", "uuid=" + uniqueId);
        return uniqueId;

    }

    /**
     * @Title: getVersionName @Description: 获取当前应用的版本号 @param context @return
     * String @throws
     */
    public static String getVersionName(Context context) {
        String version = "";
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return version;
    }

    /**
     * @Title: getVersionName @Description: 获取当前应用的代码版本号 @param context @return
     * String @throws
     */
    public static int getVersionCode(Context context) {
        int version = 1;
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionCode;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * @Title: showSoftkeyboard @Description: 是否隐藏输入法 @param @param
     * et @param @param open @return void @throws
     */
    public static void showSoftkeyboard(View view, boolean open) {
        InputMethodManager iim = (InputMethodManager) APP.getInstance()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            if (open) {
                iim.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN);
            } else {
                iim.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }

    }

    /**
     * @return boolean
     * @author：liaoxy
     * @Description: 是否有后置摄像头
     */
    public static boolean hasBackFacingCamera() {
        final int CAMERA_FACING_BACK = 0;
        return checkCameraFacing(CAMERA_FACING_BACK);

    }

    /**
     * @return boolean
     * @author：liaoxy
     * @Description: 是否有前置摄像头
     */
    public static boolean hasFrontFacingCamera() {

        final int CAMERA_FACING_BACK = 1;
        return checkCameraFacing(CAMERA_FACING_BACK);
    }

    /**
     * @return int
     * @author：liaoxy
     * @Description: 获取SDK版本
     */
    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * @param facing
     * @return boolean
     * @author：liaoxy
     * @Description:检查摄像头
     */
    private static boolean checkCameraFacing(final int facing) {

        if (getSdkVersion() < Build.VERSION_CODES.GINGERBREAD) {
            return false;
        }
        final int cameraCount = Camera.getNumberOfCameras();
        CameraInfo info = new CameraInfo();
        for (int i = 0; i < cameraCount; i++) {

            Camera.getCameraInfo(i, info);
            if (facing == info.facing) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return boolean
     * @author：ls
     * @Description:检查手机有没安装某个软件
     */
    public static boolean isInstall(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }

        return false;
    }
}
