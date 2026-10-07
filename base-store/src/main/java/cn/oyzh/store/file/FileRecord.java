package cn.oyzh.store.file;

import java.util.HashMap;

/**
 * 文件记录
 *
 * @author oyzh
 * @since 2024-11-27
 */
public class FileRecord extends HashMap<Integer, Object> {

    /**
     * 获取指定字段的值，并按目标类型进行转换
     *
     * @param key   字段索引
     * @param clazz 目标类型
     * @return 转换后的值，字段为空时返回null
     */
    public Object getValue(Integer key, Class<?> clazz) {
        Object val = this.get(key);
        if (val == null) {
            return null;
        }
//        if (CharSequence.class.isAssignableFrom(clazz)) {
            if (clazz == Integer.class || clazz == int.class) {
                return Integer.valueOf(val.toString());
            }
            if (clazz == Long.class || clazz == long.class) {
                return Long.valueOf(val.toString());
            }
            if (clazz == Float.class || clazz == float.class) {
                return Float.valueOf(val.toString());
            }
            if (clazz == Double.class || clazz == double.class) {
                return Double.valueOf(val.toString());
            }
            if (clazz == Byte.class || clazz == byte.class) {
                return Byte.valueOf(val.toString());
            }
            if (clazz == Number.class) {
                return Double.valueOf(val.toString());
            }
//        }
        return val.toString();
    }
}
