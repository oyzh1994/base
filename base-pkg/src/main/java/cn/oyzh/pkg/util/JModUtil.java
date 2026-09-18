package cn.oyzh.pkg.util;

import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.system.RuntimeUtil;
import cn.oyzh.common.system.SystemUtil;
import cn.oyzh.common.thread.ProcessExecResult;
import cn.oyzh.common.util.StringUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JModUtil {


    public static String extract(String modeName,String jdkPath) throws Exception {
        String javaHome = SystemUtil.javaHome();
        Path path = Paths.get(javaHome, "jmods", modeName);
        // 检查jmods文件是否存在
        if (Files.exists(path)) {
            String modDir = path.toFile().getName();
            modDir = modDir.substring(0, modDir.lastIndexOf("."));
            Path path1 = Paths.get(javaHome, "jmods", modDir);
            String[] cmd = PkgUtil.getJModCMD(path1.toString(), path.toString());
            cmd = PkgUtil.getJDKExecCMD(jdkPath, cmd);
            String cmdStr = StringUtil.join(" ", cmd);
            JulLog.info("JMod cmd:{}", "\n" + cmdStr);
            ProcessExecResult result = RuntimeUtil.execForResult(cmd);
            JulLog.info("JMod result:{}", result);
            if (!result.isSuccess()) {
                JulLog.error("JMod error:{}", result.getError());
                throw new Exception("JMod error:" + result.getError());
            }
            return path1.toString();
        }
        return null;
    }
}
