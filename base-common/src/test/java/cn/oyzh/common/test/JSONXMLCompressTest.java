package cn.oyzh.common.test;

import cn.oyzh.common.json.JSONUtil;
import cn.oyzh.common.xml.XMLUtil;
import com.alibaba.fastjson2.JSON;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * JSON/XML压缩正确性与基准测试。
 *
 * @author oyzh
 * @since 2026-10-24
 */
public class JSONXMLCompressTest {

    private static final long BENCH_BUDGET_MS = 400;

    @FunctionalInterface
    private interface TextOp {
        String apply(String input);
    }

    @Test
    public void compressJson() {
        String json = "{\n" +
                "  \"text\": \"  keep spaces \\\" quoted \\\\ slash  \",\n" +
                "  \"empty\": \"\",\n" +
                "  \"chars\": \"tab\\tand\\nnewline\",\n" +
                "  \"unicode\": \"中文测试 😀\",\n" +
                "  \"number\": 123.4500e-6,\n" +
                "  \"nested\": [ 1, 2, { \"a\" : true, \"b\" : null } ]\n" +
                "}\n";
        String compressed = JSONUtil.toCompress(json);
        assertFalse(JSONUtil.toCompress("\uFEFF" + compressed).startsWith("\uFEFF"));
        assertFalse(compressed.contains("\n"));
        assertFalse(compressed.contains("\t"));
        assertFalse(compressed.contains(": "));
        assertEquals(JSON.parse(json), JSON.parse(compressed));
        // 字符串内的空白必须原样保留
        assertEquals("  keep spaces \" quoted \\ slash  ", JSON.parseObject(compressed).getString("text"));
        assertEquals("tab\tand\nnewline", JSON.parseObject(compressed).getString("chars"));
        assertEquals("中文测试 😀", JSON.parseObject(compressed).getString("unicode"));
        // 幂等
        assertEquals(compressed, JSONUtil.toCompress(compressed));
    }

