package cn.oyzh.i18n.baidu;

import cn.oyzh.i18n.I18nLocales;

import java.util.Locale;


/**
 * 翻译工具
 *
 * @author oyzh
 * @since 2025-01-23
 */
public class TransUtil {

    /**
     * 获取百度的语言名称
     *
     * @param locale 语言
     * @return 名称
     */
    public static String localeToName(Locale locale) {
        if (locale.equals(Locale.CHINA) || locale.equals(Locale.PRC) || locale.equals(Locale.CHINESE)) {
            return "zh";
        }
        if (locale.equals(Locale.TAIWAN) || locale.equals(Locale.TRADITIONAL_CHINESE)) {
            return "cht";
        }
        if (locale.equals(Locale.ENGLISH) || locale.equals(Locale.US) || locale.equals(Locale.UK)) {
            return "en";
        }
        if (locale.equals(Locale.JAPAN) || locale.equals(Locale.JAPANESE)) {
            return "jp";
        }
        if (locale.equals(Locale.KOREA) || locale.equals(Locale.KOREAN)) {
            return "kor";
        }
        if (locale.equals(Locale.GERMANY) || locale.equals(Locale.GERMAN)) {
            return "de";
        }
        if (locale.equals(Locale.FRANCE) || locale.equals(Locale.FRENCH)) {
            return "fra";
        }
        if (locale.equals(Locale.ITALY) || locale.equals(Locale.ITALIAN)) {
            return "it";
        }
        if (locale.equals(I18nLocales.DA)) {
            return "dan";
        }
        if (locale.equals(I18nLocales.FI)) {
            return "fin";
        }
        if (locale.equals(I18nLocales.SL)) {
            return "slo";
        }
        if (locale.equals(I18nLocales.AR)) {
            return "ara";
        }
        if (locale.equals(I18nLocales.ET)) {
            return "est";
        }
        if (locale.equals(I18nLocales.SV)) {
            return "swe";
        }
        if (locale.equals(I18nLocales.VI)) {
            return "vie";
        }
        if (locale.equals(I18nLocales.ES)) {
            return "spa";
        }
        if (locale.equals(I18nLocales.RO)) {
            return "rom";
        }
//        if (locale.equals(I18nLocales.ZH_WYW)) {
//            return "wyw";
//        }
        if (locale.equals(I18nLocales.ZH_YUE)) {
            return "yue";
        }
//        return locale.getCountry().toLowerCase();
        return locale.toString();
    }
}
