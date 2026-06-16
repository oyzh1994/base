package cn.oyzh.store.jdbc.h2;

import cn.oyzh.common.util.CollectionUtil;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-09-24
 */
public class H2Util {

    public static String wrap(String obj) {
        return "`" + obj.toUpperCase() + "`";
    }

    public static Object wrapData(Object data) {
        if (data == null) {
            return "";
        }
        if (data instanceof byte[] || data instanceof Byte[] || data instanceof Number) {
            return data;
        }
        return "'" + data + "'";
    }

    public static String toSqlType(Class<?> javaType) {
        if (CollectionUtil.contains(List.of(Long.class, long.class), javaType)) {
            return "bigint";
        }
        if (CollectionUtil.contains(List.of(Integer.class, int.class), javaType)) {
            return "integer";
        }
        if (CollectionUtil.contains(List.of(Short.class, short.class), javaType)) {
            return "ALLINT";
        }
        if (CollectionUtil.contains(List.of(Byte.class, byte.class, Boolean.class, boolean.class), javaType)) {
            return "TINYINT";
        }
        if (CollectionUtil.contains(List.of(String.class, StringBuilder.class, StringBuffer.class, Character.class, char.class), javaType)) {
            return "varchar";
        }
        if (CollectionUtil.contains(List.of(Float.class, float.class), javaType)) {
            return "real";
        }
        if (CollectionUtil.contains(List.of(Double.class, double.class), javaType)) {
            return "double";
        }
        if (CollectionUtil.contains(List.of(Byte[].class, byte[].class), javaType)) {
            return "blob";
        }
        if (CollectionUtil.contains(List.of(Date.class, java.util.Date.class, LocalDateTime.class), javaType)) {
            return "timestamp";
        }
        if (CollectionUtil.contains(List.of(LocalDate.class), javaType)) {
            return "date";
        }
        if (CollectionUtil.contains(List.of(LocalTime.class), javaType)) {
            return "time";
        }
        return "varchar";
    }

    public static boolean checkSqlType(String sqlType, String typeName) {
        if (sqlType.equalsIgnoreCase(typeName)) {
            return true;
        }
        if (sqlType.equalsIgnoreCase("varchar") && typeName.equalsIgnoreCase("CHARACTER VARYING")) {
            return true;
        }
        if (sqlType.equalsIgnoreCase("blob") && typeName.equalsIgnoreCase("BINARY LARGE OBJECT")) {
            return true;
        }
        if (sqlType.equalsIgnoreCase("double") && typeName.equalsIgnoreCase("DOUBLE PRECISION")) {
            return true;
        }
        if (sqlType.equalsIgnoreCase("byte") && typeName.equalsIgnoreCase("TINYINT")) {
            return true;
        }
        if (sqlType.equalsIgnoreCase("boolean") && typeName.equalsIgnoreCase("TINYINT")) {
            return true;
        }
        return false;
    }
}
