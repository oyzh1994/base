package cn.oyzh.pkg.config;

import cn.oyzh.common.util.StringUtil;
import cn.oyzh.pkg.ConfigMargeAble;
import cn.oyzh.pkg.comporess.CompressConfig;
import cn.oyzh.pkg.jar.JarConfig;
import cn.oyzh.pkg.jdeps.JDepsConfig;
import cn.oyzh.pkg.jlink.JLinkConfig;
import cn.oyzh.pkg.jpackage.JPackageConfig;
import cn.oyzh.pkg.jre.JreConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 打包配置
 *
 * @author oyzh
 * @since 2026-09-18
 */
public class PackConfig implements ConfigMargeAble<PackConfig> {

    /**
     * 目标目录
     */
    private String dest;

    /**
     * jlink后的jre目录
     */
    private String jlinkJre;

    /**
     * 最小化后的jre目录
     */
    private String minimizeJre;

    /**
     * jPackage输入目录
     */
    private String jPackageInput;

    /**
     * jar解压目录
     */
    private String jarUnDir;

//    /**
//     * 打包方式
//     * jpackage win打包的exe不能重启，mac打包的不能启动，不建议使用
//     * packr
//     */
//    @Deprecated
//    private String packMode = "jpackage";

    /**
     * 最小化后的主程序
     */
    private String minimizeManJar;

    /**
     * 主程序
     */
    private String mainJar;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用图标
     */
    private String appIcon;

    /**
     * 应用版本
     */
    private String appVersion;

    /**
     * 构建类型
     */
    private String buildType;

    /**
     * 最终压缩文件
     * 可能是以下类型
     * zip
     * tar.gz
     * AppImage
     */
    private File compressFile;

    /**
     * 打包用的jre路径
     */
    private String jrePath;

    /**
     * appImageRuntime目录
     */
    private String appImageRuntime;

    /**
     * 执行用的jdk路径
     */
    private String jdkPath;

    /**
     * 平台
     */
    private String platform;

    /**
     * jfx版本
     */
    private String jfxVersion;

    /**
     * jar配置
     */
    private JarConfig jarConfig;

    /**
     * jre配置
     */
    private JreConfig jreConfig;

    /**
     * jdeps配置
     */
    private JDepsConfig jDepsConfig;

    /**
     * jlink配置
     */
    private JLinkConfig jLinkConfig;

//    /**
//     * packr配置
//     */
//    @Deprecated
//    private PackrConfig packrConfig;

    /**
     * jPackage配置
     */
    private JPackageConfig jPackageConfig;

    /**
     * 压缩配置
     */
    private CompressConfig compressConfig;

    /**
     * 属性
     */
    private final Map<String, Object> properties = new HashMap<>();

    /**
     * 设置属性
     *
     * @param key   属性键
     * @param value 属性值
     */
    public void putProperty(String key, Object value) {
        this.properties.put(key, value);
    }

    /**
     * 获取属性
     *
     * @param key 属性键
     * @return 属性值
     */
    public Object getProperty(String key) {
        return this.properties.get(key);
    }

    /**
     * 临时文件
     */
    private final List<String> tempFiles = new ArrayList<>();

    /**
     * 添加临时文件
     *
     * @param tempFile 临时文件
     */
    public void addTempFile(String tempFile){
        this.tempFiles.add(tempFile);
    }

    /**
     * 获取临时文件
     *
     * @return 临时文件
     */
    public List<String> tempFiles() {
        return tempFiles;
    }

    /**
     * 获取主程序，优先返回最小化后的主程序
     *
     * @return 主程序
     */
    public String mainJar() {
        if (this.minimizeManJar != null) {
            return this.minimizeManJar;
        }
        return this.mainJar;
    }

    /**
     * 获取主程序文件名
     *
     * @return 主程序文件名
     */
    public String mainJarName() {
        String mainJar = this.mainJar();
        if (mainJar != null) {
            if (mainJar.contains("\\")) {
                return mainJar.substring(mainJar.lastIndexOf("\\") + 1);
            }
            if (mainJar.contains("/")) {
                return mainJar.substring(mainJar.lastIndexOf("/") + 1);
            }
        }
        return mainJar;
    }

