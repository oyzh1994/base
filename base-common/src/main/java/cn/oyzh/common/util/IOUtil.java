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

    private IOUtil() {
    }

    public static void closeQuietly(AutoCloseable closeable) {
        close(closeable);
    }

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
     * @param closeable 对象
     */
    public static void closeAsync(AutoCloseable closeable) {
        if (closeable != null) {
            ThreadUtil.startVirtual(() -> IOUtil.closeQuietly(closeable));
        }
    }

    public static byte[] readBytes(InputStream stream) {
        if (stream != null) {
            try {
                return stream.readAllBytes();
            } catch (Exception ignored) {

            }
        }
        return null;
    }

    public static byte[] readBytes(String filePath) {
        try {
            return readBytes(new FileInputStream(filePath));
        } catch (Exception ignored) {

        }
        return null;
    }

    public static String readString(InputStream stream, Charset charset) {
        byte[] bytes = readBytes(stream);
        if (bytes == null) {
            return null;
        }
        return new String(bytes, charset);
    }

    public static String readUtf8String(InputStream stream) {
        return readString(stream, StandardCharsets.UTF_8);
    }

    public static String readDefaultString(InputStream stream) {
        return readString(stream, Charset.defaultCharset());
    }

    public static InputStream toStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    /**
     * 保存到文件
     *
     * @param stream   流
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
     * 保存到文件
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

    /** 从流中读取最多 max 字节（可能少于 max，如果流提前结束） */
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
