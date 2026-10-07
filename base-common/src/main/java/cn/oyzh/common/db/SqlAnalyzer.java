package cn.oyzh.common.db;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL语句分析工具
 *
 * @author oyzh
 * @since 2026-10-06
 */
public final class SqlAnalyzer {

    /**
     * 私有构造，禁止实例化
     */
    private SqlAnalyzer() {
    }

    /**
     * 判断是否为查询语句（ANSI方言）
     *
     * @param sql SQL脚本
     * @return 结果
     */
    public static boolean isQuery(String sql) {
        return isQuery(sql, SqlDatabase.ANSI);
    }

    /**
     * 判断是否为查询语句
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return 结果
     */
    public static boolean isQuery(String sql, SqlDatabase database) {
        String statement = singleStatement(sql, database);
        return statement != null && isQueryStatement(statement, profile(database));
    }

    /**
     * 判断是否为查询语句
     *
     * @param sql      SQL脚本
     * @param database 数据库名称
     * @return 结果
     */
    public static boolean isQuery(String sql, String database) {
        return isQuery(sql, SqlDialects.parse(database));
    }

    /**
     * 判断是否为查询全部字段的SQL（ANSI方言）
     *
     * @param sql SQL脚本
     * @return 结果
     */
    public static boolean isAllFieldsQuery(String sql) {
        return isAllFieldsQuery(sql, SqlDatabase.ANSI);
    }

    /**
     * 判断是否为查询全部字段的SQL
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return 结果
     */
    public static boolean isAllFieldsQuery(String sql, SqlDatabase database) {
        String statement = singleStatement(sql, database);
        return statement != null
                && isQueryStatement(statement, profile(database))
                && isAllFieldsStatement(statement, profile(database));
    }

    /**
     * 判断是否为查询全部字段的SQL
     *
     * @param sql      SQL脚本
     * @param database 数据库名称
     * @return 结果
     */
    public static boolean isAllFieldsQuery(String sql, String database) {
        return isAllFieldsQuery(sql, SqlDialects.parse(database));
    }

    /**
     * 去除SQL中的注释（ANSI方言）
     *
     * @param sql SQL脚本
     * @return 去除注释后的SQL
     */
    public static String removeComments(String sql) {
        return removeComments(sql, SqlDatabase.ANSI);
    }

    /**
     * 去除SQL中的注释，注释被替换为单个空格
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return 去除注释后的SQL
     */
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

    /**
     * 去除SQL中的注释
     *
     * @param sql      SQL脚本
     * @param database 数据库名称
     * @return 去除注释后的SQL
     */
    public static String removeComments(String sql, String database) {
        return removeComments(sql, SqlDialects.parse(database));
    }

    /**
     * 压缩SQL（ANSI方言）
     *
     * @param sql SQL脚本
     * @return 压缩后的SQL
     */
    public static String compress(String sql) {
        return SqlCompressor.compress(sql);
    }

    /**
     * 压缩SQL
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return 压缩后的SQL
     */
    public static String compress(String sql, SqlDatabase database) {
        return SqlCompressor.compress(sql, database);
    }

    /**
     * 压缩SQL
     *
     * @param sql      SQL脚本
     * @param database 数据库名称
     * @return 压缩后的SQL
     */
    public static String compress(String sql, String database) {
        return SqlCompressor.compress(sql, database);
    }

    /**
     * 压缩SQL（compress的别名，ANSI方言）
     *
     * @param sql SQL脚本
     * @return 压缩后的SQL
     */
    public static String compressSql(String sql) {
        return SqlCompressor.compressSql(sql);
    }

    /**
     * 压缩SQL（compress的别名）
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return 压缩后的SQL
     */
    public static String compressSql(String sql, SqlDatabase database) {
        return SqlCompressor.compressSql(sql, database);
    }

