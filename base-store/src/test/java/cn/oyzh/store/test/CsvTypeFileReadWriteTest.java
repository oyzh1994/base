package cn.oyzh.store.test;

import cn.oyzh.common.util.ResourceUtil;
import cn.oyzh.store.file.CsvTypeFileReader;
import cn.oyzh.store.file.CsvTypeFileWriter;
import cn.oyzh.store.file.FileColumns;
import cn.oyzh.store.file.FileReadConfig;
import cn.oyzh.store.file.FileRecord;
import cn.oyzh.store.file.FileWriteConfig;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV 读写回归测试
 *
 * <p>覆盖既有 fixture、引号字段、dataRowStarts 定位、标题开关与读写往返，
 * 用以验证 CSV 引擎上提到 base-store 后行为正确。</p>
 *
 * @author oyzh
 * @since 2026-10-19
 */
public class CsvTypeFileReadWriteTest {

    /**
     * 构造字段列表
     *
     * <p>name为字段键，desc为写出标题，与真实调用方一致。</p>
     *
     * @param names 字段名
     * @return 字段列表
     */
    private FileColumns columns(String... names) {
        FileColumns columns = new FileColumns();
        for (String name : names) {
            columns.addColumn(name, name);
        }
        return columns;
    }

    /**
     * 读取全部记录
     *
     * @param reader 读取器
     * @return 记录列表
     */
    private List<FileRecord> readAll(CsvTypeFileReader reader) throws Exception {
        List<FileRecord> records = new ArrayList<>();
        while (true) {
            FileRecord record = reader.readRecord();
            if (record == null) {
                break;
            }
            records.add(record);
        }
        reader.close();
        return records;
    }

    @Test
    public void testReadUnquotedFixture() throws Exception {
        FileReadConfig config = new FileReadConfig();
        config.setFilePath(ResourceUtil.getPath("/1.csv.csv"));
        config.setDataRowStarts(2);
        config.setTxtIdentifier(null);

        FileColumns columns = this.columns("Num", "ID_num", "Driver_Num");
        List<FileRecord> records = this.readAll(new CsvTypeFileReader(config, columns));

        Assert.assertFalse(records.isEmpty());
        FileRecord first = records.get(0);
        Assert.assertEquals("18", first.get(0));
        Assert.assertEquals("117", first.get(1));
        Assert.assertEquals("22", first.get(2));
    }

    @Test
    public void testReadQuotedFixture() throws Exception {
        FileReadConfig config = new FileReadConfig();
        config.setFilePath(ResourceUtil.getPath("/3.csv.csv"));
        config.setDataRowStarts(2);

        FileColumns columns = this.columns("路径", "数据", "访问控制表");
        List<FileRecord> records = this.readAll(new CsvTypeFileReader(config, columns));

        Assert.assertFalse(records.isEmpty());
        FileRecord first = records.get(0);
        Assert.assertEquals("/", first.get(0));
        Assert.assertEquals("sadasdas", first.get(1));
        Assert.assertEquals("world:anyone:adcwr", first.get(2));
    }

    @Test
    public void testReadSpaceDelimitedFixture() throws Exception {
        FileReadConfig config = new FileReadConfig();
        config.setFilePath(ResourceUtil.getPath("/2.csv.csv"));
        config.setDataRowStarts(2);

        FileColumns columns = this.columns("name", "age");
        // 2.csv.csv 为空格分割，需显式指定字段分割符号
        List<FileRecord> records = this.readAll(new CsvTypeFileReader(config, columns, " "));

        Assert.assertEquals(3, records.size());
        Assert.assertEquals("测试1", records.get(0).get(0));
        Assert.assertEquals("11", records.get(0).get(1));
        Assert.assertEquals("测试3", records.get(2).get(0));
        Assert.assertEquals("15", records.get(2).get(1));
    }

    @Test
    public void testDataRowStartsPositioning() throws Exception {
        File file = Files.createTempFile("dataRowStarts", ".csv").toFile();
        file.deleteOnExit();
        Files.write(file.toPath(), """
                header
                skipped1
                skipped2
                keep1
                keep2
                """.getBytes());

        FileReadConfig config = new FileReadConfig();
        config.setFilePath(file.getPath());
        config.setDataRowStarts(4);
        config.setTxtIdentifier(null);

        FileColumns columns = this.columns("col");
        List<FileRecord> records = this.readAll(new CsvTypeFileReader(config, columns));

        // dataRowStarts 为 1 基，第 4 行开始为 keep1
        Assert.assertEquals(2, records.size());
        Assert.assertEquals("keep1", records.get(0).get(0));
        Assert.assertEquals("keep2", records.get(1).get(0));
    }

