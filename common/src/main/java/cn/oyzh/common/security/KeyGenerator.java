package cn.oyzh.common.security;

import org.apache.commons.codec.binary.Hex;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

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
    public static String[] rsa(int length) {
        return generator("RSA", length);
    }

    /**
     * ed25519算法
     *
     * @return 密钥
     */
    public static String[] ed25519() {
        return generator("ED25519", null);
    }

    /**
     * 生成密钥
     *
     * @param type   类型
     * @param length 长度
     * @return [0]公钥 [1]密钥
     */
    private static String[] generator(String type, Integer length) {
        try {
            // 初始化密钥对生成器，指定使用 RSA 算法
            KeyPairGenerator generator = KeyPairGenerator.getInstance(type);
            // 指定密钥长度
            if (length != null) {
                generator.initialize(length);
            }
            // 生成密钥对
            KeyPair keyPair = generator.generateKeyPair();
            // 获取公钥
            PublicKey publicKey = keyPair.getPublic();
            // 获取私钥
            PrivateKey privateKey = keyPair.getPrivate();
            byte[] publicKeyBytes = publicKey.getEncoded();
            byte[] privateKeyBytes = privateKey.getEncoded();
            String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKeyBytes);
            String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKeyBytes);
            return new String[]{publicKeyBase64, privateKeyBase64};
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
