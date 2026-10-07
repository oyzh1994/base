package cn.oyzh.store.jdbc;

import cn.oyzh.store.jdbc.h2.H2KeyValueOperator;

import java.io.Serializable;
import java.util.Map;

/**
 * jdbc键值存储
 *
 * @author oyzh
 * @since 2024-12-21
 */
public abstract class JdbcKeyValueStore<M extends Serializable> extends JdbcStore<M> {

    /**
     * 键值操作器
     */
    private final JdbcKeyValueOperator operator;

    /**
     * 构造jdbc键值存储
     */
    public JdbcKeyValueStore() {
        try {
            TableDefinition tableDefinition = this.tableDefinition();
//            if (JdbcManager.dialect == JdbcDialect.H2) {
                this.operator = new H2KeyValueOperator(tableDefinition);
//            } else {
//                this.operator = new SqliteKeyValueOperator(tableDefinition);
//            }
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

    /**
     * 覆盖写入模型数据
     *
     * @param model 模型
     * @return 结果
     */
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

    /**
     * 查询模型数据
     *
     * @return 模型
     */
    public M select() {
        try {
            return this.toModel(this.operator.select());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 清空数据
     *
     * @return 结果
     */
    public boolean clear() {
        try {
            return this.operator.clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
