package cn.oyzh.store.file;

import cn.oyzh.common.util.IOUtil;
import cn.oyzh.common.util.StringUtil;
import cn.oyzh.common.xls.WorkbookHelper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * excel类型文件读取器
 *
 * @author oyzh
 * @since 2024-09-03
 */
public class ExcelTypeFileReader extends TypeFileReader {

    /**
     * xml读取器
     */
    private Workbook workbook;

    /**
     * 字段列表
     */
    private FileColumns columns;

    /**
     * 导入配置
     */
    private FileReadConfig config;

    /**
     * 当前行索引
     */
    private int currentRowIndex;

    public ExcelTypeFileReader(FileReadConfig config, FileColumns columns) throws Exception {
        this.config = config;
        this.columns = columns;
        boolean isXlsx = StringUtil.endWithIgnoreCase(config.filePath(), ".xlsx");
        this.workbook = WorkbookHelper.create(isXlsx, config.filePath());
        this.init();
    }

    @Override
    protected void init() throws Exception {
        if (this.config.dataRowStarts() != null) {
            this.currentRowIndex = this.config.dataRowStarts() - 1;
        }
    }

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
