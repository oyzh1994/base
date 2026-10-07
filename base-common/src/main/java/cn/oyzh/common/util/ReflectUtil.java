package cn.oyzh.common.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class ReflectUtil {

    /**
     * 私有构造，禁止实例化
     */
    private ReflectUtil() {
    }

    /**
     * 获取对象的字段值
     *
     * @param object    对象
     * @param fieldName 字段名
     * @param <T>       字段值类型
     * @return 字段值
     */
    public static <T> T getFieldValue(Object object, String fieldName) {
        Field field = getField(object.getClass(), fieldName);
        return getFieldValue(field, object);
    }

    /**
     * 获取对象的字段值（含父类字段）
     *
     * @param object    对象
     * @param fieldName 字段名
     * @param <T>       字段值类型
     * @return 字段值
     */
    public static <T> T getFieldValue2(Object object, String fieldName) {
        Field field = getField2(object.getClass(), fieldName);
        return getFieldValue(field, object);
    }

    /**
     * 获取字段值
     *
     * @param field  字段
     * @param object 对象
     * @param <T>    字段值类型
     * @return 字段值，字段为null时返回null
     */
    public static <T> T getFieldValue(Field field, Object object) {
        if (field == null) {
            return null;
        }
        try {
            field.setAccessible(true);
            return (T) field.get(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 设置字段值
     *
     * @param field  字段
     * @param value  字段值
     * @param object 对象
     */
    public static void setFieldValue(Field field, Object value, Object object) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置字段值
     *
     * @param fieldName 字段名
     * @param value     字段值
     * @param object    对象，可为Class对象（表示静态字段）
     */
    public static void setFieldValue(String fieldName, Object value, Object object) {
        Field field;
        if (object instanceof Class<?> clazz) {
            field = getField(clazz, fieldName);
            setFieldValue(field, value, null);
        } else {
            field = getField(object.getClass(), fieldName);
            setFieldValue(field, value, object);
        }
    }

    /**
     * 设置字段值（查找时含父类字段）
     *
     * @param fieldName 字段名
     * @param value     字段值
     * @param object    对象，可为Class对象（表示静态字段）
     */
    public static void setFieldValue2(String fieldName, Object value, Object object) {
        Field field;
        if (object instanceof Class<?> clazz) {
            field = getField2(clazz, fieldName);
            setFieldValue(field, value, null);
        } else {
            field = getField2(object.getClass(), fieldName);
            setFieldValue(field, value, object);
        }
    }

    /**
     * 清空字段值（置为null）
     *
     * @param field  字段
     * @param object 对象
     * @throws SecurityException        访问字段受限时抛出
     * @throws IllegalAccessException   字段不可访问时抛出
     */
    public static void clearFieldValue(Field field, Object object) throws SecurityException, IllegalAccessException {
        field.setAccessible(true);
        field.set(object, null);
    }

    /**
     * 获取字段（含非public字段）
     *
     * @param beanClass 类
     * @param fieldName 字段名
     * @return 字段，未找到时返回null
     * @throws SecurityException 访问字段受限时抛出
     */
    public static Field getField(Class<?> beanClass, String fieldName) throws SecurityException {
        return getField(beanClass, fieldName, true, false);
    }

    /**
     * 获取字段（含非public字段和父类字段）
     *
     * @param beanClass 类
     * @param fieldName 字段名
     * @return 字段，未找到时返回null
     * @throws SecurityException 访问字段受限时抛出
     */
    public static Field getField2(Class<?> beanClass, String fieldName) throws SecurityException {
        return getField(beanClass, fieldName, true, true);
    }

    /**
     * 获取字段
     *
     * @param beanClass    类
     * @param fieldName    字段名
     * @param withDeclared 是否包含非public字段
     * @param withSuper    是否查找父类
     * @return 字段，未找到时返回null
     * @throws SecurityException 访问字段受限时抛出
     */
    public static Field getField(Class<?> beanClass, String fieldName, boolean withDeclared, boolean withSuper) throws SecurityException {
        Class<?> searchType = beanClass;
        Field field = null;
        while (searchType != null) {
            try {
                field = searchType.getField(fieldName);
            } catch (NoSuchFieldException ignore) {
            }
            if (null == field && withDeclared) {
                try {
                    field = searchType.getDeclaredField(fieldName);
                } catch (NoSuchFieldException ignore) {
                }
            }
            searchType = withSuper ? searchType.getSuperclass() : null;
        }
        return field;
    }

    /**
     * 获取字段数组
     *
     * @param beanClass    类
     * @param withDeclared 是否包含非public字段
     * @param withSuper    是否查找父类
     * @return 字段数组
     * @throws SecurityException 访问字段受限时抛出
     */
    public static Field[] getFields(Class<?> beanClass, boolean withDeclared, boolean withSuper) throws SecurityException {
        Field[] allFields = null;
        Class<?> searchType = beanClass;
        Field[] fields;
        while (searchType != null) {
            if (withDeclared) {
                fields = searchType.getDeclaredFields();
            } else {
                fields = searchType.getFields();
            }
            if (null == allFields) {
                allFields = fields;
            } else {
                allFields = ArrayUtil.append(allFields, fields);
            }
            searchType = withSuper ? searchType.getSuperclass() : null;
        }
        return allFields;
    }

    /**
     * 获取方法
     *
     * @param obj        对象
     * @param methodName 方法名
     * @param paramTypes 参数类型
     * @return 方法，未找到时返回null
     * @throws SecurityException 访问方法受限时抛出
     */
    public static Method getMethod(Object obj, String methodName, Class<?>... paramTypes) throws SecurityException {
        return getMethod(obj.getClass(), methodName, true, false, paramTypes);
    }

    /**
     * 获取方法
     *
     * @param beanClass  类
     * @param methodName 方法名
     * @param paramTypes 参数类型
     * @return 方法，未找到时返回null
     * @throws SecurityException 访问方法受限时抛出
     */
    public static Method getMethod(Class<?> beanClass, String methodName, Class<?>... paramTypes) throws SecurityException {
        return getMethod(beanClass, methodName, true, false, paramTypes);
    }

    /**
     * 获取方法
     *
     * @param beanClass    类
     * @param methodName   方法名
     * @param withDeclared 是否包含非public方法
     * @param withSuper    是否查找父类
     * @param paramTypes   参数类型
     * @return 方法，未找到时返回null
     * @throws SecurityException 访问方法受限时抛出
     */
    public static Method getMethod(Class<?> beanClass, String methodName, boolean withDeclared, boolean withSuper, Class<?>... paramTypes) throws SecurityException {
        Class<?> searchType = beanClass;
        Method method = null;
        while (searchType != null) {
            try {
                method = searchType.getMethod(methodName, paramTypes);
            } catch (NoSuchMethodException ignore) {
            }
            if (null == method && withDeclared) {
                try {
                    method = searchType.getDeclaredMethod(methodName, paramTypes);
                } catch (NoSuchMethodException ignore) {
                }
            }
            searchType = withSuper ? searchType.getSuperclass() : null;
        }
        return method;
    }

    /**
     * 获取方法数组
     *
     * @param beanClass    类
     * @param withDeclared 是否包含非public方法
     * @param withSuper    是否查找父类
     * @return 方法数组
     * @throws SecurityException 访问方法受限时抛出
     */
    public static Method[] getMethods(Class<?> beanClass, boolean withDeclared, boolean withSuper) throws SecurityException {
        Method[] allMethods = null;
        Class<?> searchType = beanClass;
        Method[] methods;
        while (searchType != null) {
            if (withDeclared) {
                methods = searchType.getDeclaredMethods();
            } else {
                methods = searchType.getMethods();
            }
            if (null == allMethods) {
                allMethods = methods;
            } else {
                allMethods = ArrayUtil.append(allMethods, methods);
            }
            searchType = withSuper ? searchType.getSuperclass() : null;
        }
        return allMethods;
    }

    //    public static Object invoke(Object obj, Method method) throws InvocationTargetException, IllegalAccessException {
    //        if (method == null) {
    //            return null;
    //        }
    //        method.setAccessible(true);
    //        return method.invoke(obj);
    //    }

    /**
     * 根据方法名和参数调用方法
     *
     * @param obj        对象
     * @param methodName 方法名
     * @param params     方法参数
     * @return 方法返回值
     */
    public static Object invoke(Object obj, String methodName, Object... params) {
        Method method;
        if (params == null || params.length == 0) {
            method = getMethod(obj.getClass(), methodName, true, true);
        } else {
            Class<?>[] paramTypes = new Class[params.length];
            for (int i = 0; i < params.length; i++) {
                paramTypes[i] = params[i].getClass();
            }
            method = getMethod(obj.getClass(), methodName, true, true, paramTypes);
        }
        return invoke(obj, method, params);
    }

    /**
     * 调用
     *
     * @param obj    对象
     * @param method 方法
     * @param params 参数
     * @return 结果
     */
    public static Object invoke(Object obj, Method method, Object... params) {
        if (method == null) {
            throw new NullPointerException("method");
        }
        method.setAccessible(true);
        return invokeOnly(obj, method, params);
    }

    /**
     * 仅调用
     *
     * @param obj    对象
     * @param method 方法
     * @param params 参数
     * @return 结果
     */
    public static Object invokeOnly(Object obj, Method method, Object... params) {
        if (method != null) {
            try {
                return method.invoke(obj, params);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Object类的方法名缓存
     */
    private static List<String> objectMethodNames;

    /**
     * 获取Object类定义的方法名列表
     *
     * @return Object类方法名列表
     */
    public static List<String> objectMethodNames() {
        if (objectMethodNames == null) {
            objectMethodNames = new ArrayList<>();
            objectMethodNames.add("toString");
            objectMethodNames.add("notify");
            objectMethodNames.add("notifyAll");
            objectMethodNames.add("wait");
            objectMethodNames.add("getClass");
            objectMethodNames.add("hashCode");
            objectMethodNames.add("equals");
            objectMethodNames.add("clone");
            objectMethodNames.add("finalize");
        }
        return objectMethodNames;
    }
}
