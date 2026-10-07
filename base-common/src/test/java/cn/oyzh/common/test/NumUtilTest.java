package cn.oyzh.common.test;

import cn.oyzh.common.util.NumberUtil;
import org.junit.Test;

/**
 * 测试数值范围校验工具 NumberUtil.checkBound 的边界判断。
 *
 * @author oyzh
 * @since 2024-08-14
 */
public class NumUtilTest {

    @Test
    public void test1() {
        System.out.println(NumberUtil.checkBound(5, 10, 5, 10));
        System.out.println(NumberUtil.checkBound(5, 10, 6, 9));
        System.out.println(NumberUtil.checkBound(5, 10, 4, 9));
        System.out.println(NumberUtil.checkBound(5, 10, 4, 10));
        System.out.println(NumberUtil.checkBound(5, 10, 5, 11));
        System.out.println(NumberUtil.checkBound(5, 10, 0, 4));
    }

    @Test
    public void test2() {
        System.out.println(NumberUtil.checkBound(168, 184, 159, 194));
    }

    @Test
    public void test3() {
        System.out.println(NumberUtil.checkBound(5, 10, 0, 4));
    }

    @Test
    public void test4() {
        System.out.println(NumberUtil.checkBound(5, 10, 11, 15));
    }
}
