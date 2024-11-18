package cn.oyzh.common.bean;

import cn.oyzh.common.json.JSONParser;
import cn.oyzh.common.util.ClassUtil;
import cn.oyzh.common.util.ReflectUtil;
import cn.oyzh.common.util.StringUtil;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-11-18
 */
@UtilityClass
public class BeanUtil {

    public static Method getGetterMethod(String propertyName, Class<?> propertyType, Class<?> beanType) {
        propertyName = StringUtil.upperFirst(propertyName);
        if (propertyType == boolean.class) {
            return ReflectUtil.getMethod(beanType, "is" + propertyName, true, true);
        }
        return ReflectUtil.getMethod(beanType, "get" + propertyName, true, true);
    }

    public static Method getSetterMethod(String propertyName, Class<?> propertyType, Class<?> beanType) {
        propertyName = StringUtil.upperFirst(propertyName);
        String methodName = "set" + propertyName;
        Method[] methods = ReflectUtil.getMethods(beanType, false, true);
        for (Method method : methods) {
            // 检查方法名
            if (!method.getName().equals(methodName)) {
                continue;
            }
            // 检查参数
            if (method.getParameterCount() != 1) {
                continue;
            }
            // 检查修饰符
            int modifiers = method.getModifiers();
            if (Modifier.isNative(modifiers)
                    || Modifier.isAbstract(modifiers)
                    || !Modifier.isPublic(modifiers)
            ) {
                continue;
            }
            Class<?> paramType = method.getParameterTypes()[0];
            if (paramType == propertyType) {
                return method;
            }
            // 其他类型
            if (paramType.isAssignableFrom(propertyType) || propertyType.isAssignableFrom(paramType)) {
                return method;
            }
            // boolean类型
            if (Boolean.class.isAssignableFrom(propertyType) && boolean.class == paramType) {
                return method;
            }
            // 数字类型
            if (Number.class.isAssignableFrom(propertyType)) {
                if (Number.class.isAssignableFrom(paramType)) {
                    return method;
                }
                if (Arrays.asList(byte.class, short.class, int.class, float.class, double.class, long.class).contains(paramType)) {
                    return method;
                }
            }
            // 字符类型
            if (CharSequence.class.isAssignableFrom(propertyType) && CharSequence.class.isAssignableFrom(paramType)) {
                return method;
            }
        }
        return null;
    }

    public static void doSetter(Object obj, Method method, Object value) throws InvocationTargetException, IllegalAccessException {
        if (value == null || method.getParameterCount() == 0) {
            return;
        }
        Class<?> valType = value.getClass();
        Class<?> paramType = method.getParameterTypes()[0];
        // 对象
        if (JsonObject.class.isAssignableFrom(valType)) {
            JsonObject object = (JsonObject) value;
            Object bean = JSONParser.INSTANCE.toBean(object, paramType);
            method.setAccessible(true);
            method.invoke(obj, bean);
        } else if (JsonArray.class.isAssignableFrom(valType)) {// 树组
            JsonArray array = (JsonArray) value;
            Type type = method.getGenericParameterTypes()[0];
            if (type instanceof ParameterizedType parameterizedType) {
                type = parameterizedType.getActualTypeArguments()[0];
            }
            paramType = ClassUtil.forName(type.getTypeName());
            List<?> list = JSONParser.INSTANCE.toBean(array, paramType);
            method.setAccessible(true);
            method.invoke(obj, list);
        } else if (Number.class.isAssignableFrom(valType)) {// 数字
            Number number = (Number) value;
            if (paramType == int.class) {
                method.setAccessible(true);
                method.invoke(obj, number.intValue());
            } else if (paramType == long.class) {
                method.setAccessible(true);
                method.invoke(obj, number.longValue());
            } else if (paramType == float.class) {
                method.setAccessible(true);
                method.invoke(obj, number.floatValue());
            } else if (paramType == double.class) {
                method.setAccessible(true);
                method.invoke(obj, number.doubleValue());
            } else if (paramType == byte.class) {
                method.setAccessible(true);
                method.invoke(obj, number.byteValue());
            } else if (paramType == short.class) {
                method.setAccessible(true);
                method.invoke(obj, number.shortValue());
            }
        } else if (Boolean.class.isAssignableFrom(valType)) {// boolean
            boolean sequence = (boolean) value;
            method.setAccessible(true);
            method.invoke(obj, sequence);
        } else if (CharSequence.class.isAssignableFrom(valType)) {// 字符
            CharSequence sequence = (CharSequence) value;
            if (paramType == String.class) {
                method.setAccessible(true);
                method.invoke(obj, sequence.toString());
            } else if (paramType == StringBuilder.class) {
                method.setAccessible(true);
                method.invoke(obj, new StringBuilder(sequence.toString()));
            } else if (paramType == StringBuffer.class) {
                method.setAccessible(true);
                method.invoke(obj, new StringBuffer(sequence.toString()));
            }
        }
    }

    public static Object doGetter(Object obj, Method method) throws InvocationTargetException, IllegalAccessException {
        return ReflectUtil.invoke(obj, method);
    }
}
