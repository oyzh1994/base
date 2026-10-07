package cn.oyzh.common.thread;

import java.util.HashMap;

/**
 * 线程本地变量工具类，按线程隔离存取键值数据
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class ThreadLocalUtil {

    /**
     * 线程本地存储容器
     */
    private static final ThreadLocal<Object> LOCAL = new ThreadLocal<>();

    /**
     * 设置线程本地变量
     *
     * @param key 键
     * @param obj 值
     */
    public static void setVal(String key, Object obj) {
        ThreadLocalMap localMap;
        Object object = LOCAL.get();
        if (!(object instanceof ThreadLocalMap)) {
            localMap = new ThreadLocalMap();
            LOCAL.set(localMap);
        } else {
            localMap = (ThreadLocalMap) object;
        }
        localMap.put(key, obj);
    }

    /**
     * 移除线程本地变量
     *
     * @param key 键
     */
    public static void removeVal(String key) {
        ThreadLocalMap localMap;
        Object object = LOCAL.get();
        if (!(object instanceof ThreadLocalMap)) {
            localMap = new ThreadLocalMap();
            LOCAL.set(localMap);
        } else {
            localMap = (ThreadLocalMap) object;
        }
        localMap.remove(key);
    }

    /**
     * 获取线程本地变量
     *
     * @param key 键
     * @param <T> 值泛型
     * @return 值
     */
    public static <T> T getVal(String key) {
        Object object = LOCAL.get();
        if (object instanceof ThreadLocalMap localMap) {
            return (T) localMap.get(key);
        }
        return null;
    }

    /**
     * 是否有值
     *
     * @param key 键
     * @return 结果
     */
    public static boolean hasVal(String key) {
        Object object = LOCAL.get();
        if (object instanceof ThreadLocalMap localMap) {
            return localMap.containsKey(key);
        }
        return false;
    }

    /**
     * 线程本地键值存储容器
     */
    private static class ThreadLocalMap extends HashMap<String, Object> {

    }
}
