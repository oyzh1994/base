package cn.oyzh.common.system;

import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.log.JulLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author oyzh
 * @since 2023/11/14
 */
//@UtilityClass
public class RuntimeUtil {

    /**
     * 获取处理器数量
     *
     * @return 处理器数量
     */
    public static int processorCount() {
        int count = Runtime.getRuntime().availableProcessors();
        if (count <= 0) {
            count = 1;
        }
        return count;
    }

    /**
     * 执行并等待
     *
     * @param cmd 命令
     * @return 执行结果
     * @throws Exception 异常
     */
    public static int execAndWait(String cmd) throws Exception {
        return execAndWait(cmd, (File) null);
    }

    /**
     * 执行并等待
     *
     * @param cmd 命令
     * @param dir 执行目录
     * @return 执行结果
     * @throws Exception 异常
     */
    public static int execAndWait(String cmd, String dir) throws Exception {
        return execAndWait(cmd, new File(dir));
    }

    /**
     * 执行并等待
     *
     * @param cmd 命令
     * @param dir 执行目录
     * @return 执行结果
     * @throws Exception 异常
     */
    public static int execAndWait(String cmd, File dir) throws Exception {
        Charset charset;
        if (OSUtil.isWindows()) {
            charset = Charset.forName(System.getProperty("sun.jnu.encoding"));
        } else {
            charset = Charset.defaultCharset();
        }
        return execAndWait(cmd, dir, true, true, charset, 15_000);
    }

