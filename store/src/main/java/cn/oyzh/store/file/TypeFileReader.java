package cn.oyzh.store.file;

import java.io.Closeable;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author oyzh
 * @since 2024-09-03
 */
public abstract class TypeFileReader implements Closeable {

    protected void init() throws Exception {

    }

    public abstract FileRecord readRecord() throws Exception;

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

    protected List<String> parseLine(String line, char txtIdentifier, char fieldSeparator) throws IOException {
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
                // if (txtStart && c == fieldSeparator) {
                //     txtStart = false;
                //     continue;
                // }
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
                    }
                } else if (txtStart) {
                    sb.append(c);
                }
                lastChar = c;
            }
        }
        return list;
    }
}
