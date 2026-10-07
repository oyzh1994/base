package cn.oyzh.pkg.jpackage;


import cn.oyzh.common.util.BooleanUtil;
import cn.oyzh.pkg.ConfigMargeAble;

import java.io.File;
import java.util.Set;

/**
 * JPackage配置
 *
 * @author oyzh
 * @since 2026-09-18
 */
public class JPackageConfig implements ConfigMargeAble<JPackageConfig> {

    /**
     * 程序名
     */
    private String name;

    /**
     * 打包类型
     */
    private String type;

    /**
     * 目标目录
     */
    private String dest;

    /**
     * 输入目录
     */
    private String input;

    /**
     * 图标文件
     */
    private String icon;

    /**
     * 作者
     */
    private String vendor;

    /**
     * 主jar
     */
    private String mainJar;

    /**
     * app版本
     */
    private String appVersion;

    /**
     * 版权信息
     */
    private String copyright;

    /**
     * 程序描述
     */
    private String description;

    /**
     * 运行期jre目录
     */
    private String runtimeImage;

    /**
     * 详细信息
     */
    private Boolean verbose;

    /**
     * vm参数
     */
    private Set<String> javaOptions;

    /**
     * 是否创建开始菜单、仅windows
     */
    private Boolean winMenu;

    /**
     * 是否创建桌面图标、仅windows
     */
    private Boolean winShortcut;

    /**
     * 是否可选安装目录、仅windows
     */
    private Boolean winDirChooser;

    /**
     * mac程序唯一id、仅macos
     */
    private String macPackageIdentifier;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 获取目标目录的父目录
     *
     * @return 父目录
     */
    public String destParent() {
        return new File(dest).getParent();
    }

