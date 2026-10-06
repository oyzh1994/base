package cn.oyzh.common.db;

/**
 * PostgreSQL SQL方言
 *
 * @author oyzh
 * @since 2026/10/6
 */
public class PostgreSqlSqlDialect extends AbstractSqlDialect {

    public PostgreSqlSqlDialect() {
        super(SqlDatabase.POSTGRESQL, SqlLexicalProfile.POSTGRESQL);
    }
}
