package cn.oyzh.store.jdbc;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * jdbc连接
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class JdbcConn implements AutoCloseable {

    /**
     * 0 正常
     * 1 使用中
     * 2 已关闭
     */
    private final AtomicInteger status;

    /**
     * 连接
     */
    private Connection connection;

    /**
     * 构造jdbc连接，默认开启自动提交
     *
     * @param connection 数据库连接
     * @throws SQLException 异常
     */
    public JdbcConn(Connection connection) throws SQLException {
        connection.setAutoCommit(true);
        this.connection = connection;
        this.status = new AtomicInteger(0);
    }

    /**
     * 是否可用
     *
     * @return 结果
     */
    public boolean isUsable() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.status.set(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.status.get() == 0;
    }

    /**
     * 获取连接
     *
     * @return Connection
     */
    public Connection takeoff() {
        return this.status.compareAndSet(0, 1) ? this.connection : null;
    }

    /**
     * 归还连接
     */
    public void giveback() {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.status.set(2);
            } else {
                this.status.set(0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 是否失效
     *
     * @return 结果
     */
    public boolean isInvalid() {
        return this.status.get() == 2;
    }

    /**
     * 创建预编译语句
     *
     * @param sql sql语句
     * @return 预编译语句
     * @throws SQLException 异常
     */
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return this.connection.prepareStatement(sql);
    }

    /**
     * 创建语句
     *
     * @return 语句
     * @throws SQLException 异常
     */
    public Statement createStatement() throws SQLException {
        return this.connection.createStatement();
    }

    /**
     * 获取数据库元数据
     *
     * @return 数据库元数据
     * @throws SQLException 异常
     */
    public DatabaseMetaData getMetaData() throws SQLException {
        return this.connection.getMetaData();
    }

    /**
     * 获取列信息
     *
     * @param tableNamePattern  表名称模式
     * @param columnNamePattern 列名称模式
     * @return 结果集
     * @throws SQLException 异常
     */
    public ResultSet getColumns(String tableNamePattern, String columnNamePattern) throws SQLException {
        return this.getColumns(null, null, tableNamePattern, columnNamePattern);
    }

    /**
     * 获取列信息
     *
     * @param catalog           目录
     * @param schemaPattern     模式
     * @param tableNamePattern  表名称模式
     * @param columnNamePattern 列名称模式
     * @return 结果集
     * @throws SQLException 异常
     */
    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern) throws SQLException {
        if (JdbcManager.isH2Dialect()) {
            if (tableNamePattern != null) {
                tableNamePattern = tableNamePattern.toUpperCase();
            }
            if (columnNamePattern != null) {
                columnNamePattern = columnNamePattern.toUpperCase();
            }
        }
        return this.getMetaData().getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);
    }

    /**
     * 获取表信息
     *
     * @param tableNamePattern 表名称模式
     * @return 结果集
     * @throws SQLException 异常
     */
    public ResultSet getTables(String tableNamePattern) throws SQLException {
        return this.getTables(null, null, tableNamePattern, null);
    }

    /**
     * 获取表信息
     *
     * @param tableNamePattern 表名称模式
     * @param types            表类型
     * @return 结果集
     * @throws SQLException 异常
     */
    public ResultSet getTables(String tableNamePattern, String[] types) throws SQLException {
        return this.getTables(null, null, tableNamePattern, types);
    }

    /**
     * 获取表信息
     *
     * @param catalog          目录
     * @param schemaPattern    模式
     * @param tableNamePattern 表名称模式
     * @param types            表类型
     * @return 结果集
     * @throws SQLException 异常
     */
    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types) throws SQLException {
        if (JdbcManager.isH2Dialect()) {
            if (tableNamePattern != null) {
                tableNamePattern = tableNamePattern.toUpperCase();
            }
        }
        return this.getMetaData().getTables(catalog, schemaPattern, tableNamePattern, types);
    }

    /**
     * 设置是否自动提交
     *
     * @param autoCommit 是否自动提交
     * @throws SQLException 异常
     */
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        this.connection.setAutoCommit(autoCommit);
    }

    /**
     * 回滚事务
     *
     * @throws SQLException 异常
     */
    public void rollback() throws SQLException {
        this.connection.rollback();
    }

    /**
     * 提交事务
     *
     * @throws SQLException 异常
     */
    public void commit() throws SQLException {
        this.connection.commit();
    }

    /**
     * 获取原生连接
     *
     * @return 原生连接
     */
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
        this.connection = null;
    }
}
