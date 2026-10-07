package cn.oyzh.common.db;

import java.util.List;

/**
 * SQL显示压缩器
 *
 * @author oyzh
 * @since 2026-10-06
 */
public final class SqlCompressor {

    /**
     * 私有构造，禁止实例化
     */
    private SqlCompressor() {
    }

    /**
     * 压缩SQL（ANSI方言）
     *
     * @param sql SQL脚本
     * @return 压缩后的SQL
     */
    public static String compress(String sql) {
        return compress(sql, SqlDatabase.ANSI);
    }

    /**
     * 压缩SQL，去除注释与多余的空白字符
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return 压缩后的SQL
     */
    public static String compress(String sql, SqlDatabase database) {
        if (sql == null || sql.isBlank()) {
            return "";
        }
        String withoutComments = SqlAnalyzer.removeComments(sql, database);
        if (withoutComments.isEmpty()) {
            return "";
        }
        List<SqlToken> tokens = SqlLexer.tokenize(withoutComments, profile(database));
        StringBuilder result = new StringBuilder(withoutComments.length());
        SqlToken previous = null;
        for (SqlToken token : tokens) {
            if (token.type() == SqlTokenType.WHITESPACE || token.type() == SqlTokenType.COMMENT) {
                continue;
            }
            if (previous != null && hasWhitespaceBetween(withoutComments, previous, token)) {
                result.append(' ');
            }
            result.append(token.text());
            previous = token;
        }
        return result.toString()
                .replace("\r\n", " ")
                .replace('\r', ' ')
                .replace('\n', ' ')
                .replace('\u2028', ' ')
                .replace('\u2029', ' ')
                .strip();
    }

    /**
     * 压缩SQL
     *
     * @param sql      SQL脚本
     * @param database 数据库名称
     * @return 压缩后的SQL
     */
    public static String compress(String sql, String database) {
        return compress(sql, SqlDialects.parse(database));
    }

    /**
     * 压缩SQL（compress的别名）
     *
     * @param sql SQL脚本
     * @return 压缩后的SQL
     */
    public static String compressSql(String sql) {
        return compress(sql);
    }

    /**
     * 压缩SQL（compress的别名）
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return 压缩后的SQL
     */
    public static String compressSql(String sql, SqlDatabase database) {
        return compress(sql, database);
    }

    /**
     * 压缩SQL（compress的别名）
     *
     * @param sql      SQL脚本
     * @param database 数据库名称
     * @return 压缩后的SQL
     */
    public static String compressSql(String sql, String database) {
        return compress(sql, database);
    }

    /**
     * 获取数据库对应的词法分析配置
     *
     * @param database 数据库类型
     * @return 词法分析配置
     */
    private static SqlLexicalProfile profile(SqlDatabase database) {
        return SqlDialects.get(database).getLexicalProfile();
    }

    /**
     * 判断两个词法单元之间是否存在空白字符
     *
     * @param sql      SQL脚本
     * @param previous 前一个词法单元
     * @param current  当前词法单元
     * @return 是否存在空白字符
     */
    private static boolean hasWhitespaceBetween(String sql, SqlToken previous, SqlToken current) {
        for (int index = previous.end(); index < current.start(); index++) {
            if (Character.isWhitespace(sql.charAt(index))) {
                return true;
            }
        }
        return false;
    }
}
