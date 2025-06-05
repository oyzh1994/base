package cn.oyzh.common.bean;

import cn.oyzh.common.exception.InvalidParamException;
import cn.oyzh.common.exception.PropertyNotFoundException;
import cn.oyzh.common.util.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * bean工具类
 *
 * @author oyzh
 * @since 2024-09-24
 */
public class BeanUtil {

    /**
     * @param bean 对象
     * @param name 名称
     * @param <T>  参数类型
     * @return 对象
     */
    public static <T> T getValue(Object bean, String name) {
        return getValue(bean, name, true);
    }

    /**
     * 获取值
     *
     * @param bean      对象
     * @param name      名称
     * @param callSuper 调用父类
     * @param <T>       参数类型
     * @return 对象
     */
    public static <T> T getValue(Object bean, String name, boolean callSuper) {
        if (bean == null) {
            throw new InvalidParamException("bean");
        }
        if (name == null) {
            throw new InvalidParamException("name");
        }
        Method method = getGetterMethod(bean.getClass(), name, callSuper);
        if (method == null) {
            throw new PropertyNotFoundException(name);
        }
        try {
            method.setAccessible(true);
            return (T) method.invoke(bean);
        } catch (IllegalAccessException | InvocationTargetException ignored) {

        }
        return null;
    }

    /**
     * 获取get放
     *
     * @param clazz     类
     * @param name      名称
     * @param callSuper 是否调用父类
     * @return 方法
     */
    public static Method getGetterMethod(Class<?> clazz, String name, boolean callSuper) {
        String property = StringUtil.upperFirst(name);
        Method method = null;
        try {
            method = clazz.getMethod("get" + property);
        } catch (NoSuchMethodException ignored) {

        }
        if (method == null) {
            try {
                method = clazz.getMethod("is" + property);
            } catch (NoSuchMethodException ignored) {

            }
        }
        if (method == null && callSuper) {
            clazz = clazz.getSuperclass();
            if (clazz != Object.class) {
                method = getGetterMethod(clazz, name, true);
            }
        }
        return method;
    }

    /**
     * 设置值
     *
     * @param bean  对象
     * @param name  名称
     * @param value 值
     */
    public static void setValue(Object bean, String name, Object value) {
        setValue(bean, name, value, true);
    }

    /**
     * 获取get方法
     *
     * @param bean      对象
     * @param name      名称
     * @param value     值
     * @param callSuper 调用父类
     */
    public static void setValue(Object bean, String name, Object value, boolean callSuper) {
        if (bean == null) {
            throw new InvalidParamException("bean");
        }
        if (name == null) {
            throw new InvalidParamException("name");
        }
        Method method = getSetterMethod(bean.getClass(), name, value == null ? null : value.getClass(), callSuper);
        if (method == null) {
            throw new PropertyNotFoundException(name);
        }
        try {
            method.setAccessible(true);
            method.invoke(bean, value);
        } catch (IllegalAccessException | InvocationTargetException ignored) {

        }
    }

    /**
     * 获取set方法
     *
     * @param clazz      类
     * @param name       名称
     * @param paramClass 参数类
     * @param callSuper  是否调用父类
     * @return 方法
     */
    public static Method getSetterMethod(Class<?> clazz, String name, Class<?> paramClass, boolean callSuper) {
        String property = StringUtil.upperFirst(name);
        Method method = null;
        String mName = "set" + property;
        for (Method clazzMethod : clazz.getMethods()) {
            if (!clazzMethod.getName().equals(mName)) {
                continue;
            }
            Class<?>[] parameterTypes = clazzMethod.getParameterTypes();
            if (clazzMethod.getParameterTypes().length != 1) {
                continue;
            }
            if (paramClass == null) {
                method = clazzMethod;
                break;
            }
            Class<?> pClass = parameterTypes[0];
            if (paramClass == pClass || paramClass.isAssignableFrom(pClass)) {
                method = clazzMethod;
                break;
            }
            if (pClass == int.class || paramClass == Integer.class) {
                method = clazzMethod;
                break;
            }
            if (pClass == long.class || paramClass == Long.class) {
                method = clazzMethod;
                break;
            }
            if (pClass == double.class || paramClass == Double.class) {
                method = clazzMethod;
                break;
            }
            if (pClass == float.class || paramClass == Float.class) {
                method = clazzMethod;
                break;
            }
            if (pClass == byte.class || paramClass == Byte.class) {
                method = clazzMethod;
                break;
            }
            if (pClass == short.class || paramClass == Short.class) {
                method = clazzMethod;
                break;
            }
            if (pClass == char.class || paramClass == Character.class) {
                method = clazzMethod;
                break;
            }
        }
        if (method == null && callSuper) {
            clazz = clazz.getSuperclass();
            if (clazz != Object.class) {
                method = getSetterMethod(clazz, name, paramClass, true);
            }
        }
        return method;
    }


}
