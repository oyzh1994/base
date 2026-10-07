package cn.oyzh.common.db;

/**
 * PostgreSQL SQL方言
 *
 * @author oyzh
 * @since 2026-10-06
 */
public class PostgreSqlSqlDialect extends AbstractSqlDialect {

    /**
     * 构造PostgreSQL SQL方言实例
     */
    public PostgreSqlSqlDialect() {
        super(SqlDatabase.POSTGRESQL, SqlLexicalProfile.POSTGRESQL);
    }
}