    /**
     * 压缩SQL（compress的别名）
     *
     * @param sql      SQL脚本
     * @param database 数据库名称
     * @return 压缩后的SQL
     */
    public static String compressSql(String sql, String database) {
        return SqlCompressor.compressSql(sql, database);
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
     * 获取SQL中的唯一一条语句，语句数量不为1时返回null
     *
     * @param sql      SQL脚本
     * @param database 数据库类型
     * @return 唯一的一条SQL语句，或null
     */
    public static String singleStatement(String sql, SqlDatabase database) {
        List<String> statements = SqlDialects.get(database).split(sql);
        return statements.size() == 1 ? statements.getFirst() : null;
    }

    /**
     * 判断单条语句是否为查询语句
     *
     * @param sql     SQL语句
     * @param profile 词法分析配置
     * @return 结果
     */
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

    /**
     * 判断WITH语句是否为查询语句
     *
     * @param tokens 词法单元列表
     * @param start  WITH关键字的位置
     * @return 结果
     */
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

    /**
     * 判断是否存在顶层（非括号内）的INTO关键字，用于识别SELECT ... INTO写入语句
     *
     * @param tokens 词法单元列表
     * @param start  起始位置
     * @return 是否存在
     */
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

    /**
     * 判断单条语句的查询字段是否全部为星号（如*、t.*、schema.t.*）
     *
     * @param sql     SQL语句
     * @param profile 词法分析配置
     * @return 结果
     */
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

    /**
     * 查找查询语句中SELECT关键字的位置
     *
     * @param tokens 词法单元列表
     * @return SELECT关键字的位置，未找到返回-1
     */
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

    /**
     * 跳过SELECT后的修饰关键字（DISTINCT、ALL、TOP等）
     *
     * @param tokens 词法单元列表
     * @param start  起始位置
     * @return 查询字段的起始位置，无法确定时返回-1
     */
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

    /**
     * 跳过TOP表达式（TOP n、TOP (n)、TOP n PERCENT）
     *
     * @param tokens 词法单元列表
     * @param start  起始位置
     * @return 跳过后的位置
     */
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

    /**
     * 查找顶层（非括号内）的FROM关键字位置
     *
     * @param tokens 词法单元列表
     * @param start  起始位置
     * @return FROM关键字的位置，未找到返回-1
     */
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

    /**
     * 判断查询字段列表中每一项是否均为全部字段（星号）
     *
     * @param tokens 词法单元列表
     * @param start  查询字段起始位置
     * @param end    查询字段结束位置
     * @return 结果
     */
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

    /**
     * 判断单个查询字段项是否为全部字段（*或t.*或schema.t.*）
     *
     * @param tokens 单个查询字段的词法单元列表
     * @return 结果
     */
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

    /**
     * 查找下一个非空白、非注释的词法单元位置
     *
     * @param tokens 词法单元列表
     * @param start  起始位置
     * @return 词法单元位置，未找到返回-1
     */
    private static int nextSignificant(List<SqlToken> tokens, int start) {
        for (int index = Math.max(0, start); index < tokens.size(); index++) {
            SqlToken token = tokens.get(index);
            if (token.type() != SqlTokenType.WHITESPACE && token.type() != SqlTokenType.COMMENT) {
                return index;
            }
        }
        return -1;
    }

    /**
     * 判断词法单元是否为指定的单词（忽略大小写）
     *
     * @param token 词法单元
     * @param word  单词
     * @return 结果
     */
    private static boolean isWord(SqlToken token, String word) {
        return token.type() == SqlTokenType.WORD
                && token.text().equalsIgnoreCase(word);
    }

    /**
     * 判断词法单元是否为指定的标点符号
     *
     * @param token 词法单元
     * @param text  标点符号
     * @return 结果
     */
    private static boolean isPunctuation(SqlToken token, String text) {
        return token.type() == SqlTokenType.PUNCTUATION && token.text().equals(text);
    }

    /**
     * 判断词法单元是否为名称（单词或标识符）
     *
     * @param token 词法单元
     * @return 结果
     */
    private static boolean isName(SqlToken token) {
        return token.type() == SqlTokenType.WORD || token.type() == SqlTokenType.IDENTIFIER;
    }
}
