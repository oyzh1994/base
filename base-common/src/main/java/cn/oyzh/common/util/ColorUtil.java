package cn.oyzh.common.util;

/**
 * 颜色工具类
 *
 * @author oyzh
 * @since 2024-10-21
 */
public class ColorUtil {

    /**
     * 私有构造，禁止实例化
     */
    private ColorUtil() {
    }

    /**
     * 将RGB颜色值转换为16进制颜色字符串
     *
     * @param r 红色分量（0-255）
     * @param g 绿色分量（0-255）
     * @param b 蓝色分量（0-255）
     * @return #RRGGBB格式的16进制颜色字符串
     */
    public static String rgbToHex(int r, int g, int b) {
        // 直接使用传入的0-255范围颜色分量
        int scaledRed = r;
        int scaledGreen = g;
        int scaledBlue = b;

        // 将整数转换为16进制，并确保每个颜色分量都是两位数
        String hexRed = Integer.toHexString(scaledRed).toUpperCase();
        String hexGreen = Integer.toHexString(scaledGreen).toUpperCase();
        String hexBlue = Integer.toHexString(scaledBlue).toUpperCase();

        hexRed = hexRed.length() == 1 ? "0" + hexRed : hexRed;
        hexGreen = hexGreen.length() == 1 ? "0" + hexGreen : hexGreen;
        hexBlue = hexBlue.length() == 1 ? "0" + hexBlue : hexBlue;

        // 返回16进制颜色字符串
        return "#" + hexRed + hexGreen + hexBlue;
    }
}
