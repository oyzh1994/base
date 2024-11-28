package cn.oyzh.store.file;

import cn.oyzh.common.util.StringUtil;
import cn.oyzh.common.xls.WorkbookHelper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author oyzh
 * @since 2024-09-03
 */
public class ExcelTypeFileReader extends TypeFileReader {

    /**
     * xml读取器
     */
    private Workbook workbook;
    //
    // /**
    //  * 字段列表
    //  */
    // private List<String> columns;

    /**
     * 导入配置
     */
    private FileReadConfig config;

    /**
     * 当前行索引
     */
    private Integer currentRowIndex;

    public ExcelTypeFileReader(FileReadConfig config, FileColumns columns) throws Exception {
        this.config = config;
        boolean isXlsx = StringUtil.endWithIgnoreCase(config.filePath(), ".xlsx");
        this.workbook = WorkbookHelper.create(isXlsx, config.filePath());
        this.init();
    }

    // @Override
    // protected void init() throws Exception {
    //     this.columns = new ArrayList<>();
    //     Row row = this.workbook.getSheetAt(0).getRow(this.config.columnIndex());
    //     for (Cell cell : row) {
    //         this.columns.add(cell.getStringCellValue());
    //     }
    //     this.currentRowIndex = this.config.dataStartIndex();
    // }

    @Override
    public FileRecord readRecord() {
        Sheet sheet = this.workbook.getSheetAt(0);
        Row row = sheet.getRow(this.currentRowIndex++);
        if (row != null) {
            FileRecord record = new FileRecord();
            for (Cell cell : row) {
                Object val;
                CellType cellType = cell.getCellType();
                if (cellType == CellType.BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cellType == CellType.NUMERIC) {
                    if (DateUtil.isCellDateFormatted(cell)) {
                        val = cell.getDateCellValue();
                    } else {
                        val = cell.getNumericCellValue();
                    }
                } else if (cellType == CellType.STRING) {
                    val = cell.getStringCellValue();
                } else {
                    val = cell.getStringCellValue();
                }
                record.put(cell.getColumnIndex(), val);
            }
            return record;
        }
        return null;
    }

    @Override
    public void close() {
        try {
            this.workbook.close();
            this.workbook = null;
            this.config = null;
            // this.columns = null;
            this.currentRowIndex = null;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
