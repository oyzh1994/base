package cn.oyzh.common.log;

import cn.oyzh.common.SysConst;
import cn.oyzh.common.date.DateHelper;
import cn.oyzh.common.file.FileUtil;
import lombok.experimental.UtilityClass;

import java.io.File;

/**
 * @author oyzh
 * @since 2024-11-21
 */
@UtilityClass
public class JulUtil {

    /**
     * 获取日志文件
     *
     * @return 日志文件
     */
    public static File getLogFile() {
        String projectName = SysConst.projectName();
        String fileName = DateHelper.formatDate() + ".log";
        String filePath = System.getProperty("user.dir") + File.separator + "logs" + File.separator;
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
