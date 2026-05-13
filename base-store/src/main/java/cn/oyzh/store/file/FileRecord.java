package cn.oyzh.store.file;

import java.util.HashMap;

/**
 * 文件记录
 *
 * @author oyzh
 * @since 2024-11-27
 */
public class FileRecord extends HashMap<Integer, Object> {

    public Object getValue(Integer key, Class<?> clazz) {
        Object val = this.get(key);
        if (val == null) {
            return null;
        }
        if (CharSequence.class.isAssignableFrom(clazz)) {
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
        }
        return val.toString();
    }
}
