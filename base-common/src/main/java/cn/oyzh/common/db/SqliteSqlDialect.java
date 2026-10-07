package cn.oyzh.common.db;

/**
 * SQLite SQL方言
 *
 * @author oyzh
 * @since 2026/10/6
 */
public class SqliteSqlDialect extends AbstractSqlDialect {

    /**
     * 构造SQLite SQL方言实例
     */
    public SqliteSqlDialect() {
        super(SqlDatabase.SQLITE, SqlLexicalProfile.ANSI);
    }
}
