package cn.oyzh.common.system;

import cn.oyzh.common.SysConst;
import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.thread.TaskManager;
import cn.oyzh.common.util.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * 进程工具类
 *
 * @author oyzh
 * @since 2024-12-17
 */
public class ProcessUtil {

    /**
     * 重启应用
     *
     * @param mainClass  主类
     * @param timeout    超时时间
     * @param exitAction 退出操作
     */
    @Deprecated
    public static void restartApplication(Class<?> mainClass, int timeout, Runnable exitAction) {
        restartApplication(mainClass.getName(), timeout, exitAction);
    }

    /**
     * 重启应用
     *
     * @param mainClassName 主类
     * @param timeout       超时时间
     * @param exitAction    退出操作
     */
    @Deprecated
    public static void restartApplication(String mainClassName, int timeout, Runnable exitAction) {
        // 获取当前Java程序的路径和classpath
        String javaPath = System.getProperty("java.home");
        if (OSUtil.isWindows()) {
            if (!FileUtil.exist(javaPath + "/bin/javaw.exe")) {
                javaPath += "/bin/java.exe";
            } else {
                javaPath += "/bin/javaw.exe";
            }
            String classPath = System.getProperty("java.class.path");
            String restartCommand = javaPath + " -jar " + classPath;
            // String restartCommand = javaPath + " -cp \"" + classPath + "\" " + mainClassName;
            // 打印命令
            JulLog.info("restartCommand:{}", restartCommand);
            // 执行重启命令
            RuntimeUtil.exec(restartCommand);
            // 退出当前进程
            TaskManager.startDelay(() -> {
                if (exitAction == null) {
                    System.exit(0);
                } else {
                    exitAction.run();
                }
            }, timeout);
        } else if (OSUtil.isLinux()) {
            // 运行目录
            File dir = new File(javaPath).getParentFile();
            if (!FileUtil.exist(javaPath + "/bin/javaw")) {
                javaPath = dir.getPath() + "/jre/bin/java";
                // javaPath = dir.getPath() + "/./jre/bin/java";
            } else {
                javaPath = dir.getPath() + "/jre/bin/javaw";
                // javaPath = dir.getPath() + "/./jre/bin/javaw";
            }
            String classPath = System.getProperty("java.class.path");
            // 重启命令
            String restartCommand = javaPath + " -jar " + dir.getPath() + "/" + classPath;
            // 打印命令
            JulLog.info("restartCommand:{} dir:{}", restartCommand, dir);
            // 执行重启命令
            RuntimeUtil.exec(restartCommand, null, dir);
            // 退出当前进程
            TaskManager.startDelay(() -> {
                if (exitAction == null) {
                    System.exit(0);
                } else {
                    exitAction.run();
                }
            }, timeout);
        } else if (OSUtil.isMacOS()) {
            // 运行目录
            File dir = new File(javaPath).getParentFile();
            if (!FileUtil.exist(javaPath + "/bin/javaw")) {
                javaPath = dir.getPath() + "/./jre/bin/java";
            } else {
                javaPath = dir.getPath() + "/./jre/bin/javaw";
            }
            String classPath = System.getProperty("java.class.path");
            // 重启命令
            String[] restartCommand = new String[]{javaPath, "-jar", classPath};
            // 打印命令
            JulLog.info("restartCommand:{} dir:{}", Arrays.toString(restartCommand), dir);
            // 执行重启命令
            Process process = RuntimeUtil.exec(restartCommand);
            try {
                JulLog.info("exitCode:{}", process.waitFor());
                // 退出当前进程
                TaskManager.startDelay(() -> {
                    if (exitAction == null) {
                        System.exit(0);
                    } else {
                        exitAction.run();
                    }
                }, timeout);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 重启应用
     * 适用于app镜像
     *
     * @param timeout    超时时间
     * @param exitAction 退出操作
     */
    public static void restartApplication(int timeout, Runnable exitAction) throws IOException {
        // 获取当前Java程序的路径和classpath
        String javaPath = System.getProperty("java.home");
        // 工作目录
        File dir = new File(javaPath).getParentFile();
        // windows平台
        if (OSUtil.isWindows()) {
            if (!FileUtil.exist(javaPath + "/bin/javaw.exe")) {
                javaPath += "/bin/java.exe";
            } else {
                javaPath += "/bin/javaw.exe";
            }
            // 类路径
            String classPath = System.getProperty("java.class.path");
            // 构建重启命令
            ProcessBuilder builder = new ProcessBuilder("cmd", "/c", "start", javaPath, "-jar", classPath);
            // 设置运行目录
            builder.directory(dir);
            // 打印命令
            JulLog.info("restartCommand:{} dir:{}", Arrays.toString(builder.command().toArray()), dir);
            // 执行重启命令
            builder.start();
        } else if (OSUtil.isLinux()) {
            if (!FileUtil.exist(javaPath + "/bin/javaw")) {
                javaPath += "/bin/java";
            } else {
                javaPath += "/bin/javaw";
            }
            // 类路径
            String classPath = System.getProperty("java.class.path");
            // 构建重启命令
            ProcessBuilder builder = new ProcessBuilder("nohup", javaPath, "-jar", classPath, "&");
            // 设置运行目录
            builder.directory(dir);
            // 打印命令
            JulLog.info("restartCommand:{} dir:{}", Arrays.toString(builder.command().toArray()), dir);
            // 执行重启命令
            builder.start();
        } else if (OSUtil.isMacOS()) {
            // 可执行程序路径
            String execPath = SysConst.projectName();
            File macOS = new File(dir.getParentFile(), "MacOS");
            File[] files = macOS.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.getName().contains(".")) {
                        execPath = file.getPath();
                        break;
                    }
                }
            }
            // 构建重启命令
            ProcessBuilder builder = new ProcessBuilder("nohup", execPath, "&");
            // 打印命令
            JulLog.info("restartCommand:{} dir:{}", Arrays.toString(builder.command().toArray()), dir);
            // 执行重启命令
            builder.start();
        }
        // 退出当前进程
        TaskManager.startDelay(Objects.requireNonNullElseGet(exitAction, () -> () -> System.exit(0)), timeout);
    }

