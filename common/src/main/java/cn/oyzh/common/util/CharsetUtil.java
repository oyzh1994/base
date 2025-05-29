package cn.oyzh.common.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字符串工具类
 *
 * @author oyzh
 * @since 2024-09-29
 */
public class CharsetUtil {

    public static Charset defaultCharset() {
        return Charset.defaultCharset();
    }

    public static String defaultCharsetName() {
        return Charset.defaultCharset().displayName();
    }

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
}
