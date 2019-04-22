package com.mxkj.econtrol.utils;


import com.mxkj.econtrol.app.APP;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class RSAPKCS8X509Utils {
    // From: http://blog.csdn.net/chaijunkun/article/details/7275632
    private static RSAPKCS8X509Utils mRsa_pkcs8_x509_utils = null;
    /**
     * 私钥
     */
    private RSAPrivateKey privateKey;
    /**
     * 公钥
     */
    private RSAPublicKey publicKey;
    /**
     * 字节数据转字符串专用集合
     */
    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    /**
     * 保存生成的密钥对的文件名称。
     */
    private static final String RSA_PRIVATE_FILENAME = "server_pkcs8_private_key.pem";

    private static final String RSA_PUBLIC_FILENAME = "server_rsa_public_key.pem";


    /**
     * 获取私钥
     *
     * @return 当前的私钥对象
     */
    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     *
     *
     */
    private RSAPKCS8X509Utils() {

        try {
            loadPublicKey(APP.getInstance().getAssets().open(RSA_PUBLIC_FILENAME));
            loadPrivateKey(APP.getInstance().getAssets().open(RSA_PRIVATE_FILENAME));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static RSAPKCS8X509Utils getInstance() {
        if (mRsa_pkcs8_x509_utils == null) {
            synchronized (RSAPKCS8X509Utils.class) {
                if (mRsa_pkcs8_x509_utils == null) {
                    mRsa_pkcs8_x509_utils = new RSAPKCS8X509Utils();
                }
            }

        }
        return mRsa_pkcs8_x509_utils;
    }


    /**
     * 随机生成密钥对
     */
    public void genKeyPair() {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.publicKey = (RSAPublicKey) keyPair.getPublic();
    }


    public String getKeyFromFile(String filePath) {
        BufferedReader bufferedReader = null;
        List<String> list = new ArrayList<String>();
        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // remove the firt line and last line
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < list.size() - 1; i++) {
            stringBuilder.append(list.get(i)).append("\r");
        }
        String key = stringBuilder.toString();
        return key;
    }

    public String decryptWithBase64(String base64String) {
        byte[] binaryData = decrypt(getPrivateKey(), Base64Helper.decode(base64String));
        String string = new String(binaryData);
        return string;
    }

    public String encryptWithBase64(String string) {
        byte[] binaryData = encrypt(getPublicKey(), string.getBytes());
        String base64String = Base64Helper.encode(binaryData);
        return base64String;
    }

    /**
     * 获取公钥
     *
     * @return 当前的公钥对象
     */
    public RSAPublicKey getPublicKey() {
        return publicKey;
    }


    /**
     * 从文件中输入流中加载公钥
     *
     * @param in 公钥输入流
     * @ 加载公钥时产生的异常
     */
    public void loadPublicKey(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            loadPublicKey(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr 公钥数据字符串
     * @ 加载公钥时产生的异常
     */
    public void loadPublicKey(String publicKeyStr) {
        try {
            byte[] buffer = Base64Helper.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中加载私钥
     *
     * @param in 私钥文件名流
     * @return 是否成功
     * @
     */
    public void loadPrivateKey(InputStream in) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in));
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            loadPrivateKey(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadPrivateKey(String privateKeyStr) {
        try {
            byte[] buffer = Base64Helper.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密过程
     *
     * @param publicKey     公钥
     * @param plainTextData 明文数据
     * @return
     * @ 加密过程中的异常信息
     */
    public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// 安卓的rsa记得配置这个 ，不然解析出来会有乱码
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output = cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密过程
     *
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @ 解密过程中的异常信息
     */
    public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // 安卓的rsa记得配置这个 ，不然解析出来会有乱码
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字节数据转十六进制字符串
     *
     * @param data 输入数据
     * @return 十六进制内容
     */
    public static String byteArrayToString(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            //取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移  
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
            //取出字节的低四位 作为索引得到相应的十六进制标识符  
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
            if (i < data.length - 1) {
                stringBuilder.append(' ');
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
//        String privateKeyPath = "d:/rsa_public_key.pem";        // replace your public key path here
//        String publicKeyPath =  "d:/pkcs8_private_key.pem";     // replace your private path here
//        RSA_PKCS8_X509_Utils rsaEncryptor = new RSA_PKCS8_X509_Utils(privateKeyPath, publicKeyPath);
//        try {
//            String test = "JAVA";
//            String testRSAEnWith64 = rsaEncryptor.encryptWithBase64(test);
//            String testRSADeWith64 = rsaEncryptor.decryptWithBase64("C9MT7MqL5m51CV/h5lb/08aP2/w24CXynOL3uOGySxuh/wxkDzOePdnVTpeEevAlKRd1yEKAosaZmijVXMCS/DUDKh1D1YqWKywDC5/DT+rftdRulJfF7yYp+Zt9gpIHoOloqbBBNHy+mry3OJj4JpAiCWux13rYFnQeAoML81Q=");
//            System.out.println("\nEncrypt: \n" + testRSAEnWith64);
//            System.out.println("\nDecrypt: \n" + testRSADeWith64);
//
        // NSLog the encrypt string from Xcode , and paste it here.
        // 请粘贴来自IOS端加密后的字符串
//            String rsaBase46StringFromIOS =
//                    "nIIV7fVsHe8QquUbciMYbbumoMtbBuLsCr2yMB/WAhm+S/kGRPlf+k2GH8imZIYQ" + "\r" +
//                    "QBDssVLQmS392QlxS87hnwMRJIzWw6vdRv/k79TgTfu6tI/9QTqIOvNlQIqtIcVm" + "\r" +
//                    "R/suvydoymKgdlB+ce5/tHSxfqEOLLrL1Zl2PqJSP4A=";
//            
//            String decryptStringFromIOS = rsaEncryptor.decryptWithBase64(rsaBase46StringFromIOS);
//            System.out.println("Decrypt result from ios client: \n" + decryptStringFromIOS);

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}