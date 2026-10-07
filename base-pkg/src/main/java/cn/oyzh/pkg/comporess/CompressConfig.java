package cn.oyzh.pkg.comporess;


import cn.oyzh.pkg.ConfigMargeAble;

/**
 * 压缩配置
 *
 * @author oyzh
 * @since 2026-09-18
 */
public class CompressConfig implements ConfigMargeAble<CompressConfig> {

    /**
     * 压缩类型
     */
    private String type;

    /**
     * 压缩文件名
     */
    private String name;

    /**
     * 获取压缩类型
     *
     * @return 压缩类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置压缩类型
     *
     * @param type 压缩类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取压缩文件名
     *
     * @return 压缩文件名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置压缩文件名
     *
     * @param name 压缩文件名
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void marge(CompressConfig config) {
        if (config == null) {
            return;
        }
        if (config.type != null) {
            this.type = config.type;
        }
        if (config.name != null) {
            this.name = config.name;
        }
    }
}
