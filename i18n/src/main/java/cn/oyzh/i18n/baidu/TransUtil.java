package cn.oyzh.i18n.baidu;

import lombok.experimental.UtilityClass;

import java.util.Locale;


/**
 * @author oyzh
 * @since 2025-01-23
 */
@UtilityClass
public class TransUtil {

    /**
     * 获取百度的语言名称
     *
     * @param locale 语言
     * @return 名称
     */
    public static String localeToName(Locale locale) {
        if (locale == Locale.CHINA || locale == Locale.PRC || locale == Locale.CHINESE) {
            return "zh";
        }
        if (locale == Locale.TAIWAN || locale == Locale.TRADITIONAL_CHINESE) {
            return "cht";
        }
        if (locale == Locale.ENGLISH) {
            return "en";
        }
        if (locale == Locale.JAPAN || locale == Locale.JAPANESE) {
            return "jp";
        }
        if (locale == Locale.KOREA || locale == Locale.KOREAN) {
            return "kor";
        }
        if (locale == Locale.GERMANY || locale == Locale.GERMAN) {
            return "de";
        }
        if (locale == Locale.FRANCE || locale == Locale.FRENCH) {
            return "fra";
        }
        if (locale == Locale.ITALY || locale == Locale.ITALIAN) {
            return "it";
        }
        return "zh";
    }
}
