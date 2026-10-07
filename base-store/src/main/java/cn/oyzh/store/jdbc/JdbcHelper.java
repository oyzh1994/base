package cn.oyzh.store.jdbc;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.util.ArrayUtil;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Collection;

/**
 * jdbc辅助类
 *
 * @author oyzh
 * @since 2024-09-25
 */
public class JdbcHelper {

    /**
     * 执行sql
     *
     * @param connection 连接
     * @param sql        sql语句
     * @throws SQLException 异常
     */
    public static void execute(JdbcConn connection, String sql) throws SQLException {
        JulLog.info(sql);
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
    }

    /**
     * 执行更新
     *
     * @param connection 连接
     * @param sql        sql语句
     * @param collection 参数集合
     * @return 影响行数
     * @throws SQLException 异常
     */
    public static int executeUpdate(JdbcConn connection, String sql, Collection<?> collection) throws SQLException {
        return executeUpdate(connection, sql, collection.toArray());
    }

    /**
     * 执行更新
     *
     * @param connection 连接
     * @param sql        sql语句
     * @param params     参数
     * @return 影响行数
     * @throws SQLException 异常
     */
    public static int executeUpdate(JdbcConn connection, String sql, Object... params) throws SQLException {
        JulLog.info(sql);
        PreparedStatement statement = connection.prepareStatement(sql);
        setParams(statement, params);
        int update = statement.executeUpdate();
        statement.close();
        connection.commit();
        return update;
    }

    /**
     * 执行查询
     *
     * @param connection 连接
     * @param sql        sql语句
     * @param collection 参数集合
     * @return 结果集
     * @throws SQLException 异常
     */
    public static JdbcResultSet executeQuery(JdbcConn connection, String sql, Collection<?> collection) throws SQLException {
        return executeQuery(connection, sql, collection.toArray());
    }

    /**
     * 执行查询
     *
     * @param connection 连接
     * @param sql        sql语句
     * @param params     参数
     * @return 结果集
     * @throws SQLException 异常
     */
    public static JdbcResultSet executeQuery(JdbcConn connection, String sql, Object... params) throws SQLException {
        JulLog.info(sql);
        if (ArrayUtil.isNotEmpty(params)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            setParams(statement, params);
            ResultSet resultSet = statement.executeQuery();
            return new JdbcResultSet(resultSet, statement);
        }
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return new JdbcResultSet(resultSet, statement);
    }

    /**
     * 设置参数
     *
     * @param statement 预编译语句
     * @param params    参数
     * @throws SQLException 异常
     */
    public static void setParams(PreparedStatement statement, Object... params) throws SQLException {
        if (ArrayUtil.isNotEmpty(params)) {
            int index = 1;
            for (Object param : params) {
                if (param == null) {
                    statement.setNull(index++, Types.NULL);
                } else {
                    statement.setObject(index++, param);
                }
            }
        }
    }
}
