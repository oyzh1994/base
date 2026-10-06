package cn.oyzh.common.db;

/**
 * 达梦数据库SQL方言
 *
 * @author oyzh
 * @since 2026/10/6
 */
public class DmSqlDialect extends AbstractSqlDialect {

    public DmSqlDialect() {
        super(SqlDatabase.DM, SqlLexicalProfile.DM);
    }
}
