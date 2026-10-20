package cn.oyzh.store.file;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

/**
 * 文件excel工具
 *
 * <p>原先 base-store 与 fx-db 的 excel 读写器各有一份单元格取值实现，此处统一。</p>
 *
 * @author oyzh
 * @since 2026-10-19
 */
public final class FileExcelUtil {

    /**
     * 私有构造函数
     */
    private FileExcelUtil() {
    }

    /**
     * 读取单元格的值，并按单元格类型转换
     *
     * @param cell 单元格
     * @return 单元格的值
     */
    public static Object cellValue(Cell cell) {
        CellType cellType = cell.getCellType();
        if (cellType == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        }
        if (cellType == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            }
            return cell.getNumericCellValue();
        }
        return cell.getStringCellValue();
    }

    /**
     * 将值写入单元格
     *
     * @param cell 单元格
     * @param val  值
     */
    public static void setCellValue(org.apache.poi.ss.usermodel.Cell cell, Object val) {
        switch (val) {
            case null -> {
            }
            case java.util.Date v -> cell.setCellValue(v);
            case Double v -> cell.setCellValue(v);
            case String v -> cell.setCellValue(v);
            case Boolean v -> cell.setCellValue(v);
            case java.util.Calendar v -> cell.setCellValue(v);
            case java.time.LocalDate v -> cell.setCellValue(v);
            case java.time.LocalDateTime v -> cell.setCellValue(v);
            case Number v -> cell.setCellValue(v.doubleValue());
            default -> cell.setCellValue(val.toString());
        }
    }
}
