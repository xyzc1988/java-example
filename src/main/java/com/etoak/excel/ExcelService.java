package com.etoak.excel;

import java.io.FileOutputStream;   
import java.io.IOException;   
import java.io.OutputStream;   
import java.sql.ResultSet;   
import java.sql.SQLException;   
import java.util.*;   
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFooter;   
import org.apache.poi.hssf.usermodel.HSSFHeader;   
import org.apache.poi.hssf.usermodel.HSSFRow;   
import org.apache.poi.hssf.usermodel.HSSFSheet;   
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelService {
//  表头  
    public static final String[] tableHeader = {"id","姓名","密码"};   
//  创建工作本  
    public static HSSFWorkbook demoWorkBook = new HSSFWorkbook();   
//  创建表  
    public static HSSFSheet demoSheet = demoWorkBook.createSheet("用户信息");   
//  表头的单元格个数目  
    public static final short cellNumber = (short)tableHeader.length;   
//  数据库表的列数  
    public static final int columNumber = 3;   
    /** 
     * 34.* 创建表头 35.* 
     *  
     * @return 36. 
     */   
    @SuppressWarnings("deprecation")  
    public static void createTableHeader()   
    {  
    	//设置表头，从sheet中得到
        HSSFHeader header = demoSheet.getHeader();   
        header.setCenter("用户表");   
        //创建一行
        HSSFRow headerRow = demoSheet.createRow((short) 0);   
        for(int i = 0;i < cellNumber;i++)   
        {   
        	//创建一个单元格
            HSSFCell headerCell = headerRow.createCell((short) i);   
           // headerCell.setEncoding(HSSFCell.ENCODING_UTF_16); 
//            CellStyle cs = new CellStyle();
            //设置cell的值
            headerCell.setCellValue(tableHeader[i]);   
        }  
    }  
/** 
 * 50.* 创建行 51.* 
 *  
 * @param cells 
 *            52.* 
 * @param rowIndex 
 */   
@SuppressWarnings("deprecation")  
public static void createTableRow(List<String> cells , short rowIndex)   
{   
//  创建第rowIndex行  
    HSSFRow row = demoSheet.createRow((short) rowIndex);   
    for(short i = 0;i < cells.size();i++)   
    {   
//      创建第i个单元格  
        HSSFCell cell = row.createCell((short) i);   
        //cell.setEncoding(HSSFCell.ENCODING_UTF_16);   
        cell.setCellValue(cells.get(i));   
    }   
}   
/** 
 * 68.* 创建整个Excel表 69.* 
 *  
 * @throws SQLException 
 *             70.* 71. 
 */   
public static void createExcelSheeet() throws Exception   
{   
    createTableHeader();   //--->创建一个表头行
    ResultSet rs = SheetDataSource.selectAllDataFromDB();   //--->得到所有数据
    int rowIndex = 1;   
    while(rs.next())   
    {   
        List<String> list = new ArrayList<String>();   
        for(int i = 1;i <= columNumber;i++)   
        {   
            list.add(rs.getString(i));   
        }   
        createTableRow(list,(short)rowIndex);   
        rowIndex++;   
    }   
}   
/** 
 * 89.* 导出表格 90.* 
 *  
 * @param sheet 
 *            91.* 
 * @param os 
 *            92.* 
 * @throws IOException 
 *             93. 
 */   
public void exportExcel(HSSFSheet sheet,OutputStream os) throws IOException   
{   
    sheet.setGridsPrinted(true);   
    HSSFFooter footer = sheet.getFooter();   
    footer.setRight("Page " + HSSFFooter.page() + " of " +   
            HSSFFooter.numPages());   
    demoWorkBook.write(os);   
}   
public static void main(String[] args) {   
    String fileName = "D:\\用户信息.xls";   
    FileOutputStream fos = null;   
    try {   
    	ExcelService pd = new ExcelService();   
    	ExcelService.createExcelSheeet();   
        fos = new FileOutputStream(fileName);   
        pd.exportExcel(demoSheet,fos);   
        JOptionPane.showMessageDialog(null, "表格已成功导出到 : "+fileName);   
    } catch (Exception e) {   
        JOptionPane.showMessageDialog(null, "表格导出出错，错误信息 ："+e+"\n错误原因可能是表格已经打开。");   
        e.printStackTrace();   
    } finally {   
        try {   
            fos.close();   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
}   
}
