package com.periodicals.utils.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5_Cryptographer implements Cryptographer {
    private static final String MD5_NAME = "MD5";

    @Override
    public String encrypt(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(MD5_NAME);
            byte[] bytes = md5.digest(str.getBytes());
            StringBuilder builder = new StringBuilder();
            for (byte b : bytes) {
                builder.append(String.format("%02X", b));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
