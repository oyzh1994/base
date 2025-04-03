package cn.oyzh.common.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * @author oyzh
 * @since 2025-04-03
 */
public class KeyGenerator {

    /**
     * rsa算法
     *
     * @param length 长度
     * @return 密钥
     */
    public static KeyPair rsa(int length) {
        return generator("RSA", length);
    }

    /**
     * ed25519算法
     *
     * @return 密钥
     */
    public static KeyPair ed25519() {
        return generator("ED25519", null);
    }

    /**
     * 生成密钥
     *
     * @param type   类型
     * @param length 长度
     * @return [0]公钥 [1]密钥
     */
    private static KeyPair generator(String type, Integer length) {
        try {
            // 初始化密钥对生成器，指定使用 RSA 算法
            KeyPairGenerator generator = KeyPairGenerator.getInstance(type);
            // 指定密钥长度
            if (length != null) {
                generator.initialize(length);
            }
            // 生成密钥对
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
