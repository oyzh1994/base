package cn.oyzh.common.db;

/**
 * SQL方言基础实现
 *
 * @author oyzh
 * @since 2026-10-06
 */
public abstract class AbstractSqlDialect implements SqlDialect {

    /** 数据库类型 */
    private final SqlDatabase database;
    /** 词法分析配置 */
    private final SqlLexicalProfile profile;
    /** SQL拆分器 */
    private final SqlSplitter splitter;
    /** SQL美化器 */
    private final SqlFormatter formatter;

    /**
     * 构造SQL方言基础实例
     *
     * @param database 数据库类型
     * @param profile  词法分析配置
     */
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
