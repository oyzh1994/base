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

    private SqlDialects() {
    }

    public static SqlDialect get(SqlDatabase database) {
        return DIALECTS.get(database == null ? SqlDatabase.ANSI : database);
    }

    public static SqlDialect get(String database) {
        return get(parse(database));
    }

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
