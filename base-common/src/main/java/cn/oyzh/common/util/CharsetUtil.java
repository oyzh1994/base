package cn.oyzh.common.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字符集工具类
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class CharsetUtil {

    /**
     * GBK字符集
     */
    public static final Charset CHARSET_GBK = charset("gbk");

    /**
     * 私有构造，禁止实例化
     */
    private CharsetUtil() {
    }

    /**
     * 获取系统默认字符集
     *
     * @return 系统默认字符集
     */
    public static Charset defaultCharset() {
        return Charset.defaultCharset();
    }

    /**
     * 获取系统默认字符集名称
     *
     * @return 系统默认字符集名称
     */
    public static String defaultCharsetName() {
        return Charset.defaultCharset().displayName();
    }

    /**
     * 根据名称获取字符集，对常用字符集做了名称兼容处理
     *
     * @param charsetName 字符集名称
     * @return 字符集
     */
    public static Charset fromName(String charsetName) {
        if (StringUtil.equalsAnyIgnoreCase(charsetName, "utf8", "utf-8")) {
            return StandardCharsets.UTF_8;
        }
        if (StringUtil.equalsAnyIgnoreCase(charsetName, "iso-8859-1")) {
            return StandardCharsets.ISO_8859_1;
        }
        if (StringUtil.equalsAnyIgnoreCase(charsetName, "gbk")) {
            return Charset.forName("GBK");
        }
        if (StringUtil.equalsAnyIgnoreCase(charsetName, "gb2312")) {
            return Charset.forName("GB2312");
        }
        return Charset.forName(charsetName);
    }

    /**
     * 转换字符串的字符集
     *
     * @param str    字符串
     * @param from   原始字符集
     * @param target 目标字符集
     * @return 转换字符集后的字符串
     */
    public static String convert(String str, Charset from, Charset target) {
        return TextUtil.changeCharset(str, from, target);
    }

    /**
     * 根据名称获取字符集
     *
     * @param charset 字符集名称
     * @return 字符集，名称为空时返回null
     */
    public static Charset charset(String charset) {
        if (StringUtil.isNotBlank(charset)) {
            return Charset.forName(charset);
        }
        return null;
    }
}
