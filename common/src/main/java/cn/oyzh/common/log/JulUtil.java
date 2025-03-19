package cn.oyzh.common.log;

import cn.oyzh.common.SysConst;
import cn.oyzh.common.date.DateHelper;
import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.util.JarUtil;
import lombok.experimental.UtilityClass;

import java.io.File;

/**
 * @author oyzh
 * @since 2024-11-21
 */
//@UtilityClass
public class JulUtil {

    /**
     * 获取日志文件
     *
     * @return 日志文件
     */
    public static File getLogFile() {
        String projectName = SysConst.projectName();
        String fileName = DateHelper.formatDate() + ".log";
        String filePath;
        // 正式环境
        if (JarUtil.isInJar()) {
            filePath = SysConst.storeDir() + "logs" + File.separator;
        } else {// 开发环境
            filePath = System.getProperty("user.dir") + File.separator + "logs" + File.separator;
        }
        if (projectName != null) {
            filePath += projectName + "-";
        }
        filePath += fileName;
        File file = new File(filePath);
        if (!file.exists()) {
            FileUtil.touch(file);
        }
        return file;
    }
}
