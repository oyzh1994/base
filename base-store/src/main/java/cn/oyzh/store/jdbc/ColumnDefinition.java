package cn.oyzh.store.jdbc;

import cn.oyzh.store.jdbc.h2.H2Util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 列定义
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class ColumnDefinition {

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * 是否主键
     */
    private boolean primaryKey;

    /**
     * 是否自动生成
     */
    private boolean autoGeneration;

    /**
     * 根据字段构建列定义
     *
     * @param field 字段
     * @return 列定义
     */
    public static ColumnDefinition ofField(Field field) {
        if (field != null) {
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) || Modifier.isNative(modifiers)) {
                return null;
            }
            Column column = field.getAnnotation(Column.class);
            if (column == null) {
                return null;
            }
            ColumnDefinition columnDefinition = new ColumnDefinition();
            if (column.value().isEmpty()) {
                columnDefinition.setColumnName(field.getName());
            } else {
                columnDefinition.setColumnName(column.value());
            }
            if (column.type().isEmpty()) {
                String columnType = "";
                if (JdbcManager.dialect == JdbcDialect.H2) {
                    columnType = H2Util.toSqlType(field.getType());
//                } else if (JdbcManager.dialect == JdbcDialect.SQLITE) {
//                    columnType = SqlLiteUtil.toSqlType(field.getType());
                }
                columnDefinition.setColumnType(columnType);
            } else {
                columnDefinition.setColumnType(column.type());
            }
            columnDefinition.setFieldName(field.getName());
            PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
            if (primaryKey != null) {
                columnDefinition.setPrimaryKey(true);
                columnDefinition.setAutoGeneration(primaryKey.autoGeneration());
            }
            return columnDefinition;
        }
        return null;
    }

    /**
     * 获取列名称，H2 方言下返回大写
     *
     * @return 列名称
     */
    public String getColumnName() {
        if (JdbcManager.dialect == JdbcDialect.H2) {
            return this.columnName.toUpperCase();
        }
        return this.columnName;
    }

    /**
     * 获取字段名称
     *
     * @return 字段名称
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * 设置字段名称
     *
     * @param fieldName 字段名称
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * 设置列名称
     *
     * @param columnName 列名称
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * 获取列类型
     *
     * @return 列类型
     */
    public String getColumnType() {
        return columnType;
    }

    /**
     * 设置列类型
     *
     * @param columnType 列类型
     */
    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    /**
     * 是否为主键
     *
     * @return 是否主键
     */
    public boolean isPrimaryKey() {
        return primaryKey;
    }

    /**
     * 设置是否主键
     *
     * @param primaryKey 是否主键
     */
    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    /**
     * 是否自动生成
     *
     * @return 是否自动生成
     */
    public boolean isAutoGeneration() {
        return autoGeneration;
    }

    /**
     * 设置是否自动生成
     *
     * @param autoGeneration 是否自动生成
     */
    public void setAutoGeneration(boolean autoGeneration) {
        this.autoGeneration = autoGeneration;
    }
}
