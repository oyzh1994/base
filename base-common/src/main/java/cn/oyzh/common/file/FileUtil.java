package cn.oyzh.common.file;

import cn.oyzh.common.function.ExceptionConsumer;
import cn.oyzh.common.util.StringUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类
 *
 * @author oyzh
 * @since 2024-09-29
 */
public class FileUtil {

    /**
     * 创建文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File touch(String filePath) {
        if (StringUtil.isBlank(filePath)) {
            return null;
        }
        return touch(new File(filePath));
    }

    /**
     * 创建文件
     *
     * @param file 文件
     * @return 文件
     */
    public static File touch(File file) {
        if (file != null) {
            try {
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                return file;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static BufferedWriter getWriter(File file, Charset charset, boolean append) throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), charset));
    }

    public static BufferedReader getReader(String filePath, Charset charset) throws FileNotFoundException {
        return getReader(new File(filePath), charset);
    }

    public static BufferedReader getReader(File file, Charset charset) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    }

    public static File writeString(String content, File file, Charset charset, boolean append) {
        try {
            if (content != null && file != null) {
                if (!file.exists()) {
                    touch(file);
                }
                try (FileWriter fileWriter = new FileWriter(file, charset, append)) {
                    fileWriter.write(content);
                }
                return file;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void writeString(String content, File file) {
        writeString(content, file, Charset.defaultCharset(), false);
    }

    public static void writeUtf8String(String content, File file) {
        writeString(content, file, StandardCharsets.UTF_8, false);
    }

    public static void writeUtf8String(String content, String file) {
        writeString(content, new File(file), StandardCharsets.UTF_8, false);
    }

    public static void writeBytes(byte[] data, String fileName) {
        writeBytes(data, new File(fileName));
    }

    public static void writeBytes(byte[] data, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isDirectory(File dir) {
        return dir != null && dir.isDirectory();
    }

    public static boolean del(String file) {
        if (file != null) {
            return del(new File(file));
        }
        return false;
    }

    public static boolean del(File file) {
        if (file != null) {
            return file.delete();
        }
        return false;
    }

    public static byte[] readBytes(String file) {
        return file == null ? null : readBytes(new File(file));
    }

    public static byte[] readBytes(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return null;
        }
        byte[] bytes;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try (fis; bos) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                bytes = bos.toByteArray();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return bytes;
    }

    public static boolean exist(String file) {
        return file != null && exist(new File(file));
    }

    public static boolean exist(File file) {
        return file != null && file.exists();
    }

    public static List<String> readLines(URL url, Charset charset) {
        try {
            InputStreamReader reader = new InputStreamReader(url.openStream(), charset);
            BufferedReader bufferedReader = new BufferedReader(reader);
            try (reader; bufferedReader) {
                List<String> list = new ArrayList<>();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    list.add(line);
                }
                return list;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readLines(InputStream stream, Charset charset) {
        try {
            InputStreamReader reader = new InputStreamReader(stream, charset);
            BufferedReader bufferedReader = new BufferedReader(reader);
            try (reader; bufferedReader) {
                List<String> list = new ArrayList<>();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    list.add(line);
                }
                return list;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readString(InputStream stream, Charset charset) {
        try {
            InputStreamReader reader = new InputStreamReader(stream, charset);
            BufferedReader bufferedReader = new BufferedReader(reader);
            try (reader; bufferedReader) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                return sb.toString();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readString(URL url, Charset charset) {
        try {
            return readString(url.openStream(), charset);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String readString(File file, Charset charset) {
        if (file.exists() && file.isFile()) {
            try {
                return readString(new FileInputStream(file), charset);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static String readUtf8String(String file) {
        return readUtf8String(new File(file));
    }

    public static String readUtf8String(File file) {
        return readString(file, StandardCharsets.UTF_8);
    }

    public static File[] ls(String dir) {
        if (dir == null) {
            return null;
        }
        File dirFile = new File(dir);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return null;
        }
        return dirFile.listFiles();
    }

    public static InputStream getInputStream(String file) {
        try {
            return new FileInputStream(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void appendLines(List<String> content, String file, String charset) {
        try {
            BufferedWriter writer = getWriter(new File(file), Charset.forName(charset), true);
            try (writer) {
                for (String s : content) {
                    writer.write(s);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean move(File source, File target, boolean override) {
        if (target.exists() && !override) {
            return false;
        }
        return source.renameTo(target);
    }

    public static boolean clean(File source) {
        if (source != null && source.exists() && !source.isDirectory() && !source.isAbsolute()) {
            try (FileOutputStream fos = new FileOutputStream(source)) {
                fos.write(new byte[]{});
                return true;
            } catch (Exception ignore) {
            }
        }
        return false;
    }

    public static boolean mkdir(File dir) {
        if (dir != null && !dir.exists()) {
            return dir.mkdirs();
        }
        return false;
    }

    public static boolean mkdir(String dir) {
        if (dir == null) {
            return false;
        }
        return mkdir(new File(dir));
    }

    public static boolean exists(String file) {
        return exist(new File(file));
    }

    public static List<File> getAllFiles(File folder) {
        List<File> fileList = new ArrayList<>();
        getAllFiles(folder, fileList);
        return fileList;
    }

    public static void getAllFiles(File folder, List<File> fileList) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    getAllFiles(file, fileList);
                } else {
                    fileList.add(file);
                }
            }
        }
    }

    /**
     * 获取全部文件
     *
     * @param folder   目录
     * @param callback 回调
     * @throws Exception 异常
     */
    public static void getAllFiles(File folder, ExceptionConsumer<File> callback) throws Exception {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    getAllFiles(file, callback);
                } else {
                    callback.accept(file);
                }
            }
        }
    }

    /**
     * 获取文件大小
     *
     * @param file 文件
     * @return 文件大小
     */
    public static long size(File file) {
        return file.length();
    }

    /**
     * 获取文件大小
     *
     * @param file 文件
     * @return 文件大小
     */
    public static long size(String file) {
        return new File(file).length();
    }

    public static void forceMkdir(File dir) {
        mkdir(dir);
    }
}
