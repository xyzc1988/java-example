package com.etoak.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/** 
 * @ClassName: ExcelUtil 
 * @Description: 
 * @date 2016年2月1日 下午5:50:50 
 *  
 */  
public class ExcelUtil {
	/** 
	 * @Title isExcel 
	 * @Description
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
	 * @Description 获取sheet的行数
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
	 * @Description 获取sheet的列数
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
	 * @Description 获取Excel文件当前行的列数
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

	/** 
	 * @Title getExcel03Sheet 
	 * @Description 根据编号获取Excel2003文件sheet对象
	 * @param file
	 * @param sheetNum sheet编号,从0开始
	 * @return
	 * @throws Exception
	 */  
	public static HSSFSheet getExcel03Sheet(File file, int sheetNum)
			throws Exception {
		POIFSFileSystem poiFileSystem;
		FileInputStream fint = new FileInputStream(file);
		poiFileSystem = new POIFSFileSystem(fint);
		HSSFWorkbook wb = new HSSFWorkbook(poiFileSystem);//
		HSSFSheet sheet = wb.getSheetAt(sheetNum);// 
		return sheet;
	}

	/** 
	 * @Title getExcel07Sheet 
	 * @Description 根据编号获取Excel2007文件sheet对象
	 * @param file
	 * @param sheetNum sheet编号,从0开始
	 * @return
	 * @throws Exception
	 */  
	public static XSSFSheet getExcel07Sheet(File file, int sheetNum)
			throws Exception {
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));// 
		XSSFSheet xsheet = xwb.getSheetAt(sheetNum);// 
		return xsheet;
		
	}

	/** 
	 * @Title getCellValueByRowNumAndColNum 
	 * @Description
	 * @param isExcel03
	 * @param hssfSheet
	 * @param xssfSheet
	 * @param sheetNum
	 * @param rowNum
	 * @param colNum
	 * @return
	 * @throws Exception
	 */  
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
	 * @Description 解析Excel文件，把一行的数据解析出来，返回格式化后的字符串数组。（根据不同的版本进行数据的解析）
	 * @param isExcel03
	 * @param hssfSheet
	 * @param xssfSheet
	 * @param sheetNum
	 * @param rowNum
	 * @param colNum
	 * @return
	 * @throws Exception
	 */  
	public static String[] getCellValuesByRow(boolean isExcel03,HSSFSheet hssfSheet,
			XSSFSheet xssfSheet, int sheetNum, int rowNum,int colNum) throws Exception{
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
	
	/** 
	 * @Title get03CellValue 
	 * @Description
	 * @param cell
	 * @param i
	 * @return
	 * @throws ParseException
	 */  
	@SuppressWarnings("unused")
	private static String get03CellValue(HSSFCell cell,int i) throws ParseException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			if (i == 5 || i == 6) { // 处理授权开始、结束时间
				return sdf.format(cell.getDateCellValue());
			}
		} catch (Exception e) {
			return "time format error";
		}
		DecimalFormat df = new DecimalFormat("#.##");
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 数字类型
			return df.format(cell.getNumericCellValue());
		case HSSFCell.CELL_TYPE_STRING: // 字符串类型
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
			return String.valueOf(cell.getBooleanCellValue());
		case HSSFCell.CELL_TYPE_FORMULA: // 公式
			return String.valueOf(cell.getCellFormula());
		case HSSFCell.CELL_TYPE_BLANK: // 空白
			return null;
		case HSSFCell.CELL_TYPE_ERROR: //错误
			return null;
		default:
			return null;
		}
	}
	/** 
	 * @Title get03CellValue 
	 * @Description Excel2003文件根据单元格内容类型转化为字符串
	 * @param cell
	 * @return
	 */  
	private static String get03CellValue(HSSFCell cell) {
		
		DecimalFormat df = new DecimalFormat("#.##");
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 数字类型
			return df.format(cell.getNumericCellValue());
		case HSSFCell.CELL_TYPE_STRING: // 字符串类型
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
			return String.valueOf(cell.getBooleanCellValue());
		case HSSFCell.CELL_TYPE_FORMULA: // 公式类型
			return String.valueOf(cell.getCellFormula());
		case HSSFCell.CELL_TYPE_BLANK: // 空白
			return null;
		case HSSFCell.CELL_TYPE_ERROR: // 错误
			return null;
		default:
			return null;
		}
	}

	/** 
	 * @Title get07CellValue 
	 * @Description Excel2007文件根据单元格内容类型转化为字符串
	 * @param cell
	 * @return
	 */  
	private static String get07CellValue(XSSFCell cell) {
		DecimalFormat df = new DecimalFormat("#.##");
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: // 数字类型
			return df.format(cell.getNumericCellValue());
		case HSSFCell.CELL_TYPE_STRING: // 字符串类型
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
			return String.valueOf(cell.getBooleanCellValue());
		case HSSFCell.CELL_TYPE_FORMULA: // 公式类型
			return String.valueOf(cell.getCellFormula());
		case HSSFCell.CELL_TYPE_BLANK: // 空白
			return null;
		case HSSFCell.CELL_TYPE_ERROR: // 错误
			return null;
		default:
			return null;
		}
	}
	private static final String EXPORT_MESSAGE_FORMAT = "%s,%s,%s,%s,%s,%s,%s,%s\r\n";
	public static void main(String[] args) throws Exception {
		File uploadExcel = new File("D:/data/template.xls");
		HSSFSheet hssfSheet = ExcelUtil.getExcel03Sheet(uploadExcel, 0);//获得03excel的表单
		String[] data = ExcelUtil.getCellValuesByRow(true, hssfSheet, null, 0, 1, 4);
		for(String str : data){
			System.out.println(str);
		}
		
		File csv = new File("D:/data/test.csv");
		String str = "\"第一行\",\"我是大水哥\"!";
		try {
			PrintWriter pw = new PrintWriter(csv,"GBK");
			pw.println(str);
			pw.println(str);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print(String.format(EXPORT_MESSAGE_FORMAT, "书项ID","书项名称","状态","一级分类","二级分类","版式分类","操作员","添加时间"));
		System.out.print(String.format(EXPORT_MESSAGE_FORMAT, "书项ID","书项名称","状态","一级分类","二级分类","版式分类","操作员","添加时间"));
	}
}