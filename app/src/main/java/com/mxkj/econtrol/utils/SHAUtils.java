//package com.mxkj.econtol.utils;
//
//
//
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//
///**
// * Created by liangshan on 2017/3/21.
// *
// * @Destription:
// */
//
//public class SHAUtils {
//
//    public static String hmacSHA256(String secret, String message) {
//        String result = "";
//        try {
//            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
//            sha256_HMAC.init(secret_key);
//            result = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
//
//        } catch (Exception e) {
//            System.out.println("Error");
//        }
//        return result;
//    }
//
//}
