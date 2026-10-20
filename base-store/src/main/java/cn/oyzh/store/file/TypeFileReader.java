package cn.oyzh.store.file;

import java.io.Closeable;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件类型读取器
 *
 * @param <R> 记录类型
 * @author oyzh
 * @since 2024-11-28
 */
public abstract class TypeFileReader<R> implements Closeable {

    /**
     * 初始化
     *
     * @throws Exception 异常
     */
    protected void init() throws Exception {

    }

    /**
     * 读取一条记录
     *
     * @return 记录，读取完毕时返回null
     * @throws Exception 异常
     */
    public abstract R readRecord() throws Exception;

    /**
     * 读取指定数量的记录
     *
     * @param count 读取数量
     * @return 记录列表
     * @throws Exception 异常
     */
    public List<R> readRecords(int count) throws Exception {
        // 数据列表
        List<R> records = new ArrayList<>();
        // 读取数据
        while (records.size() < count) {
            R record = this.readRecord();
            if (record == null) {
                break;
            }
            records.add(record);
        }
        return records;
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
    protected List<String> parseLine(String line, String txtIdentifier, String fieldSeparator) throws IOException {
        return FileTextUtil.parseLine(line, txtIdentifier, fieldSeparator);
//        List<String> list = new ArrayList<>();
//        StringBuilder sb = new StringBuilder();
//        boolean txtStart = false;
//        Character lastChar = null;
//        try (StringReader reader = new StringReader(line)) {
//            while (reader.ready()) {
//                int i = reader.read();
//                if (i == -1) {
//                    break;
//                }
//                char c = (char) i;
//                if (txtIdentifier != null) {
//                    if (c == txtIdentifier.charAt(0)) {
//                        if (lastChar != null && lastChar == '\\') {
//                            sb.append(c);
//                            continue;
//                        }
//                        if (txtStart) {
//                            list.add(sb.toString());
//                            sb.delete(0, sb.length());
//                            txtStart = false;
//                        } else {
//                            txtStart = true;
//                            continue;
//                        }
//                    }
//                } else {
//                    txtStart = true;
//                }
//
//                if (fieldSeparator != null) {
//                    if (txtStart && c == fieldSeparator.charAt(0)) {
//                        txtStart = false;
//                        list.add(sb.toString());
//                        sb.delete(0, sb.length());
//                        continue;
//                    }
//                }
//
//                if (txtStart) {
//                    sb.append(c);
//                }
//
//                lastChar = c;
//            }
//        }
//        return list;
    }
}
