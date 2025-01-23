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
public class I18nRedisGenerator {

    //    private final String skFile = "/Users/oyzh/Desktop/个人/baidu_trans.sk";
    private final String skFile = "C:\\Users\\Administrator\\Desktop\\baidu_trans.sk";

    private final String baseDir = "C:\\Users\\Administrator\\IdeaProjects\\easyredis\\src\\main\\resources";

    @Test
    public void test_redis() {
        List<Runnable> tasks = new ArrayList<>();
        tasks.add(this::redis_en);
        tasks.add(this::redis_ja);
        tasks.add(this::redis_de);
        tasks.add(this::redis_tw);
        tasks.add(this::redis_kor);
        tasks.add(this::redis_fr);
        tasks.add(this::redis_it);
        tasks.add(this::redis_yue);
        tasks.add(this::redis_wyw);
        ThreadUtil.submit(tasks);
    }

    private void redis_en() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_en.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.ENGLISH);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void redis_ja() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_ja.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.JAPAN);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void redis_tw() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_zh_TW.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.TAIWAN);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void redis_kor() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_kor.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.KOREA);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void redis_fr() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_fr.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.FRANCE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void redis_de() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_de.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.GERMAN);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void redis_it() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_it.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.ITALY);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void redis_yue() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_zh_YUE.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, I18nLocales.ZH_YUE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void redis_wyw() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_zh_WYW.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, I18nLocales.ZH_WYW);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
