package cn.oyzh.store.file;

import cn.oyzh.common.file.FileUtil;
import cn.oyzh.common.json.JSONUtil;
import cn.oyzh.common.util.IOUtil;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson2.JSONObject;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author oyzh
 * @since 2024-09-03
 */
public class JsonTypeFileReader extends TypeFileReader {

    /**
     * json读取器
     */
    private JSONReader reader;

    /**
     * 导入配置
     */
    private FileReadConfig config;

    /**
     * 字段列表
     */
    private FileColumns columns;

    public JsonTypeFileReader(FileReadConfig config, FileColumns columns) throws FileNotFoundException {
        this.config = config;
        this.columns = columns;
        this.reader = new JSONReader(FileUtil.getReader(config.filePath(), Charset.forName(config.charset())));
        this.init();
    }

    @Override
    protected void init() {
        // 初始化
        if (this.reader.hasNext()) {
            this.reader.startArray();
        }
    }

    @Override
    public FileRecord readRecord() {
        if (this.reader.hasNext()) {
            try {
                String json = this.reader.readString();
                JSONObject object = JSONUtil.parseObject(json);
                FileRecord record = new FileRecord();
                for (Map.Entry<String, Object> entry : object.entrySet()) {
                    FileColumn column = this.columns.column(entry.getKey());
                    record.put(column.getPosition(), entry.getValue());
                }
                return record;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void close() {
        try {
            this.reader.endArray();
            IOUtil.close(this.reader);
            this.reader = null;
            this.config = null;
            this.columns.clear();
            this.columns = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
