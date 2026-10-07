package cn.oyzh.common.db;

/**
 * SQL词法配置
 *
 * @author oyzh
 * @since 2026/10/6
 */
public enum SqlLexicalProfile {

    /** ANSI标准SQL */
    ANSI,
    /** MySQL */
    MYSQL,
    /** PostgreSQL */
    POSTGRESQL,
    /** Oracle */
    ORACLE,
    /** SQL Server */
    SQL_SERVER,
    /** 达梦数据库 */
    DM;

    /**
     * 是否使用反斜杠转义
     *
     * @return 结果
     */
    public boolean isBackslashEscaped() {
        return this == MYSQL || this == DM;
    }

    /**
     * 是否支持井号（#）行注释
     *
     * @return 结果
     */
    public boolean isHashComment() {
        return this == MYSQL;
    }

    /**
     * 是否使用反引号作为标识符定界符
     *
     * @return 结果
     */
    public boolean isBacktickIdentifier() {
        return this == MYSQL;
    }

    /**
     * 是否使用方括号作为标识符定界符
     *
     * @return 结果
     */
    public boolean isBracketIdentifier() {
        return this == SQL_SERVER || this == DM;
    }

    /**
     * 是否支持美元符（$）引用字符串
     *
     * @return 结果
     */
    public boolean isDollarQuote() {
        return this == POSTGRESQL || this == DM;
    }

    /**
     * 是否支持Oracle的q-quote字符串
     *
     * @return 结果
     */
    public boolean isOracleQuote() {
        return this == ORACLE || this == DM;
    }

    /**
     * 是否使用斜杠（/）作为语句结束符
     *
     * @return 结果
     */
    public boolean isSlashTerminator() {
        return this == ORACLE || this == DM;
    }

    /**
     * 是否使用GO作为批处理结束标记
     *
     * @return 结果
     */
    public boolean isGoBatch() {
        return this == SQL_SERVER;
    }

    /**
     * 是否支持DELIMITER指令
     *
     * @return 结果
     */
    public boolean isDelimiterDirective() {
        return this == MYSQL || this == DM;
    }

    /**
     * 是否支持嵌套块注释
     *
     * @return 结果
     */
    public boolean isNestedBlockComment() {
        return this == POSTGRESQL || this == ORACLE || this == DM;
    }
}
