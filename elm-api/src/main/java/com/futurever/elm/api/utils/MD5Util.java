package com.futurever.elm.api.utils;

import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by wxcsdb88 on 2017/5/23 18:56.
 */
public class MD5Util {
    private static final String HEX_NUMS_STR = "0123456789ABCDEF";
    private static final Integer DEFAULT_SALT_LENGTH = 16;
    private static final Integer DEFAULT_MD5_LENGTH = 32;

    /**
     * 安全生成指定位数盐值
     *
     * @param saltLength 盐值长度
     * @return byte[]
     */
    private static byte[] generateSalt(Integer saltLength) {
        if (saltLength == null || saltLength <= DEFAULT_SALT_LENGTH) {
            saltLength = DEFAULT_SALT_LENGTH;
        }
        if (saltLength >= DEFAULT_MD5_LENGTH) {
            saltLength = DEFAULT_MD5_LENGTH;
        }
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[saltLength];
        random.nextBytes(bytes);
        return bytes;
    }

    public static String fillMD5(String md5) {
        return md5.length() == 32 ? md5 : fillMD5("0" + md5);
    }

    public static String MD5(String input) {
        MessageDigest md5 = null;
        if (input == null) {
            input = "";
        }
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(input.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String md5Val = new BigInteger(1, md5.digest()).toString(16);
            return fillMD5(md5Val);
            //BigInteger会把0省略掉，需补全至32位
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        char[] charArray = input.toCharArray();
//        byte[] byteArray = new byte[charArray.length];
//        for (int i = 0; i < charArray.length; i++)
//            byteArray[i] = (byte) charArray[i];
//        byte[] md5Bytes = md5.digest(byteArray);
//        return byteToHexString(md5Bytes);
    }

    /**
     * 生成加盐MD5 32+16 位
     *
     * @param password 待加密密码
     * @return String
     */
    public static String generate(String password) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(16);
        String salt = byteToHexString(generateSalt(DEFAULT_SALT_LENGTH));
        sb.append(salt);
        int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        } else {
            salt = sb.substring(0, 16);
        }
//        System.out.println(salt);
        password = md5Hex(password + salt);
        if (password == null) {
            return null;
        }
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

    /**
     * 校验加盐后是否和原文一致
     *
     * @param password 密码
     * @param md5      字符串
     * @return boolean
     */
    public static boolean verify(String password, String md5) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        String salt = new String(cs2);
        String md5HexVal = md5Hex(password + salt);
        return md5HexVal != null && md5HexVal.equals(new String(cs1));
    }

    /**
     * 获取十六进制字符串形式的MD5摘要
     */
    private static String md5Hex(String src) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(src.getBytes());
            return new String(new Hex().encode(bs));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将16进制字符串转换成字节数组
     *
     * @param hex 16进制字符串
     * @return byte[]
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4
                    | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));
        }
        return result;
    }

    /**
     * 将指定byte数组转换成16进制字符串
     *
     * @param b 字节数组
     * @return String
     */
    public static String byteToHexString(byte[] b) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

}
