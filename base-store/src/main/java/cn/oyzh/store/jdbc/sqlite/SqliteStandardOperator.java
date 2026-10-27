package cn.oyzh.store.jdbc.sqlite;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.store.jdbc.JdbcConn;
import cn.oyzh.store.jdbc.JdbcHelper;
import cn.oyzh.store.jdbc.JdbcManager;
import cn.oyzh.store.jdbc.JdbcStandardOperator;
import cn.oyzh.store.jdbc.JdbcUtil;
import cn.oyzh.store.jdbc.TableDefinition;
import cn.oyzh.store.jdbc.param.DeleteParam;
import cn.oyzh.store.jdbc.param.QueryParam;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * sqlite标准操作器
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class SqliteStandardOperator extends JdbcStandardOperator {

    /**
     * 构造sqlite标准操作器
     *
     * @param tableDefinition 表定义
     */
    public SqliteStandardOperator(TableDefinition tableDefinition) {
        super(tableDefinition);
    }

    @Override
    public boolean initTable() throws Exception {
        JdbcConn connection = JdbcManager.takeoff();
        if (connection == null) {
            JulLog.warn("connection is null");
            return false;
        }
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

    @Override
    public int delete(DeleteParam deleteParam) throws SQLException {
        String tableName = this.tableName();
        String condition = this.buildCondition(deleteParam);
        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(JdbcUtil.wrap(tableName));
        if (deleteParam != null && deleteParam.getLimit() != null && deleteParam.getLimit() > 0) {
            sql.append(" WHERE rowid IN (SELECT rowid FROM ")
                    .append(JdbcUtil.wrap(tableName))
                    .append(condition)
                    .append(" LIMIT ")
                    .append(deleteParam.getLimit())
                    .append(")");
        } else {
            sql.append(condition);
        }
        JdbcConn connection = JdbcManager.takeoff();
        try {
            return JdbcHelper.executeUpdate(connection, sql.toString());
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

    private String buildCondition(DeleteParam deleteParam) {
        if (deleteParam == null || CollectionUtil.isEmpty(deleteParam.getQueryParams())) {
            return "";
        }
        StringBuilder condition = new StringBuilder(" WHERE ");
        boolean first = true;
        for (QueryParam queryParam : deleteParam.getQueryParams()) {
            if (!first) {
                condition.append(" AND ");
            }
            first = false;
            condition.append(JdbcUtil.wrap(queryParam.getName()))
                    .append(queryParam.getOperator())
                    .append(JdbcUtil.wrapData(queryParam.getData()));
        }
        return condition.toString();
    }
}
