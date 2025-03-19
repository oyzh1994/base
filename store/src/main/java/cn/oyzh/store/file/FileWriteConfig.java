package cn.oyzh.store.file;


import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author oyzh
 * @since 2024/09/02
 */
//@Data
//@Accessors(chain = true, fluent = true)
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
}
