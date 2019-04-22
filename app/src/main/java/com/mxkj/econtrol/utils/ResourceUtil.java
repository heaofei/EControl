package com.mxkj.econtrol.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.mxkj.econtrol.app.APP;

/**
 * Created by liangshan on 2017/3/29.
 *
 * @Description: 获取资源工具类
 */

public class ResourceUtil {


    public static float getDimens(int resId) {
        return APP.getInstance().getResources().getDimension(resId);
    }

    public static String getString(int resId) {
        return APP.getInstance().getResources().getString(resId);
    }

    public static int getColor(int resId) {
        return APP.getInstance().getResources().getColor(resId);
    }

    public static Drawable getDrawable(int resId) {
        return APP.getInstance().getResources().getDrawable(resId);
    }

    
}
