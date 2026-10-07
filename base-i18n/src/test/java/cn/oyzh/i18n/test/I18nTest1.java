package cn.oyzh.i18n.test;

import org.junit.Test;

import java.util.Locale;

/**
 * 打印若干 Locale 常量的字符串表示，便于核对本地化标识。
 *
 * @author oyzh
 * @since 2025-02-11
 */
public class I18nTest1 {

    @Test
    public void test1() {
        System.out.println(Locale.PRC.toString());
        System.out.println(Locale.TAIWAN.toString());
        System.out.println(Locale.FRANCE.toString());
        System.out.println(Locale.GERMAN.toString());
        System.out.println(Locale.GERMANY.toString());
        System.out.println(Locale.ITALY.toString());
        System.out.println(Locale.ITALIAN.toString());
        System.out.println(Locale.ENGLISH.toString());
        System.out.println(Locale.US.toString());
        System.out.println(Locale.UK.toString());
        System.out.println(Locale.JAPAN.toString());
        System.out.println(Locale.JAPANESE.toString());
        System.out.println(Locale.KOREA.toString());
        System.out.println(Locale.KOREAN.toString());
    }
}
