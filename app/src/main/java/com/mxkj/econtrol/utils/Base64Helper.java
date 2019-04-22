package com.mxkj.econtrol.utils;


import org.apache.commons.net.util.Base64;

/**
 * Created by mx01 on 2017/3/27.
 */

public class Base64Helper {

    public static String encode(byte[] data) {
        return Base64.encodeBase64String(data,true);
    }

    public static byte[] decode(String str){
        return Base64.decodeBase64(str);
    }
}
