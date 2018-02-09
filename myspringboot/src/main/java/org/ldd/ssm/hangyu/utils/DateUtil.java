package org.ldd.ssm.hangyu.utils;

import java.sql.Timestamp;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
    
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 日期的算术操作，可以增加或者减少，可以某一部分进行操作 year--年 month-月 1-12 day-天 1-31 hour -小时 0-23
	 * minute 分钟 0-59 second 秒 0-59 millisecond 毫秒 显示格式，可以任意组合
	 * 
	 * G Era designator Text AD y Year Year 1996; 96 M Month in year Month July;
	 * Jul; 07 w Week in year Number 27 W Week in month Number 2 D Day in year
	 * Number 189 d Day in month Number 10 F Day of week in month Number 2 E Day
	 * in week Text Tuesday; Tue a Am/pm marker Text PM H Hour in day (0-23)
	 * Number 0 k Hour in day (1-24) Number 24 K Hour in am/pm (0-11) Number 0 h
	 * Hour in am/pm (1-12) Number 12 m Minute in hour Number 30 s Second in
	 * minute Number 55 S Millisecond Number 978 z Time zone General time zone
	 * Pacific Standard Time; PST; GMT-08:00 Z Time zone RFC 822 time zone -0800
	 * 
	 * @param srcDate
	 * @param srcFormat
	 * @param destFormat
	 * @param operType
	 * @param operValue
	 * @return
	 */
	public static String evalTime(String srcDate, String srcFormat, String destFormat, String operType, int operValue) {
		if (srcDate == null || srcDate.equals(""))
			return "";
		if (srcFormat == null || srcFormat.equals(""))
			srcFormat = "yyyy-MM-dd";
		if (destFormat == null || destFormat.equals(""))
			destFormat = "yyyy-MM-dd";
		if (operType == null || operType.equals(""))
			operType = "day";

		// Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("AST"));
		Calendar cal = Calendar.getInstance();
		Date currentTime = null;
		try {
			currentTime = (Date) new SimpleDateFormat(srcFormat).parse(srcDate);
		} catch (ParseException e) {
			e.printStackTrace();
			currentTime = new Date();
		}
		cal.setTime(currentTime);
		if (operType.equals("year")) {
			cal.add(Calendar.YEAR, operValue);
		} else if (operType.equals("month")) {
			cal.add(Calendar.MONTH, operValue);
		} else if (operType.equals("day")) {
			cal.add(Calendar.DAY_OF_MONTH, operValue);
		} else if (operType.equals("hour")) {
			cal.add(Calendar.HOUR_OF_DAY, operValue);
		} else if (operType.equals("minute")) {
			cal.add(Calendar.MINUTE, operValue);
		} else if (operType.equals("second")) {
			cal.add(Calendar.SECOND, operValue);
		} else if (operType.equals("millisecond")) {
			cal.add(Calendar.MILLISECOND, operValue);
		}
		String curDay = new SimpleDateFormat(destFormat).format(cal.getTime());
		return curDay;
	}

	/**
	 * 获得当前时间 转换为时间戳类型
	 * 
	 * @param
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp getCurTime2Timestamp() throws ParseException {
		Timestamp t = new Timestamp(System.currentTimeMillis());
		String d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(t);
		Timestamp.valueOf(d2);
		return t;
	}

	/**
	 * 将时间格式化
	 * @return
	 */
	public static Date DatePattern(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
		try {
			String dd=sdf.format(date);
			date = StringToDate(dd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 将时间格式化
	 * */
	public static Date DatePattern(Date date,String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			String dd=sdf.format(date);
			date = StringToDate(dd,pattern);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 日期格式转化
	 * 
	 * @param srcDate
	 * @param srcFormat
	 * @param destFormat
	 * @return
	 */
	public static String dateFormat(String srcDate, String srcFormat, String destFormat) {
		if (srcFormat == null || srcFormat.equals(""))
			srcFormat = "yyyy-MM-dd";
		if (destFormat == null || destFormat.equals(""))
			destFormat = "yyyy-MM-dd";
		if (srcDate == null || srcDate.equals(""))
			return "";

		Calendar cal = Calendar.getInstance();
		Date currentTime = null;
		try {
			currentTime = (Date) new SimpleDateFormat(srcFormat).parse(srcDate);
		} catch (ParseException e) {
			e.printStackTrace();
			currentTime = new Date();
		}
		cal.setTime(currentTime);
		String curDay = new SimpleDateFormat(destFormat).format(cal.getTime());
		return curDay;
	}

	/**
	 * 日期格式转化
	 * 
	 * @param srcDate
	 * @param srcFormat
	 * @param destFormat
	 * @return
	 */
	public static String dateFormat(Date srcDate, String destFormat) {
		if (srcDate == null)
			return "";
		if (destFormat == null || destFormat.equals(""))
			destFormat = "yyyy-MM-dd";
		String curDay = new SimpleDateFormat(destFormat).format(srcDate);
		return curDay;
	}

	/**
	 * 字符串日期转化为java.util.Date
	 * 
	 * @param srcDate
	 * @param srcFormat
	 * @return
	 */
	public static Date StringToDate(String srcDate, String srcFormat) {
		if (srcDate == null || srcDate.equals(""))
			return new Date();
		if (srcFormat == null || srcFormat.equals(""))
			srcFormat = "yyyy-MM-dd";
		Calendar cal = Calendar.getInstance();
		Date currentTime = null;
		try {
			currentTime = (Date) new SimpleDateFormat(srcFormat).parse(srcDate);
		} catch (ParseException e) {
			e.printStackTrace();
			currentTime = new Date();
		}
		cal.setTime(currentTime);
		return cal.getTime();
	}
	
	public static Date StringToDate(String srcDate) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
		try {
			d = sdf.parse(srcDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	public static boolean compare(Date d1, Date d2) {
		if (d1.after(d2)) {
			return true;
		} else {
			return false;
		}
	}

	// ***************************************************
	// 名称：dateToStr
	// 功能：将指定的日期转换成字符串
	// 输入：aDteValue: 要转换的日期;
	// aFmtDate: 转换日期的格式, 默认为:"yyyy/MM/dd"
	// 输出：
	// 返回：转换之后的字符串
	// ***************************************************
	public static String dateToStr(Date aDteValue, String aFmtDate) {
		String strRtn = null;

		if (aFmtDate.length() == 0) {
			aFmtDate = "yyyy/MM/dd";
		}
		Format fmtDate = new SimpleDateFormat(aFmtDate);
		try {
			strRtn = fmtDate.format(aDteValue);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strRtn;
	}

	// 名称：strToDate
	// 功能：将指定的字符串转换成日期
	// 输入：aStrValue: 要转换的字符串;
	// aFmtDate: 转换日期的格式, 默认为:"yyyy/MM/dd"
	// aDteRtn: 转换后的日期
	// 输出：
	// 返回：TRUE: 是正确的日期格式; FALSE: 是错误的日期格式
	// ***************************************************
	public static boolean strToDate(String aStrValue, String aFmtDate, Date aDteRtn) {
		if (aFmtDate.length() == 0) {
			aFmtDate = "yyyy/MM/dd";
		}
		SimpleDateFormat fmtDate = new SimpleDateFormat(aFmtDate);
		try {
			aDteRtn.setTime(fmtDate.parse(aStrValue).getTime());
		} catch (Exception e) {
			//e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 格式化时间（字符串格式：yyyy-MM-dd HH:mm:ss）
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(date);
	}

	/**
	 * 当前时间（字符串格式：yyyy-MM-dd HH:mm:ss）
	 * 
	 * @return
	 */
	public static String currentTime() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(new Date());
	}

	/**
	 * java.util.Date转化为java.sql.Date
	 */
	public static java.sql.Date parseSqlDate(Date date) {
		if (date == null)
			return null;
		return new java.sql.Date(date.getTime());
	}

	/**
	 * java.util.Date转化为java.sql.Timestamp
	 */
	public static Timestamp parseTimestamp(Date date, String format) {
		if (date == null)
			return null;
		long t = date.getTime();
		return new Timestamp(t);
	}

	/**
	 * 日期相减
	 * 
	 * @param date
	 *            日期
	 * @param date1
	 *            日期
	 * @return 返回相减后的日期
	 */
	public static long diffDate(Date date, Date date1) {
		if (date == null)
			return 0;
		if (date1 == null)
			return 0;
		return date.getTime() - date1.getTime();
	}

	/*
	 * 针对年月取出最大的天数 modify by shibc 20090402
	 */
	public static String getMaxDay(String yearmonth) {

		/*
		 * String day=""; int year=0; int month=0;
		 * 
		 * month=Integer.parseInt(yearmonth.substring(4));
		 * year=Integer.parseInt(yearmonth.substring(0, 4));
		 * 
		 * boolean isLeap=isLeapyear(year); if(isLeap==true){
		 * 
		 * switch(month){ case 1: case 3: case 5: case 7:case 8:case 10:case
		 * 12:day="31"; break; case 2:day="29"; break; case 4:case 6:case 9:case
		 * 11:day="30"; } }else{ switch(month){ case 1: case 3: case 5: case
		 * 7:case 8:case 10:case 12:day="31"; break; case 2:day="28"; break;
		 * case 4:case 6:case 9:case 11:day="30"; } }
		 * 
		 * return day;
		 */
		String tmp = evalTime(yearmonth, "yyyyMM", "yyyyMM", "month", 1);
		String tmp2 = tmp + "01";
		String tmp3 = evalTime(tmp2, "yyyyMMdd", "dd", "day", -1);
		return tmp3;
	}

	public boolean isLeapyear(int year) {

		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得当前月份 转换为Long类型
	 * 
	 * @param
	 * @return
	 * @throws ParseException
	 */
	public static Long getCurMonthLong() throws ParseException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		// t_log_echn_servinvoke.setTime_range(Long.valueOf(sdf.format(date)));
		return Long.parseLong(sdf.format(date));
	}

	public static String date2Str(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

//	/**
//	 * 比较时间是否在这两个时间点之间
//	 * 
//	 * @param time1
//	 * @param time2
//	 * @return
//	 */
//	public static boolean checkTime(String time1, String time2) {
//		Calendar calendar = Calendar.getInstance();
//		Date date1 = calendar.getTime();
//		Date date11 = DateUtil.StringToDate(DateUtil.dateToStr(date1, "yyyy-MM-dd") + " " + time1, DEFAULT_PATTERN);// 起始时间
//		Calendar c = Calendar.getInstance();
//		c.add(Calendar.DATE, 1);
//		Date date2 = c.getTime();
//		Date date22 = DateUtil.StringToDate(DateUtil.dateToStr(date2, "yyyy-MM-dd") + " " + time2, DEFAULT_PATTERN);// 终止时间
//		Calendar scalendar = Calendar.getInstance();
//		scalendar.setTime(date11);// 起始时间
//		System.out.println("scalendar1=="+scalendar.getTime());
//		Calendar ecalendar = Calendar.getInstance();
//		ecalendar.setTime(date22);// 终止时间
//		System.out.println("ecalendar=="+ecalendar.getTime());
//		Calendar calendarnow = Calendar.getInstance();
//		if (calendarnow.after(scalendar) && calendarnow.before(ecalendar)) {
//			return true;
//		} else {
//			return false;
//		}
//
//	}
	
	/**
	 * 比较时间是否在这两个时间点之间
	 * 
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static boolean checkTimeByday(String time1, String time2) {
		Calendar calendar = Calendar.getInstance();
		Date date1 = calendar.getTime();
		Date date11 = DateUtil.StringToDate(DateUtil.dateToStr(date1, "yyyy-MM-dd") + " " + time1, DEFAULT_PATTERN);// 起始时间
		Calendar c = Calendar.getInstance();
		Date date2 = c.getTime();
		Date date22 = DateUtil.StringToDate(DateUtil.dateToStr(date2, "yyyy-MM-dd") + " " + time2, DEFAULT_PATTERN);// 终止时间
		Calendar scalendar = Calendar.getInstance();
		scalendar.setTime(date11);// 起始时间
		//System.out.println("scalendar1=="+scalendar.getTime());
		Calendar ecalendar = Calendar.getInstance();
		ecalendar.setTime(date22);// 终止时间
		//System.out.println("ecalendar=="+ecalendar.getTime());
		Calendar calendarnow = Calendar.getInstance();
		if (calendarnow.after(scalendar) && calendarnow.before(ecalendar)) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * 取输入时间前几天或后几天的时间，以String类型返回
	 * @param date
	 * @param i
	 * @param form
	 * @return
	 */
	public static String getFormerOrBehindDate(Date date,int i,String form){
		String dateString="";
		Date tempDate  = date;
		try {
			Calendar temp=Calendar.getInstance();
			temp.setTime(tempDate);
			temp.add(Calendar.DATE, i);
			SimpleDateFormat sdfData=new SimpleDateFormat(form);
			dateString = sdfData.format(temp.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			Date dd = new Date();
			return DateUtil.date2Str(dd,"yyyyMMddHHmmss");
		}
	    return dateString;
	}
	
	/**
	 * 取输入时间前几天或后几天的时间，以String类型返回
	 * @param date
	 * @param i
	 * @param form
	 * @return
	 */
	public static String getFormerOrBehindMarch(Date date,int i,String form){
		String dateString="";
		Date tempDate  = date;
		try {
			Calendar temp=Calendar.getInstance();
			temp.setTime(tempDate);
			temp.add(Calendar.MARCH, i);
			SimpleDateFormat sdfData=new SimpleDateFormat(form);
			dateString = sdfData.format(temp.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			Date dd = new Date();
			return DateUtil.date2Str(dd,"yyyyMMddHHmmss");
		}
	    return dateString;
	}
	

	/**************************************** 需要测试end ************************************/

	public static void main(String[] args) {
//         Date date = new Date();
//         System.out.println(date2Str(date, "yyyyMMddhhmmss"));
//         String form ="yyyy-MM-dd HH:mm:ss";
//         String dd = "20100120";
//         date = StringToDate(dd,"yyyyMMdd");
//         String tempString = DateUtil.getFormerOrBehindMarch(date, 0, "MM");
//         System.out.println("tempString="+tempString);
         
//         String ssString = "201002";
//         String i = DateUtil.getMaxDay(ssString);
//         System.out.println("i="+i);
       // Date date2 = DateUtil.StringToDate(tempString,"yyyyMM");
//        Date tempdate = DateUtil.StringToDate("200912","yyyyMM");
//        String ss = DateUtil.getMaxDay("200910");
//        System.out.println("ss="+ss);
// 		boolean bool = DateUtil.compare(date, tempdate);
// 		System.out.println("bool="+bool);
		
		String date1 = "08:00:00";
		String date2 = "17:00:00";
		System.out.println(DateUtil.checkTimeByday(date1,date2));
		
	}
}

