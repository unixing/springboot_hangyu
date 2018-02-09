package org.ldd.ssm.crm.web;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.ldd.ssm.crm.utils.ExcelUtil;
import org.springframework.web.servlet.view.document.AbstractExcelView;
public class ViewExcel extends AbstractExcelView {  
    @SuppressWarnings("deprecation")
	@Override  
    protected void buildExcelDocument(Map<String, Object> obj,  
            HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)  
            throws Exception {  
        HSSFSheet sheet = workbook.createSheet("list");    
        sheet.setDefaultColumnWidth((short) 12);    
        HSSFCell cell = getCell(sheet, 0, 0);    
        setText(cell, "Spring Excel test");    
//        HSSFCellStyle dateStyle = workbook.createCellStyle();    
        //dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("mm/dd/yyyy"));    
        cell = getCell(sheet, 1, 0);    
        cell.setCellValue("日期：2008-10-23");    
        //cell.setCellStyle(dateStyle);    
        getCell(sheet, 2, 0).setCellValue("测试1");    
        getCell(sheet, 2, 1).setCellValue("测试2");  
        HSSFRow sheetRow = sheet.createRow(3);    
        for (short i = 0; i < 10; i++) {    
            sheetRow.createCell(i).setCellValue(i * 10);    
        }    
        String filename = "测试.xls";//设置下载时客户端Excel的名称     
        filename = ExcelUtil.encodeFilename(filename, request);//处理中文文件名  
        response.setContentType("application/vnd.ms-excel");     
        response.setHeader("Content-disposition", "attachment;filename=" + filename);     
        OutputStream ouputStream = response.getOutputStream();     
        workbook.write(ouputStream);     
        ouputStream.flush();     
        ouputStream.close();     
    }  
}
