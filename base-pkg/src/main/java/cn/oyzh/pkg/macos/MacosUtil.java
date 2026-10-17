package cn.oyzh.pkg.macos;

import cn.oyzh.common.file.FileNameUtil;
import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.system.OSUtil;
import cn.oyzh.common.system.SystemUtil;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.common.util.IOUtil;
import cn.oyzh.common.util.ResourceUtil;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

/**
 * windows on arm 工具类
 *
 * @author oyzh
 * @since 2026-09-19
 */
public class MacosUtil {

    /**
     * 获取jfx的lib路径
     *
     * @param mod     模块
     * @return 结果
     * @throws Exception 异常
     */
    public static Path getJfxLibPath(String mod) throws Exception {
        String name = mod.replace(".", "-");
        Path lib =null;
        if (isMacos_x64()) {
            JulLog.warn("mod:{} lib is null, find resources lib....", mod);
            String libDir = "/jfx/libs/macos_x64/" + name;
            List<String> list = ResourceUtil.listFiles(libDir);
            if (CollectionUtil.isEmpty(list)) {
                return null;
            }
            lib = Path.of(SystemUtil.tmpdir(), "_jfx_libs");
            // 复制文件
            for (String s : list) {
                InputStream stream = ResourceUtil.getResourceAsStream(libDir + "/" + s);
                IOUtil.saveToFile(stream, FileNameUtil.concat(lib.toString(), s));
                IOUtil.close(stream);
            }
        }
        return lib;
    }

    /**
     * 是否windows on arm
     *
     * @return 结果
     */
    public static boolean isMacos_x64() {
        return OSUtil.isMacOS() && OSUtil.isX64();
    }
}
