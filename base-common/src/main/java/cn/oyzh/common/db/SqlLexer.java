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

    private SqlLexer() {
    }

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

    private static SqlToken token(SqlTokenType type, String sql, int start, int end) {
        return new SqlToken(type, sql.substring(start, end), start, end);
    }

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

    private static boolean isLineComment(String sql, int index, SqlLexicalProfile profile) {
        if (index + 1 < sql.length() && sql.charAt(index) == '-' && sql.charAt(index + 1) == '-') {
            return true;
        }
        return profile.isHashComment() && sql.charAt(index) == '#';
    }

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

    private static boolean startsOracleQuote(String sql, int index) {
        if ((sql.charAt(index) != 'q' && sql.charAt(index) != 'Q')
                || index + 2 >= sql.length()
                || sql.charAt(index + 1) != '\'') {
            return false;
        }
        char open = sql.charAt(index + 2);
        return closeQuote(open) != 0;
    }

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

    private static char closeQuote(char open) {
        return switch (open) {
            case '[' -> ']';
            case '{' -> '}';
            case '(' -> ')';
            case '<' -> '>';
            default -> open;
        };
    }

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
