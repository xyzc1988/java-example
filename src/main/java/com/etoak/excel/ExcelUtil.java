package com.etoak.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;


/**
 * @author zhangcheng
 * @Description: excel解析工具类, 兼容Excel 2003/2007/2010
 * @date 2015-12-3 下午8:14:02
 */

public class ExcelUtil {

    private static SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * @param file
     * @param fileType
     * @return
     * @Title isExcel
     * @Description 判断excel文件合法性
     */
    public static boolean isExcel(String filename, String fileType) {
        return (isExcel2003(filename, fileType) || isExcel2007(filename, fileType));
    }

    /**
     * @param filename
     * @param fileType
     * @return
     * @Title isExcel2003
     * @Description
     */
    public static boolean isExcel2003(String filename, String fileType) {
        return "application/vnd.ms-excel".equals(fileType) && filename.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * @param filename
     * @param fileType
     * @return
     * @Title isExcel2007
     * @Description
     */
    public static boolean isExcel2007(String filename, String fileType) {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(fileType) && filename.matches("^.+\\.(?i)(xlsx)$");

    }

    /**
     * 获取工作簿
     *
     * @param is
     * @param sheetNum
     * @return
     * @throws Exception
     */
    public static Sheet getSheet(InputStream is, int sheetNum) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(is);
        if (sheetNum <= 0) {
            sheetNum = 0;
        }
        return workbook.getSheetAt(sheetNum);// 默认获取第1个工作薄
    }


    /**
     * 解析Excel文件，把一行的数据解析出来，返回一个读取Excel文件的字符串数组
     *
     * @param sheet
     * @param rowNum
     * @param colNum
     * @return
     * @throws Exception
     */
    public static String[] getCellValuesByRow(Sheet sheet, int rowNum, int colNum) throws Exception {
        String[] arrays = new String[colNum];
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            return null;
        }
        for (int i = 0; i < colNum; i++) {
            Cell cell = row.getCell(i);
            if (cell != null) {
                arrays[i] = getCellValue(cell);
            }
        }
        return arrays;
    }

    /**
     * 根据单元格类型返回
     *
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {

        if (cell == null)
            return "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:  //文本
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC: //数字、日期
                if (DateUtil.isCellDateFormatted(cell)) {
                    return fmt.format(cell.getDateCellValue()); //日期型
                } else {
                    return String.valueOf(cell.getNumericCellValue()); //数字
                }
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_FORMULA: //公式
                return String.valueOf(cell.getCellFormula());
            case Cell.CELL_TYPE_BLANK:  //空白
                return "";
            case Cell.CELL_TYPE_ERROR:  //错误
                return "";
            default:
                return "";
        }
    }
}