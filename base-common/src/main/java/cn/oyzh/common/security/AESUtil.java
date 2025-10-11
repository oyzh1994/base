package cn.oyzh.common.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class AESUtil {

    // 算法模式：AES-128-CBC（固定128位密钥，CBC模式带IV，PKCS5填充）
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int KEY_LENGTH = 16; // 128位密钥（16字节）
    private static final int IV_LENGTH = 16;  // IV固定16字节（CBC模式要求）

    /**
     * AES加密（入参为字符串）
     * @param plaintext 明文（待加密的字符串）
     * @param key 密钥（必须16字节，若不足会自动填充，过长会截断）
     * @return 加密后的字符串（格式：Base64(IV)+":"+Base64(密文)）
     */
    public static String encrypt(String plaintext, String key) throws Exception {
        // 1. 处理密钥（确保16字节）
        byte[] keyBytes = processKey(key);

        // 2. 生成随机IV（16字节）
        byte[] iv = new byte[IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        // 3. 初始化加密器
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        // 4. 加密并拼接IV和密文（用Base64编码，冒号分隔）
        byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        String ivBase64 = Base64.getEncoder().encodeToString(iv);
        String encryptedBase64 = Base64.getEncoder().encodeToString(encrypted);
        return ivBase64 + ":" + encryptedBase64;
    }

    /**
     * AES解密（入参为加密后的字符串）
     * @param ciphertext 加密后的字符串（格式：Base64(IV)+":"+Base64(密文)）
     * @param key 密钥（必须与加密时一致）
     * @return 解密后的明文
     */
    public static String decrypt(String ciphertext, String key) throws Exception {
        // 1. 拆分IV和密文
        String[] parts = ciphertext.split(":", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("加密字符串格式错误");
        }
        byte[] iv = Base64.getDecoder().decode(parts[0]);
        byte[] encrypted = Base64.getDecoder().decode(parts[1]);

        // 2. 处理密钥（与加密时保持一致）
        byte[] keyBytes = processKey(key);

        // 3. 初始化解密器
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        // 4. 解密并返回明文
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * 处理密钥：确保长度为16字节（不足补0，过长截断）
     */
    private static byte[] processKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] processed = new byte[KEY_LENGTH];
        System.arraycopy(keyBytes, 0, processed, 0, Math.min(keyBytes.length, KEY_LENGTH));
        return processed;
    }

    // 测试示例
    public static void main(String[] args) throws Exception {
        String plaintext = "这是一段需要加密的字符串";
        String key = "mySecretKey123"; // 密钥（任意字符串，内部会处理为16字节）

        // 加密
        String encrypted = encrypt(plaintext, key);
        System.out.println("加密后：" + encrypted);

        // 解密
        String decrypted = decrypt(encrypted, key);
        System.out.println("解密后：" + decrypted);
    }
}