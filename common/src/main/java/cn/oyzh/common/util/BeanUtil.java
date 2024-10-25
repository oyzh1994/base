package cn.oyzh.common.util;

import cn.oyzh.common.exception.InvalidParamException;
import cn.oyzh.common.exception.PropertyNotFoundException;
import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author oyzh
 * @since 2024-09-24
 */
@UtilityClass
public class BeanUtil {

    public static <T> T getValue(Object bean, String name) {
        if (bean == null) {
            throw new InvalidParamException("bean");
        }
        if (name == null) {
            throw new InvalidParamException("name");
        }
        String property = StringUtil.upperFirst(name);
        Method method = null;
        try {
            method = bean.getClass().getMethod("get" + property);
        } catch (NoSuchMethodException ignored) {

        }
        if (method == null) {
            try {
                method = bean.getClass().getMethod("is" + property);
            } catch (NoSuchMethodException ignored) {

            }
        }
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

}
