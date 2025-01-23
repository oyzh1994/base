package cn.oyzh.i18n.test;

import cn.oyzh.common.thread.ThreadUtil;
import cn.oyzh.i18n.I18nGenerator;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author oyzh
 * @since 2025-01-23
 */
public class I18nZookeeperGenerator {

    //    private final String skFile = "/Users/oyzh/Desktop/个人/baidu_trans.sk";
    private final String skFile = "C:\\Users\\Administrator\\Desktop\\baidu_trans.sk";

    private final String baseDir = "C:\\Users\\Administrator\\IdeaProjects\\easyzk\\src\\main\\resources";

    @Test
    public void test_zk() {
        List<Runnable> tasks = new ArrayList<>();
        tasks.add(this::zk_en);
        tasks.add(this::zk_ja);
        tasks.add(this::zk_de);
        tasks.add(this::zk_tw);
        tasks.add(this::zk_kor);
        tasks.add(this::zk_fr);
        tasks.add(this::zk_it);
        ThreadUtil.submit(tasks);
    }

    private void zk_en() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_en.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.ENGLISH);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void zk_ja() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_ja.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.JAPAN);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void zk_tw() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_zh_TW.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.TAIWAN);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void zk_kor() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_kor.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.KOREA);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void zk_fr() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_fr.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.FRANCE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void zk_de() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_de.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.GERMAN);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void zk_it() {
        try {
            String cnI18nFile = baseDir + "/i18n_zh_CN.properties";
            String targetI18nFile = baseDir + "/i18n_it.properties";
            I18nGenerator.i18nTranslate(skFile, cnI18nFile, targetI18nFile, Locale.ITALY);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
