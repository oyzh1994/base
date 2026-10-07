package cn.oyzh.store.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * jdbc结果
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class JdbcResultSet implements AutoCloseable {

    /**
     * 结果集
     */
    private final ResultSet resultSet;

    /**
     * 语句
     */
    private final Statement statement;

    /**
     * 构造jdbc结果集
     *
     * @param resultSet 结果集
     * @param statement 语句
     */
    public JdbcResultSet(ResultSet resultSet, Statement statement) {
        this.resultSet = resultSet;
        this.statement = statement;
    }

    @Override
    public void close() throws SQLException {
        this.resultSet.close();
        this.statement.close();
    }

    /**
     * 游标移至下一行
     *
     * @return 是否存在下一行
     * @throws SQLException 异常
     */
    public boolean next() throws SQLException {
        return this.resultSet.next();
    }

    /**
     * 获取指定列索引的int值
     *
     * @param columnIndex 列索引
     * @return int值
     * @throws SQLException 异常
     */
    public int getInt(int columnIndex) throws SQLException {
        return this.resultSet.getInt(columnIndex);
    }

    /**
     * 获取指定列索引的long值
     *
     * @param columnIndex 列索引
     * @return long值
     * @throws SQLException 异常
     */
    public long getLong(int columnIndex) throws SQLException {
        return this.resultSet.getLong(columnIndex);
    }

    /**
     * 查找指定列名称的索引
     *
     * @param columnLabel 列名称
     * @return 列索引
     * @throws SQLException 异常
     */
    public int findColumn(String columnLabel) throws SQLException {
        return this.resultSet.findColumn(columnLabel);
    }

    /**
     * 是否包含指定列
     *
     * @param columnLabel 列名称
     * @return 结果
     */
    public boolean containsColumn(String columnLabel) {
        try {
            return this.resultSet.findColumn(columnLabel) >= 0;
        } catch (SQLException ignored) {
        }
        return false;
    }

    /**
     * 获取指定列名称的对象值
     *
     * @param columnLabel 列名称
     * @return 对象值
     * @throws SQLException 异常
     */
    public Object getObject(String columnLabel) throws SQLException {
        return this.resultSet.getObject(columnLabel);
    }
}
