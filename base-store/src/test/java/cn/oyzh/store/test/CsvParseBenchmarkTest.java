package cn.oyzh.store.test;

import cn.oyzh.store.file.CsvFileWriter;
import cn.oyzh.store.file.CsvFormat;
import cn.oyzh.store.file.FileWriteConfig;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV 解析/写入基准测试
 *
 * <p>以合并前的手工逐字符解析与手工行格式化为基线，对比 Apache Commons CSV 实现的吞吐，
 * 并断言二者在规范数据上解析结果一致。用以说明 CSV 引擎上提到 base-store 后正确性不降、性能可接受。</p>
 *
 * <p>不引入 JMH，采用 {@link System#nanoTime()} 多轮取最小值，避免 JIT 预热抖动。</p>
 *
 * @author oyzh
 * @since 2026-10-19
 */
public class CsvParseBenchmarkTest {

    /**
     * 生成的数据行数
     */
    private static final int ROW_COUNT = 50_000;

    /**
     * 每行字段数
     */
    private static final int COLUMN_COUNT = 8;

    /**
     * 计时轮数，取最小值
     */
    private static final int ROUNDS = 5;

    /**
     * 合并前的基线实现：手工逐字符解析单行文本
     *
     * <p>此为 {@code TypeFileReader#parseLine} 在合并前的原始逻辑，仅作基准对照保留。</p>
     *
     * @param line           待解析的文本行
     * @param txtIdentifier  文本识别符号
     * @param fieldSeparator 字段分割符号
     * @return 字段值列表
     */
    private static List<String> legacyParseLine(String line, String txtIdentifier, String fieldSeparator) throws IOException {
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
     * 合并前的基线实现：手工格式化一行文本
     *
     * @param list            对象列表
     * @param prefix          前缀
     * @param fieldSeparator  字段分割符号
     * @param txtIdentifier   文本识别符号
     * @param recordSeparator 记录分割符号
     * @return 格式化后的文本行
     */
    private static String legacyFormatLine(List<?> list, String prefix, String fieldSeparator, String txtIdentifier, String recordSeparator) {
        StringBuilder sb = new StringBuilder();
        for (Object val : list) {
            if (fieldSeparator != null) {
                sb.append(fieldSeparator);
            }
            if (prefix != null) {
                sb.append(prefix);
            }
            if (txtIdentifier != null) {
                sb.append(txtIdentifier);
            }
            sb.append(val);
            if (txtIdentifier != null) {
                sb.append(txtIdentifier);
            }
        }
        sb.append(recordSeparator);
        if (fieldSeparator != null) {
            return sb.substring(1);
        }
        return sb.toString();
    }

    /**
     * 生成基准用 CSV 文本
     *
     * @return CSV 文本行列表
     */
    private List<String> generateRows() {
        List<String> rows = new ArrayList<>(ROW_COUNT + 1);
        StringBuilder header = new StringBuilder();
        for (int c = 0; c < COLUMN_COUNT; c++) {
            if (c > 0) {
                header.append(',');
            }
            header.append("\"col").append(c).append('"');
        }
        rows.add(header.toString());
        for (int r = 0; r < ROW_COUNT; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < COLUMN_COUNT; c++) {
                if (c > 0) {
                    sb.append(',');
                }
                // 规范数据：引号内不含分割符号，两种实现解析结果一致
                sb.append('"').append("v").append(r).append('_').append(c).append('"');
            }
            rows.add(sb.toString());
        }
        return rows;
    }

    /**
     * 执行若干轮，返回最小耗时纳秒
     *
     * @param rounds 轮数
     * @param task   待测逻辑
     * @return 最小耗时
     */
    private long timeMin(int rounds, IntTask task) throws Exception {
        long min = Long.MAX_VALUE;
        for (int i = 0; i < rounds; i++) {
            long start = System.nanoTime();
            task.run();
            min = Math.min(min, System.nanoTime() - start);
        }
        return min;
    }

    /**
     * 无参任务
     */
    @FunctionalInterface
    private interface IntTask {
        void run() throws Exception;
    }

    @Test
    public void benchmarkParse() throws Exception {
        List<String> rows = this.generateRows();
        String content = String.join("\n", rows) + "\n";

        // 预热
        for (int i = 0; i < 2; i++) {
            legacyParseAll(content);
            commonsCsvParseAll(content);
        }

        List<List<String>> legacy = legacyParseAll(content);
        List<List<String>> modern = this.commonsCsvParseAll(content);

        // 等价性：规范数据上两种实现的解析结果必须一致
        Assert.assertEquals(legacy.size(), modern.size());
        for (int i = 0; i < legacy.size(); i++) {
            Assert.assertEquals("第 " + i + " 行解析结果不一致", legacy.get(i), modern.get(i));
        }

        long legacyNanos = this.timeMin(ROUNDS, () -> legacyParseAll(content));
        long modernNanos = this.timeMin(ROUNDS, () -> this.commonsCsvParseAll(content));

        this.report("解析", ROW_COUNT + 1, legacyNanos, modernNanos);
    }

    /**
     * 手工解析全部行
     *
     * @param content csv内容
     * @return 解析结果
     */
    private static List<List<String>> legacyParseAll(String content) throws IOException {
        List<List<String>> all = new ArrayList<>();
        for (String line : content.split("\n")) {
            if (!line.isEmpty()) {
                all.add(legacyParseLine(line, "\"", ","));
            }
        }
        return all;
    }

    /**
     * commons-csv 解析全部行
     *
     * @param content csv内容
     * @return 解析结果
     * @throws IOException IO异常
     */
    private List<List<String>> commonsCsvParseAll(String content) throws IOException {
        List<List<String>> all = new ArrayList<>();
        try (Reader reader = new StringReader(content);
             CSVParser parser = CsvFormat.create(",", "\"", "\n").parse(reader)) {
            for (CSVRecord record : parser) {
                List<String> row = new ArrayList<>(record.size());
                for (String value : record) {
                    row.add(value);
                }
                all.add(row);
            }
        }
        return all;
    }

    @Test
    public void benchmarkWrite() throws Exception {
        List<String> rows = this.generateRows();
        // 预热
        for (int i = 0; i < 2; i++) {
            legacyWriteAll(rows);
            this.commonsCsvWriteAll();
        }

        long legacyNanos = this.timeMin(ROUNDS, () -> legacyWriteAll(rows));
        long modernNanos = this.timeMin(ROUNDS, () -> this.commonsCsvWriteAll());

        this.report("写入", ROW_COUNT + 1, legacyNanos, modernNanos);
    }

    /**
     * 手工格式化全部行
     *
     * @param rows 行数据
     */
    private static void legacyWriteAll(List<String> rows) throws IOException {
        for (String row : rows) {
            List<String> fields = legacyParseLine(row, "\"", ",");
            legacyFormatLine(fields, null, ",", "\"", "\n");
        }
    }

    /**
     * commons-csv 写入全部行
     *
     * @throws Exception 异常
     */
    private void commonsCsvWriteAll() throws Exception {
        Path path = Files.createTempFile("csvBench", ".csv");
        try {
            FileWriteConfig config = new FileWriteConfig();
            config.setFilePath(path.toString());
            try (CsvFileWriter writer = new CsvFileWriter(path.toString(), config, ",")) {
                for (int r = 0; r < ROW_COUNT; r++) {
                    Object[] values = new Object[COLUMN_COUNT];
                    for (int c = 0; c < COLUMN_COUNT; c++) {
                        values[c] = "v" + r + "_" + c;
                    }
                    writer.write(values);
                }
            }
        } finally {
            Files.deleteIfExists(path);
        }
    }

    /**
     * 打印基准对比结果
     *
     * @param kind      测试类型
     * @param rowCount  行数
     * @param legacyNs  基线耗时
     * @param modernNs  新实现耗时
     */
    private void report(String kind, int rowCount, long legacyNs, long modernNs) {
        double legacyMs = legacyNs / 1_000_000.0;
        double modernMs = modernNs / 1_000_000.0;
        double legacyRate = rowCount / (legacyNs / 1_000_000_000.0);
        double modernRate = rowCount / (modernNs / 1_000_000_000.0);
        System.out.printf("[基准] %s %d 行: 手工实现 %.2fms (%.0f 行/秒) | commons-csv %.2fms (%.0f 行/秒) | 倍率 %.2fx%n",
                kind, rowCount, legacyMs, legacyRate, modernMs, modernRate, legacyNs / (double) modernNs);
    }
}
