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
 * 表格辅助类
 *
 * @author oyzh
 * @since 2024/8/29
 */
public class WorkbookHelper {

    /**
     * 创建空的表格对象
     *
     * @param isXlsx 是否xlsx格式
     * @return 表格对象
     * @throws IOException 异常
     */
    public static Workbook create(boolean isXlsx) throws IOException {
        Workbook workbook;
        if (isXlsx) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new HSSFWorkbook();
        }
        return workbook;
    }

    /**
     * 根据文件路径创建表格对象
     *
     * @param filePath 文件路径
     * @return 表格对象
     * @throws IOException            异常
     * @throws InvalidFormatException 文件格式异常
     */
    public static Workbook create(String filePath) throws IOException, InvalidFormatException {
        return create(new File(filePath));
    }

    /**
     * 根据文件路径创建表格对象
     *
     * @param isXlsx   是否xlsx格式
     * @param filePath 文件路径
     * @return 表格对象
     * @throws IOException            异常
     * @throws InvalidFormatException 文件格式异常
     */
    public static Workbook create(boolean isXlsx, String filePath) throws IOException, InvalidFormatException {
        return create(isXlsx, new File(filePath));
    }

    /**
     * 根据文件创建表格对象，格式由文件后缀推断
     *
     * @param file 文件
     * @return 表格对象
     * @throws IOException            异常
     * @throws InvalidFormatException 文件格式异常
     */
    public static Workbook create(File file) throws IOException, InvalidFormatException {
        String suffix = FileNameUtil.getSuffix(file.getName());
        return create(StringUtil.equalsIgnoreCase("xlsx", suffix), file);
    }

    /**
     * 根据文件创建表格对象
     *
     * @param isXlsx 是否xlsx格式
     * @param file   文件
     * @return 表格对象
     * @throws IOException            异常
     * @throws InvalidFormatException 文件格式异常
     */
    public static Workbook create(boolean isXlsx, File file) throws IOException, InvalidFormatException {
        Workbook workbook;
        if (isXlsx) {
            workbook = new XSSFWorkbook(new FileInputStream(file));
        } else {
            workbook = new HSSFWorkbook(new FileInputStream(file));
        }
        return workbook;
    }

    /**
     * 将表格写入指定文件
     *
     * @param workbook 表格对象
     * @param filePath 文件路径
     * @throws IOException 异常
     */
    public static void write(Workbook workbook, String filePath) throws IOException {
        FileOutputStream xlsOutput = new FileOutputStream(filePath);
        workbook.write(xlsOutput);
        xlsOutput.close();
    }

    /**
     * 获取活动工作表
     *
     * @param workbook 表格对象
     * @return 活动工作表
     */
    public static Sheet getActiveSheet(Workbook workbook) {
        return workbook.getSheetAt(workbook.getActiveSheetIndex());
    }

    /**
     * 获取第一行
     *
     * @param sheet 工作表
     * @return 第一行
     */
    public static Row getFirstRow(Sheet sheet) {
        return sheet.getRow(0);
    }

    /**
     * 获取第一个单元格
     *
     * @param row 行
     * @return 第一个单元格
     */
    public static Cell getFirstCell(Row row) {
        return row.getCell(0);
    }

    /**
     * 获取最后一个单元格
     *
     * @param row 行
     * @return 最后一个单元格
     */
    public static Cell getLastCell(Row row) {
        return row.getCell(row.getLastCellNum() - 1);
    }
}
