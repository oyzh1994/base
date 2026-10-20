package cn.oyzh.store.file;

import cn.oyzh.common.file.LineFileWriter;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.common.util.IOUtil;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * txt类型文件写入器
 *
 * <p>支持在每个字段前追加前缀，供redis/zk导出使用。</p>
 *
 * @author oyzh
 * @since 2024-11-28
 */
public class TxtTypeFileWriter extends TypeFileWriter<FileRecord> {

    /**
     * 字段列表
     */
    private FileColumns columns;

    /**
     * 导出配置
     */
    private FileWriteConfig config;

    /**
     * 文件写入器
     */
    private LineFileWriter writer;

    /**
     * 构造txt类型文件写入器
     *
     * @param config  导出配置
     * @param columns 字段列表
     * @throws FileNotFoundException 异常
     */
    public TxtTypeFileWriter(FileWriteConfig config, FileColumns columns) throws FileNotFoundException {
        this.columns = columns;
        this.config = config;
        this.writer = LineFileWriter.create(config.filePath(), config.charset());
    }

    @Override
    public void writeRecord(FileRecord record) throws Exception {
        Object[] values = new Object[record.size()];
        for (Map.Entry<Integer, Object> entry : record.entrySet()) {
            int index = entry.getKey();
            Object val = this.parameterized(entry.getValue());
            values[index] = val;
        }
        this.writer.write(this.formatLine(values, this.config.prefix(), " ", null, "\n"));
    }

    /**
     * 将对象数组格式化为一行文本
     *
     * @param objects         对象数组
     * @param prefix          前缀
     * @param fieldSeparator  字段分割符号
     * @param txtIdentifier   文本识别符号
     * @param recordSeparator 记录分割符号
     * @return 格式化后的文本行
     */
    protected String formatLine(Object[] objects, String prefix, String fieldSeparator, String txtIdentifier, String recordSeparator) {
        return this.formatLine(CollectionUtil.of(objects), prefix, fieldSeparator, txtIdentifier, recordSeparator);
    }

    /**
     * 将列表格式化为一行文本
     *
     * @param list            对象列表
     * @param prefix          前缀
     * @param fieldSeparator  字段分割符号
     * @param txtIdentifier   文本识别符号
     * @param recordSeparator 记录分割符号
     * @return 格式化后的文本行
     */
    protected String formatLine(List<?> list, String prefix, String fieldSeparator, String txtIdentifier, String recordSeparator) {
        StringBuilder sb = new StringBuilder();
        for (Object val : list) {
            if (prefix != null) {
                sb.append(prefix);
                if (fieldSeparator != null) {
                    sb.append(fieldSeparator);
                }
            }
            sb.append(val);
            sb.append(recordSeparator);
        }
        return sb.toString();
    }

    @Override
    public Object parameterized(Object value) {
        if (value == null) {
            return "\"\"";
        }
        return super.parameterized(value);
    }

    @Override
    public void close() {
        try {
            IOUtil.close(this.writer);
            this.writer = null;
            this.config = null;
            this.columns.clear();
            this.columns = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
