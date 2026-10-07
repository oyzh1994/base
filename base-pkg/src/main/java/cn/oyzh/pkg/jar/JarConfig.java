package cn.oyzh.pkg.jar;


import cn.oyzh.pkg.ConfigMargeAble;

import java.util.Set;

/**
 * jar配置
 *
 * @author oyzh
 * @since 2026-09-18
 */
public class JarConfig implements ConfigMargeAble<JarConfig> {

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 是否移除空jar
     */
    private Boolean removeEmpty;

    /**
     * javafx优化
     */
    private Boolean javafxOptimize;

    /**
     * 二进制库优化
     */
    private Boolean binlibOptimize;

//    /**
//     * 可执行程序优化
//     */
//    private Boolean executableOptimize;

    /**
     * 跳过的jar
     */
    private Set<String> skipsJar;

    /**
     * 排除的文件
     */
    private Set<String> excludes;

    /**
     * javafx路径
     */
    private String javafxPath;

    /**
     * 是否移除空jar
     *
     * @return 是否移除空jar
     */
    public boolean isRemoveEmpty() {
        return removeEmpty == null || this.removeEmpty;
    }

    /**
     * 设置是否移除空jar
     *
     * @param removeEmpty 是否移除空jar
     */
    public void setRemoveEmpty(boolean removeEmpty) {
        this.removeEmpty = removeEmpty;
    }

    /**
     * 获取排除的文件
     *
     * @return 排除的文件
     */
    public Set<String> getExcludes() {
        return excludes;
    }

    /**
     * 设置排除的文件
     *
     * @param excludes 排除的文件
     */
    public void setExcludes(Set<String> excludes) {
        this.excludes = excludes;
    }

    /**
     * 获取跳过的jar
     *
     * @return 跳过的jar
     */
    public Set<String> getSkipsJar() {
        return skipsJar;
    }

    /**
     * 设置跳过的jar
     *
     * @param skipsJar 跳过的jar
     */
    public void setSkipsJar(Set<String> skipsJar) {
        this.skipsJar = skipsJar;
    }

    /**
     * 是否启用
     *
     * @return 是否启用
     */
    public boolean isEnable() {
        return enable == null || this.enable;
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
     * 是否启用javafx优化
     *
     * @return 是否启用javafx优化
     */
    public boolean isJavafxOptimize(){
        return javafxOptimize != null && this.javafxOptimize;
    }

    /**
     * 设置是否启用javafx优化
     *
     * @param javafxOptimize javafx优化
     */
    public void setJavafxOptimize(Boolean javafxOptimize) {
        this.javafxOptimize = javafxOptimize;
    }

    /**
     * 是否启用二进制库优化
     *
     * @return 是否启用二进制库优化
     */
    public boolean isBinlibOptimize(){
        return binlibOptimize != null && this.binlibOptimize;
    }

    /**
     * 设置是否启用二进制库优化
     *
     * @param binlibOptimize 二进制库优化
     */
    public void setBinlibOptimize(Boolean binlibOptimize) {
        this.binlibOptimize = binlibOptimize;
    }

//    public boolean isExecutableOptimize(){
//        return executableOptimize != null && this.executableOptimize;
//    }
//
//    public void setExecutableOptimize(Boolean executableOptimize) {
//        this.executableOptimize = executableOptimize;
//    }

    /**
     * 获取javafx路径
     *
     * @return javafx路径
     */
    public String getJavafxPath() {
        return javafxPath;
    }

    /**
     * 设置javafx路径
     *
     * @param javafxPath javafx路径
     */
    public void setJavafxPath(String javafxPath) {
        this.javafxPath = javafxPath;
    }

    @Override
    public void marge(JarConfig config) {
        if (config == null) {
            return;
        }
        if (this.skipsJar == null) {
            this.skipsJar = config.skipsJar;
        } else if (config.skipsJar != null) {
            this.skipsJar.addAll(config.skipsJar);
        }
        if (this.excludes == null) {
            this.excludes = config.excludes;
        } else if (config.excludes != null) {
            this.excludes.addAll(config.excludes);
        }
        if (config.enable != null) {
            this.enable = config.enable;
        }
        if (config.removeEmpty != null) {
            this.removeEmpty = config.removeEmpty;
        }
        if (config.javafxOptimize != null) {
            this.javafxOptimize = config.javafxOptimize;
        }
        if (config.binlibOptimize != null) {
            this.binlibOptimize = config.binlibOptimize;
        }
//        if (config.executableOptimize != null) {
//            this.executableOptimize = config.executableOptimize;
//        }
        if (config.javafxPath != null) {
            this.javafxPath = config.javafxPath;
        }
    }
}
