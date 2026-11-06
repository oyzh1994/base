package cn.oyzh.i18n.test;

import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.thread.ThreadUtil;
import cn.oyzh.i18n.I18nGenerator;
import cn.oyzh.i18n.I18nLocales;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 调用百度翻译为各项目生成并校正多语言资源文件。
 *
 * @author oyzh
 * @since 2025-01-23
 */
public class I18nGeneratorTest {

     private final String skFile = "/Users/oyzh/Desktop/baidu_trans.sk";
//    private final String skFile = "C:\\Users\\Administrator\\Desktop\\baidu_trans.sk";
       //private final String skFile = "C:\\Users\\oyzh\\OneDrive\\桌面\\baidu_trans.sk";

     private final String baseDir1 = "/Users/oyzh/IdeaProjects/oyzh/base/base-i18n/src/main/resources";
//    private final String baseDir1 = "C:\\Users\\Administrator\\IdeaProjects\\base\\base-i18n\\src\\main\\resources";
    //private final String baseDir1 = "C:\\Users\\oyzh\\Projects\\base\\base-i18n\\src\\main\\resources";

    private final String baseDir2 = "/Users/oyzh/IdeaProjects/oyzh/easyredis/src/main/resources";
    //    private final String baseDir2 = "C:\\Users\\Administrator\\IdeaProjects\\easyredis\\src\\main\\resources";
    //    private final String baseDir2 = "C:\\Users\\oyzh\\Projects\\easyredis\\src\\main\\resources";

    private final String baseDir3 = "/Users/oyzh/IdeaProjects/oyzh/easyzk/src/main/resources";
    //    private final String baseDir3 = "C:\\Users\\Administrator\\IdeaProjects\\easyzk\\src\\main\\resources";
    //private final String baseDir3 = "C:\\Users\\oyzh\\Projects\\easyzk\\src\\main\\resources";

     private final String baseDir4 = "/Users/oyzh/IdeaProjects/oyzh/easyshell/src/main/resources";
//     private final String baseDir4 = "C:\\Users\\Administrator\\IdeaProjects\\easyshell\\src\\main\\resources";
//       private final String baseDir4 = "C:\\Users\\oyzh\\Projects\\easyshell\\src\\main\\resources";

    @Test
    public void test_base() {
        transLocales(baseDir1, "base_i18n");
        transLocales(baseDir1, "base_compound");
    }

    @Test
    public void test_redis() {
        transLocales(baseDir2, "i18n");
    }

    @Test
    public void test_zk() {
        transLocales(baseDir3, "i18n");
    }

    @Test
    public void test_shell() {
        transLocales(baseDir4, "i18n");
    }

    private void transLocales(String path, String filePrefix) {
        List<Runnable> tasks = new ArrayList<>();
        for (Locale locale : I18nLocales.locales()) {
            // 中英无需翻译，默认支持
            if (locale == Locale.PRC || locale == Locale.ENGLISH) {
                continue;
            }
            tasks.add(() -> trans(path, locale, filePrefix));
        }
        ThreadUtil.submit(tasks);
    }

    private void trans(String path, Locale locale, String filePrefix) {
        try {
            String name = locale.toString();
            String cnI18nFile = path + "/" + filePrefix + "_zh_CN.properties";
            String targetI18nFile = path + "/" + filePrefix + "_" + name + ".properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, locale);
//            I18nGenerator.i18nCorrection(cnI18nFile, targetI18nFile, locale);
            if (!locale.getCountry().isEmpty()) {
                name = locale.getLanguage();
                targetI18nFile = path + "/" + filePrefix + "_" + name + ".properties";
                if (FileUtil.exists(targetI18nFile)) {
                    I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, locale);
//                    I18nGenerator.i18nCorrection(cnI18nFile, targetI18nFile, locale);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
