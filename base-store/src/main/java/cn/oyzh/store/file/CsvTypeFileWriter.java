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
 * @author oyzh
 * @since 2024-09-04
 */
public class CsvTypeFileWriter extends TypeFileWriter {

    /**
     * 字段列表
     */
    private FileColumns columns;

    /**
     * 导出配置
     */
    private FileWriteConfig config;

    /**
     * 文件读取器
     */
    private LineFileWriter writer;

    public CsvTypeFileWriter(FileWriteConfig config, FileColumns columns) throws Exception {
        this.columns = columns;
        this.config = config;
        this.writer = LineFileWriter.create(config.filePath(), config.charset());
        this.init();
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
            this.writer.write(this.formatLine(cols, null, ',', this.config.txtIdentifier(), "\n"));
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
        this.writer.write(this.formatLine(values, null, ',', this.config.txtIdentifier(), "\n"));
    }

    @Override
    public Object parameterized(Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof Number) {
            return value;
        }
        return TextUtil.escape(value.toString());
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
