package com.mxkj.econtrol.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mxkj.econtrol.app.APP;

import java.lang.reflect.Field;

/**
 * @version V1.0
 * @Title: ScreenUtil.java
 * @Description: 屏幕分辨率工具类
 */

public class ScreenUtil {

    /**
     * 屏幕宽高
     *
     * @param context
     * @return
     */
    private static int[] getScreenSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        return new int[]{screenWidth, screenHeight};
    }

    /**
     * @param context
     * @return int
     * @Title: getStatusBarHeight
     * @Description: 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * @param @return 设定文件
     * @return int 返回类型
     * @throws
     * @Title: getScreenWidth
     * @Description: 获取手机屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        int screen[] = getScreenSize(context);
        return screen[0];
    }

    /**
     * @param @return 设定文件
     * @return int 返回类型
     * @throws
     * @Title: getScreenHeight
     * @Description: 获取手机屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        int screen[] = getScreenSize(context);
        return screen[1];
    }

    /**
     * 根据手机分辨率将dp转为px单位
     */
    public static int dip2px(float dpValue) {
        final float scale = APP.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = APP.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 屏幕宽高
     *
     * @param
     * @return
     */
    private static int[] getScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = APP.getInstance().getApplicationContext().getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        return new int[]{screenWidth, screenHeight};
    }

    /**
     * @param @return 设定文件
     * @return int 返回类型
     * @throws
     * @Title: getScreenWidth
     * @Description: 获取手机屏幕的宽度
     */
    public static int getScreenWidth() {
        int screen[] = getScreenSize();
        return screen[0];
    }

    /**
     * @param @return 设定文件
     * @return int 返回类型
     * @throws
     * @Title: getScreenHeight
     * @Description: 获取手机屏幕的高度
     */
    public static int getScreenHeight() {
        int screen[] = getScreenSize();
        return screen[1];
    }

    /**
     * @return
     * @author：liaoxy
     * @Description:是否要显示输入法 有bug
     */
    public static void showSoftInput(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) APP.getInstance().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开,坑爹的是一直为true

        if (isShow ^ isOpen) {//如果不同则为真
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }
}
