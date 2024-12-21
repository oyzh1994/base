package cn.oyzh.store.jdbc;

import lombok.Getter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author oyzh
 * @since 2024-12-21
 */
public abstract class JdbcKeyValueOperator extends JdbcOperator {

    public JdbcKeyValueOperator(TableDefinition tableDefinition) {
        super(tableDefinition);
    }

    public int update(Map<String, Object> record) throws Exception {
        String tableName = this.tableName();
        JdbcConn connection = JdbcManager.takeoff();
        try {
            connection.setAutoCommit(false);
            Statement clearStatement = connection.createStatement();
            clearStatement.executeUpdate("DELETE FROM " + JdbcUtil.wrap(tableName));
            clearStatement.close();
            for (Map.Entry<String, Object> entry : record.entrySet()) {
                StringBuilder sql = new StringBuilder("INSERT INTO ");
                sql.append(JdbcUtil.wrap(tableName)).append("(`KEY`, `VALUE`) VALUES( ?,?)");
                PreparedStatement statement = connection.prepareStatement(sql.toString());
                JdbcHelper.setParams(statement, entry.getKey(), entry.getValue());
                statement.executeUpdate();
                statement.close();
            }
            connection.commit();
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            JdbcManager.giveback(connection);
        }
        return 0;
    }

    public Map<String, Object> select() throws SQLException {
        String tableName = this.tableName();
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(JdbcUtil.wrap(tableName));
        JdbcConn connection = JdbcManager.takeoff();
        try {
            JdbcResultSet resultSet = JdbcHelper.executeQuery(connection, sql.toString());
            Map<String, Object> record = new HashMap<>();
            while (resultSet.next()) {
                for (ColumnDefinition columnDefinition : this.columns()) {
                    String columnName = columnDefinition.getColumnName();
                    if (resultSet.containsColumn(columnName)) {
                        record.put(columnName, resultSet.getObject(columnName));
                    }
                }
            }
            resultSet.close();
            return record;
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    public boolean clear() throws SQLException {
        String tableName = this.tableName();
        JdbcConn connection = JdbcManager.takeoff();
        try {
            return JdbcHelper.executeUpdate(connection, "DELETE FROM " + JdbcUtil.wrap(tableName)) > 0;
        } finally {
            JdbcManager.giveback(connection);
        }
    }
}