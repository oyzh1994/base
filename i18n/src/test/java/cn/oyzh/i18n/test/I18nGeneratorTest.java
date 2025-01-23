package cn.oyzh.i18n.test;

import cn.oyzh.i18n.I18nGenerator;
import org.junit.Test;

import java.io.IOException;
import java.util.Locale;

/**
 * @author oyzh
 * @since 2025-01-23
 */
public class I18nGeneratorTest {

    private final String skFile = "/Users/oyzh/Desktop/个人/baidu_trans.sk";

    private final String baseDir = "/Users/oyzh/IdeaProjects/oyzh/base/i18n/src/main/resources/";

    @Test
    public void test1() throws IOException {
        String cnI18nFile = baseDir + "/base_i18n_zh_CN.properties";
        String targetI18nFile = baseDir + "/base_i18n_ja.properties";
        I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.JAPANESE);
    }
}