    /**
     * 获取输出路径，优先返回最终压缩文件路径
     *
     * @return 输出路径
     */
    public String outPath() {
        if (this.compressFile != null) {
            return this.compressFile.getPath();
        }
        return this.dest;
    }

    /**
     * 获取应用版本，去除版本号前导的 v
     *
     * @return 应用版本
     */
    public String appVersion() {
        if (this.appVersion == null) {
            return null;
        }
        if (this.appVersion.toLowerCase().startsWith("v")) {
            return this.appVersion.substring(1);
        }
        return this.appVersion;
    }

    /**
     * 获取主应用版本，即去掉最后一段的主版本号
     *
     * @return 主应用版本
     */
    public String mainAppVersion() {
        String appVersion = this.appVersion();
        if (StringUtil.checkCountOccurrences(appVersion, '.', 3)) {
            return appVersion.substring(0, appVersion.lastIndexOf("."));
        }
        return appVersion;
    }

//    public boolean isParkByPackr() {
//        return this.packMode.equalsIgnoreCase("packr");
//    }

    /**
     * 获取jre路径，优先返回最小化后的jre，其次返回jlink后的jre
     *
     * @return jre路径
     */
    public String jrePath() {
        if (this.minimizeJre != null) {
            return this.minimizeJre;
        }
        if (this.jlinkJre != null) {
            return this.jlinkJre;
        }
        return this.jrePath;
    }

    /**
     * 是否macos平台
     *
     * @return 是返回 true，否则返回 false
     */
    public boolean isPlatformMacos() {
        return StringUtil.containsAnyIgnoreCase(this.platform, "macos");
    }

    /**
     * 是否windows平台
     *
     * @return 是返回 true，否则返回 false
     */
    public boolean isPlatformWindows() {
        return StringUtil.containsAnyIgnoreCase(this.platform, "win");
    }

    /**
     * 是否linux平台
     *
     * @return 是返回 true，否则返回 false
     */
    public boolean isPlatformLinux() {
        return StringUtil.containsAnyIgnoreCase(this.platform, "linux");
    }

    /**
     * 获取jfx版本
     *
     * @return jfx版本
     */
    public String getJfxVersion() {
        return jfxVersion;
    }

    /**
     * 设置jfx版本
     *
     * @param jfxVersion jfx版本
     */
    public void setJfxVersion(String jfxVersion) {
        this.jfxVersion = jfxVersion;
    }

    /**
     * 获取目标目录
     *
     * @return 目标目录
     */
    public String getDest() {
        return dest;
    }

    /**
     * 设置目标目录
     *
     * @param dest 目标目录
     */
    public void setDest(String dest) {
        this.dest = dest;
    }

    /**
     * 获取jlink后的jre目录
     *
     * @return jlink后的jre目录
     */
    public String getJlinkJre() {
        return jlinkJre;
    }

    /**
     * 设置jlink后的jre目录
     *
     * @param jlinkJre jlink后的jre目录
     */
    public void setJlinkJre(String jlinkJre) {
        this.jlinkJre = jlinkJre;
    }

    /**
     * 获取最小化后的jre目录
     *
     * @return 最小化后的jre目录
     */
    public String getMinimizeJre() {
        return minimizeJre;
    }

    /**
     * 设置最小化后的jre目录
     *
     * @param minimizeJre 最小化后的jre目录
     */
    public void setMinimizeJre(String minimizeJre) {
        this.minimizeJre = minimizeJre;
    }

    /**
     * 获取jPackage输入目录
     *
     * @return jPackage输入目录
     */
    public String getJPackageInput() {
        return jPackageInput;
    }

    /**
     * 设置jPackage输入目录
     *
     * @param jPackageInput jPackage输入目录
     */
    public void setJPackageInput(String jPackageInput) {
        this.jPackageInput = jPackageInput;
    }

