package cn.oyzh.common.util;

/**
 * hex工具类
 *
 * @author oyzh
 * @since 2024-09-29
 */
public class HexUtil {

    private HexUtil() {
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String bytesToHex(byte[] bytes) {
        return bytesToHex(bytes, true);
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param bytes       字节数组
     * @param toUpperCase 大写形式
     * @return 十六进制字符串
     */
    public static String bytesToHex(byte[] bytes, boolean toUpperCase) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        String hexStr = sb.toString();
        if (toUpperCase) {
            return hexStr.toUpperCase();
        }
        return hexStr;
    }

    /**
     * 编码为hex字符串
     *
     * @param bytes       数据
     * @param toLowerCase 是否转为小写
     * @return 结果
     */
    public static String encodeHexStr(byte[] bytes, boolean toLowerCase) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        if (toLowerCase) {
            return bytesToHex(bytes, false);
        }
        return bytesToHex(bytes, true);
    }

    /**
     * 将十六进制转换为字符串字节数组
     *
     * @param s 十六进制字符串
     * @return 字节数组
     */
    public static byte[] decodeHexStr(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

}
