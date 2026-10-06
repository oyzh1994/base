package cn.oyzh.common.db;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL方言
 *
 * @author oyzh
 * @since 2026/10/6
 */
public interface SqlDialect {

    /**
     * 获取数据库类型
     *
     * @return 数据库类型
     */
    SqlDatabase getDatabase();

    /**
     * 获取SQL拆分器
     *
     * @return SQL拆分器
     */
    SqlSplitter getSplitter();

    /**
     * 获取SQL美化器
     *
     * @return SQL美化器
     */
    SqlFormatter getFormatter();

    /**
     * 获取词法分析配置
     *
     * @return 词法分析配置
     */
    default SqlLexicalProfile getLexicalProfile() {
        return SqlLexicalProfile.ANSI;
    }

    /**
     * 拆分SQL脚本
     *
     * @param sql SQL脚本
     * @return SQL语句列表
     */
    default List<String> split(String sql) {
        return this.getSplitter().split(sql);
    }

    /**
     * 美化SQL脚本
     *
     * @param sql SQL脚本
     * @return 美化后的SQL脚本
     */
    default String format(String sql) {
        List<String> formattedStatements = new ArrayList<>();
        for (String statement : this.split(sql)) {
            String formatted = this.getFormatter().format(statement);
            if (!formatted.isBlank()) {
                formattedStatements.add(formatted);
            }
        }
        String separator = this.getStatementSeparator();
        StringBuilder result = new StringBuilder();
        String previous = "";
        for (String statement : formattedStatements) {
            if (result.length() > 0) {
                result.append(previous.endsWith(";") && separator.startsWith(";")
                        ? separator.substring(1)
                        : separator);
            }
            result.append(statement);
            previous = statement;
        }
        return result.toString();
    }

    /**
     * 获取脚本语句分隔符
     *
     * @return 脚本语句分隔符
     */
    default String getStatementSeparator() {
        return ";\n\n";
    }
}
