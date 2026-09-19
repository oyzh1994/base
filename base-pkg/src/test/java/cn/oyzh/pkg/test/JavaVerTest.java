package cn.oyzh.pkg.test;

import cn.oyzh.common.system.SystemUtil;
import org.junit.Test;

/**
 *
 * @author oyzh
 * @since 2026-09-18
 */
public class JavaVerTest {

    @Test
    public void test(){
        Runtime.Version version = Runtime.version();
        System.out.println("完整版本: " + version);           // 如 17.0.8+9
        System.out.println("主版本号: " + version.feature());  // 17
        System.out.println("次版本号: " + version.interim());  // 0
        System.out.println("更新版本: " + version.update());   // 8
        System.out.println("补丁版本: " + version.patch());    // 0

    }

    @Test
    public void test1(){
        // 获取完整的版本号，如 "17.0.8" 或 "1.8.0_381"
        String version = System.getProperty("java.version");
        System.out.println("Java 版本: " + version);

        // 获取主版本号，如 "17" 或 "1.8"
        String specVersion = System.getProperty("java.specification.version");
        System.out.println("规范版本: " + specVersion);

        // 获取 JDK 厂商
        String vendor = System.getProperty("java.vendor");
        System.out.println("厂商: " + vendor);

        // 获取 JDK 安装路径
        String home = System.getProperty("java.home");
        System.out.println("安装路径: " + home);

        // 获取 JVM 版本
        String vmVersion = System.getProperty("java.vm.version");
        System.out.println("JVM 版本: " + vmVersion);
    }

    @Test
    public void test2(){
        System.out.println("Java 版本: " + SystemUtil.getJdkVersion());
    }
}
