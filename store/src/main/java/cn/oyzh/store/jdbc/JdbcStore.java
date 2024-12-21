package cn.oyzh.store.jdbc;

import cn.oyzh.common.util.ClassUtil;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.common.util.ReflectUtil;
import cn.oyzh.store.jdbc.h2.H2KeyValueOperator;
import cn.oyzh.store.jdbc.sqlite.SqliteKeyValueOperator;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author oyzh
 * @since 2024-12-21
 */
public abstract class JdbcStore<M extends Serializable> {

    /**
     * 执行初始化
     */
    protected void init() {

    }

    /**
     * 创建一个模型对象
     *
     * @return 模型对象
     */
    protected M newModel() {
        Class<?> modelClass = this.modelClass();
        return (M) ClassUtil.newInstance(modelClass);
    }

    /**
     * 获取模型类
     *
     * @return 模型类
     */
    protected abstract Class<M> modelClass();

    /**
     * 获取表定义
     *
     * @return 表定义
     */
    protected abstract TableDefinition tableDefinition();

    /**
     * 转换为模型对象
     *
     * @param record 记录列表
     * @return 模型对象
     * @throws Exception 异常
     */
    protected M toModel(Map<String, Object> record) throws Exception {
        if (CollectionUtil.isEmpty(record)) {
            return null;
        }
        TableDefinition definition = this.tableDefinition();
        if (definition == null) {
            return null;
        }
        M model = this.newModel();
        for (ColumnDefinition column : definition.getColumns()) {
            Field field = ReflectUtil.getField(model.getClass(), column.getFieldName(), true, true);
            field.setAccessible(true);
            Object sqlData = record.get(column.getColumnName());
            Object javaValue = JdbcUtil.toJavaValue(field.getType(), sqlData);
            if (javaValue != null) {
                field.set(model, javaValue);
            }
        }
        return model;
    }

    /**
     * 转换为记录
     * @param model 模型
     * @return 记录列表
     * @throws Exception 异常
     */
    protected Map<String, Object> toRecord(M model) throws Exception {
        TableDefinition definition = this.tableDefinition();
        if (definition == null) {
            return null;
        }
        Map<String, Object> record = new HashMap<>();
        for (ColumnDefinition column : definition.getColumns()) {
            Field field = ReflectUtil.getField(model.getClass(), column.getFieldName(), true, true);
            field.setAccessible(true);
            record.put(column.getColumnName(), field.get(model));
        }
        return record;
    }
}
