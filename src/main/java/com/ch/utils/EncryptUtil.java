package com.ch.utils;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;

import java.util.Random;

public class EncryptUtil {

    public static String createRandom(int length) {

        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }

        return sb.toString();
    }


    public static String encryptMD5(String source) {
        if (source == null) {
            source = "";
        }
        Sha256Hash hash = new Sha256Hash(source);

        return new Md5Hash(hash).toString();
    }
}
