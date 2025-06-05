package cn.oyzh.store.file;


import java.nio.charset.StandardCharsets;

/**
 * 文件写入配置
 *
 * @author oyzh
 * @since 2024/09/02
 */
public class FileWriteConfig {

    /**
     * 记录分割符号
     */
    private String recordSeparator = System.lineSeparator();

    /**
     * 文本识别符号
     */
    private char txtIdentifier = '"';

    /**
     * 包含标题
     */
    private boolean includeTitle;

    /**
     * 字符集
     */
    private String charset = StandardCharsets.UTF_8.name();

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 根节点名称，xml需要
     */
    private String rootNodeName = "Nodes";

    /**
     * 节点名称，xml需要
     */
    private String itemNodeName = "Node";

    /**
     * 工作薄名称，excel需要
     */
    private String sheetName = "Nodes";

    /**
     * 压缩内容
     */
    private boolean compress;

    public String filePath() {
        return this.filePath;
    }

    public FileWriteConfig filePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String charset() {
        return this.charset;
    }

    public FileWriteConfig charset(String charset) {
        this.charset = charset;
        return this;
    }

    public boolean compress() {
        return this.compress;
    }

    public FileWriteConfig compress(boolean compress) {
        this.compress = compress;
        return this;
    }

    public String prefix() {
        return this.prefix;
    }

    public FileWriteConfig prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public String rootNodeName() {
        return this.rootNodeName;
    }

    public String itemNodeName() {
        return this.itemNodeName;
    }

    public boolean includeTitle() {
        return this.includeTitle;
    }

    public FileWriteConfig includeTitle(boolean includeTitle) {
        this.includeTitle = includeTitle;
        return this;
    }

    public String sheetName() {
        return this.sheetName;
    }

    public Character txtIdentifier() {
        return this.txtIdentifier;
    }

    public FileWriteConfig txtIdentifier(Character txtIdentifier) {
        this.txtIdentifier = txtIdentifier;
        return this;
    }

    public String getRecordSeparator() {
        return recordSeparator;
    }

    public void setRecordSeparator(String recordSeparator) {
        this.recordSeparator = recordSeparator;
    }

    public char getTxtIdentifier() {
        return txtIdentifier;
    }

    public void setTxtIdentifier(char txtIdentifier) {
        this.txtIdentifier = txtIdentifier;
    }

    public boolean isIncludeTitle() {
        return includeTitle;
    }

    public void setIncludeTitle(boolean includeTitle) {
        this.includeTitle = includeTitle;
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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getRootNodeName() {
        return rootNodeName;
    }

    public void setRootNodeName(String rootNodeName) {
        this.rootNodeName = rootNodeName;
    }

    public String getItemNodeName() {
        return itemNodeName;
    }

    public void setItemNodeName(String itemNodeName) {
        this.itemNodeName = itemNodeName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public boolean isCompress() {
        return compress;
    }

    public void setCompress(boolean compress) {
        this.compress = compress;
    }
}
