package cn.oyzh.common.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author oyzh
 * @since 2023/8/29
 */
//@UtilityClass
public class NumberUtil {

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
     * @param size 大小
     * @param scale 小数位
     * @return 结果
     */
    public static String formatSize(long size, Integer scale) {
        double result = size;
        String suffix = "";
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

    public static boolean isLT(Number n1, Number n2) {
        if (n1 == null || n2 == null) {
            return false;
        }
        return n1.doubleValue() < n2.doubleValue();
    }

    public static boolean isLTEq(Number n1, Number n2) {
        if (n1 == null || n2 == null) {
            return false;
        }
        return n1.doubleValue() <= n2.doubleValue();
    }

    public static boolean isEq(Number n1, Number n2) {
        if (n1 == null || n2 == null) {
            return false;
        }
        return n1.doubleValue() == n2.doubleValue();
    }

    public static boolean isGT(Number n1, Number n2) {
        if (n1 == null || n2 == null) {
            return false;
        }
        return n1.doubleValue() > n2.doubleValue();
    }

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

    public static double round(double d, int scaleLen) {
        BigDecimal decimal = new BigDecimal(d);
        decimal = decimal.setScale(scaleLen, RoundingMode.HALF_UP);
        return decimal.doubleValue();
    }

    public static boolean isLess(BigDecimal b1, BigDecimal b2) {
        if (b1 == null || b2 == null) {
            return false;
        }
        return b1.compareTo(b2) < 0;
    }

    public static String getBinaryStr(int i) {
        return Integer.toBinaryString(i);
    }
}
