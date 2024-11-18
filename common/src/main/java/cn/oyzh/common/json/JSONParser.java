package cn.oyzh.common.json;

import cn.oyzh.common.bean.BeanUtil;
import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.ClassUtil;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.common.util.ReflectUtil;
import cn.oyzh.common.util.StringUtil;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.Jsoner;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author oyzh
 * @since 2024-11-18
 */
public class JSONParser {

    public static final JSONParser INSTANCE = new JSONParser();

    public <T> T toBean(JsonObject object, Class<T> beanClass) throws InvocationTargetException, IllegalAccessException {
        T bean = ClassUtil.newInstance(beanClass);
        for (Map.Entry<String, Object> entry : object.entrySet()) {
            Object value = entry.getValue();
            Method method = BeanUtil.getSetterMethod(entry.getKey(), value == null ? null : value.getClass(), beanClass);
            if (method != null) {
                BeanUtil.doSetter(bean, method, value);
            } else {
                JulLog.error("property:{} is not found", entry.getKey());
            }
        }
        return bean;
    }

    public <T> List<T> toBean(JsonArray array, Class<T> beanClass) throws InvocationTargetException, IllegalAccessException {
        List<T> beanList = new ArrayList<>();
        for (Object o : array) {
            T bean = this.toBean((JsonObject) o, beanClass);
            beanList.add(bean);
        }
        return beanList;
    }

    public <T> List<T> toBean(Collection<?> collection, Class<T> beanClass) throws InvocationTargetException, IllegalAccessException {
        List<T> beanList = new ArrayList<>();
        for (Object o : collection) {
            T bean = this.toBean((JsonObject) o, beanClass);
            beanList.add(bean);
        }
        return beanList;
    }

    public String toJson(Object obj) {
        try {
            if (obj == null) {
                return Jsoner.serialize(obj);
            }
            if (obj instanceof byte[]) {
                return Jsoner.serialize(obj);
            }
            if (obj instanceof short[]) {
                return Jsoner.serialize(obj);
            }
            if (obj instanceof char[]) {
                return Jsoner.serialize(obj);
            }
            if (obj instanceof int[]) {
                return Jsoner.serialize(obj);
            }
            if (obj instanceof float[]) {
                return Jsoner.serialize(obj);
            }
            if (obj instanceof double[]) {
                return Jsoner.serialize(obj);
            }
            if (obj instanceof long[]) {
                return Jsoner.serialize(obj);
            }
            if (obj instanceof Number) {
                return Jsoner.serialize(obj);
            }
            if (obj instanceof Boolean) {
                return Jsoner.serialize(obj);
            }
            if (obj instanceof Character) {
                return Jsoner.serialize(obj);
            }
            if (obj instanceof Jsonable) {
                return Jsoner.serialize(obj);
            }
            if (obj instanceof CharSequence sequence) {
                return Jsoner.serialize(sequence.toString());
            }
            // 集合
            if (obj instanceof Collection<?> collection) {
                return this.toJson(collection);
            }
            // map
            if (obj instanceof Map<?, ?> map) {
                return this.toJson(map);
            }
            // 获取类
            Class<?> beanClass = obj.getClass();
            // 获取所有字段
            Field[] fields = ReflectUtil.getFields(beanClass, true, true);
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            // 遍历字段并处理
            for (Field field : fields) {
                int modifiers = field.getModifiers();
                // 过滤字段
                if (!Modifier.isPrivate(modifiers)
                        || Modifier.isStatic(modifiers)
                        || Modifier.isNative(modifiers)
                        || Modifier.isAbstract(modifiers)
                ) {
                    continue;
                }
                // 字段信息
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();
                // 获取get方法
                Method method = BeanUtil.getGetterMethod(fieldName, fieldType, beanClass);
                Object val = BeanUtil.doGetter(obj, method);
                if (val == null) {
                    continue;
                }
                // 追加数据
                builder.append('"').append(fieldName).append('"')
                        .append(":")
                        .append(this.toJson(val))
                        .append(",");
            }
            StringUtil.deleteLast(builder, ",");
            builder.append("}");
            return builder.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 转换为json
     * @param map
     * @return
     */
    public String toJson(Map<?, ?> map) {
        if (CollectionUtil.isEmpty(map)) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            builder.append('"').append(entry.getKey()).append('"')
                    .append(":")
                    .append(this.toJson(entry.getValue()))
                    .append(",");
        }
        StringUtil.deleteLast(builder, ",");
        builder.append("}");
        return builder.toString();
    }

    public String toJson(Collection<?> obj) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Object o : obj) {
            builder.append(this.toJson(o)).append(",");
        }
        StringUtil.deleteLast(builder, ",");
        builder.append("]");
        return builder.toString();
    }



}
