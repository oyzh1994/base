package cn.oyzh.i18n;

import cn.oyzh.common.util.StringUtil;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author oyzh
 * @since 2024/4/7
 */
@UtilityClass
public class I18nLocales {

    /**
     * 获取区域列表
     *
     * @return 区域列表
     */
    public static List<Locale> locales() {
        List<Locale> locales = new ArrayList<>();
        locales.add(Locale.PRC);
        locales.add(Locale.TAIWAN);
        locales.add(Locale.ENGLISH);
        locales.add(Locale.JAPAN);
        locales.add(Locale.GERMAN);
        locales.add(Locale.FRANCE);
        locales.add(Locale.ITALY);
        return locales;
    }

    /**
     * 获取区域描述
     *
     * @param locale 区域
     * @return 区域描述
     */
    public static String getLocaleDesc(Locale locale) {
        if (locale == Locale.PRC) {
            return "中文简体";
        }
        if (locale == Locale.TAIWAN) {
            return "中文繁體";
        }
        if (locale == Locale.ENGLISH) {
            return "English";
        }
        if (locale == Locale.JAPAN) {
            return "日本語";
        }
        if (locale == Locale.GERMAN) {
            return "Deutsch";
        }
        if (locale == Locale.KOREA) {
            return "한국어";
        }
        if (locale == Locale.FRANCE) {
            return "Français";
        }
        if (locale == Locale.ITALY) {
            return "Italiano";
        }
        return "中文简体";
    }

    /**
     * 获取区域名称
     *
     * @param locale 区域
     * @return 区域名称
     */
    public static String getLocaleName(Locale locale) {
        if (locale == Locale.PRC) {
            return "zh_cn";
        }
        if (locale == Locale.TAIWAN) {
            return "zh_tw";
        }
        if (locale == Locale.ENGLISH) {
            return "en";
        }
        if (locale == Locale.JAPAN) {
            return "ja";
        }
        if (locale == Locale.GERMAN) {
            return "de";
        }
        if (locale == Locale.KOREA) {
            return "kor";
        }
        if (locale == Locale.FRANCE) {
            return "fr";
        }
        if (locale == Locale.ITALIAN) {
            return "it";
        }
        return "zh_cn";
    }

    /**
     * 获取区域
     *
     * @param localeName 区域名称
     * @return 区域
     */
    public static Locale getLocale(String localeName) {
        if (StringUtil.equals(localeName, "zh_cn")) {
            return Locale.PRC;
        }
        if (StringUtil.equals(localeName, "zh_tw")) {
            return Locale.TAIWAN;
        }
        if (StringUtil.equals(localeName, "en")) {
            return Locale.ENGLISH;
        }
        if (StringUtil.equals(localeName, "ja")) {
            return Locale.JAPAN;
        }
        if (StringUtil.equals(localeName, "de")) {
            return Locale.GERMAN;
        }
        if (StringUtil.equals(localeName, "kor")) {
            return Locale.KOREA;
        }
        if (StringUtil.equals(localeName, "fr")) {
            return Locale.FRANCE;
        }
        if (StringUtil.equals(localeName, "it")) {
            return Locale.ITALY;
        }
        return Locale.PRC;
    }
}
