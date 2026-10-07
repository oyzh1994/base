package cn.oyzh.pkg.jlink;

import cn.oyzh.common.util.BooleanUtil;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.pkg.ConfigMargeAble;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * jlink配置
 *
 * @author oyzh
 * @since 2026-09-18
 */
public class JLinkConfig implements ConfigMargeAble<JLinkConfig> {

    /**
     * vm类型
     */
    private String vm;

    /**
     * 输出目录
     */
    private String output;

    /**
     * 压缩等级
     */
    private String compress;

    /**
     * 打印过程日志
     */
    private Boolean verbose;

    /**
     * 无需man手册
     */
    private Boolean noManPages;

    /**
     * 无需头文件
     */
    private Boolean noHeaderFiles;

    /**
     * 去除debug文件
     */
    private Boolean stripDebug;

    /**
     * 去除debug属性
     */
    private Boolean stripJavaDebugAttributes;

    /**
     * 去除debug调试符
     */
    private String stripNativeDebugSymbols;

    /**
     * 忽略签名信息
     */
    private Boolean ignoreSigningInformation;

    /**
     * 添加的模块
     */
    private Set<String> addModules;

    /**
     * 排除文件
     */
    private Set<String> excludeFiles;

    /**
     * 是否启用
     */
    private Boolean enable;

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
     * 合并添加的模块
     *
     * @param addModules 添加的模块
     */
    public void margeAddModules(Collection<String> addModules) {
        if (CollectionUtil.isNotEmpty(addModules)) {
            if (this.addModules == null) {
                this.addModules = new HashSet<>(addModules);
            } else {
                this.addModules.addAll(addModules);
            }
        }
    }

    /**
     * 获取vm类型
     *
     * @return vm类型
     */
    public String getVm() {
        return vm;
    }

    /**
     * 设置vm类型
     *
     * @param vm vm类型
     */
    public void setVm(String vm) {
        this.vm = vm;
    }

    /**
     * 获取输出目录
     *
     * @return 输出目录
     */
    public String getOutput() {
        return output;
    }

    /**
     * 设置输出目录
     *
     * @param output 输出目录
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     * 获取压缩等级
     *
     * @return 压缩等级
     */
    public String getCompress() {
        return compress;
    }

    /**
     * 设置压缩等级
     *
     * @param compress 压缩等级
     */
    public void setCompress(String compress) {
        this.compress = compress;
    }

    /**
     * 是否打印过程日志
     *
     * @return 是否打印过程日志
     */
    public boolean isVerbose() {
        return BooleanUtil.isTrue(this.verbose);
    }

    /**
     * 设置是否打印过程日志
     *
     * @param verbose 打印过程日志
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * 是否无需man手册
     *
     * @return 是否无需man手册
     */
    public boolean isNoManPages() {
        return BooleanUtil.isTrue(this.noManPages);
    }

    /**
     * 设置是否无需man手册
     *
     * @param noManPages 无需man手册
     */
    public void setNoManPages(boolean noManPages) {
        this.noManPages = noManPages;
    }

    /**
     * 是否无需头文件
     *
     * @return 是否无需头文件
     */
    public boolean isNoHeaderFiles() {
        return BooleanUtil.isTrue(this.noHeaderFiles);
    }

    /**
     * 设置是否无需头文件
     *
     * @param noHeaderFiles 无需头文件
     */
    public void setNoHeaderFiles(boolean noHeaderFiles) {
        this.noHeaderFiles = noHeaderFiles;
    }

    /**
     * 是否去除debug文件
     *
     * @return 是否去除debug文件
     */
    public boolean isStripDebug() {
        return BooleanUtil.isTrue(this.stripDebug);
    }

    /**
     * 设置是否去除debug文件
     *
     * @param stripDebug 去除debug文件
     */
    public void setStripDebug(boolean stripDebug) {
        this.stripDebug = stripDebug;
    }

    /**
     * 是否去除debug属性
     *
     * @return 是否去除debug属性
     */
    public boolean isStripJavaDebugAttributes() {
        return BooleanUtil.isTrue(this.stripJavaDebugAttributes);
    }

    /**
     * 设置是否去除debug属性
     *
     * @param stripJavaDebugAttributes 去除debug属性
     */
    public void setStripJavaDebugAttributes(boolean stripJavaDebugAttributes) {
        this.stripJavaDebugAttributes = stripJavaDebugAttributes;
    }

    /**
     * 获取去除debug调试符
     *
     * @return 去除debug调试符
     */
    public String getStripNativeDebugSymbols() {
        return stripNativeDebugSymbols;
    }

    /**
     * 设置去除debug调试符
     *
     * @param stripNativeDebugSymbols 去除debug调试符
     */
    public void setStripNativeDebugSymbols(String stripNativeDebugSymbols) {
        this.stripNativeDebugSymbols = stripNativeDebugSymbols;
    }

    /**
     * 获取添加的模块
     *
     * @return 添加的模块
     */
    public Set<String> getAddModules() {
        return addModules;
    }

    /**
     * 设置添加的模块
     *
     * @param addModules 添加的模块
     */
    public void setAddModules(Set<String> addModules) {
        this.addModules = addModules;
    }

    /**
     * 获取排除文件
     *
     * @return 排除文件
     */
    public Set<String> getExcludeFiles() {
        return excludeFiles;
    }

    /**
     * 设置排除文件
     *
     * @param excludeFiles 排除文件
     */
    public void setExcludeFiles(Set<String> excludeFiles) {
        this.excludeFiles = excludeFiles;
    }

    /**
     * 是否忽略签名信息
     *
     * @return 是否忽略签名信息
     */
    public boolean isIgnoreSigningInformation() {
        return BooleanUtil.isTrue(this.ignoreSigningInformation);
    }

    /**
     * 设置是否忽略签名信息
     *
     * @param ignoreSigningInformation 忽略签名信息
     */
    public void setIgnoreSigningInformation(boolean ignoreSigningInformation) {
        this.ignoreSigningInformation = ignoreSigningInformation;
    }

    @Override
    public void marge(JLinkConfig config) {
        if (config == null) {
            return;
        }
        if (config.vm != null) {
            this.vm = config.vm;
        }
        if (config.output != null) {
            this.output = config.output;
        }
        if (config.compress != null) {
            this.compress = config.compress;
        }
        if (this.addModules == null) {
            this.addModules = config.addModules;
        } else if (config.addModules != null) {
            this.addModules.addAll(config.addModules);
        }
        if (this.excludeFiles == null) {
            this.excludeFiles = config.excludeFiles;
        } else if (config.excludeFiles != null) {
            this.excludeFiles.addAll(config.excludeFiles);
        }
        if (config.enable != null) {
            this.enable = config.enable;
        }
        if (config.verbose != null) {
            this.verbose = config.verbose;
        }
        if (config.stripDebug != null) {
            this.stripDebug = config.stripDebug;
        }
        if (config.noManPages != null) {
            this.noManPages = config.noManPages;
        }
        if (config.noHeaderFiles != null) {
            this.noHeaderFiles = config.noHeaderFiles;
        }
        if (config.ignoreSigningInformation != null) {
            this.ignoreSigningInformation = config.ignoreSigningInformation;
        }
        if (config.stripJavaDebugAttributes != null) {
            this.stripJavaDebugAttributes = config.stripJavaDebugAttributes;
        }
        if (config.stripNativeDebugSymbols != null) {
            this.stripNativeDebugSymbols = config.stripNativeDebugSymbols;
        }
    }
}
