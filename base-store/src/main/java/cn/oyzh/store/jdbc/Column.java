package cn.oyzh.store.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段注解
 *
 * @author oyzh
 * @since 2024-09-24
 */
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * 列名称，为空时使用 Java 字段名
     *
     * @return 列名称
     */
    String value() default "";

    /**
     * 列类型，为空时根据 Java 字段类型自动推断
     *
     * @return 列类型
     */
    String type() default "";
}