    /**
     * 执行并等待
     *
     * @param cmd         命令
     * @param dir         执行目录
     * @param printInput  打印输入内容
     * @param printError  打印异常内容
     * @param charset     流字符集
     * @param execTimeout 执行超时
     * @return 执行结果
     * @throws Exception 异常
     */
    public static int execAndWait(String cmd, File dir, boolean printInput, boolean printError, Charset charset, int execTimeout) throws Exception {
        int code = 0;
        try {
            Charset streamCharset = charset == null ? Charset.defaultCharset() : charset;
            JulLog.info("execAndWait start cmd:{} dir:{} printInput:{} printError:{}", cmd, dir, printInput, printError);
            // ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", cmd);
            // if (FileUtil.isDirectory(dir)) {
            //     builder.directory(dir);
            // } else {
            //     builder.directory(null);
            // }
            // builder.redirectErrorStream(true);
            // Process process = builder.start();
            Process process;
            if (FileUtil.isDirectory(dir)) {
                process = Runtime.getRuntime().exec(cmd, null, dir);
            } else {
                process = Runtime.getRuntime().exec(cmd, null);
            }
            // if (process.getInputStream().available() <= 0) {
            //     process.waitFor(1000, TimeUnit.MILLISECONDS);
            // }
            Thread inputThread = null, errorThread = null;
            if (printInput) {
                inputThread = new Thread(() -> {
                    try {
                        if (process.getInputStream().available() > 0) {
                            JulLog.info("process input--->start");
                            // 获取进程的标准输出流
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), streamCharset));
                            // 读取输出并打印到控制台
                            String line;
                            while ((line = reader.readLine()) != null) {
                                JulLog.info(line);
                            }
                            JulLog.info("process input--->end");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                inputThread.start();
            }
            if (printError) {
                errorThread = new Thread(() -> {
                    try {
                        if (process.getErrorStream().available() > 0) {
                            JulLog.error("process error--->start");
                            // 获取进程的标准输出流
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), streamCharset));
                            // 读取输出并打印到控制台
                            String line;
                            while ((line = reader.readLine()) != null) {
                                JulLog.error(line);
                            }
                            JulLog.error("process error--->end");
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                errorThread.start();
            }
            // 等待进程执行完成
            code = process.waitFor();
            if (inputThread != null) {
                inputThread.join();
            }
            if (errorThread != null) {
                errorThread.join();
            }
        } finally {
            JulLog.info("execAndWait finish code:{}", code);
        }
        return code;
    }

    // /**
    //  * 执行并等待
    //  *
    //  * @param cmd        命令
    //  * @param dir        执行目录
    //  * @param printInput 打印输入内容
    //  * @param printError 打印异常内容
    //  * @param charset    流打印字符集
    //  * @return 执行结果
    //  * @throws Exception 异常
    //  */
    // public static int execAndWait1(String cmd, File dir, boolean printInput, boolean printError, Charset charset) throws Exception {
    //     int code = 0;
    //     try {
    //         if (charset == null) {
    //             charset = Charset.defaultCharset();
    //         }
    //
    //         ProcessBuilder builder = new ProcessBuilder(cmd);
    //         if(FileUtil.isDirectory(dir)) {
    //             builder.directory(dir);
    //         }
    //         builder.redirectErrorStream(true);
    //         JulLog.info("execAndWait start cmd:{} dir:{} printInput:{} printError:{}", cmd, dir, printInput, printError);
    //         Process process=builder.start();
    //         if (printInput) {
    //             Charset finalCharset = charset;
    //             inputThread = new Thread(() -> {
    //                 try {
    //                     if (process.getInputStream().available() > 0) {
    //                         JulLog.info("process input--->start");
    //                         // 获取进程的标准输出流
    //                         BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), finalCharset));
    //                         // 读取输出并打印到控制台
    //                         String line;
    //                         while ((line = reader.readLine()) != null) {
    //                             JulLog.info(line);
    //                         }
    //                         JulLog.info("process input--->end");
    //                     }
    //                 } catch (IOException e) {
    //                     throw new RuntimeException(e);
    //                 }
    //             });
    //             inputThread.start();
    //         }
    //         if (printError) {
    //             Charset finalCharset1 = charset;
    //             errorThread = new Thread(() -> {
    //                 try {
    //                     if (process.getErrorStream().available() > 0) {
    //                         JulLog.error("process error--->start");
    //                         // 获取进程的标准输出流
    //                         BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream(), finalCharset1));
    //                         // 读取输出并打印到控制台
    //                         String line;
    //                         while ((line = reader.readLine()) != null) {
    //                             JulLog.error(line);
    //                         }
    //                         JulLog.error("process error--->end");
    //                     }
    //                 } catch (IOException e) {
    //                     throw new RuntimeException(e);
    //                 }
    //             });
    //             errorThread.start();
    //         }
    //         // 等待进程执行完成
    //         code = process.waitFor();
    //         if (inputThread != null) {
    //             inputThread.join();
    //         }
    //         if (errorThread != null) {
    //             errorThread.join();
    //         }
    //     } finally {
    //         JulLog.info("execAndWait finish code:{}", code);
    //     }
    //     return code;
    // }

    public static String execForStr(String... cmdArr) {
        try {
            // 执行命令
            Process process;
            if (cmdArr.length == 1) {
                process = Runtime.getRuntime().exec(cmdArr[0], null);
            } else {
                process = Runtime.getRuntime().exec(cmdArr, null);
            }
            // 读取命令的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            // 等待命令执行完成
            int exitCode = process.waitFor();
            JulLog.debug("Runtime executed with exit code:{}", exitCode);
            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Process exec(String cmd) {
        return exec(cmd, null, null);
    }

    public static Process exec(String cmd, String[] envp, File dir) {
        try {
            // 执行命令
            Process process = Runtime.getRuntime().exec(cmd, envp, dir);
            String line;
            // 读取命令的输出
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder input = new StringBuilder();
            while ((line = inputReader.readLine()) != null) {
                input.append(line);
            }
            if (!input.isEmpty()) {
                JulLog.info("exec result:{}", input.toString());
            }
            // 读取错误的输出
            BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder err = new StringBuilder();
            while ((line = errReader.readLine()) != null) {
                err.append(line);
            }
            if (!err.isEmpty()) {
                JulLog.error("exec error:{}", err.toString());
            }
            return process;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Process exec(String[] cmdArr) {
        return exec(cmdArr, null, null);
    }

    public static Process exec(String[] cmdArr, String[] envp, File dir) {
        try {
            // 执行命令
            Process process = Runtime.getRuntime().exec(cmdArr, envp, dir);
            String line;
            // 读取命令的输出
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder input = new StringBuilder();
            while ((line = inputReader.readLine()) != null) {
                input.append(line);
            }
            if (!input.isEmpty()) {
                JulLog.info("exec result:{}", input.toString());
            }
            // 读取错误的输出
            BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder err = new StringBuilder();
            while ((line = errReader.readLine()) != null) {
                err.append(line);
            }
            if (!err.isEmpty()) {
                JulLog.error("exec error:{}", err.toString());
            }
            return process;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 执行命令
     *
     * @param cmdArr 命令列表
     * @return 结果
     */
    public static int execAndWait(String[] cmdArr) {
        try {
            // 执行命令
            Process process = Runtime.getRuntime().exec(cmdArr, null);
            return process.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public static int getProcessorCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static void addShutdownHook(Thread thread) {
         Runtime.getRuntime().addShutdownHook(thread);
    }
}
