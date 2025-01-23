package cn.oyzh.i18n;

import cn.oyzh.common.util.StringUtil;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author oyzh
 * @since 2024/4/7
 */
@UtilityClass
public class I18nLocales {

    public static Locale ZH_YUE = Locale.of("zh", "yue");

    public static Locale ZH_WYW = Locale.of("zh", "wyw");

    public static Locale RU = Locale.of("russian", "ru");

    public static Locale DAN = Locale.of("dan", "dan");

    public static Locale PT = Locale.of("pt", "pt");

    public static Locale TH = Locale.of("th", "th");

    public static Locale EL = Locale.of("el", "el");

    public static Locale FIN = Locale.of("fin", "fin");

    private static final List<I1n8Locale> locales = new ArrayList<>();

    static {
        locales.add(new I1n8Locale("zh_cn", Locale.PRC, "中国（简体）","中文简体"));
        locales.add(new I1n8Locale("zh_tw", Locale.TAIWAN, "中國（臺灣）","中文繁体"));
        locales.add(new I1n8Locale("zh_yue", ZH_YUE, "中國（粵語）","中文粤语"));
        locales.add(new I1n8Locale("zh_wyw", ZH_WYW, "中國（文言文）","中文文言文"));
        locales.add(new I1n8Locale("en", Locale.ENGLISH, "English","英语"));
        locales.add(new I1n8Locale("ja", Locale.JAPAN, "日本語","日语"));
        locales.add(new I1n8Locale("de", Locale.GERMAN, "Deutsch","德语"));
        locales.add(new I1n8Locale("kor", Locale.KOREA, "한국어","韩语"));
        locales.add(new I1n8Locale("fr", Locale.FRANCE, "Français","法语"));
        locales.add(new I1n8Locale("it", Locale.ITALY, "Italiano","意大利语"));
        locales.add(new I1n8Locale("ru", RU, "Русский","俄语"));
        locales.add(new I1n8Locale("dan", DAN, "Dansk","丹麦语"));
        locales.add(new I1n8Locale("pt", PT, "Português","葡萄牙语"));
        locales.add(new I1n8Locale("th", TH, "ไทย","台语"));
        locales.add(new I1n8Locale("el", EL, "Ελληνικά","希腊语"));
        locales.add(new I1n8Locale("fin", FIN, "Suomi","芬兰语"));
    }

    /**
     * 获取区域列表
     *
     * @return 区域列表
     */
    public static List<Locale> locales() {
        return locales.parallelStream().map(I1n8Locale::getLocale).toList();
    }

    /**
     * 获取区域描述
     *
     * @param locale 区域
     * @return 区域描述
     */
    public static String getLocaleDesc(Locale locale) {
        for (I1n8Locale i1n8Locale : locales) {
            if (i1n8Locale.getLocale() == locale) {
                return i1n8Locale.getDisplayName();
            }
        }
        return locales.getFirst().getDisplayName();
    }

    /**
     * 获取区域名称
     *
     * @param locale 区域
     * @return 区域名称
     */
    public static String getLocaleName(Locale locale) {
        for (I1n8Locale i1n8Locale : locales) {
            if (i1n8Locale.getLocale() == locale) {
                return i1n8Locale.getName();
            }
        }
        return locales.getFirst().getName();
    }

    /**
     * 获取区域
     *
     * @param localeName 区域名称
     * @return 区域
     */
    public static Locale getLocale(String localeName) {
        for (I1n8Locale i1n8Locale : locales) {
            if (StringUtil.equalsIgnoreCase(localeName, i1n8Locale.getName())) {
                return i1n8Locale.getLocale();
            }
        }
        return locales.getFirst().getLocale();
    }
}
