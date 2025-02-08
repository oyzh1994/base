package cn.oyzh.common.cache;

/**
 * @author oyzh
 * @since 2024-09-29
 */
public interface Cache<K, V> {

    V get(K key);

    void clear();

    void put(K key, V value);

    void remove(K key);

    boolean containsKey(K key);
}
