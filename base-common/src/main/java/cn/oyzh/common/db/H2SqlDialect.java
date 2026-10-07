package cn.oyzh.common.db;

/**
 * H2 SQL方言
 *
 * @author oyzh
 * @since 2026/10/6
 */
public class H2SqlDialect extends AbstractSqlDialect {

    /**
     * 构造H2 SQL方言实例
     */
    public H2SqlDialect() {
        super(SqlDatabase.H2, SqlLexicalProfile.ANSI);
    }
}
