package cn.oyzh.common.test;

import cn.oyzh.common.util.StringUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * StringUtil.split(String, int) 按固定长度切分字符串的单元测试。
 *
 * @author oyzh
 * @since 2026-10-07
 */
public class StringUtilTest {

    /**
     * 入参为 null 时返回 null。
     */
    @Test
    public void testSplitNull() {
        Assert.assertNull(StringUtil.split(null, 2));
    }

    /**
     * 长度非正数时原样返回单元素数组。
     */
    @Test
    public void testSplitNonPositiveLen() {
        Assert.assertArrayEquals(new String[]{"abcde"}, StringUtil.split("abcde", 0));
        Assert.assertArrayEquals(new String[]{"abcde"}, StringUtil.split("abcde", -3));
    }

    /**
     * 能整除时等长切分。
     */
    @Test
    public void testSplitExact() {
        Assert.assertArrayEquals(new String[]{"ab", "cd"}, StringUtil.split("abcd", 2));
        Assert.assertArrayEquals(new String[]{"abc", "def"}, StringUtil.split("abcdef", 3));
    }

    /**
     * 不能整除时最后一段为余数长度。
     */
    @Test
    public void testSplitRemainder() {
        Assert.assertArrayEquals(new String[]{"ab", "cd", "e"}, StringUtil.split("abcde", 2));
    }

    /**
     * 单段情形：长度等于或大于字符串长度。
     */
    @Test
    public void testSplitSingleSegment() {
        Assert.assertArrayEquals(new String[]{"abcde"}, StringUtil.split("abcde", 5));
        Assert.assertArrayEquals(new String[]{"abcde"}, StringUtil.split("abcde", 10));
        Assert.assertArrayEquals(new String[]{"a"}, StringUtil.split("a", 2));
    }

    /**
     * 空字符串返回空数组。
     */
    @Test
    public void testSplitEmpty() {
        Assert.assertArrayEquals(new String[0], StringUtil.split("", 2));
    }
}
