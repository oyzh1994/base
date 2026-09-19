package cn.oyzh.pkg.test;

import cn.oyzh.common.system.SystemUtil;
import cn.oyzh.pkg.util.WinArmHandler;
import org.junit.Test;

/**
 *
 * @author oyzh
 * @since 2026-09-18
 */
public class WinArmPreHandler {

    private boolean githubAction;

    @Test
    public void run() throws Exception {
        WinArmHandler handler = new WinArmHandler();
        handler.jfxJModToMavenJar();
    }

    public static void main(String[] args) throws Exception {
        WinArmPreHandler pack = new WinArmPreHandler();
        pack.githubAction = SystemUtil.isCIEnv();
        pack.run();
    }
}
