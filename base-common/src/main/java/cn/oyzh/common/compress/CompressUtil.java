package cn.oyzh.common.compress;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 压缩工具类（压缩类型通过参数指定）
 * 支持格式：ZIP、TAR、TAR_GZ
 */
public class CompressUtil {

    // 压缩类型枚举（明确支持的格式）
    public enum CompressType {
        ZIP,        // ZIP格式
        TAR,        // TAR格式（仅打包）
        TAR_GZ      // TAR+GZIP格式（打包并压缩）
    }

    private static final int BUFFER_SIZE = 8192;

    /**
     * 压缩文件/文件夹到目标归档
     *
     * @param sourceFiles  源文件/文件夹（支持多个）
     * @param targetPath   目标归档文件路径（如：D:/output/result）
     * @param compressType 压缩类型（ZIP/TAR/TAR_GZ）
     * @throws IOException      IO异常
     * @throws ArchiveException 压缩格式异常
     */
    public static void compress(List<File> sourceFiles, String targetPath, CompressType compressType) throws IOException, ArchiveException {
        // 验证源文件是否存在
        for (File file : sourceFiles) {
            if (!file.exists()) {
                throw new FileNotFoundException("源路径不存在: " + file.getPath());
            }
        }
        // 根据压缩类型处理输出流
        try (OutputStream out = Files.newOutputStream(Path.of(targetPath))) {
            switch (compressType) {
                case ZIP:
                    // 处理ZIP格式
                    try (ZipArchiveOutputStream zipOut = new ZipArchiveOutputStream(out)) {
                        zipOut.setUseZip64(Zip64Mode.Always); // 支持大文件
                        addFilesToArchive(zipOut, sourceFiles, "");
                    }
                    break;
                case TAR:
                    // 处理TAR格式（仅打包）
                    try (TarArchiveOutputStream tarOut = new TarArchiveOutputStream(out)) {
                        tarOut.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX); // 支持长文件名
                        addFilesToArchive(tarOut, sourceFiles, "");
                    }
                    break;
                case TAR_GZ:
                    // 处理TAR_GZ格式（先打包再压缩）
                    try (GzipCompressorOutputStream gzipOut = new GzipCompressorOutputStream(out);
                         TarArchiveOutputStream tarOut = new TarArchiveOutputStream(gzipOut)) {
                        tarOut.setLongFileMode(TarArchiveOutputStream.LONGFILE_POSIX);
                        addFilesToArchive(tarOut, sourceFiles, "");
                    }
                    break;
                default:
                    throw new ArchiveException("不支持的压缩类型: " + compressType);
            }
        }
    }

    /**
     * 向归档流添加文件/文件夹（保持目录结构）
     */
    private static void addFilesToArchive(ArchiveOutputStream archiveOut, List<File> sourceFiles, String baseDir)
            throws IOException {
        for (File file : sourceFiles) {
            String entryName = baseDir + file.getName();
            if (file.isDirectory()) {
                // 处理文件夹：添加目录项并递归子文件
                ArchiveEntry entry = archiveOut.createArchiveEntry(file, entryName + "/");
                archiveOut.putArchiveEntry(entry);
                archiveOut.closeArchiveEntry();

                // 递归添加子文件
                File[] children = file.listFiles();
                if (children != null) {
                    addFilesToArchive(archiveOut, new ArrayList<>(List.of(children)), entryName + "/");
                }
            } else {
                // 处理文件：添加文件项并写入内容
                ArchiveEntry entry = archiveOut.createArchiveEntry(file, entryName);
                archiveOut.putArchiveEntry(entry);
                try (InputStream in = Files.newInputStream(file.toPath())) {
                    IOUtils.copy(in, archiveOut, BUFFER_SIZE);
                }
                archiveOut.closeArchiveEntry();
            }
        }
    }

    // 简化方法：压缩单个文件
    public static void compress(String sourcePath, String targetPath, CompressType compressType)
            throws IOException, ArchiveException {
        List<String> sources = new ArrayList<>();
        sources.add(sourcePath);
        compress(List.of(new File(sourcePath)), targetPath, compressType);
    }
}