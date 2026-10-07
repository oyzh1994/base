package cn.oyzh.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 数字工具类
 *
 * @author oyzh
 * @since 2023/8/29
 */
public class NumberUtil {

    /**
     * 私有构造，禁止实例化
     */
    private NumberUtil() {
    }

    /**
     * 格式化大小
     *
     * @param size 大小
     * @return 结果
     */
    public static String formatSize(long size) {
        return formatSize(size, null);
    }

    /**
     * 格式化大小
     *
     * @param size  大小
     * @param scale 小数位
     * @return 结果
     */
    public static String formatSize(double size, Integer scale) {
        double result;
        String suffix;
        if (size < 1024) {
            result = size;
            suffix = "B";
        } else if (size < 1024 * 1024) {
            result = size / 1024.d;
            suffix = "KB";
        } else if (size < 1024 * 1024 * 1024) {
            result = size / 1024.d / 1024.d;
            suffix = "MB";
        } else if (size < 1024 * 1024 * 1024 * 1024L) {
            result = size / 1024.d / 1024.d / 1024D;
            suffix = "GB";
        } else {
            result = size / 1024.d / 1024 / 1024 / 1024;
            suffix = "TB";
        }
        if (scale != null && scale >= 0) {
            DecimalFormat df = new DecimalFormat("#." + "0".repeat(scale));
            return df.format(result) + suffix;
        }
        return result + suffix;
    }

    /**
     * 解析大小
     *
     * @param size 大小
     * @return 结果
     */
    public static double parseSize(String size) {
        size = size.trim().toUpperCase();
        double result;
        if (size.contains("TB")) {
            result = NumberUtil.parseDouble(size.replace("TB", ""));
            result = result * 1024 * 1024 * 1024 * 1024;
        } else if (size.contains("GB")) {
            result = NumberUtil.parseDouble(size.replace("GB", ""));
            result = result * 1024 * 1024 * 1024;
        } else if (size.contains("MB")) {
            result = NumberUtil.parseDouble(size.replace("MB", ""));
            result = result * 1024 * 1024;
        } else if (size.contains("KB")) {
            result = NumberUtil.parseDouble(size.replace("KB", ""));
            result = result * 1024;
        } else if (size.contains("B")) {
            result = NumberUtil.parseDouble(size.replace("B", ""));
        } else {
            result = NumberUtil.parseDouble(size);
        }
        return result;
    }

    /**
     * 保留小数
     *
     * @param size  大小
     * @param scale 小数位
     * @return 结果
     */
    public static double scale(double size, Integer scale) {
        DecimalFormat df = new DecimalFormat("#." + "0".repeat(scale));
        String result = df.format(size);
        if (result.contains(",")) {
            result = result.replace(",", "");
        }
        return Double.parseDouble(result);
    }

    /**
     * 判断n1是否小于n2
     *
     * @param n1 数值1
     * @param n2 数值2
     * @return n1小于n2返回true，任一为null时返回false
     */
    public static boolean isLT(Number n1, Number n2) {
        if (n1 == null || n2 == null) {
            return false;
        }
        return n1.doubleValue() < n2.doubleValue();
    }

    /**
     * 判断n1是否小于等于n2
     *
     * @param n1 数值1
     * @param n2 数值2
     * @return n1小于等于n2返回true，任一为null时返回false
     */
    public static boolean isLTEq(Number n1, Number n2) {
        if (n1 == null || n2 == null) {
            return false;
        }
        return n1.doubleValue() <= n2.doubleValue();
    }

    /**
     * 判断n1是否等于n2（按double值比较）
     *
     * @param n1 数值1
     * @param n2 数值2
     * @return n1等于n2返回true，任一为null时返回false
     */
    public static boolean isEq(Number n1, Number n2) {
        if (n1 == null || n2 == null) {
            return false;
        }
        return n1.doubleValue() == n2.doubleValue();
    }

    /**
     * 判断n1是否大于n2
     *
     * @param n1 数值1
     * @param n2 数值2
     * @return n1大于n2返回true，任一为null时返回false
     */
    public static boolean isGT(Number n1, Number n2) {
        if (n1 == null || n2 == null) {
            return false;
        }
        return n1.doubleValue() > n2.doubleValue();
    }

