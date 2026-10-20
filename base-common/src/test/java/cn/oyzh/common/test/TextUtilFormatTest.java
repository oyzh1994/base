package cn.oyzh.common.test;

import cn.oyzh.common.util.TextUtil;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TextUtilFormatTest {

    @Test
    public void calculatesTerminalCellWidth() {
        assertEquals(3, TextUtil.getDisplayLen("abc"));
        assertEquals(4, TextUtil.getDisplayLen("中文"));
        assertEquals(1, TextUtil.getDisplayLen("e\u0301"));
        assertEquals(2, TextUtil.getDisplayLen("😀"));
    }

    @Test
    public void formatsEqualWidthColumnsWithoutTabs() {
        List<String> values = List.of(
                "序号", "命令", "版本", "状态", "描述",
                "1)", "get", ">=1.0.0", "正常", "读取节点"
        );
        String output = TextUtil.beautifyFormat(values, 5, 2);
        List<String> lines = output.lines().toList();

        assertEquals(2, lines.size());
        assertFalse(output.contains("\t"));
        assertFalse(lines.stream().anyMatch(line -> line.endsWith(" ")));
        assertEquals(6, TextUtil.getDisplayLen(lines.get(0).substring(0, lines.get(0).indexOf("命令"))));
        assertEquals(12, TextUtil.getDisplayLen(lines.get(0).substring(0, lines.get(0).indexOf("版本"))));
        assertEquals(21, TextUtil.getDisplayLen(lines.get(1).substring(0, lines.get(1).indexOf("正常"))));
        assertEquals(27, TextUtil.getDisplayLen(lines.get(1).substring(0, lines.get(1).indexOf("读取节点"))));
    }
}
