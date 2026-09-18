package cn.oyzh.pkg.test;

import cn.oyzh.pkg.util.WinArmHandler;
import org.junit.Test;

/**
 *
 * @author oyzh
 * @since 2026-09-18
 */
public class WinArmTest {

    @Test
    public void test() throws Exception {
        WinArmHandler handler=new WinArmHandler();
        handler.jfxJModToMavenJar();
    }
}
