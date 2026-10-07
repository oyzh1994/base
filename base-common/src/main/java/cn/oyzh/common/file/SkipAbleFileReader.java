package cn.oyzh.common.file;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 可跳过文件读取器
 *
 * @author oyzh
 * @since 2024-09-02
 */
public class SkipAbleFileReader implements AutoCloseable {

    /** 底层缓冲读取器 */
    protected BufferedReader reader;

    /** 当前已读取的行号 */
    private int currentLine = 0;

    /** 自定义换行符，为 null 时使用 BufferedReader 默认行为 */
    private String lineBreak;

    /**
     * 以 UTF-8 编码创建读取器
     *
     * @param filePath 文件路径
     * @throws FileNotFoundException 文件不存在时抛出
     */
    public SkipAbleFileReader(String filePath) throws FileNotFoundException {
        this(new File(filePath), StandardCharsets.UTF_8);
    }

    /**
     * 以 UTF-8 编码创建读取器
     *
     * @param file 文件
     * @throws FileNotFoundException 文件不存在时抛出
     */
    public SkipAbleFileReader(File file) throws FileNotFoundException {
        this(file, StandardCharsets.UTF_8);
    }

    /**
     * 以指定字符集创建读取器
     *
     * @param filePath 文件路径
     * @param charset  字符集
     * @throws FileNotFoundException 文件不存在时抛出
     */
    public SkipAbleFileReader(String filePath, Charset charset) throws FileNotFoundException {
        this(new File(filePath), charset);
    }

    /**
     * 以指定字符集创建读取器
     *
     * @param file    文件
     * @param charset 字符集
     * @throws FileNotFoundException 文件不存在时抛出
     */
    public SkipAbleFileReader(File file, Charset charset) throws FileNotFoundException {
        this.reader = FileUtil.getReader(file, charset);
    }

    /**
     * 判断底层读取器是否已就绪
     *
     * @return 结果
     * @throws IOException 异常
     */
    public boolean ready() throws IOException {
        return this.reader.ready();
    }

    /**
     * 跳过一行
     *
     * @throws IOException 异常
     */
    public void skipLine() throws IOException {
        this.skipLine(1);
    }

    /**
     * 跳过指定行数
     *
     * @param lineCount 要跳过的行数
     * @throws IOException 异常
     */
    public void skipLine(int lineCount) throws IOException {
        if (lineCount > 0) {
            long count = lineCount;
            while (count-- > 0) {
                this.readLine();
            }
        }
    }

    /**
     * 跳转到指定行
     *
     * @param toLine 目标行号
     * @throws IOException 异常
     */
    public void jumpLine(int toLine) throws IOException {
        if (this.currentLine != toLine) {
            this.skipLine(toLine - this.currentLine);
        }
    }

    /**
     * 按自定义换行符读取一行，未设置换行符时退化为 {@link BufferedReader#readLine()}
     *
     * @return 行内容
     * @throws IOException 异常
     */
    private String _readLine() throws IOException {
        if (this.lineBreak == null) {
            return this.reader.readLine();
        }
        StringBuilder builder = new StringBuilder();
        char lineChar1 = this.lineBreak.charAt(0);
        if (this.lineBreak.length() == 1) {
            while (true) {
                int i = this.reader.read();
                if (i == -1) {
                    break;
                }
                char c = (char) i;
                if (c == lineChar1) {
                    break;
                }
                builder.append(c);
            }
        } else {
            Character prevChar = null;
            char lineChar2 = this.lineBreak.charAt(1);
            while (true) {
                int i = this.reader.read();
                if (i == -1) {
                    break;
                }
                char c = (char) i;
                if (prevChar != null && prevChar == lineChar1 && c == lineChar2) {
                    break;
                }
                builder.append(c);
                prevChar = c;
            }
        }
        return builder.toString();
    }

    /**
     * 读取一行并累加当前行号
     *
     * @return 行内容，已到末尾时返回 null
     * @throws IOException 异常
     */
    public String readLine() throws IOException {
        String line = this._readLine();
        this.currentLine++;
        return line;
    }

    /**
     * 读取指定行数的内容
     *
     * @param count 要读取的行数
     * @return 行列表
     * @throws IOException 异常
     */
    public List<String> readLines(int count) throws IOException {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String line = this.readLine();
            if (line != null) {
                lines.add(line);
            } else {
                break;
            }
        }
        return lines;
    }

    @Override
    public void close() {
        if (this.reader != null) {
            try {
                this.reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 读取一个字符
     *
     * @return 字符，已到末尾时返回 -1
     * @throws IOException 异常
     */
    public int read() throws IOException {
        return this.reader.read();
    }

    /**
     * 设置自定义换行符
     *
     * @param lineBreak 换行符
     */
    public void lineBreak(String lineBreak) {
        this.lineBreak = lineBreak;
    }
}
