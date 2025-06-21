package cn.oyzh.ssh.util;

import cn.oyzh.common.util.StringUtil;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.config.keys.writer.openssh.OpenSSHKeyPairResourceWriter;
import org.apache.sshd.common.keyprovider.KeyPairProvider;
import org.apache.sshd.common.util.security.SecurityUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.KeyPair;

/**
 * 密钥工具类
 *
 * @author oyzh
 * @since 2025/06/22
 */
public class SSHKeyUtil {

    /***
     * 生成ed25519的密钥
     * @return 密钥
     * @throws Exception 异常
     */
    public static String[] generateEd25519() throws Exception {
        KeyPair keyPair = KeyUtils.generateKeyPair(KeyPairProvider.SSH_ED25519, 256);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OpenSSHKeyPairResourceWriter.INSTANCE.writePublicKey(keyPair.getPublic(), null, baos);

        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        OpenSSHKeyPairResourceWriter.INSTANCE.writePrivateKey(keyPair, null, null, baos1);
        String publicKey = baos.toString();
        String privateKey = baos1.toString();
        baos.close();
        baos1.close();
        return new String[]{publicKey, privateKey};
    }

    /***
     * 生成rsa的密钥
     * @param keySize 长度
     * @return 密钥
     * @throws Exception 异常
     */
    public static String[] generateRSA(int keySize) throws Exception {
        KeyPair keyPair = KeyUtils.generateKeyPair(KeyPairProvider.SSH_RSA, keySize);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OpenSSHKeyPairResourceWriter.INSTANCE.writePublicKey(keyPair.getPublic(), null, baos);
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        OpenSSHKeyPairResourceWriter.INSTANCE.writePrivateKey(keyPair, null, null, baos1);
        String publicKey = baos.toString();
        String privateKey = baos1.toString();
        baos.close();
        baos1.close();
        return new String[]{publicKey, privateKey};
    }

    /***
     * 生成ecdsa的密钥
     * @param keySize 长度
     * @return 密钥
     * @throws Exception 异常
     */
    public static String[] generateEcdsa(int keySize) throws Exception {
        String keyType = switch (keySize) {
            case 256 -> KeyPairProvider.ECDSA_SHA2_NISTP256;
            case 384 -> KeyPairProvider.ECDSA_SHA2_NISTP384;
            case 521 -> KeyPairProvider.ECDSA_SHA2_NISTP521;
            default -> throw new IllegalStateException("Unexpected value: " + keySize);
        };
        KeyPair keyPair = KeyUtils.generateKeyPair(keyType, keySize);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OpenSSHKeyPairResourceWriter.INSTANCE.writePublicKey(keyPair.getPublic(), null, baos);
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        OpenSSHKeyPairResourceWriter.INSTANCE.writePrivateKey(keyPair, null, null, baos1);
        String publicKey = baos.toString();
        String privateKey = baos1.toString();
        baos.close();
        baos1.close();
        return new String[]{publicKey, privateKey};
    }

    /**
     * 获取密钥长度
     *
     * @param key 密钥
     * @return 长度
     */
    public static int getKeySize(String key) {
        if (StringUtil.isNotEmpty(key)) {
            try {
                // 将PEM字符串转换为PrivateKey
                Iterable<KeyPair> pairs = SecurityUtils.loadKeyPairIdentities(
                        null,
                        null,
                        new ByteArrayInputStream(key.getBytes()),
                        null
                );
                if (pairs != null && pairs.iterator().hasNext()) {
                    return KeyUtils.getKeySize(pairs.iterator().next().getPrivate());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     * 获取密钥类型
     *
     * @param key 密钥
     * @return 类型
     */
    public static String getKeyType(String key) {
        if (StringUtil.isNotEmpty(key)) {
            try {
                // 将PEM字符串转换为PrivateKey
                Iterable<KeyPair> pairs = SecurityUtils.loadKeyPairIdentities(
                        null,
                        null,
                        new ByteArrayInputStream(key.getBytes()),
                        null
                );
                if (pairs != null && pairs.iterator().hasNext()) {
                    return KeyUtils.getKeyType(pairs.iterator().next().getPrivate());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
