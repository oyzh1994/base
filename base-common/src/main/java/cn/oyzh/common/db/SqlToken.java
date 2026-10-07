package cn.oyzh.common.db;

/**
 * SQL词法单元
 *
 * @param type  词法单元类型
 * @param text  原始文本
 * @param start 起始位置
 * @param end   结束位置
 * @author oyzh
 * @since 2026/10/6
 */
public record SqlToken(SqlTokenType type, String text, int start, int end) {

    /**
     * 判断当前词法单元是否为指定的单词（忽略大小写）
     *
     * @param word 单词
     * @return 是否匹配
     */
    public boolean isWord(String word) {
        return this.type == SqlTokenType.WORD && this.text.equalsIgnoreCase(word);
    }
}
