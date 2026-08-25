package cn.oyzh.common.util;

import java.util.regex.Pattern;

/**
 * 正则工具类
 *
 * @author oyzh
 * @since 2023/2/24
 */
public class RegexUtil {

    private RegexUtil() {
    }

    /**
     * IPv4的正则表达式
     */
    public static String IPV4_REGEX = "(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$";

    /**
     * IPv4的Pattern对象
     */
    public static Pattern IPV4_PATTERN = Pattern.compile(IPV4_REGEX);

    /**
     * 匹配整数的正则表达式
     */
    public static String NUMBER_REGEX = "-?\\d+";

    /**
     * 匹配整数的Pattern对象
     */
    public static Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);

    /**
     * 匹配小数的正则表达式
     */
    public static String DECIMAL_REGEX = "-?\\d+(\\.\\d*)?";

    /**
     * 匹配小数的Pattern对象
     */
    public static Pattern DECIMAL_PATTERN = Pattern.compile(DECIMAL_REGEX);

    /**
     * 是否数字
     *
     * @param str 字符串
     * @return 结果
     */
    public static boolean isDecimal(String str) {
        if (StringUtil.isBlank(str)) {
            return false;
        }
        if (str.startsWith("-") || str.startsWith("+")) {
            str = str.substring(1);
        }
        return DECIMAL_PATTERN.matcher(str).matches();
    }

    /**
     * 是否整数
     *
     * @param str 字符串
     * @return 结果
     */
    public static boolean isNumber(String str) {
        if (StringUtil.isBlank(str)) {
            return false;
        }
        if (str.startsWith("-") || str.startsWith("+")) {
            str = str.substring(1);
        }
        return NUMBER_PATTERN.matcher(str).matches();
    }

    /**
     * 是否ipv4
     *
     * @param str 字符串
     * @return 结果
     */
    public static boolean isIPV4(String str) {
        if (StringUtil.isBlank(str)) {
            return false;
        }
        return IPV4_PATTERN.matcher(str).matches();
    }

    /**
     * 根据 IDE 风格的选项构建搜索用的 Pattern 对象
     *
     * @param searchText   用户输入的搜索文本
     * @param caseSensitive 是否区分大小写（true = 区分，false = 不区分）
     * @param wholeWord     是否全词匹配（true = 是）
     * @param useRegex      是否启用正则表达式（true = 是）
     * @return 编译好的 Pattern
     */
    public static Pattern createSearchPattern(
            String searchText,
            boolean caseSensitive,
            boolean wholeWord,
            boolean useRegex) {
        if (searchText == null || searchText.isEmpty()) {
            // 空搜索文本时，可以返回一个永远不会匹配的 Pattern 或直接抛出异常
            throw new IllegalArgumentException("搜索文本不能为空");
        }

        int flags = 0;
        if (!caseSensitive) {
            flags |= Pattern.CASE_INSENSITIVE;
            flags |= Pattern.UNICODE_CASE; // 推荐：更好地支持 Unicode 的大小写不敏感
        }

        String finalRegex;
        if (useRegex) {
            // 直接使用用户输入作为正则
            finalRegex = searchText;
            if (wholeWord) {
                // 全词匹配时，用 \b 和非捕获组包裹，避免影响原有的分组
                finalRegex = "\\b(?:" + finalRegex + ")\\b";
            }
        } else {
            // 非正则模式：先将用户文本中的所有特殊字符转义
            finalRegex = Pattern.quote(searchText);
            if (wholeWord) {
                finalRegex = "\\b" + finalRegex + "\\b";
            }
        }

        return Pattern.compile(finalRegex, flags);
    }
}
