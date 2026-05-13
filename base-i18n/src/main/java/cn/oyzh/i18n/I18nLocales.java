package cn.oyzh.i18n;

import cn.oyzh.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * i18n方言
 *
 * @author oyzh
 * @since 2024/4/7
 */
public class I18nLocales {

    public static Locale ZH_YUE = Locale.of("zh", "yue");

//    public static Locale ZH_WYW = Locale.of("zh", "wyw");

    public static Locale RU = Locale.of("ru");

    public static Locale DA = Locale.of("da");

    public static Locale PT = Locale.of("pt");

    public static Locale TH = Locale.of("th");

    public static Locale EL = Locale.of("el");

    public static Locale FI = Locale.of("fi");

    public static Locale SL = Locale.of("sl");

    public static Locale AR = Locale.of("ar");

    public static Locale NL = Locale.of("nl");

    public static Locale ES = Locale.of("es");

    public static Locale ET = Locale.of("et");

    public static Locale CS = Locale.of("cs");

    public static Locale SV = Locale.of("sv");

    public static Locale VI = Locale.of("vi");

    public static Locale PL = Locale.of("pl");

    public static Locale RO = Locale.of("ro");

    public static Locale HU = Locale.of("hu");

    private static final List<I18nLocale> locales = new ArrayList<>();

    static {
        locales.add(new I18nLocale(Locale.PRC.toString(), Locale.PRC, "中文（简体）", "中文简体"));
        locales.add(new I18nLocale(Locale.TAIWAN.toString(), Locale.TAIWAN, "中文（繁体）", "中文繁体"));
        locales.add(new I18nLocale(ZH_YUE.toString(), ZH_YUE, "中文（粵語）", "中文粤语"));
//        locales.add(new I18nLocale(ZH_WYW.toString(), ZH_WYW, "中文（文言文）", "中文文言文"));
        locales.add(new I18nLocale(Locale.ENGLISH.toString(), Locale.ENGLISH, "English", "英语"));
        locales.add(new I18nLocale(Locale.JAPAN.toString(), Locale.JAPAN, "日本語", "日语"));
        locales.add(new I18nLocale(Locale.GERMAN.toString(), Locale.GERMAN, "Deutsch", "德语"));
        locales.add(new I18nLocale(Locale.KOREA.toString(), Locale.KOREA, "한국어", "韩语"));
        locales.add(new I18nLocale(Locale.FRANCE.toString(), Locale.FRANCE, "Français", "法语"));
        locales.add(new I18nLocale(Locale.ITALY.toString(), Locale.ITALY, "Italiano", "意大利语"));
        locales.add(new I18nLocale(RU.toString(), RU, "Русский", "俄语"));
        locales.add(new I18nLocale(DA.toString(), DA, "Dansk", "丹麦语"));
        locales.add(new I18nLocale(PT.toString(), PT, "Português", "葡萄牙语"));
        locales.add(new I18nLocale(TH.toString(), TH, "ไทย", "台语"));
        locales.add(new I18nLocale(EL.toString(), EL, "Ελληνικά", "希腊语"));
        locales.add(new I18nLocale(FI.toString(), FI, "Suomi", "芬兰语"));
        locales.add(new I18nLocale(SL.toString(), SL, "slovenski jezik", "斯洛文尼亚语"));
        locales.add(new I18nLocale(AR.toString(), AR, "بالعربية", "阿拉伯语"));
        locales.add(new I18nLocale(NL.toString(), NL, "Nederlands", "荷兰语"));
        locales.add(new I18nLocale(ET.toString(), ET, "Eesti keel", "爱沙尼亚语"));
        locales.add(new I18nLocale(CS.toString(), CS, "Čeština", "捷克语"));
        locales.add(new I18nLocale(SV.toString(), SV, "Svenska språket", "瑞典语"));
        locales.add(new I18nLocale(VI.toString(), VI, "Tiếng Việt", "越南语"));
        locales.add(new I18nLocale(ES.toString(), ES, "Español", "西班牙语"));
        locales.add(new I18nLocale(PL.toString(), PL, "Język polski", "波兰语"));
        locales.add(new I18nLocale(RO.toString(), RO, "română", "罗马尼亚语"));
        locales.add(new I18nLocale(HU.toString(), HU, "Magyar nyelv", "匈牙利语"));
    }

    /**
     * 获取区域列表
     *
     * @return 区域列表
     */
    public static List<Locale> locales() {
        return locales.parallelStream().map(I18nLocale::getLocale).toList();
    }

    /**
     * 获取区域描述
     *
     * @param locale 区域
     * @return 区域描述
     */
    public static String getLocaleDesc(Locale locale) {
        for (I18nLocale i1n8Locale : locales) {
            if (i1n8Locale.getLocale().equals(locale)) {
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
        for (I18nLocale i1n8Locale : locales) {
            if (i1n8Locale.getLocale().equals(locale)) {
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
        for (I18nLocale i1n8Locale : locales) {
            if (StringUtil.equalsIgnoreCase(localeName, i1n8Locale.getName())) {
                return i1n8Locale.getLocale();
            }
        }
        return locales.getFirst().getLocale();
    }
}
