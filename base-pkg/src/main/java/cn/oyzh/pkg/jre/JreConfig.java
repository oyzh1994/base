package cn.oyzh.pkg.jre;

import cn.oyzh.pkg.ConfigMargeAble;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * jre配置
 *
 * @author oyzh
 * @since 2026-09-18
 */
public class JreConfig implements ConfigMargeAble<JreConfig> {

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * 排除的文件
     */
    private Set<String> excludes;

    /**
     * 解析配置
     *
     * @param object 配置内容
     */
    public void parseConfig(JSONObject object) {
        JSONArray excludes = object.getJSONArray("excludes");
        if (excludes != null) {
            this.excludes = new HashSet<>();
            for (Object o : excludes) {
                this.excludes.add(o.toString());
            }
        }
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

    @Override
    public void marge(JreConfig config) {
        if (config == null) {
            return;
        }
        if (this.excludes == null) {
            this.excludes = config.excludes;
        } else if (config.excludes != null){
            this.excludes.addAll(config.excludes);
        }
        if (config.enable != null) {
            this.enable = config.enable;
        }
    }
}
