package cn.oyzh.store.jdbc.h2;

import cn.oyzh.store.jdbc.ColumnDefinition;
import cn.oyzh.store.jdbc.JdbcConn;
import cn.oyzh.store.jdbc.JdbcHelper;
import cn.oyzh.store.jdbc.JdbcKeyValueOperator;
import cn.oyzh.store.jdbc.JdbcManager;
import cn.oyzh.store.jdbc.JdbcUtil;
import cn.oyzh.store.jdbc.TableDefinition;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class H2KeyValueOperator extends JdbcKeyValueOperator {

    public H2KeyValueOperator(TableDefinition tableDefinition) {
        super(tableDefinition);
    }

    @Override
    public boolean initTable() throws Exception {
        JdbcConn connection = JdbcManager.takeoff();
        try {
            String tableName = this.tableName();
            ResultSet resultSet = connection.getTables(tableName);
            boolean exists = resultSet.next();
            resultSet.close();
            if (exists) {
                this.alterTable();
            } else {
                this.createTable();
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
            String tableName = this.tableName();
            List<ColumnDefinition> addedColumns = new ArrayList<>();
            List<ColumnDefinition> changedColumns = new ArrayList<>();
            ResultSet resultSet = connection.getColumns(tableName, "KEY");
            try (resultSet) {
                ColumnDefinition definition = new ColumnDefinition();
                definition.setColumnName("KEY");
                definition.setColumnType("VARCHAR");
                // 字段不存在
                if (!resultSet.next()) {
                    addedColumns.add(definition);
                } else if (!H2Util.checkSqlType("CHARACTER VARYING", resultSet.getString("TYPE_NAME"))) { // 字段类型不相同
                    changedColumns.add(definition);
                }
            }
            ResultSet resultSet1 = connection.getColumns(tableName, "VALUE");
            try (resultSet1) {
                ColumnDefinition definition = new ColumnDefinition();
                definition.setColumnName("VALUE");
                definition.setColumnType("CHARACTER VARYING");
                // 字段不存在
                if (!resultSet1.next()) {
                    addedColumns.add(definition);
                } else if (!H2Util.checkSqlType("CHARACTER VARYING", resultSet1.getString("TYPE_NAME"))) { // 字段类型不相同
                    changedColumns.add(definition);
                }
            }
            if (!addedColumns.isEmpty() || !changedColumns.isEmpty()) {
                // 修改
                for (ColumnDefinition column : changedColumns) {
                    StringBuilder sql = new StringBuilder();
                    sql.append("ALTER TABLE ");
                    sql.append(JdbcUtil.wrap(tableName));
                    sql.append(" ALTER COLUMN ");
                    sql.append(H2Util.wrap(column.getColumnName()));
                    sql.append(" ");
                    sql.append(column.getColumnType().toUpperCase());
                    JdbcHelper.executeUpdate(connection, sql.toString());
                }
                // 新增
                for (ColumnDefinition column : addedColumns) {
                    StringBuilder sql = new StringBuilder();
                    sql.append("ALTER TABLE ");
                    sql.append(JdbcUtil.wrap(tableName));
                    sql.append(" ADD COLUMN ");
                    sql.append(H2Util.wrap(column.getColumnName()));
                    sql.append(" ");
                    sql.append(column.getColumnType().toUpperCase());
                    JdbcHelper.executeUpdate(connection, sql.toString());
                }
            }
        } finally {
            JdbcManager.giveback(connection);
        }
    }

    @Override
    protected void createTable() throws SQLException {
        JdbcConn connection = JdbcManager.takeoff();
        try {
            String tableName = this.tableName();
            StringBuilder sql = new StringBuilder();
            sql.append("CREATE TABLE ")
                    .append(H2Util.wrap(tableName))
                    .append(" (");
            sql.append(H2Util.wrap("KEY")).append(" VARCHAR,");
            sql.append(H2Util.wrap("VALUE")).append(" VARCHAR");
            sql.append(")");
            JdbcHelper.executeUpdate(connection, sql.toString());
        } finally {
            JdbcManager.giveback(connection);
        }
    }

}