    @Test
    public void testQuotedFieldWithDelimiterInside() throws Exception {
        File file = Files.createTempFile("quoted", ".csv").toFile();
        file.deleteOnExit();
        Files.write(file.toPath(), """
                "a","b"
                "x,1","y""2"
                """.getBytes());

        FileReadConfig config = new FileReadConfig();
        config.setFilePath(file.getPath());
        config.setDataRowStarts(2);

        FileColumns columns = this.columns("a", "b");
        List<FileRecord> records = this.readAll(new CsvTypeFileReader(config, columns));

        Assert.assertEquals(1, records.size());
        // 引号内的逗号不应被当作分隔符
        Assert.assertEquals("x,1", records.get(0).get(0));
    }

    @Test
    public void testRoundTrip() throws Exception {
        File file = Files.createTempFile("roundTrip", ".csv").toFile();
        file.deleteOnExit();

        FileColumns columns = this.columns("name", "age", "city");

        FileWriteConfig writeConfig = new FileWriteConfig();
        writeConfig.setFilePath(file.getPath());
        writeConfig.includeTitle(true);

        try (CsvTypeFileWriter writer = new CsvTypeFileWriter(writeConfig, columns)) {
            writer.writeHeader();
            List<FileRecord> records = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                FileRecord record = new FileRecord();
                record.put(0, "name" + i);
                record.put(1, String.valueOf(20 + i));
                record.put(2, "city" + i);
                records.add(record);
            }
            writer.writeRecords(records);
        }

        FileReadConfig readConfig = new FileReadConfig();
        readConfig.setFilePath(file.getPath());
        // 跳过标题行
        readConfig.setDataRowStarts(2);

        List<FileRecord> records = this.readAll(new CsvTypeFileReader(readConfig, columns));
        Assert.assertEquals(3, records.size());
        for (int i = 0; i < 3; i++) {
            Assert.assertEquals("name" + i, records.get(i).get(0));
            Assert.assertEquals(String.valueOf(20 + i), records.get(i).get(1));
            Assert.assertEquals("city" + i, records.get(i).get(2));
        }
    }

    @Test
    public void testRoundTripWithoutTitle() throws Exception {
        File file = Files.createTempFile("roundTripNoTitle", ".csv").toFile();
        file.deleteOnExit();

        FileColumns columns = this.columns("name", "age");

        FileWriteConfig writeConfig = new FileWriteConfig();
        writeConfig.setFilePath(file.getPath());
        writeConfig.includeTitle(false);

        try (CsvTypeFileWriter writer = new CsvTypeFileWriter(writeConfig, columns)) {
            writer.writeHeader();
            FileRecord record = new FileRecord();
            record.put(0, "only");
            record.put(1, "1");
            writer.writeRecord(record);
        }

        FileReadConfig readConfig = new FileReadConfig();
        readConfig.setFilePath(file.getPath());
        // 无标题行，数据从第 1 行开始
        readConfig.setDataRowStarts(1);

        List<FileRecord> records = this.readAll(new CsvTypeFileReader(readConfig, columns));
        Assert.assertEquals(1, records.size());
        Assert.assertEquals("only", records.get(0).get(0));
        Assert.assertEquals("1", records.get(0).get(1));
    }

    @Test
    public void testTxtDelimiterFromConfig() throws Exception {
        File out = Files.createTempFile("txtOut", ".txt").toFile();
        out.deleteOnExit();

        FileWriteConfig outConfig = new FileWriteConfig();
        outConfig.setFilePath(out.getPath());
        outConfig.setFieldSeparator("|");
        outConfig.includeTitle(true);

        FileColumns columns = this.columns("a", "b", "c");
        try (CsvTypeFileWriter writer = new CsvTypeFileWriter(outConfig, columns, "|")) {
            writer.writeHeader();
            FileRecord record = new FileRecord();
            record.put(0, "1");
            record.put(1, "2");
            record.put(2, "3");
            writer.writeRecord(record);
        }

        List<String> lines = Files.readAllLines(out.toPath());
        Assert.assertEquals(2, lines.size());
        // QuoteMode.ALL：所有字段均被文本识别符号包裹，保持原有导出格式
        Assert.assertEquals("\"a\"|\"b\"|\"c\"", lines.get(0));
        Assert.assertEquals("\"1\"|\"2\"|\"3\"", lines.get(1));
    }
}
