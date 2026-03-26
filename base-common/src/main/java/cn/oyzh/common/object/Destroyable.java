package cn.oyzh.common.object;

import cn.oyzh.common.util.BooleanUtil;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 可销毁接口
 *
 * @author oyzh
 * @since 2024-09-26
 */
public interface Destroyable {

    /**
     * 销毁对象记录
     */
    Map<Destroyable, Boolean> DESTROYED = new WeakHashMap<>();

    /**
     * 执行销毁
     */
    void destroy();

    /**
     * 是否已销毁
     *
     * @return 结果
     */
    default boolean isDestroyed() {
        boolean result = BooleanUtil.isTrue(DESTROYED.get(this));
        if (result) {
            DESTROYED.remove(this);
        }
        return result;
    }

    /**
     * 标记为已销毁
     */
    default void markDestroyed() {
        DESTROYED.put(this, true);
    }
}
