package cn.oyzh.store.file;

import cn.oyzh.common.file.SkipAbleFileReader;
import cn.oyzh.common.util.IOUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;

/**
 * @author oyzh
 * @since 2024-09-04
 */
public class TxtTypeFileReader extends TypeFileReader {

    /**
     * 字段列表
     */
    private FileColumns columns;

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
        this.columns = columns;
        this.reader = new SkipAbleFileReader(config.filePath(), Charset.forName(config.charset()));
        // 设置换行符
        if (!Objects.equals(config.recordSeparator(), System.lineSeparator())) {
            this.reader.lineBreak(config.recordSeparator());
        }
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
        try {
            IOUtil.close(this.reader);
            this.reader = null;
            this.config = null;
            this.columns.clear();
            this.columns = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
