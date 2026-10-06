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
 * @since 2026/10/6
 */
public class LexicalSqlSplitter implements SqlSplitter {

    private final SqlLexicalProfile profile;

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

    private static void addStatement(List<String> statements, String sql, int start, int end) {
        if (start >= end) {
            return;
        }
        String statement = sql.substring(start, end).strip();
        if (!statement.isEmpty()) {
            statements.add(statement);
        }
    }

    private static int skipLine(List<SqlToken> tokens, int index, int nextLineStart) {
        int result = index + 1;
        while (result < tokens.size() && tokens.get(result).start() < nextLineStart) {
            result++;
        }
        return result;
    }

    private static int skipDelimiter(List<SqlToken> tokens, int index, int position) {
        int result = index;
        while (result < tokens.size() && tokens.get(result).start() < position) {
            result++;
        }
        return result;
    }

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

    private static int lineStart(String sql, int position) {
        int result = Math.max(sql.lastIndexOf('\n', position - 1), sql.lastIndexOf('\r', position - 1));
        return result < 0 ? 0 : result + 1;
    }

    private static int lineEnd(String sql, int position) {
        int result = position;
        while (result < sql.length() && sql.charAt(result) != '\n' && sql.charAt(result) != '\r') {
            result++;
        }
        return result;
    }

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

    private static boolean isBlank(String value) {
        return value.isBlank();
    }

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

    private static boolean isGoLine(String sql, int position) {
        String line = sql.substring(position, lineEnd(sql, position)).strip();
        return line.matches("(?i)GO\\s*(--.*)?")
                || line.matches("(?i)GO\\s+\\d+\\s*(--.*)?");
    }

    private static boolean isSlashLine(String sql, int position) {
        String line = sql.substring(position, lineEnd(sql, position)).strip();
        return line.equals("/") || line.matches("/\\s*(--.*)?");
    }

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

    private record DelimiterDirective(String delimiter, int nextLineStart) {
    }

    private static final class BlockTracker {

        private final SqlLexicalProfile profile;
        private final Deque<String> constructs = new ArrayDeque<>();
        private boolean createCandidate;
        private boolean routineMode;
        private boolean routineEnded;
        private int statementWordCount;

        private BlockTracker(SqlLexicalProfile profile) {
            this.profile = profile;
        }

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

        private boolean isStatementStart() {
            return statementWordCount <= 2 || constructs.isEmpty();
        }

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

        private boolean isProtected() {
            if (routineMode && !routineEnded) {
                return true;
            }
            return !constructs.isEmpty();
        }

        private boolean isRoutineStatement() {
            return routineMode;
        }

        private void reset() {
            constructs.clear();
            createCandidate = false;
            routineMode = false;
            routineEnded = false;
            statementWordCount = 0;
        }

        private static boolean isRoutineObject(String word) {
            return switch (word) {
                case "PROCEDURE", "FUNCTION", "TRIGGER", "EVENT", "PACKAGE", "TYPE" -> true;
                default -> false;
            };
        }
    }
}
