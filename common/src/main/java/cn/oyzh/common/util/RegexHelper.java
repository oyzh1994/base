package cn.oyzh.common.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

/**
 * @author oyzh
 * @since 2024/7/5
 */
//@UtilityClass
public class RegexHelper {

    /**
     * json符号正则模式
     */
    private static Pattern Json_Symbol_Pattern;

    public static Pattern jsonSymbolPattern() {
        if (Json_Symbol_Pattern == null) {
            Json_Symbol_Pattern = Pattern.compile("[{}|\\[\\]]");
        }
        return Json_Symbol_Pattern;
    }

    /**
     * json键正则模式
     */
    private static Pattern Json_Key_Pattern;

    public static Pattern jsonKeyPattern() {
        if (Json_Key_Pattern == null) {
//            Json_Key_Pattern = Pattern.compile("\"(.*?)\"(?=\\s*:)");
            Json_Key_Pattern = Pattern.compile("\"([a-zA-Z0-9-_.]+[\\w\\s]*[\\w]+)\":");
        }
        return Json_Key_Pattern;
    }

    /**
     * json值正则模式
     */
    private static Pattern Json_Value_Pattern;

    public static Pattern jsonValuePattern() {
        if (Json_Value_Pattern == null) {
//            String regex = "(\"[^\"]*\"|\\d+\\.?\\d*|true|false|null|\\{.*?\\}|\\[.*?\\])";
//            Json_Value_Pattern = Pattern.compile(regex);
            Json_Value_Pattern = Pattern.compile("(?<=:)\\s*(\"[^\"]*\"|\\d+|true|false|\\[.*?]|\\{.*?})");
        }
        return Json_Value_Pattern;
    }

    /**
     * xml标签正则模式
     */
    private static Pattern Xml_Pattern;

    public static Pattern xmlPattern() {
        if (Xml_Pattern == null) {
            Xml_Pattern = Pattern.compile("<(/?[a-zA-Z0-9_\\-\\.]+)([^>]*)>");
        }
        return Xml_Pattern;
    }

    /**
     * xml注释正则模式
     */
    private static Pattern Xml_Comment_Pattern;

    public static Pattern xmlCommentPattern() {
        if (Xml_Comment_Pattern == null) {
            Xml_Comment_Pattern = Pattern.compile("<!--.*?-->", Pattern.DOTALL);
        }
        return Xml_Comment_Pattern;
    }

    /**
     * html标签正则模式
     */
    private static Pattern Html_Pattern;

    public static Pattern htmlPattern() {
        if (Html_Pattern == null) {
            Html_Pattern = Pattern.compile("<(/?[a-zA-Z0-9_\\-\\.]+)([^>]*)>");
        }
        return Html_Pattern;
    }

    /**
     * html注释正则模式
     */
    private static Pattern Html_Comment_Pattern;

    public static Pattern htmlCommentPattern() {
        if (Html_Comment_Pattern == null) {
            Html_Comment_Pattern = Pattern.compile("<!--.*?-->", Pattern.DOTALL);
        }
        return Html_Comment_Pattern;
    }
}
