// MIT License
// Copyright (c) 2017 SNU_ARTOON TEAM

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashManager {
    public static String md5(String key) {
        MessageDigest digester;
        try {
            digester = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // never happens in our use case
            return null;
        }
        return bytesToString(digester.digest(key.getBytes()));
    }

    /**
     * Change a given byte to string.
     * @param b a given byte
     * @return length 2 string.
     */
    private static String aByteToString(byte b) {
        String converted = Integer.toHexString((int)b & 0xFF);
        if (converted.length() == 2) {
            return converted;
        }
        // return 0X form if converted.length == 1
        return "0" + converted;
    }

    /**
     * Change given bytes into string.
     * @param bytes given bytes to be converted
     * @return converted string form bytes
     */
    private static String bytesToString(byte[] bytes) {
        String converted = "";
        for (byte b : bytes) {
            converted += aByteToString(b);
        }
        return converted;
    }
}
