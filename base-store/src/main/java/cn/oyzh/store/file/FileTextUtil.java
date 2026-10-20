package cn.oyzh.store.file;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件文本工具，提供定界文本的行解析与行格式化能力
 *
 * <p>原先 {@link TypeFileReader} 与 fx-db 的 DBDataTypeFileReader 各有一份解析实现，
 * {@link TypeFileWriter} 与 DBDataTypeFileWriter 各有一份格式化实现，此处统一。</p>
 *
 * @author oyzh
 * @since 2026-10-19
 */
public final class FileTextUtil {

    /**
     * 私有构造函数
     */
    private FileTextUtil() {
    }

    /**
     * 解析单行文本为字段值列表
     *
     * @param line           待解析的文本行
     * @param txtIdentifier  文本识别符号，用于包裹字段值
     * @param fieldSeparator 字段分割符号
     * @return 字段值列表
     * @throws IOException 异常
     */
    public static List<String> parseLine(String line, String txtIdentifier, String fieldSeparator) throws IOException {
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean txtStart = false;
        Character lastChar = null;
        try (StringReader reader = new StringReader(line)) {
            while (reader.ready()) {
                int i = reader.read();
                if (i == -1) {
                    break;
                }
                char c = (char) i;
                if (txtIdentifier != null) {
                    if (c == txtIdentifier.charAt(0)) {
                        if (lastChar != null && lastChar == '\\') {
                            sb.append(c);
                            continue;
                        }
                        if (txtStart) {
                            list.add(sb.toString());
                            sb.delete(0, sb.length());
                            txtStart = false;
                        } else {
                            txtStart = true;
                            continue;
                        }
                    }
                } else {
                    txtStart = true;
                }

                if (fieldSeparator != null) {
                    if (txtStart && c == fieldSeparator.charAt(0)) {
                        txtStart = false;
                        list.add(sb.toString());
                        sb.delete(0, sb.length());
                        continue;
                    }
                }

                if (txtStart) {
                    sb.append(c);
                }

                lastChar = c;
            }
        }
        return list;
    }

    /**
     * 解析单行文本为字段值列表
     *
     * @param line           待解析的文本行
     * @param txtIdentifier  文本识别符号
     * @param fieldSeparator 字段分割符号
     * @return 字段值列表
     * @throws IOException 异常
     */
    public static List<String> parseLine(String line, char txtIdentifier, char fieldSeparator) throws IOException {
        return parseLine(line, String.valueOf(txtIdentifier), String.valueOf(fieldSeparator));
    }

//    /**
//     * 将对象数组格式化为一行文本
//     *
//     * @param objects         对象数组
//     * @param prefix          前缀
//     * @param fieldSeparator  字段分割符号
//     * @param txtIdentifier   文本识别符号
//     * @param recordSeparator 记录分割符号
//     * @return 格式化后的文本行
//     */
//    public static String formatLine(Object[] objects, String prefix, String fieldSeparator, String txtIdentifier, String recordSeparator) {
//        return formatLine(List.of(objects), prefix, fieldSeparator, txtIdentifier, recordSeparator);
//    }

//    /**
//     * 将列表格式化为一行文本
//     *
//     * @param list            对象列表
//     * @param prefix          前缀
//     * @param fieldSeparator  字段分割符号
//     * @param txtIdentifier   文本识别符号
//     * @param recordSeparator 记录分割符号
//     * @return 格式化后的文本行
//     */
//    public static String formatLine(List<?> list, String prefix, String fieldSeparator, String txtIdentifier, String recordSeparator) {
//        StringBuilder sb = new StringBuilder();
//        for (Object val : list) {
//            if (prefix != null) {
//                sb.append(prefix);
//            }
//            if (fieldSeparator != null) {
//                sb.append(fieldSeparator);
//            }
//            sb.append(val);
//            if (fieldSeparator != null) {
//                sb.append(fieldSeparator);
//            }
//            sb.append(recordSeparator);
//        }
//        if (fieldSeparator != null) {
//            return sb.substring(1);
//        }
//        return sb.toString();
//    }

    /**
     * 将列表格式化为一行文本，空值以空字符串替代
     *
     * @param list            对象列表
     * @param fieldSeparator  字段分割符号
     * @param txtIdentifier   文本识别符号
     * @param recordSeparator 记录分割符号
     * @return 格式化后的文本行
     */
    public static String formatLine(List<?> list, String fieldSeparator, String txtIdentifier, String recordSeparator) {
        if (list.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object val : list) {
            sb.append(fieldSeparator).append(txtIdentifier).append(val == null ? "" : val).append(txtIdentifier);
        }
        sb.append(recordSeparator);
        return sb.substring(1);
    }
}
