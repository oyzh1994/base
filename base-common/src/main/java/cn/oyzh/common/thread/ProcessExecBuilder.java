package cn.oyzh.common.thread;

import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.system.OSUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 进程执行构建类
 *
 * @author oyzh
 * @since 2025-02-11
 */
public class ProcessExecBuilder {

    private int timeout;

    private File directory;

    private Charset charset;

    private boolean catchInput = true;

    private boolean catchError = true;

    private List<String> command;

    {
        if (OSUtil.isWindows()) {
            this.charset = Charset.forName(System.getProperty("sun.jnu.encoding"));
        } else {
            this.charset = Charset.defaultCharset();
        }
    }

    public ProcessExecBuilder command(List<String> command) {
        this.command = command;
        return this;
    }

    public ProcessExecBuilder command(String... command) {
        this.command = List.of(command);
        return this;
    }

    public ProcessExecBuilder command(String command) {
        this.command = List.of(command);
        return this;
    }

    public ProcessExecBuilder directory(String directory) {
        this.directory = new File(directory);
        return this;
    }

    public ProcessExecBuilder directory(File directory) {
        this.directory = directory;
        return this;
    }

    public ProcessExecBuilder timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public ProcessExecBuilder charset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public ProcessExecBuilder catchInput(boolean catchInput) {
        this.catchInput = catchInput;
        return this;
    }

    public ProcessExecBuilder catchError(boolean catchError) {
        this.catchError = catchError;
        return this;
    }

    public Process build() throws IOException {
        if (this.command == null || this.command.isEmpty()) {
            throw new IllegalArgumentException("command is empty");
        }
        Process process;
        if (FileUtil.isDirectory(this.directory)) {
            if (this.command.size() > 1) {
                process = Runtime.getRuntime().exec(this.command.toArray(new String[]{}), null, this.directory);
            } else {
                process = Runtime.getRuntime().exec(this.command.getFirst(), null, this.directory);
            }
        } else {
            if (this.command.size() > 1) {
                process = Runtime.getRuntime().exec(this.command.toArray(new String[]{}), null, null);
            } else {
                process = Runtime.getRuntime().exec(this.command.getFirst(), null, null);
            }
        }
        return process;
    }

    /**
     * 抓取正常输出
     *
     * @param process 进程
     * @return 输出
     * @throws IOException 异常
     */
    private String doCatchInput(Process process) throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStream stream = process.getInputStream();
        if (OSUtil.isWindows() || stream.available() > 0) {
            JulLog.info("process input--->start");
            // 获取进程的标准输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, this.charset));
            // 读取输出并打印到控制台
            while (process.isAlive() || stream.available() > 0) {
                String line = reader.readLine();
                if (line != null) {
                    JulLog.info(line);
                    builder.append(line);
                }
            }
            stream.close();
            reader.close();
            JulLog.info("process input--->end");
        }
        return builder.toString();
    }

    /**
     * 抓取异常输出
     *
     * @param process 进程
     * @return 输出
     * @throws IOException 异常
     */
    private String doCatchError(Process process) throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStream stream = process.getErrorStream();
        if (OSUtil.isWindows() || stream.available() > 0) {
            JulLog.error("process error--->start");
            // 获取进程的标准输出流
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, this.charset));
            // 读取输出并打印到控制台
            while (process.isAlive() || stream.available() > 0) {
                String line = reader.readLine();
                if (line != null) {
                    JulLog.error(line);
                    builder.append(line);
                }
            }
            stream.close();
            reader.close();
            JulLog.error("process error--->end");
        }
        return builder.toString();
    }

    /**
     * 执行命令
     *
     * @return 结果
     * @throws IOException          异常
     * @throws InterruptedException 异常
     */
    public ProcessExecResult exec() throws IOException, InterruptedException {
        Process process = this.build();
        ProcessExecResult execResult = new ProcessExecResult();
        // 等待进程执行完成
        // 已设置超时时间
        if (this.timeout > 0) {
            boolean waitFor = process.waitFor(this.timeout, TimeUnit.MILLISECONDS);
            // 抓取正常输出
            if (this.catchInput) {
                execResult.setInput(this.doCatchInput(process));
            }
            // 抓取异常输出
            if (this.catchError) {
                execResult.setInput(this.doCatchError(process));
            }
            if (!waitFor) {
                process.destroyForcibly();
                execResult.setTimedOut(true);
            }
            // 设置执行结果
            execResult.setExitCode(process.exitValue());
        } else {// 未设置超时时间
            int code = process.waitFor();
            // 抓取正常输出
            if (this.catchInput) {
                execResult.setInput(this.doCatchInput(process));
            }
            // 抓取异常输出
            if (this.catchError) {
                execResult.setInput(this.doCatchError(process));
            }
            // 设置执行结果
            execResult.setExitCode(code);
        }
        return execResult;
    }

    public String execForInput() throws IOException, InterruptedException {
        ProcessExecResult execResult = this.exec();
        return execResult == null ? null : execResult.getInput();
    }

    public void execAsync() throws IOException {
        Process process = this.build();
        Thread thread = new Thread(() -> {
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    public static ProcessExecBuilder newBuilder() {
        return new ProcessExecBuilder();
    }

    public static ProcessExecBuilder newBuilder(StringBuilder command) {
        return newBuilder(command.toString());
    }

    public static ProcessExecBuilder newBuilder(String command) {
        ProcessExecBuilder builder = new ProcessExecBuilder();
        builder.command(command);
        return builder;
    }
}
