package cn.oyzh.common.xml;

/**
 * xml工具类
 *
 * @author oyzh
 * @since 2026-10-24
 */
public class XMLUtil {

    /**
     * 压缩xml。
     * <p>
     * 移除标签之间仅用于格式化的空白文本节点（必须包含换行），并折叠标签内、引号外的空白。
     * 元素文本（如plist的string/key）原样保留，注释、CDATA、处理指令和DOCTYPE原样保留。
     *
     * @param xml xml字符串
     * @return xml压缩字符串
     */
    public static String toCompress(String xml) {
        if (xml == null || xml.isEmpty()) {
            return xml;
        }
        int length = xml.length();
        StringBuilder result = new StringBuilder(length);
        // 跳过utf-8 bom
        int index = !xml.isEmpty() && xml.codePointAt(0) == 0xFEFF ? 1 : 0;
        while (index < length) {
            char c = xml.charAt(index);
            if (c == '<') {
                if (xml.startsWith("<!--", index)) {
                    index = copyUntil(result, xml, index, "-->");
                } else if (xml.startsWith("<![CDATA[", index)) {
                    index = copyUntil(result, xml, index, "]]>");
                } else if (xml.startsWith("<?", index)) {
                    index = copyUntil(result, xml, index, "?>");
                } else if (xml.startsWith("<!DOCTYPE", index)) {
                    index = appendDoctype(result, xml, index);
                } else {
                    index = appendTag(result, xml, index);
                }
            } else {
                int end = xml.indexOf('<', index);
                if (end < 0) {
                    end = length;
                }
                String text = xml.substring(index, end);
                // 只移除包含换行的纯空白文本，避免误删元素内有意义的内容
                if (!isFormattingWhitespace(text)) {
                    result.append(text);
                }
                index = end;
            }
        }
        return result.toString();
    }

    /**
     * 压缩xml
     *
     * @param xml xml字符串
     * @return xml压缩字符串
     */
    public static String compress(String xml) {
        return toCompress(xml);
    }

    /**
     * 复制直到结束标记
     *
     * @param result     结果
     * @param xml        xml字符串
     * @param start      起始位置
     * @param terminator 结束标记
     * @return 新位置
     */
    private static int copyUntil(StringBuilder result, String xml, int start, String terminator) {
        int end = xml.indexOf(terminator, start + 1);
        end = end < 0 ? xml.length() : end + terminator.length();
        result.append(xml, start, end);
        return end;
    }

    /**
     * 追加DOCTYPE内容，跳过引号和中括号内的结束符
     *
     * @param result 结果
     * @param xml    xml字符串
     * @param start  起始位置
     * @return 新位置
     */
    private static int appendDoctype(StringBuilder result, String xml, int start) {
        int length = xml.length();
        int index = start + "<!DOCTYPE".length();
        char quote = 0;
        int brackets = 0;
        while (index < length) {
            char c = xml.charAt(index);
            if (quote != 0) {
                if (c == quote) {
                    quote = 0;
                }
            } else if (c == '"' || c == '\'') {
                quote = c;
            } else if (c == '[') {
                brackets++;
            } else if (c == ']') {
                if (brackets > 0) {
                    brackets--;
                }
            } else if (c == '>' && brackets == 0) {
                index++;
                break;
            }
            index++;
        }
        result.append(xml, start, index);
        return index;
    }

    /**
     * 追加标签，折叠引号外的空白
     *
     * @param result 结果
     * @param xml    xml字符串
     * @param start  起始位置
     * @return 新位置
     */
    private static int appendTag(StringBuilder result, String xml, int start) {
        int length = xml.length();
        int index = start + 1;
        char quote = 0;
        boolean pendingSpace = false;
        result.append('<');
        while (index < length) {
            char c = xml.charAt(index);
            if (quote != 0) {
                result.append(c);
                if (c == quote) {
                    quote = 0;
                }
            } else if (c == '"' || c == '\'') {
                if (pendingSpace) {
                    appendSpace(result);
                    pendingSpace = false;
                }
                result.append(c);
                quote = c;
            } else if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                pendingSpace = true;
            } else if (c == '>') {
                if (pendingSpace && result.charAt(result.length() - 1) == ' ') {
                    result.setLength(result.length() - 1);
                }
                result.append('>');
                return index + 1;
            } else if (c == '/' || c == '=') {
                if (pendingSpace && result.charAt(result.length() - 1) == ' ') {
                    result.setLength(result.length() - 1);
                }
                pendingSpace = false;
                result.append(c);
            } else {
                if (pendingSpace) {
                    appendSpace(result);
                    pendingSpace = false;
                }
                result.append(c);
            }
            index++;
        }
        return index;
    }

    /**
     * 追加单个空格
     *
     * @param result 结果
     */
    private static void appendSpace(StringBuilder result) {
        if (result.length() > 0) {
            char last = result.charAt(result.length() - 1);
            if (last != '<' && last != ' ' && last != '=') {
                result.append(' ');
            }
        }
    }

    /**
     * 是否为仅用于格式化的空白文本
     *
     * @param text 文本
     * @return 结果
     */
    private static boolean isFormattingWhitespace(String text) {
        boolean newline = false;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n' || c == '\r') {
                newline = true;
            } else if (c != ' ' && c != '\t') {
                return false;
            }
        }
        return newline;
    }
}
