package cn.oyzh.common.system;

import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.log.JulLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * ProcessBuilder工具类
 *
 * @author oyzh
 * @since 2025/09/12
 */
public class ProcessBuilderUtil {

    /**
     * 执行
     *
     * @param cmd 命令
     * @return 进程
     * @throws IOException 异常
     */
    public static Process exec(String... cmd) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(cmd);
        return processBuilder.start();
    }

}
