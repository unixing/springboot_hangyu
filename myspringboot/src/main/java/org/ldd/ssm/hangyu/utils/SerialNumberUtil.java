package org.ldd.ssm.hangyu.utils;

import java.util.Date;

public class SerialNumberUtil {
	
	public static String createSeriaNumber(String type,String lastNum){
		String date=DateUtil.date2Str(new Date(),"yyyyMMdd");
		if (type.length()>2) {
			type=type.substring(0, 2);
		}
		String seriaNumber=null;
		if (lastNum.length()==1) {
			seriaNumber=date+type+"0000"+lastNum;
		}
		else if (lastNum.length()==2) {
			seriaNumber=date+type+"000"+lastNum;
		}
		else if (lastNum.length()==3) {
			seriaNumber=date+type+"00"+lastNum;
		}
		else if (lastNum.length()==4) {
			seriaNumber=date+type+"0"+lastNum;
		}
		else {
			seriaNumber=date+type+lastNum;
		}
		return seriaNumber;
	}
}
