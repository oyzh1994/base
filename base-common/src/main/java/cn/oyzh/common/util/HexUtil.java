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

    public static String bytesToHex(byte[] bytes) {
        return bytesToHex(bytes, true);
    }

    public static String bytesToHex(byte[] bytes, boolean toUpperCase) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        String hexStr = hexString.toString();
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
}
