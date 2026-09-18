package cn.oyzh.pkg.util;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.system.RuntimeUtil;
import cn.oyzh.common.system.SystemUtil;
import cn.oyzh.common.thread.ProcessExecResult;

import java.util.ArrayList;
import java.util.List;

public class WinArmHandler {

    /**
     * 获取jdk版本
     *
     * @return 结果
     */
    public static String getJdkVersion() {
        Runtime.Version version = Runtime.version();
        if (version.update() == 0) {
            return version.feature() + "";
        }
        return version.feature() + "." + version.interim() + "." + version.update();
    }

    private static final String[] mods = new String[]{
            "javafx.base",
            "javafx.controls",
            "javafx.graphics",
            "javafx.fxml",
            "javafx.media",
            "javafx.web",
            "jfx.incubator.input",
            "jfx.incubator.richtext"
    };

    public void jfxJModToMavenJar() throws Exception {
        String jdkPath = SystemUtil.javaHome() + "/bin";
        for (String mod : mods) {
            this.jfxJModToMvnJar(jdkPath, mod);
        }
    }

    private void jfxJModToMvnJar(String jdkPath, String mod) throws Exception {
        String modDir = JModUtil.extract(mod + ".jmod", jdkPath);
        String[] mvnCmd = mvnCmd(modDir, mod);
        mvnInstall(mvnCmd);
    }

    private static void mvnInstall(String[] cmd) throws Exception {
        ProcessExecResult result = RuntimeUtil.execForResult(cmd);
        JulLog.info("mvn install result:{}", result);
        if (!result.isSuccess()) {
            JulLog.error("mvn install error:{}", result.getError());
            throw new Exception("mvn install error:" + result.getError());
        }
    }

    private static String[] mvnCmd(String modDir, String mod) {
        /*
         *
         * mvn install:install-file ^
         *   -Dfile=C:\jfx-jmods\javafx.base\javafx.base.jar ^
         *   -DgroupId=org.openjfx ^
         *   -DartifactId=javafx-base ^
         *   -Dversion=27-ea+24 ^
         *   -Dpackaging=jar ^
         *   -Dclassifier=win-aarch64
         */
        List<String> list = new ArrayList<>();
        list.add("mvn");
        list.add("install:install-file");
        list.add("-Dfile=" + modDir + mod + ".jar");
        list.add("-DgroupId=org.openjfx");
        list.add("-DartifactId=" + mod.replace(".", "-"));
        list.add("-Dpackaging=jar");
        list.add("-Dversion=" + getJdkVersion());
        list.add("-Dclassifier=win-aarch64");
        return list.toArray(new String[]{});
    }
}
