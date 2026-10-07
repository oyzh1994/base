package cn.oyzh.common.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 行文件写入器
 *
 * @author oyzh
 * @since 2024-10-18
 */
public class LineFileWriter implements AutoCloseable {

    /** 底层缓冲写入器 */
    protected BufferedWriter writer;

    /**
     * 以 UTF-8 编码创建写入器
     *
     * @param filePath 文件路径
     * @throws FileNotFoundException 文件不存在时抛出
     */
    public LineFileWriter(String filePath) throws FileNotFoundException {
        this(new File(filePath), StandardCharsets.UTF_8);
    }

    /**
     * 以 UTF-8 编码创建写入器
     *
     * @param file 文件
     * @throws FileNotFoundException 文件不存在时抛出
     */
    public LineFileWriter(File file) throws FileNotFoundException {
        this(file, StandardCharsets.UTF_8);
    }

    /**
     * 以指定字符集创建写入器
     *
     * @param file    文件
     * @param charset 字符集
     * @throws FileNotFoundException 文件不存在时抛出
     */
    public LineFileWriter(File file, Charset charset) throws FileNotFoundException {
        this.writer = FileUtil.getWriter(file, charset, false);
    }

    /**
     * 写入字符串（不换行）
     *
     * @param str 内容，为 null 时不处理
     * @throws IOException 写入失败时抛出
     */
    public void write(String str) throws IOException {
        if (str != null) {
            this.writer.write(str);
        }
    }

    /**
     * 写入一行（自动补充系统换行符）
     *
     * @param line 行内容，为 null 时不处理
     * @throws IOException 写入失败时抛出
     */
    public void writeLine(String line) throws IOException {
        if (line != null) {
            if (line.endsWith(System.lineSeparator())) {
                this.writer.write(line);
            } else {
                this.writer.write(line + System.lineSeparator());
            }
        }
    }

    @Override
    public void close() {
        if (this.writer != null) {
            try {
                this.writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 创建写入器
     *
     * @param filePath 文件路径
     * @param charset  字符集名称
     * @return 写入器
     * @throws FileNotFoundException 文件不存在时抛出
     */
    public static LineFileWriter create(String filePath, String charset) throws FileNotFoundException {
        return new LineFileWriter(new File(filePath), Charset.forName(charset));
    }

    /**
     * 创建写入器
     *
     * @param filePath 文件路径
     * @param charset  字符集
     * @return 写入器
     * @throws FileNotFoundException 文件不存在时抛出
     */
    public static LineFileWriter create(String filePath, Charset charset) throws FileNotFoundException {
        return new LineFileWriter(new File(filePath), charset);
    }

    /**
     * 创建写入器
     *
     * @param file    文件
     * @param charset 字符集名称
     * @return 写入器
     * @throws FileNotFoundException 文件不存在时抛出
     */
    public static LineFileWriter create(File file, String charset) throws FileNotFoundException {
        return new LineFileWriter(file, Charset.forName(charset));
    }

    /**
     * 创建写入器
     *
     * @param file    文件
     * @param charset 字符集
     * @return 写入器
     * @throws FileNotFoundException 文件不存在时抛出
     */
    public static LineFileWriter create(File file, Charset charset) throws FileNotFoundException {
        return new LineFileWriter(file, charset);
    }
}
