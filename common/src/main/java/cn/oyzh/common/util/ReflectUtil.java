package cn.oyzh.common.util;


import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author oyzh
 * @since 2024-09-24
 */
public class ReflectUtil {

    public static <T> T getFieldValue(Object object, String fieldName) {
        Field field = getField(object.getClass(), fieldName);
        return getFieldValue(field, object);
    }

    public static <T> T getFieldValue(Field field, Object object) {
        try {
            field.setAccessible(true);
            return (T) field.get(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void setFieldValue(Field field, Object value, Object object) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

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

    public static void clearFieldValue(Field field, Object object) throws SecurityException, IllegalAccessException {
        field.setAccessible(true);
        field.set(object, null);
    }

    public static Field getField(Class<?> beanClass, String fieldName) throws SecurityException {
        return getField(beanClass, fieldName, true, false);
    }

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

    public static Method getMethod(Class<?> beanClass, String methodName, Class<?>... paramTypes) throws SecurityException {
        return getMethod(beanClass, methodName, true, false, paramTypes);
    }

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

    public static Object invoke(Object obj, Method method, Object... params) {
        if (method != null) {
            try {
                method.setAccessible(true);
                return method.invoke(obj, params);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
