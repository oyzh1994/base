package cn.oyzh.common.db;

/**
 * SQL词法单元类型
 *
 * @author oyzh
 * @since 2026-10-06
 */
public enum SqlTokenType {

    /** 空白字符 */
    WHITESPACE,
    /** 注释 */
    COMMENT,
    /** 字符串字面量 */
    STRING,
    /** 标识符（被引号、反引号或方括号包裹的名称） */
    IDENTIFIER,
    /** 关键字或普通单词 */
    WORD,
    /** 数字 */
    NUMBER,
    /** 参数占位符 */
    PARAMETER,
    /** 标点符号 */
    PUNCTUATION,
    /** 其他无法归类的字符 */
    OTHER
}
