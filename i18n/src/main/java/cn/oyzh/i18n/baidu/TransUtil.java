package cn.oyzh.i18n.baidu;

import lombok.experimental.UtilityClass;

import java.util.Locale;


/**
 * @author oyzh
 * @since 2025-01-23
 */
@UtilityClass
public class TransUtil {

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
            return "kor";
        }
        return "zh";
    }
}