    /**
     * 判断是否运行在appImage格式下
     *
     * @return 结果
     */
    public static boolean isRunningInAppImage() {
        // 检查 APPIMAGE 或 APPDIR 环境变量是否存在
        String appImagePath = System.getenv("APPIMAGE");
        String appDir = System.getenv("APPDIR");
        return (appImagePath != null && !appImagePath.isEmpty())
                || (appDir != null && !appDir.isEmpty());
    }

    /**
     * 重启应用
     * 适用于安装包
     *
     * @param timeout    超时时间
     * @param exitAction 退出操作
     */
    public static void restartApplication2(int timeout, Runnable exitAction) throws IOException {
        // 获取当前Java程序的路径和classpath
        String javaPath = System.getProperty("java.home");
        // 工作目录
        File dir = new File(javaPath).getParentFile();
        // windows平台
        if (OSUtil.isWindows()) {
            // 可执行程序路径
            String execPath = null;
            int index = 0;
            File pFile = dir;
            while (pFile != null && index <= 5) {
                File file = new File(pFile, SysConst.projectName() + ".exe");
                if (file.exists()) {
                    execPath = file.getPath();
                    break;
                }
                index++;
                pFile = pFile.getParentFile();
            }
            if (execPath != null) {
                // 构建重启命令
                ProcessBuilder builder = new ProcessBuilder("cmd", "/c", execPath);
                // 设置运行目录
                builder.directory(dir);
                // 打印命令
                JulLog.info("restartCommand:{} dir:{}", Arrays.toString(builder.command().toArray()), dir);
                // 执行重启命令
                builder.start();
            } else {
                JulLog.warn("未找到程序路径，执行重启失败！");
            }
        } else if (OSUtil.isLinux()) {
            ProcessBuilder builder;
            // 运行在appImage格式中
            if (isRunningInAppImage()) {
                JulLog.info("running in AppImage...");
                String appImagePath = System.getenv("APPIMAGE");
                // 工作目录
                dir = new File(appImagePath).getParentFile();
                // 构建重启命令
                builder = new ProcessBuilder("nohup", appImagePath, "&");
                Map<String, String> env = builder.environment();
                env.put("LD_LIBRARY_PATH", "/path/to/appimage/libs:" + env.getOrDefault("LD_LIBRARY_PATH", ""));
                // 设置运行目录
                builder.directory(dir);
            } else {
                if (!FileUtil.exist(javaPath + "/bin/javaw")) {
                    javaPath += "/bin/java";
                } else {
                    javaPath += "/bin/javaw";
                }
                // 类路径
                String classPath = System.getProperty("java.class.path");
                // 构建重启命令
                builder = new ProcessBuilder("nohup", javaPath, "-jar", classPath, "&");
                // 设置运行目录
                builder.directory(dir);
            }
            // 打印命令
            JulLog.info("restartCommand:{} dir:{}", Arrays.toString(builder.command().toArray()), dir);
            // 执行重启命令
            builder.start();
        } else if (OSUtil.isMacOS()) {
            // 可执行程序路径
            String execPath = null;
            int index = 0;
            File pFile = dir.getParentFile();
            while (pFile != null && index <= 5) {
                File file = new File(pFile, "MacOS");
                File file1 = new File(file, SysConst.projectName());
                if (file.exists() && file1.exists()) {
                    execPath = file1.getPath();
                    break;
                }
                index++;
                pFile = pFile.getParentFile();
            }
            if (execPath != null) {
                // 构建重启命令
                ProcessBuilder builder = new ProcessBuilder("nohup", execPath, "&");
                // 打印命令
                JulLog.info("restartCommand:{} dir:{}", Arrays.toString(builder.command().toArray()), dir);
                // 执行重启命令
                builder.start();
            } else {
                JulLog.warn("未找到程序路径，执行重启失败！");
            }
        }
        // 退出当前进程
        TaskManager.startDelay(Objects.requireNonNullElseGet(exitAction, () -> () -> System.exit(0)), timeout);
    }

