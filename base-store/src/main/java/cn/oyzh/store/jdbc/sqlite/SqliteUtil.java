package cn.oyzh.store.jdbc.sqlite;

import cn.oyzh.store.jdbc.ColumnDefinition;
import cn.oyzh.store.jdbc.JdbcConn;
import cn.oyzh.store.jdbc.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * sqlite工具类
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class SqliteUtil {

    /**
     * 包装标识符
     *
     * @param obj 标识符
     * @return 包装后的标识符
     */
    public static String wrap(String obj) {
        if (obj == null) {
            throw new IllegalArgumentException("obj");
        }
        return "\"" + obj.replace("\"", "\"\"") + "\"";
    }

    /**
     * 包装数据值
     *
     * @param data 数据值
     * @return 包装后的数据值
     */
    public static Object wrapData(Object data) {
        if (data == null) {
            return "";
        }
        if (data instanceof Date || data instanceof LocalDate || data instanceof LocalTime
                || data instanceof LocalDateTime || data instanceof ZonedDateTime) {
            data = toSqlValue(data);
        }
        if (data instanceof byte[] bytes) {
            return toBlobLiteral(bytes);
        }
        if (data instanceof Number) {
            return data;
        }
        if (data instanceof Byte[] bytes) {
            return toBlobLiteral(toByteArray(bytes));
        }
        if (data instanceof Boolean bool) {
            return bool ? 1 : 0;
        }
        return "'" + data.toString().replace("'", "''") + "'";
    }

    /**
     * 转换为sqlite驱动可直接写入的数据
     *
     * @param data Java数据
     * @return sqlite驱动数据
     */
    public static Object toSqlValue(Object data) {
        if (data == null) {
            return null;
        }
        if (data instanceof byte[] || data instanceof Number) {
            return data;
        }
        if (data instanceof Byte[] bytes) {
            return toByteArray(bytes);
        }
        if (data instanceof Boolean bool) {
            return bool ? 1 : 0;
        }
        if (data instanceof Time time) {
            return time.toLocalTime().toString();
        }
        if (data instanceof Timestamp timestamp) {
            return timestamp.toInstant().toString();
        }
        if (data instanceof Date date) {
            return new Timestamp(date.getTime()).toInstant().toString();
        }
        if (data instanceof LocalDate || data instanceof LocalTime
                || data instanceof LocalDateTime || data instanceof ZonedDateTime) {
            return data.toString();
        }
        if (data instanceof CharSequence || data instanceof Character) {
            return data.toString();
        }
        return data.toString();
    }

    /**
     * 将Java类型映射为sqlite的sql类型
     *
     * @param javaType Java类型
     * @return sql类型
     */
    public static String toSqlType(Class<?> javaType) {
        if (javaType == long.class || javaType == Long.class
                || javaType == int.class || javaType == Integer.class
                || javaType == short.class || javaType == Short.class
                || javaType == byte.class || javaType == Byte.class
                || javaType == boolean.class || javaType == Boolean.class) {
            return "integer";
        }
        if (javaType == float.class || javaType == Float.class
                || javaType == double.class || javaType == Double.class) {
            return "real";
        }
        if (javaType == byte[].class || javaType == Byte[].class) {
            return "blob";
        }
        return "text";
    }

    /**
     * 校验sql类型是否匹配
     *
     * @param sqlType  sql类型
     * @param typeName 数据库中的类型名称
     * @return 结果
     */
    public static boolean checkSqlType(String sqlType, String typeName) {
        return normalizeType(sqlType).equals(normalizeType(typeName));
    }

    /**
     * 创建sqlite建表sql
     *
     * @param tableName 表名称
     * @param columns   列定义
     * @return 建表sql
     */
    static String createTableSql(String tableName, List<ColumnDefinition> columns) {
        if (columns == null || columns.isEmpty()) {
            throw new IllegalArgumentException("columns");
        }
        StringBuilder sql = new StringBuilder("CREATE TABLE ");
        sql.append(wrap(tableName)).append(" (");
        for (ColumnDefinition column : columns) {
            sql.append(wrap(column.getColumnName()))
                    .append(" ")
                    .append(column.getColumnType());
            if (column.isPrimaryKey()) {
                sql.append(" PRIMARY KEY");
            }
            sql.append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        return sql.append(")").toString();
    }

    /**
     * 判断现有表结构是否与目标表结构一致
     *
     * @param connection    数据库连接
     * @param tableName     表名称
     * @param targetColumns 目标列定义
     * @return 结果
     * @throws SQLException sql异常
     */
    static boolean schemaMatches(JdbcConn connection, String tableName,
                                 List<ColumnDefinition> targetColumns) throws SQLException {
        Map<String, String> currentColumns = readColumns(connection, tableName);
        if (currentColumns.size() != targetColumns.size()) {
            return false;
        }
        for (ColumnDefinition targetColumn : targetColumns) {
            String typeName = currentColumns.get(targetColumn.getColumnName().toLowerCase(Locale.ROOT));
            if (typeName == null || !checkSqlType(targetColumn.getColumnType(), typeName)) {
                return false;
            }
        }
        return readPrimaryKeys(connection, tableName).equals(primaryKeyNames(targetColumns));
    }

    /**
     * 重建表并保留同名字段数据
     *
     * @param connection 数据库连接
     * @param tableName  表名称
     * @param columns    目标列定义
     * @throws SQLException sql异常
     */
    static void rebuildTable(JdbcConn connection, String tableName,
                             List<ColumnDefinition> columns) throws SQLException {
        Map<String, String> currentColumns = readColumns(connection, tableName);
        String backupName = tableName + "_backup_" + UUID.randomUUID().toString().replace("-", "");
        JdbcHelper.execute(connection, "ALTER TABLE " + wrap(tableName) + " RENAME TO " + wrap(backupName));

        List<String> commonColumns = new ArrayList<>();
        for (ColumnDefinition column : columns) {
            if (currentColumns.containsKey(column.getColumnName().toLowerCase(Locale.ROOT))) {
                commonColumns.add(column.getColumnName());
            }
        }

        JdbcHelper.execute(connection, createTableSql(tableName, columns));
        if (!commonColumns.isEmpty()) {
            StringBuilder sql = new StringBuilder("INSERT INTO ");
            sql.append(wrap(tableName)).append(" (");
            for (String column : commonColumns) {
                sql.append(wrap(column)).append(",");
            }
            sql.deleteCharAt(sql.length() - 1).append(") SELECT ");
            for (String column : commonColumns) {
                sql.append(wrap(column)).append(",");
            }
            sql.deleteCharAt(sql.length() - 1)
                    .append(" FROM ")
                    .append(wrap(backupName));
            JdbcHelper.execute(connection, sql.toString());
        }
        JdbcHelper.execute(connection, "DROP TABLE " + wrap(backupName));
    }

    private static Map<String, String> readColumns(JdbcConn connection, String tableName) throws SQLException {
        Map<String, String> columns = new LinkedHashMap<>();
        ResultSet resultSet = connection.getColumns(tableName, null);
        try (resultSet) {
            while (resultSet.next()) {
                String columnName = resultSet.getString("COLUMN_NAME");
                columns.put(columnName.toLowerCase(Locale.ROOT), resultSet.getString("TYPE_NAME"));
            }
        }
        return columns;
    }

    private static Set<String> readPrimaryKeys(JdbcConn connection, String tableName) throws SQLException {
        Set<String> primaryKeys = new LinkedHashSet<>();
        ResultSet resultSet = connection.getMetaData().getPrimaryKeys(null, null, tableName);
        try (resultSet) {
            while (resultSet.next()) {
                primaryKeys.add(resultSet.getString("COLUMN_NAME").toLowerCase(Locale.ROOT));
            }
        }
        return primaryKeys;
    }

    private static Set<String> primaryKeyNames(List<ColumnDefinition> columns) {
        Set<String> primaryKeys = new LinkedHashSet<>();
        for (ColumnDefinition column : columns) {
            if (column.isPrimaryKey()) {
                primaryKeys.add(column.getColumnName().toLowerCase(Locale.ROOT));
            }
        }
        return primaryKeys;
    }

    private static String normalizeType(String type) {
        if (type == null) {
            return "";
        }
        String normalized = type.trim().toUpperCase(Locale.ROOT);
        int parameterIndex = normalized.indexOf('(');
        if (parameterIndex >= 0) {
            normalized = normalized.substring(0, parameterIndex).trim();
        }
        if (normalized.contains("CHAR") || normalized.contains("TEXT") || normalized.contains("CLOB")) {
            return "TEXT";
        }
        if (normalized.contains("INT") || normalized.contains("BOOL")) {
            return "INTEGER";
        }
        if (normalized.contains("BLOB") || normalized.contains("BINARY")) {
            return "BLOB";
        }
        if (normalized.contains("REAL") || normalized.contains("FLOA") || normalized.contains("DOUB")) {
            return "REAL";
        }
        if (normalized.contains("DEC") || normalized.contains("NUMERIC")) {
            return "NUMERIC";
        }
        return normalized;
    }

    private static byte[] toByteArray(Byte[] bytes) {
        byte[] result = new byte[bytes.length];
        for (int index = 0; index < bytes.length; index++) {
            result[index] = bytes[index];
        }
        return result;
    }

    private static String toBlobLiteral(byte[] bytes) {
        StringBuilder value = new StringBuilder("X'");
        for (byte data : bytes) {
            value.append(String.format("%02X", data));
        }
        return value.append("'").toString();
    }
}
