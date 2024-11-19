package cn.oyzh.store.jdbc;

import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.common.util.StringUtil;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author oyzh
 * @since 2024-09-25
 */
@UtilityClass
public class JdbcManager {

    /**
     * 方言
     */
    public static JdbcDialect dialect;

    /**
     * 连接列表
     */
    private static final List<JdbcConn> CONNECTIONS = new CopyOnWriteArrayList<>();

    static {
        try {
            String dialect = System.getProperty(JdbcConst.DB_DIALECT);
            if (StringUtil.isEmpty(dialect)) {
                JdbcManager.dialect = JdbcDialect.H2;
            } else {
                JdbcManager.dialect = JdbcDialect.valueOf(dialect);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JdbcManager.dialect = JdbcDialect.H2;
        }
    }

    public static boolean isH2Dialect() {
        return JdbcDialect.H2 == dialect;
    }

    public static boolean isSqliteDialect() {
        return JdbcDialect.SQLITE == dialect;
    }

    /**
     * 获取连接
     *
     * @return Connection
     * @throws SQLException 异常
     */
    public static JdbcConn takeoff() throws SQLException {
        for (JdbcConn connection : CONNECTIONS) {
            if (connection.isUsable()) {
                return connection;
            }
        }
        String dbFile = System.getProperty(JdbcConst.DB_FILE);
        if (StringUtil.isBlank(dbFile)) {
            dbFile = System.getProperty("user.dir") + File.separator + "temp_db";
        }
        Connection connection;
        String url = "";
        if (dialect == JdbcDialect.H2) {
            url = "jdbc:h2:" + dbFile;
            String cacheSize = System.getProperty(JdbcConst.DB_CACHE_SIZE);
            if (cacheSize != null) {
                url += ";CACHE_SIZE=" + cacheSize;
            }
            String cacheType = System.getProperty(JdbcConst.DB_CACHE_TYPE);
            if (cacheType != null) {
                url += ";CACHE_TYPE=" + cacheType;
            }
            String pageSize = System.getProperty(JdbcConst.DB_PAGE_SIZE);
            if (pageSize != null) {
                url += ";PAGE_SIZE=" + pageSize;
            }
        } else {
            url = "jdbc:sqlite:" + dbFile;
        }
        connection = DriverManager.getConnection(url);
        JdbcConn jdbcConn = new JdbcConn(connection);
        CONNECTIONS.add(jdbcConn);
        return jdbcConn;
    }

    /**
     * 归还连接
     *
     * @param connection 连接
     */
    public static void giveback(Connection connection) {
        if (connection != null) {
            List<JdbcConn> invalid = null;
            for (JdbcConn sqlConnection : CONNECTIONS) {
                if (sqlConnection.isInvalid()) {
                    if (invalid == null) {
                        invalid = new ArrayList<>();
                    }
                    invalid.add(sqlConnection);
                }
                if (sqlConnection.getConnection() == connection) {
                    sqlConnection.giveback();
                    break;
                }
            }
            if (CollectionUtil.isNotEmpty(invalid)) {
                CONNECTIONS.removeAll(invalid);
            }
        }
    }

    /**
     * 归还连接
     *
     * @param connection 连接
     */
    public static void giveback(JdbcConn connection) {
        if (connection != null) {
            connection.giveback();
            List<JdbcConn> invalid = null;
            for (JdbcConn sqlConnection : CONNECTIONS) {
                if (sqlConnection.isInvalid()) {
                    if (invalid == null) {
                        invalid = new ArrayList<>();
                    }
                    invalid.add(sqlConnection);
                }
            }
            if (CollectionUtil.isNotEmpty(invalid)) {
                CONNECTIONS.removeAll(invalid);
            }
        }
    }
}
