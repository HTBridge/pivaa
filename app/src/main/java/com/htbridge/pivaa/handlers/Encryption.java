package com.htbridge.pivaa.handlers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public final class Encryption {

    /**
     * Hashing algorithm
     * @param text
     * @return
     */
    public static String hash(String algorithm, String text) {
        String hashedOutput = "";
        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance(algorithm);
            digest.update(text.getBytes());
            byte[] messageDigest = digest.digest();

            hashedOutput = String.format("%032X", new BigInteger(1, messageDigest));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hashedOutput;
    }

    /**
     * Weak random number generator
     * @return
     */
    public static String rng() {
        Random rnd = new Random();
        int n = rnd.nextInt(100000) + 1;

        return Integer.toString(n);
    }

    /**
     * Encrypt DATA
     * @param value
     * @return
     */
    public static String encryptAES_ECB_PKCS5Padding(String value) {
        try {
            byte[] key = {
                    1, 2, 3, 4, 5, 6, 7, 8, 8, 7, 6, 5, 4, 3, 2, 1
            };
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            Base64 b64 = new Base64();
            System.out.println("encrypted string: " + new String(b64.encodeBase64(encrypted)));

            return new String(b64.encodeBase64(encrypted));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    /**
     * Decrypt DATA
     * @param encryptedBase64
     * @return
     */
    public static String decryptAES_ECB_PKCS5Padding(String encryptedBase64) {
        try {
            byte[] IV = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec iv = new IvParameterSpec(IV);

            byte[] key = {
                    1, 2, 3, 4, 5, 6, 7, 8, 8, 7, 6, 5, 4, 3, 2, 1
            };
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);

            Base64 b64 = new Base64();
            byte[] encryptedBase64Bytes = encryptedBase64.getBytes();
            byte[] original = cipher.doFinal(b64.decodeBase64(encryptedBase64Bytes));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }



    /**
     * Encrypt DATA
     * @param value
     * @return
     */
    public static String encryptAES_CBC_PKCS5Padding(String value) {
        try {
            byte[] IV = {
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
            };
            IvParameterSpec iv = new IvParameterSpec(IV);

            byte[] key = {
                    1, 2, 3, 4, 5, 6, 7, 8, 8, 7, 6, 5, 4, 3, 2, 1
            };
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            Base64 b64 = new Base64();
            System.out.println("encrypted string: " + new String(b64.encodeBase64(encrypted)));

            return new String(b64.encodeBase64(encrypted));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }
}
