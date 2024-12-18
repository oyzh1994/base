package cn.oyzh.common.util;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.thread.TaskManager;
import lombok.experimental.UtilityClass;

import java.io.IOException;

/**
 * @author oyzh
 * @since 2024-12-17
 */
@UtilityClass
public class ProcessUtil {

    /**
     * 重启应用
     *
     * @param mainClass  主类
     * @param timeout    超时时间
     * @param exitAction 退出操作
     */
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
    public static void restartApplication(String mainClassName, int timeout, Runnable exitAction) {
        TaskManager.startDelay(() -> {
            try {
                // 获取当前Java程序的路径和classpath
                String javaPath = System.getProperty("java.home");
                if (OSUtil.isWindows()) {
                    javaPath += "/bin/javaw.exe";
                } else if (OSUtil.isLinux()) {
                    javaPath += "/bin/javaw";
                } else if (OSUtil.isMacOS()) {
                    javaPath += "/bin/javaw";
                }
                String classPath = System.getProperty("java.class.path");
                // 构建重启命令
                String restartCommand = javaPath + " -cp \"" + classPath + "\" " + mainClassName;
                // 打印命令
                JulLog.info("restartCommand:{}", restartCommand);
                // 执行重启命令
                Runtime.getRuntime().exec(restartCommand);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 退出当前进程
            if (exitAction == null) {
                System.exit(0);
            } else {
                exitAction.run();
            }
        }, timeout);
    }
}
