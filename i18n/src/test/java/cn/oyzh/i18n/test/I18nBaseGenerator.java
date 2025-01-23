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
public class I18nBaseGenerator {

    //    private final String skFile = "/Users/oyzh/Desktop/个人/baidu_trans.sk";
    private final String skFile = "C:\\Users\\Administrator\\Desktop\\baidu_trans.sk";

    //    private final String baseDir = "/Users/oyzh/IdeaProjects/oyzh/base/i18n/src/main/resources/";
    private final String baseDir = "C:\\Users\\Administrator\\IdeaProjects\\base\\i18n\\src\\main\\resources";

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
        tasks.add(this::base_yue);
        tasks.add(this::base_wyw);
        tasks.add(this::base_ru);
        tasks.add(this::base_dan);
        ThreadUtil.submit(tasks);
    }

    private void base_en() {
        try {
            String cnI18nFile = baseDir + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/base_i18n_en.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.ENGLISH);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_ja() {
        try {
            String cnI18nFile = baseDir + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/base_i18n_ja.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.JAPAN);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_tw() {
        try {
            String cnI18nFile = baseDir + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/base_i18n_zh_TW.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.TAIWAN);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_kor() {
        try {
            String cnI18nFile = baseDir + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/base_i18n_kor.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.KOREA);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_fr() {
        try {
            String cnI18nFile = baseDir + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/base_i18n_fr.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.FRANCE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_de() {
        try {
            String cnI18nFile = baseDir + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/base_i18n_de.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.GERMAN);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_it() {
        try {
            String cnI18nFile = baseDir + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/base_i18n_it.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.ITALY);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_yue() {
        try {
            String cnI18nFile = baseDir + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/base_i18n_zh_YUE.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, I18nLocales.ZH_YUE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_wyw() {
        try {
            String cnI18nFile = baseDir + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/base_i18n_zh_WYW.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, I18nLocales.ZH_WYW);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_ru() {
        try {
            String cnI18nFile = baseDir + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/base_i18n_ru.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, I18nLocales.RU);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void base_dan() {
        try {
            String cnI18nFile = baseDir + "/base_i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/base_i18n_dan.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, I18nLocales.DAN);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
