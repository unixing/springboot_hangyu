package org.ldd.ssm.crm.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
/**
 * 创建一个保存历史信息的文件1111
 * @author Taimei
 *
 */
public class CreateRecordFile {
	public static void CreateNewFile(List<String> list) {
		StringBuffer buffer = new StringBuffer();
		String string = UUID.randomUUID().toString();
		FileWriter file;
		try {
			File file2 = new File("C:\\Users\\Taimei\\Desktop\\" + string
					+ ".txt");
			if (!file2.exists()) {
				file2.createNewFile();
			}
			file = new FileWriter(file2);
			for (String tableName : list) {
				buffer.append(tableName);
			}
			file.write(buffer.toString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}