package cn.oyzh.common.util;

/**
 * 颜色工具类
 *
 * @author oyzh
 * @since 2024/08/17
 */
//@UtilityClass
public class ColorUtil {

    /**
     * 获取颜色16进制值
     *
     * @param r 颜色
     * @param g 颜色
     * @param b 颜色
     * @return 16进制值
     */
    public static String rgbToHex(int r, int g, int b) {
        // 将归一化的RGB值缩放到0-255范围
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
