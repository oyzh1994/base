package cn.oyzh.pkg.test;

import cn.oyzh.common.system.SystemUtil;
import cn.oyzh.pkg.woarm.WinArmHandler;
import cn.oyzh.pkg.woarm.WinArmHandler2;
import org.junit.Test;

/**
 *
 * @author oyzh
 * @since 2026-09-18
 */
public class WinArmPreHandler {

    private String jfxVersion;

    private boolean githubAction;

    @Test
    public void run() throws Exception {
        WinArmHandler handler = new WinArmHandler();
        handler.setJfxVersion(this.jfxVersion);
        handler.run();
    }

    @Test
    public void run2() throws Exception {
        WinArmHandler2 handler = new WinArmHandler2();
        handler.setJfxVersion(this.jfxVersion);
        handler.run();
    }

    public static void main(String[] args) throws Exception {
        WinArmPreHandler pack = new WinArmPreHandler();
        if (args != null && args.length > 0 && args[0] != null) {
            pack.jfxVersion = args[0];
        }
        pack.githubAction = SystemUtil.isCIEnv();
        pack.run2();
    }
}
