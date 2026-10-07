package cn.oyzh.common.db;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Locale;

/**
 * 基于词法分析的SQL拆分器
 *
 * @author oyzh
 * @since 2026-10-06
 */
public class LexicalSqlSplitter implements SqlSplitter {

    /** 词法分析配置 */
    private final SqlLexicalProfile profile;

    /**
     * 构造方法
     *
     * @param profile 词法分析配置
     */
    public LexicalSqlSplitter(SqlLexicalProfile profile) {
        this.profile = profile;
    }

    @Override
    public List<String> split(String sql) {
        List<String> statements = new ArrayList<>();
        if (sql == null || sql.isBlank()) {
            return statements;
        }
        List<SqlToken> tokens = SqlLexer.tokenize(sql, profile);
        BlockTracker blockTracker = new BlockTracker(profile);
        String delimiter = ";";
        int currentStart = 0;
        int parenthesisDepth = 0;
        int index = 0;
        while (index < tokens.size()) {
            SqlToken token = tokens.get(index);
            int lineStart = lineStart(sql, token.start());
            boolean firstTokenOnLine = isBlank(sql.substring(lineStart, token.start()));
            if (firstTokenOnLine && profile.isDelimiterDirective()
                    && isDelimiterDirective(sql, lineStart)) {
                DelimiterDirective directive = parseDelimiterDirective(sql, lineStart);
                if (directive != null) {
                    addStatement(statements, sql, currentStart, lineStart);
                    delimiter = directive.delimiter();
                    blockTracker.reset();
                    parenthesisDepth = 0;
                    currentStart = directive.nextLineStart();
                    index = skipLine(tokens, index, directive.nextLineStart());
                    continue;
                }
            }
            if (firstTokenOnLine && profile.isGoBatch()
                    && token.isWord("GO")
                    && isGoLine(sql, token.start())) {
                int lineEnd = lineEnd(sql, token.start());
                addStatement(statements, sql, currentStart, lineStart);
                blockTracker.reset();
                parenthesisDepth = 0;
                currentStart = nextLineStart(sql, lineEnd);
                index = skipLine(tokens, index, currentStart);
                continue;
            }
            if (firstTokenOnLine && profile.isSlashTerminator()
                    && token.text().equals("/")
                    && isSlashLine(sql, token.start())) {
                int lineEnd = lineEnd(sql, token.start());
                addStatement(statements, sql, currentStart, lineStart);
                blockTracker.reset();
                parenthesisDepth = 0;
                currentStart = nextLineStart(sql, lineEnd);
                index = skipLine(tokens, index, currentStart);
                continue;
            }

            String nextWord = nextWord(tokens, index);
            blockTracker.accept(token, nextWord);
            if (token.text().equals("(")) {
                parenthesisDepth++;
            } else if (token.text().equals(")") && parenthesisDepth > 0) {
                parenthesisDepth--;
            }

            boolean protectedSemicolon = blockTracker.isProtected()
                    || (profile.isSlashTerminator() && blockTracker.isRoutineStatement());
            boolean delimiterCandidate = token.type() == SqlTokenType.WORD
                    || token.type() == SqlTokenType.PUNCTUATION
                    || token.type() == SqlTokenType.OTHER;
            int delimiterPosition = delimiterCandidate
                    ? delimiterPosition(sql, token, delimiter)
                    : -1;
            if (delimiterPosition >= 0
                    && parenthesisDepth == 0
                    && (!protectedSemicolon || !delimiter.equals(";"))) {
                addStatement(statements, sql, currentStart, delimiterPosition);
                currentStart = delimiterPosition + delimiter.length();
                index = skipDelimiter(tokens, index, currentStart);
                blockTracker.reset();
                parenthesisDepth = 0;
                continue;
            }
            index++;
        }
        addStatement(statements, sql, currentStart, sql.length());
        return statements;
    }

