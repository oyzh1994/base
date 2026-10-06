package cn.oyzh.common.db;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * 基于词法分析的SQL美化器
 *
 * @author oyzh
 * @since 2026/10/6
 */
public class LexicalSqlFormatter implements SqlFormatter {

    private static final Set<String> KEYWORDS = Set.of(
            "ALL", "ALTER", "AND", "ANY", "AS", "ASC", "BEGIN", "BETWEEN", "BY",
            "CALL", "CASE", "CAST", "CONNECT", "CONSTRAINT", "CREATE", "CROSS",
            "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "DECLARE", "DEFAULT",
            "DELETE", "DESC", "DISTINCT", "DO", "DROP", "ELSE", "END", "ESCAPE",
            "EXCEPT", "EXISTS", "EXPLAIN", "FALSE", "FETCH", "FOR", "FROM", "FULL",
            "FUNCTION", "GROUP", "HAVING", "IF", "IN", "INDEX", "INNER", "INSERT",
            "INTERSECT", "INTERVAL", "INTO", "IS", "JOIN", "KEY", "LEFT", "LIKE",
            "LIMIT", "LOOP", "MERGE", "NATURAL", "NOT", "NULL", "NULLS", "OFFSET",
            "ON", "OR", "ORDER", "OUTER", "OVER", "PACKAGE", "PARTITION", "PRECISION",
            "PRIMARY", "PROCEDURE", "REFERENCES", "REPEAT", "REPLACE", "RETURN",
            "RETURNING", "RIGHT", "ROW", "SELECT", "SET", "START", "TABLE", "THEN",
            "TRIGGER", "TRUE", "TRUNCATE", "UNION", "UNIQUE", "UNKNOWN", "UPDATE",
            "USING", "VALUES", "VIEW", "WHEN", "WHERE", "WHILE", "WINDOW", "WITH", "WORK"
    );

    private static final Set<String> CLAUSE_STARTS = Set.of(
            "SELECT", "FROM", "WHERE", "HAVING", "VALUES", "SET", "UNION",
            "INTERSECT", "EXCEPT", "LIMIT", "OFFSET", "RETURNING", "WINDOW"
    );

    private static final Set<String> BLOCK_STARTS = Set.of(
            "BEGIN", "DECLARE", "IF", "LOOP", "WHILE", "REPEAT"
    );

    private final SqlLexicalProfile profile;

    public LexicalSqlFormatter(SqlLexicalProfile profile) {
        this.profile = profile;
    }

    @Override
    public String format(String sql) {
        if (sql == null || sql.isBlank()) {
            return "";
        }
        List<SqlToken> tokens = SqlLexer.tokenize(sql, profile);
        StringBuilder output = new StringBuilder(sql.length() + 32);
        Deque<Boolean> parentheses = new ArrayDeque<>();
        int indent = 0;
        int caseDepth = 0;
        int blockDepth = 0;
        for (int index = 0; index < tokens.size(); index++) {
            SqlToken token = tokens.get(index);
            if (token.type() == SqlTokenType.WHITESPACE) {
                continue;
            }
            if (token.type() == SqlTokenType.COMMENT) {
                appendComment(output, token.text(), indent);
                continue;
            }
            if (token.type() == SqlTokenType.PUNCTUATION && token.text().equals("(")) {
                boolean nestedQuery = startsNestedQuery(tokens, index + 1);
                if (nestedQuery) {
                    appendSpace(output);
                }
                appendText(output, "(");
                parentheses.push(nestedQuery);
                if (nestedQuery) {
                    indent++;
                    newline(output, indent);
                }
                continue;
            }
            if (token.type() == SqlTokenType.PUNCTUATION && token.text().equals(")")) {
                boolean nestedQuery = parentheses.isEmpty() || parentheses.pop();
                if (nestedQuery) {
                    indent = Math.max(0, indent - 1);
                    newline(output, indent);
                }
                appendText(output, ")");
                continue;
            }
            if (token.type() == SqlTokenType.PUNCTUATION && token.text().equals(",")) {
                appendText(output, ",");
                if (indent > 0 || parentheses.isEmpty() || parentheses.peek()) {
                    newline(output, indent + 1);
                } else {
                    appendSpace(output);
                }
                continue;
            }
            if (token.type() == SqlTokenType.PUNCTUATION && token.text().equals(";")) {
                stripTrailingSpace(output);
                appendText(output, ";");
                continue;
            }

            if (token.type() == SqlTokenType.WORD) {
                String word = token.text().toUpperCase(Locale.ROOT);
                if (isClauseStart(tokens, index)) {
                    if (!atLineStart(output) && output.length() > 0) {
                        newline(output, indent);
                    }
                } else if ((word.equals("AND") || word.equals("OR"))
                        && !atLineStart(output)
                        && (parentheses.isEmpty() || parentheses.peek())) {
                    newline(output, indent + 1);
                } else if (caseDepth > 0 && (word.equals("WHEN") || word.equals("THEN")
                        || word.equals("ELSE"))) {
                    newline(output, indent + 1);
                } else if (word.equals("END") && isCaseEnd(tokens, index, caseDepth, blockDepth)) {
                    indent = Math.max(0, indent - 1);
                    caseDepth--;
                    newline(output, indent);
                } else if (word.equals("END") && blockDepth > 0) {
                    indent = Math.max(0, indent - 1);
                    blockDepth--;
                    newline(output, indent);
                } else if (BLOCK_STARTS.contains(word) && output.length() > 0
                        && (word.equals("BEGIN") || word.equals("DECLARE") || blockDepth > 0)) {
                    newline(output, indent);
                }

                if (word.equals("CASE")) {
                    caseDepth++;
                    indent++;
                } else if (startsBlock(tokens, index, blockDepth)) {
                    blockDepth++;
                    indent++;
                }
                appendText(output, KEYWORDS.contains(word) ? word : token.text());
                continue;
            }

            appendToken(output, token);
        }
        stripTrailingSpace(output);
        return output.toString();
    }

