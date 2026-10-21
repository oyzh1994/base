package cn.oyzh.store.jdbc.sqlite;

import cn.oyzh.store.jdbc.ColumnDefinition;
import cn.oyzh.store.jdbc.JdbcConn;
import cn.oyzh.store.jdbc.JdbcHelper;
import cn.oyzh.store.jdbc.JdbcKeyValueOperator;
import cn.oyzh.store.jdbc.JdbcManager;
import cn.oyzh.store.jdbc.TableDefinition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * sqlite键值操作器
 *
 * @author oyzh
 * @since 2024-12-21
 */
public class SqliteKeyValueOperator extends JdbcKeyValueOperator {

    /**
     * 构造sqlite键值操作器
     *
     * @param tableDefinition 表定义
     */
    public SqliteKeyValueOperator(TableDefinition tableDefinition) {
        super(tableDefinition);
    }

    @Override
    protected List<ColumnDefinition> columns() {
        ColumnDefinition keyColumn = new ColumnDefinition();
        keyColumn.setColumnName("KEY");
        keyColumn.setColumnType("TEXT");

        ColumnDefinition valueColumn = new ColumnDefinition();
        valueColumn.setColumnName("VALUE");
        valueColumn.setColumnType("TEXT");

        return List.of(keyColumn, valueColumn);
    }

    @Override
    public boolean initTable() throws Exception {
        JdbcConn connection = JdbcManager.takeoff();
        try {
            String tableName = this.tableName();
            ResultSet resultSet = connection.getTables(tableName);
            boolean exists = resultSet.next();
            resultSet.close();
            if (!exists) {
                JdbcHelper.executeUpdate(connection,
                        SqliteUtil.createTableSql(tableName, this.columns()));
            } else if (!SqliteUtil.schemaMatches(connection, tableName, this.columns())) {
                this.rebuildTable(connection);
            }
            return true;
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    @Override
    protected void alterTable() throws SQLException {
        JdbcConn connection = JdbcManager.takeoff();
        try {
            this.rebuildTable(connection);
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    @Override
    protected void createTable() throws SQLException {
        JdbcConn connection = JdbcManager.takeoff();
        try {
            JdbcHelper.executeUpdate(connection,
                    SqliteUtil.createTableSql(this.tableName(), this.columns()));
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    private void rebuildTable(JdbcConn connection) throws SQLException {
        try {
            connection.setAutoCommit(false);
            SqliteUtil.rebuildTable(connection, this.tableName(), this.columns());
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