    /**
     * 将指定区间内的文本作为一条语句加入结果，空白内容会被忽略
     *
     * @param statements 语句结果集
     * @param sql        SQL脚本
     * @param start      起始位置
     * @param end        结束位置
     */
    private static void addStatement(List<String> statements, String sql, int start, int end) {
        if (start >= end) {
            return;
        }
        String statement = sql.substring(start, end).strip();
        if (!statement.isEmpty()) {
            statements.add(statement);
        }
    }

    /**
     * 跳过当前行剩余的词法单元
     *
     * @param tokens       词法单元列表
     * @param index        当前词法单元位置
     * @param nextLineStart 下一行的起始位置
     * @return 下一行起始位置对应的词法单元位置
     */
    private static int skipLine(List<SqlToken> tokens, int index, int nextLineStart) {
        int result = index + 1;
        while (result < tokens.size() && tokens.get(result).start() < nextLineStart) {
            result++;
        }
        return result;
    }

    /**
     * 跳过结束符所占的词法单元
     *
     * @param tokens   词法单元列表
     * @param index    当前词法单元位置
     * @param position 结束符之后的位置
     * @return 结束符之后的词法单元位置
     */
    private static int skipDelimiter(List<SqlToken> tokens, int index, int position) {
        int result = index;
        while (result < tokens.size() && tokens.get(result).start() < position) {
            result++;
        }
        return result;
    }

    /**
     * 获取词法单元中结束符所在的位置，可从单元起始处或末尾匹配
     *
     * @param sql       SQL脚本
     * @param token     词法单元
     * @param delimiter 结束符
     * @return 结束符位置，未匹配返回-1
     */
    private static int delimiterPosition(String sql, SqlToken token, String delimiter) {
        if (delimiter.isEmpty()) {
            return -1;
        }
        if (sql.startsWith(delimiter, token.start())) {
            return token.start();
        }
        int suffixStart = token.end() - delimiter.length();
        return suffixStart >= token.start() && sql.startsWith(delimiter, suffixStart)
                ? suffixStart
                : -1;
    }

    /**
     * 获取指定位置所在行的起始位置
     *
     * @param sql      SQL脚本
     * @param position 位置
     * @return 行起始位置
     */
    private static int lineStart(String sql, int position) {
        int result = Math.max(sql.lastIndexOf('\n', position - 1), sql.lastIndexOf('\r', position - 1));
        return result < 0 ? 0 : result + 1;
    }

    /**
     * 获取指定位置所在行的结束位置
     *
     * @param sql      SQL脚本
     * @param position 位置
     * @return 行结束位置
     */
    private static int lineEnd(String sql, int position) {
        int result = position;
        while (result < sql.length() && sql.charAt(result) != '\n' && sql.charAt(result) != '\r') {
            result++;
        }
        return result;
    }

    /**
     * 获取指定行结束位置之后下一行的起始位置，兼容回车换行
     *
     * @param sql     SQL脚本
     * @param lineEnd 行结束位置
     * @return 下一行起始位置
     */
    private static int nextLineStart(String sql, int lineEnd) {
        int result = lineEnd;
        if (result < sql.length() && sql.charAt(result) == '\r') {
            result++;
        }
        if (result < sql.length() && sql.charAt(result) == '\n') {
            result++;
        }
        return result;
    }

    /**
     * 判断字符串是否为空
     *
     * @param value 字符串
     * @return 结果
     */
    private static boolean isBlank(String value) {
        return value.isBlank();
    }

    /**
     * 判断某行是否为DELIMITER指令
     *
     * @param sql       SQL脚本
     * @param lineStart 行起始位置
     * @return 结果
     */
    private static boolean isDelimiterDirective(String sql, int lineStart) {
        int position = lineStart;
        while (position < sql.length() && Character.isWhitespace(sql.charAt(position))
                && sql.charAt(position) != '\n' && sql.charAt(position) != '\r') {
            position++;
        }
        String prefix = "DELIMITER";
        return sql.regionMatches(true, position, prefix, 0, prefix.length())
                && (position + prefix.length() == sql.length()
                || Character.isWhitespace(sql.charAt(position + prefix.length())));
    }

