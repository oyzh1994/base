package cn.oyzh.common.bean;

import cn.oyzh.common.util.ReflectUtil;
import cn.oyzh.common.util.StringUtil;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

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
}
