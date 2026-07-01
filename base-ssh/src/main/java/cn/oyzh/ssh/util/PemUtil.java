package cn.oyzh.ssh.util;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;

import java.io.FileReader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * pem工具类
 *
 * @author oyzh
 * @since 2023/12/15
 */
public class PemUtil {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 从 PEM 文件中同时解析证书和私钥
     *
     * @param pemFile            包含证书和私钥的 PEM 文件（证书在前，私钥在后，可多个证书）
     * @param privateKeyPassword 私钥密码，null 表示无密码
     * @return 解析结果，包含证书列表和私钥
     */
    public static PemKeyCertData loadKeyAndCertificates(String pemFile, String privateKeyPassword) throws Exception {
        List<X509Certificate> certificates = new ArrayList<>();
        PrivateKey privateKey = null;

        try (PEMParser parser = new PEMParser(new FileReader(pemFile))) {
            Object obj;
            JcaPEMKeyConverter keyConverter = new JcaPEMKeyConverter().setProvider("BC");
            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter().setProvider("BC");

            while ((obj = parser.readObject()) != null) {
                if (obj instanceof X509CertificateHolder) {
                    // 证书对象
                    certificates.add(certConverter.getCertificate((X509CertificateHolder) obj));
                } else if (obj instanceof PEMKeyPair) {
                    // 未加密私钥对
                    privateKey = keyConverter.getKeyPair((PEMKeyPair) obj).getPrivate();
                } else if (obj instanceof PrivateKeyInfo) {
                    // 未加密私钥信息
                    privateKey = keyConverter.getPrivateKey((PrivateKeyInfo) obj);
                } else if (obj instanceof PEMEncryptedKeyPair) {
                    // 加密私钥
                    if (privateKeyPassword == null) {
                        throw new IllegalArgumentException("Private key is encrypted but no password provided");
                    }
                    PEMDecryptorProvider decryptor = new JcePEMDecryptorProviderBuilder()
                            .build(privateKeyPassword.toCharArray());
                    KeyPair kp = keyConverter.getKeyPair(((PEMEncryptedKeyPair) obj).decryptKeyPair(decryptor));
                    privateKey = kp.getPrivate();
                }
            }
        }

        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("No certificate found in PEM file: " + pemFile);
        }
        if (privateKey == null) {
            throw new IllegalArgumentException("No private key found in PEM file: " + pemFile);
        }

        return new PemKeyCertData(privateKey, certificates);
    }

    /**
     * 仅加载 PEM 文件中的证书（不要求私钥）
     */
    public static X509Certificate[] loadCertificates(String pemFile) throws Exception {
        List<X509Certificate> certs = new ArrayList<>();
        try (PEMParser parser = new PEMParser(new FileReader(pemFile))) {
            Object obj;
            JcaX509CertificateConverter converter = new JcaX509CertificateConverter().setProvider("BC");
            while ((obj = parser.readObject()) != null) {
                if (obj instanceof X509CertificateHolder) {
                    certs.add(converter.getCertificate((X509CertificateHolder) obj));
                }
            }
        }
        return certs.toArray(new X509Certificate[0]);
    }

    /**
     * 辅助数据类
     */
    public static class PemKeyCertData {

        private final PrivateKey privateKey;

        private final List<X509Certificate> certificates;

        public PemKeyCertData(PrivateKey privateKey, List<X509Certificate> certificates) {
            this.privateKey = privateKey;
            this.certificates = certificates;
        }

        public PrivateKey getPrivateKey() {
            return privateKey;
        }

        public List<X509Certificate> getCertificates() {
            return certificates;
        }
    }
}