    /**
     * 判断某行是否为GO批处理标记，支持GO、GO n及后跟注释的形式
     *
     * @param sql      SQL脚本
     * @param position 位置
     * @return 结果
     */
    private static boolean isGoLine(String sql, int position) {
        String line = sql.substring(position, lineEnd(sql, position)).strip();
        return line.matches("(?i)GO\\s*(--.*)?")
                || line.matches("(?i)GO\\s+\\d+\\s*(--.*)?");
    }

    /**
     * 判断某行是否为斜杠结束标记
     *
     * @param sql      SQL脚本
     * @param position 位置
     * @return 结果
     */
    private static boolean isSlashLine(String sql, int position) {
        String line = sql.substring(position, lineEnd(sql, position)).strip();
        return line.equals("/") || line.matches("/\\s*(--.*)?");
    }

    /**
     * 解析DELIMITER指令，提取新的结束符及下一行的起始位置
     *
     * @param sql      SQL脚本
     * @param position 位置
     * @return DELIMITER指令信息，解析失败返回null
     */
    private static DelimiterDirective parseDelimiterDirective(String sql, int position) {
        int lineEnd = lineEnd(sql, position);
        int keywordStart = position;
        while (keywordStart < lineEnd && Character.isWhitespace(sql.charAt(keywordStart))) {
            keywordStart++;
        }
        int valueStart = keywordStart + "DELIMITER".length();
        while (valueStart < lineEnd && Character.isWhitespace(sql.charAt(valueStart))) {
            valueStart++;
        }
        int valueEnd = lineEnd;
        while (valueEnd > valueStart && Character.isWhitespace(sql.charAt(valueEnd - 1))) {
            valueEnd--;
        }
        if (valueStart == valueEnd) {
            return null;
        }
        String value = sql.substring(valueStart, valueEnd);
        int comment = value.indexOf("--");
        if (comment >= 0) {
            value = value.substring(0, comment).strip();
        }
        if (value.isEmpty()) {
            return null;
        }
        return new DelimiterDirective(value, nextLineStart(sql, lineEnd));
    }

    /**
     * 获取当前位置之后的第一个单词
     *
     * @param tokens 词法单元列表
     * @param index  当前位置
     * @return 单词文本，未找到返回空字符串
     */
    private static String nextWord(List<SqlToken> tokens, int index) {
        for (int cursor = index + 1; cursor < tokens.size(); cursor++) {
            SqlToken token = tokens.get(cursor);
            if (token.type() == SqlTokenType.WORD) {
                return token.text();
            }
            if (token.type() != SqlTokenType.WHITESPACE && token.type() != SqlTokenType.COMMENT) {
                return "";
            }
        }
        return "";
    }

    /**
     * DELIMITER指令信息
     *
     * @param delimiter     新的结束符
     * @param nextLineStart 指令之后下一行的起始位置
     */
    private record DelimiterDirective(String delimiter, int nextLineStart) {
    }

    /**
     * 块结构追踪器，用于识别存储过程、函数等过程体，避免在过程体内错误地按分号拆分
     */
    private static final class BlockTracker {

        /** 词法分析配置 */
        private final SqlLexicalProfile profile;
        /** 当前嵌套的块结构栈 */
        private final Deque<String> constructs = new ArrayDeque<>();
        /** 是否已出现CREATE或ALTER，用于判断后续是否可能是过程对象 */
        private boolean createCandidate;
        /** 是否处于过程体模式 */
        private boolean routineMode;
        /** 过程体是否已结束 */
        private boolean routineEnded;
        /** 当前语句已遇到的单词数量 */
        private int statementWordCount;

        /**
         * 构造方法
         *
         * @param profile 词法分析配置
         */
        private BlockTracker(SqlLexicalProfile profile) {
            this.profile = profile;
        }

