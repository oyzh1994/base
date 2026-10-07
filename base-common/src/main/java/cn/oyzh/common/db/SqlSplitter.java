package cn.oyzh.common.db;

import java.util.List;

/**
 * SQL拆分器
 *
 * @author oyzh
 * @since 2026-10-06
 */
public interface SqlSplitter {

    /**
     * 拆分SQL脚本
     *
     * @param sql SQL脚本
     * @return SQL语句列表
     */
    List<String> split(String sql);
}
