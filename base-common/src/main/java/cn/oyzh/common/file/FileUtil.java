package cn.oyzh.common.file;

import cn.oyzh.common.exception.InvalidParamException;
import cn.oyzh.common.function.ExceptionConsumer;
import cn.oyzh.common.system.OSUtil;
import cn.oyzh.common.system.RuntimeUtil;
import cn.oyzh.common.util.StringUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 文件工具类
 *
 * @author oyzh
 * @since 2024-10-18
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
                if (!file.exists() && file.getParentFile() != null) {
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

    /**
     * 获取文件写入器
     *
     * @param file    文件
     * @param charset 字符集
     * @param append  是否追加写入
     * @return 缓冲写入器
     * @throws FileNotFoundException 文件不存在时抛出
     */
    public static BufferedWriter getWriter(File file, Charset charset, boolean append) throws FileNotFoundException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), charset));
    }

    /**
     * 获取文件读取器
     *
     * @param filePath 文件路径
     * @param charset  字符集
     * @return 缓冲读取器
     * @throws FileNotFoundException 文件不存在时抛出
     */
    public static BufferedReader getReader(String filePath, Charset charset) throws FileNotFoundException {
        return getReader(new File(filePath), charset);
    }

    /**
     * 获取文件读取器
     *
     * @param file    文件
     * @param charset 字符集
     * @return 缓冲读取器
     * @throws FileNotFoundException 文件不存在时抛出
     */
    public static BufferedReader getReader(File file, Charset charset) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
    }

    /**
     * 将字符串写入文件
     *
     * @param content 内容
     * @param file    目标文件
     * @param charset 字符集
     * @param append  是否追加写入
     * @return 目标文件，写入失败时返回 null
     */
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

    /**
     * 将字符串以默认字符集覆盖写入文件
     *
     * @param content 内容
     * @param file    目标文件
     */
    public static void writeString(String content, File file) {
        writeString(content, file, Charset.defaultCharset(), false);
    }

    /**
     * 将字符串以 UTF-8 编码覆盖写入文件
     *
     * @param content 内容
     * @param file    目标文件
     */
    public static void writeUtf8String(String content, File file) {
        writeString(content, file, StandardCharsets.UTF_8, false);
    }

    /**
     * 将字符串以 UTF-8 编码覆盖写入文件
     *
     * @param content 内容
     * @param file    目标文件路径
     */
    public static void writeUtf8String(String content, String file) {
        writeString(content, new File(file), StandardCharsets.UTF_8, false);
    }

    /**
     * 将字节数组写入文件
     *
     * @param data     数据
     * @param fileName 文件路径
     */
    public static void writeBytes(byte[] data, String fileName) {
        writeBytes(data, new File(fileName));
    }

    /**
     * 将字节数组写入文件
     *
     * @param data 数据
     * @param file 目标文件
     */
    public static void writeBytes(byte[] data, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 是否目录
     *
     * @param dir 目录
     * @return 结果
     */
    public static boolean isDirectory(File dir) {
        return dir != null && Files.isDirectory(dir.toPath());
    }

    /**
     * 是否目录
     *
     * @param dir 目录
     * @return 结果
     */
    public static boolean isDirectory(String dir) {
        return dir != null && isDirectory(new File(dir));
    }

    /**
     * 删除
     *
     * @param file 文件
     * @return 结果
     */
    public static boolean del(String file) {
        return file != null && del(new File(file), false);
    }

    /**
     * 删除
     *
     * @param path 文件
     * @return 结果
     */
    public static boolean del(Path path) {
        return path != null && del(path.toFile(), false);
    }

    /**
     * 删除
     *
     * @param file 文件
     * @return 结果
     */
    public static boolean del(File file) {
        return del(file, false);
    }

    /**
     * 删除
     *
     * @param file  文件
     * @param force 是否强制删除
     * @return 结果
     */
    public static boolean del(String file, boolean force) {
        return del(new File(file), force);
    }

    /**
     * 删除
     *
     * @param file  文件
     * @param force 强制
     * @return 结果
     */
    public static boolean del(File file, boolean force) {
        if (file == null || !file.exists()) {
            return true;
        }
        boolean success = deleteRecursively(file, true);
        if (!success && force) {
            try {
                if (OSUtil.isWindows()) {
                    String[] cmdArr = {"rmdir", "/s", "/q", file.getPath()};
                    RuntimeUtil.execForStr(cmdArr);
                } else {
                    RuntimeUtil.exec("rm -rf \"" + file + "\"", null, file.getParentFile());
                }
                success = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return success;
    }

    /**
     * 递归删除
     *
     * @param file    文件
     * @param delSelf 是否删除自身
     * @return 结果
     */
    private static boolean deleteRecursively(File file, boolean delSelf) {
        if (file == null || !file.exists()) {
            return true;
        }
        if (file.isDirectory() && !Files.isSymbolicLink(file.toPath())) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    if (!deleteRecursively(child, true)) {
                        return false;
                    }
                }
            }
        }
        if (!delSelf) {
            return true;
        }
        return file.delete();
    }

    /**
     * 读取文件全部字节
     *
     * @param file 文件路径
     * @return 字节数组
     */
    public static byte[] readBytes(String file) {
        return file == null ? null : readBytes(new File(file));
    }

    /**
     * 读取文件全部字节
     *
     * @param file 文件
     * @return 字节数组，文件不存在或为目录时返回 null
     */
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

    /**
     * 判断文件是否存在
     *
     * @param file 文件路径
     * @return 结果
     */
    public static boolean exists(String file) {
        return file != null && exists(new File(file));
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return 结果
     */
    public static boolean exists(File file) {
        return file != null && file.exists();
    }

    /**
     * 判断路径是否存在
     *
     * @param file 路径
     * @return 结果
     */
    public static boolean exists(Path file) {
        return file != null && Files.exists(file);
    }

    /**
     * 读取 URL 内容的所有行
     *
     * @param url     URL
     * @param charset 字符集
     * @return 行列表
     */
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

    /**
     * 读取输入流的所有行
     *
     * @param stream  输入流
     * @param charset 字符集
     * @return 行列表
     */
    public static List<String> readLines(InputStream stream, Charset charset) {
        try (InputStreamReader reader = new InputStreamReader(stream, charset);
             BufferedReader bufferedReader = new BufferedReader(reader);
        ) {
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

    /**
     * 读取输入流的全部内容为字符串（每行后补换行符）
     *
     * @param stream  输入流
     * @param charset 字符集
     * @return 内容
     */
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

    /**
     * 读取 URL 的全部内容为字符串
     *
     * @param url     URL
     * @param charset 字符集
     * @return 内容，读取失败时返回 null
     */
    public static String readString(URL url, Charset charset) {
        try {
            return readString(url.openStream(), charset);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文件的全部内容为字符串
     *
     * @param file    文件
     * @param charset 字符集
     * @return 内容，文件不存在或读取失败时返回 null
     */
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

    /**
     * 以 UTF-8 编码读取文件全部内容
     *
     * @param file 文件路径
     * @return 内容
     */
    public static String readUtf8String(String file) {
        return readUtf8String(new File(file));
    }

    /**
     * 以 UTF-8 编码读取文件全部内容
     *
     * @param file 文件
     * @return 内容
     */
    public static String readUtf8String(File file) {
        return readString(file, StandardCharsets.UTF_8);
    }

    /**
     * 列出目录下的文件
     *
     * @param dir 目录路径
     * @return 文件数组，目录不存在时返回 null
     */
    public static File[] ls(Path dir) {
        if (dir == null) {
            return null;
        }
        return ls(dir.toFile());
    }

    /**
     * 列出目录下的文件
     *
     * @param dir 目录
     * @return 文件数组，目录不存在时返回 null
     */
    public static File[] ls(File dir) {
        if (dir == null) {
            return null;
        }
        return ls(dir.getPath(), null);
    }

    /**
     * 列出目录下的文件
     *
     * @param dir 目录路径
     * @return 文件数组，目录不存在时返回 null
     */
    public static File[] ls(String dir) {
        return ls(dir, null);
    }

    /**
     * 按过滤器列出目录下的文件
     *
     * @param dir    目录路径
     * @param filter 过滤器，为 null 时列出全部
     * @return 文件数组，目录不存在时返回 null
     */
    public static File[] ls(String dir, FileFilter filter) {
        if (dir == null) {
            return null;
        }
        File dirFile = new File(dir);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return null;
        }
        if (filter == null) {
            return dirFile.listFiles();
        }
        return dirFile.listFiles(filter);
    }

    /**
     * 获取文件输入流
     *
     * @param file 文件路径
     * @return 输入流，打开失败时返回 null
     */
    public static InputStream getInputStream(String file) {
        try {
            return new FileInputStream(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 将多行内容以追加方式写入文件
     *
     * @param content 内容列表
     * @param file    文件路径
     * @param charset 字符集名称
     */
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

    /**
     * 将文件移动到指定目录下。
     * <p>
     * source 必须是已存在的文件，dir 必须是已存在的目录；若目标目录下已存在同名文件
     * 且 override 为 false，则不执行移动并返回 false。
     *
     * @param source   源文件
     * @param dir      目标目录
     * @param override 是否覆盖同名文件
     * @return 结果
     * @throws IOException 异常
     */
    public static boolean moveFile(File source, File dir, boolean override) throws IOException {
        if (!source.exists() || !source.isFile()) {
            throw new InvalidParamException(source.getPath());
        }
        if (!dir.exists() || !dir.isDirectory()) {
            throw new InvalidParamException(dir.getPath());
        }
        File tFile = new File(dir, source.getName());
        if (tFile.exists() && !override) {
            return false;
        }
        return source.renameTo(tFile);
    }

    /**
     * 移动目录。
     * <p>
     * source 必须是已存在的目录；目标已存在且 override 为 false 时返回 false；
     * 移动完成后会删除源目录。
     *
     * @param source   源目录
     * @param target   目标路径
     * @param override 是否覆盖
     * @return 结果
     * @throws IOException 异常
     */
    public static boolean moveDir(String source, String target, boolean override) throws IOException {
        return moveDir(new File(source), new File(target), override);
    }

    /**
     * 移动目录。
     * <p>
     * source 必须是已存在的目录；目标已存在且 override 为 false 时返回 false；
     * 移动完成后会删除源目录。
     *
     * @param source   源目录
     * @param target   目标路径
     * @param override 是否覆盖
     * @return 结果
     * @throws IOException 异常
     */
    public static boolean moveDir(File source, File target, boolean override) throws IOException {
        if (!source.exists() || !source.isDirectory()) {
            throw new InvalidParamException(source.getPath());
        }
        if (!target.exists() && target.isDirectory()) {
            throw new InvalidParamException(target.getPath());
        }
        if (target.exists() && !override) {
            return false;
        }
        Path sPath = source.toPath();
        Path tPath = target.toPath();
        if (target.exists() && Files.isSameFile(sPath, tPath)) {
            return false;
        }
        Files.move(sPath, tPath);
        del(source);
        return true;
    }

    /**
     * 重命名文件
     *
     * @param source   源文件
     * @param target   目标文件
     * @param override 是否覆盖
     * @return 结果
     * @throws IOException 异常
     */
    public static boolean renameFile(String source, String target, boolean override) throws IOException {
        return renameFile(new File(source), new File(target), override);
    }

    /**
     * 重命名文件
     *
     * @param source   源文件
     * @param target   目标文件
     * @param override 是否覆盖
     * @return 结果
     * @throws IOException 异常
     */
    public static boolean renameFile(File source, File target, boolean override) throws IOException {
        if (!source.exists() || !source.isFile()) {
            throw new InvalidParamException(source.getPath());
        }
        if (!target.exists() && target.isFile()) {
            throw new InvalidParamException(target.getPath());
        }
        if (target.exists() && !override) {
            return false;
        }
        Path sPath = source.toPath();
        Path tPath = target.toPath();
        if (target.exists() && Files.isSameFile(sPath, tPath)) {
            return false;
        }
        return source.renameTo(target);
    }

    /**
     * 清空文件
     *
     * @param source 文件
     * @return 结果
     */
    public static boolean cleanFile(File source) {
        if (source != null && source.exists() && !source.isDirectory() && source.isFile()) {
            try (FileOutputStream fos = new FileOutputStream(source)) {
                fos.write(new byte[]{});
                return true;
            } catch (Exception ignore) {
            }
        }
        return false;
    }

    /**
     * 创建目录（含多级父目录）
     *
     * @param dir 目录路径
     * @return 是否创建成功
     */
    public static boolean mkdir(Path dir) {
        if (dir == null) {
            return false;
        }
        return mkdir(dir.toFile());
    }

    /**
     * 创建目录（含多级父目录）
     *
     * @param dir 目录
     * @return 是否创建成功
     */
    public static boolean mkdir(File dir) {
        if (dir != null && !dir.exists()) {
            return dir.mkdirs();
        }
        return false;
    }

    /**
     * 创建目录（含多级父目录）
     *
     * @param dir 目录路径
     * @return 是否创建成功
     */
    public static boolean mkdir(String dir) {
        if (dir == null) {
            return false;
        }
        return mkdir(new File(dir));
    }

    //    public static boolean exists(String file) {
    //        return exist(Path.of(file));
    //    }

    /**
     * 判断路径是否存在
     *
     * @param file 起始路径
     * @param more 追加的子路径
     * @return 结果
     */
    public static boolean exists(String file, String... more) {
        if (file == null) {
            return false;
        }
        return exists(Path.of(file, more));
    }

    /**
     * 递归获取目录下的所有文件
     *
     * @param folder 目录路径
     * @return 文件列表
     */
    public static List<File> getAllFiles(String folder) {
        return getAllFiles(new File(folder));
    }

    /**
     * 递归获取目录下的所有文件
     *
     * @param folder 目录
     * @return 文件列表
     */
    public static List<File> getAllFiles(File folder) {
        List<File> fileList = new ArrayList<>();
        getAllFiles(folder, fileList);
        return fileList;
    }

    /**
     * 递归获取目录下的所有文件并收集到指定列表
     *
     * @param folder   目录
     * @param fileList 结果列表
     */
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
    public static void getAllFiles(String folder, ExceptionConsumer<File> callback) throws Exception {
        getAllFiles(new File(folder), callback);
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

    /**
     * 创建目录（不存在时）
     *
     * @param dir 目录
     */
    public static void forceMkdir(File dir) {
        mkdir(dir);
    }

    /**
     * 清空目录内容
     *
     * @param directory 目录
     * @return 结果
     */
    public static boolean clean(String directory) {
        return directory != null && clean(new File(directory));
    }

    /**
     * 清空目录内容
     *
     * @param directory 目录
     * @return 结果
     */
    public static boolean clean(File directory) {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            return true;
        }
        return deleteRecursively(directory, false);
    }

    /**
     * 删除目录及其全部内容
     *
     * @param directory 目录
     * @return 结果
     */
    @Deprecated
    public static boolean cleanDir(String directory) {
        return cleanDir(new File(directory));
    }

    /**
     * 删除目录及其全部内容
     *
     * @param directory 目录路径
     * @return 结果
     */
    @Deprecated
    public static boolean cleanDir(Path directory) {
        if (directory == null) {
            return false;
        }
        return cleanDir(directory.toFile());
    }

    /**
     * 删除目录及其全部内容
     *
     * @param directory 目录
     * @return 结果
     */
    @Deprecated
    public static boolean cleanDir(File directory) {
        return clean(directory);
    }

    /**
     * 写入utf8数据行
     *
     * @param list 数据列表
     * @param file 文件
     */
    public static void writeUtf8Lines(Collection<String> list, File file) {
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(s).append(System.lineSeparator());
        }
        writeString(builder.toString(), file);
    }

    /**
     * 获取系统临时目录路径
     *
     * @return 临时目录路径
     */
    public static String tmpPath() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 获取系统临时目录
     *
     * @return 临时目录
     */
    public static File tmpdir() {
        return new File(tmpPath());
    }

    /**
     * 创建临时文件
     *
     * @param tempFile 临时文件名称
     * @return 临时文件
     */
    public static File newTmpFile(String tempFile) {
        return new File(tmpPath(), tempFile);
    }

    /**
     * 创建临时文件
     *
     * @param suffix     文件后缀
     * @param isReCreate 是否重新创建文件
     * @return 临时文件
     */
    public static File createTempFile(String suffix, boolean isReCreate) {
        try {
            Path tempFile = Files.createTempFile("base", suffix);
            if (isReCreate) {
                Files.delete(tempFile);
                Files.createFile(tempFile);
            }
            return tempFile.toFile();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    /**
     * 复制文件或目录
     *
     * @param source   来源
     * @param target   目标
     * @param override 是否覆盖
     * @return 目标文件或目录
     */
    public static File copy(File source, File target, boolean override) {
        if (source == null || !source.exists()) {
            throw new IllegalArgumentException("source not exists");
        }
        if (target == null) {
            throw new IllegalArgumentException("target is null");
        }
        File actualTarget = target;
        if (source.isFile() && target.isDirectory()) {
            actualTarget = new File(target, source.getName());
        } else if (source.isDirectory() && !target.exists()) {
            actualTarget = new File(target, source.getName());
        }
        if (!override && actualTarget.exists()) {
            return actualTarget;
        }
        try {
            if (source.isDirectory()) {
                copyDirectory(source, actualTarget);
            } else {
                copyFile(source, actualTarget);
            }
            return actualTarget;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    /**
     * 复制文件或目录
     *
     * @param source   来源路径
     * @param target   目标路径
     * @param override 是否覆盖
     * @return 目标文件或目录
     */
    public static File copy(String source, String target, boolean override) {
        return copy(new File(source), new File(target), override);
    }

    /**
     * 复制目录内容
     *
     * @param source   来源目录
     * @param target   目标目录
     * @param override 是否覆盖
     * @return 目标目录
     */
    public static File copyContent(File source, File target, boolean override) {
        if (source == null || !source.isDirectory()) {
            throw new IllegalArgumentException("source is not directory");
        }
        if (target == null) {
            throw new IllegalArgumentException("target is null");
        }
        if (!override && target.exists()) {
            return target;
        }
        try {
            copyDirectory(source, target);
            return target;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    /**
     * 移动文件或目录
     *
     * @param source   来源
     * @param target   目标
     * @param override 是否覆盖
     * @return 目标文件或目录
     */
    public static File move(File source, File target, boolean override) {
        if (source == null || !source.exists()) {
            throw new IllegalArgumentException("source not exists");
        }
        if (target == null) {
            throw new IllegalArgumentException("target is null");
        }
        File actualTarget = target.isDirectory() ? new File(target, source.getName()) : target;
        File parent = actualTarget.getParentFile();
        if (parent != null) {
            mkdir(parent);
        }
        try {
            if (override) {
                Files.move(source.toPath(), actualTarget.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.move(source.toPath(), actualTarget.toPath());
            }
            return actualTarget;
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    /**
     * 复制文件/目录
     *
     * @param source 源
     * @param target 目标
     * @throws Exception 异常
     */
    public static void copy(String source, String target) throws Exception {
        copy(new File(source), new File(target));
    }

    /**
     * 复制文件/目录
     * <p>
     * 1. source为文件，target为文件，复制内容
     * 2. source为目录，target为目录，复制内容
     * 3. source为文件，target为目录，复制内容到target
     *
     * @param source 源
     * @param target 目标
     * @throws Exception 异常
     */
    public static void copy(File source, File target) throws Exception {
        if (source.isDirectory() && target.isFile()) {
            throw new InvalidPathException(target.getPath(), "not dir");
        }
        if (!source.exists()) {
            throw new FileNotFoundException(source.getAbsolutePath());
        }
        if (source.isFile()) {
            // 目标为目录，则把源文件复制到目标目录
            if (target.isDirectory()) {
                copyFile(source, new File(target, source.getName()));
            } else { // 处理文件复制
                copyFile(source, target);
            }
        } else if (source.isDirectory() && target.isDirectory()) {
            // 处理目录复制
            copyDirectory(source, target);
        } else {
            throw new IOException("不支持的文件类型: " + source.getAbsolutePath());
        }
    }

    /**
     * 复制单个文件
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     */
    public static void copyFile(String sourceFile, String targetFile) throws IOException {
        copyFile(new File(sourceFile), new File(targetFile));
    }

    /**
     * 复制单个文件
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     */
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        // 如果目标是目录，则在目录下创建同名文件
        if (targetFile.isDirectory()) {
            targetFile = new File(targetFile, sourceFile.getName());
        }

        // 确保目标文件的父目录存在
        File parentDir = targetFile.getParentFile();
        mkdir(parentDir);

        // 使用NIO的Files.copy方法，支持覆盖已存在的文件
        Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 复制目录（递归处理子文件和子目录）
     *
     * @param sourceDir 源目录
     * @param targetDir 目标目录
     */
    public static void copyDirectory(String sourceDir, String targetDir) throws IOException {
        copyDirectory(new File(sourceDir), new File(targetDir));
    }

    /**
     * 复制目录（递归处理子文件和子目录）
     *
     * @param sourceDir 源目录
     * @param targetDir 目标目录
     */
    public static void copyDirectory(File sourceDir, File targetDir) throws IOException {
        // 如果目标目录不存在，则创建
        mkdir(targetDir);

        // 获取源目录下的所有文件和子目录
        File[] files = sourceDir.listFiles();
        if (files == null) {
            throw new IOException("无法读取目录内容: " + sourceDir.getAbsolutePath());
        }

        // 递归复制每个子文件/子目录
        for (File file : files) {
            File targetFile = new File(targetDir, file.getName());
            if (file.isFile()) {
                copyFile(file, targetFile);
            } else if (file.isDirectory()) {
                copyDirectory(file, targetFile);
            }
        }
    }

    /**
     * 是否文件
     *
     * @param file 文件
     * @return 结果
     */
    public static boolean isFile(String file) {
        if (file == null) {
            return false;
        }
        return Files.isRegularFile(new File(file).toPath());
    }

    /**
     * 是否文件
     *
     * @param file 文件
     * @return 结果
     */
    public static boolean isFile(File file) {
        return file != null && Files.isRegularFile(file.toPath());
    }

    /**
     * 获取unix模式
     *
     * @param path 路径
     * @return 结果
     */
    public static int getUnixMode(Path path) {
        try {
            Set<PosixFilePermission> perms = Files.getPosixFilePermissions(path);
            return posixPermissionsToInt(perms);
        } catch (IOException | UnsupportedOperationException e) {
            // 非 POSIX 系统（如 Windows）的回退逻辑
            int mode = Files.isDirectory(path) ? 0755 : 0644;
            if (Files.isExecutable(path)) {
                mode |= 0111;
            }
            return mode;
        }
    }

    /**
     * 将 POSIX 权限集合转换为八进制模式值（如 "rwxr-xr-x" -> 0755）。
     */
    public static int posixPermissionsToInt(Set<PosixFilePermission> perms) {
        String permStr = PosixFilePermissions.toString(perms); // 如 "rwxr-xr-x"
        int mode = 0;
        if (permStr.charAt(0) == 'r')
            mode |= 0400;
        if (permStr.charAt(1) == 'w')
            mode |= 0200;
        if (permStr.charAt(2) == 'x')
            mode |= 0100;
        if (permStr.charAt(3) == 'r')
            mode |= 0040;
        if (permStr.charAt(4) == 'w')
            mode |= 0020;
        if (permStr.charAt(5) == 'x')
            mode |= 0010;
        if (permStr.charAt(6) == 'r')
            mode |= 0004;
        if (permStr.charAt(7) == 'w')
            mode |= 0002;
        if (permStr.charAt(8) == 'x')
            mode |= 0001;
        return mode;
    }

    /**
     * 统计目录
     *
     * @param file      文件
     * @param fileCount 文件总数
     * @param fileSize  文件大小
     * @param callback  回调函数
     * @param filter    过滤器
     */
    public static void calcDir(File file, LongAdder fileCount, LongAdder fileSize, BiConsumer<LongAdder, LongAdder> callback, Function<File, Boolean> filter) {
        if (file.isFile()) {
            if (filter != null && !filter.apply(file)) {
                return;
            }
            if (fileCount != null) {
                fileCount.add(1);
            }
            if (fileSize != null) {
                fileSize.add(file.length());
            }
            if (callback != null) {
                callback.accept(fileCount, fileSize);
            }
        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File file1 : files) {
                    calcDir(file1, fileCount, fileSize, callback, filter);
                }
            }
        }
    }

    /**
     * 清理目录
     *
     * @param file      文件
     * @param fileCount 文件总数
     * @param fileSize  文件大小
     * @param callback  回调函数
     * @param filter    过滤器
     */
    public static void clearDir(File file, LongAdder fileCount, LongAdder fileSize, BiConsumer<LongAdder, LongAdder> callback, Function<File, Boolean> filter) {
        if (file.isFile()) {
            if (filter != null && !filter.apply(file)) {
                return;
            }
            if (fileCount != null) {
                fileCount.add(1);
            }
            if (fileSize != null) {
                fileSize.add(file.length());
            }
            if (file.delete() && callback != null) {
                callback.accept(fileCount, fileSize);
            }
        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File file1 : files) {
                    clearDir(file1, fileCount, fileSize, callback, filter);
                }
            }
        }
    }
}
