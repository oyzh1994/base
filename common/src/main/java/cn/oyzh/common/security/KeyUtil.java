package cn.oyzh.common.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author oyzh
 * @since 2025-04-03
 */
public class KeyUtil {

    /**
     * 获取rsa密钥长度
     *
     * @param publicKey 公钥
     * @return 结果
     */
    public static int getRSAKeyLength(String publicKey) {
        try {
            if (publicKey.contains(" ")) {
                publicKey = publicKey.substring(publicKey.indexOf(" ") + 1);
            }
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
            KeyFactory rsaKeyFactory = KeyFactory.getInstance("RSA");
            PublicKey rsaPublicKey = rsaKeyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            RSAPublicKey rsaPublicKeyIntf = (RSAPublicKey) rsaPublicKey;
            return rsaPublicKeyIntf.getModulus().bitLength();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return -1;
    }

//
//    public static String[] toSSHKey(KeyPair keyPair) {
//
//        // 获取公钥
//        PublicKey publicKey = keyPair.getPublic();
//        // 获取私钥
//        PrivateKey privateKey = keyPair.getPrivate();
//        byte[] publicKeyBytes = publicKey.getEncoded();
//        byte[] privateKeyBytes = privateKey.getEncoded();
//        String publicKeyBase64 = Base64.getMimeEncoder().encodeToString(publicKeyBytes);
//        StringBuilder sb = new StringBuilder();
//        sb.append("-----BEGIN PRIVATE KEY-----").append("\n");
//        String privateKeyBase64 = Base64.getMimeEncoder().encodeToString(privateKeyBytes);
//        sb.append(privateKeyBase64).append("\n");
//        sb.append("-----END PRIVATE KEY-----");
//        return new String[]{publicKeyBase64, sb.toString()};
//    }

}
