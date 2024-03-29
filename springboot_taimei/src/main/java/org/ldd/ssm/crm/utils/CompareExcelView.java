package org.ldd.ssm.crm.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.beanutils.PropertyUtils;
import org.ldd.ssm.crm.domain.TicketInfo;
import org.ldd.ssm.crm.query.TicketInfoCompare;
import org.springframework.web.servlet.view.document.AbstractJExcelView;

/**
 * 票面上传比对结果导出
 * @author wxm
 *
 * @date 2017-3-10
 */
@SuppressWarnings("deprecation")
public class CompareExcelView extends AbstractJExcelView{


	private String[] columnNames = new String[] {
			"航班日期","航班号","航段","票号","票款","","航班日期","航班号","航段","票号","票款"};

	private String[] dbColumnNames = new String[] { 
			"flightDate", "flightNum","leg","ticketNum","ticketPri","",
			"flightDate", "flightNum","leg","ticketNumDetail","ticketPri"};

	private Integer[] columnWidths = new Integer[] {
			20, 20, 20, 20, 20, 
			5, 20, 20, 20, 20,20, 20, 20, 20, 20, 20, 20, 20, 20  };

	private Map<String,Object> map;
	
	public CompareExcelView(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	public void buildExcelDocument(Map<String, Object> dataMap,WritableWorkbook work, HttpServletRequest req,HttpServletResponse response) {
		OutputStream os = null;
		try {
			String excelName = "票面对比详情.xls";
			//解决中文文件名的问题
			excelName = new String(excelName.getBytes(), "iso8859-1");
			// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ excelName);
			os = response.getOutputStream();
			work.setColourRGB(Colour.LIGHT_ORANGE, 0xE2, 0x93, 0x7A);
			// 全局设置
			WorkbookSettings setting = new WorkbookSettings();
			java.util.Locale locale = new java.util.Locale("zh", "CN");
			setting.setLocale(locale);
			setting.setEncoding("ISO-8859-1");
			@SuppressWarnings("unchecked")
			List<TicketInfoCompare> list = (List<TicketInfoCompare>) dataMap.get("list");
			String sheetNames ="对比表";
			work = Workbook.createWorkbook(os); // 建立excel文件
			// 创建工作薄
			jxl.write.WritableSheet ws = work.createSheet(sheetNames,1); // sheet名称
			// 添加标题
			addColumNameToWsheet(ws);
			// 创建工作薄内容
			writeContext(ws,list);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// 写入文件
			try {
				work.write();
				work.close();
				os.flush();
				os.close();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
	}

	private void writeContext(WritableSheet wsheet, List<TicketInfoCompare> list) {
		//拿到数据长度
		int rows = list.size();
		//jsl对象
		jxl.write.Label wlabel = null;
		//设置格式
//		jxl.write.WritableCellFormat wcf = getFormat();
		//拿到key的长度
		int cols = dbColumnNames.length;
		//列的名
		String columnName = null;
		//基类
		Object value = null;
		jxl.write.NumberFormat nf= new jxl.write.NumberFormat("#0.00");
		jxl.write.WritableCellFormat wcf=new jxl.write.WritableCellFormat(nf);
		try {
			WritableCellFormat center = new WritableCellFormat(); 
	        // 设置居中 
			center.setAlignment(Alignment.CENTRE); 
			int j;
			//循环写入数据
			 	WritableFont font1 = new WritableFont(WritableFont.ARIAL,14,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.WHITE);  
			 	WritableCellFormat wc = new WritableCellFormat(font1); 
		        // 设置居中 
		        wc.setAlignment(Alignment.CENTRE); 
		        // 设置边框线 
		        // 设置单元格的背景颜色 
		        wc.setBackground(jxl.format.Colour.BLUE_GREY); 
			
			//第一行 写系统数据和上传数据
				 wsheet.mergeCells(0,0,4,0); 
			     wlabel = new jxl.write.Label(0,0, "上传数据",wc); 
			     wsheet.addCell(wlabel);
			//第一行 写系统数据和上传数据
				 wsheet.mergeCells(6,0,10,0); 
			     wlabel = new jxl.write.Label(6,0,"系统数据",wc); 
			     wsheet.addCell(wlabel);
			for (int i = 0; i < rows; i++) {
				//拿到数据集合中的数据
				TicketInfo each = list.get(i).getTicketInfoImport();
				TicketInfo eachSystem = list.get(i).getTicketInfoSystem();
				for (j=0; j < cols/2; j++) {
					//拿到每天列数据的key
					if(j!=5){
						columnName = dbColumnNames[j];
						if(null!=each){
							if(columnName.equals("ticketPri")&&null!=eachSystem){
								WritableCellFormat formateRed = new WritableCellFormat(); 
						        // 设置居中 
								formateRed.setAlignment(Alignment.CENTRE); 
						        // 设置边框线 
								formateRed.setBorder(Border.ALL, BorderLineStyle.THIN); 
						        // 设置单元格的背景颜色 
								formateRed.setBackground(jxl.format.Colour.LIGHT_ORANGE);
								//拿到每条数据的值
								value = PropertyUtils.getProperty(each, columnName);
								if(value instanceof String){
									wlabel = new jxl.write.Label(j, (i + 2), value + "",formateRed);
									wsheet.addCell(wlabel);
								}else if(value instanceof Integer || value instanceof BigDecimal|| value instanceof Double){
									jxl.write.Number labelNf= new jxl.write.Number(j, (i + 2),Double.valueOf(value.toString()),formateRed);
									wsheet.addCell(labelNf);
								}
							}else{
								//拿到每条数据的值
								value = PropertyUtils.getProperty(each, columnName);
								if(value instanceof String){
									wlabel = new jxl.write.Label(j, (i + 2), value + "");
									wsheet.addCell(wlabel);
								}else if(value instanceof Integer || value instanceof BigDecimal|| value instanceof Double){
									jxl.write.Number labelNf= new jxl.write.Number(j, (i + 2),Double.valueOf(value.toString()),wcf);
									wsheet.addCell(labelNf);
								}
							}
						}else{
							 wsheet.mergeCells(j,i+2,j+4,i+2); 
						     wlabel = new jxl.write.Label(j,i+2,"无对应票面数据",center); 
						     wsheet.addCell(wlabel);
						     break;
						}
					}
				}
				for (j = cols/2; j < cols; j++) {
					//拿到每天列数据的key
					if(j!=5){
						columnName = dbColumnNames[j];
						if(null!=eachSystem){
							if(columnName.equals("ticketPri")&&null!=each){//标注不一样的价钱
								WritableCellFeatures cellFeatures = new WritableCellFeatures();  
								cellFeatures.setComment("包含联程，数据可能有误差");  
								WritableCellFormat formateRed = new WritableCellFormat(); 
						        // 设置居中 
								formateRed.setAlignment(Alignment.CENTRE); 
								 // 设置边框线 
								formateRed.setBorder(Border.ALL, BorderLineStyle.THIN); 
						        // 设置单元格的背景颜色 
								formateRed.setBackground(jxl.format.Colour.LIGHT_ORANGE);
								//拿到每条数据的值
								value = PropertyUtils.getProperty(eachSystem, columnName);
								if(value instanceof String){
									wlabel = new jxl.write.Label(j, (i + 2), value + "",formateRed);
									wsheet.addCell(wlabel);
								}else if(value instanceof Integer || value instanceof BigDecimal|| value instanceof Double){
									jxl.write.Number labelNf= new jxl.write.Number(j, (i + 2),Double.valueOf(value.toString()),formateRed);
									if(eachSystem.getFlag().equals("1")&&each.getFlag().equals("1")){
										labelNf.setCellFeatures(cellFeatures);
									}
									wsheet.addCell(labelNf);
								}
							}else{
								//拿到每条数据的值
								value = PropertyUtils.getProperty(eachSystem, columnName);
								if(value instanceof String){
									wlabel = new jxl.write.Label(j, (i + 2), value + "");
									wsheet.addCell(wlabel);
								}else if(value instanceof Integer || value instanceof BigDecimal|| value instanceof Double){
									jxl.write.Number labelNf= new jxl.write.Number(j, (i + 2),Double.valueOf(value.toString()),wcf);
									wsheet.addCell(labelNf);
								}
							}
						}else{
							 wsheet.mergeCells(j,i+2,j+4,i+2); 
						     wlabel = new jxl.write.Label(j,i+2,"无对应票面数据",center); 
						     wsheet.addCell(wlabel);
						     break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 添加标题样式
	private void addColumNameToWsheet(jxl.write.WritableSheet wsheet)
			throws RowsExceededException, WriteException {

		// 设置excel标题
		jxl.write.WritableFont wfont = getFont();
		if (null == wfont) {
			wfont = new WritableFont(WritableFont.ARIAL,
					WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD);

		}
		jxl.write.WritableCellFormat wcfFC = getFormat();
		if (null == wcfFC) {
			wcfFC = new jxl.write.WritableCellFormat(wfont);
			try {
				wcfFC.setWrap(true);// 自动换行
				wcfFC.setAlignment(Alignment.CENTRE);
				wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置对齐方式
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}

		jxl.write.Label wlabel1 = null;
		String[] columNames = columnNames;
		if (null == columNames)
			return;
		int colSize = columNames.length;

		Integer[] colsWidth = columnWidths;
		if (null == colsWidth) {
			colsWidth = new Integer[colSize];
			for (int i = 0; i < colSize; i++) {
				colsWidth[i] = 20;
			}
		}

		int temp = 0;
		String colName = null;
		for (int i = 0; i < colSize; i++) {
			colName = columNames[i];
			if (null == colName || "".equals(colName))
				colName = "";
			wlabel1 = new jxl.write.Label(i, 1, colName, wcfFC);
			wsheet.addCell(wlabel1);
			temp = colsWidth[i].intValue();
			// 默认设置列宽
			temp = temp == 0 ? 20 : temp;
			wsheet.setColumnView(i, temp);
		}

	}

	// 设置格式
	private WritableCellFormat getFormat() {

		jxl.write.WritableFont wfont = getFont();
		jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(
				wfont);
		try {
			wcfFC.setWrap(true);
			wcfFC.setAlignment(Alignment.CENTRE);
			wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wcfFC;
	}

	// 设置字体
	private WritableFont getFont() {
		return new WritableFont(WritableFont.ARIAL,
				WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD);
	}

}
