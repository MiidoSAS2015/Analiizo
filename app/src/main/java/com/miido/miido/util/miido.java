package com.miido.miido.util;

import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Alvaro on 03/03/2015.
 */
public class miido {

    private String key;

    public byte[] encrypt(String value) throws Exception {
        final byte[] bytes = value.getBytes("UTF-8");
        final Cipher aes = getCipher(true);
        final byte[] encrypted = aes.doFinal(bytes);
        return encrypted;
    }

    public String decrypt(byte[] value) throws Exception {
        final Cipher aes = getCipher(false);
        final byte[] bytes = aes.doFinal(value);
        final String decrypted = new String(bytes, "UTF-8");
        return decrypted;
    }

    private Cipher getCipher(boolean paraCifrar) throws Exception {
        final String phrase = "";
        final MessageDigest digest = MessageDigest.getInstance("SHA");
        digest.update(phrase.getBytes("UTF-8"));
        final SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");

        final Cipher aes = Cipher.getInstance(this.key);
        if (paraCifrar) {
            aes.init(Cipher.ENCRYPT_MODE, key);
        } else {
            aes.init(Cipher.DECRYPT_MODE, key);
        }
        return aes;
    }

    public void setKey(String key){
        this.key = key;
    }
}







