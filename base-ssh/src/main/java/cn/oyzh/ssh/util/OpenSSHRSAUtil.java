package cn.oyzh.ssh.util;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.io.pem.PemObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;


/**
 * openssh格式，rsa密钥生成器
 *
 * @author oyzh
 * @since 2025/04/04
 */
@Deprecated
public class OpenSSHRSAUtil {

    public static void main(String[] args) throws Exception {
        // 生成RSA密钥对（2048位）
        KeyPair keyPair = generateRSAKeyPair(2048);

        // 生成OpenSSH格式公钥
        String publicKey = generateOpenSSHPublicKey(keyPair.getPublic());
        System.out.println("Public Key (OpenSSH):");
        System.out.println(publicKey);

        // 生成PEM格式的私钥（PKCS#1）
        String privateKey = generatePEMPrivateKey(keyPair.getPrivate());
        System.out.println("\nPrivate Key (PEM PKCS#1):");
        System.out.println(privateKey);
    }

    /**
     * 生成密钥
     *
     * @return 结果
     */
    public static String[] generateKey(int keySize) throws Exception {
        try {
            // 生成Ed25519密钥对
            KeyPair keyPair = generateRSAKeyPair(keySize);
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

    /**
     * 获取键长度
     *
     * @param publicKey 公钥
     * @return 长度
     */
    public static int getKeyLength(String publicKey) {
        try {
            String[] arr = publicKey.split(" ");
            byte[] publicKeyBytes;
            if (arr.length == 1) {
                publicKeyBytes = Base64.getDecoder().decode(publicKey);
            } else {
                publicKeyBytes = Base64.getDecoder().decode(arr[1]);
            }
            // 解析SSH编码结构
            try (ByteArrayInputStream bis = new ByteArrayInputStream(publicKeyBytes);
                 DataInputStream dis = new DataInputStream(bis)) {

                // 读取算法类型
                int typeLen = dis.readInt();
                byte[] typeBytes = new byte[typeLen];
                dis.readFully(typeBytes);

                // 读取指数e
                int eLen = dis.readInt();
                byte[] eBytes = new byte[eLen];
                dis.readFully(eBytes);

                // 读取模数n
                int nLen = dis.readInt();
                byte[] nBytes = new byte[nLen];
                dis.readFully(nBytes);

                // 转换为BigInteger计算位数
                BigInteger n = new BigInteger(1, nBytes);
                return n.bitLength();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * 生成rsa密钥对
     *
     * @param keySize 键长度
     * @return rsa密钥对
     * @throws NoSuchAlgorithmException 异常
     */
    private static KeyPair generateRSAKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 生成openssh格式的公钥
     *
     * @param publicKey 公钥
     * @return openssh的公钥
     */
    private static String generateOpenSSHPublicKey(PublicKey publicKey) {
        if (!(publicKey instanceof RSAPublicKey rsaPublicKey)) {
            throw new IllegalArgumentException("Not an RSA public key");
        }
        // OpenSSH公钥格式：ssh-rsa + Base64编码的 [类型长度][类型][e长度][e][n长度][n]
        byte[] eBytes = rsaPublicKey.getPublicExponent().toByteArray();
        byte[] nBytes = rsaPublicKey.getModulus().toByteArray();
        try {
            ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(byteOs);
            // 写入算法类型 "ssh-rsa"
            dos.writeInt("ssh-rsa".getBytes().length);
            dos.write("ssh-rsa".getBytes());
            // 写入e
            dos.writeInt(eBytes.length);
            dos.write(eBytes);
            // 写入n
            dos.writeInt(nBytes.length);
            dos.write(nBytes);
            String encoded = Base64.getEncoder().encodeToString(byteOs.toByteArray());
            return "ssh-rsa " + encoded + " generated-by-java";
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 生成pem格式的私钥
     *
     * @param privateKey 私钥
     * @return pem格式的私钥
     * @throws IOException 异常
     */
    private static String generatePEMPrivateKey(PrivateKey privateKey) throws Exception {
        // 转换私钥到PKCS#1格式
        PrivateKeyInfo pkInfo = PrivateKeyInfo.getInstance(privateKey.getEncoded());
        PemObject pemObject = new PemObject("RSA PRIVATE KEY", pkInfo.parsePrivateKey().toASN1Primitive().getEncoded());
        // 写入PEM格式
        StringWriter sw = new StringWriter();
        try (JcaPEMWriter pw = new JcaPEMWriter(sw)) {
            pw.writeObject(pemObject);
        }
        return sw.toString();
    }
}
