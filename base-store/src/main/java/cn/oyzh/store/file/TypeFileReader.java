package cn.oyzh.store.file;

import java.io.Closeable;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件类型读取器
 *
 * @author oyzh
 * @since 2024-09-03
 */
public abstract class TypeFileReader implements Closeable {

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
     * @return 文件记录，读取完毕时返回null
     * @throws Exception 异常
     */
    public abstract FileRecord readRecord() throws Exception;

    /**
     * 读取指定数量的记录
     *
     * @param count 读取数量
     * @return 文件记录列表
     * @throws Exception 异常
     */
    public List<FileRecord> readRecords(int count) throws Exception {
        // 数据列表
        List<FileRecord> records = new ArrayList<>();
        // 读取数据
        while (records.size() < count) {
            FileRecord record = this.readRecord();
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
     * @param fieldSeparator 字段分隔符号
     * @return 字段值列表
     * @throws IOException 异常
     */
    protected List<String> parseLine(String line, Character txtIdentifier, Character fieldSeparator) throws IOException {
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
                    if (c == txtIdentifier) {
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
                    if (txtStart && c == fieldSeparator) {
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
}
