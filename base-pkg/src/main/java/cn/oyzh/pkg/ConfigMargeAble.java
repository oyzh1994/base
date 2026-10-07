package cn.oyzh.pkg;

/**
 * 配置合并接口，用于将其他配置合并到当前配置
 *
 * @author oyzh
 * @since 2025-11-12
 */
public interface ConfigMargeAble<T> {

    /**
     * 合并配置
     *
     * @param config 配置
     */
    void marge(T config);
}
