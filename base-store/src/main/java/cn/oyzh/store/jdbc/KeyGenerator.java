package cn.oyzh.store.jdbc;


import cn.oyzh.common.util.StringUtil;
import cn.oyzh.common.util.UUIDUtil;

/**
 * 键生成器
 *
 * @author oyzh
 * @since 2024-09-24
 */
public class KeyGenerator {

    public static final KeyGenerator INSTANCE = new KeyGenerator();

    public Object generator(String columnType) {
        if (StringUtil.containsAnyIgnoreCase(columnType, "text", "LONGVARCHAR", "NVARCHAR", "NCHAR", "varchar", "char")) {
            return UUIDUtil.uuid();
        }
        if (StringUtil.equalsAnyIgnoreCase(columnType, "integer", "int", "bigint", "TINYINT", "ALLINT")) {
            return System.currentTimeMillis() + Math.round(Math.random() * 1000);
        }
        return null;
    }

    public static Object generatorKey(String columnType) {
        return INSTANCE.generator(columnType);
    }
}
