package cn.oyzh.common.util;

/**
 * boolean工具类
 *
 * @author oyzh
 * @since 2024-09-29
 */
public class BooleanUtil {

    /**
     * 私有构造，禁止实例化
     */
    private BooleanUtil() {
    }

    /**
     * 判断是否为true
     *
     * @param bool 布尔值
     * @return 值为true时返回true，为false或null时返回false
     */
    public static boolean isTrue(Boolean bool) {
        return bool != null && bool;
    }

    /**
     * 判断是否为false
     *
     * @param bool 布尔值
     * @return 值为false时返回true，为true或null时返回false
     */
    public static boolean isFalse(Boolean bool) {
        return bool != null && !bool;
    }
}
