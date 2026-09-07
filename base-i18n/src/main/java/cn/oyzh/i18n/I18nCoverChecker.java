package cn.oyzh.i18n;

import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.file.PropertiesFile;
import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.CoverUtil;

import java.io.File;
import java.util.Set;

/**
 * javafx的覆盖管理器
 *
 * @author oyzh
 * @since 2026/09/06
 */
public class I18nCoverChecker {

    private String prefx;

    private String mainI18n;

    private String projectPath;

    public String getPrefx() {
        return prefx;
    }

    public void setPrefx(String prefx) {
        this.prefx = prefx;
    }

    public String getMainI18n() {
        return mainI18n;
    }

    public void setMainI18n(String mainI18n) {
        this.mainI18n = mainI18n;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    /**
     * i18n检查
     *
     * @throws Exception 异常
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
