package org.ldd.ssm.crm.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

public class ParserDataUtils {

	public static List<String[]> imp(File upload) throws Exception {
		// 准备一个装数据的集合
		List<String[]> list = new ArrayList<String[]>();
		
		// 获取xls文件
		char[] name = upload.getName().toCharArray();
		if(!"xls".equals(name[name.length-3]+""+name[name.length-2]+""+name[name.length-1])){
			return null;
		}
		Workbook workbook = Workbook.getWorkbook(upload);
		Sheet[] sheets = workbook.getSheets();
		for (Sheet sheet : sheets) {
			for (int i = 1; i < sheet.getRows(); i++) {
				// //每次循环都准备一个数据, 装每行的数据
				String[] strings = new String[sheet.getColumns()];
				// 循环列
				for (int j = 0; j < sheet.getColumns(); j++) {
					// 获取内容:j列 i行的数据
					Cell cell = sheet.getCell(j, i);
					if(cell.getType()==CellType.DATE){
						DateCell date = (DateCell)cell;
						Date date2 = date.getDate();
						String format = new SimpleDateFormat("yyyy-MM-dd").format(date2);
						strings[j]=format;
						continue;
					}
					// 将数据放入数组的指定位置
					strings[j] = cell.getContents();
				}
				// //将数组添加进集合
				list.add(strings);
			}
		}
		 //关闭流
		workbook.close();
		 //返回集合
		return list;
	}
}