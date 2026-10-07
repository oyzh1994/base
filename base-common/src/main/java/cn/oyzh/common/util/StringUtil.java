package cn.oyzh.common.util;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * String工具类
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class StringUtil {

    public static final String SPACE = " ";

    /**
     * 私有构造，禁止实例化
     */
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

    /**
     * 删除最后一个指定的字符
     *
     * @param builder StringBuilder对象
     * @param str     字符
     */
    public static void deleteLast(StringBuilder builder, String str) {
        if (builder != null && str != null && builder.toString().contains(str)) {
            builder.deleteCharAt(builder.lastIndexOf(str));
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

    /**
     * 是否为空
     *
     * @param string 字符串
     * @return 结果
     */
    public static boolean isBlank(String string) {
        return string == null || string.isBlank();
    }

    /**
     * 是否任意字符为空
     *
     * @param strings 字符列表
     * @return 结果
     */
    public static boolean isAnyBlank(String... strings) {
        for (String string : strings) {
            if (isBlank(string)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否不为空
     *
     * @param string 字符串
     * @return 结果
     */
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

    /**
     * 是否为空
     *
     * @param string 字符串
     * @return 结果
     */
    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * 是否不为空
     *
     * @param string 字符串
     * @return 结果
     */
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    /**
     * 是否相等
     *
     * @param source 源字符串
     * @param target 目标字符串
     * @return 结果
     */
    public static boolean equals(String source, String target) {
        return Objects.equals(source, target);
    }

    /**
     * 是否不相等
     *
     * @param source 源字符串
     * @param target 目标字符串
     * @return 结果
     */
    public static boolean notEquals(String source, String target) {
        return !equals(source, target);
    }

    /**
     * 是否相等，忽略大小写
     *
     * @param source 源字符串
     * @param target 目标字符串
     * @return 结果
     */
    public static boolean equalsIgnoreCase(String source, String target) {
        if (source != null && target != null) {
            return source.equalsIgnoreCase(target);
        }
        return Objects.equals(source, target);
    }

    /**
     * 是否等于任意一个目标字符串
     *
     * @param source  源字符串
     * @param strings 目标字符串列表
     * @return 结果
     */
    public static boolean equalsAny(String source, String... strings) {
        if (source != null && strings != null) {
            for (String string : strings) {
                if (Objects.equals(source, string)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否等于任意一个目标字符串，忽略大小写
     *
     * @param source  源字符串
     * @param strings 目标字符串列表
     * @return 结果
     */
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

    /**
     * 是否不包含目标字符串
     *
     * @param source 源字符串
     * @param target 目标字符串
     * @return 结果
     */
    public static boolean notContains(String source, String target) {
        return !contains(source, target);
    }

    /**
     * 是否包含任意一个目标字符串
     *
     * @param source  源字符串
     * @param strings 目标字符串列表
     * @return 结果
     */
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

    /**
     * 是否包含任意一个目标字符串，忽略大小写
     *
     * @param source  源字符串
     * @param strings 目标字符串列表
     * @return 结果
     */
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

    /**
     * 是否以目标字符串开头
     *
     * @param source 源字符串
     * @param target 目标字符串
     * @return 结果
     */
    public static boolean startWith(String source, String target) {
        if (source != null && target != null) {
            return source.startsWith(target.toLowerCase());
        }
        return false;
    }

    /**
     * 是否以目标字符串开头，忽略大小写
     *
     * @param source 源字符串
     * @param target 目标字符串
     * @return 结果
     */
    public static boolean startWithIgnoreCase(String source, String target) {
        if (source != null && target != null) {
            return source.toLowerCase().startsWith(target.toLowerCase());
        }
        return false;
    }

    /**
     * 是否以任意一个目标字符串开头
     *
     * @param source 源字符串
     * @param target 目标字符串列表
     * @return 结果
     */
    public static boolean startWithAny(String source, String... target) {
        if (source != null && target != null) {
            for (String s : target) {
                if (startWith(source, s)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否以任意一个目标字符串开头，忽略大小写
     *
     * @param source 源字符串
     * @param target 目标字符串列表
     * @return 结果
     */
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

    /**
     * 按固定长度切分字符串
     *
     * @param str 字符串
     * @param len 每段的长度
     * @return 切分后的字符串数组
     */
    public static String[] split(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len <= 0) {
            return new String[]{str};
        }
        int length = str.length();
        int size = (length + len - 1) / len;
        String[] arr = new String[size];
        for (int i = 0; i < size; i++) {
            int start = i * len;
            arr[i] = str.substring(start, Math.min(start + len, length));
        }
        return arr;
    }

    /**
     * 按正则切分字符串
     *
     * @param str   字符串
     * @param regex 正则表达式
     * @return 切分后的字符串列表
     */
    public static List<String> split(String str, String regex) {
        if (str == null || regex == null) {
            return null;
        }
        return List.of(str.split(regex));
    }

    /**
     * 内容为空时返回默认值
     *
     * @param str          内容
     * @param defaultValue 默认值
     * @return 结果
     */
    public static String emptyToDefault(String str, String defaultValue) {
        if (isEmpty(str)) {
            return defaultValue;
        }
        return str;
    }

    /**
     * 内容为空白时返回默认值
     *
     * @param str          内容
     * @param defaultValue 默认值
     * @return 结果
     */
    public static String blankToDefault(String str, String defaultValue) {
        if (isBlank(str)) {
            return defaultValue;
        }
        return str;
    }

    /**
     * 内容为null时返回默认值
     *
     * @param str          内容
     * @param defaultValue 默认值
     * @return 结果
     */
    public static String nullToDefault(String str, String defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        return str;
    }

    /**
     * 替换字符串
     *
     * @param src     源字符串
     * @param search  查找字符串
     * @param replace 替换字符串
     * @return 结果
     */
    public static String replace(String src, String search, String replace) {
        if (!isEmpty(src) && !isEmpty(search) && !isEmpty(replace)) {
            return src.replace(search, replace);
        }
        return src;
    }

    /**
     * 删除指定区间的字符
     *
     * @param str   字符串
     * @param start 起始下标
     * @param end   结束下标
     * @return 结果
     */
    public static String delete(String str, int start, int end) {
        StringBuilder builder = new StringBuilder(str);
        builder.delete(start, end);
        return builder.toString();
    }

    /**
     * 计算字符出现的次数
     *
     * @param s   字符
     * @param str 查找的字符
     * @return 结果
     */
    public static long count(String s, String str) {
        if (s == null || str == null) {
            return 0;
        }
        int index = 0;
        int count = 0;
        while (true) {
            index = s.indexOf(str, index);
            if (index == -1) {
                break;
            }
            index++;
            count++;
        }
        return count;
    }

    /**
     * 计算字符出现的次数
     *
     * @param s 字符串
     * @param c 字符
     * @return 次数
     */
    public static long count(String s, char c) {
        if (s == null) {
            return 0;
        }
        long count = 0;
        for (char c1 : s.toCharArray()) {
            if (c == c1) {
                count++;
            }
        }
        return count;
    }

    /**
     * 首字母转小写
     *
     * @param str 字符串
     * @return 结果
     */
    public static String lowerFirst(String str) {
        if (isEmpty(str)) {
            return str;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(str.substring(0, 1).toLowerCase());
        builder.append(str.substring(1));
        return builder.toString();
    }

    /**
     * 是否以任意一个目标内容结尾
     *
     * @param str     内容
     * @param endText 目标内容列表
     * @return 结果
     */
    public static boolean endWithAny(String str, String... endText) {
        return endsWithAny(str, endText);
    }

    /**
     * 是否包含目标字符串
     *
     * @param source 源字符串
     * @param target 目标字符串
     * @return 结果
     */
    public static boolean contains(String source, String target) {
        return source != null && target != null && source.contains(target);
    }

    /**
     * 是否互相包含
     *
     * @param str    字符串
     * @param target 目标字符串
     * @return 结果
     */
    public static boolean containsReverse(String str, String target) {
        return contains(str, target) || contains(target, str);
    }

    /**
     * 是否包含目标字符串，忽略大小写
     *
     * @param source 源字符串
     * @param target 目标字符串
     * @return 结果
     */
    public static boolean containsIgnoreCase(String source, String target) {
        if (source != null && target != null) {
            return source.toLowerCase().contains(target.toLowerCase());
        }
        return false;
    }

    /**
     * 是否互相包含，忽略大小写
     *
     * @param source 源字符串
     * @param target 目标字符串
     * @return 结果
     */
    public static boolean containsIgnoreCaseReverse(String source, String target) {
        return containsIgnoreCase(source, target) || containsIgnoreCase(target, source);
    }

    /**
     * 首字母转大写
     *
     * @param source 源字符串
     * @return 结果
     */
    public static String upperFirst(String source) {
        if (source == null || source.isEmpty()) {
            return source;
        }
        return source.substring(0, 1).toUpperCase() + source.substring(1);
    }

    /**
     * 是否以目标内容结尾，忽略大小写
     *
     * @param source 内容
     * @param str    目标内容
     * @return 结果
     */
    public static boolean endWithIgnoreCase(String source, String str) {
        if (source == null || source.isEmpty() || str == null) {
            return false;
        }
        return source.toLowerCase().endsWith(str.toLowerCase());
    }

    /**
     * 是否以任意一个目标内容结尾，忽略大小写
     *
     * @param source 内容
     * @param target 目标内容列表
     * @return 结果
     */
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

    /**
     * 拼接集合
     *
     * @param space      拼接字符
     * @param collection 集合
     * @return 拼接后的字符串
     */
    public static String join(String space, Collection<?> collection) {
        StringBuilder builder = new StringBuilder();
        for (Object o : collection) {
            builder.append(space).append(o.toString());
        }
        return builder.substring(space.length());
    }

    /**
     * 拼接对象数组
     *
     * @param space 拼接字符
     * @param array 对象数组
     * @return 拼接后的字符串
     */
    public static String join(String space, Object[] array) {
        return join(space, Arrays.asList(array));
    }

    /**
     * 拼接字符串数组
     *
     * @param space 拼接字符
     * @param array 字符串数组
     * @return 拼接后的字符串
     */
    public static String join(String space, String[] array) {
        return join(space, Arrays.asList(array));
    }

    /**
     * 替换第一次出现的子串
     *
     * @param original    原字符串
     * @param target      目标子串
     * @param replacement 替换子串
     * @return 替换后的字符串
     */
    public static String replaceOneTime(String original, String target, String replacement) {
        return replaceNTimes(original, target, replacement, 1);
    }

    /**
     * 替换指定次数的子串
     *
     * @param original    原字符串
     * @param target      目标子串
     * @param replacement 替换子串
     * @param n           替换次数
     * @return 替换后的字符串
     */
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

    /**
     * 计算两个字符串的编辑距离
     *
     * @param s1 字符串1
     * @param s2 字符串2
     * @return 编辑距离
     */
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

    /**
     * 计算两个字符串的相似度
     *
     * @param s1 字符串1
     * @param s2 字符串2
     * @return 相似度，取值范围为0到1
     */
    public static double similarity(String s1, String s2) {
        int distance = levenshteinDistance(s1, s2);
        int maxLength = Math.max(s1.length(), s2.length());
        return 1 - ((double) distance / maxLength);
    }

    /**
     * 是否以目标内容结尾
     *
     * @param source 内容
     * @param target 目标内容
     * @return 结果
     */
    public static boolean endsWith(String source, String target) {
        if (source != null && target != null) {
            return source.endsWith(target);
        }
        return false;
    }

    /**
     * 是否以目标内容结尾，忽略大小写
     *
     * @param source 内容
     * @param target 目标内容
     * @return 结果
     */
    public static boolean endsWithIgnoreCase(String source, String target) {
        if (source != null && target != null) {
            return source.toLowerCase().endsWith(target.toLowerCase());
        }
        return false;
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
            if (endsWith(str, s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 统计出现的字符数是否大于等于指定数量
     *
     * @param str           字符串
     * @param target        字符
     * @param maxOccurrence 判定阈值，出现次数达到该值即返回true
     * @return 达到阈值返回true，否则返回false
     */
    public static boolean checkCountOccurrences(String str, char target, int maxOccurrence) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == target) {
                count++;
                if (count >= maxOccurrence) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取字符串长度
     *
     * @param str 字符串
     * @return 长度
     */
    public static int length(String str) {
        return str == null ? 0 : str.length();
    }

    /**
     * 如果内容为空，则转为null
     *
     * @param str 内容
     * @return 结果
     */
    public static String emptyToNull(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return str;
    }

    /**
     * 比较大小
     *
     * @param str1       字符1
     * @param str2       字符2
     * @param nullIsLess null为更小值
     * @return 结果
     */
    public static int compare(CharSequence str1, CharSequence str2, boolean nullIsLess) {
        if (str1 == str2) {
            return 0;
        }
        if (str1 == null) {
            return nullIsLess ? -1 : 1;
        }
        if (str2 == null) {
            return nullIsLess ? 1 : -1;
        }
        return str1.toString().compareTo(str2.toString());
    }

    /**
     * 替换最后一次出现的子串
     *
     * @param str    原字符串
     * @param source 被替换的子串
     * @param target 替换后的子串
     * @return 替换后的字符串
     */
    public static String replaceLast(String str, String source, String target) {
        if (str == null || source == null || target == null) {
            return str;
        }
        int index = str.lastIndexOf(source);
        if (index == -1) {
            return str;
        }
        String s1 = str.substring(0, index);
        String s2 = str.substring(index + source.length());
        return s1 + target + s2;
    }

    /**
     * 移除最后一个字符
     *
     * @param str 字符串
     * @return 结果
     */
    public static String removeLast(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return str.substring(0, str.length() - 1);
    }

    /**
     * 转换为大写
     *
     * @param str 字符串
     * @return 结果
     */
    public static String toUpperCase(String str) {
        return str == null ? null : str.toUpperCase();
    }

    /**
     * 转换为小写
     *
     * @param str 字符串
     * @return 结果
     */
    public static String toLowerCase(String str) {
        return str == null ? null : str.toLowerCase();
    }

    /**
     * 获取两个字符串的最长公共前缀。
     *
     * @param a 第一个字符串
     * @param b 第二个字符串
     * @return 最长公共前缀，如果没有则返回空字符串 ""
     */
    public static String commonPrefix(String a, String b) {
        if (a == null || b == null) {
            return "";
        }
        int minLength = Math.min(a.length(), b.length());
        int i = 0;
        while (i < minLength && a.charAt(i) == b.charAt(i)) {
            i++;
        }
        return a.substring(0, i);
    }

    /**
     * 获取多个字符串的最长公共前缀。
     *
     * @param strs 可变参数字符串数组
     * @return 最长公共前缀，如果没有则返回 ""
     */
    public static String commonPrefix(List<String> strs) {
        if (strs == null || strs.isEmpty()) {
            return "";
        }
        String prefix = strs.getFirst();
        for (int i = 1; i < strs.size(); i++) {
            prefix = commonPrefix(prefix, strs.get(i));
            if (prefix.isEmpty()) {
                break; // 已经无公共前缀，提前结束
            }
        }
        return prefix;
    }

    /**
     * 清除内容
     *
     * @param builder StringBuilder
     */
    public static void clear(StringBuilder builder) {
        if (builder != null) {
            builder.delete(0, builder.length());
        }
    }
}
