package cn.oyzh.common.test;

import cn.oyzh.common.compress.CompressUtil;
import org.apache.commons.compress.archivers.ArchiveException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author oyzh
 * @since 2025-09-26
 */
public class CompressTest {

    @Test
    public void test1() throws IOException, ArchiveException {
        String f1 = "/Users/oyzh/Desktop/tsxk";
        String f2 = "/Users/oyzh/Desktop/k2";
        String compressFile = "/Users/oyzh/Downloads/123456.tar.gz";
        CompressUtil.compress(List.of(new File(f1), new File(f2)), compressFile, CompressUtil.CompressType.TAR_GZ);
    }

    // @Test
    // public void test2() throws IOException, ArchiveException {
    //     String f1 = "/Users/oyzh/Desktop/tsxk";
    //     String f2 = "/Users/oyzh/Desktop/k2";
    //     String compressFile = "/Users/oyzh/Desktop/123456.tar.gz";
    //     SimpleCompressUtils.compress(List.of(new File(f1), new File(f2)), compressFile, SimpleCompressUtils.CompressType.TAR_GZ);
    // }
    //
    // @Test
    // public void test3() throws IOException, ArchiveException {
    //     String f1 = "/Users/oyzh/Desktop/tsxk";
    //     String f2 = "/Users/oyzh/Desktop/k2";
    //     String compressFile = "/Users/oyzh/Downloads/aaa.tar.gz";
    //     FlatCompressUtils.compress(List.of(new File(f1), new File(f2)), compressFile, FlatCompressUtils.CompressType.TAR_GZ);
    // }
}
