package org.ldd.ssm.crm.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.web.servlet.view.document.AbstractJExcelView;


@SuppressWarnings("deprecation")
public class TitleExcelView extends AbstractJExcelView {

	private String[] columnNames = new String[] {"日期","航程","航班号","公司","航线","时刻","班次"
			,"座位总数","每班座位","旅客人数","每班旅客","座位投入比例","承运旅客比例","产投比","团队人数","每班团队","团队成行率","团队比例","客座率"
			,"收益客座率","总收入","每班收入","座公里收入","平均折扣","散客折扣","团队折扣","两舱比例","全价比例","9折比例","8.5折比例","8折比例"
			,"7.5折比例","7折比例","6.5折比例","6折比例","5.5折比例","5折比例","4.5折比例","4折比例","特殊舱比例","R舱比例","U舱比例","I舱比例"
			,"Z舱比例","E舱比例","A舱比例","O舱比例","S舱比例","H舱比例","X舱比例","儿童"};
	private Integer[] columnWidths = new Integer[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,
			10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10};

	@Override
	public void buildExcelDocument(Map<String, Object> map,
			WritableWorkbook work, HttpServletRequest req,
			HttpServletResponse response) {
		OutputStream os = null;
		try {
			String excelName = "模型表头.xls";
			//解决中文文件名的问题
			excelName = new String(excelName.getBytes(), "iso8859-1");
			// 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ excelName);
			os = response.getOutputStream();
			// sheet名称
			String sheetName = "数据模型";

			// 全局设置
			WorkbookSettings setting = new WorkbookSettings();
			java.util.Locale locale = new java.util.Locale("zh", "CN");
			setting.setLocale(locale);
			setting.setEncoding("ISO-8859-1");
			// 创建工作薄
			work = Workbook.createWorkbook(os); // 建立excel文件
			// 创建第一个工作表
			jxl.write.WritableSheet ws = work.createSheet(sheetName, 1); // sheet名称
			// 添加标题
			addColumNameToWsheet(ws);
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
			wlabel1 = new jxl.write.Label(i, 0, colName, wcfFC);
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
