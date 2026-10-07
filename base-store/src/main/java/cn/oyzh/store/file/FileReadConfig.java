package cn.oyzh.store.file;


import java.nio.charset.StandardCharsets;

/**
 * 文件读取配置
 *
 * @author oyzh
 * @since 2024-11-28
 */
public class FileReadConfig {

    /**
     * 记录分割符号
     */
    private String recordSeparator = System.lineSeparator();

    /**
     * 字段分割符号
     */
    private Character fieldSeparator = ' ';

    /**
     * 文本识别符号
     */
    private Character txtIdentifier = '"';

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
     * 注意，起始下标为1
     */
    private Integer dataRowStarts;

    /**
     * 获取文件路径
     *
     * @return 文件路径
     */
    public String filePath() {
        return this.filePath;
    }

    /**
     * 设置文件路径
     *
     * @param filePath 文件路径
     * @return 当前对象
     */
    public FileReadConfig filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    /**
     * 获取字符集
     *
     * @return 字符集
     */
    public String charset() {
        return this.charset;
    }

    /**
     * 设置字符集
     *
     * @param charset 字符集
     * @return 当前对象
     */
    public final FileReadConfig charset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * 获取记录分割符号
     *
     * @return 记录分割符号
     */
    public String recordSeparator() {
        return this.recordSeparator;
    }

    /**
     * 获取文本识别符号
     *
     * @return 文本识别符号
     */
    public Character txtIdentifier() {
        return this.txtIdentifier;
    }

    /**
     * 设置文本识别符号
     *
     * @param txtIdentifier 文本识别符号
     * @return 当前对象
     */
    public FileReadConfig txtIdentifier(Character txtIdentifier) {
        this.txtIdentifier = txtIdentifier;
        return this;
    }

    /**
     * 获取字段分割符号
     *
     * @return 字段分割符号
     */
    public Character fieldSeparator() {
        return this.fieldSeparator;
    }

    /**
     * 获取数据行开始索引
     *
     * @return 数据行开始索引
     */
    public Integer dataRowStarts() {
        return this.dataRowStarts;
    }

    /**
     * 设置数据行开始索引
     *
     * @param dataRowStarts 数据行开始索引
     * @return 当前对象
     */
    public FileReadConfig dataRowStarts(Integer dataRowStarts) {
        this.dataRowStarts = dataRowStarts;
        return this;
    }

    /**
     * 获取记录分割符号
     *
     * @return 记录分割符号
     */
    public String getRecordSeparator() {
        return recordSeparator;
    }

    /**
     * 设置记录分割符号
     *
     * @param recordSeparator 记录分割符号
     */
    public void setRecordSeparator(String recordSeparator) {
        this.recordSeparator = recordSeparator;
    }

    /**
     * 获取字段分割符号
     *
     * @return 字段分割符号
     */
    public Character getFieldSeparator() {
        return fieldSeparator;
    }

    /**
     * 设置字段分割符号
     *
     * @param fieldSeparator 字段分割符号
     */
    public void setFieldSeparator(Character fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

    /**
     * 获取文本识别符号
     *
     * @return 文本识别符号
     */
    public Character getTxtIdentifier() {
        return txtIdentifier;
    }

    /**
     * 设置文本识别符号
     *
     * @param txtIdentifier 文本识别符号
     */
    public void setTxtIdentifier(Character txtIdentifier) {
        this.txtIdentifier = txtIdentifier;
    }

    /**
     * 获取字符集
     *
     * @return 字符集
     */
    public String getCharset() {
        return charset;
    }

    /**
     * 设置字符集
     *
     * @param charset 字符集
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * 获取文件路径
     *
     * @return 文件路径
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置文件路径
     *
     * @param filePath 文件路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取数据行开始索引
     *
     * @return 数据行开始索引
     */
    public Integer getDataRowStarts() {
        return dataRowStarts;
    }

    /**
     * 设置数据行开始索引
     *
     * @param dataRowStarts 数据行开始索引
     */
    public void setDataRowStarts(Integer dataRowStarts) {
        this.dataRowStarts = dataRowStarts;
    }
}
