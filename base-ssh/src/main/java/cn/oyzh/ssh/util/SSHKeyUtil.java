package cn.oyzh.ssh.util;

import cn.oyzh.common.util.IOUtil;
import cn.oyzh.common.util.StringUtil;
import org.apache.sshd.common.config.keys.FilePasswordProvider;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.config.keys.writer.openssh.OpenSSHKeyEncryptionContext;
import org.apache.sshd.common.config.keys.writer.openssh.OpenSSHKeyPairResourceWriter;
import org.apache.sshd.common.keyprovider.KeyPairProvider;
import org.apache.sshd.common.util.security.SecurityUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
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
     * @param keySize 长度
     * @param password 密码
     * @return 密钥
     * @throws Exception 异常
     */
    public static String[] generateEd25519(int keySize, String password) throws Exception {
        KeyPair keyPair = KeyUtils.generateKeyPair(KeyPairProvider.SSH_ED25519, keySize);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OpenSSHKeyPairResourceWriter.INSTANCE.writePublicKey(keyPair.getPublic(), null, baos);
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        OpenSSHKeyEncryptionContext context = null;
        if (StringUtil.isNotBlank(password)) {
            context = new OpenSSHKeyEncryptionContext();
            context.setCipherType("256");
            context.setPassword(password);
        }
        OpenSSHKeyPairResourceWriter.INSTANCE.writePrivateKey(keyPair, null, context, baos1);
        String publicKey = baos.toString();
        String privateKey = baos1.toString();
        baos.close();
        baos1.close();
        return new String[]{publicKey, privateKey};
    }

    /***
     * 生成rsa的密钥
     * @param keySize 长度
     * @param password 密码
     * @return 密钥
     * @throws Exception 异常
     */
    public static String[] generateRsa(int keySize, String password) throws Exception {
        KeyPair keyPair = KeyUtils.generateKeyPair(KeyPairProvider.SSH_RSA, keySize);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OpenSSHKeyPairResourceWriter.INSTANCE.writePublicKey(keyPair.getPublic(), null, baos);
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        OpenSSHKeyEncryptionContext context = null;
        if (StringUtil.isNotBlank(password)) {
            context = new OpenSSHKeyEncryptionContext();
            context.setCipherType("256");
            context.setPassword(password);
        }
        OpenSSHKeyPairResourceWriter.INSTANCE.writePrivateKey(keyPair, null, context, baos1);
        String publicKey = baos.toString();
        String privateKey = baos1.toString();
        baos.close();
        baos1.close();
        return new String[]{publicKey, privateKey};
    }

    /***
     * 生成dsa的密钥
     * @param keySize 长度
     * @param password 密码
     * @return 密钥
     * @throws Exception 异常
     */
    public static String[] generateDsa(int keySize, String password) throws Exception {
        KeyPair keyPair = KeyUtils.generateKeyPair(KeyPairProvider.SSH_DSS, keySize);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OpenSSHKeyPairResourceWriter.INSTANCE.writePublicKey(keyPair.getPublic(), null, baos);
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        OpenSSHKeyEncryptionContext context = null;
        if (StringUtil.isNotBlank(password)) {
            context = new OpenSSHKeyEncryptionContext();
            context.setCipherType("256");
            context.setPassword(password);
        }
        OpenSSHKeyPairResourceWriter.INSTANCE.writePrivateKey(keyPair, null, context, baos1);
        String publicKey = baos.toString();
        String privateKey = baos1.toString();
        baos.close();
        baos1.close();
        return new String[]{publicKey, privateKey};
    }

    /***
     * 生成ecdsa的密钥
     * @param keySize 长度
     * @param password 密码
     * @return 密钥
     * @throws Exception 异常
     */
    public static String[] generateEcdsa(int keySize, String password) throws Exception {
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
        OpenSSHKeyEncryptionContext context = null;
        if (StringUtil.isNotBlank(password)) {
            context = new OpenSSHKeyEncryptionContext();
            context.setCipherType("256");
            context.setPassword(password);
        }
        OpenSSHKeyPairResourceWriter.INSTANCE.writePrivateKey(keyPair, null, context, baos1);
        String publicKey = baos.toString();
        String privateKey = baos1.toString();
        baos.close();
        baos1.close();
        return new String[]{publicKey, privateKey};
    }

    /**
     * 获取密钥长度
     *
     * @param key      密钥
     * @param password 密码
     * @return 长度
     */
    public static int getKeySize(String key, String password) throws Exception {
        if (StringUtil.isNotEmpty(key)) {
            FilePasswordProvider passwordProvider = null;
            // 证书密码
            if (StringUtil.isNotBlank(password)) {
                passwordProvider = FilePasswordProvider.of(password);
            }
            // 将PEM字符串转换为PrivateKey
            Iterable<KeyPair> pairs = SecurityUtils.loadKeyPairIdentities(
                    null,
                    null,
                    new ByteArrayInputStream(key.getBytes()),
                    passwordProvider
            );
            if (pairs != null && pairs.iterator().hasNext()) {
                return KeyUtils.getKeySize(pairs.iterator().next().getPrivate());
            }
        }
        return -1;
    }

    /**
     * 获取密钥类型
     *
     * @param key      密钥
     * @param password 密码
     * @return 类型
     */
    public static String getKeyType(String key, String password) throws Exception {
        if (StringUtil.isNotEmpty(key)) {
            FilePasswordProvider passwordProvider = null;
            // 证书密码
            if (StringUtil.isNotBlank(password)) {
                passwordProvider = FilePasswordProvider.of(password);
            }
            // 将PEM字符串转换为PrivateKey
            Iterable<KeyPair> pairs = SecurityUtils.loadKeyPairIdentities(
                    null,
                    null,
                    new ByteArrayInputStream(key.getBytes()),
                    passwordProvider
            );
            if (pairs != null && pairs.iterator().hasNext()) {
                return KeyUtils.getKeyType(pairs.iterator().next().getPrivate());
            }
        }
        return null;
    }

    /**
     * 从文本加载证书
     *
     * @param key      密钥
     * @param password 秘密
     * @return 证书
     * @throws Exception 异常
     */
    public static Iterable<KeyPair> loadKeysForStr(String key, String password) throws Exception {
        return loadKeysForBytes(key.getBytes(), password);
    }

    /**
     * 从文本加载证书
     *
     * @param key      密钥
     * @param password 秘密
     * @return 证书
     * @throws Exception 异常
     */
    public static Iterable<KeyPair> loadKeysForBytes(byte[] key, String password) throws Exception {
        FilePasswordProvider passwordProvider = null;
        // 证书密码
        if (StringUtil.isNotBlank(password)) {
            passwordProvider = FilePasswordProvider.of(password);
        }
        return SecurityUtils.loadKeyPairIdentities(
                null,
                null,
                new ByteArrayInputStream(key),
                passwordProvider
        );
    }

    /**
     * 从文件加载证书
     *
     * @param path     路径
     * @param password 秘密
     * @return 证书
     * @throws Exception 异常
     */
    public static Iterable<KeyPair> loadKeysFromFile(String path, String password) throws Exception {
        FilePasswordProvider passwordProvider = null;
        // 证书密码
        if (StringUtil.isNotBlank(password)) {
            passwordProvider = FilePasswordProvider.of(password);
        }
        FileInputStream fis = new FileInputStream(path);
        Iterable<KeyPair> keyPairs = SecurityUtils.loadKeyPairIdentities(
                null,
                null,
                fis,
                passwordProvider
        );
        IOUtil.close(fis);
        return keyPairs;
    }
}
