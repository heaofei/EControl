package com.mxkj.econtrol.utils;

import android.content.Context;
import android.content.Intent;

import com.mxkj.econtrol.app.APP;
import com.mxkj.econtrol.ui.activity.LoginRegistActivity;

/**
 * Created by ${  chenjun  } on 2018/8/3.
 */

public class LoginUtil {


    /***
     * 判断是否登录，然后跳转登录页面
     * ***/
    public static boolean isLogin(Context context) {
        if (APP.isLogin) {
            return true;
        } else {
            context.startActivity(new Intent(context, LoginRegistActivity.class));
            return false;
        }
    }

    /*****
     * 判断是否处于体验状态，然后跳转登录页面
     * @param context
     * @return
     */
    public static boolean isExperience(Context context) {
        if (APP.isExperience) {
            return true;
        } else {
//            context.startActivity(new Intent(context, LoginRegistActivity.class));
            return false;
        }
    }


}
