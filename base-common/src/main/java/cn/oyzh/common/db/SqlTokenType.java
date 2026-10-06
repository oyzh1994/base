package cn.oyzh.common.db;

/**
 * SQL词法单元类型
 *
 * @author oyzh
 * @since 2026/10/6
 */
public enum SqlTokenType {

    WHITESPACE,
    COMMENT,
    STRING,
    IDENTIFIER,
    WORD,
    NUMBER,
    PARAMETER,
    PUNCTUATION,
    OTHER
}