    /**
     * 获取程序名
     *
     * @return 程序名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置程序名
     *
     * @param name 程序名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取打包类型
     *
     * @return 打包类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置打包类型
     *
     * @param type 打包类型
     */
    public void setType(String type) {
        this.type = type;
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
     * 获取输入目录
     *
     * @return 输入目录
     */
    public String getInput() {
        return input;
    }

    /**
     * 设置输入目录
     *
     * @param input 输入目录
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * 获取图标文件
     *
     * @return 图标文件
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标文件
     *
     * @param icon 图标文件
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取作者
     *
     * @return 作者
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * 设置作者
     *
     * @param vendor 作者
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * 获取主jar
     *
     * @return 主jar
     */
    public String getMainJar() {
        return mainJar;
    }

    /**
     * 设置主jar
     *
     * @param mainJar 主jar
     */
    public void setMainJar(String mainJar) {
        this.mainJar = mainJar;
    }

    /**
     * 获取app版本
     *
     * @return app版本
     */
    public String getAppVersion() {
        return appVersion;
    }

    /**
     * 设置app版本
     *
     * @param appVersion app版本
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    /**
     * 获取程序描述
     *
     * @return 程序描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置程序描述
     *
     * @param description 程序描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取运行期jre目录
     *
     * @return 运行期jre目录
     */
    public String getRuntimeImage() {
        return runtimeImage;
    }

    /**
     * 设置运行期jre目录
     *
     * @param runtimeImage 运行期jre目录
     */
    public void setRuntimeImage(String runtimeImage) {
        this.runtimeImage = runtimeImage;
    }

    /**
     * 是否详细信息
     *
     * @return 是否详细信息
     */
    public boolean isVerbose() {
        return BooleanUtil.isTrue(verbose);
    }

    /**
     * 设置是否详细信息
     *
     * @param verbose 详细信息
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * 是否创建开始菜单、仅windows
     *
     * @return 是否创建开始菜单、仅windows
     */
    public boolean isWinMenu() {
        return BooleanUtil.isTrue(winMenu);
    }

    /**
     * 设置是否创建开始菜单、仅windows
     *
     * @param winMenu 是否创建开始菜单、仅windows
     */
    public void setWinMenu(boolean winMenu) {
        this.winMenu = winMenu;
    }

    /**
     * 是否创建桌面图标、仅windows
     *
     * @return 是否创建桌面图标、仅windows
     */
    public boolean isWinShortcut() {
        return BooleanUtil.isTrue(winShortcut);
    }

    /**
     * 设置是否创建桌面图标、仅windows
     *
     * @param winShortcut 是否创建桌面图标、仅windows
     */
    public void setWinShortcut(boolean winShortcut) {
        this.winShortcut = winShortcut;
    }

    /**
     * 是否可选安装目录、仅windows
     *
     * @return 是否可选安装目录、仅windows
     */
    public boolean isWinDirChooser() {
        return BooleanUtil.isTrue(winDirChooser);
    }

    /**
     * 设置是否可选安装目录、仅windows
     *
     * @param winDirChooser 是否可选安装目录、仅windows
     */
    public void setWinDirChooser(boolean winDirChooser) {
        this.winDirChooser = winDirChooser;
    }

    /**
     * 获取mac程序唯一id、仅macos
     *
     * @return mac程序唯一id、仅macos
     */
    public String getMacPackageIdentifier() {
        return macPackageIdentifier;
    }

    /**
     * 设置mac程序唯一id、仅macos
     *
     * @param macPackageIdentifier mac程序唯一id、仅macos
     */
    public void setMacPackageIdentifier(String macPackageIdentifier) {
        this.macPackageIdentifier = macPackageIdentifier;
    }

    /**
     * 获取vm参数
     *
     * @return vm参数
     */
    public Set<String> getJavaOptions() {
        return javaOptions;
    }

    /**
     * 设置vm参数
     *
     * @param javaOptions vm参数
     */
    public void setJavaOptions(Set<String> javaOptions) {
        this.javaOptions = javaOptions;
    }

    /**
     * 是否启用
     *
     * @return 是否启用
     */
    public boolean isEnable() {
        return BooleanUtil.isTrue(this.enable);
    }

    /**
     * 设置是否启用
     *
     * @param enable 是否启用
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    /**
     * 获取版权信息
     *
     * @return 版权信息
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * 设置版权信息
     *
     * @param copyright 版权信息
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    // public String fixedType() {
    //     if (StringUtil.equalsIgnoreCase(this.type, "AppImage")) {
    //         return "app-image";
    //     }
    //     return this.type;
    // }

    @Override
    public void marge(JPackageConfig config) {
        if (config == null) {
            return;
        }
        if (config.name != null) {
            this.name = config.name;
        }
        if (config.dest != null) {
            this.dest = config.dest;
        }
        if (config.type != null) {
            this.type = config.type;
        }
        if (config.icon != null) {
            this.icon = config.icon;
        }
        if (config.input != null) {
            this.input = config.input;
        }
        if (config.vendor != null) {
            this.vendor = config.vendor;
        }
        if (config.mainJar != null) {
            this.mainJar = config.mainJar;
        }
        if (config.appVersion != null) {
            this.appVersion = config.appVersion;
        }
        if (config.copyright != null) {
            this.copyright = config.copyright;
        }
        if (config.description != null) {
            this.description = config.description;
        }
        if (config.runtimeImage != null) {
            this.runtimeImage = config.runtimeImage;
        }
        if (config.macPackageIdentifier != null) {
            this.macPackageIdentifier = config.macPackageIdentifier;
        }
        if (this.javaOptions == null) {
            this.javaOptions = config.javaOptions;
        } else if (config.javaOptions != null) {
            this.javaOptions.addAll(config.javaOptions);
        }
        if (config.enable != null) {
            this.enable = config.enable;
        }
        if (config.verbose != null) {
            this.verbose = config.verbose;
        }
        if (config.winMenu != null) {
            this.winMenu = config.winMenu;
        }
        if (config.winShortcut != null) {
            this.winShortcut = config.winShortcut;
        }
        if (config.winDirChooser != null) {
            this.winDirChooser = config.winDirChooser;
        }
    }
}
