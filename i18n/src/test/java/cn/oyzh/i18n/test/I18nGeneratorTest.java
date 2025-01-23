package cn.oyzh.i18n.test;

import cn.oyzh.common.thread.IRunnable;
import cn.oyzh.common.thread.Task;
import cn.oyzh.common.thread.TaskManager;
import cn.oyzh.common.thread.ThreadUtil;
import cn.oyzh.i18n.I18nGenerator;
import lombok.SneakyThrows;
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

    //    private final String skFile = "/Users/oyzh/Desktop/个人/baidu_trans.sk";
    private final String skFile = "C:\\Users\\Administrator\\Desktop\\baidu_trans.sk";

    //    private final String baseDir = "/Users/oyzh/IdeaProjects/oyzh/base/i18n/src/main/resources/";
    private final String baseDir1 = "C:\\Users\\Administrator\\IdeaProjects\\base\\i18n\\src\\main\\resources";

    private final String baseDir2 = "/Users/oyzh/IdeaProjects/oyzh/base/i18n/src/main/resources/";

    private final String baseDir3 = "/Users/oyzh/IdeaProjects/oyzh/base/i18n/src/main/resources/";

    @Test
    public void test_base() {
        List<Runnable> tasks = new ArrayList<>();
        tasks.add(this::base_en);
        tasks.add(this::base_ja);
        tasks.add(this::base_de);
        tasks.add(this::base_tw);
        tasks.add(this::base_kor);
        tasks.add(this::base_fr);
        tasks.add(this::base_it);
        ThreadUtil.submit(tasks);
    }

    private void base_en() {
        try {
            String cnI18nFile = baseDir1 + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir1 + "/base_i18n_en.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.ENGLISH);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_ja() {
        try {
            String cnI18nFile = baseDir1 + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir1 + "/base_i18n_ja.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.JAPAN);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_tw() {
        try {
            String cnI18nFile = baseDir1 + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir1 + "/base_i18n_zh_TW.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.TAIWAN);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_kor() {
        try {
            String cnI18nFile = baseDir1 + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir1 + "/base_i18n_kor.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.KOREA);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_fr() {
        try {
            String cnI18nFile = baseDir1 + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir1 + "/base_i18n_fr.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.FRANCE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_de() {
        try {
            String cnI18nFile = baseDir1 + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir1 + "/base_i18n_de.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.GERMAN);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_it() {
        try {
            String cnI18nFile = baseDir1 + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir1 + "/base_i18n_it.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.ITALY);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
