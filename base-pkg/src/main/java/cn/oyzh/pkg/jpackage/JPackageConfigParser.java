package cn.oyzh.pkg.jpackage;

import cn.oyzh.common.json.JSONUtil;
import cn.oyzh.pkg.ConfigParser;
import com.alibaba.fastjson2.JSONObject;

import java.util.HashSet;
import java.util.List;

/**
 * jpackage配置解析器
 *
 * @author oyzh
 * @since 2026-09-18
 */
public class JPackageConfigParser implements ConfigParser<JPackageConfig> {

    @Override
    public JPackageConfig parse(JSONObject object) {
        JPackageConfig config = new JPackageConfig();
        String name = object.getString("name");
        if (name != null) {
            config.setName(name);
        }
        String type = object.getString("type");
        if (type != null) {
            config.setType(type);
        }
        String appVersion = object.getString("appVersion");
        if (appVersion != null) {
            config.setAppVersion(appVersion);
        }
        String mainJar = object.getString("mainJar");
        if (mainJar != null) {
            config.setMainJar(mainJar);
        }
        String runtimeImage = object.getString("runtimeImage");
        if (runtimeImage != null) {
            config.setRuntimeImage(runtimeImage);
        }
        String icon = object.getString("icon");
        if (icon != null) {
            config.setIcon(icon);
        }
        String input = object.getString("input");
        if (input != null) {
            config.setInput(input);
        }
        String dest = object.getString("dest");
        if (dest != null) {
            config.setDest(dest);
        }
        String vendor = object.getString("vendor");
        if (vendor != null) {
            config.setVendor(vendor);
        }
        Boolean verbose = object.getBoolean("verbose");
        if (verbose != null) {
            config.setVerbose(verbose);
        }
        List<String> javaOptions = JSONUtil.toList(object, "java-options", String.class);
        if (javaOptions != null) {
            config.setJavaOptions(new HashSet<>(javaOptions));
        }
        Boolean winMenu = object.getBoolean("win-menu");
        if (winMenu != null) {
            config.setWinMenu(winMenu);
        }
        Boolean winShortcut = object.getBoolean("win-shortcut");
        if (winShortcut != null) {
            config.setWinShortcut(winShortcut);
        }
        Boolean winDirChooser = object.getBoolean("win-dir-chooser");
        if (winDirChooser != null) {
            config.setWinDirChooser(winDirChooser);
        }
        String macPackageIdentifier = object.getString("mac-package-identifier");
        if (macPackageIdentifier != null) {
            config.setMacPackageIdentifier(macPackageIdentifier);
        }
        String description = object.getString("description");
        if (description != null) {
            config.setDescription(description);
        }
        Boolean enable = object.getBoolean("enable");
        if (enable != null) {
            config.setEnable(enable);
        }
        return config;
    }

    /**
     * 解析配置
     *
     * @param object 配置内容
     * @return 配置类
     */
    public static JPackageConfig parseConfig(JSONObject object) {
        return new JPackageConfigParser().parse(object);
    }

    /**
     * 解析配置
     *
     * @param configFile 配置文件
     * @return 配置类
     */
    public static JPackageConfig parseConfig(String configFile) {
        return new JPackageConfigParser().parse(configFile);
    }
}
