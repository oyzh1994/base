package cn.oyzh.common.cache;

/**
 * 缓存
 *
 * @param <K> 键
 * @param <V> 值
 * @author oyzh
 * @since 2024-09-29
 */
public interface Cache<K, V> {

    /**
     * 获取缓存值
     *
     * @param key 键
     * @return 值
     */
    V get(K key);

    /**
     * 清空缓存
     */
    void clear();

    /**
     * 写入缓存
     *
     * @param key   键
     * @param value 值
     */
    void put(K key, V value);

    /**
     * 移除缓存
     *
     * @param key 键
     */
    void remove(K key);

    /**
     * 是否包含指定键
     *
     * @param key 键
     * @return 结果
     */
    boolean containsKey(K key);
}
