package com.mxkj.econtrol.utils;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * 工具类
 */
public class SHAHelper {
    /**
     * SHA-1消息摘要算法
     */
    public static String hmacSHA256(String key, String content) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] secretByte = key.getBytes("utf-8");
            byte[] dataBytes = content.getBytes("utf-8");

            SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");
            mac.init(secret);

            byte[] doFinal = mac.doFinal(dataBytes);
            //byte[] base64 = new Base64().encode(doFinal,Base64.DEFAULT);
            /*
            * CRLF 这个参数看起来比较眼熟，它就是Win风格的换行符，
            * 意思就是使用CR LF这一对作为一行的结尾而不是Unix风格的LF
            * DEFAULT 这个参数是默认，使用默认的方法来加密
            * NO_PADDING 这个参数是略去加密字符串最后的”=”
            * NO_WRAP 这个参数意思是略去所有的换行符（设置后CRLF就没用了）
            * URL_SAFE 这个参数意思是加密时不使用对URL和文件名有特殊意义的字符来作为加密字符，
            具体就是以-和_取代+和/*/
            byte[] base64 = Base64.encode(doFinal, Base64.NO_WRAP|Base64.URL_SAFE| Base64.NO_PADDING);
            //encode(doFinal);
            return new String(base64, "utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String hmacSHA256(String key, Map<String, ?> map) {
        StringBuilder s = new StringBuilder();
        for (Object values : map.values()) {
            if (values instanceof String[]) {
                for (String value : (String[]) values) {
                    s.append(value);
                }
            } else if (values instanceof List) {
                for (String value : (List<String>) values) {
                    s.append(value);
                }
            } else {
                s.append(values);
            }
        }
        return hmacSHA256(key, s.toString());
    }

//    public static void main(String[] args) throws Exception {
//        String testString = "admin123#11111111111111111111111111111sdfasdfwerj34511111111111111111111111111111111111111111111111111111111111111111111";
//        System.out.println(SHAHelper.hmacSHA256("123123123", testString));
//        System.out.println(SHAHelper.hmacSHA256("123123123", testString).length());
//
//    }



    /**
     * SHA加密
     *
     * @param strSrc
     *            明文
     * @return 加密之后的密文
     */
    public static String hmacSHA1(String strSrc) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-1");// 将此换成SHA-1、SHA-512、SHA-384 、SHA-256等参数
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
            strDes=strDes.toUpperCase(); // 转大写输出
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    /**
     * byte数组转换为16进制字符串
     *
     * @param bts
     *            数据源
     * @return 16进制字符串
     */
    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }


}
