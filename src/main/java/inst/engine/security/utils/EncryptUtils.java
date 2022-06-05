package inst.engine.security.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtils {

    /**
     * AES CBC 加密
     *
     * @param message 需要加密的字符串
     * @param key     密匙
     * @param iv      IV，需要和key长度相同
     * @return 返回加密后密文，编码为base64
     */
    public static String encryptCBC(String message, String key, String iv) {
        final String cipherMode = "AES/CBC/PKCS5Padding";
        final String charsetName = "UTF-8";
        try {
            byte[] content = new byte[0];
            content = message.getBytes(charsetName);
            //
            byte[] keyByte = key.getBytes(charsetName);
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
            //
            byte[] ivByte = iv.getBytes(charsetName);
            IvParameterSpec ivSpec = new IvParameterSpec(ivByte);

            Cipher cipher = Cipher.getInstance(cipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] data = cipher.doFinal(content);
            final Base64.Encoder encoder = Base64.getEncoder();
            final String result = encoder.encodeToString(data);
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * AES CBC 解密
     *
     * @param messageBase64 密文，base64编码
     * @param key           密匙，和加密时相同
     * @param iv            IV，需要和key长度相同
     * @return 解密后数据
     */
    public static String decryptCBC(String messageBase64, String key, String iv) {
        final String cipherMode = "AES/CBC/PKCS5Padding";
        final String charsetName = "UTF-8";
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            byte[] messageByte = decoder.decode(messageBase64);

            //
            byte[] keyByte = key.getBytes(charsetName);
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");
            //
            byte[] ivByte = iv.getBytes(charsetName);
            IvParameterSpec ivSpec = new IvParameterSpec(ivByte);

            Cipher cipher = Cipher.getInstance(cipherMode);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] content = cipher.doFinal(messageByte);
            String result = new String(content, charsetName);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES ECB 加密
     *
     * @param message 需要加密的字符串
     * @param key     密匙
     * @return 返回加密后密文，编码为base64
     */
    public static String encryptECB(String message, String key) {
        final String cipherMode = "AES/ECB/PKCS5Padding";
        final String charsetName = "UTF-8";
        try {
            byte[] content = new byte[0];
            content = message.getBytes(charsetName);
            //
            byte[] keyByte = key.getBytes(charsetName);
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");

            Cipher cipher = Cipher.getInstance(cipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] data = cipher.doFinal(content);
            final Base64.Encoder encoder = Base64.getEncoder();
            final String result = encoder.encodeToString(data);
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * AES ECB 解密
     *
     * @param messageBase64 密文，base64编码
     * @param key           密匙，和加密时相同
     * @return 解密后数据
     */
    public static String decryptECB(String messageBase64, String key) {
        final String cipherMode = "AES/ECB/PKCS5Padding";
        final String charsetName = "UTF-8";
        try {
            final Base64.Decoder decoder = Base64.getDecoder();
            byte[] messageByte = decoder.decode(messageBase64);

            //
            byte[] keyByte = key.getBytes(charsetName);
            SecretKeySpec keySpec = new SecretKeySpec(keyByte, "AES");

            Cipher cipher = Cipher.getInstance(cipherMode);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] content = cipher.doFinal(messageByte);
            String result = new String(content, charsetName);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String customAlgorithem(String messageBase64, String key) {
        System.out.println(messageBase64);
        System.out.println(key);
        return "customic algorithm";
    }
}