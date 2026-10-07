package cn.oyzh.common.db;

/**
 * Oracle SQL方言
 *
 * @author oyzh
 * @since 2026/10/6
 */
public class OracleSqlDialect extends AbstractSqlDialect {

    /**
     * 构造Oracle SQL方言实例
     */
    public OracleSqlDialect() {
        super(SqlDatabase.ORACLE, SqlLexicalProfile.ORACLE);
    }
}
