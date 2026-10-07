package cn.oyzh.common.util;

import java.util.UUID;

/**
 * uuid工具类
 *
 * @author oyzh
 * @since 2024-09-29
 */
public class UUIDUtil {

    /**
     * 私有构造，禁止实例化
     */
    private UUIDUtil() {
    }

    /**
     * 生成随机UUID对象
     *
     * @return UUID对象
     */
    public static UUID randomUUID() {
        return UUID.randomUUID();
    }

    /**
     * 生成带连字符的UUID字符串
     *
     * @return UUID字符串
     */
    public static String uuid() {
        return randomUUID().toString();
    }

    /**
     * 生成不带连字符的UUID字符串
     *
     * @return 去掉连字符后的UUID字符串
     */
    public static String uuidSimple() {
        return randomUUID().toString().replace("-", "");
    }
}
