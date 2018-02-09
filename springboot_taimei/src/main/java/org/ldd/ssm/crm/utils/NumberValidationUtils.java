package org.ldd.ssm.crm.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则判断是否负数,是否小数, 是否数值
 * 
 * @author Taimei
 * 
 */
public class NumberValidationUtils {
	private static boolean isMatch(String regex, String orginal) {
		try {
			if (orginal == null || orginal.trim().equals("")) {
				return false;
			}
			Pattern pattern = Pattern.compile(regex);
			Matcher isNum = pattern.matcher(orginal);
			return isNum.matches();
		} catch (Exception e) {
			return false;
		}

	}
	/**
	 * 纠正数据,以及字符类型
	 * @param orginal
	 * @return
	 */
	public static Double isDecimal(String orginal) {
		String replaceAll = orginal.replaceAll("[^0-9\\.\\-]", "");
		if (replaceAll == "") {
			replaceAll = "0";
		}
		try {
			if (isMatch("-?[0-9]*.?[0-9]*", replaceAll)) {
				return Double.valueOf(replaceAll);
			} else {
				return 0.0;
			}
		} catch (Exception e) {
			return 0.0;
		}
	}
}
