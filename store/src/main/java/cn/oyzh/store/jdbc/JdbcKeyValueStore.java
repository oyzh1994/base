package cn.oyzh.store.jdbc;

import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.common.util.ReflectUtil;
import cn.oyzh.store.h2.H2KeyValueOperator;
import cn.oyzh.store.h2.H2Operator;
import cn.oyzh.store.sqlite.SqliteKeyValueOperator;
import cn.oyzh.store.sqlite.SqliteOperator;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author oyzh
 * @since 2024-09-23
 */
public abstract class JdbcKeyValueStore<M extends Serializable> {

    private final JdbcKeyValueOperator operator;

    public JdbcKeyValueStore() {
        try {
            TableDefinition tableDefinition = this.tableDefinition();
            if (JdbcManager.dialect == JdbcDialect.H2) {
                this.operator = new H2KeyValueOperator(tableDefinition);
            } else {
                this.operator = new SqliteKeyValueOperator(tableDefinition);
            }
            this.operator.initTable();
            this.init();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    protected void init() {

    }

    protected abstract M newModel();

    protected abstract Class<M> modelClass();

    protected TableDefinition tableDefinition() {
        if (this.operator != null) {
            return this.operator.getTableDefinition();
        }
        return TableDefinition.ofClass(this.modelClass());
    }

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

    public boolean insert(M model) {
        if (model != null) {
            try {
                return this.operator.insert(this.toRecord(model)) > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean update(M model) {
        if (model != null) {
            try {
                Map<String, Object> record = this.toRecord(model);
                return this.operator.update(record) > 0;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public M select() {
        try {
            return this.toModel(this.operator.select());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean clear() {
        try {
            return this.operator.clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
