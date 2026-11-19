package cn.oyzh.common.test;

import cn.oyzh.common.util.TextUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * TextUtil.unescape 单元测试。
 *
 * @author oyzh
 * @since 2026-11-20
 */
public class TextUtilEscapeTest {

    @Test
    public void unescapesCommonSequences() {
        Assert.assertEquals("{\"id\":1}", TextUtil.unescape("{\\\"id\\\":1}"));
        Assert.assertEquals("a\nb\tc\r\f\b", TextUtil.unescape("a\\nb\\tc\\r\\f\\b"));
        Assert.assertEquals("a/b\\c\"d", TextUtil.unescape("a\\/b\\\\c\\\"d"));
        Assert.assertEquals("中文", TextUtil.unescape("\\u4E2D\\u6587"));
    }

    @Test
    public void preservesIncompleteOrUnknownSequences() {
        Assert.assertEquals("\\u12", TextUtil.unescape("\\u12"));
        Assert.assertEquals("\\u+123", TextUtil.unescape("\\u+123"));
        Assert.assertEquals("\\x", TextUtil.unescape("\\x"));
        Assert.assertEquals("abc\\", TextUtil.unescape("abc\\"));
        Assert.assertNull(TextUtil.unescape(null));
        Assert.assertEquals("plain", TextUtil.unescape("plain"));
    }

    @Test
    public void roundTripsEscapedDisplayText() {
        String text = "a\"b\nc\td";
        Assert.assertEquals(text, TextUtil.unescape(TextUtil.escape(text)));
    }
}
