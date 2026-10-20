package cn.oyzh.store.file;

import cn.oyzh.common.file.LineFileWriter;
import cn.oyzh.common.util.IOUtil;
import cn.oyzh.common.util.TextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * csv类型文件写入器
 *
 * <p>基于Apache Commons CSV实现，字段分割符号默认为逗号，可通过构造参数覆盖。</p>
 *
 * @author oyzh
 * @since 2024-11-28
 */
public class CsvTypeFileWriter extends TypeFileWriter<FileRecord> {

    /**
     * csv分割符号
     */
    private static final String CSV_DELIMITER = ",";

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
    private CsvFileWriter writer;
//    private LineFileWriter writer;

    /**
     * 构造csv类型文件写入器
     *
     * @param config  导出配置
     * @param columns 字段列表
     * @throws Exception 异常
     */
    public CsvTypeFileWriter(FileWriteConfig config, FileColumns columns) throws Exception {
        this(config, columns, CSV_DELIMITER);
    }

    /**
     * 构造定界文本类型文件写入器
     *
     * @param config         导出配置
     * @param columns        字段列表
     * @param fieldSeparator 字段分割符号
     * @throws Exception 异常
     */
    public CsvTypeFileWriter(FileWriteConfig config, FileColumns columns, String fieldSeparator) throws Exception {
        this.columns = columns;
        this.config = config;
//        this.writer = LineFileWriter.create(config.filePath(), config.charset());
        this.writer = new CsvFileWriter(config.filePath(), config, fieldSeparator);
    }

    @Override
    public void writeHeader() throws Exception {
        if (this.config.includeTitle()) {
            List<String> cols = new ArrayList<>();
            List<FileColumn> columnList = this.columns.sortOfPosition();
            for (FileColumn fileColumn : columnList) {
                cols.add(fileColumn.getDesc());
            }
            // 写入列名
//            this.writer.write(this.formatLine(cols, null, ",", this.config.getTxtIdentifier(), "\n"));
            this.writer.write(cols);
        }
    }

    @Override
    public void writeRecord(FileRecord record) throws Exception {
        Object[] values = new Object[record.size()];
        for (Map.Entry<Integer, Object> entry : record.entrySet()) {
            int index = entry.getKey();
            Object val = this.parameterized(entry.getValue());
            values[index] = val;
        }
//        this.writer.write(this.formatLine(values, null, ",", this.config.getTxtIdentifier(), "\n"));
        this.writer.write(values);
    }

    @Override
    public void writeRecords(List<FileRecord> records) throws Exception {
//        if (value == null) {
//            return "";
//        }
//        if (value instanceof Number) {
//            return value;
//        }
//        return TextUtil.escape(value.toString());
        super.writeRecords(records);
        this.writer.flush();
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
