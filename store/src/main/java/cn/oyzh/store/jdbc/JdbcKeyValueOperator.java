package cn.oyzh.store.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author oyzh
 * @since 2024-12-21
 */
public abstract class JdbcKeyValueOperator extends JdbcOperator {

    public JdbcKeyValueOperator(TableDefinition tableDefinition) {
        super(tableDefinition);
    }

    /**
     * 更新数据
     * @param record 记录列表
     * @return 更新结果
     * @throws Exception
     */
    public boolean update(Map<String, Object> record) throws Exception {
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
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            JdbcManager.giveback(connection);
        }
        return false;
    }

    /**
     * 查询数据
     *
     * @return 结果
     * @throws SQLException 异常
     */
    public Map<String, Object> select() throws SQLException {
        String tableName = this.tableName();
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(JdbcUtil.wrap(tableName));
        JdbcConn connection = JdbcManager.takeoff();
        try {
            JdbcResultSet resultSet = JdbcHelper.executeQuery(connection, sql.toString());
            Map<String, Object> record = new HashMap<>();
            while (resultSet.next()) {
                String key = (String) resultSet.getObject("KEY");
                Object value = resultSet.getObject("VALUE");
                record.put(key, value);
            }
            resultSet.close();
            return record;
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    /**
     * 清除数据
     * @return 结果
     * @throws SQLException 异常
     */
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