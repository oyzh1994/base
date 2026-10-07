package cn.oyzh.common.db;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL词法分析器
 *
 * @author oyzh
 * @since 2026/10/6
 */
public final class SqlLexer {

    /**
     * 私有构造，禁止实例化
     */
    private SqlLexer() {
    }

    /**
     * 对SQL进行词法分析，拆解为词法单元列表
     *
     * @param sql     SQL脚本
     * @param profile 词法分析配置
     * @return 词法单元列表
     */
    public static List<SqlToken> tokenize(String sql, SqlLexicalProfile profile) {
        List<SqlToken> tokens = new ArrayList<>();
        if (sql == null || sql.isEmpty()) {
            return tokens;
        }
        int index = 0;
        while (index < sql.length()) {
            int start = index;
            char current = sql.charAt(index);
            if (Character.isWhitespace(current)) {
                index = scanWhitespace(sql, index);
                tokens.add(token(SqlTokenType.WHITESPACE, sql, start, index));
            } else if (isLineComment(sql, index, profile)) {
                index = scanLineComment(sql, index, profile);
                tokens.add(token(SqlTokenType.COMMENT, sql, start, index));
            } else if (current == '/' && index + 1 < sql.length() && sql.charAt(index + 1) == '*') {
                index = scanBlockComment(sql, index, profile);
                tokens.add(token(SqlTokenType.COMMENT, sql, start, index));
            } else if (current == '\'' || current == '\"' || current == '`'
                    || (current == '[' && profile.isBracketIdentifier())) {
                index = scanQuoted(sql, index, current, profile);
                SqlTokenType type = current == '\'' ? SqlTokenType.STRING : SqlTokenType.IDENTIFIER;
                tokens.add(token(type, sql, start, index));
            } else if (profile.isDollarQuote() && current == '$' && startsDollarQuote(sql, index)) {
                index = scanDollarQuote(sql, index);
                tokens.add(token(SqlTokenType.STRING, sql, start, index));
            } else if (profile.isOracleQuote() && startsOracleQuote(sql, index)) {
                index = scanOracleQuote(sql, index);
                tokens.add(token(SqlTokenType.STRING, sql, start, index));
            } else if (Character.isLetter(current) || current == '_') {
                index = scanWord(sql, index);
                tokens.add(token(SqlTokenType.WORD, sql, start, index));
            } else if (Character.isDigit(current)) {
                index = scanNumber(sql, index);
                tokens.add(token(SqlTokenType.NUMBER, sql, start, index));
            } else if (isParameterStart(sql, index)) {
                index = scanParameter(sql, index);
                tokens.add(token(SqlTokenType.PARAMETER, sql, start, index));
            } else {
                index = scanPunctuation(sql, index);
                tokens.add(token(SqlTokenType.PUNCTUATION, sql, start, index));
            }
        }
        return tokens;
    }

    /**
     * 根据起止位置构建词法单元
     *
     * @param type  词法单元类型
     * @param sql   SQL脚本
     * @param start 起始位置
     * @param end   结束位置
     * @return 词法单元
     */
    private static SqlToken token(SqlTokenType type, String sql, int start, int end) {
        return new SqlToken(type, sql.substring(start, end), start, end);
    }

    /**
     * 扫描空白字符，遇到换行时终止，以便保留行结构
     *
     * @param sql   SQL脚本
     * @param index 起始位置
     * @return 结束后的位置
     */
    private static int scanWhitespace(String sql, int index) {
        int result = index;
        while (result < sql.length() && Character.isWhitespace(sql.charAt(result))) {
            if (sql.charAt(result) == '\r' || sql.charAt(result) == '\n') {
                result++;
                if (result < sql.length() && sql.charAt(result - 1) == '\r'
                        && sql.charAt(result) == '\n') {
                    result++;
                }
                break;
            }
            result++;
        }
        return result;
    }

