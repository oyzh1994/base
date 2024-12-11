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
}
