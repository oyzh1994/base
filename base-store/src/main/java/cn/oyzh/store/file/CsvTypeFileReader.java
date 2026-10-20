package cn.oyzh.store.file;

import cn.oyzh.common.file.SkipAbleFileReader;
import cn.oyzh.common.util.IOUtil;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

/**
 * csv类型文件读取器
 *
 * <p>基于Apache Commons CSV实现，字段分割符号默认为逗号，可通过构造参数覆盖。</p>
 *
 * @author oyzh
 * @since 2024-11-28
 */
public class CsvTypeFileReader extends TypeFileReader<FileRecord> {

    /**
     * csv分割符号
     */
    private static final String CSV_DELIMITER = ",";

    /**
     * 字段列表
     */
    private FileColumns columns;

    /**
     * 导入配置
     */
    private FileReadConfig config;
//
//    /**
//     * 文件读取器
//     */
//    private SkipAbleFileReader reader;

    /**
     * CSV解析器
     */
    private CSVParser parser;

    /**
     * CSV记录迭代器
     */
    private Iterator<CSVRecord> records;

    /**
     * 构造csv类型文件读取器
     *
     * @param config  导入配置
     * @param columns 字段列表
     * @throws Exception 异常
     */
    public CsvTypeFileReader(FileReadConfig config, FileColumns columns) throws Exception {
        this(config, columns, CSV_DELIMITER);
    }

    /**
     * 构造定界文本类型文件读取器
     *
     * @param config         导入配置
     * @param columns        字段列表
     * @param fieldSeparator 字段分割符号
     * @throws Exception 异常
     */
    public CsvTypeFileReader(FileReadConfig config, FileColumns columns, String fieldSeparator) throws Exception {
        this.config = config;
        this.columns = columns;
//        this.reader = new SkipAbleFileReader(config.filePath(), Charset.forName(config.charset()));
        Charset charset = Charset.forName(config.getCharset());
        Reader reader = Files.newBufferedReader(java.nio.file.Path.of(config.filePath()), charset);
        boolean initialized = false;
        CSVParser csvParser = null;
        try {
            csvParser = CsvFormat.create(fieldSeparator, config.getTxtIdentifier(), config.getRecordSeparator()).parse(reader);
            this.parser = csvParser;
            this.records = csvParser.iterator();
            this.init();
            initialized = true;
        } finally {
            if (!initialized) {
                if (csvParser != null) {
                    csvParser.close();
                } else {
                    reader.close();
                }
            }
        }
    }

    @Override
    protected void init() throws Exception {
        if (this.config.dataRowStarts() != null) {
//            this.reader.skipLine(this.config.dataRowStarts() - 1);
            this.skipTo(this.config.dataRowStarts() - 1);
        }
    }

    /**
     * 将迭代器定位到指定记录索引
     *
     * @param recordIndex 记录索引
     * @throws IOException IO异常
     */
    private void skipTo(int recordIndex) throws IOException {
        int skipped = 0;
        while (skipped < recordIndex) {
            if (!this.records.hasNext()) {
                return;
            }
            this.records.next();
            skipped++;
        }
    }

    @Override
    public FileRecord readRecord() throws IOException {
//        String line = this.reader.readLine();
//        if (line != null) {
//            List<String> arr = this.parseLine(line, this.config.getTxtIdentifier(), ",");
//            FileRecord record = new FileRecord();
//            for (int i = 0; i < arr.size(); i++) {
//                record.put(i, arr.get(i));
//            }
//            return record;
//        }
//        return null;
        if (!this.records.hasNext()) {
            return null;
        }
        CSVRecord record = this.records.next();
        FileRecord fileRecord = new FileRecord();
        for (int i = 0; i < record.size(); i++) {
            fileRecord.put(i, record.get(i));
        }
        return fileRecord;
    }

    @Override
    public void close() {
        try {
//            IOUtil.close(this.reader);
//            this.reader = null;
            IOUtil.close(this.parser);
            this.parser = null;
            this.records = null;
            this.config = null;
            this.columns.clear();
            this.columns = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
