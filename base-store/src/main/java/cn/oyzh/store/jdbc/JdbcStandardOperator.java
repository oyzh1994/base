package cn.oyzh.store.jdbc;

import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.common.util.StringUtil;
import cn.oyzh.store.jdbc.param.DeleteParam;
import cn.oyzh.store.jdbc.param.OrderByParam;
import cn.oyzh.store.jdbc.param.PageParam;
import cn.oyzh.store.jdbc.param.QueryParam;
import cn.oyzh.store.jdbc.param.QueryParams;
import cn.oyzh.store.jdbc.param.SelectParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jdbc标准操作器
 *
 * @author oyzh
 * @since 2024-10-18
 */
public abstract class JdbcStandardOperator extends JdbcOperator {

    /**
     * 构造jdbc标准操作器
     *
     * @param tableDefinition 表定义
     */
    public JdbcStandardOperator(TableDefinition tableDefinition) {
        super(tableDefinition);
    }

    /**
     * 根据主键值构建主键列
     *
     * @param primaryKey 主键值
     * @return 主键列
     */
    protected PrimaryKeyColumn getPrimaryKeyColumn(Object primaryKey) {
        ColumnDefinition columnDefinition = this.tableDefinition.primaryKey();
        if (columnDefinition == null) {
            return null;
        }
        return new PrimaryKeyColumn(columnDefinition.getColumnName(), primaryKey);
    }

