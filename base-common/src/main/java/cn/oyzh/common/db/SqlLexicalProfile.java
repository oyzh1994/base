package cn.oyzh.common.db;

/**
 * SQL词法配置
 *
 * @author oyzh
 * @since 2026/10/6
 */
public enum SqlLexicalProfile {

    ANSI,
    MYSQL,
    POSTGRESQL,
    ORACLE,
    SQL_SERVER,
    DM;

    public boolean isBackslashEscaped() {
        return this == MYSQL || this == DM;
    }

    public boolean isHashComment() {
        return this == MYSQL;
    }

    public boolean isBacktickIdentifier() {
        return this == MYSQL;
    }

    public boolean isBracketIdentifier() {
        return this == SQL_SERVER || this == DM;
    }

    public boolean isDollarQuote() {
        return this == POSTGRESQL || this == DM;
    }

    public boolean isOracleQuote() {
        return this == ORACLE || this == DM;
    }

    public boolean isSlashTerminator() {
        return this == ORACLE || this == DM;
    }

    public boolean isGoBatch() {
        return this == SQL_SERVER;
    }

    public boolean isDelimiterDirective() {
        return this == MYSQL || this == DM;
    }

    public boolean isNestedBlockComment() {
        return this == POSTGRESQL || this == ORACLE || this == DM;
    }
}
