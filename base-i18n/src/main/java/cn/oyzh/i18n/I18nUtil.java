package cn.oyzh.i18n;

import java.util.Locale;

/**
 * i18n工具类，用于对区域进行归一化处理
 *
 * @author oyzh
 * @since 2026-09-30
 */
public class I18nUtil {

    /**
     * 纠正区域，将英国、美国、加拿大及根区域统一为英语，将加拿大法语统一为法语
     *
     * @param l 区域
     * @return 纠正后的区域
     */
    public static Locale corrLocale(Locale l) {
        if (l == Locale.UK || l == Locale.US || l == Locale.CANADA || l == Locale.ROOT) {
            l = Locale.ENGLISH;
        } else if (l == Locale.CANADA_FRENCH) {
            l = Locale.FRENCH;
        }
        return l;
    }
}
