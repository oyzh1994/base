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

    public static Locale ZH_YUE = Locale.of("chinese", "yue");

    public static Locale ZH_WYW = Locale.of("chinese", "wyw");

    public static Locale RU = Locale.of("russian", "ru");

    public static Locale DAN = Locale.of("dan", "dan");

    public static Locale PT = Locale.of("pt", "pt");

    public static Locale TH = Locale.of("th", "th");

    public static Locale EL = Locale.of("el", "el");

    public static Locale FIN = Locale.of("fin", "fin");

    public static Locale SLO = Locale.of("slo", "slo");

    public static Locale ARA = Locale.of("ara", "ara");

    public static Locale NL = Locale.of("nl", "nl");

    public static Locale EST = Locale.of("est", "est");

    public static Locale CS = Locale.of("cs", "cs");

    public static Locale SWE = Locale.of("swe", "swe");

    public static Locale VIE = Locale.of("vie", "vie");

    public static Locale SPA = Locale.of("spa", "spa");

    public static Locale PL = Locale.of("pl", "pl");

    public static Locale ROM = Locale.of("rom", "rom");

    public static Locale HU = Locale.of("hu", "hu");

    private static final List<I1n8Locale> locales = new ArrayList<>();

    static {
        locales.add(new I1n8Locale("zh_cn", Locale.PRC, "中文（简体）", "中文简体"));
        locales.add(new I1n8Locale("zh_tw", Locale.TAIWAN, "中文（繁体）", "中文繁体"));
        locales.add(new I1n8Locale("zh_yue", ZH_YUE, "中文（粵語）", "中文粤语"));
        locales.add(new I1n8Locale("zh_wyw", ZH_WYW, "中文（文言文）", "中文文言文"));
        locales.add(new I1n8Locale("en", Locale.ENGLISH, "English", "英语"));
        locales.add(new I1n8Locale("ja", Locale.JAPAN, "日本語", "日语"));
        locales.add(new I1n8Locale("de", Locale.GERMAN, "Deutsch", "德语"));
        locales.add(new I1n8Locale("kor", Locale.KOREA, "한국어", "韩语"));
        locales.add(new I1n8Locale("fr", Locale.FRANCE, "Français", "法语"));
        locales.add(new I1n8Locale("it", Locale.ITALY, "Italiano", "意大利语"));
        locales.add(new I1n8Locale(RU.getCountry(), RU, "Русский", "俄语"));
        locales.add(new I1n8Locale(DAN.getCountry(), DAN, "Dansk", "丹麦语"));
        locales.add(new I1n8Locale(PT.getCountry(), PT, "Português", "葡萄牙语"));
        locales.add(new I1n8Locale(TH.getCountry(), TH, "ไทย", "台语"));
        locales.add(new I1n8Locale(EL.getCountry(), EL, "Ελληνικά", "希腊语"));
        locales.add(new I1n8Locale(FIN.getCountry(), FIN, "Suomi", "芬兰语"));
        locales.add(new I1n8Locale(SLO.getCountry(), SLO, "slovenski jezik", "斯洛文尼亚语"));
        locales.add(new I1n8Locale(ARA.getCountry(), ARA, "بالعربية", "阿拉伯语"));
        locales.add(new I1n8Locale(NL.getCountry(), NL, "Nederlands", "荷兰语"));
        locales.add(new I1n8Locale(EST.getCountry(), EST, "Eesti keel", "爱沙尼亚语"));
        locales.add(new I1n8Locale(CS.getCountry(), CS, "Čeština", "捷克语"));
        locales.add(new I1n8Locale(SWE.getCountry(), SWE, "Svenska språket", "瑞典语"));
        locales.add(new I1n8Locale(VIE.getCountry(), VIE, "Tiếng Việt", "越南语"));
        locales.add(new I1n8Locale(SPA.getCountry(), SPA, "Español", "西班牙语"));
        locales.add(new I1n8Locale(PL.getCountry(), PL, "Język polski", "波兰语"));
        locales.add(new I1n8Locale(ROM.getCountry(), ROM, "română", "罗马尼亚语"));
        locales.add(new I1n8Locale(HU.getCountry(), HU, "Magyar nyelv", "匈牙利语"));
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
                return i1n8Locale.getName().toLowerCase();
            }
        }
        return locales.getFirst().getName().toLowerCase();
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
