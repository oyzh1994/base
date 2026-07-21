package cn.oyzh.common.security;

import cn.oyzh.common.util.HexUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 *
 * @author oyzh
 * @since 2026-06-29
 */
public class SHA256Util {

    /**
     * SHA256 哈希，返回十六进制字符串
     *
     * @param msg 消息
     * @return 结果
     * @throws Exception 异常
     */
    public static String sha256Hex(String msg) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] digest = md.digest(msg.getBytes(StandardCharsets.UTF_8));
        return HexUtil.bytesToHex(digest, false);
    }

    /**
     * HMAC-SHA256 加密，返回字节数组
     *
     * @param key 密钥
     * @param msg 消息
     * @return 结果
     * @throws Exception 异常
     */
    public static byte[] hmacSha256(byte[] key, String msg) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA256");
        mac.init(secretKeySpec);
        return mac.doFinal(msg.getBytes(StandardCharsets.UTF_8));
    }
}
