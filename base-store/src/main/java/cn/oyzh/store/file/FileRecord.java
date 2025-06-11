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
        if (val.getClass() == String.class) {
            if (clazz == String.class) {
                return val;
            }
            if (clazz.isAssignableFrom(Integer.class)) {
                return Integer.valueOf(val.toString());
            }
            if (clazz.isAssignableFrom(Long.class)) {
                return Long.valueOf(val.toString());
            }
            if (clazz.isAssignableFrom(Float.class)) {
                return Float.valueOf(val.toString());
            }
            if (clazz.isAssignableFrom(Double.class)) {
                return Double.valueOf(val.toString());
            }
            if (clazz.isAssignableFrom(Byte.class)) {
                return Byte.valueOf(val.toString());
            }
            if (clazz.isAssignableFrom(Number.class)) {
                return Double.valueOf(val.toString());
            }
        }
        return val;
    }
}