    /**
     * 判断n1是否大于等于n2
     *
     * @param n1 数值1
     * @param n2 数值2
     * @return n1大于等于n2返回true，任一为null时返回false
     */
    public static boolean isGTEq(Number n1, Number n2) {
        if (n1 == null || n2 == null) {
            return false;
        }
        return n1.doubleValue() >= n2.doubleValue();
    }

    /**
     * 限制大小
     *
     * @param val 值
     * @param min 最小值
     * @param max 最大值
     * @return 结果
     */
    public static double limit(double val, double min, double max) {
        val = Math.max(val, min);
        val = Math.min(val, max);
        return val;
    }

    /**
     * 检查边距
     *
     * @param start       开始
     * @param end         结束
     * @param targetStart 目标开始
     * @param targetEnd   目标结束
     * @return 是否交叉
     */
    public static boolean checkBound(double start, double end, double targetStart, double targetEnd) {
        // 单点
        if (targetStart == targetEnd && start == targetStart) {
            return true;
        }
        // 相同
        if (start == targetStart && end == targetEnd) {
            return true;
        }
        // 包含
        if (start < targetStart && end > targetEnd) {
            return true;
        }
        // 被包含
        if (start > targetStart && end < targetEnd) {
            return true;
        }
        // 左交叉
        if (start > targetStart && end >= targetEnd && start < targetEnd) {
            return true;
        }
        // 右交叉
        if (start <= targetStart && end < targetEnd && end > targetStart) {
            return true;
        }
        return false;
    }

    /**
     * 解析为Long
     *
     * @param str 字符串
     * @return Long
     */
    public static Long parseLong(String str) {
        if (str != null) {
            return Long.parseLong(str);
        }
        return null;
    }

    /**
     * 解析为Double
     *
     * @param str 字符串
     * @return Double
     */
    public static Double parseDouble(String str) {
        if (str != null) {
            return Double.parseDouble(str);
        }
        return null;
    }

    /**
     * 解析为Number
     *
     * @param str 字符串
     * @return Number
     */
    public static Number parseNumber(String str) {
        if (RegexUtil.isDecimal(str)) {
            return Double.parseDouble(str);
        }
        if (RegexUtil.isNumber(str)) {
            return Long.parseLong(str);
        }
        return Double.parseDouble(str);
    }

    /**
     * 解析为BigDecimal
     *
     * @param str 字符串
     * @return BigDecimal
     */
    public static BigDecimal parseBigDecimal(String str) {
        if (str != null) {
            return new BigDecimal(str);
        }
        return null;
    }

    /**
     * 四舍五入保留指定小数位
     *
     * @param d        数值
     * @param scaleLen 保留的小数位数
     * @return 四舍五入后的数值
     */
    public static double round(double d, int scaleLen) {
        BigDecimal decimal = new BigDecimal(d);
        decimal = decimal.setScale(scaleLen, RoundingMode.HALF_UP);
        return decimal.doubleValue();
    }

    /**
     * 判断b1是否小于b2
     *
     * @param b1 数值1
     * @param b2 数值2
     * @return b1小于b2返回true，任一为null时返回false
     */
    public static boolean isLess(BigDecimal b1, BigDecimal b2) {
        if (b1 == null || b2 == null) {
            return false;
        }
        return b1.compareTo(b2) < 0;
    }

    /**
     * 获取整数的二进制字符串
     *
     * @param i 整数
     * @return 二进制字符串
     */
    public static String getBinaryStr(int i) {
        return Integer.toBinaryString(i);
    }

    /**
     * 字符串转int
     *
     * @param str 字符串
     * @return int值
     */
    public static int toInt(String str) {
        return Integer.parseInt(str);
    }

    /**
     * 字符串转long
     *
     * @param str 字符串
     * @return long值
     */
    public static long toLong(String str) {
        return Long.parseLong(str);
    }

    /**
     * 字符串转double
     *
     * @param str 字符串
     * @return double值
     */
    public static double toDouble(String str) {
        return Double.parseDouble(str);
    }
}
