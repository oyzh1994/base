package cn.oyzh.common.util;

import java.util.Base64;

/**
 *
 * @author oyzh
 * @since 2026-06-08
 */
public class Base64Util {

    public static byte[] decode(String src) {
        return Base64.getDecoder().decode(src);
    }

    public static byte[] encode(byte[] src) {
        return Base64.getEncoder().encode(src);
    }

    public static String encodeToString(byte[] src) {
        return Base64.getEncoder().encodeToString(src);
    }
}
