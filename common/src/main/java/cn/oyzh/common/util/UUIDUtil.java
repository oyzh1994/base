package cn.oyzh.common.util;

import java.util.UUID;

/**
 * uuid工具类
 *
 * @author oyzh
 * @since 2024-09-29
 */
public class UUIDUtil {

    public static UUID randomUUID() {
        return UUID.randomUUID();
    }

    public static String uuid() {
        return randomUUID().toString();
    }

    public static String uuidSimple() {
        return randomUUID().toString().replace("-", "");
    }
}
