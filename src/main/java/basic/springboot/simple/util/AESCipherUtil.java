package basic.springboot.simple.util;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESCipherUtil {
    public static String encrypt(String input) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        AESCipherUtil aesCipherUtil = new AESCipherUtil();
        SecretKey key = aesCipherUtil.generateKey(128);
        IvParameterSpec ivParameterSpec = aesCipherUtil.generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";

        return aesCipherUtil.encryptCipher(algorithm, input, key, ivParameterSpec);
    }

    public static String decrypt(String cipherText) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        AESCipherUtil aesCipherUtil = new AESCipherUtil();
        SecretKey key = aesCipherUtil.generateKey(128);
        IvParameterSpec ivParameterSpec = aesCipherUtil.generateIv();
        String algorithm = "AES/CBC/PKCS5Padding";
        return aesCipherUtil.decryptCipher(algorithm, cipherText, key, ivParameterSpec);
    }

    private SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        /* KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key; */

        String encodedKey = "6ZNx2A9n1W/okNkiZM6t8w==";
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

        return key;
    }

    private IvParameterSpec generateIv() {
        /* byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv); */

        String ivParameterSpecEncoded = "y4hp6AhK1gcOVv/b3mJB0w==";
        byte[] decodedIV = Base64.getDecoder().decode(ivParameterSpecEncoded);
        return new IvParameterSpec(decodedIV);
    }

    private String encryptCipher(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    private String decryptCipher(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }
}
