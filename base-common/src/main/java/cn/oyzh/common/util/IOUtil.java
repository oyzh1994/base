package cn.oyzh.common.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
}
