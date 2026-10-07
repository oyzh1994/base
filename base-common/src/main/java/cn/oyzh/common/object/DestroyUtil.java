package cn.oyzh.common.object;

import java.util.Collection;

/**
 * 销毁工具类
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class DestroyUtil {

    /**
     * 销毁对象
     *
     * @param obj 目标对象
     */
    public static void destroy(Object obj) {
        if (obj instanceof Destroyable destroyable) {
            destroyable.destroy();
        }
    }

    /**
     * 销毁集合中的所有对象
     *
     * @param collection 目标集合
     */
    public static void destroy(Collection<?> collection) {
        if (collection != null && !collection.isEmpty()) {
            for (Object object : collection) {
                destroy(object);
            }
        }
    }
}
