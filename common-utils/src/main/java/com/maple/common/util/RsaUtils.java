package com.maple.common.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/*import java.util.Base64;*/

//java 后端
public class RsaUtils {
    //私钥
    public static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALcxLg7mAo2AwN/U\n" +
            "azLHwFFWHlmCp73qEvySUIt7IlPmdNtlvrhMeSVt6b5n9K6mMnMYZ/d4t8cTwncq\n" +
            "ml7Q0SlbB8g2yTYd2zit/Yhi6MTSnth1LQkjrc6b0Jwhf8h7n9QIuJguBSfn9fMI\n" +
            "tHrZnmWz1PEZUT3KFlSWmCSjfcvtAgMBAAECgYAnQxUaAFX/ud6cmkw3X/Rp+kka\n" +
            "6hPFckuLclhs7IEY9kWJWC8eFNXKHdqBAorAe8JIRKzTrz3EHwoIvErFk3+gPnrz\n" +
            "GKLLYARZxmyFL9O6bgve0zK4171PNBvYV8vFrEj32WIfESlLbQSi6/vtwZRoaUcU\n" +
            "VuHlJi8ixI874nDrQQJBAORhR0Xl5T+BGUyAScSDyIBeqFvrcBQUMY8mAkzANII7\n" +
            "Zoqt6b5TIJtuBOE8cgn/lw3S41jEo63LM/Bwn245OdUCQQDNWN6ObxuZD/vTioUl\n" +
            "d7eT27YTUqgSE3RFSw2I/+zE9o9ZezMPfi79NgZGG/ZWx7TGBZi04WNSfex+BWS8\n" +
            "Tn25AkEA2/UVQCz7bv5X72ZfpfWG1pxUarOHbs9ELCrfbCxyZjvvtwFpbvWBIjIE\n" +
            "YSpma1rPkXDbplM9kdV0YM8XvoE/QQJAbAByxY0wgbeDZoZzcvpdRloHkHAnz6IF\n" +
            "xDAuRUD9obZgPNrsRxjB1BGnCRbJ4GiW7pOg9nmYyJlMWheyHMxXmQJAKZrcI5QE\n" +
            "hE85kGoTl3iYge2mOLxEY8cXQH2xPIDYZ2NnANV/94u7bYZmy6edh9+tIABP7Qgb\n" +
            "khzG5rWmAR8s8w==";
    //公钥
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3MS4O5gKNgMDf1Gsyx8BRVh5Z\n" +
            "gqe96hL8klCLeyJT5nTbZb64THklbem+Z/SupjJzGGf3eLfHE8J3Kppe0NEpWwfI\n" +
            "Nsk2Hds4rf2IYujE0p7YdS0JI63Om9CcIX/Ie5/UCLiYLgUn5/XzCLR62Z5ls9Tx\n" +
            "GVE9yhZUlpgko33L7QIDAQAB";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 获取密钥对
     *
     * @return 密钥对
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        byte[] decodedKey = Base64.decode(new String(privateKey.getBytes()));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     *
     * @param data      待加密数据
     * @param publicKey 公钥
     * @return
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encode((encryptedData)));
    }

    /**
     * RSA解密
     *
     * @param data       待解密数据
     * @param privateKey 私钥
     * @return
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.decode(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 签名
     *
     * @param data       待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(data.getBytes());
        return Base64.encode(signature.sign());
    }

    /**
     * 验签
     *
     * @param srcData   原始字符串
     * @param publicKey 公钥
     * @param sign      签名
     * @return 是否验签通过
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decode(sign));
    }


    public static void main(String[] args) {
        try {
            // 生成密钥对
//            KeyPair keyPair = getKeyPair();
//            String privateKey = new String(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()));
//            String publicKey = new String(Base64.getEncoder().encode(keyPair.getPublic().getEncoded()));
//            System.out.println("私钥:" + privateKey);
//            System.out.println("公钥:" + publicKey);


            // RSA加密
            String data = "待加密的文字内容";
            String encryptData = encrypt(data, getPublicKey(publicKey));
            System.out.println("加密后内容:" + encryptData);
            // RSA解密
            String decryptData = decrypt(encryptData, getPrivateKey(privateKey));
            System.out.println("解密后内容:" + decryptData);

            // RSA签名
            String sign = sign(data, getPrivateKey(privateKey));
            // RSA验签
            boolean result = verify(data, getPublicKey(publicKey), sign);
            System.out.print("验签结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("加解密异常");
        }
    }

}
