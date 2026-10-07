package cn.oyzh.common.file;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * 快速文件写入器
 *
 * @author oyzh
 * @since 2024/08/23
 */
public class FastFileWriter implements Closeable {

    /** 底层文件写入器 */
    private final FileWriter writer;

    /**
     * 以 UTF-8 编码创建写入器
     *
     * @param filePath 文件路径
     * @throws IOException 打开文件失败时抛出
     */
    public FastFileWriter(String filePath) throws IOException {
        this(new File(filePath), StandardCharsets.UTF_8);
    }

    /**
     * 以 UTF-8 编码创建写入器
     *
     * @param file 文件
     * @throws IOException 打开文件失败时抛出
     */
    public FastFileWriter(File file) throws IOException {
        this(file, StandardCharsets.UTF_8);
    }

    /**
     * 以指定字符集创建写入器
     *
     * @param file    文件
     * @param charset 字符集
     * @throws IOException 打开文件失败时抛出
     */
    public FastFileWriter(File file, Charset charset) throws IOException {
        this.writer = new FileWriter(file, charset);
    }

    /**
     * 追加一行（自动补换行符）并立即刷新
     *
     * @param line 行内容，为 null 时不处理
     * @throws IOException 写入失败时抛出
     */
    public void appendLine(String line) throws IOException {
        if (line != null) {
            if (!line.endsWith("\n")) {
                line += "\n";
            }
            this.writer.append(line);
            this.fulsh();
        }
    }

    /**
     * 批量追加多行（每行自动补换行符）并立即刷新
     *
     * @param lines 行集合，为 null 时不处理
     * @throws IOException 写入失败时抛出
     */
    public void appendLines(Collection<String> lines) throws IOException {
        if (lines != null) {
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                if (line.endsWith("\n")) {
                    sb.append(line);
                } else {
                    sb.append(line).append("\n");
                }
            }
            this.writer.append(sb.toString());
            this.fulsh();
        }
    }

    /**
     * 写入一行（自动补换行符）并立即刷新
     *
     * @param line 行内容，为 null 时不处理
     * @throws IOException 写入失败时抛出
     */
    public void writeLine(String line) throws IOException {
        if (line != null) {
            if (line.endsWith("\n")) {
                this.writer.write(line);
            } else {
                this.writer.write(line + "\n");
            }
            this.appendLine(line);
        }
    }

    /**
     * 批量写入多行（每行自动补换行符），不主动刷新
     *
     * @param lines 行集合，为 null 时不处理
     * @throws IOException 写入失败时抛出
     */
    public void writeLines(Collection<String> lines) throws IOException {
        if (lines != null) {
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                if (line.endsWith("\n")) {
                    sb.append(line);
                } else {
                    sb.append(line).append("\n");
                }
            }
            this.writer.write(sb.toString());
        }
    }

    /**
     * 刷新缓冲区
     *
     * @throws IOException 刷新失败时抛出
     */
    public void fulsh() throws IOException {
        this.writer.flush();
    }

    @Override
    public void close() {
        try {
            if (this.writer != null) {
                this.fulsh();
                this.writer.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //
    //    @Override
    //    protected void finalize() throws Throwable {
    //        this.close();
    //        super.finalize();
    //    }
}
