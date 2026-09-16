package cn.oyzh.common.test;

import org.junit.Test;

/**
 *
 * @author oyzh
 * @since 2026-09-17
 */
public class StringTest {

    @Test
    public void test1(){
        String str="/Users/oyzh/Desktop/Package/EasyShell/1.2.2/EasyShell.app/Contents/runtime/Contents/Home/bin/nested:/Users/oyzh/Desktop/Package/EasyShell/1.2.2/EasyShell.app/Contents/app/easyshell-1.2.2_clip.jar/!BOOT-INF/lib/base-common-1.0.4.jar!";

        System.out.println(str.contains(":/")&&str.contains("/!"));
    }
}
