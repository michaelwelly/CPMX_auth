package com.croco.auth.service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class EncryptionUtil {

    private static final String ALGORITHM = "AES";

    // Метод для получения секретного ключа из строки
    private static SecretKey getSecretKeyFromString(String key) {
        byte[] keyBytes = new byte[16]; // AES требует ключ длиной 16 байт (128 бит)
        byte[] tempKey = key.getBytes(StandardCharsets.UTF_8);
        System.arraycopy(tempKey, 0, keyBytes, 0, Math.min(tempKey.length, keyBytes.length));
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    // Метод для шифрования строки
    public static String encrypt(String data, String secretKeyString) throws Exception {
        SecretKey secretKey = getSecretKeyFromString(secretKeyString);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // Метод для дешифрования строки
    public static String decrypt(String encryptedData, String secretKeyString) throws Exception {
        SecretKey secretKey = getSecretKeyFromString(secretKeyString);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedData, StandardCharsets.UTF_8);
    }
}