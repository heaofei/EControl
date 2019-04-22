package com.mxkj.econtrol.utils;

import android.util.Log;

/**
 * Created by liangshan on 2017/3/28.
 *
 * @Description:日志工具类
 */

public class LogUtil {

    public static String tag = "tag";
    public static boolean isDebug = true;

    public static void i(String msg) {
        if (isDebug) {
            Log.i(tag, msg);
        }

    }

    public static void e(String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void v(String msg) {
        if (isDebug) {
            Log.v(tag, msg);
        }
    }

    public static void w(String msg) {
        if (isDebug) {
            Log.w(tag, msg);
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            Log.d(tag, msg);
        }
    }
}
