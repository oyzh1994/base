package cn.oyzh.pkg.util;

import cn.oyzh.common.file.FileNameUtil;
import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.system.OSUtil;
import cn.oyzh.common.system.RuntimeUtil;
import cn.oyzh.common.system.SystemUtil;
import cn.oyzh.common.thread.ProcessExecResult;
import cn.oyzh.common.util.ResourceUtil;
import cn.oyzh.common.util.StringUtil;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * windows arm处理器
 *
 * @author oyzh
 * @since 2026/09/18
 */
public class WinArmHandler {

    private String jfxVersion;

    public String getJfxVersion() {
        return jfxVersion;
    }

    public void setJfxVersion(String jfxVersion) {
        this.jfxVersion = jfxVersion;
    }

    private String jfxVersion() {
        if (this.jfxVersion == null) {
            return getJdkVersion();
        }
        return this.jfxVersion;
    }

    /**
     * 模块列表
     */
    private static final String[] mods = new String[]{
            "javafx.base",
            "javafx.controls",
            "javafx.graphics",
            "javafx.fxml",
            "javafx.media",
            "javafx.swing",
            "javafx.web",
            "jfx.incubator.input",
            "jfx.incubator.richtext",
    };

    /**
     * jfx模块转mvn模块
     *
     * @throws Exception 异常
     */
    public void jfxJModToMavenJar() throws Exception {
        if (!(OSUtil.isWindows() && OSUtil.isAarch64())) {
            JulLog.warn("only run in windows on arm!");
            return;
        }
        String jdkPath = SystemUtil.javaHome();
        // 仅在开发环境更新jfx的pom
        if (!SystemUtil.isCIEnv()) {
            this.updateJfxPomFile();
        }
        for (String mod : mods) {
            this.jfxJModToMvnJar(jdkPath, mod);
        }
        this.clean(jdkPath);
    }

    /**
     * jfx模块转mvn模块
     *
     * @param jdkPath jdk路径
     * @param mod     模块
     * @throws Exception 异常
     */
    private void jfxJModToMvnJar(String jdkPath, String mod) throws Exception {
        String modDir = JModUtil.extract(mod + ".jmod", jdkPath);
        if (modDir == null) {
            JulLog.warn("mod:{} modDir is null, ignore....", mod);
            return;
        }
        this.jarCf(modDir, mod);
        this.mvnInstall(modDir, mod);
    }

    /**
     * 更新jfx的pom文件
     *
     * @throws Exception 异常
     */
    private void updateJfxPomFile() throws Exception {
        String repo = MvnUtil.getLocalRepository();
        if (!FileUtil.exists(repo)) {
            JulLog.warn("mvn repository not exists!");
            return;
        }
        String jfxVer = this.jfxVersion();
        // 获取模块路径
        Path path = Paths.get(repo, "/org/openjfx/javafx/" + jfxVer + "/javafx-" + jfxVer + ".pom");
        // 读取预设模版
        InputStream stream = ResourceUtil.getResourceAsStream("/jfx/javafx.pom");
        String content = FileUtil.readString(stream, Charset.defaultCharset());
        content = content.replace("${javafx_version}", jfxVer);
        // 覆盖文件
        FileUtil.writeString(content, path.toFile());
    }

    /**
     * 复制jfx的pom文件
     *
     * @param mod 模块
     * @return 文件
     */
    private String copyJfxPomFile(String mod) {
        String jfxVer = this.jfxVersion();
        String name = mod.replace(".", "-");
        // 获取模块路径
        Path path = Paths.get(SystemUtil.tmpdir(), name + "-" + jfxVer + ".pom");
        // 读取预设模版
        InputStream stream = ResourceUtil.getResourceAsStream("/jfx/" + name + ".pom");
        String content = FileUtil.readString(stream, Charset.defaultCharset());
        content = content.replace("${javafx_version}", jfxVer);
        // 固定为win-aarch64
        if (SystemUtil.isCIEnv()) {
            content = content.replace("${javafx.platform}", "win-aarch64");
        }
        // 覆盖文件
        FileUtil.writeString(content, path.toFile());
        return path.toString();
    }