    /**
     * 新增数据
     *
     * @param record 记录
     * @return 影响行数
     * @throws Exception 异常
     */
    public int insert(Map<String, Object> record) throws Exception {
        String tableName = this.tableName();
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        sql.append(JdbcUtil.wrap(tableName));
        sql.append("(");
        for (String column : record.keySet()) {
            sql.append(JdbcUtil.wrap(column)).append(",");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(") VALUES(");
        sql.append("?,".repeat(record.size()));
        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        JdbcConn connection = JdbcManager.takeoff();
        try {
            List<Object> values = new ArrayList<>();
            for (String key : record.keySet()) {
                values.add(record.get(key));
            }
            return JdbcHelper.executeUpdate(connection, sql.toString(), values);
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    /**
     * 根据主键值更新数据
     *
     * @param record     记录
     * @param primaryKey 主键值
     * @return 影响行数
     * @throws Exception 异常
     */
    public int update(Map<String, Object> record, Object primaryKey) throws Exception {
        PrimaryKeyColumn primaryKeyColumn = this.getPrimaryKeyColumn(primaryKey);
        return primaryKeyColumn == null ? 0 : this.update(record, primaryKeyColumn);
    }

    /**
     * 根据主键列更新数据
     *
     * @param record     记录
     * @param primaryKey 主键列
     * @return 影响行数
     * @throws Exception 异常
     */
    public int update(Map<String, Object> record, PrimaryKeyColumn primaryKey) throws Exception {
        record.remove(primaryKey.getColumnName());
        String tableName = this.tableName();
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(JdbcUtil.wrap(tableName));
        sql.append(" SET ");
        for (String column : record.keySet()) {
            sql.append(JdbcUtil.wrap(column)).append(" = ?,");
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(" WHERE ");
        sql.append(primaryKey.getColumnName());
        sql.append(" = ?");
        JdbcConn connection = JdbcManager.takeoff();
        try {
            List<Object> values = new ArrayList<>();
            for (String key : record.keySet()) {
                values.add(record.get(key));
            }
            values.add(primaryKey.getColumnData());
            return JdbcHelper.executeUpdate(connection, sql.toString(), values);
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    /**
     * 根据主键值判断数据是否存在
     *
     * @param primaryKey 主键值
     * @return 结果
     * @throws SQLException 异常
     */
    public boolean exist(Object primaryKey) throws SQLException {
        PrimaryKeyColumn primaryKeyColumn = this.getPrimaryKeyColumn(primaryKey);
        return primaryKeyColumn != null && this.exist(primaryKeyColumn);
    }

    /**
     * 根据主键列判断数据是否存在
     *
     * @param primaryKey 主键列
     * @return 结果
     * @throws SQLException 异常
     */
    public boolean exist(PrimaryKeyColumn primaryKey) throws SQLException {
        String tableName = this.tableName();
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ");
        sql.append(JdbcUtil.wrap(tableName));
        sql.append(" WHERE ");
        sql.append(primaryKey.getColumnName());
        sql.append(" = ?");
        JdbcConn connection = JdbcManager.takeoff();
        try {
            JdbcResultSet resultSet = JdbcHelper.executeQuery(connection, sql.toString(), primaryKey.getColumnData());
            boolean exists = false;
            if (resultSet.next()) {
                exists = resultSet.getInt(1) > 0;
            }
            resultSet.close();
            return exists;
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    /**
     * 判断符合条件的数据是否存在
     *
     * @param params 查询条件
     * @return 结果
     * @throws SQLException 异常
     */
    public boolean exist(Map<String, Object> params) throws SQLException {
        String tableName = this.tableName();
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ");
        sql.append(JdbcUtil.wrap(tableName));
        if (CollectionUtil.isNotEmpty(params)) {
            boolean first = true;
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (first) {
                    first = false;
                    sql.append(" WHERE ");
                } else {
                    sql.append(" AND ");
                }
                sql.append(JdbcUtil.wrap(entry.getKey()));
                sql.append(" = ");
                sql.append(JdbcUtil.wrapData(entry.getValue()));
            }
        }
        JdbcConn connection = JdbcManager.takeoff();
        try {
            JdbcResultSet resultSet = JdbcHelper.executeQuery(connection, sql.toString());
            boolean exists = false;
            if (resultSet.next()) {
                exists = resultSet.getInt(1) > 0;
            }
            resultSet.close();
            return exists;
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    /**
     * 根据主键值查询单条记录
     *
     * @param primaryKey 主键值
     * @return 记录
     * @throws Exception 异常
     */
    public Map<String, Object> selectOne(Object primaryKey) throws Exception {
        PrimaryKeyColumn primaryKeyColumn = this.getPrimaryKeyColumn(primaryKey);
        return primaryKeyColumn == null ? null : this.selectOne(primaryKeyColumn);
    }

    /**
     * 根据主键列查询单条记录
     *
     * @param primaryKey 主键列
     * @return 记录
     * @throws SQLException 异常
     */
    public Map<String, Object> selectOne(PrimaryKeyColumn primaryKey) throws SQLException {
        String tableName = this.tableName();
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(JdbcUtil.wrap(tableName));
        sql.append(" WHERE ");
        sql.append(JdbcUtil.wrap(primaryKey.getColumnName()));
        sql.append(" = ?");
        JdbcConn connection = JdbcManager.takeoff();
        try {
            JdbcResultSet resultSet = JdbcHelper.executeQuery(connection, sql.toString(), primaryKey.getColumnData());
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

    /**
     * 根据查询条件查询单条记录
     *
     * @param queryParam 查询条件
     * @return 记录
     * @throws SQLException 异常
     */
    public Map<String, Object> selectOne(QueryParam queryParam) throws SQLException {
        SelectParam selectParam = new SelectParam();
        selectParam.addQueryParam(queryParam);
        return this.selectOne(selectParam);
    }

    /**
     * 根据查询参数查询单条记录
     *
     * @param selectParam 查询参数
     * @return 记录
     * @throws SQLException 异常
     */
    public Map<String, Object> selectOne(SelectParam selectParam) throws SQLException {
        String tableName = this.tableName();
        StringBuilder sql = new StringBuilder("SELECT ");
        if (selectParam == null || CollectionUtil.isEmpty(selectParam.getQueryColumns())) {
            sql.append("*");
        } else {
            for (String queryColumn : selectParam.getQueryColumns()) {
                sql.append(queryColumn).append(",");
            }
            sql.deleteCharAt(sql.lastIndexOf(","));
        }
        sql.append(" FROM ");
        sql.append(JdbcUtil.wrap(tableName));
        if (selectParam != null) {
            if (CollectionUtil.isNotEmpty(selectParam.getQueryParams())) {
                boolean first = true;
                for (QueryParam queryParam : selectParam.getQueryParams()) {
                    if (first) {
                        first = false;
                        sql.append(" WHERE ");
                    } else {
                        sql.append(" AND ");
                    }
                    sql.append(JdbcUtil.wrap(queryParam.getName()));
                    sql.append(queryParam.getOperator());
                    sql.append(JdbcUtil.wrapData(queryParam.getData()));
                }
            }
            // 处理order by
            if (CollectionUtil.isNotEmpty(selectParam.getOrderByParams())) {
                sql.append(" ORDER BY ");
                for (OrderByParam orderByParam : selectParam.getOrderByParams()) {
                    sql.append(orderByParam.getName());
                    sql.append(" ");
                    sql.append(orderByParam.getType().toUpperCase()).append(",");
                }
                StringUtil.deleteLast(sql);
            }
            // 处理指定位置
            if (selectParam.getLimit() != null) {
                sql.append(" LIMIT ").append(selectParam.getLimit());
                if (selectParam.getOffset() != null) {
                    sql.append(" OFFSET ").append(selectParam.getOffset());
                }
            }
        }
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

    /**
     * 根据查询参数查询记录列表
     *
     * @param selectParam 查询参数
     * @return 记录列表
     * @throws SQLException 异常
     */
    public List<Map<String, Object>> selectList(SelectParam selectParam) throws SQLException {
        String tableName = this.tableName();
        StringBuilder sql = new StringBuilder("SELECT ");
        if (selectParam == null || CollectionUtil.isEmpty(selectParam.getQueryColumns())) {
            sql.append("*");
        } else {
            for (String queryColumn : selectParam.getQueryColumns()) {
                sql.append(queryColumn).append(",");
            }
            sql.deleteCharAt(sql.lastIndexOf(","));
        }
        sql.append(" FROM ");
        sql.append(tableName);
        if (selectParam != null) {
            if (CollectionUtil.isNotEmpty(selectParam.getQueryParams())) {
                boolean first = true;
                for (QueryParam queryParam : selectParam.getQueryParams()) {
                    if (first) {
                        first = false;
                        sql.append(" WHERE ");
                    } else {
                        sql.append(" AND ");
                    }
                    sql.append(JdbcUtil.wrap(queryParam.getName()));
                    sql.append(queryParam.getOperator());
                    sql.append(JdbcUtil.wrapData(queryParam.getData()));
                }
            }
            // 处理order by
            if (CollectionUtil.isNotEmpty(selectParam.getOrderByParams())) {
                sql.append(" ORDER BY ");
                for (OrderByParam orderByParam : selectParam.getOrderByParams()) {
                    sql.append(orderByParam.getName());
                    sql.append(" ");
                    sql.append(orderByParam.getType().toUpperCase()).append(",");
                }
                StringUtil.deleteLast(sql);
            }
            // 处理指定位置
            if (selectParam.getLimit() != null) {
                sql.append(" LIMIT ").append(selectParam.getLimit());
                if (selectParam.getOffset() != null) {
                    sql.append(" OFFSET ").append(selectParam.getOffset());
                }
            }
        }
        JdbcConn connection = JdbcManager.takeoff();
        try {
            JdbcResultSet resultSet = JdbcHelper.executeQuery(connection, sql.toString());
            List<Map<String, Object>> records = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> record = new HashMap<>();
                for (ColumnDefinition columnDefinition : this.columns()) {
                    String columnName = columnDefinition.getColumnName();
                    if (resultSet.containsColumn(columnName)) {
                        record.put(columnName, resultSet.getObject(columnName));
                    }
                }
                records.add(record);
            }
            resultSet.close();
            return records;
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    /**
     * 根据查询条件列表统计数据条数
     *
     * @param params 查询条件列表
     * @return 数据条数
     * @throws SQLException 异常
     */
    public long selectCount(List<QueryParam> params) throws SQLException {
        String tableName = this.tableName();
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ");
        sql.append(JdbcUtil.wrap(tableName));
        if (CollectionUtil.isNotEmpty(params)) {
            boolean first = true;
            for (QueryParam param : params) {
                if (first) {
                    first = false;
                    sql.append(" WHERE ");
                } else {
                    sql.append(" AND ");
                }
                sql.append(JdbcUtil.wrap(param.getName()));
                sql.append(param.getOperator());
                sql.append(JdbcUtil.wrapData(param.getData()));
            }
        }
        JdbcConn connection = JdbcManager.takeoff();
        try {
            JdbcResultSet resultSet = JdbcHelper.executeQuery(connection, sql.toString());
            long count = 0;
            if (resultSet.next()) {
                count = resultSet.getLong(1);
            }
            resultSet.close();
            return count;
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    /**
     * 根据关键字和查询条件统计数据条数
     *
     * @param kw          关键字
     * @param columns     关键字匹配的列
     * @param queryParams 查询条件
     * @return 数据条数
     * @throws SQLException 异常
     */
    public long selectCount(String kw, List<String> columns, QueryParams queryParams) throws SQLException {
        String tableName = this.tableName();
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ");
        sql.append(JdbcUtil.wrap(tableName));
        boolean kwHandle = false;
        if (StringUtil.isNotBlank(kw) && CollectionUtil.isNotEmpty(columns)) {
            kwHandle = true;
            boolean first = true;
            for (String column : columns) {
                if (first) {
                    first = false;
                    sql.append(" WHERE ");
                } else {
                    sql.append(" OR ");
                }
                sql.append(JdbcUtil.wrap(column));
                sql.append(" LIKE ");
                sql.append(JdbcUtil.wrapData("%" + kw + "%"));
            }
        }
        if (CollectionUtil.isNotEmpty(queryParams)) {
            boolean first = true;
            for (QueryParam queryParam : queryParams) {
                if (!kwHandle && first) {
                    first = false;
                    sql.append(" WHERE ");
                }
                sql.append(queryParam.getName())
                        .append(queryParam.getOperator())
                        .append(JdbcUtil.wrapData(queryParam.getData()));
            }
        }
        JdbcConn connection = JdbcManager.takeoff();
        try {
            JdbcResultSet resultSet = JdbcHelper.executeQuery(connection, sql.toString());
            long count = 0;
            if (resultSet.next()) {
                count = resultSet.getLong(1);
            }
            resultSet.close();
            return count;
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    /**
     * 分页查询记录列表
     *
     * @param kw        关键字
     * @param columns   关键字匹配的列
     * @param pageParam 分页参数
     * @return 记录列表
     * @throws SQLException 异常
     */
    public List<Map<String, Object>> selectPage(String kw, List<String> columns, PageParam pageParam) throws SQLException {
        String tableName = this.tableName();
        StringBuilder sql = new StringBuilder("SELECT * FROM ");
        sql.append(JdbcUtil.wrap(tableName));
        boolean kwHandle = false;
        if (StringUtil.isNotBlank(kw) && CollectionUtil.isNotEmpty(columns)) {
            kwHandle = true;
            boolean first = true;
            for (String column : columns) {
                if (first) {
                    first = false;
                    sql.append(" WHERE ");
                } else {
                    sql.append(" OR ");
                }
                sql.append(JdbcUtil.wrap(column));
                sql.append(" LIKE ");
                sql.append(JdbcUtil.wrapData("%" + kw + "%"));
            }
        }
        if (!pageParam.getQueryParams().isEmpty()) {
            boolean first = true;
            for (QueryParam queryParam : pageParam.getQueryParams()) {
                if (!kwHandle && first) {
                    first = false;
                    sql.append(" WHERE ");
                }
                sql.append(queryParam.getName())
                        .append(queryParam.getOperator())
                        .append(JdbcUtil.wrapData(queryParam.getData()));
            }
        }
        sql.append(" LIMIT ")
                .append(pageParam.getLimit())
                .append(" OFFSET ")
                .append(pageParam.getStart());
        JdbcConn connection = JdbcManager.takeoff();
        try {
            JdbcResultSet resultSet = JdbcHelper.executeQuery(connection, sql.toString());
            List<Map<String, Object>> records = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> record = new HashMap<>();
                for (ColumnDefinition columnDefinition : this.columns()) {
                    String columnName = columnDefinition.getColumnName();
                    if (resultSet.containsColumn(columnName)) {
                        record.put(columnName, resultSet.getObject(columnName));
                    }
                }
                records.add(record);
            }
            resultSet.close();
            return records;
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    /**
     * 根据主键值删除数据
     *
     * @param primaryKey 主键值
     * @return 影响行数
     * @throws Exception 异常
     */
    public int delete(Object primaryKey) throws Exception {
        PrimaryKeyColumn primaryKeyColumn = this.getPrimaryKeyColumn(primaryKey);
        return primaryKeyColumn == null ? 0 : this.delete(primaryKeyColumn);
    }

    /**
     * 根据主键列删除数据
     *
     * @param primaryKey 主键列
     * @return 影响行数
     * @throws SQLException 异常
     */
    public int delete(PrimaryKeyColumn primaryKey) throws SQLException {
        String tableName = this.tableName();
        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(JdbcUtil.wrap(tableName));
        sql.append(" WHERE ");
        sql.append(primaryKey.getColumnName());
        sql.append(" = ?");
        JdbcConn connection = JdbcManager.takeoff();
        try {
            return JdbcHelper.executeUpdate(connection, sql.toString(), primaryKey.getColumnData());
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    /**
     * 根据删除参数删除数据
     *
     * @param deleteParam 删除参数
     * @return 影响行数
     * @throws SQLException 异常
     */
    public abstract int delete(DeleteParam deleteParam) throws SQLException;
}
