package com.mxkj.econtrol.utils;

import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;


public class RSAUtils {

    /**
     * 算法名称
     */
    private static final String ALGORITHOM = "RSA";
    /**
     * 保存生成的密钥对的文件名称。
     */
    private static final String RSA_PAIR_FILENAME = "classpath:keypair/RSA_PAIR.txt";
    /**
     * 密钥大小
     */
    private static final int KEY_SIZE = 512;
    /**
     * 默认的安全服务提供者
     */
    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();

    private static KeyPairGenerator keyPairGen = null;
    private static KeyFactory keyFactory = null;
    /**
     * 缓存的密钥对。
     */
    private static KeyPair oneKeyPair = null;

    private static File rsaPairFile = null;

    private static final String publicExponent = "65537";

    static {
        try {
            keyPairGen = KeyPairGenerator.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
            keyFactory = KeyFactory.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        } catch (NoSuchAlgorithmException ex) {

        }


    }

    private RSAUtils() {
    }


    /**
     * 根据给定的系数和专用指数构造一个RSA专用的公钥对象。
     *
     * @param modulus        系数。
     * @param publicExponent 专用指数。
     * @return RSA专用公钥对象。
     */
    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(
                modulus), new BigInteger(publicExponent));
        try {
            return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 根据给定的系数和专用指数构造一个RSA专用的私钥对象。
     *
     * @param modulus         系数。
     * @param privateExponent 专用指数。
     * @return RSA专用私钥对象。
     */
    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {
        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
        try {
            return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 使用指定的公钥加密数据。
     *
     * @param publicKey 给定的公钥。
     * @param data      要加密的数据。
     * @return 加密后的数据。
     */
    public static byte[] encrypt(PublicKey publicKey, byte[] data)
            throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        ci.init(Cipher.ENCRYPT_MODE, publicKey);
        return ci.doFinal(data);
    }


    /**
     * 使用给定的公钥加密给定的字符串。
     * <p/>
     * 若 {@code publicKey} 为 {@code null}，或者 {@code plaintext} 为 {@code null}
     * 则返回 {@code null}。
     *
     * @param publicKey 给定的公钥。
     * @param plaintext 字符串。
     * @return 给定字符串的密文。
     */
    public static String encryptString(PublicKey publicKey, String plaintext) {
        if (publicKey == null || plaintext == null) {
            return null;
        }
        byte[] data = plaintext.getBytes();
        try {
            byte[] en_data = encrypt(publicKey, data);
            return new String(Hex.encodeHex(en_data));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static RSAPublicKey createRSAPublicKey(String mode) {
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(mode), new BigInteger(publicExponent));
        try {
            return (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException e) {

            e.printStackTrace();
        }
        return null;
    }

//
//    /**
//     * 生产密钥对文件并且打印公钥的modulus和exponent(js里面需要用到modulus和exponent)
//     *
//     * @param args
//     * @throws Exception
//     */
//    public static void main(String[] args) throws Exception {
//        KeyPair keyPair = generateKeyPair();
//
//        RSAPublicKey publcikey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privatekey = (RSAPrivateKey) keyPair.getPrivate();
//
//        System.out.println("publcikey.getPublicExponent():" + publcikey.getPublicExponent().toString(16));
//        //转化成16进制字符串
//        String k = publcikey.getModulus().toString(32);
//        System.out.println("publcikey.getModulus().toString(16):" + publcikey.getModulus().toString(16).length());
//        System.out.println("publcikey.getModulus().toString(32)" + publcikey.getModulus().toString(32).length());
//        System.out.println(publcikey.getModulus().toString(16));
//        System.out.println(publcikey.getModulus().toString(32));
//        System.out.println("public1 key :" + k);
//        System.out.println("public2 key :" + publcikey.getModulus());
//        //还原为长整型
//        System.out.println("public3 key :" + new BigInteger(k, 32));
//
//        String text = "chenhailong";
//        String encryptText = RSAUtils.encryptString(text, keyPair);
//
//        String encryptText2 = RSAUtils.encryptString(publcikey, text);
//        System.out.println("encryptText2:" + encryptText2);
//        System.out.println("____________:" + (publcikey.getModulus().toString(16)));
//        System.out.println("base16Moduls:" + (privatekey.getModulus().toString(16)));
//        System.out.println("base16PrivateExponent:" + (privatekey.getPrivateExponent().toString(16)));
//        System.out.println("base64Moduls:" + Base64Helper.encode(privatekey.getModulus().toByteArray()));
//        System.out.println("base64PrivateExponent:" + Base64Helper.encode(privatekey.getPrivateExponent().toByteArray()));
//
//        String base16Moduls = "85aee3647fb3bdd170af2026bca81be1e41058035879bf1e9d025cb4a631a062c35f3c51215fb93f7e71f767b60fc74af7c5933d5a09fe9210865947eb284cfb";
//        String base16PrivateExponent = "19e7508299c81f60e4ffda3007605fbebead781075e569662a3059fc5b33c48af603ac1e083374cf51e4f33881a028529df7e2d56caada0be86e8967c8f0e5c9";
//        String base64Moduls = "AIWu42R/s73RcK8gJryoG+HkEFgDWHm/Hp0CXLSmMaBiw188USFfuT9+cfdntg/HSvfFkz1aCf6SEIZZR+soTPs=";
//        String base64PrivateExponent = "GedQgpnIH2Dk/9owB2Bfvr6teBB15WlmKjBZ/FszxIr2A6weCDN0z1Hk8ziBoChSnffi1Wyq2gvobolnyPDlyQ==";
//
//        System.out.println(base16Moduls);
//        System.out.println(base16PrivateExponent);
//        System.out.println(base64Moduls);
//        System.out.println(base64PrivateExponent);
//
//        BigInteger mo = Base64Helper.decodeInteger(base64Moduls);
//        BigInteger ex = Base64Helper.decodeInteger(base64PrivateExponent);
//        RSAPublicKey base64publickey = (RSAPublicKey) RSAUtils.createRSAPublicKey(mo.toString());
//        RSAPrivateKey base64privatekey = (RSAPrivateKey) RSAUtils.createRSAPrivateKey(mo.toString(), ex.toString());
//
//        String dd = "AEvjaEO+B3O8AEB+UGgpxTNaabBq6Sn1vDoawhOUJ6LFbKBi1WQ8OYYJ63Sllh7Q8lpJVc40OJQQ0Lsu/tETwvQ=";
//        String p = "123123123123";
//        String e = RSAUtils.encryptString(base64publickey, p);
//        System.out.println("____e:" + e);
//        String d = RSAUtils.decryptString(base64privatekey, Hex.encodeHexString(Base64Helper.decodeInteger(dd).toByteArray()));
//        System.out.println("____d:" + d);


//		RSAPublicKey publcikey4 = createRSAPublicKey((new BigInteger("8372860ef67752087b88ce681ed235f31c4099390e76a22b165316952664af47d61aaabf02cf43fe66462fea9a00e13cba4a734070bca0548ad1a27f5bc0e263",16)).toString());
//		String encryptText8 = RSAUtils.encryptString(publcikey4,text);
//		System.out.println("encryptText8:"+encryptText8);

//
//		//重构密码对
//		
//		
//		
//		System.out.println("public key ___________:" + Hex.encodeHexString(publcikey.getModulus().toByteArray()));
//		System.out.println("public key ___________:" + Hex.encodeHexString(publcikey.getModulus().toByteArray()).length());
//		RSAPublicKey publcikey2 = createRSAPublicKey(String.valueOf(publcikey.getModulus()));
//		RSAPrivateKey privatekey2 = createRSAPrivateKey(String.valueOf(privatekey.getModulus())  , String.valueOf(privatekey.getPrivateExponent())  );
//		System.out.println("public22222 key :" + publcikey.equals(publcikey2));
//		System.out.println("private222222 key :" + privatekey.equals(privatekey2));
//		System.out.println("000000000000private222222 key :" + publcikey2.getModulus().toString(16));
//		System.out.println("000000000000private222222 key :" + privatekey.getPrivateExponent().toString(16));
//		String oringText2 =  RSAUtils.decryptString(privatekey2, encryptText);
//		System.out.println("oringText:"+oringText2);
//		System.out.println(text.equals(oringText2));
//		
//		
//		System.out.println("public key ___________:" + Hex.encodeHexString(publcikey.getModulus().toByteArray()));
//		RSAPublicKey publcikey3 = createRSAPublicKey((new BigInteger(publcikey.getModulus().toString(16),16)).toString());
//		RSAPrivateKey privatekey3 = createRSAPrivateKey((new BigInteger(privatekey.getModulus().toString(16),16)).toString()  ,(new BigInteger(privatekey.getPrivateExponent().toString(16),16)).toString());
//		System.out.println("public3333333333 key :" + publcikey.equals(publcikey3));
//		System.out.println("private3333333333 key :" + privatekey.equals(privatekey3));
//		String oringText3 =  RSAUtils.decryptString(privatekey3, encryptText);
//		System.out.println("oringText3333333333:"+oringText3);
//		System.out.println("3333333333:"+text.equals(oringText3));
//		
//		
//		RSAPublicKey publcikey4 = createRSAPublicKey((new BigInteger("8372860ef67752087b88ce681ed235f31c4099390e76a22b165316952664af47d61aaabf02cf43fe66462fea9a00e13cba4a734070bca0548ad1a27f5bc0e263",16)).toString());
//		String encryptText8 = RSAUtils.encryptString(publcikey4,text);
//		System.out.println("encryptText8:"+encryptText8);
}
//}