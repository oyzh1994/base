package cn.oyzh.common.object;

import java.util.Collection;

/**
 * 销毁工具类
 *
 * @author oyzh
 * @since 2024-09-26
 */
public class DestroyUtil {

    public static void destroy(Object obj) {
        if (obj instanceof Destroyable destroyable) {
            destroyable.destroy();
        }
    }

    public static void destroy(Collection<?> collection) {
        if (collection != null && !collection.isEmpty()) {
            for (Object object : collection) {
                destroy(object);
            }
        }
    }
}
