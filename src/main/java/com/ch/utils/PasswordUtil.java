package com.ch.utils;

import java.security.MessageDigest;
import java.util.Random;

public class PasswordUtil {

    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /*
     * 创建盐值
     *
     * @param length 长度
     */
    public static String createSalt(int length){

        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<length; i++){
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }

        return sb.toString();
    }

    /*
     * 加密
     *
     * @param password 要加密的密码
     * @param salt 盐值
     */
    public static String encryption(String password, String salt) {

        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 加密后的字符串
            result = byteArrayToHexString(md.digest(mergePasswordAndSalt(password, salt)
                    .getBytes("utf-8")));
        } catch (Exception ex) {

        }

        return result;
    }

    private static String mergePasswordAndSalt(String password, String salt) {

        if (password == null) {
            password = "";
        }

        if ((salt == null) || "".equals(salt)) {
            return password;
        } else {
            return password + "{" + salt.toString() + "}";
        }
    }

    /*
     * 转换字节数组为16进制字串
     *
     * @param b 字节数组
     * @return 16进制字串
     */
    private static String byteArrayToHexString(byte[] b) {

        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {

        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;

        return hexDigits[d1] + hexDigits[d2];
    }

    /*
     * 验证密码
     * @param salt 盐值
     * @param encPassword   加密的密码
     * @param enterPassword 用户输入的密码
     * @return
     */
    public static boolean checkPassword(String salt, String encPassword, String enterPassword){

        boolean passwordValid = encPassword.equals(encryption(enterPassword, salt));

        return passwordValid;
    }
}
