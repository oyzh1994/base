package cn.oyzh.common.db;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

/**
 * SQL方言工厂
 *
 * @author oyzh
 * @since 2026/10/6
 */
public final class SqlDialects {

    /** 数据库类型与SQL方言的映射 */
    private static final Map<SqlDatabase, SqlDialect> DIALECTS = new EnumMap<>(SqlDatabase.class);

    static {
        DIALECTS.put(SqlDatabase.ANSI, new AnsiSqlDialect());
        DIALECTS.put(SqlDatabase.MYSQL, new MySqlSqlDialect());
        DIALECTS.put(SqlDatabase.MARIADB, new MariaDbSqlDialect());
        DIALECTS.put(SqlDatabase.POSTGRESQL, new PostgreSqlSqlDialect());
        DIALECTS.put(SqlDatabase.ORACLE, new OracleSqlDialect());
        DIALECTS.put(SqlDatabase.SQL_SERVER, new SqlServerSqlDialect());
        DIALECTS.put(SqlDatabase.SQLITE, new SqliteSqlDialect());
        DIALECTS.put(SqlDatabase.H2, new H2SqlDialect());
        DIALECTS.put(SqlDatabase.DM, new DmSqlDialect());
    }

    /**
     * 私有构造，禁止实例化
     */
    private SqlDialects() {
    }

    /**
     * 获取指定数据库类型的SQL方言
     *
     * @param database 数据库类型
     * @return SQL方言
     */
    public static SqlDialect get(SqlDatabase database) {
        return DIALECTS.get(database == null ? SqlDatabase.ANSI : database);
    }

    /**
     * 获取指定数据库名称的SQL方言
     *
     * @param database 数据库名称
     * @return SQL方言
     */
    public static SqlDialect get(String database) {
        return get(parse(database));
    }

    /**
     * 解析数据库名称，无法识别时返回ANSI
     *
     * @param database 数据库名称
     * @return 数据库类型
     */
    public static SqlDatabase parse(String database) {
        if (database == null || database.isBlank()) {
            return SqlDatabase.ANSI;
        }
        if (database.contains("达梦")) {
            return SqlDatabase.DM;
        }
        String normalized = database.trim()
                .toUpperCase(Locale.ROOT)
                .replaceAll("[\\s_\\-]", "");
        return switch (normalized) {
            case "MYSQL" -> SqlDatabase.MYSQL;
            case "MARIADB" -> SqlDatabase.MARIADB;
            case "POSTGRESQL", "POSTGRES", "PG" -> SqlDatabase.POSTGRESQL;
            case "ORACLE" -> SqlDatabase.ORACLE;
            case "SQLSERVER", "MSSQL" -> SqlDatabase.SQL_SERVER;
            case "SQLITE" -> SqlDatabase.SQLITE;
            case "H2" -> SqlDatabase.H2;
            case "DM", "DAMENG", "DM8" -> SqlDatabase.DM;
            default -> SqlDatabase.ANSI;
        };
    }
}
