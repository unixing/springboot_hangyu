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
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.beanutils.PropertyUtils;
import org.ldd.ssm.crm.query.AirportInfoData;
import org.ldd.ssm.crm.query.ThroughputOrGdp;
import org.springframework.web.servlet.view.document.AbstractJExcelView;

/**
 * 航班旅客信息票面信息列表导出
 * @author wxm
 *
 * @date 2017-3-10
 */
@SuppressWarnings("deprecation")
public class AirportCompareExportExcel extends AbstractJExcelView{


	private String[] columnNames = new String[] {
			"机场","飞行区等级","跑道（条）","城市","最近四年机场吞吐量及增长率","","","","最近四年城市GDP及增长率","","","","城市人口","旅客资源","基地航司"};

	private String[] dbColumnNames = new String[] { 
			"airPortName", "airfieldLvl", "runwayArticleNumber", "city","throughputs","throughputs","throughputs","throughputs",
			"gdps","gdps","gdps","gdps","cityPgeNumber","touristResources","baseNavigationDep"};

	private Integer[] columnWidths = new Integer[] {
			20, 20, 20, 20, 20, 
			20, 20, 20, 20, 20,20, 20, 20, 20, 20, 20, 20, 20, 20  };

	private Map<String,Object> map;
	
	public AirportCompareExportExcel(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	public void buildExcelDocument(Map<String, Object> dataMap,WritableWorkbook work, HttpServletRequest req,HttpServletResponse response) {
		OutputStream os = null;
		try {
			String excelName = "机场对比表.xls";
			//解决中文文件名的问题
			excelName = new String(excelName.getBytes(), "iso8859-1");
			// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ excelName);
			os = response.getOutputStream();

			// 全局设置
			WorkbookSettings setting = new WorkbookSettings();
			java.util.Locale locale = new java.util.Locale("zh", "CN");
			setting.setLocale(locale);
			setting.setEncoding("ISO-8859-1");
			@SuppressWarnings("unchecked")
			List<AirportInfoData> list = (List<AirportInfoData>) dataMap.get("list");
			@SuppressWarnings("unchecked")
			String sheetNames ="机场对比表";
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

	private void writeContext(WritableSheet wsheet, List<AirportInfoData> list) {
		//拿到数据长度
		int rows = list.size();//列
		//jsl对象
		jxl.write.Label wlabel = null;
		//设置格式
//		jxl.write.WritableCellFormat wcf = getFormat();
		//拿到key的长度
		int cols = dbColumnNames.length;//行
		//列的名
		String columnName = null;
		//基类
		Object value = null;
		jxl.write.NumberFormat nf= new jxl.write.NumberFormat("#0.00");
		jxl.write.WritableCellFormat wcf=new jxl.write.WritableCellFormat(nf);
		try {
			//循环写入数据
			for (int i = 0; i < rows; i++) {
				//拿到数据集合中的数据
				AirportInfoData each = list.get(i);				
				for (int j = 0; j < cols; j++) {
					//拿到每天列数据的key
					columnName = dbColumnNames[j];
					if(columnName.equals("throughputs")||columnName.equals("gdps")){
						int k=j;
						//拿到每条数据的值
						@SuppressWarnings("unchecked")
						List<ThroughputOrGdp> values = (List<ThroughputOrGdp>) PropertyUtils.getProperty(each, columnName);
						 for(ThroughputOrGdp t:values){
							 String s=t.getYear()+"年:"+t.getData()+"("+t.getGrowthRate()+")";
							 wlabel = new jxl.write.Label((i + 1),k++, s);
							 wsheet.addCell(wlabel);
						 }
						 j=j+3;
					}else{
						//拿到每条数据的值
						value = PropertyUtils.getProperty(each, columnName);
						if(value instanceof String){
							wlabel = new jxl.write.Label((i + 1),j, value + "");
							wsheet.addCell(wlabel);
						}else if(value instanceof Integer || value instanceof BigDecimal|| value instanceof Double){
							jxl.write.Number labelNf= new jxl.write.Number((i + 1),j,Double.valueOf(value.toString()),wcf);
							wsheet.addCell(labelNf);
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
			wlabel1 = new jxl.write.Label(0, i, colName, wcfFC);
			wsheet.addCell(wlabel1);
			temp = colsWidth[i].intValue();
			// 默认设置列宽
			temp = temp == 0 ? 30 : temp;
			wsheet.setColumnView(i, temp);
		}
		wsheet.mergeCells(0,4,0,7); 
		wsheet.mergeCells(0,8,0,11); 

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
