package cn.oyzh.common.cache;

/**
 * 缓存工具类
 *
 * @author oyzh
 * @since 2024-09-29
 */
public class CacheUtil {
    /**
     * 私有构造，禁止实例化
     */
    private CacheUtil() {
    }

    /**
     * 创建弱引用缓存
     *
     * @param <K> 键
     * @param <V> 值
     * @return 弱引用缓存
     */
    public static <K, V> WeakCache<K, V> newWeakCache() {
        return new WeakCache<>();
    }

    /**
     * 创建定时缓存
     *
     * @param timeout 存活时间，单位毫秒，小于等于0表示不过期
     * @param <K>     键
     * @param <V>     值
     * @return 定时缓存
     */
    public static <K, V> TimedCache<K, V> newTimedCache(long timeout) {
        return new TimedCache<>(timeout);
    }

}