    private static boolean isClauseStart(List<SqlToken> tokens, int index) {
        String firstWord = phrase(tokens, index, 1);
        String twoWords = phrase(tokens, index, 2);
        if (CLAUSE_STARTS.contains(firstWord) || twoWords.equals("GROUP BY")
                || twoWords.equals("ORDER BY") || twoWords.equals("PARTITION BY")
                || twoWords.equals("INSERT INTO") || twoWords.equals("DELETE FROM")
                || twoWords.equals("MERGE INTO") || twoWords.equals("CONNECT BY")
                || twoWords.equals("START WITH") || twoWords.equals("ON CONFLICT")) {
            return true;
        }
        SqlToken token = tokens.get(index);
        if (!token.type().equals(SqlTokenType.WORD)) {
            return false;
        }
        String word = token.text().toUpperCase(Locale.ROOT);
        if (word.equals("JOIN")) {
            return true;
        }
        if (Set.of("LEFT", "RIGHT", "FULL", "INNER", "CROSS", "NATURAL").contains(word)) {
            String next = nextWord(tokens, index + 1);
            return next.equals("JOIN") || (next.equals("OUTER") && nextWord(tokens, index + 2).equals("JOIN"));
        }
        return Set.of("CREATE", "ALTER", "DROP", "TRUNCATE", "UPDATE", "WITH").contains(word);
    }

    private static boolean startsNestedQuery(List<SqlToken> tokens, int index) {
        String next = nextWord(tokens, index);
        return Set.of("SELECT", "WITH", "VALUES", "UPDATE", "DELETE").contains(next)
                || nextTokenText(tokens, index).equals("(");
    }

    private static boolean isCaseEnd(List<SqlToken> tokens, int index, int caseDepth, int blockDepth) {
        return caseDepth > 0 && (nextWord(tokens, index + 1).equals("CASE") || blockDepth == 0);
    }

    private static boolean startsBlock(List<SqlToken> tokens, int index, int blockDepth) {
        SqlToken token = tokens.get(index);
        String word = token.text().toUpperCase(Locale.ROOT);
        if (word.equals("BEGIN")) {
            return true;
        }
        if (!Set.of("IF", "LOOP", "WHILE", "REPEAT").contains(word) || blockDepth == 0) {
            return false;
        }
        return !previousWord(tokens, index - 1).equals("END");
    }

    private static String phrase(List<SqlToken> tokens, int index, int maxWords) {
        StringBuilder result = new StringBuilder();
        int count = 0;
        for (int cursor = index; cursor < tokens.size() && count < maxWords; cursor++) {
            SqlToken token = tokens.get(cursor);
            if (token.type() == SqlTokenType.WHITESPACE || token.type() == SqlTokenType.COMMENT) {
                continue;
            }
            if (token.type() != SqlTokenType.WORD) {
                break;
            }
            if (count > 0) {
                result.append(' ');
            }
            result.append(token.text().toUpperCase(Locale.ROOT));
            count++;
        }
        return result.toString();
    }

    private static String nextWord(List<SqlToken> tokens, int index) {
        for (int cursor = index; cursor < tokens.size(); cursor++) {
            SqlToken token = tokens.get(cursor);
            if (token.type() == SqlTokenType.WORD) {
                return token.text().toUpperCase(Locale.ROOT);
            }
            if (token.type() != SqlTokenType.WHITESPACE && token.type() != SqlTokenType.COMMENT) {
                return "";
            }
        }
        return "";
    }

