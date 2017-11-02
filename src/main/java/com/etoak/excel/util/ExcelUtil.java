package com.etoak.excel.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/** 
 * @ClassName: ExcelUtil 
 * @Description: 
 * @author zhangcheng
 * @date 2015-12-3 下午8:14:02 
 *  
 */  
public class ExcelUtil {
	/** 
	 * @Title isExcel 
	 * @Description 判断excel文件合法性
	 * @param file
	 * @param fileType
	 * @return
	 */  
	public static boolean isExcel(File file, String fileType) {
		if (isExcel2003(file, fileType) || isExcel2007(file, fileType))
			return true;
		return false;
	}

	/** 
	 * @Title isExcel2003 
	 * @Description 
	 * @param file
	 * @param fileType
	 * @return
	 */  
	public static boolean isExcel2003(File file, String fileType) {
		String fileName = file.getName();
		if ("application/vnd.ms-excel".equals(fileType)
				|| fileName.matches("^.+\\.(?i)(xls)$"))
			return true;
		return false;
	}

	/** 
	 * @Title isExcel2007 
	 * @Description
	 * @param file
	 * @param fileType
	 * @return
	 */  
	public static boolean isExcel2007(File file, String fileType) {
		String fileName = file.getName();
		if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
				.equals(fileType) || fileName.matches("^.+\\.(?i)(xlsx)$"))
			return true;
		return false;
	}

	/** 
	 * @Title getSheetRowCount 
	 * @Description 获取工作薄的总行数(包含标题)
	 * @param isExcel03
	 * @param hssfSheet
	 * @param xssfSheet
	 * @param sheetNum
	 * @return
	 * @throws Exception
	 */  
	public static int getSheetRowCount(boolean isExcel03, HSSFSheet hssfSheet,
                                       XSSFSheet xssfSheet, int sheetNum) throws Exception {
		int rows = 0;
		if (isExcel03)
			rows = hssfSheet.getLastRowNum();
		else
			rows = xssfSheet.getLastRowNum();
		if (rows < 0)
			return rows;
		else
			return rows + 1;
	}

	/** 
	 * @Title getSheetColCount 
	 * @Description 获取工作薄的总列数
	 * @param isExcel03
	 * @param hssfSheet
	 * @param xssfSheet
	 * @param sheetNum
	 * @return
	 * @throws Exception
	 */  
	public static int getSheetColCount(boolean isExcel03, HSSFSheet hssfSheet,
                                       XSSFSheet xssfSheet, int sheetNum) throws Exception {
		int maxCol = 0;
		if (getSheetRowCount(isExcel03, hssfSheet, xssfSheet, sheetNum) == 0)
			return 0;
		if (isExcel03) {
			for (int i = 0; i < hssfSheet.getLastRowNum() + 1; i++) {
				HSSFRow row = hssfSheet.getRow(i);
				if (row.getLastCellNum() > maxCol) {
					maxCol = row.getLastCellNum();
				}
			}
		} else {
			for (int i = 0; i < xssfSheet.getLastRowNum() + 1; i++) {
				XSSFRow row = xssfSheet.getRow(i);
				if (row.getLastCellNum() > maxCol) {
					maxCol = row.getLastCellNum();
				}
			}
		}
		return maxCol;
	}

	/** 
	 * @Title getColCountByRowNum 
	 * @Description
	 * @param isExcel03
	 * @param hssfSheet
	 * @param xssfSheet
	 * @param sheetNum
	 * @param rowNum
	 * @return
	 * @throws Exception
	 */  
	public static int getColCountByRowNum(boolean isExcel03,
                                          HSSFSheet hssfSheet, XSSFSheet xssfSheet, int sheetNum, int rowNum)
			throws Exception {
		int colNum = 0;
		int pysicalColNum = 0;
		if (isExcel03) {
			HSSFRow row = hssfSheet.getRow(rowNum);
			if (row != null) {
				colNum = row.getLastCellNum();
				pysicalColNum = row.getPhysicalNumberOfCells();
			}
		} else {
			XSSFRow row = xssfSheet.getRow(rowNum);
			if (row != null) {
				colNum = row.getLastCellNum();
				pysicalColNum = row.getPhysicalNumberOfCells();
			}
		}
		if (colNum == 0) {
			if (pysicalColNum > 0)
				colNum = 1;
		}
		return colNum;
	}
	
	public static HSSFSheet getExcel03Sheet(File file, int sheetNum)
			throws Exception {
		POIFSFileSystem poiFileSystem;
		FileInputStream fint = new FileInputStream(file);
		poiFileSystem = new POIFSFileSystem(fint);
		HSSFWorkbook wb = new HSSFWorkbook(poiFileSystem);//
		HSSFSheet sheet = wb.getSheetAt(0);// 获取第工作薄
		return sheet;
	}

	/**
	 * 閼惧嘲褰�007閻ㄥ墕heet
	 * 
	 * @param file
	 *            閺傚洣娆㈤崘鍛啇
	 * @param sheetNum
	 *            閺傚洣娆㈢粭顒�殤妞わ拷
	 * @return Excel07閻ㄥ墕heet
	 * @throws Exception
	 */
	public static XSSFSheet getExcel07Sheet(File file, int sheetNum)
			throws Exception {
		@SuppressWarnings("deprecation")
        XSSFWorkbook xwb = new XSSFWorkbook(file.getPath());// 閹垫挸绱戦弬鍥︽閸擃垱婀�
		XSSFSheet xsheet = xwb.getSheetAt(sheetNum);// 閼惧嘲褰囩粭顑跨瀵姾銆�
		return xsheet;
	}

	public static String getCellValueByRowNumAndColNum(boolean isExcel03,
                                                       HSSFSheet hssfSheet, XSSFSheet xssfSheet, int sheetNum, int rowNum,
                                                       int colNum) throws Exception {
		if (isExcel03) {
			HSSFRow row = hssfSheet.getRow(rowNum);
			if (row == null)
				return "";
			HSSFCell cell = row.getCell(colNum);
			if (cell == null)
				return "";
			return get03CellValue(cell);
		} else {
			XSSFRow row = xssfSheet.getRow(rowNum);
			if (row == null)
				return "";
			XSSFCell cell = row.getCell(colNum);
			if (cell == null)
				return "";
			return get07CellValue(cell);
		}
	}
	/** 
	 * @Title getCellValuesByRow 
	 * @Description  解析Excel文件，把一行的数据解析出来，返回一个读取Excel文件的字符串数组。（根据不同的版本进行数据的解析）
	 * @param isExcel03
	 * @param hssfSheet
	 * @param xssfSheet
	 * @param sheetNum
	 * @param rowNum
	 * @param colNum
	 * @return
	 * @throws Exception
	 */  
	public static String[] getCellValuesByRow(boolean isExcel03, HSSFSheet hssfSheet,
                                              XSSFSheet xssfSheet, int sheetNum, int rowNum, int colNum) throws Exception{
		String[] arrays = new String[colNum];
		if (isExcel03) {
			HSSFRow row = hssfSheet.getRow(rowNum);
			if (row == null){
				return null;
			}
			for(int i = 0 ; i < colNum ; i++){
				HSSFCell cell = row.getCell(i);
				if (cell != null){
					arrays[i] = get03CellValue(cell);
				}
			}
		} else {
			XSSFRow row = xssfSheet.getRow(rowNum);
			if (row == null){
				return null;
			}
			for(int i = 0 ; i < colNum ; i++){
				XSSFCell cell = row.getCell(i);
				if (cell != null){
					arrays[i] = get07CellValue(cell);
				}
			}
		}
		return arrays;
	} 
	
	private static String get03CellValue(HSSFCell cell, int i) throws ParseException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			if (i == 5 || i == 6) { // 澶勭悊鎺堟潈寮�銆佺粨鏉熸椂闂�
				return sdf.format(cell.getDateCellValue());
			}
		} catch (Exception e) {
			return "time format error";
		}
		DecimalFormat df = new DecimalFormat("#");
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 閺佹澘鐡�
			System.out.println(cell.getNumericCellValue());
			return df.format(cell.getNumericCellValue());
		case HSSFCell.CELL_TYPE_STRING: // 鐎涙顑佹稉锟�
			System.out.println(cell.getStringCellValue());
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
			return String.valueOf(cell.getBooleanCellValue());
		case HSSFCell.CELL_TYPE_FORMULA: // 閸忣剙绱�
			return String.valueOf(cell.getCellFormula());
		case HSSFCell.CELL_TYPE_BLANK: // 缁屽搫锟�
			return null;
		case HSSFCell.CELL_TYPE_ERROR: // 閺佸懘娈�
			return null;
		default:
			return null;
		}
	}
	private static String get03CellValue(HSSFCell cell) {
		
		DecimalFormat df = new DecimalFormat("#");
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 閺佹澘鐡�
			System.out.println(cell.getNumericCellValue());
			return df.format(cell.getNumericCellValue());
		case HSSFCell.CELL_TYPE_STRING: // 鐎涙顑佹稉锟�
			System.out.println(cell.getStringCellValue());
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
			return String.valueOf(cell.getBooleanCellValue());
		case HSSFCell.CELL_TYPE_FORMULA: // 閸忣剙绱�
			return String.valueOf(cell.getCellFormula());
		case HSSFCell.CELL_TYPE_BLANK: // 缁屽搫锟�
			return null;
		case HSSFCell.CELL_TYPE_ERROR: // 閺佸懘娈�
			return null;
		default:
			return null;
		}
	}
	private static String get07CellValue(XSSFCell cell) {
		DecimalFormat df = new DecimalFormat("#");
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 閺佹澘鐡�
			return df.format(cell.getNumericCellValue());
		case HSSFCell.CELL_TYPE_STRING: // 鐎涙顑佹稉锟�
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
			return String.valueOf(cell.getBooleanCellValue());
		case HSSFCell.CELL_TYPE_FORMULA: // 閸忣剙绱�
			return String.valueOf(cell.getCellFormula());
		case HSSFCell.CELL_TYPE_BLANK: // 缁屽搫锟�
			return null;
		case HSSFCell.CELL_TYPE_ERROR: // 閺佸懘娈�
			return null;
		default:
			return null;
		}
	}
	public static void main(String[] args) throws Exception {
		String[] data  = new String[10];
		File fileExcel = new File("C:\\Users\\zhangcheng\\Desktop\\临时\\OpenBatchTemplate.xls");  
		HSSFSheet hssfSheet =  ExcelUtil.getExcel03Sheet(fileExcel,0);
		int rowNum = hssfSheet.getLastRowNum() + 1;
		int rowNum1 = hssfSheet.getNumMergedRegions();
		
		for(int i=0;i<rowNum;i++){
			HSSFRow row = hssfSheet.getRow(i);
			int j = row.getLastCellNum();
			data = ExcelUtil.getCellValuesByRow(true,hssfSheet,null,0,i,10);
		}
	}
}