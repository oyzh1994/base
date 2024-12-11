package cn.oyzh.store.file;

import cn.oyzh.common.file.LineFileWriter;
import cn.oyzh.common.json.JSONUtil;
import cn.oyzh.common.util.IOUtil;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * @author oyzh
 * @since 2024-09-04
 */
public class JsonTypeFileWriter extends TypeFileWriter {

    /**
     * 字段列表
     */
    private FileColumns columns;

    /**
     * 文件读取器
     */
    private LineFileWriter writer;

    /**
     * 是否首次写入
     */
    private boolean firstWrite = true;

    /**
     * 导出配置
     */
    private FileWriteConfig config;

    public JsonTypeFileWriter(FileWriteConfig config, FileColumns columns) throws FileNotFoundException {
        this.columns = columns;
        this.config = config;
        this.writer = LineFileWriter.create(config.filePath(), config.charset());
    }

    @Override
    public void writeHeader() throws Exception {
        this.writer.writeLine("[");
    }

    @Override
    public void writeTrial() throws Exception {
        this.writer.write("\n]");
    }

    @Override
    public void writeRecord(FileRecord record) throws Exception {
        if (!this.firstWrite) {
            this.writer.write(",\n");
        }
        int size = record.size();
        StringBuilder builder = new StringBuilder("  {\n");
        for (Map.Entry<Integer, Object> entry : record.entrySet()) {
            String columnName = this.columns.columnName(entry.getKey());
            // 名称
            builder.append("   \"").append(columnName).append("\" : ");
            // 值处理
            Object val = this.parameterized(entry.getValue());
            builder.append(val);
            if (--size != 0) {
                builder.append(",\n");
            } else {
                builder.append("\n");
            }
        }
        builder.append("  }");
        this.writer.write(builder.toString());
        this.firstWrite = false;
    }

    @Override
    public Object parameterized(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof Number) {
            return value;
        }
        return JSONUtil.toJson(value);
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
