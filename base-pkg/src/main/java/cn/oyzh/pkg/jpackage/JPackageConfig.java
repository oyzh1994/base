package cn.oyzh.pkg.jpackage;


import cn.oyzh.common.util.BooleanUtil;
import cn.oyzh.pkg.ConfigMargeAble;

import java.io.File;
import java.util.Set;

/**
 * JPackage配置
 *
 * @author oyzh
 * @since 2023/3/8
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

    public String destParent() {
        return new File(dest).getParent();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getMainJar() {
        return mainJar;
    }

    public void setMainJar(String mainJar) {
        this.mainJar = mainJar;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRuntimeImage() {
        return runtimeImage;
    }

    public void setRuntimeImage(String runtimeImage) {
        this.runtimeImage = runtimeImage;
    }

    public boolean isVerbose() {
        return BooleanUtil.isTrue(verbose);
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isWinMenu() {
        return BooleanUtil.isTrue(winMenu);
    }

    public void setWinMenu(boolean winMenu) {
        this.winMenu = winMenu;
    }

    public boolean isWinShortcut() {
        return BooleanUtil.isTrue(winShortcut);
    }

    public void setWinShortcut(boolean winShortcut) {
        this.winShortcut = winShortcut;
    }

    public boolean isWinDirChooser() {
        return BooleanUtil.isTrue(winDirChooser);
    }

    public void setWinDirChooser(boolean winDirChooser) {
        this.winDirChooser = winDirChooser;
    }

    public String getMacPackageIdentifier() {
        return macPackageIdentifier;
    }

    public void setMacPackageIdentifier(String macPackageIdentifier) {
        this.macPackageIdentifier = macPackageIdentifier;
    }

    public Set<String> getJavaOptions() {
        return javaOptions;
    }

    public void setJavaOptions(Set<String> javaOptions) {
        this.javaOptions = javaOptions;
    }

    public boolean isEnable() {
        return BooleanUtil.isTrue(this.enable);
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getCopyright() {
        return copyright;
    }

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
