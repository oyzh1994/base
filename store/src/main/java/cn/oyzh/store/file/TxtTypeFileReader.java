package cn.oyzh.store.file;

import cn.oyzh.common.file.SkipAbleFileReader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

/**
 * @author oyzh
 * @since 2024-09-04
 */
public class TxtTypeFileReader extends TypeFileReader {

    // /**
    //  * 字段列表
    //  */
    // private List<String> columns;

    /**
     * 导入配置
     */
    private FileReadConfig config;

    /**
     * 文件读取器
     */
    private SkipAbleFileReader reader;

    public TxtTypeFileReader(FileReadConfig config, FileColumns columns) throws IOException {
        this.config = config;
        this.reader = new SkipAbleFileReader(config.filePath(), Charset.forName(config.charset()));
        // 设置换行符
        if (!Objects.equals(config.recordSeparator(), System.lineSeparator())) {
            this.reader.lineBreak(config.recordSeparator());
        }
        this.init();
    }

    @Override
    protected void init() throws IOException {
        // this.reader.jumpLine(this.config.columnIndex());
        // this.columns = new ArrayList<>();
        // String line = this.reader.readLine();
        // this.columns.addAll(this.parseLine(line, this.config.txtIdentifierChar(), this.config.fieldSeparatorChar()));
        // this.reader.jumpLine(this.config.dataStartIndex());
    }

    @Override
    public FileRecord readRecord() throws IOException {
        String line = this.reader.readLine();
        if (line != null) {
            List<String> arr = this.parseLine(line, this.config.txtIdentifier(), this.config.fieldSeparator());
            FileRecord record = new FileRecord();
            for (int i = 0; i < arr.size(); i++) {
                record.put(i, arr.get(i));
            }
            return record;
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
        this.reader = null;
        this.config = null;
        // this.columns.clear();
        // this.columns = null;
    }
}
