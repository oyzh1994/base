package cn.oyzh.store.jdbc;


import cn.oyzh.common.util.StringUtil;
import cn.oyzh.common.util.UUIDUtil;

/**
 * 键生成器
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class KeyGenerator {

    /**
     * 单例
     */
    public static final KeyGenerator INSTANCE = new KeyGenerator();

    /**
     * 根据列类型生成主键值
     *
     * @param columnType 列类型
     * @return 主键值
     */
    public Object generator(String columnType) {
        if (StringUtil.containsAnyIgnoreCase(columnType, "text", "LONGVARCHAR", "NVARCHAR", "NCHAR", "varchar", "char")) {
            return UUIDUtil.uuid();
        }
        if (StringUtil.equalsAnyIgnoreCase(columnType, "integer", "int", "bigint", "TINYINT", "ALLINT")) {
            return System.currentTimeMillis() + Math.round(Math.random() * 1000);
        }
        return null;
    }

    /**
     * 根据列类型生成主键值
     *
     * @param columnType 列类型
     * @return 主键值
     */
    public static Object generatorKey(String columnType) {
        return INSTANCE.generator(columnType);
    }
}
