package cn.oyzh.store.file;

import cn.oyzh.common.file.LineFileWriter;
import cn.oyzh.common.util.IOUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * @author oyzh
 * @since 2024-09-04
 */
public class XmlTypeFileWriter extends TypeFileWriter {

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

    public XmlTypeFileWriter(FileWriteConfig config, FileColumns columns) throws FileNotFoundException {
        this.columns = columns;
        this.config = config;
        this.writer = LineFileWriter.create(config.filePath(), config.charset());
    }

    @Override
    public void writeHeader() throws Exception {
        this.writer.writeLine("<?xml version=\"1.0\" standalone=\"yes\"?>");
        if (this.config.compress()) {
            this.writer.write("<" + this.config.rootNodeName() + ">");
        } else {
            this.writer.writeLine("<" + this.config.rootNodeName() + ">");
        }
    }

    @Override
    public void writeTrial() throws Exception {
        if (this.config.compress()) {
            this.writer.write("</" + this.config.rootNodeName() + ">");
        } else {
            this.writer.writeLine("</" + this.config.rootNodeName() + ">");
        }
    }

    @Override
    public void writeRecord(FileRecord record) throws Exception {
        StringBuilder builder;
        if (this.config.compress()) {
            builder = new StringBuilder("<");
        } else {
            builder = new StringBuilder("  <");
        }
        builder.append(this.config.itemNodeName()).append(">");
        if (!this.config.compress()) {
            builder.append("\n");
        }
        for (Map.Entry<Integer, Object> entry : record.entrySet()) {
            String columnName = this.columns.columnName(entry.getKey());
            if (!this.config.compress()) {
                builder.append("   ");
            }
            // 名称
            builder.append("<").append(columnName);
            Object val = entry.getValue();
            if (val != null) {
                builder.append(">");
                builder.append(val);
                builder.append("</").append(columnName).append(">");
            } else {
                builder.append("/>");
            }
            if (!this.config.compress()) {
                builder.append("\n");
            }
        }
        if (!this.config.compress()) {
            builder.append("  ");
        }
        builder.append("</").append(this.config.itemNodeName()).append(">");
        if (!this.config.compress()) {
            this.writer.writeLine(builder.toString());
        } else {
            this.writer.write(builder.toString());
        }
    }

    @Override
    public void close() throws IOException {
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
