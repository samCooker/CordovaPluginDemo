package com.cookie.aestool;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * 2017-9-18
 *
 * @author Shicx
 * <p>
 * 对称加密算法
 */
public class AesTool {

    //用于加密的密钥
    private static final String PUBLIC_KEY = "2sG_3*";
    private static final String EN_UTF8 = "UTF-8";

    /**
     * AES加密字符串
     *
     * @param content
     * @return
     */
    public static String encStr(String content) {
        if (content == null) {
            return null;
        }
        try {
            byte[] bytes = encrypt(content, PUBLIC_KEY);
            return parseByte2HexStr(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密字符串
     *
     * @param content
     * @return
     */
    public static String decStr(String content, String encoding) {
        if (content == null) {
            return null;
        }
        if (encoding == null) {
            encoding = EN_UTF8;
        }
        try {
            byte[] bytes = decrypt(parseHexStr2Byte(content), PUBLIC_KEY);
            return new String(bytes, encoding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES加密字符串
     *
     * @param content   需要被加密的字符串
     * @param publicKey 加密需要的密码
     * @return 密文
     */
    public static byte[] encrypt(String content, String publicKey) throws Exception {

        KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者

        kgen.init(128, new SecureRandom(publicKey.getBytes()));

        SecretKey secretKey = kgen.generateKey();//生成一个密钥

        byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回 null。

        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥

        Cipher cipher = Cipher.getInstance("AES");// 创建密码器

        byte[] byteContent = content.getBytes("utf-8");

        cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器

        byte[] result = cipher.doFinal(byteContent);// 加密

        return result;

    }

    /**
     * 解密AES加密过的字符串
     *
     * @param content  AES加密过过的内容
     * @param password 加密时的密码
     * @return 明文
     */
    public static byte[] decrypt(byte[] content, String password) throws Exception {

        KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
        kgen.init(128, new SecureRandom(password.getBytes()));
        SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
        byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
        Cipher cipher = Cipher.getInstance("AES");// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
        byte[] result = cipher.doFinal(content);
        return result; // 明文
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        String content = "uid=1&account=admin";
        String password = PUBLIC_KEY;
        System.out.println("加密之前：" + content);
        // 加密
        AesTool aes = new AesTool();
        byte[] encrypt = aes.encrypt(content, password);
        System.out.println("加密后的内容：" + new String(encrypt));

        //如果想要加密内容不显示乱码，可以先将密文转换为16进制
        String hexStrResult = aes.parseByte2HexStr(encrypt);
        System.out.println("16进制的密文：" + hexStrResult);

        //如果的到的是16进制密文，别忘了先转为2进制再解密
        byte[] twoStrResult = aes.parseHexStr2Byte(hexStrResult);

        // 解密
        byte[] decrypt = aes.decrypt(twoStrResult, password);
        System.out.println("解密后的内容：" + new String(decrypt));
    }

    private static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};


    /**
     * 获取8位uuid
     *
     * @return
     */
    public static String getShortUuid() {
        StringBuffer stringBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int strInteger = Integer.parseInt(str, 16);
            stringBuffer.append(chars[strInteger % 0x3E]);
        }

        return stringBuffer.toString();
    }
}
