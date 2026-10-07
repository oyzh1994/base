package cn.oyzh.common.db;

/**
 * MariaDB SQL方言
 *
 * @author oyzh
 * @since 2026/10/6
 */
public class MariaDbSqlDialect extends AbstractSqlDialect {

    /**
     * 构造MariaDB SQL方言实例
     */
    public MariaDbSqlDialect() {
        super(SqlDatabase.MARIADB, SqlLexicalProfile.MYSQL);
    }
}
