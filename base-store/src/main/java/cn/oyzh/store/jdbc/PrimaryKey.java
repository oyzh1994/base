package cn.oyzh.store.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主键
 *
 * @author oyzh
 * @since 2024-10-18
 */
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {

    /**
     * 是否自动生成主键值
     *
     * @return 结果
     */
    boolean autoGeneration() default true;
}
