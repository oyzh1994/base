package cn.oyzh.common.db;

/**
 * 支持的数据库类型
 *
 * @author oyzh
 * @since 2026-10-06
 */
public enum SqlDatabase {

    /** ANSI标准SQL */
    ANSI,
    /** MySQL数据库 */
    MYSQL,
    /** MariaDB数据库 */
    MARIADB,
    /** PostgreSQL数据库 */
    POSTGRESQL,
    /** Oracle数据库 */
    ORACLE,
    /** SQL Server数据库 */
    SQL_SERVER,
    /** SQLite数据库 */
    SQLITE,
    /** H2数据库 */
    H2,
    /** 达梦数据库 */
    DM
}
