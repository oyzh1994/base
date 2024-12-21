package cn.oyzh.store.jdbc;

import lombok.Getter;

import java.sql.SQLException;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-12-21
 */
public abstract class JdbcOperator {

    /**
     * 表定义
     */
    @Getter
    protected final TableDefinition tableDefinition;

    public JdbcOperator(TableDefinition tableDefinition) {
        this.tableDefinition = tableDefinition;
    }

    /**
     * 获取表名称
     *
     * @return 表名称
     */
    protected String tableName() {
        return this.tableDefinition.getTableName();
    }

    /**
     * 获取字段
     *
     * @return 字段定义
     */
    protected List<ColumnDefinition> columns() {
        return this.tableDefinition.getColumns();
    }

    /**
     * 初始化表
     *
     * @return 结果
     * @throws Exception 异常
     */
    public boolean initTable() throws Exception {
        return false;
    }

    /**
     * 更改表
     *
     * @throws SQLException 异常
     */
    protected void alterTable() throws SQLException {
    }

    /**
     * 创建表
     *
     * @throws SQLException 异常
     */
    protected void createTable() throws SQLException {
    }
}