package cn.oyzh.common.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定时缓存，缓存项超过指定存活时间后失效
 *
 * @param <K> 键
 * @param <V> 值
 * @author oyzh
 * @since 2024-10-18
 */
public class TimedCache<K, V> implements Cache<K, V> {

    /**
     * 带时间信息的缓存值
     *
     * @param <V> 值泛型
     */
    private static class TimedValue<V> {
        /**
         * 值
         */
        private V value;
        /**
         * 写入时间
         */
        private Long putTime;
    }

    /**
     * 存活时间，单位毫秒，小于等于0表示不过期
     */
    private final long timeout;

    /**
     * 缓存数据
     */
    private final Map<K, TimedValue<V>> cache;

    /**
     * 构造定时缓存
     *
     * @param timeout 存活时间，单位毫秒，小于等于0表示不过期
     */
    public TimedCache(long timeout) {
        this.timeout = timeout;
        this.cache = new ConcurrentHashMap<>();
    }

    @Override
    public V get(K key) {
        TimedValue<V> value = this.cache.get(key);
        if (value == null) {
            return null;
        }
        // 检查值
        if (this.checkTimeout(value)) {
            this.cache.remove(key);
            return null;
        }
        return value.value;
    }

    @Override
    public void put(K key, V value) {
        TimedValue<V> timedValue = new TimedValue<>();
        timedValue.value = value;
        if (this.timeout > 0) {
            timedValue.putTime = System.currentTimeMillis();
        }
        this.cache.put(key, timedValue);
    }

    @Override
    public void remove(K key) {
        this.cache.remove(key);
    }

    @Override
    public void clear() {
        this.cache.clear();
    }

    @Override
    public boolean containsKey(K key) {
        TimedValue<V> value = this.cache.get(key);
        if (value == null) {
            return false;
        }
        return !this.checkTimeout(value);
    }

    /**
     * 检查缓存值是否超时
     *
     * @param value 缓存值
     * @return 结果
     */
    private boolean checkTimeout(TimedValue<V> value) {
        if (value != null && this.timeout > 0) {
            return System.currentTimeMillis() - value.putTime > this.timeout;
        }
        return false;
    }
}
