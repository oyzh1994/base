package cn.oyzh.store.file;


import java.io.Closeable;
import java.util.List;

/**
 * 文件类型写入器
 *
 * @param <R> 记录类型
 * @author oyzh
 * @since 2024-11-28
 */
public abstract class TypeFileWriter<R> implements Closeable {

    /**
     * 初始化
     *
     * @throws Exception 异常
     */
    protected void init() throws Exception {

    }

    /**
     * 参数化
     *
     * @param value 值
     * @return 参数化后的值
     */
    public Object parameterized(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    /**
     * 写入头
     *
     * @throws Exception 异常
     */
    public void writeHeader() throws Exception {

    }

    /**
     * 写入尾
     *
     * @throws Exception 异常
     */
    public void writeTrial() throws Exception {

    }

    /**
     * 写入对象
     *
     * @param record 记录
     * @throws Exception 异常
     */
    public abstract void writeRecord(R record) throws Exception;

    /**
     * 写入多个对象
     *
     * @param records 记录列表
     * @throws Exception 异常
     */
    public void writeRecords(List<R> records) throws Exception {
        for (R object : records) {
            this.writeRecord(object);
        }
    }
}