    /**
     * 检查指定名称的进程是否正在运行
     *
     * @param processName 要检查的进程名，需包含 .exe 后缀
     * @return 如果进程正在运行返回 true，否则返回 false
     */
    public static boolean isProcessRunning(String... processName) {
        try {
            if (OSUtil.isWindows()) {
                // 创建 ProcessBuilder 来执行 tasklist 命令
                ProcessBuilder processBuilder = new ProcessBuilder("tasklist");
                Process process = processBuilder.start();
                // 获取命令执行结果的输入流
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    // 检查当前行是否包含指定的进程名
                    if (StringUtil.containsAny(line, processName)) {
                        reader.close();
                        return true;
                    }
                }
                // 关闭 BufferedReader
                reader.close();
            } else if (OSUtil.isMacOS()) {
                // 创建 ProcessBuilder 实例，执行 ps -ef 命令
                ProcessBuilder processBuilder = new ProcessBuilder("ps", "-ef");
                // 启动进程
                Process process = processBuilder.start();
                // 获取进程的输入流，用于读取命令输出
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                // 逐行读取命令输出
                while ((line = reader.readLine()) != null) {
                    // 检查当前行是否包含指定的进程名
                    if (StringUtil.containsAny(line, processName)) {
                        // 排除 ps -ef 命令本身和 grep 命令的输出，避免误判
                        if (!line.contains("ps -ef") && !line.contains("grep")) {
                            return true;
                        }
                    }
                }
                // 关闭输入流
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 关闭指定名称的进程
     *
     * @param processName 要关闭的进程名
     * @return 如果成功关闭进程返回 true，否则返回 false
     */
    public static boolean killProcess(String processName) {
        try {
            if (OSUtil.isWindows()) {
                // 创建 ProcessBuilder 来执行 taskkill 命令强制终止进程
                ProcessBuilder processBuilder = new ProcessBuilder("taskkill", "/F", "/IM", processName);
                Process process = processBuilder.start();
                // 等待命令执行完成并获取退出状态码
                int exitCode = process.waitFor();
                // 退出状态码为 0 表示命令执行成功
                return exitCode == 0;
            }
            if (OSUtil.isLinux() || OSUtil.isMacOS()) {
                // 创建 ProcessBuilder 来执行 killall 命令强制终止进程
                ProcessBuilder processBuilder = new ProcessBuilder("killall", "-9", processName);
                Process process = processBuilder.start();
                // 等待命令执行完成并获取退出状态码
                int exitCode = process.waitFor();
                // 退出状态码为 0 表示命令执行成功
                return exitCode == 0;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 关闭指定的进程
     *
     * @param pid 要关闭的进程id
     * @return 如果成功关闭进程返回 true，否则返回 false
     */
    public static boolean killProcess(long pid) {
        try {
            if (OSUtil.isWindows()) {
                // 创建 ProcessBuilder 来执行 taskkill 命令强制终止进程
                ProcessBuilder processBuilder = new ProcessBuilder("taskkill", "/F", "/PID", pid + "");
                Process process = processBuilder.start();
                // 等待命令执行完成并获取退出状态码
                int exitCode = process.waitFor();
                // 退出状态码为 0 表示命令执行成功
                return exitCode == 0;
            }
            if (OSUtil.isLinux() || OSUtil.isMacOS()) {
                // 创建 ProcessBuilder 来执行 kill 命令强制终止进程
                ProcessBuilder processBuilder = new ProcessBuilder("kill", "-9", pid + "");
                Process process = processBuilder.start();
                // 等待命令执行完成并获取退出状态码
                int exitCode = process.waitFor();
                // 退出状态码为 0 表示命令执行成功
                return exitCode == 0;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
