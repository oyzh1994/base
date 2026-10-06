package cn.oyzh.common.db;

/**
 * ANSI SQL方言
 *
 * @author oyzh
 * @since 2026/10/6
 */
public class AnsiSqlDialect extends AbstractSqlDialect {

    public AnsiSqlDialect() {
        super(SqlDatabase.ANSI, SqlLexicalProfile.ANSI);
    }
}
