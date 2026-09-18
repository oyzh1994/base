package cn.oyzh.pkg.util;

import cn.oyzh.common.file.FileNameUtil;
import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.log.JulLog;
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

    /**
     * 模块列表
     */
    private static final String[] mods = new String[]{
            "javafx.base",
            "javafx.controls",
            "javafx.graphics",
            "javafx.fxml",
            "javafx.media",
            "jfx.incubator.input",
            "jfx.incubator.richtext",
            "javafx.swing",
            "javafx.web",
    };

    /**
     * jfx模块转mvn模块
     *
     * @throws Exception 异常
     */
    public void jfxJModToMavenJar() throws Exception {
        String jdkPath = SystemUtil.javaHome();
        for (String mod : mods) {
            this.jfxJModToMvnJar(jdkPath, mod);
        }
        this.updateJfxPomFile();
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
        jarCf(modDir, mod);
        mvnInstall(modDir, mod);
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
        String jdkVer = getJdkVersion();
        Path path = Paths.get(repo, "/org/openjfx/javafx/" + jdkVer + "/javafx-" + jdkVer + ".pom");
        // 读取预设模版
        InputStream stream = ResourceUtil.getResourceAsStream("/javafx.pom");
        String content = FileUtil.readString(stream, Charset.defaultCharset());
        content = content.replace("${javafx_version}", jdkVer);
        // 覆盖文件
        FileUtil.writeString(content, path.toFile());

        //        if (!FileUtil.exists(path)) {
        //            JulLog.warn("mvn repository not exists!");
        //            return;
        //        }
        //        SAXReader reader = new SAXReader();
        //        Document doc = reader.read(path.toFile());
        //        Element profiles = doc.getRootElement().element("profiles");
        //        List<Element> elements = profiles.elements();
        //        for (Element element : elements) {
        //            String idText = element.attribute("id").getText();
        //            if (idText.equals("javafx.platform.windows.aarch64")) {
        //                JulLog.warn("windows aarch64 already exists!");
        //                return;
        //            }
        //        }
        //
        //        // win aarch64 profile
        //        Element profile = profiles.addElement("profile");
        //        // <id>
        //        profile.addElement("id").setText("javafx.platform.windows.aarch64");
        //
        //        // <activation>
        //        Element activation = profile.addElement("activation");
        //
        //        // <activation><os>
        //        Element os = activation.addElement("os");
        //        os.addElement("family").setText("windows");
        //        os.addElement("arch").setText("aarch64");
        //
        //        // <properties>
        //        Element properties = profile.addElement("properties");
        //        properties.addElement("javafx.platform").setText("aarch64");
        //
        //        try (FileWriter fw = new FileWriter("profile.xml")) {
        //            XMLWriter writer = new XMLWriter(fw, OutputFormat.createPrettyPrint());
        //            writer.write(doc);
        //        }
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
            if (file.isFile() && FileNameUtil.isJarType(file.getName())) {
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
    private static void jarCf(String modDir, String mod) throws Exception {
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
    private static void mvnInstall(String modDir, String mod) throws Exception {
        String[] mvnCmd = mvnCmd(Path.of(modDir).getParent().toString(), mod);
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
     * @param modDir 模块路径
     * @param mod    模块
     * @return 结果
     */
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
        String mvnExe = MvnUtil.mvnExec();
        if (StringUtil.isBlank(mvnExe)) {
            throw new RuntimeException("maven程序未找到!");
        }
        List<String> list = new ArrayList<>();
        list.add(mvnExe);
        list.add("install:install-file");
        list.add("-Dfile=" + FileNameUtil.concat(modDir, mod) + ".jar");
        list.add("-DgroupId=org.openjfx");
        list.add("-DartifactId=" + mod.replace(".", "-"));
        list.add("-Dpackaging=jar");
        list.add("-Dversion=" + getJdkVersion());
        list.add("-Dclassifier=win-aarch64");
        return list.toArray(new String[]{});
    }
}
