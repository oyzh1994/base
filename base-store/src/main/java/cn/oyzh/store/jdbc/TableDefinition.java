package cn.oyzh.store.jdbc;


import cn.oyzh.common.util.ReflectUtil;
import cn.oyzh.common.util.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 表定义
 *
 * @author oyzh
 * @since 2024-09-24
 */
public class TableDefinition {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 列定义列表
     */
    private List<ColumnDefinition> columns;

    /**
     * 添加列定义
     *
     * @param columnDefinition 列定义
     */
    public void addColumn(ColumnDefinition columnDefinition) {
        if (this.columns == null) {
            this.columns = new ArrayList<>();
        }
        this.columns.add(columnDefinition);
    }

    /**
     * 是否包含指定列
     *
     * @param columnName 列名称
     * @return 结果
     */
    public boolean hasColumn(String columnName) {
        for (ColumnDefinition column : this.columns) {
            if (StringUtil.equalsIgnoreCase(column.getColumnName(), columnName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取主键列定义
     *
     * @return 主键列定义
     */
    public ColumnDefinition primaryKey() {
        for (ColumnDefinition columnDefinition : columns) {
            if (columnDefinition.isPrimaryKey()) {
                return columnDefinition;
            }
        }
        return null;
    }

    /**
     * 获取主键列名称
     *
     * @return 主键列名称
     */
    public String primaryKeyName() {
        ColumnDefinition primaryKeyColumn = this.primaryKey();
        return primaryKeyColumn == null ? null : primaryKeyColumn.getColumnName();
    }

    /**
     * 获取模型的主键字段
     *
     * @param model 模型对象
     * @return 主键字段
     * @throws IllegalAccessException 异常
     */
    public PrimaryKeyColumn primaryKeyColumn(Object model) throws IllegalAccessException {
        ColumnDefinition column = this.primaryKey();
        if (column != null) {
            Field field = ReflectUtil.getField(model.getClass(), column.getFieldName(), true, true);
            field.setAccessible(true);
            Object primaryVal = field.get(model);
            return new PrimaryKeyColumn(column.getColumnName(), primaryVal);
        }
        return null;
    }

    /**
     * 处理模型的主键值，主键为空且允许自动生成时填充生成值
     *
     * @param model 模型对象
     * @throws IllegalAccessException 异常
     */
    public void handlePrimaryKeyValue(Object model) throws IllegalAccessException {
        ColumnDefinition columnDefinition = this.primaryKey();
        if (columnDefinition != null) {
            Field field = ReflectUtil.getField(model.getClass(), columnDefinition.getFieldName(), true, true);
            field.setAccessible(true);
            Object primaryVal = field.get(model);
            if (primaryVal == null) {
                if (columnDefinition.isAutoGeneration()) {
                    primaryVal = KeyGenerator.generatorKey(columnDefinition.getColumnType());
                }
                if (primaryVal != null) {
                    field.set(model, primaryVal);
                }
            }
        }
    }

    /**
     * 获取模型的主键值
     *
     * @param model 模型对象
     * @return 主键值
     * @throws IllegalAccessException 异常
     */
    public Object getPrimaryKeyValue(Object model) throws IllegalAccessException {
        ColumnDefinition columnDefinition = this.primaryKey();
        if (columnDefinition != null) {
            Field field = ReflectUtil.getField(model.getClass(), columnDefinition.getFieldName(), true, true);
            field.setAccessible(true);
            return field.get(model);
        }
        return null;
    }

    /**
     * 根据类型构建表定义
     *
     * @param clazz 类型
     * @return 表定义
     */
    public static TableDefinition ofClass(Class<?> clazz) {
        if (clazz != null) {
            TableDefinition definition = new TableDefinition();
            Table table = clazz.getAnnotation(Table.class);
            if (table != null) {
                if (table.value().isEmpty()) {
                    definition.tableName = clazz.getSimpleName().toLowerCase();
                } else {
                    definition.tableName = table.value();
                }
            }
            Field[] fields = ReflectUtil.getFields(clazz, true, true);
            for (Field field : fields) {
                ColumnDefinition columnDefinition = ColumnDefinition.ofField(field);
                if (columnDefinition != null) {
                    definition.addColumn(columnDefinition);
                }
            }
            return definition;
        }
        return null;
    }

    /**
     * 获取表名称
     *
     * @return 表名称
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设置表名称
     *
     * @param tableName 表名称
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 获取列定义列表
     *
     * @return 列定义列表
     */
    public List<ColumnDefinition> getColumns() {
        return columns;
    }

    /**
     * 设置列定义列表
     *
     * @param columns 列定义列表
     */
    public void setColumns(List<ColumnDefinition> columns) {
        this.columns = columns;
    }
}
