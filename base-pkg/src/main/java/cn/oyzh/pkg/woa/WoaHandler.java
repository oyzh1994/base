package cn.oyzh.pkg.woa;

import cn.oyzh.common.file.FileNameUtil;
import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.system.OSUtil;
import cn.oyzh.common.system.RuntimeUtil;
import cn.oyzh.common.system.SystemUtil;
import cn.oyzh.common.thread.ProcessExecResult;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.common.util.IOUtil;
import cn.oyzh.common.util.ResourceUtil;
import cn.oyzh.common.util.StringUtil;
import cn.oyzh.pkg.PackOrder;
import cn.oyzh.pkg.PreHandler;
import cn.oyzh.pkg.config.PackConfig;
import cn.oyzh.pkg.util.JModUtil;
import cn.oyzh.pkg.util.MvnUtil;
import cn.oyzh.pkg.util.PkgUtil;

import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * windows arm处理器
 *
 * @author oyzh
 * @since 2026/09/18
 */
public class WoaHandler implements PreHandler {

    private int order = PackOrder.ORDER_P10;

    @Override
    public int order() {
        return order;
    }

    @Override
    public void order(int order) {
        this.order = order;
    }

    @Override
    public String name() {
        return "WOA处理";
    }

    @Override
    public void handle(PackConfig packConfig) throws Exception {
        this.jfxVersion = packConfig.getJfxVersion();
        this.run();
    }

    private String jfxVersion;

    public String getJfxVersion() {
        return jfxVersion;
    }

    public void setJfxVersion(String jfxVersion) {
        this.jfxVersion = jfxVersion;
    }

    private String jfxVersion() {
        if (StringUtil.isBlank(this.jfxVersion)) {
            return SystemUtil.getJdkVersion();
        }
        return this.jfxVersion;
    }

    /**
     * 模块列表
     */
    private static final String[] mods = new String[]{
            "javafx.graphics",
            "javafx.media",
            "javafx.web"
    };

    /**
     * 业务入口
     *
     * @throws Exception 异常
     */
    public void run() throws Exception {
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
            this.updateJfxJarFile(jdkPath, mod);
        }
        this.clean(jdkPath);
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
        String content = """
                <?xml version="1.0" encoding="UTF-8"?>
                <project xmlns="http://maven.apache.org/POM/4.0.0"
                         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
                    <modelVersion>4.0.0</modelVersion>
                    <groupId>org.openjfx</groupId>
                    <artifactId>javafx</artifactId>
                    <version>${javafx_version}</version>
                    <packaging>pom</packaging>
                    <name>openjfx</name>
                    <properties>
                        <javafx.version>${javafx_version}</javafx.version>
                    </properties>
                    <profiles>
                        <profile>
                            <id>javafx.platform.windows.aarch64</id>
                            <activation>
                                <os>
                                    <family>windows</family>
                                    <arch>aarch64</arch>
                                </os>
                            </activation>
                            <properties>
                                <javafx.platform>win</javafx.platform>
                            </properties>
                        </profile>
                    </profiles>
                </project>
                """;
        // 更新模版
        content = content.replace("${javafx_version}", jfxVer);
        // 覆盖文件
        FileUtil.writeString(content, path.toFile());
    }

    /**
     * 更新jfx的jar文件
     *
     * @param jdkPath jdk路径
     * @param mod     模块
     * @throws Exception 异常
     */
    private void updateJfxJarFile(String jdkPath, String mod) throws Exception {
        // 检查mvn仓库
        String repo = MvnUtil.getLocalRepository();
        if (!FileUtil.exists(repo)) {
            JulLog.warn("mvn repository not exists!");
            return;
        }
        // 解压jmod
        String modDir = JModUtil.extract(mod + ".jmod", jdkPath);
        String name = mod.replace(".", "-");
        Path lib = modDir == null ? null : Path.of(modDir, "lib");
        // 不存在模块路径，则从资源目录获取
        if (lib == null || !Files.exists(lib)) {
            JulLog.warn("mod:{} lib is null, find resources lib....", mod);
            lib = Path.of(SystemUtil.tmpdir(), "_jfx_win_arm_libs");
            String libDir = "/jfx/libs/" + name;
            List<String> list = ResourceUtil.listFiles(libDir);
            if (CollectionUtil.isEmpty(list)) {
                JulLog.warn("mod:{} lib is null, ignore....", mod);
                return;
            }
            // 复制文件
            for (String s : list) {
                InputStream stream = ResourceUtil.getResourceAsStream(libDir + "/" + s);
                IOUtil.saveToFile(stream, FileNameUtil.concat(lib.toString(), s));
                IOUtil.close(stream);
            }
        }
        String jfxVer = this.jfxVersion();
        // 获取模块路径
        Path path = Paths.get(repo, "/org/openjfx/" + name + "/" + jfxVer + "/" + name + "-" + jfxVer + "-win.jar");
        // 解压jar
        String jarDir = this.jarXf(path.toString());
        // 过滤器
        FileFilter filter = f -> FileNameUtil.isDllType(FileNameUtil.extName(f));
        // 删除旧lib
        File[] libs1 = FileUtil.ls(jarDir, filter);
        for (File file : libs1) {
            FileUtil.del(file);
        }
        // 复制新lib
        File[] libs2 = FileUtil.ls(lib.toString(), filter);
        for (File file : libs2) {
            FileUtil.copy(file, new File(jarDir, file.getName()));
        }
        // 压缩jar
        this.jarCf(path.toString(), jarDir);
        // 删除目录
        FileUtil.cleanDir(jarDir);
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
            if (file.isDirectory() && file.getName().contains(".")) {
                FileUtil.cleanDir(file);
                FileUtil.del(file);
                JulLog.info("file:{} is deleted.", file);
            }
        }
    }

    /**
     * jar打包
     *
     * @param jarPath jar路径
     * @param jarFile jar文件路径
     * @throws Exception 异常
     */
    private void jarCf(String jarPath, String jarFile) throws Exception {
        String[] jarCmd = PkgUtil.getJarCfCMD(jarPath, List.of(jarFile));
        jarCmd = PkgUtil.getJDKExecCMD(SystemUtil.javaHome(), jarCmd);
        ProcessExecResult result = RuntimeUtil.execForResult(jarCmd);
        JulLog.info("jar cf:{}", result);
        if (!result.isSuccess()) {
            JulLog.error("jar cf error:{}", result.getError());
            throw new Exception("jar cf error:" + result.getError());
        }
    }

    /**
     * jar解压
     *
     * @param jarPath jar文件
     * @throws Exception 异常
     */
    private String jarXf(String jarPath) throws Exception {
        Path p = Path.of(jarPath);
        String pPath = p.getParent().toString();
        Path fPath = Path.of(pPath, p.toFile().getName().substring(0, p.toFile().getName().lastIndexOf(".")));
        if (FileUtil.exists(fPath)) {
            FileUtil.cleanDir(fPath);
        }
        FileUtil.mkdir(fPath);
        String[] jarCmd = PkgUtil.getJarXfCMD(jarPath);
        jarCmd = PkgUtil.getJDKExecCMD(SystemUtil.javaHome(), jarCmd);
        ProcessExecResult result = RuntimeUtil.execForResult(jarCmd, null, fPath.toFile());
        JulLog.info("jar xf:{}", result);
        if (!result.isSuccess()) {
            JulLog.error("jar xf error:{}", result.getError());
            throw new Exception("jar xf error:" + result.getError());
        }
        return fPath.toString();
    }
}