    /**
     * 清理
     *
     * @param jdkPath jdk路径
     */
    private void clean(String jdkPath) {
        Path p = Path.of(jdkPath, "jmods");
        if (!Files.exists(p)) {
            return;
        }
        File[] files = FileUtil.ls(p.toFile());
        for (File file : files) {
            if (file.isFile() && FileNameUtil.isJarType(FileNameUtil.extName(file))) {
                FileUtil.del(file);
            } else if (file.isDirectory() && file.getName().contains(".")) {
                FileUtil.cleanDir(file);
                FileUtil.del(file);
            }
        }
    }

    /**
     * jar打包
     *
     * @param modDir 模块路径
     * @param mod    模块
     * @throws Exception 异常
     */
    private void jarCf(String modDir, String mod) throws Exception {
        Path path1 = Paths.get(modDir, "classes");
        Path path2 = Paths.get(modDir, "lib");
        Path jarPath = Paths.get(Paths.get(modDir).getParent().toString(), mod + ".jar");
        List<String> files = new ArrayList<>();
        if (Files.exists(path1)) {
            files.add(path1.toString());
        }
        if (Files.exists(path2)) {
            files.add(path2.toString());
        }
        String[] jarCmd = PkgUtil.getJarCMD(jarPath.toString(), files);
        jarCmd = PkgUtil.getJDKExecCMD(SystemUtil.javaHome(), jarCmd);
        ProcessExecResult result = RuntimeUtil.execForResult(jarCmd);
        JulLog.info("jar cf:{}", result);
        if (!result.isSuccess()) {
            JulLog.error("jar cf error:{}", result.getError());
            throw new Exception("jar cf error:" + result.getError());
        }
    }

    /**
     * mvn安装jar
     *
     * @param modDir 模块路径
     * @param mod    模块
     * @throws Exception 异常
     */
    private void mvnInstall(String modDir, String mod) throws Exception {
        // 处理pom文件
        String pomFile = this.copyJfxPomFile(mod);
        String[] mvnCmd = this.mvnCmd(Path.of(modDir).getParent().toString(), mod, pomFile);
        ProcessExecResult result = RuntimeUtil.execForResult(mvnCmd);
        JulLog.info("mvn install result:{}", result);
        if (!result.isSuccess()) {
            JulLog.error("mvn install error:{}", result.getError());
            throw new Exception("mvn install error:" + result.getError());
        }
    }

    /**
     * 获取mvn命令
     *
     * @param modDir  模块路径
     * @param mod     模块
     * @param pomFile pom文件
     * @return 结果
     */
    private String[] mvnCmd(String modDir, String mod, String pomFile) {
        /*
         *
         * mvn install:install-file ^
         *   -Dfile=C:\jfx-jmods\javafx.base\javafx.base.jar ^
         *   -DpomFile=C:\jfx-jmods\javafx.base\javafx.base.pom ^
         *   -DgroupId=org.openjfx ^
         *   -DartifactId=javafx-base ^
         *   -Dversion=27-ea+24 ^
         *   -Dpackaging=jar ^
         *   -Dclassifier=win-aarch64
         */
        String mvnExe = MvnUtil.mvnExec();
        if (StringUtil.isBlank(mvnExe)) {
            throw new RuntimeException("maven程序未找到!");
        }
        List<String> list = new ArrayList<>();
        list.add(mvnExe);
        list.add("install:install-file");
        list.add("-Dfile=" + FileNameUtil.concat(modDir, mod) + ".jar");
        list.add("-DpomFile=" + pomFile);
        list.add("-DgroupId=org.openjfx");
        list.add("-DartifactId=" + mod.replace(".", "-"));
        list.add("-Dpackaging=jar");
        list.add("-Dversion=" + this.jfxVersion());
        list.add("-Dclassifier=win-aarch64");
        return list.toArray(new String[]{});
    }

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
}
