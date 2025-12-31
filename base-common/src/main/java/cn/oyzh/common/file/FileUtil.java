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
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
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
        return del(new File(file), false);
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
        if (file == null) {
            return false;
        }
        boolean success = false;
        try {
            success = file.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    /**
     * 移动文件到文件夹
     * 3. source为文件，target为目录，移动到target
     *
     * @param source   源文件
     * @param override 是否覆盖
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
     * 移动文件夹
     * 1. source为文件，target为文件，直接覆盖
     * 2. source为目录，target为目录，直接覆盖
     * 3. source为文件，target为目录，移动到target
     *
     * @param source   源文件
     * @param target   目标文件
     * @param override 是否覆盖
     * @return 结果
     * @throws IOException 异常
     */
    public static boolean moveDir(String source, String target, boolean override) throws IOException {
        return moveDir(new File(source), new File(target), override);
    }

    /**
     * 移动文件夹
     * 1. source为文件，target为文件，直接覆盖
     * 2. source为目录，target为目录，直接覆盖
     * 3. source为文件，target为目录，移动到target
     *
     * @param source   源文件
     * @param target   目标文件
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

    public static List<File> getAllFiles(String folder) {
        return getAllFiles(new File(folder));
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

    public static void forceMkdir(File dir) {
        mkdir(dir);
    }

    /**
     * 清空目录
     *
     * @param directory 目录
     * @return 结果
     */
    public static boolean cleanDir(String directory) {
        return cleanDir(new File(directory));
    }

    /**
     * 清空目录
     *
     * @param directory 目录
     * @return 结果
     */
    public static boolean cleanDir(File directory) {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            return true;
        }
        final File[] files = directory.listFiles();
        if (null != files) {
            for (File childFile : files) {
                boolean result = false;
                // 删除目录
                if (childFile.isDirectory()) {
                    result = cleanDir(childFile);
                } else if (childFile.isFile()) {// 删除文件
                    result = del(childFile);
                }
                if (!result) {
                    return false;
                }
            }
        }
        // 删除目录自身
        return del(directory);
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

    public static String tmpPath() {
        return System.getProperty("java.io.tmpdir");
    }

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
            throw new FileNotFoundException("源文件/目录不存在: " + source.getAbsolutePath());
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

}
