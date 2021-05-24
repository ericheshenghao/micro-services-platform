package cn.central.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Base64;

public class AESUtil {

    private static final String UTF8 = "UTF-8";
    private static final String AES = "AES";
    private static final String AES_CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    private static final String AES_CBC_NO_PADDING = "AES/CBC/NoPadding";

    /**
     * JDK只支持AES-128加密，也就是密钥长度必须是128bit；
     * 参数为密钥key，key的长度小于16字符时用"0"补充，
     * key长度大于16字符时截取前16位。
     * <p>
     * 要实现256的需要依赖其他Jar包
     **/
    private static SecretKeySpec create128BitsKey(String key) {
        if (key == null) {
            key = "";
        }
        byte[] data = null;
        StringBuffer buffer = new StringBuffer(16);
        buffer.append(key);
        //小于16后面补0
        while (buffer.length() < 16) {
            buffer.append("0");
        }
        //大于16，截取前16个字符
        if (buffer.length() > 16) {
            buffer.setLength(16);
        }
        try {
            data = buffer.toString().getBytes(UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(data, AES);
    }

    /**
     * 创建128位的偏移量，iv的长度小于16时后面补0，
     * 大于16，截取前16个字符;
     *
     * @param iv
     * @return
     */
    private static IvParameterSpec create128BitsIV(String iv) {
        if (iv == null) {
            iv = "";
        }
        byte[] data = null;
        StringBuffer buffer = new StringBuffer(16);
        buffer.append(iv);
        while (buffer.length() < 16) {
            buffer.append("0");
        }
        if (buffer.length() > 16) {
            buffer.setLength(16);
        }
        try {
            data = buffer.toString().getBytes(UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new IvParameterSpec(data);
    }

    /**
     * 填充方式为Pkcs5Padding时，最后一个块需要填充χ个字节，填充的值就是χ，也就是填充内容由JDK确定
     *
     * @param srcContent
     * @param password
     * @param iv
     * @return
     */
    public static String aesCbcPkcs5PaddingEncrypt(String srcContent, String password, String iv) throws Exception {
        SecretKeySpec key = create128BitsKey(password);
        IvParameterSpec ivParameterSpec = create128BitsIV(iv);
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] data = srcContent.getBytes(Charset.forName("UTF8"));
        byte[] encryptedContent = cipher.doFinal(data);
        //为了打印出来的字符串没有乱码，进行base64编码
        return Base64.getEncoder().encodeToString(encryptedContent);

    }


    public static String aesCbcPkcs5PaddingDecrypt(String encryptedContent, String password, String iv) throws Exception {
        SecretKeySpec key = create128BitsKey(password);
        IvParameterSpec ivParameterSpec = create128BitsIV(iv);
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] data = Base64.getDecoder().decode(encryptedContent);
        byte[] decryptedContent = cipher.doFinal(data);
        return new String(decryptedContent,"UTF8");

    }

    /**
     * 填充方式为NoPadding时，最后一个块的填充内容由程序员确定，通常为0.
     * AES/CBC/NoPadding加密的明文长度必须是16的整数倍，明文长度不满足16时，程序员要扩充到16的整数倍
     *
     * @param sSrc
     * @param aesKey
     * @param aesIV
     * @return
     */
    public static byte[] aesCbcNoPaddingEncrypt(byte[] sSrc, String aesKey, String aesIV) throws Exception {
        //加密的数据长度不是16的整数倍时，原始数据后面补0，直到长度满足16的整数倍
        int len = sSrc.length;
        //计算补0后的长度
        while (len % 16 != 0) {
            len++;
        }
        byte[] result = new byte[len];
        //在最后补0
        for (int i = 0; i < len; ++i) {
            if (i < sSrc.length) {
                result[i] = sSrc[i];
            } else {
                //填充字符'a'
                //result[i] = 'a';
                result[i] = 0;
            }
        }
        SecretKeySpec skeySpec = create128BitsKey(aesKey);
        //使用CBC模式，需要一个初始向量iv，可增加加密算法的强度
        IvParameterSpec iv = create128BitsIV(aesIV);
        Cipher cipher = Cipher.getInstance(AES_CBC_NO_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = null;
        encrypted = cipher.doFinal(result);
        return encrypted;
    }

    public static byte[] aesCbcNoPaddingDecrypt(byte[] sSrc, String aesKey, String aesIV) throws Exception {
        SecretKeySpec skeySpec = create128BitsKey(aesKey);
        IvParameterSpec iv = create128BitsIV(aesIV);
        Cipher cipher = Cipher.getInstance(AES_CBC_NO_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] decryptContent = cipher.doFinal(sSrc);
        return decryptContent;
    }

    public static void main(String[] args) throws Exception {
        String source = "amigoxie";
        System.out.println("原文: " + source);
        String aeskey = "1234567890987654";
        String aesIV = "1234567890987654";
        String encryptData1 = aesCbcPkcs5PaddingEncrypt(source, aeskey, aesIV);
        System.out.println("加密后:" + encryptData1);
        String dencryptData1 = aesCbcPkcs5PaddingDecrypt(encryptData1, aeskey, aesIV);
        System.out.println("解密后:" + new String(dencryptData1));
    }
}