    private static String nextTokenText(List<SqlToken> tokens, int index) {
        for (int cursor = index; cursor < tokens.size(); cursor++) {
            SqlToken token = tokens.get(cursor);
            if (token.type() != SqlTokenType.WHITESPACE && token.type() != SqlTokenType.COMMENT) {
                return token.text();
            }
        }
        return "";
    }

    private static String previousWord(List<SqlToken> tokens, int index) {
        for (int cursor = index; cursor >= 0; cursor--) {
            SqlToken token = tokens.get(cursor);
            if (token.type() == SqlTokenType.WORD) {
                return token.text().toUpperCase(Locale.ROOT);
            }
            if (token.type() != SqlTokenType.WHITESPACE && token.type() != SqlTokenType.COMMENT) {
                return "";
            }
        }
        return "";
    }

    private static void appendComment(StringBuilder output, String comment, int indent) {
        if (output.length() > 0 && !atLineStart(output)) {
            newline(output, indent);
        }
        output.append(comment);
        if (!comment.contains("\n") && !comment.contains("\r")) {
            newline(output, indent);
        }
    }

    private static void appendToken(StringBuilder output, SqlToken token) {
        String text = token.text();
        if (token.type() == SqlTokenType.STRING && hasLiteralPrefix(output)) {
            stripTrailingSpace(output);
            output.append(text);
            return;
        }
        if (token.type() == SqlTokenType.PUNCTUATION && isOperator(text)) {
            if (!text.equals("::")) {
                appendSpace(output);
            }
            output.append(text);
            if (!text.equals("::")) {
                appendSpace(output);
            }
            return;
        }
        if (text.equals(".") || text.equals("::")) {
            stripTrailingSpace(output);
            output.append(text);
            return;
        }
        appendText(output, text);
    }

    private static boolean hasLiteralPrefix(StringBuilder output) {
        int end = output.length();
        while (end > 0 && Character.isWhitespace(output.charAt(end - 1))) {
            end--;
        }
        if (end > 0 && output.charAt(end - 1) == '&') {
            int prefixEnd = end - 1;
            while (prefixEnd > 0 && Character.isWhitespace(output.charAt(prefixEnd - 1))) {
                prefixEnd--;
            }
            if (prefixEnd > 0 && output.charAt(prefixEnd - 1) == 'U') {
                return true;
            }
        }
        int start = end;
        while (start > 0) {
            char current = output.charAt(start - 1);
            if (!Character.isLetterOrDigit(current) && current != '_' && current != '$') {
                break;
            }
            start--;
        }
        String word = output.substring(start).toUpperCase(Locale.ROOT);
        return Set.of("N", "E", "X", "B", "D", "T").contains(word)
                || (word.length() > 1 && word.charAt(0) == '_');
    }

    private static void appendText(StringBuilder output, String text) {
        if (needsSpace(output, text)) {
            appendSpace(output);
        }
        output.append(text);
    }

    private static boolean needsSpace(StringBuilder output, String text) {
        if (output.length() == 0 || Character.isWhitespace(output.charAt(output.length() - 1))) {
            return false;
        }
        char last = output.charAt(output.length() - 1);
        char first = text.charAt(0);
        if (text.equals("(") || text.equals(",") || text.equals(";") || text.equals(")")) {
            return false;
        }
        return last != '(' && last != '.' && first != ')' && first != ',' && first != ';';
    }

    private static boolean isOperator(String text) {
        return Set.of("+", "-", "*", "/", "%", "=", "<>", "!=", "<", ">", "<=", ">=", "=>", ":=", "||").contains(text);
    }

    private static void newline(StringBuilder output, int indent) {
        stripTrailingSpace(output);
        if (output.length() > 0 && output.charAt(output.length() - 1) != '\n') {
            output.append('\n');
        }
        output.append(" ".repeat(Math.max(0, indent * 4)));
    }

    private static void appendSpace(StringBuilder output) {
        if (output.length() > 0 && !Character.isWhitespace(output.charAt(output.length() - 1))) {
            output.append(' ');
        }
    }

    private static void stripTrailingSpace(StringBuilder output) {
        while (output.length() > 0 && (output.charAt(output.length() - 1) == ' '
                || output.charAt(output.length() - 1) == '\t')) {
            output.setLength(output.length() - 1);
        }
    }

    private static boolean atLineStart(StringBuilder output) {
        int index = output.length() - 1;
        while (index >= 0) {
            char current = output.charAt(index);
            if (current == '\n' || current == '\r') {
                return true;
            }
            if (!Character.isWhitespace(current)) {
                return false;
            }
            index--;
        }
        return true;
    }
}
