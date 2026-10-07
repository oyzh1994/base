package cn.oyzh.pkg.jdeps;


import cn.oyzh.common.util.BooleanUtil;
import cn.oyzh.pkg.ConfigMargeAble;

import java.util.Set;

/**
 * jdeps配置
 *
 * @author oyzh
 * @since 2026-09-18
 */
public class JDepsConfig implements ConfigMargeAble<JDepsConfig> {

    /**
     * 汇总信息
     */
    private Boolean summary;

    /**
     * 详细模式
     */
    private Boolean verbose;

    /**
     * 跳过的文件
     */
    private Set<String> skips;

    /**
     * 排除的文件
     */
    private Set<String> excludes;

    /**
     * 多版本
     */
    private Integer multiRelease;

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
     * 是否汇总信息
     *
     * @return 是否汇总信息
     */
    public boolean isSummary() {
        return BooleanUtil.isTrue(this.summary);
    }

    /**
     * 设置是否汇总信息
     *
     * @param summary 汇总信息
     */
    public void setSummary(boolean summary) {
        this.summary = summary;
    }

    /**
     * 是否详细模式
     *
     * @return 是否详细模式
     */
    public boolean isVerbose() {
        return BooleanUtil.isTrue(this.verbose);
    }

    /**
     * 设置是否详细模式
     *
     * @param verbose 详细模式
     */
    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /**
     * 获取跳过的文件
     *
     * @return 跳过的文件
     */
    public Set<String> getSkips() {
        return skips;
    }

    /**
     * 设置跳过的文件
     *
     * @param skips 跳过的文件
     */
    public void setSkips(Set<String> skips) {
        this.skips = skips;
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
     * 获取多版本
     *
     * @return 多版本
     */
    public Integer getMultiRelease() {
        return multiRelease;
    }

    /**
     * 设置多版本
     *
     * @param multiRelease 多版本
     */
    public void setMultiRelease(Integer multiRelease) {
        this.multiRelease = multiRelease;
    }

    @Override
    public void marge(JDepsConfig config) {
        if (config == null) {
            return;
        }
        if (config.multiRelease != null) {
            this.multiRelease = config.multiRelease;
        }
        if (this.skips == null) {
            this.skips = config.skips;
        } else if (config.skips != null) {
            this.skips.addAll(config.skips);
        }
        if (this.excludes == null) {
            this.excludes = config.excludes;
        } else if (config.excludes != null) {
            this.excludes.addAll(config.excludes);
        }
        if (config.enable != null) {
            this.enable = config.enable;
        }
        if (config.summary != null) {
            this.summary = config.summary;
        }
        if (config.verbose != null) {
            this.verbose = config.verbose;
        }
    }
}
