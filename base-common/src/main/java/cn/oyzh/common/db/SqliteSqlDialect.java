package cn.oyzh.common.db;

/**
 * SQLite SQL方言
 *
 * @author oyzh
 * @since 2026/10/6
 */
public class SqliteSqlDialect extends AbstractSqlDialect {

    public SqliteSqlDialect() {
        super(SqlDatabase.SQLITE, SqlLexicalProfile.ANSI);
    }
}
