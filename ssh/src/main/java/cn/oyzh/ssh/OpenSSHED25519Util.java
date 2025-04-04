package cn.oyzh.ssh;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * openssh格式，ed25519密钥生成器
 *
 * @author oyzh
 * @since 2025/04/04
 */
public class OpenSSHED25519Util {

    public static void main(String[] args) throws Exception {
        // 生成Ed25519密钥对
        KeyPair keyPair = generateEd25519KeyPair();

        // 生成OpenSSH格式公钥
        String publicKey = generateOpenSSHPublicKey(keyPair.getPublic());
        System.out.println("Public Key (OpenSSH):");
        System.out.println(publicKey);

        // 生成PEM格式私钥
        String privateKey = generatePEMPrivateKey(keyPair.getPrivate());
        System.out.println("\nPrivate Key (PEM):");
        System.out.println(privateKey);
    }

    /**
     * 生成密钥
     *
     * @return 结果
     */
    public static String[] generateKey() {
        try {
            // 生成Ed25519密钥对
            KeyPair keyPair = generateEd25519KeyPair();
            // 生成OpenSSH格式公钥
            String publicKey = generateOpenSSHPublicKey(keyPair.getPublic());
            // 生成PEM格式私钥
            String privateKey = generatePEMPrivateKey(keyPair.getPrivate());
            return new String[]{publicKey, privateKey};
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new String[]{"", ""};
    }

    private static KeyPair generateEd25519KeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("Ed25519");
        return kpg.generateKeyPair();
    }

    private static String generateOpenSSHPublicKey(PublicKey publicKey) {
        if (!"EdDSA".equals(publicKey.getAlgorithm())) {
            throw new IllegalArgumentException("Not an Ed25519 public key");
        }
        // OpenSSH公钥格式结构：
        // ssh-ed25519 + Base64([类型长度][类型][密钥长度][密钥])
        byte[] pubBytes = publicKey.getEncoded();
        byte[] keyBytes = new byte[pubBytes.length - 12];
        System.arraycopy(pubBytes, 12, keyBytes, 0, keyBytes.length);
        try {
            ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(byteOs);
            // 写入算法标识符
            dos.writeInt("ssh-ed25519".length());
            dos.write("ssh-ed25519".getBytes());
            // 写入密钥数据
            dos.writeInt(keyBytes.length);
            dos.write(keyBytes);
            String encoded = Base64.getEncoder().encodeToString(byteOs.toByteArray());
            return "ssh-ed25519 " + encoded + " generated-by-java";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String generatePEMPrivateKey(PrivateKey privateKey) throws IOException {
        // 构造PKCS#8格式的PEM文件
        PemObject pemObject = new PemObject("PRIVATE KEY", privateKey.getEncoded());
        StringWriter sw = new StringWriter();
        try (PemWriter pw = new PemWriter(sw)) {
            pw.writeObject(pemObject);
        }
        return sw.toString();
    }
}
