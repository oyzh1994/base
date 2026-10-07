package cn.oyzh.store.file;


import java.nio.charset.StandardCharsets;

/**
 * 文件写入配置
 *
 * @author oyzh
 * @since 2024-11-28
 */
public class FileWriteConfig {

    /**
     * 记录分割符号
     */
    private String recordSeparator = System.lineSeparator();

    /**
     * 文本识别符号
     */
    private Character txtIdentifier = '"';

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
    public FileWriteConfig filePath(String filePath) {
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
    public FileWriteConfig charset(String charset) {
        this.charset = charset;
        return this;
    }

    /**
     * 获取是否压缩
     *
     * @return 是否压缩
     */
    public boolean compress() {
        return this.compress;
    }

    /**
     * 设置是否压缩
     *
     * @param compress 是否压缩
     * @return 当前对象
     */
    public FileWriteConfig compress(boolean compress) {
        this.compress = compress;
        return this;
    }

    /**
     * 获取前缀
     *
     * @return 前缀
     */
    public String prefix() {
        return this.prefix;
    }

    /**
     * 设置前缀
     *
     * @param prefix 前缀
     * @return 当前对象
     */
    public FileWriteConfig prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    /**
     * 获取根节点名称
     *
     * @return 根节点名称
     */
    public String rootNodeName() {
        return this.rootNodeName;
    }

    /**
     * 设置根节点名称
     *
     * @param rootNodeName 根节点名称
     * @return 当前对象
     */
    public FileWriteConfig rootNodeName(String rootNodeName) {
        this.rootNodeName = rootNodeName;
        return this;
    }

    /**
     * 获取节点名称
     *
     * @return 节点名称
     */
    public String itemNodeName() {
        return this.itemNodeName;
    }

    /**
     * 设置节点名称
     *
     * @param itemNodeName 节点名称
     * @return 当前对象
     */
    public FileWriteConfig itemNodeName(String itemNodeName) {
        this.itemNodeName = itemNodeName;
        return this;
    }

    /**
     * 获取是否包含标题
     *
     * @return 是否包含标题
     */
    public boolean includeTitle() {
        return this.includeTitle;
    }

    /**
     * 设置是否包含标题
     *
     * @param includeTitle 是否包含标题
     * @return 当前对象
     */
    public FileWriteConfig includeTitle(boolean includeTitle) {
        this.includeTitle = includeTitle;
        return this;
    }

    /**
     * 获取工作薄名称
     *
     * @return 工作薄名称
     */
    public String sheetName() {
        return this.sheetName;
    }

    /**
     * 设置工作薄名称
     *
     * @param sheetName 工作薄名称
     * @return 当前对象
     */
    public FileWriteConfig sheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
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
    public FileWriteConfig txtIdentifier(Character txtIdentifier) {
        this.txtIdentifier = txtIdentifier;
        return this;
    }

    /**
     * 获取记录分割符号
     *
     * @return 记录分割符号
     */
    public String recordSeparator() {
        return recordSeparator;
    }

    /**
     * 设置记录分割符号
     *
     * @param recordSeparator 记录分割符号
     * @return 当前对象
     */
    public FileWriteConfig recordSeparator(String recordSeparator) {
        this.recordSeparator = recordSeparator;
        return this;
    }
}
