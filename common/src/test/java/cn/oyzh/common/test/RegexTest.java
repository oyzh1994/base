package cn.oyzh.common.test;

import cn.oyzh.common.util.RegexHelper;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author oyzh
 * @since 2025-02-18
 */
public class RegexTest {

    @Test
    public  void test1() {
        String html = """
        <div id='div1'>
            <span name="span1">
             <h1 h1>Hello</h1>
            </span>
         </div>
        """;
        Pattern pattern = RegexHelper.htmlPattern();
        Matcher matcher = pattern.matcher(html);
        while (matcher.find()) {
            System.out.println("Tag: " + matcher.group(1));
            System.out.println("Content: " + matcher.group(2));
            System.out.println("--------------------");
        }
    }

    @Test
    public  void test2() {
        String html = """
        <div id='div1'>
            <span name="span1">
             <h1 h1>Hello</h1>
            </span>
         </div>
        """;

        // 正则表达式：提取所有标签内容（包括嵌套的标签）
        Pattern tagPattern = Pattern.compile("<([^>]+)>");
        Matcher tagMatcher = tagPattern.matcher(html);

        while (tagMatcher.find()) {
            String tagContent = tagMatcher.group(1);
            System.out.println("标签内容：" + tagContent);

            // 提取属性
            extractAttributes(tagContent);
        }
//        Pattern pattern = RegexHelper.htmlAttrPattern();
//        Matcher matcher = pattern.matcher(html);
//        while (matcher.find()) {
//            System.out.println("Attr: " + matcher.group(1));
//            System.out.println("Value: " + matcher.group(2));
//            System.out.println("--------------------");
//        }
    }

    private static void extractAttributes(String tagContent) {
        // 正则表达式：提取属性及其值
        Pattern attrPattern = Pattern.compile(
                "(\\w+)\\s*=\\s*\"([^\"]*)\"" +  // 匹配形如 name="value"
                        "|(\\w+)\\s*=\\s*'([^']*)'" +  // 匹配形如 name='value'
                        "|(\\w+)"                     // 匹配无值属性（如 disabled）
        );
        Matcher attrMatcher = attrPattern.matcher(tagContent);

        while (attrMatcher.find()) {
            String attrName1 = attrMatcher.group(1);
            String value1    = attrMatcher.group(2);

            String attrName2 = attrMatcher.group(3);
            String value2    = attrMatcher.group(4);

            String attrName3 = attrMatcher.group(5);

            if (attrName1 != null) {
                System.out.println("属性名：" + attrName1 + "，值：" + value1);
            } else if (attrName2 != null) {
                System.out.println("属性名：" + attrName2 + "，值：" + value2);
            } else if (attrName3 != null) {
                System.out.println("属性名：" + attrName3 + "，无值");
            }
        }
    }
}
