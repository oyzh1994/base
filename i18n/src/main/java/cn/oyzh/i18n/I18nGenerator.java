package cn.oyzh.i18n;

import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.json.JSONArray;
import cn.oyzh.common.json.JSONObject;
import cn.oyzh.common.json.JSONUtil;
import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.StringUtil;
import cn.oyzh.i18n.baidu.TransApi;
import cn.oyzh.i18n.baidu.TransUtil;
import lombok.experimental.UtilityClass;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * @author oyzh
 * @since 2025-01-23
 */
@UtilityClass
public class I18nGenerator {

    /**
     * 执行i18n翻译
     *
     * @param skFilePath     百度的app和密钥信息，第一行appid，第二行密钥
     * @param cnI18nFile     中文i18n文件
     * @param targetI18nFile 目标i18n文件
     * @param targetLocale   目标语言
     * @return 完成翻译的键数量
     * @throws IOException 异常
     */
    public static int i18nTranslate(String skFilePath, String cnI18nFile, String targetI18nFile, Locale targetLocale) throws IOException {
        List<String> list = FileUtil.readLines(new FileInputStream(skFilePath), StandardCharsets.UTF_8);
        if (list.size() < 2) {
            throw new RuntimeException("invalid baidu app!");
        }
        if (!FileUtil.exist(cnI18nFile)) {
            throw new RuntimeException("invalid file : " + cnI18nFile);
        }
//        if (!FileUtil.exist(targetI18nFile)) {
//            throw new RuntimeException("invalid file : " + targetI18nFile);
//        }
        // 初始化百度信息
        String appid = list.getFirst();
        String securityKey = list.get(1);
        TransApi api = new TransApi(appid, securityKey);

        // 中文属性
        Properties cnProp = new Properties();
        cnProp.load(new FileInputStream(cnI18nFile));

        // 目标属性
        Properties targetProp = new Properties();
        // 目标文件不存在就创建
        if (!FileUtil.exist(targetI18nFile)) {
            FileUtil.touch(targetI18nFile);
        }
        targetProp.load(new FileInputStream(targetI18nFile));

        int count = 0;
        // 遍历中文key
        for (Object key : cnProp.keySet()) {
            try {
                // 检查是否为空
                String source = (String) cnProp.get(key);
                if (StringUtil.isBlank(source)) {
                    continue;
                }
                // 检查是否不为空
                String target = (String) targetProp.get(key);
                if (StringUtil.isNotBlank(target)) {
                    continue;
                }
                // 获取名称
                String targetName = TransUtil.localeToName(targetLocale);
                // 执行翻译
                String result = api.trans(source, "zh", targetName);
                JulLog.info("translate result:{}", result);
                // 解析数据
                JSONObject object = JSONUtil.parseObject(result);
                if (object.containsKey("trans_result")) {
                    JSONArray array = object.getJSONArray("trans_result");
                    String dst = array.getJSONObject(0).getString("dst");
                    JulLog.info("translate:{} key:{}={}={} count={}", targetLocale.getLanguage(), key, source, dst, count++);
                    targetProp.setProperty((String) key, dst);
                } else {
//                    targetProp.setProperty((String) key, source);
                    JulLog.warn("translate:{} key:{}={} fail count={}", targetLocale.getLanguage(), key, source, count);
                }
                // 存储数据
                if (count % 10 == 0) {
                    targetProp.store(new FileOutputStream(targetI18nFile), null);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // 存储数据
        targetProp.store(new FileOutputStream(targetI18nFile), null);
        return count;
    }

    /**
     * 执行i18n修正
     *
     * @param cnI18nFile     中文i18n文件
     * @param targetI18nFile 目标i18n文件
     * @param targetLocale   目标语言
     * @throws IOException 异常
     */
    public static void i18nCorrection(String cnI18nFile, String targetI18nFile, Locale targetLocale) throws IOException {
        if (!FileUtil.exist(cnI18nFile)) {
            throw new RuntimeException("invalid file : " + cnI18nFile);
        }
        if (!FileUtil.exist(targetI18nFile)) {
            throw new RuntimeException("invalid file : " + targetI18nFile);
        }
        // 中文属性
        Properties cnProp = new Properties();
        cnProp.load(new FileInputStream(cnI18nFile));

        // 目标属性
        Properties targetProp = new Properties();
        targetProp.load(new FileInputStream(targetI18nFile));

        // 遍历中文key
        for (Object key : cnProp.keySet()) {
            try {
                // 检查是否为空
                String source = (String) cnProp.get(key);
                if (StringUtil.isBlank(source)) {
                    continue;
                }
                // 检查是否不为空
                String target = (String) targetProp.get(key);
                if (StringUtil.isBlank(target)) {
                    continue;
                }
                // 句号
                if (!source.contains("。") && target.contains("。")) {
                    target = target.replaceAll("。", ".");
                    JulLog.info("correction:{} key:{}={}={}", targetLocale.getLanguage(), key, source, target);
                    targetProp.setProperty((String) key, target);
                }
                // 逗号
                if (!source.contains("，") && target.contains("，")) {
                    target = target.replaceAll("，", ",");
                    JulLog.info("correction:{} key:{}={}={}", targetLocale.getLanguage(), key, source, target);
                    targetProp.setProperty((String) key, target);
                }
                // 冒号1
                if (!source.contains("：") && target.contains("：")) {
                    target = target.replaceAll("：", ":");
                    JulLog.info("correction:{} key:{}={}={}", targetLocale.getLanguage(), key, source, target);
                    targetProp.setProperty((String) key, target);
                }
                // 冒号2
                if (!source.contains(": ") && target.contains(": ")) {
                    target = target.replaceAll(": ", ":");
                    JulLog.info("correction:{} key:{}={}={}", targetLocale.getLanguage(), key, source, target);
                    targetProp.setProperty((String) key, target);
                }
                // 除号1
                if (!source.contains(" / ") && target.contains(" / ")) {
                    target = target.replaceAll(" / ", "/");
                    JulLog.info("correction:{} key:{}={}={}", targetLocale.getLanguage(), key, source, target);
                    targetProp.setProperty((String) key, target);
                }
                // 分号1
                if (!source.contains("; ") && target.contains("; ")) {
                    target = target.replaceAll("; ", ";");
                    JulLog.info("correction:{} key:{}={}={}", targetLocale.getLanguage(), key, source, target);
                    targetProp.setProperty((String) key, target);
                }
                // 分号2
                if (!source.contains("； ") && target.contains("； ")) {
                    target = target.replaceAll("； ", ";");
                    JulLog.info("correction:{} key:{}={}={}", targetLocale.getLanguage(), key, source, target);
                    targetProp.setProperty((String) key, target);
                }
                // 分号3
                if (!source.contains("；") && target.contains("；")) {
                    target = target.replaceAll("；", ";");
                    JulLog.info("correction:{} key:{}={}={}", targetLocale.getLanguage(), key, source, target);
                    targetProp.setProperty((String) key, target);
                }
                // 特殊情况1
                if (!source.contains("\\") && target.contains("\\")) {
                    target = target.replaceAll("\\\\", "");
                    JulLog.info("correction:{} key:{}={}={}", targetLocale.getLanguage(), key, source, target);
                    targetProp.setProperty((String) key, target);
                }
                // 特殊情况2
                if (!source.contains("\\:") && target.contains("\\:")) {
                    target = target.replaceAll("\\\\:", ":");
                    JulLog.info("correction:{} key:{}={}={}", targetLocale.getLanguage(), key, source, target);
                    targetProp.setProperty((String) key, target);
                }
                // 特殊情况3
                if (!source.contains("\\!") && target.contains("\\!")) {
                    target = target.replaceAll("\\\\!", "!");
                    JulLog.info("correction:{} key:{}={}={}", targetLocale.getLanguage(), key, source, target);
                    targetProp.setProperty((String) key, target);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // 存储数据
        targetProp.store(new FileOutputStream(targetI18nFile), null);
    }
}