    /**
     * 判断当前位置是否为行注释的开始
     *
     * @param sql     SQL脚本
     * @param index   起始位置
     * @param profile 词法分析配置
     * @return 结果
     */
    private static boolean isLineComment(String sql, int index, SqlLexicalProfile profile) {
        if (index + 1 < sql.length() && sql.charAt(index) == '-' && sql.charAt(index + 1) == '-') {
            return true;
        }
        return profile.isHashComment() && sql.charAt(index) == '#';
    }

    /**
     * 扫描行注释，直到行尾
     *
     * @param sql     SQL脚本
     * @param index   起始位置
     * @param profile 词法分析配置
     * @return 结束后的位置
     */
    private static int scanLineComment(String sql, int index, SqlLexicalProfile profile) {
        int result = index;
        while (result < sql.length()) {
            char current = sql.charAt(result);
            if (current == '\n' || current == '\r') {
                break;
            }
            result++;
        }
        return result;
    }

    /**
     * 扫描块注释，配置支持嵌套时按嵌套层级匹配结束
     *
     * @param sql     SQL脚本
     * @param index   起始位置（指向注释起始的斜杠星号）
     * @param profile 词法分析配置
     * @return 结束后的位置
     */
    private static int scanBlockComment(String sql, int index, SqlLexicalProfile profile) {
        int depth = 1;
        int result = index + 2;
        while (result < sql.length()) {
            if (result + 1 < sql.length() && sql.charAt(result) == '/' && sql.charAt(result + 1) == '*') {
                if (profile.isNestedBlockComment()) {
                    depth++;
                }
                result += 2;
            } else if (result + 1 < sql.length() && sql.charAt(result) == '*' && sql.charAt(result + 1) == '/') {
                depth--;
                result += 2;
                if (depth == 0) {
                    break;
                }
            } else {
                result++;
            }
        }
        return result;
    }

    /**
     * 扫描被引号包裹的内容，连续两个引号视为转义，支持反斜杠转义配置
     *
     * @param sql     SQL脚本
     * @param index   起始位置
     * @param quote   起始引号字符
     * @param profile 词法分析配置
     * @return 结束后的位置
     */
    private static int scanQuoted(String sql, int index, char quote, SqlLexicalProfile profile) {
        char closing = quote == '[' ? ']' : quote;
        int result = index + 1;
        while (result < sql.length()) {
            char current = sql.charAt(result);
            if (current == closing) {
                if (result + 1 < sql.length() && sql.charAt(result + 1) == closing) {
                    result += 2;
                    continue;
                }
                return result + 1;
            }
            if ((quote == '\'' || quote == '\"') && profile.isBackslashEscaped() && current == '\\') {
                result += 2;
            } else {
                result++;
            }
        }
        return sql.length();
    }

    /**
     * 判断当前位置是否为美元符引用字符串的开始（$tag$形式）
     *
     * @param sql   SQL脚本
     * @param index 起始位置
     * @return 结果
     */
    private static boolean startsDollarQuote(String sql, int index) {
        int result = index + 1;
        while (result < sql.length()) {
            char current = sql.charAt(result);
            if (current == '$') {
                return true;
            }
            if (!Character.isLetterOrDigit(current) && current != '_') {
                return false;
            }
            result++;
        }
        return false;
    }

    /**
     * 扫描美元符引用字符串，直到匹配到相同的标记
     *
     * @param sql   SQL脚本
     * @param index 起始位置
     * @return 结束后的位置
     */
    private static int scanDollarQuote(String sql, int index) {
        int markerEnd = index + 1;
        while (markerEnd < sql.length() && sql.charAt(markerEnd) != '$') {
            markerEnd++;
        }
        markerEnd++;
        String marker = sql.substring(index, markerEnd);
        int close = sql.indexOf(marker, markerEnd);
        return close < 0 ? sql.length() : close + marker.length();
    }

    /**
     * 判断当前位置是否为Oracle的q-quote字符串开始（q'x...x'形式）
     *
     * @param sql   SQL脚本
     * @param index 起始位置
     * @return 结果
     */
    private static boolean startsOracleQuote(String sql, int index) {
        if ((sql.charAt(index) != 'q' && sql.charAt(index) != 'Q')
                || index + 2 >= sql.length()
                || sql.charAt(index + 1) != '\'') {
            return false;
        }
        char open = sql.charAt(index + 2);
        return closeQuote(open) != 0;
    }

