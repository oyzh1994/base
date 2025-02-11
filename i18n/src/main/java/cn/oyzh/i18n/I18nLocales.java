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

    public static Locale RU = Locale.of("ru");

    public static Locale DAN = Locale.of("dan");

    public static Locale PT = Locale.of("pt");

    public static Locale TH = Locale.of("th");

    public static Locale EL = Locale.of("el");

    public static Locale FIN = Locale.of("fin");

    public static Locale SLO = Locale.of("slo");

    public static Locale ARA = Locale.of("ara");

    public static Locale NL = Locale.of("nl");

    public static Locale EST = Locale.of("est");

    public static Locale CS = Locale.of("cs");

    public static Locale SWE = Locale.of("swe");

    public static Locale VIE = Locale.of("vie");

    public static Locale SPA = Locale.of("spa");

    public static Locale PL = Locale.of("pl");

    public static Locale ROM = Locale.of("rom");

    public static Locale HU = Locale.of("hu");

    private static final List<I1n8Locale> locales = new ArrayList<>();

    static {
        locales.add(new I1n8Locale(Locale.PRC.toString(), Locale.PRC, "中文（简体）", "中文简体"));
        locales.add(new I1n8Locale(Locale.TAIWAN.toString(), Locale.TAIWAN, "中文（繁体）", "中文繁体"));
        locales.add(new I1n8Locale(ZH_YUE.toString(), ZH_YUE, "中文（粵語）", "中文粤语"));
        locales.add(new I1n8Locale(ZH_WYW.toString(), ZH_WYW, "中文（文言文）", "中文文言文"));
        locales.add(new I1n8Locale(Locale.ENGLISH.toString(), Locale.ENGLISH, "English", "英语"));
        locales.add(new I1n8Locale(Locale.JAPAN.toString(), Locale.JAPAN, "日本語", "日语"));
        locales.add(new I1n8Locale(Locale.GERMAN.toString(), Locale.GERMAN, "Deutsch", "德语"));
        locales.add(new I1n8Locale(Locale.KOREA.toString(), Locale.KOREA, "한국어", "韩语"));
        locales.add(new I1n8Locale(Locale.FRANCE.toString(), Locale.FRANCE, "Français", "法语"));
        locales.add(new I1n8Locale(Locale.ITALY.toString(), Locale.ITALY, "Italiano", "意大利语"));
        locales.add(new I1n8Locale(RU.toString(), RU, "Русский", "俄语"));
        locales.add(new I1n8Locale(DAN.toString(), DAN, "Dansk", "丹麦语"));
        locales.add(new I1n8Locale(PT.toString(), PT, "Português", "葡萄牙语"));
        locales.add(new I1n8Locale(TH.toString(), TH, "ไทย", "台语"));
        locales.add(new I1n8Locale(EL.toString(), EL, "Ελληνικά", "希腊语"));
        locales.add(new I1n8Locale(FIN.toString(), FIN, "Suomi", "芬兰语"));
        locales.add(new I1n8Locale(SLO.toString(), SLO, "slovenski jezik", "斯洛文尼亚语"));
        locales.add(new I1n8Locale(ARA.toString(), ARA, "بالعربية", "阿拉伯语"));
        locales.add(new I1n8Locale(NL.toString(), NL, "Nederlands", "荷兰语"));
        locales.add(new I1n8Locale(EST.toString(), EST, "Eesti keel", "爱沙尼亚语"));
        locales.add(new I1n8Locale(CS.toString(), CS, "Čeština", "捷克语"));
        locales.add(new I1n8Locale(SWE.toString(), SWE, "Svenska språket", "瑞典语"));
        locales.add(new I1n8Locale(VIE.toString(), VIE, "Tiếng Việt", "越南语"));
        locales.add(new I1n8Locale(SPA.toString(), SPA, "Español", "西班牙语"));
        locales.add(new I1n8Locale(PL.toString(), PL, "Język polski", "波兰语"));
        locales.add(new I1n8Locale(ROM.toString(), ROM, "română", "罗马尼亚语"));
        locales.add(new I1n8Locale(HU.toString(), HU, "Magyar nyelv", "匈牙利语"));
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
