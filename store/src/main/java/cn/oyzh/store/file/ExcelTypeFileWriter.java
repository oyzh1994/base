package cn.oyzh.store.file;

import cn.oyzh.common.util.IOUtil;
import cn.oyzh.common.util.StringUtil;
import cn.oyzh.common.xls.WorkbookHelper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author oyzh
 * @since 2024-09-04
 */
public class ExcelTypeFileWriter extends TypeFileWriter {

    /**
     * 字段列表
     */
    private FileColumns columns;

    /**
     * 导出配置
     */
    private FileWriteConfig config;

    /**
     * xls工作薄
     */
    private Workbook workbook;

    /**
     * xls行记录
     */
    private int xlsRowIndex = 0;

    public ExcelTypeFileWriter(FileWriteConfig config, FileColumns columns) throws IOException, InvalidFormatException {
        this.columns = columns;
        this.config = config;
        boolean isXlsx = StringUtil.endWithIgnoreCase(config.filePath(), ".xlsx");
        this.workbook = WorkbookHelper.create(isXlsx);
    }

    // @Override
    // public void writeHeader() throws Exception {
    //     // 重置行索引
    //     this.xlsRowIndex = 1;
    //     // 创建一个新的工作表sheet
    //     Sheet sheet = this.workbook.createSheet(this.config.sheetName());
    //     // 创建列名行
    //     Row headerRow = sheet.createRow(0);
    //     // 写入列名
    //     List<FileColumn> columnList = columns.sortOfPosition();
    //     for (int i = 0; i < columnList.size(); i++) {
    //         Cell cell = headerRow.createCell(i);
    //         cell.setCellValue(columnList.get(i).getName());
    //     }
    //     // 写入数据
    //     WorkbookHelper.write(this.workbook, this.config.filePath());
    // }

    /**
     * 刷新工作薄
     *
     * @throws IOException 异常
     */
    private void flush() throws IOException {
        WorkbookHelper.write(this.workbook, this.config.filePath());
    }

    /**
     * 写入记录
     *
     * @param record 记录
     * @param flush  是否刷新
     * @throws Exception 异常
     */
    private void writeRecord(FileRecord record, boolean flush) throws Exception {
        // 获取当前页
        Sheet sheet;
        if (this.xlsRowIndex == 0) {
            sheet = this.workbook.createSheet(this.config.sheetName());
        } else {
            sheet = WorkbookHelper.getActiveSheet(this.workbook);
        }
        // 处理数据
        Object[] values = new Object[record.size()];
        for (Map.Entry<Integer, Object> entry : record.entrySet()) {
            int index = entry.getKey();
            Object val = entry.getValue();
            values[index] = val;
        }
        // 创建数据行
        Row row = sheet.createRow(this.xlsRowIndex++);
        // 填充数据列
        for (int i = 0; i < values.length; i++) {
            Cell cell = row.createCell(i);
            Object val = values[i];
            switch (val) {
                case null -> {
                }
                case Date v -> cell.setCellValue(v);
                case Double v -> cell.setCellValue(v);
                case String v -> cell.setCellValue(v);
                case Boolean v -> cell.setCellValue(v);
                case Calendar v -> cell.setCellValue(v);
                case LocalDate v -> cell.setCellValue(v);
                case LocalDateTime v -> cell.setCellValue(v);
                case Number v -> cell.setCellValue(v.doubleValue());
                default -> cell.setCellValue(val.toString());
            }
        }
        if (flush) {
            this.flush();
        }
    }

    @Override
    public void writeRecord(FileRecord record) throws Exception {
        this.writeRecord(record, true);
    }

    @Override
    public void writeRecords(List<FileRecord> records) throws Exception {
        for (FileRecord record : records) {
            this.writeRecord(record, false);
        }
        this.flush();
    }

    @Override
    public void close() {
        try {
            IOUtil.close(this.workbook);
            this.workbook = null;
            this.config = null;
            this.columns.clear();
            this.columns = null;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
