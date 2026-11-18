package cn.oyzh.common.db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Oracle SQL方言
 *
 * @author oyzh
 * @since 2026-10-06
 */
public class OracleSqlDialect extends AbstractSqlDialect {

    /** Oracle 12c 及以上版本支持的标准 OFFSET/FETCH 分页。 */
    private static final Pattern RELEASE_VERSION = Pattern.compile(
            "(?:RELEASE\\s+|ORACLE\\s+DATABASE\\s+)(\\d+)(?:\\D|$)",
            Pattern.CASE_INSENSITIVE);

    /** JDBC也有可能只返回如 11.2.0.4.0 或 19c 的版本号。 */
    private static final Pattern BARE_VERSION = Pattern.compile("^\\s*(?:V)?(\\d+)(?:\\.|c|$)",
            Pattern.CASE_INSENSITIVE);

    /** Oracle 11g 兼容分页使用的伪列别名。 */
    public static final String PAGE_ROWNUM_ALIAS = "EASYSHELL_ROWNUM__";

    /**
     * 构造Oracle SQL方言实例
     */
    public OracleSqlDialect() {
        super(SqlDatabase.ORACLE, SqlLexicalProfile.ORACLE);
    }

    /**
     * 判断Oracle版本是否支持 OFFSET/FETCH 分页。
     *
     * @param productVersion JDBC返回的数据库产品版本
     * @return Oracle 12c及以上返回true；版本无法识别时返回false，以兼容11g
     */
    public boolean supportsOffsetFetch(String productVersion) {
        if (productVersion == null || productVersion.isBlank()) {
            return false;
        }
        Matcher matcher = RELEASE_VERSION.matcher(productVersion);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1)) >= 12;
        }
        matcher = BARE_VERSION.matcher(productVersion);
        return matcher.find() && Integer.parseInt(matcher.group(1)) >= 12;
    }

    /**
     * 生成Oracle分页SQL。
     *
     * @param query             原始查询
     * @param offset            跳过的行数
     * @param limit             返回的行数
     * @param supportsOffsetFetch 是否支持OFFSET/FETCH
     * @return 分页SQL
     */
    public String paginate(String query, long offset, long limit, boolean supportsOffsetFetch) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("query must not be blank");
        }
        if (offset < 0) {
            throw new IllegalArgumentException("offset must not be negative");
        }
        if (limit <= 0) {
            throw new IllegalArgumentException("limit must be positive");
        }
        String normalized = query.strip();
        while (normalized.endsWith(";")) {
            normalized = normalized.substring(0, normalized.length() - 1).stripTrailing();
        }
        if (supportsOffsetFetch) {
            return normalized + " OFFSET " + offset + " ROWS FETCH NEXT " + limit + " ROWS ONLY";
        }
        long upperBound = offset + limit;
        return """
                SELECT PAGE_SOURCE__.* FROM (
                    SELECT SOURCE_DATA__.*, ROWNUM AS %s FROM (
                        %s
                    ) SOURCE_DATA__
                    WHERE ROWNUM <= %d
                ) PAGE_SOURCE__
                WHERE %s > %d
                """.formatted(PAGE_ROWNUM_ALIAS, normalized, upperBound, PAGE_ROWNUM_ALIAS, offset);
    }
}