    /**
     * 获取jar解压目录
     *
     * @return jar解压目录
     */
    public String getJarUnDir() {
        return jarUnDir;
    }

    /**
     * 设置jar解压目录
     *
     * @param jarUnDir jar解压目录
     */
    public void setJarUnDir(String jarUnDir) {
        this.jarUnDir = jarUnDir;
    }
//
//    public String getPackMode() {
//        return packMode;
//    }
//
//    public void setPackMode(String packMode) {
//        this.packMode = packMode;
//    }

    /**
     * 获取最小化后的主程序
     *
     * @return 最小化后的主程序
     */
    public String getMinimizeManJar() {
        return minimizeManJar;
    }

    /**
     * 设置最小化后的主程序
     *
     * @param minimizeManJar 最小化后的主程序
     */
    public void setMinimizeManJar(String minimizeManJar) {
        this.minimizeManJar = minimizeManJar;
    }

    /**
     * 获取主程序
     *
     * @return 主程序
     */
    public String getMainJar() {
        return mainJar;
    }

    /**
     * 设置主程序
     *
     * @param mainJar 主程序
     */
    public void setMainJar(String mainJar) {
        this.mainJar = mainJar;
    }

    /**
     * 获取应用名称
     *
     * @return 应用名称
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置应用名称
     *
     * @param appName 应用名称
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 获取应用图标
     *
     * @return 应用图标
     */
    public String getAppIcon() {
        return appIcon;
    }

    /**
     * 设置应用图标
     *
     * @param appIcon 应用图标
     */
    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    /**
     * 获取应用版本
     *
     * @return 应用版本
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * 设置应用版本
     *
     * @param appVersion 应用版本
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * 获取构建类型
     *
     * @return 构建类型
     */
    public String getBuildType() {
        return buildType;
    }

    /**
     * 设置构建类型
     *
     * @param buildType 构建类型
     */
    public void setBuildType(String buildType) {
        this.buildType = buildType;
    }

    /**
     * 获取最终压缩文件
     *
     * @return 最终压缩文件
     */
    public File getCompressFile() {
        return compressFile;
    }

    /**
     * 设置最终压缩文件
     *
     * @param compressFile 最终压缩文件
     */
    public void setCompressFile(File compressFile) {
        this.compressFile = compressFile;
    }

    /**
     * 获取打包用的jre路径
     *
     * @return 打包用的jre路径
     */
    public String getJrePath() {
        return jrePath;
    }

    /**
     * 设置打包用的jre路径
     *
     * @param jrePath 打包用的jre路径
     */
    public void setJrePath(String jrePath) {
        this.jrePath = jrePath;
    }

    /**
     * 获取执行用的jdk路径
     *
     * @return 执行用的jdk路径
     */
    public String getJdkPath() {
        return jdkPath;
    }

    /**
     * 设置执行用的jdk路径
     *
     * @param jdkPath 执行用的jdk路径
     */
    public void setJdkPath(String jdkPath) {
        this.jdkPath = jdkPath;
    }

    /**
     * 获取平台
     *
     * @return 平台
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 设置平台
     *
     * @param platform 平台
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * 获取jar配置
     *
     * @return jar配置
     */
    public JarConfig getJarConfig() {
        return jarConfig;
    }

    /**
     * 设置jar配置
     *
     * @param jarConfig jar配置
     */
    public void setJarConfig(JarConfig jarConfig) {
        this.jarConfig = jarConfig;
    }

    /**
     * 获取jre配置
     *
     * @return jre配置
     */
    public JreConfig getJreConfig() {
        return jreConfig;
    }

    /**
     * 设置jre配置
     *
     * @param jreConfig jre配置
     */
    public void setJreConfig(JreConfig jreConfig) {
        this.jreConfig = jreConfig;
    }

    /**
     * 获取jdeps配置
     *
     * @return jdeps配置
     */
    public JDepsConfig getJDepsConfig() {
        return jDepsConfig;
    }

    /**
     * 设置jdeps配置
     *
     * @param jDepsConfig jdeps配置
     */
    public void setJDepsConfig(JDepsConfig jDepsConfig) {
        this.jDepsConfig = jDepsConfig;
    }

