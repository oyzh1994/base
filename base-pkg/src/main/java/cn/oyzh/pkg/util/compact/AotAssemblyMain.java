package cn.oyzh.pkg.util.compact;

import java.nio.file.Path;

/**
 * 独立的后台 AOT 装配入口。
 * <p>
 * 应用退出后由启动器拉起，避免将大缓存的创建和落盘时间计入应用退出流程。
 */
public final class AotAssemblyMain {

    private AotAssemblyMain() {
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.exit(2);
        }
        try {
            System.exit(AotCacheManager.assembleDetachedCache(Path.of(args[0]), args[1]));
        } catch (Exception ex) {
            System.exit(1);
        }
    }
}
