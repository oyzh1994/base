package cn.oyzh.store.file;


import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author oyzh
 * @since 2024/09/02
 */
//@Data
//@Accessors(chain = true, fluent = true)
public class FileReadConfig {

    /**
     * 记录分割符号
     */
    private String recordSeparator = System.lineSeparator();

    /**
     * 字段分割符号
     */
    private char fieldSeparator = ' ';

    /**
     * 文本识别符号
     */
    private char txtIdentifier = '"';

    /**
     * 字符集
     */
    private String charset = StandardCharsets.UTF_8.name();

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 数据行开始索引
     */
    private Integer dataRowStarts;

    public String filePath() {
        return this.filePath;
    }

    public FileReadConfig filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String charset() {
        return this.charset;
    }

    public final FileReadConfig charset(String charset) {
        this.charset = charset;
        return this;
    }

    public String recordSeparator() {
        return this.recordSeparator;
    }

    public char txtIdentifier() {
        return this.txtIdentifier;
    }

    public FileReadConfig txtIdentifier(char txtIdentifier) {
        this.txtIdentifier = txtIdentifier;
        return this;
    }

    public char fieldSeparator() {
        return this.fieldSeparator;
    }

    public Integer dataRowStarts() {
        return this.dataRowStarts;
    }

    public FileReadConfig dataRowStarts(Integer dataRowStarts) {
        this.dataRowStarts = dataRowStarts;
        return this;
    }
}
