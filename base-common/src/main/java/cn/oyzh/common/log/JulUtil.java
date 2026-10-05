package cn.oyzh.common.log;

import cn.oyzh.common.SysConst;
import cn.oyzh.common.date.DateHelper;
import cn.oyzh.common.file.FileNameUtil;
import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.system.SystemUtil;
import cn.oyzh.common.util.StringUtil;

import java.io.File;

/**
 * jul工具类
 *
 * @author oyzh
 * @since 2024-11-21
 */
public class JulUtil {

    /**
     * 获取日志文件
     *
     * @return 日志文件
     */
    public static File getLogFile() {
        String logFile = System.getProperty("jullog.file");
        if (StringUtil.isNotBlank(logFile)) {
            if (FileUtil.isDirectory(logFile)) {
                throw new RuntimeException("file " + logFile + " is directory");
            }
            if (!FileUtil.exists(logFile)) {
                FileUtil.touch(logFile);
            }
            return new File(logFile);
        }
        String projectName = SysConst.projectName();
        String fileName = DateHelper.formatDate() + ".log";
        // 日志目录
        String filePath = getLogsDir();
        if (StringUtil.isNotBlank(projectName)) {
            fileName = projectName + "-" + fileName;
        }
        File file = new File(FileNameUtil.concat(filePath, fileName));
        if (!file.exists()) {
            FileUtil.touch(file);
        }
        return file;
    }

    /**
     * 获取日志等级
     *
     * @return 日志等级
     */
    public static JulLevel getLogLevel() {
        String level = System.getProperty("jullog.level");
        if (StringUtil.isNotBlank(level)) {
            JulLevel level1 = JulLevel.ofLevel(level);
            if (level1 != null) {
                return level1;
            }
        }
        return JulLevel.DEBUG;
    }

    /**
     * 获取日志目录
     *
     * @return 结果
     */
    public static String getLogsDir() {
        String filePath;
        String baseDir;
        if (StringUtil.isNotBlank(SysConst.tempDir())) {
            baseDir = SysConst.tempDir();
        } else if (StringUtil.isNotBlank(SysConst.storeDir())) {
            baseDir = SysConst.storeDir();
        } else {
            baseDir = SystemUtil.userDir();
        }
        //        // 正式环境
        //        if (JarUtil.isInJar()) {
        filePath = FileNameUtil.concat(baseDir, "logs");
        //            filePath = SysConst.storeDir() + "logs" + File.separator;
        //        } else {// 开发环境
        //                    filePath = SystemUtil.userDir() + File.separator + "logs" + File.separator;
        //        }
        return filePath;
    }
}