        /**
         * 接收一个词法单元，更新块结构的嵌套状态
         *
         * @param token    词法单元
         * @param nextWord 该词法单元之后的下一个单词
         */
        private void accept(SqlToken token, String nextWord) {
            if (token.type() != SqlTokenType.WORD) {
                return;
            }
            String word = token.text().toUpperCase(Locale.ROOT);
            String next = nextWord.toUpperCase(Locale.ROOT);
            statementWordCount++;
            if (word.equals("CREATE") || word.equals("ALTER")) {
                createCandidate = true;
            } else if (isRoutineObject(word) && createCandidate && statementWordCount <= 20
                    && profile != SqlLexicalProfile.ANSI
                    && profile != SqlLexicalProfile.POSTGRESQL) {
                routineMode = true;
            }

            if (word.equals("BEGIN")) {
                if (!next.equals("TRANSACTION") && !next.equals("TRAN") && !next.equals("WORK")) {
                    routineMode = routineMode || statementWordCount == 1;
                    if (constructs.isEmpty()) {
                        constructs.push(next.equals("TRY") ? "TRY" : "BLOCK");
                    }
                }
            } else if (word.equals("CASE")) {
                constructs.push("CASE");
            } else if (word.equals("IF") && isStatementStart()) {
                constructs.push("IF");
            } else if (word.equals("LOOP") && !constructs.isEmpty()) {
                constructs.push("LOOP");
            } else if ((word.equals("WHILE") || word.equals("REPEAT")) && isStatementStart()) {
                constructs.push(word);
            } else if (word.equals("FOR") && isStatementStart()) {
                constructs.push("FOR");
            } else if (word.equals("END")) {
                popConstruct(next);
                if (constructs.isEmpty() && routineMode) {
                    routineEnded = true;
                }
            }
        }

        /**
         * 判断当前单词是否处于语句起始位置
         *
         * @return 结果
         */
        private boolean isStatementStart() {
            return statementWordCount <= 2 || constructs.isEmpty();
        }

        /**
         * 根据结束关键字弹出一个块结构，必要时回退到匹配的结构
         *
         * @param nextWord END之后的下一个单词
         */
        private void popConstruct(String nextWord) {
            String expected = switch (nextWord) {
                case "IF" -> "IF";
                case "CASE" -> "CASE";
                case "LOOP" -> "LOOP";
                case "WHILE" -> "WHILE";
                case "REPEAT" -> "REPEAT";
                case "FOR" -> "FOR";
                case "TRY" -> "TRY";
                default -> "";
            };
            if (expected.isEmpty()) {
                if (!constructs.isEmpty()) {
                    constructs.pop();
                }
                return;
            }
            if (constructs.contains(expected)) {
                while (!constructs.isEmpty()) {
                    String actual = constructs.pop();
                    if (actual.equals(expected)) {
                        break;
                    }
                }
            }
        }

        /**
         * 判断当前是否处于受保护的块结构内（不应按分号拆分）
         *
         * @return 结果
         */
        private boolean isProtected() {
            if (routineMode && !routineEnded) {
                return true;
            }
            return !constructs.isEmpty();
        }

        /**
         * 判断当前是否处于过程体语句中
         *
         * @return 结果
         */
        private boolean isRoutineStatement() {
            return routineMode;
        }

        /**
         * 重置状态，用于开始一条新语句时
         */
        private void reset() {
            constructs.clear();
            createCandidate = false;
            routineMode = false;
            routineEnded = false;
            statementWordCount = 0;
        }

        /**
         * 判断单词是否为过程对象关键字（存储过程、函数、触发器等）
         *
         * @param word 单词
         * @return 结果
         */
        private static boolean isRoutineObject(String word) {
            return switch (word) {
                case "PROCEDURE", "FUNCTION", "TRIGGER", "EVENT", "PACKAGE", "TYPE" -> true;
                default -> false;
            };
        }
    }
}
