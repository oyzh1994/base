package cn.oyzh.store.file;


import java.nio.charset.StandardCharsets;

/**
 * 文件读取配置
 *
 * @author oyzh
 * @since 2024/09/02
 */
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

    public String getRecordSeparator() {
        return recordSeparator;
    }

    public void setRecordSeparator(String recordSeparator) {
        this.recordSeparator = recordSeparator;
    }

    public char getFieldSeparator() {
        return fieldSeparator;
    }

    public void setFieldSeparator(char fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

    public char getTxtIdentifier() {
        return txtIdentifier;
    }

    public void setTxtIdentifier(char txtIdentifier) {
        this.txtIdentifier = txtIdentifier;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getDataRowStarts() {
        return dataRowStarts;
    }

    public void setDataRowStarts(Integer dataRowStarts) {
        this.dataRowStarts = dataRowStarts;
    }
}
