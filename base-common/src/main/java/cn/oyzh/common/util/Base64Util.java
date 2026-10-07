package cn.oyzh.common.util;

import java.util.Base64;

/**
 * Base64工具类
 *
 * @author oyzh
 * @since 2026-06-08
 */
public class Base64Util {

    /**
     * 解码Base64字符串
     *
     * @param src Base64字符串
     * @return 解码后的字节数组
     */
    public static byte[] decode(String src) {
        return Base64.getDecoder().decode(src);
    }

    /**
     * 编码字节数组为Base64字节数组
     *
     * @param src 源字节数组
     * @return Base64编码后的字节数组
     */
    public static byte[] encode(byte[] src) {
        return Base64.getEncoder().encode(src);
    }

    /**
     * 编码字节数组为Base64字符串
     *
     * @param src 源字节数组
     * @return Base64编码后的字符串
     */
    public static String encodeToString(byte[] src) {
        return Base64.getEncoder().encodeToString(src);
    }
}
