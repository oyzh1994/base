package cn.oyzh.pkg.test;

import org.apache.commons.compress.archivers.zip.*;
import java.io.*;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

public class JarRecompressor {

    public static void recompress(File src, File dst) throws IOException {
        try (ZipFile zip = ZipFile.builder().setFile(src).get();
             ZipArchiveOutputStream out = new ZipArchiveOutputStream(dst)) {

            out.setEncoding("UTF-8");

            Enumeration<ZipArchiveEntry> entries = zip.getEntries();
            while (entries.hasMoreElements()) {
                ZipArchiveEntry entry = entries.nextElement();

                // 目录条目：必须保持 STORED、大小 0
                if (entry.isDirectory()) {
                    ZipArchiveEntry dir = new ZipArchiveEntry(entry.getName());
                    dir.setMethod(ZipArchiveEntry.STORED);
                    dir.setSize(0);
                    dir.setCompressedSize(0);
                    dir.setCrc(0);
                    dir.setTime(entry.getTime());
                    out.putArchiveEntry(dir);
                    out.closeArchiveEntry();
                    continue;
                }

                byte[] data = zip.getInputStream(entry).readAllBytes();

                // 已经是 STORED 的条目（如嵌套 jar）保持原样，避免破坏结构
                if (entry.getMethod() == ZipArchiveEntry.STORED) {
                    ZipArchiveEntry stored = new ZipArchiveEntry(entry.getName());
                    stored.setMethod(ZipArchiveEntry.STORED);
                    stored.setSize(data.length);
                    stored.setCompressedSize(data.length);
                    stored.setCrc(crc32(data));
                    stored.setTime(entry.getTime());
                    out.putArchiveEntry(stored);
                    out.write(data);
                    out.closeArchiveEntry();
                    continue;
                }

                // 其他条目：重新压缩
                byte[] compressed = deflate(data);
                ZipArchiveEntry deflated = new ZipArchiveEntry(entry.getName());
                deflated.setMethod(ZipArchiveEntry.DEFLATED);
                deflated.setSize(data.length);
                deflated.setCompressedSize(compressed.length);
                deflated.setCrc(crc32(data));
                deflated.setTime(entry.getTime());

                out.putArchiveEntry(deflated);
                out.write(compressed);
                out.closeArchiveEntry();
            }
        }
    }

    private static long crc32(byte[] data) {
        CRC32 crc = new CRC32();
        crc.update(data);
        return crc.getValue();
    }

    private static byte[] deflate(byte[] data) {
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION, true);
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        byte[] buf = new byte[8192];
        while (!deflater.finished()) {
            int n = deflater.deflate(buf);
            if (n > 0) bos.write(buf, 0, n);
        }
        deflater.end();
        return bos.toByteArray();
    }

    public static void main(String[] args) throws IOException {
        File f1= new File("C:\\Users\\Administrator\\IdeaProjects\\easyshell\\target\\easyshell-1.2.2.jar");
        File f2 = new File("C:\\Users\\Administrator\\IdeaProjects\\easyshell\\target\\easyshell-1.2.2_compress3.jar");

        recompress(f1,
                f2);
    }
}