package cn.oyzh.common.xls;

import cn.oyzh.common.file.FileNameUtil;
import cn.oyzh.common.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author oyzh
 * @since 2024/8/29
 */
//@UtilityClass
public class WorkbookHelper {

    public static Workbook create(boolean isXlsx) throws IOException {
        Workbook workbook;
        if (isXlsx) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new HSSFWorkbook();
        }
        return workbook;
    }

    public static Workbook create(String filePath) throws IOException, InvalidFormatException {
        return create(new File(filePath));
    }

    public static Workbook create(boolean isXlsx, String filePath) throws IOException, InvalidFormatException {
        return create(isXlsx, new File(filePath));
    }

    public static Workbook create(File file) throws IOException, InvalidFormatException {
        String suffix = FileNameUtil.getSuffix(file.getName());
        return create(StringUtil.equalsIgnoreCase("xlsx", suffix), file);
    }

    public static Workbook create(boolean isXlsx, File file) throws IOException, InvalidFormatException {
        Workbook workbook;
        if (isXlsx) {
            workbook = new XSSFWorkbook(new FileInputStream(file));
        } else {
            workbook = new HSSFWorkbook(new FileInputStream(file));
        }
        return workbook;
    }

    public static void write(Workbook workbook, String filePath) throws IOException {
        FileOutputStream xlsOutput = new FileOutputStream(filePath);
        workbook.write(xlsOutput);
        xlsOutput.close();
    }

    public static Sheet getActiveSheet(Workbook workbook) {
        return workbook.getSheetAt(workbook.getActiveSheetIndex());
    }

    public static Row getFirstRow(Sheet sheet) {
        return sheet.getRow(0);
    }

    public static Cell getFirstCell(Row row) {
        return row.getCell(0);
    }

    public static Cell getLastCell(Row row) {
        return row.getCell(row.getLastCellNum() - 1);
    }
}
