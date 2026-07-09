package cn.oyzh.common.cache;

import cn.oyzh.common.exception.InvalidParamException;
import cn.oyzh.common.util.StringUtil;

import java.lang.ref.WeakReference;

/**
 * 缓存辅助类
 *
 * @author oyzh
 * @since 2024/7/11
 */
public class CacheHelper {

    private static final TimedCache<String, Object> CACHE = CacheUtil.newTimedCache(-1);

    public static <T> T get(String key) {
        if (StringUtil.notContains(key, ":")) {
            throw new InvalidParamException(key);
        }
        WeakReference<?> reference = (WeakReference<?>) CACHE.get(key);
        return reference == null ? null : (T) reference.get();
    }

    public static void remove(String key) {
        if (StringUtil.notContains(key, ":")) {
            throw new InvalidParamException(key);
        }
        CACHE.remove(key);
    }

    public static void set(String key, Object val) {
        if (StringUtil.notContains(key, ":")) {
            throw new InvalidParamException(key);
        }
        CACHE.put(key, new WeakReference<>(val));
    }

    //    public static void clear() {
    //        CACHE.clear();
    //    }
}
