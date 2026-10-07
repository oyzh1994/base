package cn.oyzh.common.cache;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 弱引用缓存
 *
 * @param <K> 键
 * @param <V> 值
 * @author oyzh
 * @since 2024-10-18
 */
public class WeakCache<K, V> implements Cache<K, V> {

    /**
     * 缓存数据
     */
    private final Map<K, WeakReference<V>> cache;

    /**
     * 构造弱引用缓存
     */
    public WeakCache() {
        this.cache = new ConcurrentHashMap<>();
    }

    @Override
    public V get(K key) {
        WeakReference<V> ref = this.cache.get(key);
        if (ref == null) {
            return null;
        }
        if (ref.get() == null) {
            this.cache.remove(key);
            return null;
        }
        return ref.get();
    }

    @Override
    public void put(K key, V value) {
        this.cache.put(key, new WeakReference<>(value));
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
        WeakReference<V> ref = this.cache.get(key);
        return ref != null && ref.get() != null;
    }
}
