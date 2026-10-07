package cn.oyzh.common.test;

import cn.oyzh.common.util.ClassUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.function.Predicate;

/**
 * 测试类路径扫描工具 ClassUtil 的类扫描能力。
 *
 * @author oyzh
 * @since 2026-05-12
 */
public class ClassUtilTest {

    @Test
    public void test1() throws IOException, ClassNotFoundException {
        ClassUtil.scanClasses("cn.oyzh", new Predicate<Class<?>>() {
            @Override
            public boolean test(Class<?> aClass) {
                return false;
            }
        });
    }
}
