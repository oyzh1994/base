package cn.oyzh.pkg.test;


import lu.luz.jzopfli.ZopfliH;
import lu.luz.jzopfli.Zopfli_bin;

import java.io.File;
import java.io.IOException;

public class JzopfliExample {
    public static void main(String[] args) throws IOException {
        // 输入原始 JAR，输出压缩后的 JAR
        File inputJar = new File("C:\\Users\\Administrator\\IdeaProjects\\easyshell\\target\\easyshell-1.2.2.jar");
        File outputJar = new File("C:\\Users\\Administrator\\IdeaProjects\\easyshell\\target\\easyshell-1.2.2_compress1.jar");
        ZopfliH.ZopfliOptions options = new ZopfliH.ZopfliOptions();
        options.verbose = true;
        options.verbose_more = true;
        options.numiterations = 15;
        options.blocksplitting = true;
        options.blocksplittinglast = false;
        options.blocksplittingmax = 15;
        ZopfliH.ZopfliFormat output_type = ZopfliH.ZopfliFormat.ZOPFLI_FORMAT_DEFLATE;
        Zopfli_bin.CompressFile(options, output_type, inputJar.getPath(), outputJar.getPath());
        System.out.println("压缩完成: " + outputJar.length() + " bytes");
    }
}