package com.gmail.romkatsis;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingTool {

    //Calculate the password hash and get its hex representation in upper case
    public static String getStringHex(String string, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] hash = digest.digest(string.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02X", b));
        }

        return hexString.toString();
    }
}