    @Test
    public void compressXml() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
                "<plist version=\"1.0\">\n" +
                "\t<dict>\n" +
                "\t\t<key>name</key>\n" +
                "\t\t<string>  keep spaces  </string>\n" +
                "\t\t<key>pattern</key>\n" +
                "\t\t<string>^\\s*foo\\s+bar$</string>\n" +
                "\t\t<key>multiline</key>\n" +
                "\t\t<string>line1\nline2</string>\n" +
                "\t\t<key>spaces</key>\n" +
                "\t\t<string>   </string>\n" +
                "\t\t<key>attrs</key>\n" +
                "\t\t<dict  a = \"x  y\"   b='1' >\n" +
                "\t\t\t<key>data</key>\n" +
                "\t\t\t<string><![CDATA[  cdata  <raw>  ]]></string>\n" +
                "\t\t</dict>\n" +
                "\t\t<!-- keep comment -->\n" +
                "\t</dict>\n" +
                "</plist>\n";
        String compressed = XMLUtil.toCompress(xml);
        assertTrue(compressed.length() < xml.length());
        // 保留声明、DOCTYPE、注释、CDATA
        assertTrue(compressed.startsWith("<?xml"));
        assertTrue(compressed.contains("<!DOCTYPE plist"));
        assertTrue(compressed.contains("<!-- keep comment -->"));
        assertTrue(compressed.contains("<![CDATA[  cdata  <raw>  ]]>"));
        // 语义等价：叶子文本、属性和注释保持一致
        assertEquals(leafValues(xml), leafValues(compressed));
        assertEquals(comments(xml), comments(compressed));
        // 幂等
        assertEquals(compressed, XMLUtil.toCompress(compressed));
        assertEquals(compressed, XMLUtil.compress(compressed));
    }

    @Test
    public void benchmarkJson() {
        String json = buildLargeJson();
        bench("JSON", json, JSONUtil::toCompress);
        String compressed = JSONUtil.toCompress(json);
        assertEquals(JSON.parse(json), JSON.parse(compressed));
    }

    @Test
    public void benchmarkXml() {
        String xml = buildLargeXml();
        bench("XML", xml, XMLUtil::toCompress);
        try {
            assertEquals(leafValues(xml), leafValues(XMLUtil.toCompress(xml)));
        } catch (Exception ex) {
            throw new AssertionError(ex);
        }
    }

    private static void bench(String name, String input, TextOp op) {
        // 预热
        for (int i = 0; i < 3; i++) {
            op.apply(input);
        }
        long bytes = input.getBytes(StandardCharsets.UTF_8).length;
        long iterations = 0;
        long outputBytes = 0;
        long start = System.nanoTime();
        do {
            outputBytes = op.apply(input).getBytes(StandardCharsets.UTF_8).length;
            iterations++;
        } while (System.nanoTime() - start < BENCH_BUDGET_MS * 1_000_000L);
        double seconds = (System.nanoTime() - start) / 1_000_000_000.0;
        double mbps = bytes * (double) iterations / seconds / (1024 * 1024);
        double ratio = 1 - outputBytes / (double) bytes;
        System.out.printf(java.util.Locale.ROOT,
                "COMPRESS_BENCH\t%s\tinput=%.1fKB\toutput=%.1fKB\tratio=%.1f%%\titerations=%d\tthroughput=%.1fMB/s%n",
                name, bytes / 1024.0, outputBytes / 1024.0, ratio * 100, iterations, mbps);
    }

    private static String buildLargeJson() {
        StringBuilder builder = new StringBuilder(512 * 1024);
        builder.append("{\n  \"name\": \"benchmark\",\n  \"items\": [\n");
        for (int i = 0; i < 3000; i++) {
            if (i > 0) {
                builder.append(",\n");
            }
            builder.append("    {\n");
            builder.append("      \"id\": ").append(i).append(",\n");
            builder.append("      \"name\": \"item ").append(i).append(" with spaces\",\n");
            builder.append("      \"enabled\": ").append(i % 2 == 0).append(",\n");
            builder.append("      \"score\": ").append(i * 1.25).append(",\n");
            builder.append("      \"tags\": [ \"alpha\", \"beta\", \"中文\" ]\n");
            builder.append("    }");
        }
        builder.append("\n  ]\n}\n");
        return builder.toString();
    }

    private static String buildLargeXml() {
        StringBuilder builder = new StringBuilder(512 * 1024);
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        builder.append("<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n");
        builder.append("<plist version=\"1.0\">\n<dict>\n");
        for (int i = 0; i < 3000; i++) {
            builder.append("\t<key>item").append(i).append("</key>\n");
            builder.append("\t<dict>\n");
            builder.append("\t\t<key>name</key>\n");
            builder.append("\t\t<string>item ").append(i).append(" with spaces</string>\n");
            builder.append("\t\t<key>pattern</key>\n");
            builder.append("\t\t<string>^\\s*item\\s+").append(i).append("$</string>\n");
            builder.append("\t\t<key>enabled</key>\n");
            builder.append("\t\t<").append(i % 2 == 0).append("/>\n");
            builder.append("\t</dict>\n");
        }
        builder.append("</dict>\n</plist>\n");
        return builder.toString();
    }

    private static Document parse(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setExpandEntityReferences(false);
        return factory.newDocumentBuilder()
                .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
    }

    private static List<String> leafValues(String xml) throws Exception {
        List<String> values = new ArrayList<>();
        collect(parse(xml).getDocumentElement(), values);
        return values;
    }

    private static void collect(Element element, List<String> values) {
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            values.add("@" + attribute.getNodeName() + "=" + attribute.getNodeValue());
        }
        boolean hasElementChild = false;
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                hasElementChild = true;
                break;
            }
        }
        if (!hasElementChild) {
            values.add(element.getTagName() + "=" + element.getTextContent());
        }
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                collect((Element) child, values);
            }
        }
    }

    private static List<String> comments(String xml) throws Exception {
        List<String> comments = new ArrayList<>();
        NodeList nodes = parse(xml).getDocumentElement().getElementsByTagName("*");
        for (int i = 0; i < nodes.getLength(); i++) {
            NodeList children = nodes.item(i).getChildNodes();
            for (int j = 0; j < children.getLength(); j++) {
                Node child = children.item(j);
                if (child.getNodeType() == Node.COMMENT_NODE) {
                    comments.add(child.getNodeValue());
                }
            }
        }
        return comments;
    }
}
