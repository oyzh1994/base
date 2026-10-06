package cn.oyzh.common.db;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL语句分析工具
 *
 * @author oyzh
 * @since 2026/10/6
 */
public final class SqlAnalyzer {

    private SqlAnalyzer() {
    }

    public static boolean isQuery(String sql) {
        return isQuery(sql, SqlDatabase.ANSI);
    }

    public static boolean isQuery(String sql, SqlDatabase database) {
        String statement = singleStatement(sql, database);
        return statement != null && isQueryStatement(statement, profile(database));
    }

    public static boolean isQuery(String sql, String database) {
        return isQuery(sql, SqlDialects.parse(database));
    }

    public static boolean isAllFieldsQuery(String sql) {
        return isAllFieldsQuery(sql, SqlDatabase.ANSI);
    }

    public static boolean isAllFieldsQuery(String sql, SqlDatabase database) {
        String statement = singleStatement(sql, database);
        return statement != null
                && isQueryStatement(statement, profile(database))
                && isAllFieldsStatement(statement, profile(database));
    }

    public static boolean isAllFieldsQuery(String sql, String database) {
        return isAllFieldsQuery(sql, SqlDialects.parse(database));
    }

    public static boolean isAllFieldQuery(String sql) {
        return isAllFieldsQuery(sql);
    }

    public static boolean isAllFieldQuery(String sql, SqlDatabase database) {
        return isAllFieldsQuery(sql, database);
    }

    public static boolean isAllFieldQuery(String sql, String database) {
        return isAllFieldsQuery(sql, database);
    }

    public static boolean isSelectAll(String sql) {
        return isAllFieldsQuery(sql);
    }

    public static boolean isSelectAll(String sql, SqlDatabase database) {
        return isAllFieldsQuery(sql, database);
    }

    public static boolean isSelectAll(String sql, String database) {
        return isAllFieldsQuery(sql, database);
    }

    public static String removeComments(String sql) {
        return removeComments(sql, SqlDatabase.ANSI);
    }

    public static String removeComments(String sql, SqlDatabase database) {
        if (sql == null || sql.isEmpty()) {
            return "";
        }
        List<SqlToken> tokens = SqlLexer.tokenize(sql, profile(database));
        StringBuilder result = new StringBuilder(sql.length());
        for (SqlToken token : tokens) {
            if (token.type() == SqlTokenType.COMMENT) {
                result.append(' ');
            } else {
                result.append(token.text());
            }
        }
        return result.toString().strip();
    }

    public static String removeComments(String sql, String database) {
        return removeComments(sql, SqlDialects.parse(database));
    }

    public static String compress(String sql) {
        return SqlCompressor.compress(sql);
    }

    public static String compress(String sql, SqlDatabase database) {
        return SqlCompressor.compress(sql, database);
    }

    public static String compress(String sql, String database) {
        return SqlCompressor.compress(sql, database);
    }

    public static String compressSql(String sql) {
        return SqlCompressor.compressSql(sql);
    }

    public static String compressSql(String sql, SqlDatabase database) {
        return SqlCompressor.compressSql(sql, database);
    }

    public static String compressSql(String sql, String database) {
        return SqlCompressor.compressSql(sql, database);
    }

    private static SqlLexicalProfile profile(SqlDatabase database) {
        return SqlDialects.get(database).getLexicalProfile();
    }

    public static String singleStatement(String sql, SqlDatabase database) {
        List<String> statements = SqlDialects.get(database).split(sql);
        return statements.size() == 1 ? statements.getFirst() : null;
    }

    private static boolean isQueryStatement(String sql, SqlLexicalProfile profile) {
        List<SqlToken> tokens = SqlLexer.tokenize(sql, profile);
        int first = nextSignificant(tokens, 0);
        if (first < 0) {
            return false;
        }
        if (isWord(tokens.get(first), "SELECT")) {
            return !hasTopLevelInto(tokens, first);
        }
        if (isWord(tokens.get(first), "WITH")) {
            return isWithQuery(tokens, first);
        }
        return isWord(tokens.get(first), "SHOW")
                || isWord(tokens.get(first), "DESCRIBE")
                || isWord(tokens.get(first), "DESC")
                || isWord(tokens.get(first), "EXPLAIN")
                || isWord(tokens.get(first), "VALUES")
                || isWord(tokens.get(first), "TABLE")
                || isWord(tokens.get(first), "PRAGMA");
    }

    private static boolean isWithQuery(List<SqlToken> tokens, int start) {
        int depth = 0;
        for (int index = start + 1; index < tokens.size(); index++) {
            SqlToken token = tokens.get(index);
            if (isPunctuation(token, "(")) {
                depth++;
            } else if (isPunctuation(token, ")") && depth > 0) {
                depth--;
            } else if (depth == 0 && isWord(token, "SELECT")) {
                return !hasTopLevelInto(tokens, index);
            } else if (depth == 0 && (isWord(token, "INSERT")
                    || isWord(token, "UPDATE")
                    || isWord(token, "DELETE")
                    || isWord(token, "MERGE"))) {
                return false;
            }
        }
        return false;
    }

