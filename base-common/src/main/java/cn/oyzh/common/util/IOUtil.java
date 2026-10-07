package cn.oyzh.common.util;

import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.thread.ThreadUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * io工具类
 *
 * @author oyzh
 * @since 2024-09-29
 */
public class IOUtil {

    /**
     * 私有构造，禁止实例化
     */
    private IOUtil() {
    }

    /**
     * 静默关闭对象，忽略关闭异常
     *
     * @param closeable 待关闭对象
     */
    public static void closeQuietly(AutoCloseable closeable) {
        close(closeable);
    }

    /**
     * 关闭对象，忽略关闭异常
     *
     * @param closeable 待关闭对象
     */
    public static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * 异步关闭
     *
     * @param closeable 待关闭对象
     */
    public static void closeAsync(AutoCloseable closeable) {
        if (closeable != null) {
            ThreadUtil.startVirtual(() -> IOUtil.closeQuietly(closeable));
        }
    }

    /**
     * 读取输入流的全部字节
     *
     * @param stream 输入流
     * @return 字节数组，流为null或读取失败时返回null
     */
    public static byte[] readBytes(InputStream stream) {
        if (stream != null) {
            try {
                return stream.readAllBytes();
            } catch (Exception ignored) {

            }
        }
        return null;
    }

    /**
     * 读取文件的全部字节
     *
     * @param filePath 文件路径
     * @return 字节数组，读取失败时返回null
     */
    public static byte[] readBytes(String filePath) {
        try {
            return readBytes(new FileInputStream(filePath));
        } catch (Exception ignored) {

        }
        return null;
    }

    /**
     * 以指定字符集读取输入流的文本
     *
     * @param stream  输入流
     * @param charset 字符集
     * @return 文本内容，流为null或读取失败时返回null
     */
    public static String readString(InputStream stream, Charset charset) {
        byte[] bytes = readBytes(stream);
        if (bytes == null) {
            return null;
        }
        return new String(bytes, charset);
    }

    /**
     * 以UTF-8字符集读取输入流的文本
     *
     * @param stream 输入流
     * @return 文本内容
     */
    public static String readUtf8String(InputStream stream) {
        return readString(stream, StandardCharsets.UTF_8);
    }

    /**
     * 以系统默认字符集读取输入流的文本
     *
     * @param stream 输入流
     * @return 文本内容
     */
    public static String readDefaultString(InputStream stream) {
        return readString(stream, Charset.defaultCharset());
    }

    /**
     * 将字节数组转换为输入流
     *
     * @param bytes 字节数组
     * @return 字节输入流
     */
    public static InputStream toStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 保存到文件
     *
     * @param stream   输入流
     * @param filePath 文件路径
     */
    public static void saveToFile(InputStream stream, String filePath) {
        if (stream != null) {
            try {
                byte[] bytes = new byte[4096];
                int len;
                if (!FileUtil.exists(filePath)) {
                    FileUtil.touch(filePath);
                }
                FileOutputStream fos = new FileOutputStream(filePath);
                while ((len = stream.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
                IOUtil.close(fos);
            } catch (Exception ignored) {

            }
        }
    }

    /**
     * 将输入流内容复制到输出流
     *
     * @param in  输入流
     * @param out 输出流
     */
    public static void saveToStream(InputStream in, OutputStream out) {
        if (in != null) {
            try {
                byte[] bytes = new byte[4096];
                int len;
                while ((len = in.read(bytes)) != -1) {
                    out.write(bytes, 0, len);
                }
            } catch (Exception ignored) {

            }
        }
    }

    /**
     * 从流中读取最多max个字节
     *
     * @param in  输入流
     * @param max 最多读取的字节数
     * @return 读取到的字节数组，可能少于max（流提前结束时）
     * @throws IOException 读取过程中发生IO异常时抛出
     */
    public static byte[] readAtMost(InputStream in, int max) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(Math.min(max, 8192));
        byte[] buf = new byte[Math.min(max, 8192)];
        int remaining = max;
        while (remaining > 0) {
            int n = in.read(buf, 0, Math.min(buf.length, remaining));
            if (n < 0) break;
            baos.write(buf, 0, n);
            remaining -= n;
        }
        return baos.toByteArray();
    }
}
