package cn.oyzh.common.thread;

import java.util.HashMap;

/**
 * 线程工具类
 *
 * @author oyzh
 * @since 2023/1/3
 */
public class ThreadLocalUtil {

    private static final ThreadLocal<Object> LOCAL = new ThreadLocal<>();

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

    private static class ThreadLocalMap extends HashMap<String, Object> {

    }
}
