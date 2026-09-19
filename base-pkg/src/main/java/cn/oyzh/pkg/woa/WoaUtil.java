package cn.oyzh.pkg.woa;

import cn.oyzh.common.file.FileNameUtil;
import cn.oyzh.common.log.JulLog;
import cn.oyzh.common.system.OSUtil;
import cn.oyzh.common.system.SystemUtil;
import cn.oyzh.common.util.CollectionUtil;
import cn.oyzh.common.util.IOUtil;
import cn.oyzh.common.util.ResourceUtil;
import cn.oyzh.pkg.util.JModUtil;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author oyzh
 * @since 2026-09-19
 */
public class WoaUtil {

    /**
     * 获取jfx的lib路径
     *
     * @param mod     模块
     * @param jdkPath jdk路径
     * @return 结果
     * @throws Exception 异常
     */
    public static Path getJfxLibPath(String mod, String jdkPath) throws Exception {
        String name = mod.replace(".", "-");
        // 解压jmod
        String modDir = JModUtil.extract(mod + ".jmod", jdkPath);
        Path lib = modDir == null ? null : Path.of(modDir, "lib");
        // 不存在模块路径，则从资源目录获取
        if (lib == null || !Files.exists(lib)) {
            if (isWoa()) {
                JulLog.warn("mod:{} lib is null, find resources lib....", mod);
                lib = Path.of(SystemUtil.tmpdir(), "_jfx_win_arm_libs");
                String libDir = "/jfx/libs/" + name;
                List<String> list = ResourceUtil.listFiles(libDir);
                if (CollectionUtil.isEmpty(list)) {
                    JulLog.warn("mod:{} lib is null, ignore....", mod);
                    return null;
                }
                // 复制文件
                for (String s : list) {
                    InputStream stream = ResourceUtil.getResourceAsStream(libDir + "/" + s);
                    IOUtil.saveToFile(stream, FileNameUtil.concat(lib.toString(), s));
                    IOUtil.close(stream);
                }
            } else {
                JulLog.warn("mod:{} lib is null, ignore....", mod);
            }
        }
        return lib;
    }

    /**
     * 是否windows on arm
     *
     * @return 结果
     */
    public static boolean isWoa() {
        return OSUtil.isWindows() && OSUtil.isAarch64();
    }
}
