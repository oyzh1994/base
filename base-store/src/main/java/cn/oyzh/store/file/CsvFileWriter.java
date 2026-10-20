package cn.oyzh.store.file;

import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 基于 Apache Commons CSV 的文件写入器
 *
 * @author oyzh
 * @since 2026-10-14
 */
public class CsvFileWriter implements AutoCloseable {

    /**
     * CSV 写入器
     */
    private final CSVPrinter printer;

    /**
     * 构造 CSV 文件写入器
     *
     * @param filePath 文件路径
     * @param config   导出配置
     * @throws IOException IO异常
     */
    public CsvFileWriter(String filePath, FileWriteConfig config) throws IOException {
        this(filePath, config, ",");
    }

    /**
     * 构造定界文本文件写入器
     *
     * @param filePath       文件路径
     * @param config         导出配置
     * @param fieldSeparator 字段分割符号
     * @throws IOException IO异常
     */
    public CsvFileWriter(String filePath, FileWriteConfig config, String fieldSeparator) throws IOException {
        File file = new File(filePath);
        Charset charset = Charset.forName(config.charset());
        this.printer = CsvFormat.create(fieldSeparator, config.getTxtIdentifier(), config.getRecordSeparator())
                .print(file, charset);
    }

    /**
     * 写入一条记录
     *
     * @param values 字段值
     * @throws IOException IO异常
     */
    public void write(Object... values) throws IOException {
        List<Object> records = new ArrayList<>(values.length);
        for (Object value : values) {
            records.add(Objects.requireNonNullElse(value, ""));
        }
        this.printer.printRecord(records);
    }

    /**
     * 写入一条记录
     *
     * @param values 字段值
     * @throws IOException IO异常
     */
    public void write(Iterable<?> values) throws IOException {
        List<Object> records = new ArrayList<>();
        for (Object value : values) {
            records.add(Objects.requireNonNullElse(value, ""));
        }
        this.printer.printRecord(records);
    }

    /**
     * 提交缓冲内容
     *
     * @throws IOException IO异常
     */
    public void flush() throws IOException {
        this.printer.flush();
    }

    @Override
    public void close() throws IOException {
        this.printer.close();
    }
}
