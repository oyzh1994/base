package cn.oyzh.common.db;

/**
 * ANSI SQL方言
 *
 * @author oyzh
 * @since 2026-10-06
 */
public class AnsiSqlDialect extends AbstractSqlDialect {

    /**
     * 构造ANSI SQL方言实例
     */
    public AnsiSqlDialect() {
        super(SqlDatabase.ANSI, SqlLexicalProfile.ANSI);
    }
}
