package cn.oyzh.store.file;

import cn.oyzh.common.util.StringUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 文件字段列表
 *
 * @author oyzh
 * @since 2024-11-28
 */
public class FileColumns {

    /**
     * 字段列表
     */
    private final List<FileColumn> columns = new ArrayList<>();

    /**
     * 添加字段
     *
     * @param name 字段名称
     */
    public void addColumn(String name) {
        this.columns.add(new FileColumn(name));
    }

    /**
     * 添加字段
     *
     * @param name 字段名称
     * @param desc 字段描述
     */
    public void addColumn(String name, String desc) {
        this.columns.add(new FileColumn(name, desc));
    }

    /**
     * 添加字段
     *
     * @param name     字段名称
     * @param position 字段位置
     */
    public void addColumn(String name, int position) {
        this.columns.add(new FileColumn(name, position));
    }

    /**
     * 获取字段索引
     *
     * @param columnName 字段名称
     * @return 字段索引，未找到时返回-1
     */
    public int index(String columnName) {
        FileColumn column = this.column(columnName);
        return column == null ? -1 : this.columns.indexOf(column);
    }

    /**
     * 获取指定位置的字段名称
     *
     * @param index 字段索引
     * @return 字段名称，不存在时返回null
     */
    public String columnName(int index) {
        FileColumn column = this.columns.get(index);
        return column == null ? null : column.getName();
    }

    /**
     * 按名称获取字段
     *
     * @param columnName 字段名称
     * @return 字段，未找到时返回null
     */
    public FileColumn column(String columnName) {
        for (FileColumn column : columns) {
            if (StringUtil.equals(columnName, column.getName())) {
                return column;
            }
        }
        return null;
    }

    /**
     * 按位置排序字段
     *
     * @return 排序后的字段列表
     */
    public List<FileColumn> sortOfPosition() {
        this.columns.sort(Comparator.comparingInt(FileColumn::getPosition));
        return this.columns;
    }

    /**
     * 清空字段列表
     */
    public void clear() {
        this.columns.clear();
    }
}
