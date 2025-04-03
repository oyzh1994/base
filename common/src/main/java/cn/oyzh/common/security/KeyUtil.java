package cn.oyzh.common.security;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
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
}
