package cn.oyzh.common.util;


import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * String工具类
 *
 * @author oyzh
 * @since 2023/08/12 0024 18:16
 */
public class StringUtil {

    private StringUtil() {
    }

    /**
     * 字符串转二进制
     *
     * @param str 字符串
     * @return 二进制字符
     */
    public static String toBinary(String str) {
        if (str == null) {
            return "";
        }
        return toBinary(str.getBytes());
    }

    /**
     * 字符串转二进制
     *
     * @param bytes 字节数组
     * @return 二进制字符
     */
    public static String toBinary(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }

    /**
     * 字符串转二进制
     *
     * @param bytes 字节数组
     * @return 二进制字符
     */
    public static String toBinary(Byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuilder binary = new StringBuilder();
        for (Byte b : bytes) {
            if (b == null) {
                continue;
            }
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }

    public static void deleteLast(StringBuilder builder, String str) {
        if (builder != null && str != null) {
            if (builder.toString().endsWith(str)) {
                builder.deleteCharAt(builder.length() - 1);
            }
        }
    }

    /**
     * 删除最后一个字符
     *
     * @param builder StringBuilder对象
     */
    public static void deleteLast(StringBuilder builder) {
        if (builder != null) {
            builder.deleteCharAt(builder.length() - 1);
        }
    }

    public static boolean isBlank(String string) {
        return string == null || string.isBlank();
    }

    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

    /**
     * 是否不为空
     *
     * @param strings 字符串列表
     * @return 结果
     */
    public static boolean isNotBlank(String... strings) {
        for (String string : strings) {
            if (isBlank(string)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public static boolean equals(String source, String target) {
        return Objects.equals(source, target);
    }

    public static boolean notEquals(String source, String target) {
        return !equals(source, target);
    }

    public static boolean equalsIgnoreCase(String source, String target) {
        if (source != null && target != null) {
            return source.equalsIgnoreCase(target);
        }
        return false;
    }

    public static boolean equalsAny(String source, String... strings) {
        if (source != null && strings != null) {
            for (String string : strings) {
                if (source.equals(string)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean equalsAnyIgnoreCase(String source, String... strings) {
        if (source != null && strings != null) {
            for (String string : strings) {
                if (source.equalsIgnoreCase(string)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean notContains(String source, String target) {
        return !contains(source, target);
    }

    public static boolean containsAny(String source, String... strings) {
        if (source != null && strings != null) {
            for (String string : strings) {
                if (source.contains(string)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean containsAnyIgnoreCase(String source, String... strings) {
        if (source != null && strings != null) {
            source = source.toLowerCase();
            for (String string : strings) {
                if (source.contains(string.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean startWith(String source, String target) {
        if (source != null && target != null) {
            return source.startsWith(target.toLowerCase());
        }
        return false;
    }

    public static boolean startWithIgnoreCase(String source, String target) {
        if (source != null && target != null) {
            return source.toLowerCase().startsWith(target.toLowerCase());
        }
        return false;
    }

    public static boolean startWithAnyIgnoreCase(String source, String... target) {
        if (source != null && target != null) {
            for (String s : target) {
                if (startWithIgnoreCase(source, s)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String[] split(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len <= 0) {
            return new String[]{str};
        }
        int mod = str.length() % len;

        int arrLen = mod == 0 ? mod : mod + 1;
        String[] arr = new String[arrLen];

        int aIndex = 0;
        int idx = 0;
        while (true) {
            if (idx + len < str.length()) {
                idx = idx + len;
                arr[aIndex++] = str.substring(idx, idx);
                continue;
            }
            arr[aIndex] = str.substring(idx);
            break;
        }
        return arr;
    }

    public static List<String> split(String str, String regex) {
        if (str == null || regex == null) {
            return null;
        }
        return List.of(str.split(regex));
    }

    public static String emptyToDefault(String str, String defaultValue) {
        if (isEmpty(str)) {
            return defaultValue;
        }
        return str;
    }

    public static String blankToDefault(String str, String defaultValue) {
        if (isBlank(str)) {
            return defaultValue;
        }
        return str;
    }

    public static String nullToDefault(String str, String defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        return str;
    }

    public static String replace(String src, String search, String replace) {
        if (!isEmpty(src) && !isEmpty(search) && !isEmpty(replace)) {
            return src.replace(search, replace);
        }
        return src;
    }

    public static String delete(String str, int start, int end) {
        StringBuilder builder = new StringBuilder(str);
        builder.delete(start, end);
        return builder.toString();
    }

    public static long count(String s, String lineSeparator) {
        if (s == null || lineSeparator == null) {
            return 0;
        }
        return s.split(lineSeparator).length;
    }

    public static String lowerFirst(String str) {
        if (isEmpty(str)) {
            return str;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(str.substring(0, 1).toLowerCase());
        builder.append(str.substring(1));
        return builder.toString();
    }

    public static boolean endWith(String str, String endText) {
        return str != null && str.endsWith(endText);
    }

    public static boolean endWithAny(String str, String... endText) {
        if (str == null || endText == null) {
            return false;
        }
        for (String s : endText) {
            if (endWith(str, s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(String source, String target) {
        return source != null && target != null && source.contains(target);
    }

    public static boolean containsReverse(String str, String target) {
        return contains(str, target) || contains(target, str);
    }

    public static boolean containsIgnoreCase(String source, String target) {
        if (source != null && target != null) {
            return source.toLowerCase().contains(target.toLowerCase());
        }
        return false;
    }

    public static boolean containsIgnoreCaseReverse(String source, String target) {
        return containsIgnoreCase(source, target) || containsIgnoreCase(target, source);
    }

    public static String upperFirst(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        return source.substring(0, 1).toUpperCase() + source.substring(1);
    }

    public static boolean endWithIgnoreCase(String source, String str) {
        if (source == null || source.isEmpty() || str == null) {
            return false;
        }
        return source.toLowerCase().endsWith(str.toLowerCase());
    }

    public static boolean endWithAnyIgnoreCase(String source, String... target) {
        if (source != null && target != null) {
            for (String s : target) {
                if (endWithIgnoreCase(source, s)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String join(String space, Collection<?> collection) {
        StringBuilder builder = new StringBuilder();
        for (Object o : collection) {
            builder.append(space).append(o.toString());
        }
        return builder.substring(space.length());
    }

    public static String replaceOneTime(String original, String target, String replacement) {
        return replaceNTimes(original, target, replacement, 1);
    }

    public static String replaceNTimes(String original, String target, String replacement, int n) {
        if (n <= 0 || target == null || target.isEmpty() || replacement == null) {
            return original;
        }
        StringBuilder sb = new StringBuilder();
        int targetLength = target.length();
        int startIndex = 0;
        int currentIndex;
        int replaceCount = 0;
        while ((currentIndex = original.indexOf(target, startIndex)) != -1) {
            if (replaceCount < n) {
                // 添加从上一个索引到当前索引之间的字符串
                sb.append(original, startIndex, currentIndex);
                // 添加替换字符串
                sb.append(replacement);
                // 更新索引以跳过已替换的部分
                startIndex = currentIndex + targetLength;
                replaceCount++;
            } else {
                // 如果已达到替换次数，添加剩余部分并退出循环
                sb.append(original.substring(startIndex));
                break;
            }
        }
        // 如果没有找到任何匹配项，或者替换次数未达到但字符串已遍历完，添加剩余部分
        if (currentIndex == -1) {
            sb.append(original.substring(startIndex));
        }
        return sb.toString();
    }

    public static int levenshteinDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    public static double similarity(String s1, String s2) {
        int distance = levenshteinDistance(s1, s2);
        int maxLength = Math.max(s1.length(), s2.length());
        return 1 - ((double) distance / maxLength);
    }

    /**
     * 是否以目标内容结尾
     *
     * @param str     内容
     * @param endText 目标内容
     * @return 结果
     */
    public static boolean endsWith(String str, String endText) {
        return endWith(str, endText);
    }

    /**
     * 是否以任意目标内容结尾
     *
     * @param str     内容
     * @param endText 目标内容
     * @return 结果
     */
    public static boolean endsWithAny(String str, String... endText) {
        for (String s : endText) {
            if (endWith(str, s)) {
                return true;
            }
        }
        return false;
    }
}
