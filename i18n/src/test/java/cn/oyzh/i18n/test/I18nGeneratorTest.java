package cn.oyzh.i18n.test;

import cn.oyzh.common.thread.ThreadUtil;
import cn.oyzh.i18n.I18nGenerator;
import cn.oyzh.i18n.I18nLocales;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author oyzh
 * @since 2025-01-23
 */
public class I18nGeneratorTest {

        private final String skFile = "/Users/oyzh/Desktop/个人/baidu_trans.sk";
//    private final String skFile = "C:\\Users\\Administrator\\Desktop\\baidu_trans.sk";

    private final String baseDir1 = "/Users/oyzh/IdeaProjects/oyzh/base/i18n/src/main/resources";
//    private final String baseDir1 = "C:\\Users\\Administrator\\IdeaProjects\\base\\i18n\\src\\main\\resources";

//    private final String baseDir2 = "/Users/oyzh/IdeaProjects/oyzh/easyredis/src/main/resources";
    private final String baseDir2 = "C:\\Users\\Administrator\\IdeaProjects\\easyredis\\src\\main\\resources";

//    private final String baseDir3 = "/Users/oyzh/IdeaProjects/oyzh/easyzk/src/main/resources";
    private final String baseDir3 = "C:\\Users\\Administrator\\IdeaProjects\\easyzk\\src\\main\\resources";

        private final String baseDir4 = "/Users/oyzh/IdeaProjects/oyzh/easyshell/src/main/resources";
//    private final String baseDir4 = "C:\\Users\\Administrator\\IdeaProjects\\easyshell\\src\\main\\resources";

    @Test
    public void test_base() {
        List<Runnable> tasks = new ArrayList<>();
        for (Locale locale : I18nLocales.locales()) {
            if (locale == Locale.PRC) {
                continue;
            }
            tasks.add(() -> trans_base(baseDir1, locale));
        }
        ThreadUtil.submit(tasks);
    }

    private void trans_base(String path, Locale locale) {
        try {
            String name = I18nLocales.getLocaleName(locale);
            String cnI18nFile = path + "/base_i18n_zh_CN.properties";
            String targetI18nFile = path + "/base_i18n_" + name + ".properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, locale);
            I18nGenerator.i18nCorrection(cnI18nFile, targetI18nFile, locale);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void test_redis() {
        List<Runnable> tasks = new ArrayList<>();
        for (Locale locale : I18nLocales.locales()) {
            if (locale == Locale.PRC) {
                continue;
            }
            tasks.add(() -> trans_program(baseDir2, locale));
        }
        ThreadUtil.submit(tasks);
    }

    @Test
    public void test_zk() {
        List<Runnable> tasks = new ArrayList<>();
        for (Locale locale : I18nLocales.locales()) {
            if (locale == Locale.PRC) {
                continue;
            }
            tasks.add(() -> trans_program(baseDir3, locale));
        }
        ThreadUtil.submit(tasks);
    }

    @Test
    public void test_shell() {
        List<Runnable> tasks = new ArrayList<>();
        for (Locale locale : I18nLocales.locales()) {
            if (locale == Locale.PRC) {
                continue;
            }
            tasks.add(() -> trans_program(baseDir4, locale));
        }
        ThreadUtil.submit(tasks);
    }

    private void trans_program(String path, Locale locale) {
        try {
            String name = I18nLocales.getLocaleName(locale);
            String cnI18nFile = path + "/i18n_zh_CN.properties";
            String targetI18nFile = path + "/i18n_" + name + ".properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, locale);
            I18nGenerator.i18nCorrection(cnI18nFile, targetI18nFile, locale);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
