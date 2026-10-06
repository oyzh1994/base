package cn.oyzh.common.db;

import java.util.List;

/**
 * SQL显示压缩器
 *
 * @author oyzh
 * @since 2026/10/6
 */
public final class SqlCompressor {

    private SqlCompressor() {
    }

    public static String compress(String sql) {
        return compress(sql, SqlDatabase.ANSI);
    }

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

    public static String compress(String sql, String database) {
        return compress(sql, SqlDialects.parse(database));
    }

    public static String compressSql(String sql) {
        return compress(sql);
    }

    public static String compressSql(String sql, SqlDatabase database) {
        return compress(sql, database);
    }

    public static String compressSql(String sql, String database) {
        return compress(sql, database);
    }

    private static SqlLexicalProfile profile(SqlDatabase database) {
        return SqlDialects.get(database).getLexicalProfile();
    }

    private static boolean hasWhitespaceBetween(String sql, SqlToken previous, SqlToken current) {
        for (int index = previous.end(); index < current.start(); index++) {
            if (Character.isWhitespace(sql.charAt(index))) {
                return true;
            }
        }
        return false;
    }
}
