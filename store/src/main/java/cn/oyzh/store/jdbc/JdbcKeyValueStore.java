package cn.oyzh.store.jdbc;

import cn.oyzh.store.jdbc.h2.H2KeyValueOperator;
import cn.oyzh.store.jdbc.sqlite.SqliteKeyValueOperator;

import java.io.Serializable;
import java.util.Map;

/**
 * @author oyzh
 * @since 2024-12-21
 */
public abstract class JdbcKeyValueStore<M extends Serializable> extends JdbcStore<M> {

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

    @Override
    protected TableDefinition tableDefinition() {
        if (this.operator != null) {
            return this.operator.getTableDefinition();
        }
        return TableDefinition.ofClass(this.modelClass());
    }

    public boolean update(M model) {
        if (model != null) {
            try {
                Map<String, Object> record = this.toRecord(model);
                return this.operator.update(record);
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
