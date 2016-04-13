package com.anxin.changbaishan.utils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by Txw on 2016/4/7.
 */
public class RSAUtil {

    private static final String KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7kinnUfqV6z0dqQGJW5Q8CZVX" +
            "MYzcgzyrjUqrkHuF7HTNAQnk7XH9yc7Dy6SjluNRoWEcWnjkuGskC1eYHpol0I5Y" +
            "jDO39LUoI/5Sjcnv2fjVVTvpGXyePx5pcWXOJucIgtffmnXIyaIscGWtl+StEdgg" +
            "ud821amDoKu0mAi8LwIDAQAB";

    private static final String RSA = "RSA";
    private static RSAPublicKey publicKey;

    /**
     * 从字符串中加载公钥
     * @throws Exception
     *             加载公钥时产生的异常
     */
    public static void loadPublicKey() throws Exception
    {
        try {
            byte[] buffer = Base64Util.decode(KEY);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e){
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 用公钥加密 <br>
     * 每次加密的字节数，不能超过密钥的长度值减去11
     *
     * @param data
     *            需加密数据的byte数据
     * @return 加密后的byte型数据
     */
    public static byte[] encryptData(byte[] data)
    {
        try
        {
            Cipher cipher =  Cipher.getInstance("RSA/ECB/PKCS1Padding");

            if (publicKey == null) {
                loadPublicKey();
            }

            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 传入编码数据并返回编码结果
            return cipher.doFinal(data);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
