package cn.oyzh.store.file;

import lombok.Data;
import lombok.experimental.Accessors;

import java.nio.charset.StandardCharsets;

/**
 * @author oyzh
 * @since 2024/09/02
 */
@Data
@Accessors(chain = true, fluent = true)
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

}