    /**
     * 获取jlink配置
     *
     * @return jlink配置
     */
    public JLinkConfig getJLinkConfig() {
        return jLinkConfig;
    }

    /**
     * 设置jlink配置
     *
     * @param jLinkConfig jlink配置
     */
    public void setJLinkConfig(JLinkConfig jLinkConfig) {
        this.jLinkConfig = jLinkConfig;
    }

//    public PackrConfig getPackrConfig() {
//        return packrConfig;
//    }
//
//    public void setPackrConfig(PackrConfig packrConfig) {
//        this.packrConfig = packrConfig;
//    }

    /**
     * 获取jPackage配置
     *
     * @return jPackage配置
     */
    public JPackageConfig getjPackageConfig() {
        return jPackageConfig;
    }

    /**
     * 设置jPackage配置
     *
     * @param jPackageConfig jPackage配置
     */
    public void setjPackageConfig(JPackageConfig jPackageConfig) {
        this.jPackageConfig = jPackageConfig;
    }

    /**
     * 获取压缩配置
     *
     * @return 压缩配置
     */
    public CompressConfig getCompressConfig() {
        return compressConfig;
    }

    /**
     * 设置压缩配置
     *
     * @param compressConfig 压缩配置
     */
    public void setCompressConfig(CompressConfig compressConfig) {
        this.compressConfig = compressConfig;
    }

    /**
     * 获取属性
     *
     * @return 属性
     */
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * 获取appImageRuntime目录
     *
     * @return appImageRuntime目录
     */
    public String getAppImageRuntime() {
        return appImageRuntime;
    }

    /**
     * 设置appImageRuntime目录
     *
     * @param appImageRuntime appImageRuntime目录
     */
    public void setAppImageRuntime(String appImageRuntime) {
        this.appImageRuntime = appImageRuntime;
    }

    @Override
    public void marge(PackConfig config) {
        if (config == null) {
            return;
        }
        if (config.dest != null) {
            this.dest = config.dest;
        }
        if (config.appIcon != null) {
            this.appIcon = config.appIcon;
        }
        if (config.jdkPath != null) {
            this.jdkPath = config.jdkPath;
        }
        if (config.appName != null) {
            this.appName = config.appName;
        }
        if (config.mainJar != null) {
            this.mainJar = config.mainJar;
        }
        if (config.platform != null) {
            this.platform = config.platform;
        }
        if (config.appVersion != null) {
            this.appVersion = config.appVersion;
        }
        if (config.buildType != null) {
            this.buildType = config.buildType;
        }
        if (config.jfxVersion != null) {
            this.jfxVersion = config.jfxVersion;
        }
        if (config.appImageRuntime != null) {
            this.appImageRuntime = config.appImageRuntime;
        }
        if (this.jarConfig == null) {
            this.jarConfig = config.jarConfig;
        } else {
            this.jarConfig.marge(config.jarConfig);
        }
        if (this.jreConfig == null) {
            this.jreConfig = config.jreConfig;
        } else {
            this.jreConfig.marge(config.jreConfig);
        }
        if (this.jLinkConfig == null) {
            this.jLinkConfig = config.jLinkConfig;
        } else {
            this.jLinkConfig.marge(config.jLinkConfig);
        }
        if (this.jDepsConfig == null) {
            this.jDepsConfig = config.jDepsConfig;
        } else {
            this.jDepsConfig.marge(config.jDepsConfig);
        }
        if (this.compressConfig == null) {
            this.compressConfig = config.compressConfig;
        } else {
            this.compressConfig.marge(config.compressConfig);
        }
        if (this.jPackageConfig == null) {
            this.jPackageConfig = config.jPackageConfig;
        } else {
            this.jPackageConfig.marge(config.jPackageConfig);
        }
    }

    /**
     * 获取打包类型
     *
     * @return 打包类型
     */
    public String packageType() {
        return this.getjPackageConfig() == null ? null : this.getjPackageConfig().getType();
    }
}
