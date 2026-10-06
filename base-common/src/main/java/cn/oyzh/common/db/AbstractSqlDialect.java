package cn.oyzh.common.db;

/**
 * SQL方言基础实现
 *
 * @author oyzh
 * @since 2026/10/6
 */
public abstract class AbstractSqlDialect implements SqlDialect {

    private final SqlDatabase database;
    private final SqlLexicalProfile profile;
    private final SqlSplitter splitter;
    private final SqlFormatter formatter;

    protected AbstractSqlDialect(SqlDatabase database, SqlLexicalProfile profile) {
        this.database = database;
        this.profile = profile;
        this.splitter = new LexicalSqlSplitter(profile);
        this.formatter = new LexicalSqlFormatter(profile);
    }

    @Override
    public SqlDatabase getDatabase() {
        return database;
    }

    @Override
    public SqlSplitter getSplitter() {
        return splitter;
    }

    @Override
    public SqlFormatter getFormatter() {
        return formatter;
    }

    @Override
    public SqlLexicalProfile getLexicalProfile() {
        return profile;
    }
}
