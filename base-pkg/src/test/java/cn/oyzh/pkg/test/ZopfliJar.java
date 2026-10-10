//package cn.oyzh.pkg.test;
//
//import org.apache.commons.compress.archivers.zip.*;
//import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
//import org.apache.commons.compress.archivers.zip.ZipFile;
//
//import java.io.*;
//import java.io.IOException;
//import java.util.Enumeration;
//
//public class ZopfliJar {
//    public static void recompress(File src, File dst) throws IOException {
//        try (ZipFile zip = ZipFile.builder().setFile(src).get();
//             ZipArchiveOutputStream out = new ZipArchiveOutputStream(dst)) {
//
//            Enumeration<ZipArchiveEntry> entries = zip.getEntries();
//            while (entries.hasMoreElements()) {
//                ZipArchiveEntry entry = entries.nextElement();
//                byte[] data = zip.getInputStream(entry).readAllBytes();
//
//                // 用 Zopfli 压缩（DEFLATE 格式）
//                byte[] compressed = zopfliCompress(data);
//
//                ZipArchiveEntry newEntry = new ZipArchiveEntry(entry.getName());
//                newEntry.setMethod(ZipArchiveEntry.DEFLATED);
//                newEntry.setSize(data.length);          // 原始大小
//                newEntry.setCompressedSize(compressed.length); // 压缩后大小
//                newEntry.setCrc(crc32(data));           // 重新计算 CRC
//
//                out.putArchiveEntry(newEntry);
//                out.write(compressed);
//                out.closeArchiveEntry();
//            }
//        }
//    }
//}