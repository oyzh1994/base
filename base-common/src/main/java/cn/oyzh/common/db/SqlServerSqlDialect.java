package cn.oyzh.common.db;

/**
 * SQL Server SQL方言
 *
 * @author oyzh
 * @since 2026/10/6
 */
public class SqlServerSqlDialect extends AbstractSqlDialect {

    public SqlServerSqlDialect() {
        super(SqlDatabase.SQL_SERVER, SqlLexicalProfile.SQL_SERVER);
    }
}
