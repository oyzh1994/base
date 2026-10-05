package cn.oyzh.i18n;

import java.util.Locale;

/**
 *
 * @author oyzh
 * @since 2026-09-30
 */
public class I18nUtil {

    /**
     * 纠正区域
     *
     * @param l 区域
     * @return 结果
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
