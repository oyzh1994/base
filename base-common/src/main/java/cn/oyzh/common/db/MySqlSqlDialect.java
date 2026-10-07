package cn.oyzh.common.db;

/**
 * MySQL SQL方言
 *
 * @author oyzh
 * @since 2026-10-06
 */
public class MySqlSqlDialect extends AbstractSqlDialect {

    /**
     * 构造MySQL SQL方言实例
     */
    public MySqlSqlDialect() {
        super(SqlDatabase.MYSQL, SqlLexicalProfile.MYSQL);
    }
}