    /**
     * 扫描Oracle的q-quote字符串，直到匹配到对应的结束定界符
     *
     * @param sql   SQL脚本
     * @param index 起始位置
     * @return 结束后的位置
     */
    private static int scanOracleQuote(String sql, int index) {
        char open = sql.charAt(index + 2);
        char close = closeQuote(open);
        int result = index + 3;
        while (result + 1 < sql.length()) {
            if (sql.charAt(result) == close && sql.charAt(result + 1) == '\'') {
                return result + 2;
            }
            result++;
        }
        return sql.length();
    }

    /**
     * 获取q-quote起始定界符对应的结束定界符
     *
     * @param open 起始定界符
     * @return 结束定界符
     */
    private static char closeQuote(char open) {
        return switch (open) {
            case '[' -> ']';
            case '{' -> '}';
            case '(' -> ')';
            case '<' -> '>';
            default -> open;
        };
    }

    /**
     * 扫描单词（标识符或关键字）
     *
     * @param sql   SQL脚本
     * @param index 起始位置
     * @return 结束后的位置
     */
    private static int scanWord(String sql, int index) {
        int result = index;
        while (result < sql.length()) {
            char current = sql.charAt(result);
            if (!Character.isLetterOrDigit(current) && current != '_' && current != '$') {
                break;
            }
            result++;
        }
        return result;
    }

    /**
     * 扫描数字，支持小数点与指数部分
     *
     * @param sql   SQL脚本
     * @param index 起始位置
     * @return 结束后的位置
     */
    private static int scanNumber(String sql, int index) {
        int result = index;
        boolean exponent = false;
        while (result < sql.length()) {
            char current = sql.charAt(result);
            if (Character.isDigit(current) || current == '.') {
                result++;
            } else if ((current == 'e' || current == 'E') && !exponent) {
                exponent = true;
                result++;
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * 判断当前位置是否为参数占位符的开始
     *
     * @param sql   SQL脚本
     * @param index 起始位置
     * @return 结果
     */
    private static boolean isParameterStart(String sql, int index) {
        char current = sql.charAt(index);
        if (current == '?' || current == '@') {
            return true;
        }
        if (current == ':' && index + 1 < sql.length() && sql.charAt(index + 1) != ':') {
            return Character.isLetter(sql.charAt(index + 1)) || sql.charAt(index + 1) == '_';
        }
        return (current == '#' || current == '$')
                && index + 1 < sql.length()
                && (sql.charAt(index + 1) == '{');
    }

    /**
     * 扫描参数占位符，支持?、@、:name、#{name}、${name}等形式
     *
     * @param sql   SQL脚本
     * @param index 起始位置
     * @return 结束后的位置
     */
    private static int scanParameter(String sql, int index) {
        char current = sql.charAt(index);
        if (current == '#' || current == '$') {
            int close = sql.indexOf('}', index + 2);
            return close < 0 ? sql.length() : close + 1;
        }
        if (current == '?') {
            int result = index + 1;
            while (result < sql.length() && Character.isDigit(sql.charAt(result))) {
                result++;
            }
            return result;
        }
        int result = index + 1;
        while (result < sql.length()
                && (Character.isLetterOrDigit(sql.charAt(result)) || sql.charAt(result) == '_')) {
            result++;
        }
        return result;
    }

    /**
     * 扫描标点符号，双字符运算符整体作为一个词法单元
     *
     * @param sql   SQL脚本
     * @param index 起始位置
     * @return 结束后的位置
     */
    private static int scanPunctuation(String sql, int index) {
        if (index + 1 < sql.length()) {
            String pair = sql.substring(index, index + 2);
            if (pair.equals("::") || pair.equals("||") || pair.equals("<=")
                    || pair.equals(">=") || pair.equals("<>") || pair.equals("!=")
                    || pair.equals("=>") || pair.equals(":=")) {
                return index + 2;
            }
        }
        return index + 1;
    }
}
