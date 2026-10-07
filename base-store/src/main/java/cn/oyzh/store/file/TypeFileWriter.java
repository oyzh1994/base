package cn.oyzh.store.file;


import java.io.Closeable;
import java.util.List;

/**
 * 文件类型写入器
 *
 * @author oyzh
 * @since 2024-09-04
 */
public abstract class TypeFileWriter implements Closeable {

    /**
     * 初始化
     *
     * @throws Exception 异常
     */
    protected void init() throws Exception {

    }

    /**
     * 参数化
     *
     * @param value 值
     * @return 参数化后的值
     */
    public Object parameterized(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    /**
     * 写入头
     *
     * @throws Exception 异常
     */
    public void writeHeader() throws Exception {

    }

    /**
     * 写入尾
     *
     * @throws Exception 异常
     */
    public void writeTrial() throws Exception {

    }

    /**
     * 写入对象
     *
     * @param record 记录
     * @throws Exception 异常
     */
    public abstract void writeRecord(FileRecord record) throws Exception;

    /**
     * 写入多个对象
     *
     * @param records 记录列表
     * @throws Exception 异常
     */
    public void writeRecords(List<FileRecord> records) throws Exception {
        for (FileRecord object : records) {
            this.writeRecord(object);
        }
    }

    /**
     * 将对象数组格式化为一行文本
     *
     * @param objects         对象数组
     * @param prefix          前缀
     * @param fieldSeparator  字段分隔符号
     * @param txtIdentifier   文本识别符号
     * @param recordSeparator 记录分隔符号
     * @return 格式化后的文本行
     */
    protected String formatLine(Object[] objects, String prefix, Character fieldSeparator, Character txtIdentifier, String recordSeparator) {
        return this.formatLine(List.of(objects), prefix, fieldSeparator, txtIdentifier, recordSeparator);
    }

    /**
     * 将列表格式化为一行文本
     *
     * @param list            对象列表
     * @param prefix          前缀
     * @param fieldSeparator  字段分隔符号
     * @param txtIdentifier   文本识别符号
     * @param recordSeparator 记录分隔符号
     * @return 格式化后的文本行
     */
    protected String formatLine(List<?> list, String prefix, Character fieldSeparator, Character txtIdentifier, String recordSeparator) {
        StringBuilder sb = new StringBuilder();
        for (Object val : list) {
            if (fieldSeparator != null) {
                sb.append(fieldSeparator);
            }
            if (prefix != null) {
                sb.append(prefix);
            }
            if (txtIdentifier != null) {
                sb.append(txtIdentifier);
            }
            sb.append(val);
            if (txtIdentifier != null) {
                sb.append(txtIdentifier);
            }
        }
        sb.append(recordSeparator);
        if (fieldSeparator != null) {
            return sb.substring(1);
        }
        return sb.toString();
    }
}