    private static boolean hasTopLevelInto(List<SqlToken> tokens, int start) {
        int depth = 0;
        for (int index = start + 1; index < tokens.size(); index++) {
            SqlToken token = tokens.get(index);
            if (isPunctuation(token, "(")) {
                depth++;
            } else if (isPunctuation(token, ")") && depth > 0) {
                depth--;
            } else if (depth == 0 && isWord(token, "INTO")) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAllFieldsStatement(String sql, SqlLexicalProfile profile) {
        List<SqlToken> tokens = SqlLexer.tokenize(sql, profile);
        int select = findSelect(tokens);
        if (select < 0 || hasTopLevelInto(tokens, select)) {
            return false;
        }
        int projectionStart = nextSignificant(tokens, select + 1);
        projectionStart = skipSelectModifiers(tokens, projectionStart);
        if (projectionStart < 0) {
            return false;
        }
        int projectionEnd = findTopLevelFrom(tokens, projectionStart);
        if (projectionEnd < 0) {
            projectionEnd = tokens.size();
        }
        return isAllFieldProjection(tokens, projectionStart, projectionEnd);
    }

    private static int findSelect(List<SqlToken> tokens) {
        int first = nextSignificant(tokens, 0);
        if (first < 0) {
            return -1;
        }
        if (isWord(tokens.get(first), "SELECT")) {
            return first;
        }
        if (!isWord(tokens.get(first), "WITH")) {
            return -1;
        }
        int depth = 0;
        for (int index = first + 1; index < tokens.size(); index++) {
            SqlToken token = tokens.get(index);
            if (isPunctuation(token, "(")) {
                depth++;
            } else if (isPunctuation(token, ")") && depth > 0) {
                depth--;
            } else if (depth == 0 && isWord(token, "SELECT")) {
                return index;
            }
        }
        return -1;
    }

    private static int skipSelectModifiers(List<SqlToken> tokens, int start) {
        int index = start;
        while (index >= 0) {
            SqlToken token = tokens.get(index);
            if (isWord(token, "DISTINCT") || isWord(token, "ALL")
                    || isWord(token, "UNIQUE") || isWord(token, "SQL_CALC_FOUND_ROWS")) {
                index = nextSignificant(tokens, index + 1);
                continue;
            }
            if (isWord(token, "TOP")) {
                index = skipTopExpression(tokens, nextSignificant(tokens, index + 1));
                continue;
            }
            break;
        }
        return index;
    }

    private static int skipTopExpression(List<SqlToken> tokens, int start) {
        if (start < 0) {
            return -1;
        }
        if (isPunctuation(tokens.get(start), "(")) {
            int depth = 0;
            int index = start;
            for (; index < tokens.size(); index++) {
                if (isPunctuation(tokens.get(index), "(")) {
                    depth++;
                } else if (isPunctuation(tokens.get(index), ")")) {
                    depth--;
                    if (depth == 0) {
                        index++;
                        break;
                    }
                }
            }
            return nextSignificant(tokens, index);
        }
        int index = nextSignificant(tokens, start + 1);
        if (index >= 0 && isWord(tokens.get(index), "PERCENT")) {
            index = nextSignificant(tokens, index + 1);
        }
        return index;
    }

    private static int findTopLevelFrom(List<SqlToken> tokens, int start) {
        int depth = 0;
        for (int index = start; index < tokens.size(); index++) {
            SqlToken token = tokens.get(index);
            if (isPunctuation(token, "(")) {
                depth++;
            } else if (isPunctuation(token, ")") && depth > 0) {
                depth--;
            } else if (depth == 0 && isWord(token, "FROM")) {
                return index;
            }
        }
        return -1;
    }

    private static boolean isAllFieldProjection(List<SqlToken> tokens, int start, int end) {
        List<List<SqlToken>> items = new ArrayList<>();
        List<SqlToken> current = new ArrayList<>();
        int depth = 0;
        for (int index = start; index < end; index++) {
            SqlToken token = tokens.get(index);
            if (isPunctuation(token, "(")) {
                depth++;
            } else if (isPunctuation(token, ")") && depth > 0) {
                depth--;
            }
            if (depth == 0 && isPunctuation(token, ",")) {
                items.add(current);
                current = new ArrayList<>();
            } else if (token.type() != SqlTokenType.WHITESPACE && token.type() != SqlTokenType.COMMENT) {
                current.add(token);
            }
        }
        items.add(current);
        return !items.isEmpty() && items.stream().allMatch(SqlAnalyzer::isAllFieldItem);
    }

    private static boolean isAllFieldItem(List<SqlToken> tokens) {
        if (tokens.size() == 1) {
            return isPunctuation(tokens.getFirst(), "*");
        }
        if (tokens.size() < 3 || !isPunctuation(tokens.getLast(), "*")) {
            return false;
        }
        int dotBeforeStar = tokens.size() - 2;
        if (!isPunctuation(tokens.get(dotBeforeStar), ".") || !isName(tokens.get(0))) {
            return false;
        }
        int cursor = 0;
        while (cursor < dotBeforeStar - 1) {
            if (!isPunctuation(tokens.get(cursor + 1), ".")
                    || !isName(tokens.get(cursor + 2))) {
                return false;
            }
            cursor += 2;
        }
        return cursor == dotBeforeStar - 1;
    }

    private static int nextSignificant(List<SqlToken> tokens, int start) {
        for (int index = Math.max(0, start); index < tokens.size(); index++) {
            SqlToken token = tokens.get(index);
            if (token.type() != SqlTokenType.WHITESPACE && token.type() != SqlTokenType.COMMENT) {
                return index;
            }
        }
        return -1;
    }

    private static boolean isWord(SqlToken token, String word) {
        return token.type() == SqlTokenType.WORD
                && token.text().equalsIgnoreCase(word);
    }

    private static boolean isPunctuation(SqlToken token, String text) {
        return token.type() == SqlTokenType.PUNCTUATION && token.text().equals(text);
    }

    private static boolean isName(SqlToken token) {
        return token.type() == SqlTokenType.WORD || token.type() == SqlTokenType.IDENTIFIER;
    }
}
