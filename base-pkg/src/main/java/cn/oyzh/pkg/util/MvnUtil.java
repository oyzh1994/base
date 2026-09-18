package cn.oyzh.pkg.util;

import cn.oyzh.common.system.OSUtil;
import cn.oyzh.common.system.SystemUtil;
import cn.oyzh.common.util.StringUtil;
import org.apache.maven.settings.Settings;
import org.apache.maven.settings.building.DefaultSettingsBuilderFactory;
import org.apache.maven.settings.building.DefaultSettingsBuildingRequest;
import org.apache.maven.settings.building.SettingsBuilder;
import org.apache.maven.settings.building.SettingsBuildingRequest;
import org.apache.maven.settings.building.SettingsBuildingResult;

/**
 * mvn工具
 *
 * @author oyzh
 * @since 2026-09-18
 */
public class MvnUtil {

    /**
     * 获取本地仓库地址
     *
     * @return 结果
     * @throws Exception 异常
     */
    public static String getLocalRepository() throws Exception {
        SettingsBuilder settingsBuilder = new DefaultSettingsBuilderFactory().newInstance();
        SettingsBuildingRequest request = new DefaultSettingsBuildingRequest();
        request.setUserSettingsFile(new java.io.File(System.getProperty("user.home"), ".m2/settings.xml"));
        request.setGlobalSettingsFile(new java.io.File(System.getenv("MAVEN_HOME"), "conf/settings.xml"));
        SettingsBuildingResult result = settingsBuilder.build(request);
        Settings settings = result.getEffectiveSettings();
        String localRepo = settings.getLocalRepository();
        if (localRepo == null) {
            localRepo = System.getProperty("user.home") + "/.m2/repository";
        }
        return localRepo;
    }

    /**
     * 获取mvn exec路径
     *
     * @return 结果
     */
    public static String mvnExec() {
        String mvnHome = SystemUtil.mvnHomeEnv();
        if (StringUtil.isBlank(mvnHome)) {
            throw new RuntimeException("maven主目录未找到!");
        }
        String mvnExe;
        if (OSUtil.isLinux()) {
            mvnExe = mvnHome + "/bin/mvn.sh";
        } else if (OSUtil.isWindows()) {
            mvnExe = mvnHome + "/bin/mvn.cmd";
        } else {
            mvnExe = mvnHome + "/bin/mvn";
        }
        return mvnExe;
    }
}
