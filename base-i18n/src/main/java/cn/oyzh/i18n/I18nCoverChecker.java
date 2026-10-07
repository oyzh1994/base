package cn.oyzh.i18n;

import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.file.PropertiesFile;
import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.CoverUtil;

import java.io.File;
import java.util.Set;

/**
 * i18n覆盖检查器，用于校验各语言资源文件的键是否与主体资源文件完全一致
 *
 * @author oyzh
 * @since 2026/09/06
 */
public class I18nCoverChecker {

    /**
     * 资源文件前缀
     */
    private String prefx;

    /**
     * 主体i18n资源文件名称
     */
    private String mainI18n;

    /**
     * 项目路径
     */
    private String projectPath;

    /**
     * 获取资源文件前缀
     *
     * @return 资源文件前缀
     */
    public String getPrefx() {
        return prefx;
    }

    /**
     * 设置资源文件前缀
     *
     * @param prefx 资源文件前缀
     */
    public void setPrefx(String prefx) {
        this.prefx = prefx;
    }

    /**
     * 获取主体i18n资源文件名称
     *
     * @return 主体i18n资源文件名称
     */
    public String getMainI18n() {
        return mainI18n;
    }

    /**
     * 设置主体i18n资源文件名称
     *
     * @param mainI18n 主体i18n资源文件名称
     */
    public void setMainI18n(String mainI18n) {
        this.mainI18n = mainI18n;
    }

    /**
     * 获取项目路径
     *
     * @return 项目路径
     */
    public String getProjectPath() {
        return projectPath;
    }

    /**
     * 设置项目路径
     *
     * @param projectPath 项目路径
     */
    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    /**
     * 执行i18n检查，逐一比对各语言资源文件与主体资源文件的键集合，不一致时结束检查
     *
     * @throws Exception 检查不通过时抛出异常
     */
    public void i18Check() throws Exception {
        JulLog.info("i18n check start");
        String mainI18nFile = this.prefx + this.mainI18n + ".properties";
        PropertiesFile mainFile = new PropertiesFile(mainI18nFile);
        Set<?> keys = mainFile.keySet();
        String p = CoverUtil.getClassesPath(this.projectPath);
        File[] files = FileUtil.ls(p, f -> f.isFile() && f.getName().startsWith(this.prefx) && f.getName().endsWith(".properties"));
        for (File file : files) {
            PropertiesFile propertiesFile = new PropertiesFile(file.getName());
            Set<?> keySet = propertiesFile.keySet();
            if (keys.size() != keySet.size() || !keys.containsAll(keySet)) {
                throw new RuntimeException("check i18n:" + file.getName() + " fail");
            }
        }
        JulLog.info("i18n check finish");
    }


}
