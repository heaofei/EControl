package com.mxkj.econtrol.utils;


import org.apache.commons.net.util.Base64;

import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DESUtils {


    //算法名称
    public static final String KEY_ALGORITHM = "DES";
    //算法名称/加密模式/填充方式
    //DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式

    public static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";


    public static String getDefault8Keys(){
        String s = UUID.randomUUID().toString();
        System.out.println(s);
        String [] str = s.split("-");
        s = "";
        for(String st : str){
            s = s + st;
        }
        return s.substring(0,8);
    }



    private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8};
    public static String decryptDES(String decryptString, String decryptKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), KEY_ALGORITHM);
        byte[] byteMi = Base64.decodeBase64(decryptString);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);

        return new String(decryptedData);
    }


    public static String encryptDES(String encryptString, String encryptKey)
            throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return Base64.encodeBase64String(encryptedData);
    }


    public static void main(String[] args) throws Exception {
        String key =getDefault8Keys();
        String data = "{\"device\":\"SHZJHFSJ131803\",\"serial\":\"01njSuxhhFbd\",\"action\":\"REGIST\",\"clientId\":\"Cient1000\",\"appId\":\"6953E411FCE84CFD150406C3E2A558AD0ECB3BED\",\"randomStr\":\"20171206150232\",\"sign\":\"f1f64c215c1f18f2d51b2e85eefa47de952b471d\"}";
//      String result = "XfLgGoqjor3C9kXQy2Jh+0vLnAxayKD2cJFRUdIs20q4HWAUM+VU7rB9Fb5IwZUDcH1mw1UUgFtQHR+LFey9YjPkVTWWY20ODPCMoXt633oO8LkaVev1vrxkZ8NfZEGwGTZR+vzZxRzFoN4i97fZwojkPD80wQCQ2j+lib7FQDP7xW2Uh+RIX0oawoMMMhouWRn93Bi/woWGcEd/v5Md0vFA6iZCnUo9NXwX3m4kDyLf2lsV+QGFliywnxG1Nkz9nyTtyXdZdCTQuKim5YAf3F89cDQlVNkFgRsa4A5tnfI=";

        System.out.println("键值("+key.length()+")："+key);
        System.out.println("数据（"+data.length()+"）："+data);

        String result  = encryptDES(data, key);//用DES加密
        System.out.println("加密结果("+result.length()+")："+result);
        System.out.println("解密结果："+decryptDES(result,key));
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     * @param value 指定的字符串
     * @return 字符串的长度
     */
    public static int length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 3;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }
}
