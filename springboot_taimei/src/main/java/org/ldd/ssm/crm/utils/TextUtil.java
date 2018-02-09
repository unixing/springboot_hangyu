package org.ldd.ssm.crm.utils;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeSet;

import org.ldd.ssm.crm.domain.AirLineAllInfo;
import org.ldd.ssm.crm.domain.Airdiscount;
import org.ldd.ssm.crm.domain.AllAirLineFltDetailData;
import org.ldd.ssm.crm.domain.ClassRanking;
import org.ldd.ssm.crm.domain.EveryDuanInfo;
import org.ldd.ssm.crm.domain.FlightAirline;
import org.ldd.ssm.crm.domain.HomePageData;
import org.ldd.ssm.crm.domain.TotalFly;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.HomePageQuery;
/**
 * 工具类
 * @Title:TextUtil 
 * @Description:
 * @author taimei-gds 
 * @date 2016-4-26 下午2:23:40
 */
public class TextUtil {
	/**
	 * 判断字符串是否为空
	 * @Title: isEmpty 
	 * @Description:  
	 * @param @param str
	 * @param @return    
	 * @return boolean     
	 * @throws
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0 || "".equals(str)||"null".equals(str)) { 
			return true;
		}
		return false;
	}
	/**
	 * 判定是否为数字
	 * @Title: isNum 
	 * @Description:  
	 * @param @param str
	 * @param @return    
	 * @return boolean     
	 * @throws
	 */
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	/**
	 * 得到该月的5周的日期
	 * @Title: getWeekOfMonth 
	 * @Description:  
	 * @param @param year
	 * @param @param month
	 * @param @return    
	 * @return Map<String,List<String>>     
	 * @throws
	 */
	public static Map<String, List<String>> getWeekOfMonth(int year,int month){
		Map<String, List<String>> monthMap = new HashMap<String, List<String>>();
        for (int i = 1; i <= 5 ; i++){
        	String date = countData(year,month,i) ;  
        	String [] str = date.split("-");
        	int start = Integer.parseInt(str[0]);
        	int end = Integer.parseInt(str[1]);
        	List<String> timeList = new ArrayList<String>();
        	for(int j =start ;j<=end;j++){
        		String startTime = year +"-"+ month +"-"+ j;
        		timeList.add(startTime);
        	}
        	monthMap.put(i+"", timeList);
        }
		return monthMap;
	}
	/**
	 * 根据日期转换对应的星期
	 * @Title: getWeekOfDate 
	 * @Description:  
	 * @param @param date
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public static String getWeekOfDate(Date date) {      
	    String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};        
	    Calendar calendar = Calendar.getInstance();      
	    if(date != null){        
	       calendar.setTime(date);      
	    }        
	    int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;      
	    if (w < 0){        
	        w = 0;      
	    }      
	    return weekOfDays[w];    
	}
	public static String getWeekOfDate3(Date date) {      
		String[] weekOfDays = {"日", "一", "二", "三", "四", "五", "六"};        
		Calendar calendar = Calendar.getInstance();      
		if(date != null){        
			calendar.setTime(date);      
		}        
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;      
		if (w < 0){        
			w = 0;      
		}      
		return weekOfDays[w];    
	}
	public static String getWeekOfDate2(Date date) {      
		String[] weekOfDays = {"7", "1", "2", "3", "4", "5", "6"};        
		Calendar calendar = Calendar.getInstance();      
		if(date != null){        
			calendar.setTime(date);      
		}        
		int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;      
		if (w < 0){        
			w = 0;      
		}      
		return weekOfDays[w];    
	}
	 //判断闰年
    public static boolean isLeapYear(int year){
        return (year%4 == 0 && year%100 != 0) || (year%400 == 0);
    }
    //计算日期
    public static String countData(int year,int month,int weekend){
    	 int leapYear[] = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};   //闰年每月天数
 	    int commonYear[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //平年每月天数
 	    int start, end;
 	    int day, weekday, allDays=0;
         //分别表示当月天数、当月一号是星期几、1900.1.1到当前输入日期之间的天数
         day = isLeapYear(year)?leapYear[month]:commonYear[month];
         if(weekend > (day==28?4:5)){
             return null;
         }
         //计算天数
         for(int i=1900; i<year; i++){
             allDays += isLeapYear(i)?366:365;
         }
         for(int i=1; i<month; i++){
             allDays += isLeapYear(year)?leapYear[i]:commonYear[i];
         }
         //计算星期几
         weekday = 1+allDays%7;
          
         //计算第weekend周的开始和结束
         if(weekend == 1){
             start = 1;
             end = 8 - weekday;
         }
         else{
             start = (weekend-2)*7 + (9-weekday);
             end = start + 6;
             if(end > day)
                 end = day;
         }
         return start+"-"+end;
    }
    /**
     * 得到指定日期对应的月份的最大天数
     * @Title: getDayOfMonth 
     * @Description:  
     * @param @param date
     * @param @return    
     * @return int     
     * @throws
     */
    public static int getMaxDayOfMonth(String date){
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		String [] dates = date.split("-");
		aCalendar.set(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]) - 1,1);
		int maxDay = aCalendar.getActualMaximum(Calendar.DATE);
		return maxDay;
	}
    
    //得到夏秋起始日期
  	public static Map<String,String> getXQDate(int year){
  		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  	 	Map<String,String> timeMap = new HashMap<String, String>();
  	 	String startTime = "";
  		String endTime = "";
  		Map<String, List<String>>  dateMap = TextUtil.getWeekOfMonth(year, 3);
  		List<String> dateStr = dateMap.get("5");
  		for (String date : dateStr) {
  			try {
  				String week = TextUtil.getWeekOfDate(df.parse(date));
  				if("星期日".equals(week)){
  					startTime = date;
  				}
  			} catch (ParseException e) {
  				e.printStackTrace();
  			}
  		}
  		if("".equals(startTime)){
  			List<String> dateStr2 = dateMap.get("4");
  			for (String date : dateStr2) {
  				try {
  					String week = TextUtil.getWeekOfDate(df.parse(date));
  					if("星期日".equals(week)){
  						startTime = date;
  					}
  				} catch (ParseException e) {
  					e.printStackTrace();
  				}
  			}
  		}
  		Map<String, List<String>>  dateMap2 = TextUtil.getWeekOfMonth(year, 10);
  		List<String> dateStr3 = dateMap2.get("5");
  		for (String date : dateStr3) {
  			try {
  				String week = TextUtil.getWeekOfDate(df.parse(date));
  				if("星期日".equals(week)){
  					endTime = addDateDay(date, -1);
  				}
  			} catch (ParseException e) {
  				e.printStackTrace();
  			}
  		}
  		if("".equals(endTime)){
  			List<String> dateStr4 = dateMap2.get("4");
  			for (String date : dateStr4) {
  				try {
  					String week = TextUtil.getWeekOfDate(df.parse(date));
  					if("星期日".equals(week)){
  						endTime = addDateDay(date, -1);
  					}
  				} catch (ParseException e) {
  					e.printStackTrace();
  				}
  			}
  		}
  		String [] str = endTime.split("-");
  		endTime = str[0]+"-"+str[1]+"-"+((Integer.parseInt(str[2])-1)+"");
  		timeMap.put("startTime", startTime);
  		timeMap.put("endTime", TextUtil.addDateDay(endTime, 1));
  		return timeMap;
  	}
  	//得到冬春起始数据
  	public static Map<String,String> getDCDate(int year){
  		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  		Map<String,String> timeMap = new HashMap<String, String>();
  		String startTime = "";
  		String endTime = "";
  		Map<String, List<String>>  dateMap = TextUtil.getWeekOfMonth(year, 10);
  		List<String> dateStr = dateMap.get("5");
  		for (String date : dateStr) {
  			try {
  				String week = TextUtil.getWeekOfDate(df.parse(date));
  				if("星期日".equals(week)){
  					startTime = date;
  				}
  			} catch (ParseException e) {
  				e.printStackTrace();
  			}
  		}
  		if("".equals(startTime)){
  			List<String> dateStr2 = dateMap.get("4");
  			for (String date : dateStr2) {
  				try {
  					String week = TextUtil.getWeekOfDate(df.parse(date));
  					if("星期日".equals(week)){
  						startTime = date;
  					}
  				} catch (ParseException e) {
  					e.printStackTrace();
  				}
  			}
  		}
  		Map<String, List<String>>  dateMap2 = TextUtil.getWeekOfMonth(year+1, 3);
  		List<String> dateStr3 = dateMap2.get("5");
  		for (String date : dateStr3) {
  			try {
  				String week = TextUtil.getWeekOfDate(df.parse(date));
  				if("星期日".equals(week)){
  					endTime = addDateDay(date, -1);
  				}
  			} catch (ParseException e) {
  				e.printStackTrace();
  			}
  		}
  		if("".equals(endTime)){
  			List<String> dateStr4 = dateMap2.get("4");
  			for (String date : dateStr4) {
  				try {
  					String week = TextUtil.getWeekOfDate(df.parse(date));
  					if("星期日".equals(week)){
  						endTime = addDateDay(date, -1);
  					}
  				} catch (ParseException e) {
  					e.printStackTrace();
  				}
  			}
  		}
  		String [] str = endTime.split("-");
  		endTime = str[0]+"-"+str[1]+"-"+((Integer.parseInt(str[2])-1)+"");
  		timeMap.put("startTime", startTime);
  		timeMap.put("endTime", TextUtil.addDateDay(endTime, 1));
  		return timeMap;
  	}
  	/**
  	 * 月份加多少个月
  	 * @Title: dateFormat 
  	 * @Description:  
  	 * @param @param datetime
  	 * @param @return    
  	 * @return String     
  	 * @throws
  	 */
  	 public static String addDateMonth(String datetime,int i) {  
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
         Date date = null;  
         try {  
             date = sdf.parse(datetime);  
         } catch (ParseException e) {  
             e.printStackTrace();  
         }  
         Calendar cl = Calendar.getInstance();  
         cl.setTime(date);  
         cl.add(Calendar.MONTH, i);  
         date = cl.getTime();  
         return sdf.format(date);  
     }  
  	 /**
  	  * 日期加多少天
  	  * @Title: dateFormat 
  	  * @Description:  
  	  * @param @param datetime
  	  * @param @return    
  	  * @return String     
  	  * @throws
  	  */
  	 public static String addDateDay(String datetime,int i) {  
  		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
  		 Date date = null;  
  		 try {  
  			 date = sdf.parse(datetime);  
  		 } catch (ParseException e) {  
  			 e.printStackTrace();  
  		 }  
  		 Calendar cl = Calendar.getInstance();  
  		 cl.setTime(date);  
  		 cl.add(Calendar.DAY_OF_MONTH, i);  
  		 date = cl.getTime();  
  		 return sdf.format(date);  
  	 }  
  	 /**
  	  * 字符串去重
  	  * @Title: removeRepeated 
  	  * @Description:  
  	  * @param @param str
  	  * @param @return    
  	  * @return String     
  	  * @throws
  	  */
  	public static String removeRepeated(String str){
  		TreeSet<String> noReapted = new TreeSet<String>();
        for (int i = 0; i < str.length(); i++){
            noReapted.add(""+str.charAt(i));
        }
        str = "";
        for(String index:noReapted){
            str += index;
        }
        return str;
    }
  	/**
  	 * 后面一个日期减去前面日期的天数
  	 * @Title: daysBetween 
  	 * @Description:  
  	 * @param @param startTime
  	 * @param @param endTime
  	 * @param @return
  	 * @return int     
  	 * @throws
  	 */
  	 public static int daysBetween(String startTime,String  endTime){    
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
         Date smdate = null ;
         Date bdate = null ;
		try {
			smdate=sdf.parse(startTime);  
	        bdate=sdf.parse(endTime); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
         Calendar cal = Calendar.getInstance();    
         cal.setTime(smdate);    
         long time1 = cal.getTimeInMillis();                 
         cal.setTime(bdate);    
         long time2 = cal.getTimeInMillis();         
         long between_days=(time2-time1)/(1000*3600*24);  
         return Integer.parseInt(String.valueOf(between_days));           
     }  
  	/**
  	 * 是否包含异常数据
  	 * @Title: getIsIncludeExceptionData 
  	 * @Description:  
  	 * @param @param allList
  	 * @param @param isIncludeExceptionData
  	 * @param @return    
  	 * @return Map<String,Object>     
  	 * @throws
  	 */
  	public static Map<String,Object> getIsIncludeExceptionData(List<Z_Airdata> allList, String isIncludeExceptionData){
  		//包括异常数据的所有数据
		List<Z_Airdata> zairdataListA = new ArrayList<Z_Airdata>();
		zairdataListA.addAll(allList);
		//返回集合
		List<Z_Airdata> zairdataListB = new ArrayList<Z_Airdata>();
		//异常数据
		List<Z_Airdata> zairdataListC = new ArrayList<Z_Airdata>();
		//最终存放数据的list
		List<Z_Airdata> list = new ArrayList<Z_Airdata>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		list.addAll(zairdataListA);
		List<String> tList = new ArrayList<String>();
		//找出异常数据
		for (Z_Airdata z_Airdata : zairdataListA) {
			if(z_Airdata.getCount_Set()<=0||TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())){
				String fltNum = z_Airdata.getFlt_Nbr();
				String date = sdf.format(z_Airdata.getLcl_Dpt_Day());
				tList.add(fltNum+"-"+date);
			}
		}
		for (Z_Airdata z_Airdata2 : zairdataListA) {
			String fltNum2 = z_Airdata2.getFlt_Nbr();
			String date2 = sdf.format(z_Airdata2.getLcl_Dpt_Day());
			if(tList.contains(fltNum2+"-"+date2)){
				zairdataListC.add(z_Airdata2);
			}
		}
		for (Z_Airdata z_Airdatac : zairdataListC) {
			//a就是正常数据
			zairdataListA.remove(z_Airdatac);
		}
		//判断是否包含异常数据
		if("on".equals(isIncludeExceptionData)){
			zairdataListB = list;
		}else{
			zairdataListB = zairdataListA;
		}
		String flage = "false";
		if(zairdataListC.size()>0){
			flage = "true";
		}
		Map<String,Object> retMap = new HashMap<String,Object>();
		retMap.put("flage", flage);
		retMap.put("zairdataListB", zairdataListB);
		return retMap;
 }
  	public static Map<String,Object> getIsIncludeExceptionDataDay(List<Z_Airdata> allList, String isIncludeExceptionData,String day){
  		//包括异常数据的所有数据
  		List<Z_Airdata> zairdataListA = new ArrayList<Z_Airdata>();
  		zairdataListA.addAll(allList);
  		//返回集合
  		List<Z_Airdata> zairdataListB = new ArrayList<Z_Airdata>();
  		//异常数据
  		List<Z_Airdata> zairdataListC = new ArrayList<Z_Airdata>();
  		//最终存放数据的list
  		List<Z_Airdata> list = new ArrayList<Z_Airdata>();
  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  		list.addAll(zairdataListA);
  		//找出异常数据
  		for (Z_Airdata z_Airdata : zairdataListA) {
  			if(z_Airdata.getCount_Set()<=0||TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())){
  				String fltNum = z_Airdata.getFlt_Nbr();
  				Date date = z_Airdata.getLcl_Dpt_Day();
  				for (Z_Airdata z_Airdata2 : zairdataListA) {
  					String fltNum2 = z_Airdata2.getFlt_Nbr();
  					Date date2 = z_Airdata2.getLcl_Dpt_Day();
  					if(fltNum.equals(fltNum2)&&sdf.format(date).equals(sdf.format(date2))&&day.equals(sdf.format(date2))){
  						if(!zairdataListC.contains(z_Airdata2)){
  							zairdataListC.add(z_Airdata2);
  						}
  					}
  				}
  			}
  		}
  		for (Z_Airdata z_Airdatac : zairdataListC) {
  			//a就是正常数据
  			zairdataListA.remove(z_Airdatac);
  		}
  		//判断是否包含异常数据
  		if("on".equals(isIncludeExceptionData)){
  			zairdataListB = list;
  		}else{
  			zairdataListB = zairdataListA;
  		}
  		String flage = "false";
  		if(zairdataListC.size()>0){
  			flage = "true";
  		}
  		Map<String,Object> retMap = new HashMap<String,Object>();
  		retMap.put("flage", flage);
  		retMap.put("zairdataListB", zairdataListB);
  		return retMap;
  	}
  	/**
  	 * 是否包含异常航段数据
  	 * @Title: getIsIncludeExceptionHangduan 
  	 * @Description:  
  	 * @param @param allList
  	 * @param @param isIncludeExceptionData
  	 * @param @return    
  	 * @return Map<String,Object>     
  	 * @throws
  	 */
//  	public static Map<String,Object> getIsIncludeExceptionHangduan(List<Z_Airdata> allList, String isIncludeExceptionData,String isSingle){
//  		//isSingle位false时候为单程，为true的时候为来回，为single_false的时候为单短，single_true的时候为单短来回
//  		int duanshu1 = 0;
//  		int duanshu2 = 0;
//  		if("true".equals(isSingle)){
//  			duanshu1 = 6;
//  			duanshu2 = 2;
//  		}
//  		if("false".equals(isSingle)){
//  			duanshu1 = 3;
//  			duanshu2 = 1;
//  		}
//  		if("single_true".equals(isSingle)){
//  			duanshu1 = 2;
//  			duanshu2 = 2;
//  		}
//  		if("single_false".equals(isSingle)){
//  			duanshu1 = 1;
//  			duanshu2 = 1;
//  		}
//	 	//包括异常数据的所有数据
//		List<Z_Airdata> zairdataListA = new ArrayList<Z_Airdata>();
//		zairdataListA.addAll(allList);
//		//返回集合
//		List<Z_Airdata> zairdataListB = new ArrayList<Z_Airdata>();
//		//异常数据
//		List<Z_Airdata> zairdataListC = new ArrayList<Z_Airdata>();
//		//最终存放数据的list
//		List<Z_Airdata> list = new ArrayList<Z_Airdata>();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		list.addAll(zairdataListA);
//		//找出异常数据
////		System.out.println(zairdataListA.size()+"-0-"+new Date().getTime());
//		
//		for (Z_Airdata z_Airdata : zairdataListA) {
//			int index = 0;
//			String fltNum = z_Airdata.getFlt_Nbr();
//			if(fltNum.length()<6){
//				continue;
//			}
//			String lastStr = fltNum.substring(fltNum.length()-1, fltNum.length());
//			if(!TextUtil.isNum(lastStr)){
//				lastStr = TextUtil.HbhCharater(lastStr)+"";
//				fltNum = fltNum.substring(0, fltNum.length()-1)+lastStr;
//			}
//			String fltNumtwo = fltNum.substring(0, fltNum.length()-3);
//			List<Z_Airdata> zairdataListTemp = new ArrayList<Z_Airdata>();
//			if(HbhCharater(lastStr)%2==1){
//				fltNumtwo = fltNumtwo + (Integer.parseInt(fltNum.substring(fltNum.length()-3, fltNum.length()))+1);
//			}else{
//				fltNumtwo = fltNumtwo + (Integer.parseInt(fltNum.substring(fltNum.length()-3, fltNum.length()))-1);
//			}
//			Date date = z_Airdata.getLcl_Dpt_Day();
//			for (Z_Airdata z_Airdata2 : zairdataListA) {
//				String fltNum2 = z_Airdata2.getFlt_Nbr();
//				Date date2 = z_Airdata2.getLcl_Dpt_Day();
//				if((fltNum.equals(fltNum2)||fltNumtwo.equals(fltNum2))&&sdf.format(date).equals(sdf.format(date2))){
//					index++;
//					zairdataListTemp.add(z_Airdata2);
//				}
//			}
//			if(z_Airdata.getFlt_Rte_Cd().trim().length()>6){
//				//经停，应该有6段
//				if(index<duanshu1){
//					for (Z_Airdata z_Airdata3 : zairdataListTemp) {
//						if(!zairdataListC.contains(z_Airdata3)){
//							zairdataListC.add(z_Airdata3);
//						}
//					}
//				}
//			}
//			if(z_Airdata.getFlt_Rte_Cd().trim().length()<9){
//				//直飞，应该有2段
//				if(index<duanshu2){
//					for (Z_Airdata z_Airdata3 : zairdataListTemp) {
//						if(!zairdataListC.contains(z_Airdata3)){
//							zairdataListC.add(z_Airdata3);
//						}
//					}
//				}
//			}
//			if(fltNum.length()<6||!TextUtil.isNum(fltNum.substring(fltNum.length()-1, fltNum.length()))){
//				if(!zairdataListC.contains(z_Airdata)){
//					zairdataListC.add(z_Airdata);
//				}
//			}
//		}
////		System.out.println(zairdataListA.size()+"-1-"+new Date().getTime());
//		for (Z_Airdata z_Airdatac : zairdataListC) {
//			//a就是正常数据
//			zairdataListA.remove(z_Airdatac);
//		}
//		//判断是否包含异常航段数据
//		if("on".equals(isIncludeExceptionData)){
//			zairdataListB = list;
//		}else{
//			zairdataListB = zairdataListA;
//		}
//		String flage = "false";
//		if(zairdataListC.size()>0){
//			flage = "true";
//		}
//		Map<String,Object> retMap = new HashMap<String,Object>();
//		retMap.put("flage", flage);
//		retMap.put("zairdataListB", zairdataListB);
//		return retMap;
// }
  	public static Map<String,Object> getIsIncludeExceptionHangduan(List<Z_Airdata> allList, String isIncludeExceptionData,String isSingle){
		//isSingle位false时候为单程，为true的时候为来回，为single_false的时候为单短，single_true的时候为单短来回
  		Map<String,Object> retMap = new HashMap<String,Object>();
	 	//包括异常数据的所有数据
		List<Z_Airdata> zairdataListA = new ArrayList<Z_Airdata>();
		zairdataListA.addAll(allList);
		//正常数据集合
		List<Z_Airdata> zairdataListB = new ArrayList<Z_Airdata>();
		//异常数据
		List<Z_Airdata> zairdataListC = new ArrayList<Z_Airdata>();
		//返回集合
		List<Z_Airdata> retList = new ArrayList<Z_Airdata>();
		if("true".equals(isSingle)){
			//往返
			for (Z_Airdata z_Airdata : zairdataListA) {
				String comeAndGo = z_Airdata.getComeAndGo();
				if("1".equals(comeAndGo)){
					zairdataListB.add(z_Airdata);
				}else{
					zairdataListC.add(z_Airdata);
				}
			}
		}else if("false".equals(isSingle)){
			//单程
			for (Z_Airdata z_Airdata : zairdataListA) {
				String allData = z_Airdata.getAllData();
				if("1".equals(allData)){
					zairdataListB.add(z_Airdata);
				}else{
					zairdataListC.add(z_Airdata);
				}
			}
		}else if("single_true".equals(isSingle)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (Z_Airdata z_Airdata : zairdataListA) {
				String fltNum = z_Airdata.getFlt_Nbr();
				String lastStr = fltNum.substring(fltNum.length()-1, fltNum.length());
				if(!TextUtil.isNum(lastStr)){
					lastStr = TextUtil.HbhCharater(lastStr)+"";
					fltNum = fltNum.substring(0, fltNum.length()-1)+lastStr;
				}
				String fltNumtwo = fltNum.substring(0, fltNum.length()-3);
				if(HbhCharater(lastStr)%2==1){
					fltNumtwo = fltNumtwo + (Integer.parseInt(fltNum.substring(fltNum.length()-3, fltNum.length()))+1);
				}else{
					fltNumtwo = fltNumtwo + (Integer.parseInt(fltNum.substring(fltNum.length()-3, fltNum.length()))-1);
				}
				String date = sdf.format(z_Airdata.getLcl_Dpt_Day());
				for (Z_Airdata z_Airdata2 : zairdataListA) {
					String fltNumt = z_Airdata2.getFlt_Nbr();
					String date2 = sdf.format(z_Airdata2.getLcl_Dpt_Day());
					if((fltNumtwo.equals(fltNumt))&&date.equals(date2)){
						zairdataListB.add(z_Airdata);
					}
				}
			}
			for (Z_Airdata z_Airdata : zairdataListA) {
				if(!zairdataListB.contains(z_Airdata)){
					zairdataListC.add(z_Airdata);
				}
			}
//			for (Z_Airdata z_Airdata : zairdataListA) {
//				zairdataListB.add(z_Airdata);
//			}
		}else if("single_false".equals(isSingle)){
			for (Z_Airdata z_Airdata : zairdataListA) {
				zairdataListB.add(z_Airdata);
			}
		}
		//判断是否包含异常航段数据
		if("on".equals(isIncludeExceptionData)){
			retList = zairdataListA;
		}else{
			retList = zairdataListB;
		}
		String flage = "false";
		if(zairdataListC.size()>0&&"on".equals(isIncludeExceptionData)){
			flage = "true";
		}
		retMap.put("flage", flage);
		retMap.put("zairdataListB", retList);
		return retMap;
  	}
  	public static Map<String,Object> getIsIncludeExceptionHangduanDay(List<Z_Airdata> allList, String isIncludeExceptionData,String isSingle,String day){
  		//isSingle位false时候为单程，为true的时候为来回，为single_false的时候为单短，single_true的时候为单短来回
  		int duanshu1 = 0;
  		int duanshu2 = 0;
  		if("true".equals(isSingle)){
  			duanshu1 = 6;
  			duanshu2 = 2;
  		}
  		if("false".equals(isSingle)){
  			duanshu1 = 3;
  			duanshu2 = 1;
  		}
  		if("single_true".equals(isSingle)){
  			duanshu1 = 2;
  			duanshu2 = 2;
  		}
  		if("single_false".equals(isSingle)){
  			duanshu1 = 1;
  			duanshu2 = 1;
  		}
  		//包括异常数据的所有数据
  		List<Z_Airdata> zairdataListA = new ArrayList<Z_Airdata>();
  		zairdataListA.addAll(allList);
  		//返回集合
  		List<Z_Airdata> zairdataListB = new ArrayList<Z_Airdata>();
  		//异常数据
  		List<Z_Airdata> zairdataListC = new ArrayList<Z_Airdata>();
  		//最终存放数据的list
  		List<Z_Airdata> list = new ArrayList<Z_Airdata>();
  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  		list.addAll(zairdataListA);
  		//找出异常数据
  		for (Z_Airdata z_Airdata : zairdataListA) {
  			int index = 0;
  			String fltNum = z_Airdata.getFlt_Nbr();
  			if(fltNum.length()<6){
  				continue;
  			}
  			String lastStr = fltNum.substring(fltNum.length()-1, fltNum.length());
  			if(!TextUtil.isNum(lastStr)){
  				lastStr = TextUtil.HbhCharater(lastStr)+"";
  				fltNum = fltNum.substring(0, fltNum.length()-1)+lastStr;
  			}
  			String fltNumtwo = fltNum.substring(0, fltNum.length()-3);
  			List<Z_Airdata> zairdataListTemp = new ArrayList<Z_Airdata>();
  			if(HbhCharater(lastStr)%2==1){
  				fltNumtwo = fltNumtwo + (Integer.parseInt(fltNum.substring(fltNum.length()-3, fltNum.length()))+1);
  			}else{
  				fltNumtwo = fltNumtwo + (Integer.parseInt(fltNum.substring(fltNum.length()-3, fltNum.length()))-1);
  			}
  			Date date = z_Airdata.getLcl_Dpt_Day();
  			for (Z_Airdata z_Airdata2 : zairdataListA) {
  				String fltNum2 = z_Airdata2.getFlt_Nbr();
  				Date date2 = z_Airdata2.getLcl_Dpt_Day();
  				if((fltNum.equals(fltNum2)||fltNumtwo.equals(fltNum2))&&sdf.format(date).equals(sdf.format(date2))&&day.equals(sdf.format(date2))){
  					index++;
  					zairdataListTemp.add(z_Airdata2);
  				}
  			}
  			if(z_Airdata.getFlt_Rte_Cd().trim().length()>6){
  				//经停，应该有6段
  				if(index<duanshu1){
  					for (Z_Airdata z_Airdata3 : zairdataListTemp) {
  						if(!zairdataListC.contains(z_Airdata3)){
  							zairdataListC.add(z_Airdata3);
  						}
  					}
  				}
  			}
  			if(z_Airdata.getFlt_Rte_Cd().trim().length()<9){
  				//直飞，应该有2段
  				if(index<duanshu2){
  					for (Z_Airdata z_Airdata3 : zairdataListTemp) {
  						if(!zairdataListC.contains(z_Airdata3)){
  							zairdataListC.add(z_Airdata3);
  						}
  					}
  				}
  			}
  			if(fltNum.length()<6||!TextUtil.isNum(fltNum.substring(fltNum.length()-1, fltNum.length()))&&day.equals(sdf.format(date))){
  				if(!zairdataListC.contains(z_Airdata)){
  					zairdataListC.add(z_Airdata);
  				}
  			}
  		}
  		for (Z_Airdata z_Airdatac : zairdataListC) {
  			//a就是正常数据
  			zairdataListA.remove(z_Airdatac);
  		}
  		//判断是否包含异常航段数据
  		if("on".equals(isIncludeExceptionData)){
  			zairdataListB = list;
  		}else{
  			zairdataListB = zairdataListA;
  		}
  		String flage = "false";
  		if(zairdataListC.size()>0){
  			flage = "true";
  		}
  		Map<String,Object> retMap = new HashMap<String,Object>();
  		retMap.put("flage", flage);
  		retMap.put("zairdataListB", zairdataListB);
  		return retMap;
  	}
  	/**
  	 * 按某个字段排序
  	 * @Title: sort 
  	 * @Description:  
  	 * @param @param targetList
  	 * @param @param sortField
  	 * @param @param sortMode    
  	 * @return void     
  	 * @throws
  	 */
  	@SuppressWarnings({ "unchecked", "rawtypes" })  
    public static List<TotalFly> sort(List<TotalFly> targetList, final String sortField, final String sortMode) {  
        Collections.sort(targetList, new Comparator() {  
            public int compare(Object obj1, Object obj2) {   
            	double retVal = 0.0;  
                try {  
                    //首字母转大写  
                    String newStr=sortField.substring(0, 1).toUpperCase()+sortField.replaceFirst("\\w","");   
                    String methodStr="get"+newStr;  
                    Method method1 = ((TotalFly)obj1).getClass().getMethod(methodStr, null);  
                    Method method2 = ((TotalFly)obj2).getClass().getMethod(methodStr, null);  
                    if (sortMode != null && "DESC".equals(sortMode)) {  
                        retVal = Double.parseDouble(method2.invoke(((TotalFly) obj2), null).toString())-(Double.parseDouble(method1.invoke(((TotalFly) obj1), null).toString())); // 倒序  
                    } else {  
                        retVal = Double.parseDouble(method2.invoke(((TotalFly) obj1), null).toString())-(Double.parseDouble(method1.invoke(((TotalFly) obj2), null).toString())); // 正序  
                    }  
                } catch (Exception e) {  
                    throw new RuntimeException();  
                }  
                if(retVal>0){
                	retVal = 1;
                }else{
                	retVal = -1;
                }
                return (int)retVal;  
            }  
        });  
       return  targetList;
    }  
  	@SuppressWarnings({ "unchecked", "rawtypes" })  
  	public static List<FlightAirline> sortFlightAirline(List<FlightAirline> targetList, final String sortField, final String sortMode) {  
  		Collections.sort(targetList, new Comparator() {  
  			public int compare(Object obj1, Object obj2) {   
  				double retVal = 0.0;  
  				try {  
  					//首字母转大写  
  					String newStr=sortField.substring(0, 1).toUpperCase()+sortField.replaceFirst("\\w","");   
  					String methodStr="get"+newStr;  
  					Method method1 = ((FlightAirline)obj1).getClass().getMethod(methodStr, null);  
  					Method method2 = ((FlightAirline)obj2).getClass().getMethod(methodStr, null);  
  					if (sortMode != null && "DESC".equals(sortMode)) {  
  						retVal = Double.parseDouble(method2.invoke(((FlightAirline) obj2), null).toString())-(Double.parseDouble(method1.invoke(((FlightAirline) obj1), null).toString())); // 倒序  
  					} else {  
  						retVal = Double.parseDouble(method2.invoke(((FlightAirline) obj1), null).toString())-(Double.parseDouble(method1.invoke(((FlightAirline) obj2), null).toString())); // 正序  
  					}  
  				} catch (Exception e) {  
  					throw new RuntimeException();  
  				}  
  				if(retVal>0){
                	retVal = 1;
                }else{
                	retVal = -1;
                }
  				return (int)retVal;  
  			}  
  		});  
  		return  targetList;
  	}  
  	@SuppressWarnings({ "unchecked", "rawtypes" })  
  	public static List<ClassRanking> sortClassRanking(List<ClassRanking> targetList, final String sortField, final String sortMode) {  
  		Collections.sort(targetList, new Comparator() {  
  			public int compare(Object obj1, Object obj2) {   
  				double retVal = 0.0;  
  				try {  
  					//首字母转大写  
  					String newStr=sortField.substring(0, 1).toUpperCase()+sortField.replaceFirst("\\w","");   
  					String methodStr="get"+newStr;  
  					Method method1 = ((ClassRanking)obj1).getClass().getMethod(methodStr, null);  
  					Method method2 = ((ClassRanking)obj2).getClass().getMethod(methodStr, null);  
  					if (sortMode != null && "DESC".equals(sortMode)) {  
  						retVal = Double.parseDouble(method2.invoke(((ClassRanking) obj2), null).toString())-(Double.parseDouble(method1.invoke(((ClassRanking) obj1), null).toString())); // 倒序  
  					} else {  
  						retVal = Double.parseDouble(method2.invoke(((ClassRanking) obj1), null).toString())-(Double.parseDouble(method1.invoke(((ClassRanking) obj2), null).toString())); // 正序  
  					}  
  				} catch (Exception e) {  
  					throw new RuntimeException();  
  				}  
  				if(retVal>0){
                	retVal = 1;
                }else{
                	retVal = -1;
                }
  				return (int)retVal;  
  			}  
  		});  
  		if(targetList.size()>10){
  			return  targetList.subList(0, 10);
  		}else{
  			return targetList;
  		}
  	}  
  	@SuppressWarnings({ "unchecked", "rawtypes" })  
  	public static List<AllAirLineFltDetailData> sortAllAirLineFltDetailData(List<AllAirLineFltDetailData> targetList, final String sortField, final String sortMode) {  
  		Collections.sort(targetList, new Comparator() {  
  			public int compare(Object obj1, Object obj2) {   
  				double retVal = 0.0;  
  				try {  
  					//首字母转大写  
  					String newStr=sortField.substring(0, 1).toUpperCase()+sortField.replaceFirst("\\w","");   
  					String methodStr="get"+newStr;  
  					Method method1 = ((AllAirLineFltDetailData)obj1).getClass().getMethod(methodStr, null);  
  					Method method2 = ((AllAirLineFltDetailData)obj2).getClass().getMethod(methodStr, null);  
  					String s1 = method2.invoke(((AllAirLineFltDetailData) obj1), null).toString();
					String s2 = method1.invoke(((AllAirLineFltDetailData) obj2), null).toString();
  					if (sortMode != null && "DESC".equals(sortMode)) {  
  						retVal = s2.compareTo(s1); // 倒序  
  					} else {  
  						retVal = s1.compareTo(s2); // 正序  
  					}  
  				} catch (Exception e) {  
  					throw new RuntimeException();  
  				}  
  				if(retVal>0){
                	retVal = 1;
                }else{
                	retVal = -1;
                }
  				return (int)retVal;  
  			}  
  		});  
  		return targetList;
  	}  
  	 public static List<Map<String,Object>> listSort(List<Map<String,Object>> resultList) throws Exception{  
         Collections.sort(resultList,new Comparator<Map<String,Object>>() {  
	          public int compare(Map<String, Object> o1,Map<String, Object> o2) {  
	        	  try {  
		               Integer s1 = Integer.parseInt((String)o1.get("key")) ;  
		               Integer s2 = Integer.parseInt((String)o2.get("key"));  
			           if(s1>s2) {  
			            return 1;  
			           }else if(s1==s2){
			        	  return 0;  
			           }else{  
			            return -1;  
			           }  
	        	  }catch (Exception e) {
	        		  throw new RuntimeException();  
				}
	          }  
         });  
         return resultList;
    }  
  	
  	/**
  	 * 航班号配对
  	 * @Title: getHbhLH 
  	 * @Description:  
  	 * @param @param list
  	 * @param @return    
  	 * @return List<String>     
  	 * @throws
  	 */
  	public static List<String> getHbhLH(List<String> list) {  
  		List<String> retListTemp = new ArrayList<String>();
  		List<String> retList = new ArrayList<String>();
		String tempFlyNum = "";
		for (String str : list) {
			if(str.length()==6&&TextUtil.isNum(str.substring(5, 6))){
				String firststr = str.substring(0, 2);
				int lasttwostr = Integer.parseInt(str.substring(2, 5));
				int laststr = Integer.parseInt(str.substring(5, 6));
				String pdStr = "";
				for (String str2 : list) {
					if(laststr%2==0){
						if(laststr==0){
							pdStr =firststr + (lasttwostr-1) + "9";
						}else{
							pdStr = firststr +lasttwostr+  (laststr-1);
						}
					}else{
						if(laststr==9){
							pdStr =firststr + (lasttwostr+1) + "0";
						}else{
							pdStr = firststr +lasttwostr+  (laststr+1);
						}
					}
					if(pdStr.equals(str2)){
						tempFlyNum = str + "/" + str2;
						if(!retListTemp.contains(tempFlyNum)){
							retListTemp.add(tempFlyNum);
						}
					}
				}
			}
		}
		for (String str : retListTemp) {
			if(str.length()>6&&TextUtil.isNum(str.substring(5, 6))){
				if((Integer.parseInt(str.substring(5, 6))%2)==1){
					retList.add(str);
				}
			}
		}
	  	return retList;
  	}
  	/**
  	 * 航班号配对（包括不能配起对的）
  	 * @Title: getHbhLH 
  	 * @Description:  
  	 * @param @param list
  	 * @param @return    
  	 * @return List<String>     
  	 * @throws
  	 */
  	public static List<String> getHbhLHAll(List<String> list) {  
  		List<String> retListTemp = new ArrayList<String>();
  		List<String> retList = new ArrayList<String>();
		String tempFlyNum = "";
		for (String str : list) {
			str = str.substring(0, str.length()-1)+HbhCharater(str.substring(str.length()-1, str.length()));
			retList.add(str);
		}
		for (String str : retList) {
			String firststr = str.substring(0, 2);
			int lasttwostr = Integer.parseInt(str.substring(2, str.length()));
			String pdStr = "";
			int index = 0;
			for (String str2 : retList) {
				if(lasttwostr%2==0){
					pdStr = firststr + (lasttwostr-1);
				}else{
					pdStr = firststr +  (lasttwostr+1);
				}
				if(pdStr.equals(str2)){
					index ++;
					if(lasttwostr%2!=0){
						tempFlyNum = str + "/" + str2;
					}else{
						tempFlyNum = str2 + "/" + str;
					}
					if(!retListTemp.contains(tempFlyNum)){
						retListTemp.add(tempFlyNum);
					}
				}
			}
			if(index==0){
				String strr = "";
				if(lasttwostr%2==0){
					pdStr = firststr + (lasttwostr-1);
					strr = pdStr +"/"+str;
				}else{
					pdStr = firststr +  (lasttwostr+1);
					strr = str +"/"+pdStr;
				}
				retListTemp.add(strr);	
			}
		}
	  	return retListTemp;
  	}
  	/**
  	 * 航班号字母转义
  	 * @Title: HbhCharater 
  	 * @Description:  
  	 * @param @param numnew
  	 * @param @return    
  	 * @return int     
  	 * @throws
  	 */
  	public static int HbhCharater(String numnew){
		int lastH = 0;
		if("Z".equals(numnew)){
			lastH = 0;
		}else if("Y".equals(numnew)){
			lastH = 1;
		}else if("X".equals(numnew)){
			lastH = 2;
		}else if("W".equals(numnew)){
			lastH = 3;
		}else if("V".equals(numnew)){
			lastH = 4;
		}else if("U".equals(numnew)){
			lastH = 5;
		}else if("T".equals(numnew)){
			lastH = 6;
		}else if("S".equals(numnew)){
			lastH = 7;
		}else if("R".equals(numnew)){
			lastH = 8;
		}else if("Q".equals(numnew)){
			lastH = 9;
		}else if(TextUtil.isNum(numnew)){
			lastH = Integer.parseInt(numnew);
		}
		return lastH;
	}
  	/**
  	 * 根据航司公布的折扣,将每个舱位中的人数取出,放进集合中
  	 * @param z_Airdata
  	 * @param airdiscounts
  	 * @return
  	 */
	public static List<Airdiscount> getAirdiscounts(Z_Airdata z_Airdata,List<Airdiscount> airdiscounts) {
		List<Airdiscount> airdiscounts2 = new ArrayList<Airdiscount>();
		for (Airdiscount airdiscount : airdiscounts) {
			Airdiscount airdiscount2 = new Airdiscount();
			if(!TextUtil.isEmpty(airdiscount.getDct_Ppt())){
				Double valueOf = Double.valueOf(airdiscount.getDct_Ppt());
				if(valueOf==100){
					int ful_Pce_Ppt = z_Airdata.getFul_Pce_Ppt(); // 全价
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(ful_Pce_Ppt+"");
				}else if(valueOf==90){
					int nne_Dnt_Ppt = z_Airdata.getNne_Dnt_Ppt(); // 9折
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(nne_Dnt_Ppt+"");
				}else if(valueOf==85){
					int eht_Five_Dnt_Ppt = z_Airdata.getEht_Five_Dnt_Ppt(); // 8.5折比例
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(eht_Five_Dnt_Ppt+"");
				}else if(valueOf==80){
					int eht_Dnt_Ppt = z_Airdata.getEht_Dnt_Ppt(); // 8折比例
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(eht_Dnt_Ppt+"");
				}else if(valueOf==75){
					int sen_Five_Dnt_Ppt = z_Airdata.getSen_Five_Dnt_Ppt(); // 7.5折比例
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(sen_Five_Dnt_Ppt+"");
				}else if(valueOf==70){
					int sen_Dnt_Ppt = z_Airdata.getSen_Dnt_Ppt(); // 7折比例
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(sen_Dnt_Ppt+"");
				}else if(valueOf==65){
					int six_Five_Dnt_Ppt = z_Airdata.getSix_Five_Dnt_Ppt(); // 6.5折比例
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(six_Five_Dnt_Ppt+"");
				}else if(valueOf==60){
					int six_Dnt_Ppt = z_Airdata.getSix_Dnt_Ppt(); // 6折比例
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(six_Dnt_Ppt+"");
				}else if(valueOf==55){
					int fve_Fve_Dnt_Ppt = z_Airdata.getFve_Fve_Dnt_Ppt(); // 5.5折比例
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(fve_Fve_Dnt_Ppt+"");
				}else if(valueOf==50){
					int fve_Dnt_Ppt = z_Airdata.getFve_Dnt_Ppt(); // 5折比例
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(fve_Dnt_Ppt+"");
				}else if(valueOf==45){
					int fur_Fve_Dnt_Ppt = z_Airdata.getFur_Fve_Dnt_Ppt(); // 4.5折比例
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(fur_Fve_Dnt_Ppt+"");
				}else if(valueOf==40){
					int fur_Dnt_Ppt = z_Airdata.getFur_Dnt_Ppt(); // 4折比例
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(fur_Dnt_Ppt+"");
				}else if(valueOf==35){
					int thr_Fve_Dnt_Ppt = z_Airdata.getThr_Fve_Dnt_Ppt(); // 35%
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(thr_Fve_Dnt_Ppt+"");
				}else if(valueOf==30){
					int thr_Dnt_Ppt = z_Airdata.getThr_Dnt_Ppt(); // 30%
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(thr_Dnt_Ppt+"");
				}else if(valueOf==25){
					int two_Fve_Dnt_Ppt = z_Airdata.getTwo_Fve_Dnt_Ppt(); // 25%
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(two_Fve_Dnt_Ppt+"");
				}else if(valueOf==20){
					int two_Dnt_Ppt = z_Airdata.getTwo_Dnt_Ppt(); // 20%         
					airdiscount2.setDct_Ppt(valueOf.intValue()+"");
					airdiscount2.setDct_Chr(airdiscount.getDct_Chr());
					airdiscount2.setDct_Pge(two_Dnt_Ppt+"");
				}
			}
			if(!TextUtil.isEmpty(airdiscount2.getDct_Chr())){
				airdiscounts2.add(airdiscount2);
			}
		}
		return airdiscounts2;
	}
	/**
	 * 得到执行班次
	 * @Title: getExecClass 
	 * @Description:  
	 * @param @param list 只能传入一个航班对或者一个航班的list
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public static double getExecClass(List<Z_Airdata> ListAll) {
		if(ListAll==null||ListAll.size()<=0){
			return 0.0;
		}
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> days = new ArrayList<String>();
		for (Z_Airdata z_Airdata : ListAll) {
			String day = sdf.format(z_Airdata.getLcl_Dpt_Day());
			if(!days.contains(day)){
				days.add(day);
			}
		}
		return (double)days.size();
	}
	public static String getOtherFlyNbr(String fltNum){
		String lastStr = fltNum.substring(fltNum.length()-1, fltNum.length());
		if(!TextUtil.isNum(lastStr)){
			lastStr = TextUtil.HbhCharater(lastStr)+"";
			fltNum = fltNum.substring(0, fltNum.length()-1)+lastStr;
		}
		String fltNumtwo = fltNum.substring(0, fltNum.length()-3);
		if(HbhCharater(lastStr)%2==1){
			fltNumtwo = fltNumtwo + (Integer.parseInt(fltNum.substring(fltNum.length()-3, fltNum.length()))+1);
		}else{
			fltNumtwo = fltNumtwo + (Integer.parseInt(fltNum.substring(fltNum.length()-3, fltNum.length()))-1);
		}
		return fltNumtwo;
	}
	/**
	 * 根据每段的详细信息算出单程或者往返的相关指标
	 * @Title: getEveryDuanInfo 
	 * @Description:  
	 * @param @param airLineAllInfo
	 * @param @return    
	 * @return EveryDuanInfo     
	 * @throws
	 */
	public static EveryDuanInfo getEveryDuanInfo(AirLineAllInfo airLineAllInfo){
		EveryDuanInfo  everyDuanInfo = new EveryDuanInfo();
		double go_zgl = 0;
		double back_zgl = 0;
		double goAndBack_zgl = 0;
		double go_zk = 0;
		double back_zk = 0;
		double goAndBack_zk = 0;
		double go_tdzk = 0;
		double back_tdzk = 0;
		double goAndBack_tdzk = 0;
		double go_xssr = 0;
		double back_xssr = 0;
		double goAndBack_xssr = 0;
		double go_kzl = 0;
		double back_kzl = 0;
		double goAndBack_kzl = 0;
		double go_sr = 0;
		double back_sr = 0;
		double goAndBack_sr = 0;
		double go_tdsr = 0;
		double back_tdsr = 0;
		double goAndBack_tdsr = 0;
		double go_rs = 0;
		double back_rs = 0;
		double goAndBack_rs = 0;
		double go_tdrs = 0;
		double back_tdrs = 0;
		double goAndBack_tdrs = 0;
	    double go_fxsj = 0;
		double back_fxsj = 0;
		double goAndBack_fxsj = 0;
		//去的座公里（完整航班=总收入/cap/短航距之和）（不完整每段求平均）
		if(airLineAllInfo.getGo_index()>2&&airLineAllInfo.getGo_sr_cd()>0&&airLineAllInfo.getGo_sr_dd1()>0&&airLineAllInfo.getGo_sr_dd2()>0&&airLineAllInfo.getGo_cap()>0&&airLineAllInfo.getGo_hj_dd1()>0&&airLineAllInfo.getGo_hj_dd2()>0){
			go_zgl = (airLineAllInfo.getGo_sr_cd()+airLineAllInfo.getGo_sr_dd1()+airLineAllInfo.getGo_sr_dd2())/airLineAllInfo.getGo_cap()/(airLineAllInfo.getGo_hj_dd1()+airLineAllInfo.getGo_hj_dd2());
		}else{
			int bzindex = 0;
			if(airLineAllInfo.getGo_zgl_cd()>0){
				bzindex++;
			}
			if(airLineAllInfo.getGo_zgl_dd1()>0){
				bzindex++;
			}
			if(airLineAllInfo.getGo_zgl_dd2()>0){
				bzindex++;
			}
			if(bzindex>0){
				go_zgl = (airLineAllInfo.getGo_zgl_cd()+airLineAllInfo.getGo_zgl_dd1()+airLineAllInfo.getGo_zgl_dd2())/bzindex;
			}else{
				go_zgl = 0;
			}
		}
		everyDuanInfo.setGo_zgl(go_zgl);
		//回的座公里（完整航班=总收入/cap/短航距之和）（不完整每段求平均）
		if(airLineAllInfo.getBack_index()>2&&airLineAllInfo.getBack_sr_cd()>0&&airLineAllInfo.getBack_sr_dd1()>0&&airLineAllInfo.getBack_sr_dd2()>0&&airLineAllInfo.getBack_cap()>0&&airLineAllInfo.getBack_hj_dd1()>0&&airLineAllInfo.getBack_hj_dd2()>0){
			back_zgl = (airLineAllInfo.getBack_sr_cd()+airLineAllInfo.getBack_sr_dd1()+airLineAllInfo.getBack_sr_dd2())/airLineAllInfo.getBack_cap()/(airLineAllInfo.getBack_hj_dd1()+airLineAllInfo.getBack_hj_dd2());
		}else{
			int bzindex = 0;
			if(airLineAllInfo.getBack_zgl_cd()>0){
				bzindex++;
			}
			if(airLineAllInfo.getBack_zgl_dd1()>0){
				bzindex++;
			}
			if(airLineAllInfo.getBack_zgl_dd2()>0){
				bzindex++;
			}
			if(bzindex>0){
				back_zgl = (airLineAllInfo.getBack_zgl_cd()+airLineAllInfo.getBack_zgl_dd1()+airLineAllInfo.getBack_zgl_dd2())/bzindex;
			}else{
				back_zgl = 0;
			}
		}
		everyDuanInfo.setBack_zgl(back_zgl);
		//往返座公里（去和回都有座公里 = （去的座公里+回的座公里）/2） 否者等于有座公里的航班
		if(everyDuanInfo.getGo_zgl()>0&&everyDuanInfo.getBack_zgl()>0){
			goAndBack_zgl = (everyDuanInfo.getGo_zgl() + everyDuanInfo.getBack_zgl())/2;
		}else{
			if(everyDuanInfo.getGo_zgl()>0){
				goAndBack_zgl = everyDuanInfo.getGo_zgl();
			}else if(everyDuanInfo.getBack_zgl()>0){
				goAndBack_zgl = everyDuanInfo.getBack_zgl();
			}else{
				goAndBack_zgl = 0;
			}
		}
		everyDuanInfo.setGoAndBack_zgl(goAndBack_zgl);
		//去的折扣（有折扣的段数求平均）
		if(airLineAllInfo.getGo_index()>2&&airLineAllInfo.getGo_zk_cd()>0&&airLineAllInfo.getGo_zk_dd1()>0&&airLineAllInfo.getGo_zk_dd2()>0){
			go_zk = (airLineAllInfo.getGo_zk_cd() + airLineAllInfo.getGo_zk_dd1() + airLineAllInfo.getGo_zk_dd2())/3;
		}else{
			int bzindex = 0;
			if(airLineAllInfo.getGo_zk_cd()>0){
				bzindex++;
			}
			if(airLineAllInfo.getGo_zk_dd1()>0){
				bzindex++;
			}
			if(airLineAllInfo.getGo_zk_dd2()>0){
				bzindex++;
			}
			if(bzindex>0){
				go_zk = (airLineAllInfo.getGo_zk_cd()+airLineAllInfo.getGo_zk_dd1()+airLineAllInfo.getGo_zk_dd2())/bzindex;
			}else{
				go_zk = 0;
			}
		}
		everyDuanInfo.setGo_zk(go_zk);
		//回来的折扣（有折扣的段数求平均）
		if(airLineAllInfo.getBack_index()>2&&airLineAllInfo.getBack_zk_cd()>0&&airLineAllInfo.getBack_zk_dd1()>0&&airLineAllInfo.getBack_zk_dd2()>0){
			back_zk = (airLineAllInfo.getBack_zk_cd() + airLineAllInfo.getBack_zk_dd1() + airLineAllInfo.getBack_zk_dd2())/3;
		}else{
			int bzindex = 0;
			if(airLineAllInfo.getBack_zk_cd()>0){
				bzindex++;
			}
			if(airLineAllInfo.getBack_zk_dd1()>0){
				bzindex++;
			}
			if(airLineAllInfo.getBack_zk_dd2()>0){
				bzindex++;
			}
			if(bzindex>0){
				back_zk = (airLineAllInfo.getBack_zk_cd()+airLineAllInfo.getBack_zk_dd1()+airLineAllInfo.getBack_zk_dd2())/bzindex;
			}else{
				back_zk = 0;
			}
		}
		everyDuanInfo.setBack_zk(back_zk);
		//往返的折扣（如果来回都有折扣则求平均，否则等于单程的折扣）
		if(everyDuanInfo.getGo_zk()>0&&everyDuanInfo.getBack_zk()>0){
			goAndBack_zk = (everyDuanInfo.getGo_zk() + everyDuanInfo.getBack_zk())/2;
		}else{
			if(everyDuanInfo.getGo_zk()>0){
				goAndBack_zk = everyDuanInfo.getGo_zk();
			}else if(everyDuanInfo.getBack_zk()>0){
				goAndBack_zk = everyDuanInfo.getBack_zk();
			}else{
				goAndBack_zk = 0;
			}
		}
		everyDuanInfo.setGoAndBack_zk(goAndBack_zk);
		//去的团队折扣（有折扣的段数求平均）
		if(airLineAllInfo.getGo_index()>2&&airLineAllInfo.getGo_tdzk_cd()>0&&airLineAllInfo.getGo_tdzk_dd1()>0&&airLineAllInfo.getGo_tdzk_dd2()>0){
			go_tdzk = (airLineAllInfo.getGo_tdzk_cd() + airLineAllInfo.getGo_tdzk_dd1() + airLineAllInfo.getGo_tdzk_dd2())/3;
		}else{
			int bzindex = 0;
			if(airLineAllInfo.getGo_tdzk_cd()>0){
				bzindex++;
			}
			if(airLineAllInfo.getGo_tdzk_dd1()>0){
				bzindex++;
			}
			if(airLineAllInfo.getGo_tdzk_dd2()>0){
				bzindex++;
			}
			if(bzindex>0){
				go_tdzk = (airLineAllInfo.getGo_tdzk_cd()+airLineAllInfo.getGo_tdzk_dd1()+airLineAllInfo.getGo_tdzk_dd2())/bzindex;
			}else{
				go_tdzk = 0;
			}
		}
		everyDuanInfo.setGo_tdzk(go_tdzk);
		//回来的团队折扣（有折扣的段数求平均）
		if(airLineAllInfo.getBack_index()>2&&airLineAllInfo.getBack_tdzk_cd()>0&&airLineAllInfo.getBack_tdzk_dd1()>0&&airLineAllInfo.getBack_tdzk_dd2()>0){
			back_tdzk = (airLineAllInfo.getBack_tdzk_cd() + airLineAllInfo.getBack_tdzk_dd1() + airLineAllInfo.getBack_tdzk_dd2())/3;
		}else{
			int bzindex = 0;
			if(airLineAllInfo.getBack_tdzk_cd()>0){
				bzindex++;
			}
			if(airLineAllInfo.getBack_tdzk_dd1()>0){
				bzindex++;
			}
			if(airLineAllInfo.getBack_tdzk_dd2()>0){
				bzindex++;
			}
			if(bzindex>0){
				back_tdzk = (airLineAllInfo.getBack_tdzk_cd()+airLineAllInfo.getBack_tdzk_dd1()+airLineAllInfo.getBack_tdzk_dd2())/bzindex;
			}else{
				back_tdzk = 0;
			}
		}
		everyDuanInfo.setBack_tdzk(back_tdzk);
		//往返的团队折扣（如果来回都有折扣则求平均，否则等于单程的折扣）
		if(everyDuanInfo.getGo_tdzk()>0&&everyDuanInfo.getBack_tdzk()>0){
			goAndBack_tdzk = (everyDuanInfo.getGo_tdzk() + everyDuanInfo.getBack_tdzk())/2;
		}else{
			if(everyDuanInfo.getGo_tdzk()>0){
				goAndBack_tdzk = everyDuanInfo.getGo_tdzk();
			}else if(everyDuanInfo.getBack_tdzk()>0){
				goAndBack_tdzk = everyDuanInfo.getBack_tdzk();
			}else{
				goAndBack_tdzk = 0;
			}
		}
		everyDuanInfo.setGoAndBack_tdzk(goAndBack_tdzk);
		//去的小时收入（不缺段并且其他分母指标都完整情况下=总收入/两短段时间和）
		if(airLineAllInfo.getGo_index()>2&&airLineAllInfo.getGo_sr_cd()>0&&airLineAllInfo.getGo_sr_dd1()>0&&airLineAllInfo.getGo_sr_dd2()>0&&airLineAllInfo.getGo_fxsj_dd1()>0&&airLineAllInfo.getGo_fxsj_dd2()>0){
			go_xssr = (airLineAllInfo.getGo_sr_cd()+airLineAllInfo.getGo_sr_dd1()+airLineAllInfo.getGo_sr_dd2())/(airLineAllInfo.getGo_fxsj_dd1()+airLineAllInfo.getGo_fxsj_dd2());
		}else{
			int bzindex = 0;
			if(airLineAllInfo.getGo_xssr_cd()>0){
				bzindex++;
			}
			if(airLineAllInfo.getGo_xssr_dd1()>0){
				bzindex++;
			}
			if(airLineAllInfo.getGo_xssr_dd2()>0){
				bzindex++;
			}
			if(bzindex>0){
				go_xssr = (airLineAllInfo.getGo_xssr_cd()+airLineAllInfo.getGo_xssr_dd1()+airLineAllInfo.getGo_xssr_dd2())/bzindex;
			}else{
				go_xssr = 0;
			}
		}
		everyDuanInfo.setGo_xssr(go_xssr);
		//回的小时收入（不缺段并且其他分母指标都完整情况下=总收入/两短段时间和）
		if(airLineAllInfo.getBack_index()>2&&airLineAllInfo.getBack_sr_cd()>0&&airLineAllInfo.getBack_sr_dd1()>0&&airLineAllInfo.getBack_sr_dd2()>0&&airLineAllInfo.getBack_fxsj_dd1()>0&&airLineAllInfo.getBack_fxsj_dd2()>0){
			back_xssr = (airLineAllInfo.getBack_sr_cd()+airLineAllInfo.getBack_sr_dd1()+airLineAllInfo.getBack_sr_dd2())/(airLineAllInfo.getBack_fxsj_dd1()+airLineAllInfo.getBack_fxsj_dd2());
		}else{
			int bzindex = 0;
			if(airLineAllInfo.getBack_xssr_cd()>0){
				bzindex++;
			}
			if(airLineAllInfo.getBack_xssr_dd1()>0){
				bzindex++;
			}
			if(airLineAllInfo.getBack_xssr_dd2()>0){
				bzindex++;
			}
			if(bzindex>0){
				back_xssr = (airLineAllInfo.getBack_xssr_cd()+airLineAllInfo.getBack_xssr_dd1()+airLineAllInfo.getBack_xssr_dd2())/bzindex;
			}else{
				back_xssr = 0;
			}
		}
		everyDuanInfo.setBack_xssr(back_xssr);
		//来回的小时收入（如果来回都有小时收入=求平均，否则等于有的航班的小时收入）
		if(everyDuanInfo.getGo_xssr()>0&&everyDuanInfo.getBack_xssr()>0){
			goAndBack_xssr = (everyDuanInfo.getGo_xssr() + everyDuanInfo.getBack_xssr())/2;
		}else{
			if(everyDuanInfo.getGo_xssr()>0){
				goAndBack_xssr = everyDuanInfo.getGo_xssr();
			}else if(everyDuanInfo.getBack_xssr()>0){
				goAndBack_xssr = everyDuanInfo.getBack_xssr();
			}else{
				goAndBack_xssr = 0;
			}
		}
		everyDuanInfo.setGoAndBack_xssr(goAndBack_xssr);
		//去的平均客座率（不缺段并且分母指标完整情况下=（短1人数+短2人数+2*长段人数）/cap,否者等于有数据的段求平均）
		if(airLineAllInfo.getGo_index()>2&&airLineAllInfo.getGo_rs_cd()>0&&airLineAllInfo.getGo_rs_dd1()>0&&airLineAllInfo.getGo_rs_dd2()>0&&airLineAllInfo.getGo_cap()>0){
			go_kzl = (airLineAllInfo.getGo_rs_cd()*2+airLineAllInfo.getGo_rs_dd1()+airLineAllInfo.getGo_rs_dd2())/(2*airLineAllInfo.getGo_cap())*100;
		}else{
			int bzindex = 0;
			if(airLineAllInfo.getGo_kzl_cd()>0){
				bzindex++;
			}
			if(airLineAllInfo.getGo_kzl_dd1()>0){
				bzindex++;
			}
			if(airLineAllInfo.getGo_kzl_dd2()>0){
				bzindex++;
			}
			if(bzindex>0){
				go_kzl = (airLineAllInfo.getGo_kzl_cd()+airLineAllInfo.getGo_kzl_dd1()+airLineAllInfo.getGo_kzl_dd2())/bzindex;
			}else{
				go_kzl = 0;
			}
		}
		everyDuanInfo.setGo_kzl(go_kzl);
		//回的平均客座率（不缺段并且分母指标完整情况下=（短1人数+短2人数+2*长段人数）/cap,否者等于有数据的段求平均）
		if(airLineAllInfo.getBack_index()>2&&airLineAllInfo.getBack_rs_cd()>0&&airLineAllInfo.getBack_rs_dd1()>0&&airLineAllInfo.getBack_rs_dd2()>0&&airLineAllInfo.getBack_cap()>0){
			back_kzl = (airLineAllInfo.getBack_rs_cd()*2+airLineAllInfo.getBack_rs_dd1()+airLineAllInfo.getBack_rs_dd2())/(2*airLineAllInfo.getBack_cap())*100;
		}else{
			int bzindex = 0;
			if(airLineAllInfo.getBack_kzl_cd()>0){
				bzindex++;
			}
			if(airLineAllInfo.getBack_kzl_dd1()>0){
				bzindex++;
			}
			if(airLineAllInfo.getBack_kzl_dd2()>0){
				bzindex++;
			}
			if(bzindex>0){
				back_kzl = (airLineAllInfo.getBack_kzl_cd()+airLineAllInfo.getBack_kzl_dd1()+airLineAllInfo.getBack_kzl_dd2())/bzindex;
			}else{
				back_kzl = 0;
			}
		}
		everyDuanInfo.setBack_kzl(back_kzl);
		//来回的客座率（如果来回都有客座率=求平均，否则等于有的航班的客座率）
		if(everyDuanInfo.getGo_kzl()>0&&everyDuanInfo.getBack_kzl()>0){
			goAndBack_kzl = (everyDuanInfo.getGo_kzl() + everyDuanInfo.getBack_kzl())/2;
		}else{
			if(everyDuanInfo.getGo_kzl()>0){
				goAndBack_kzl = everyDuanInfo.getGo_kzl();
			}else if(everyDuanInfo.getBack_kzl()>0){
				goAndBack_kzl = everyDuanInfo.getBack_kzl();
			}else{
				goAndBack_kzl = 0;
			}
		}
		everyDuanInfo.setGoAndBack_kzl(goAndBack_kzl);
		//去收入、回的收入、来回的收入都是有数据的求和
		go_sr = airLineAllInfo.getGo_sr_cd()+airLineAllInfo.getGo_sr_dd1()+airLineAllInfo.getGo_sr_dd2();
		back_sr = airLineAllInfo.getBack_sr_cd()+airLineAllInfo.getBack_sr_dd1()+airLineAllInfo.getBack_sr_dd2();
		goAndBack_sr = go_sr + back_sr;
		everyDuanInfo.setGo_sr(go_sr);
		everyDuanInfo.setBack_sr(back_sr);
		everyDuanInfo.setGoAndBack_sr(goAndBack_sr);
		//去团队收入、回团队的收入、来回团队的收入都是有数据的求和
		go_tdsr = airLineAllInfo.getGo_tdsr_cd()+airLineAllInfo.getGo_tdsr_dd1()+airLineAllInfo.getGo_tdsr_dd2();
		back_tdsr = airLineAllInfo.getBack_tdsr_cd()+airLineAllInfo.getBack_tdsr_dd1()+airLineAllInfo.getBack_tdsr_dd2();
		goAndBack_tdsr = go_tdsr + back_tdsr;
		everyDuanInfo.setGo_tdsr(go_tdsr);
		everyDuanInfo.setBack_tdsr(back_tdsr);
		everyDuanInfo.setGoAndBack_tdsr(goAndBack_tdsr);
		//去人数、回的人数、来回的人数都是有数据的求和
		go_rs = airLineAllInfo.getGo_rs_cd()+airLineAllInfo.getGo_rs_dd1()+airLineAllInfo.getGo_rs_dd2();
		back_rs = airLineAllInfo.getBack_rs_cd()+airLineAllInfo.getBack_rs_dd1()+airLineAllInfo.getBack_rs_dd2();
		goAndBack_rs = go_rs + back_rs;
		everyDuanInfo.setGo_rs(go_rs);
		everyDuanInfo.setBack_rs(back_rs);
		everyDuanInfo.setGoAndBack_rs(goAndBack_rs);
		//去团队人数、回的团队人数、来回的团队人数都是有数据的求和
		go_tdrs = airLineAllInfo.getGo_tdrs_cd()+airLineAllInfo.getGo_tdrs_dd1()+airLineAllInfo.getGo_tdrs_dd2();
		back_tdrs = airLineAllInfo.getBack_tdrs_cd()+airLineAllInfo.getBack_tdrs_dd1()+airLineAllInfo.getBack_tdrs_dd2();
		goAndBack_tdrs = go_tdrs + back_tdrs;
		everyDuanInfo.setGo_tdrs(go_tdrs);
		everyDuanInfo.setBack_tdrs(back_tdrs);
		everyDuanInfo.setGoAndBack_tdrs(goAndBack_tdrs);
		//去的飞行时间，回的飞行时间，来回的飞行时间
		if(airLineAllInfo.getGo_fxsj_dd1()>0||airLineAllInfo.getGo_fxsj_dd2()>0){
			go_fxsj = airLineAllInfo.getGo_fxsj_dd1()+airLineAllInfo.getGo_fxsj_dd2();
		}else{
			go_fxsj = airLineAllInfo.getGo_fxsj_cd();
		}
		if(airLineAllInfo.getBack_fxsj_dd1()>0||airLineAllInfo.getBack_fxsj_dd2()>0){
			back_fxsj = airLineAllInfo.getBack_fxsj_dd1()+airLineAllInfo.getBack_fxsj_dd2();
		}else{
			back_fxsj = airLineAllInfo.getBack_fxsj_cd();
		}
		
		goAndBack_fxsj = go_fxsj + back_fxsj;
		everyDuanInfo.setGo_fxsj(go_fxsj);
		everyDuanInfo.setBack_fxsj(back_fxsj);
		everyDuanInfo.setGoAndBack_fxsj(goAndBack_fxsj);
		return everyDuanInfo;
	}
	/**
	 * 组装某一天的航线对应的各个段的详细数据
	 * @Title: getAirLineAllInfo 
	 * @Description:  
	 * @param @param list
	 * @param @param day
	 * @param @param dpt_AirPt_CdCode
	 * @param @param pas_stpCode
	 * @param @param arrv_Airpt_CdCode
	 * @param @return    
	 * @return AirLineAllInfo     
	 * @throws
	 */
	public static AirLineAllInfo getAirLineAllInfo(List<Z_Airdata> list,String day,String dpt_AirPt_CdCode,String pas_stpCode,String arrv_Airpt_CdCode ){
		AirLineAllInfo airLineAllInfo = new AirLineAllInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!TextUtil.isEmpty(pas_stpCode)){
			for (Z_Airdata z_Airdata : list) {
				String dpt = z_Airdata.getDpt_AirPt_Cd();
				String arr = z_Airdata.getArrv_Airpt_Cd();
				if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
					//去程
					if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode))||(dpt.equals(dpt_AirPt_CdCode)&&arr.equals(arrv_Airpt_CdCode))||(dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode))){
						//短1
						if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(pas_stpCode))){
							airLineAllInfo.setGo_sr_dd1(z_Airdata.getTotalNumber());//收入
							airLineAllInfo.setGo_tdsr_dd1(z_Airdata.getGrp_Ine().doubleValue());//团队收入
							airLineAllInfo.setGo_ftzws_dd1(z_Airdata.getTal_Nbr_Set());//分摊座位数
							if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm())||"0".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm())||"0".equals(z_Airdata.getLcl_Dpt_Tm()))){
								airLineAllInfo.setGo_fxsj_dd1(0.0);//飞行时间
								airLineAllInfo.setGo_xssr_dd1(0.0);//小时收入
							}else{
								if(Long.parseLong(z_Airdata.getLcl_Arrv_Tm())>Long.parseLong(z_Airdata.getLcl_Dpt_Tm())){
									airLineAllInfo.setGo_fxsj_dd1(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000));
								}else{
									airLineAllInfo.setGo_fxsj_dd1(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000)+24);
								}
								airLineAllInfo.setGo_xssr_dd1(airLineAllInfo.getGo_sr_dd1()/airLineAllInfo.getGo_fxsj_dd1());//小时收入
							}
							airLineAllInfo.setGo_hj_dd1(z_Airdata.getSailingDistance());//航距
							airLineAllInfo.setGo_zk_dd1(z_Airdata.getAvg_Dct().doubleValue());//折扣
							airLineAllInfo.setGo_tdzk_dd1(z_Airdata.getGrp_Dct().doubleValue());//团队折扣
							airLineAllInfo.setGo_rs_dd1(z_Airdata.getPgs_Per_Cls());//人数
							airLineAllInfo.setGo_tdrs_dd1(z_Airdata.getGrp_Nbr());//团队
							if(z_Airdata.getCount_Set()>0){
								airLineAllInfo.setGo_cap(z_Airdata.getCount_Set());//布局座位数
							}
							airLineAllInfo.setGo_index(airLineAllInfo.getGo_index()+1);
							if(z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
								airLineAllInfo.setGo_zgl_dd1((double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance());//座公里
							}else{
								airLineAllInfo.setGo_zgl_dd1(0);
							}
							if(z_Airdata.getTal_Nbr_Set()>0){
								airLineAllInfo.setGo_kzl_dd1((double)z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100);//客座率
							}
						}
						//短2
						if((dpt.equals(pas_stpCode)&&arr.equals(arrv_Airpt_CdCode))){
							airLineAllInfo.setGo_sr_dd2(z_Airdata.getTotalNumber());//收入
							airLineAllInfo.setGo_tdsr_dd2(z_Airdata.getGrp_Ine().doubleValue());//团队收入
							airLineAllInfo.setGo_ftzws_dd2(z_Airdata.getTal_Nbr_Set());//分摊座位数
							if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm())||"0".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm())||"0".equals(z_Airdata.getLcl_Dpt_Tm()))){
								airLineAllInfo.setGo_fxsj_dd2(0.0);//飞行时间
								airLineAllInfo.setGo_xssr_dd2(0.0);//小时收入
							}else{
								if(Long.parseLong(z_Airdata.getLcl_Arrv_Tm())>Long.parseLong(z_Airdata.getLcl_Dpt_Tm())){
									airLineAllInfo.setGo_fxsj_dd2(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000));
								}else{
									airLineAllInfo.setGo_fxsj_dd2(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000)+24);
								}
								airLineAllInfo.setGo_xssr_dd2(airLineAllInfo.getGo_sr_dd2()/airLineAllInfo.getGo_fxsj_dd2());//小时收入
							}
							airLineAllInfo.setGo_hj_dd2(z_Airdata.getSailingDistance());//航距
							airLineAllInfo.setGo_zk_dd2(z_Airdata.getAvg_Dct().doubleValue());//折扣
							airLineAllInfo.setGo_tdzk_dd2(z_Airdata.getGrp_Dct().doubleValue());//团队折扣
							airLineAllInfo.setGo_rs_dd2(z_Airdata.getPgs_Per_Cls());//人数
							airLineAllInfo.setGo_tdrs_dd2(z_Airdata.getGrp_Nbr());//团队
							if(z_Airdata.getCount_Set()>0){
								airLineAllInfo.setGo_cap(z_Airdata.getCount_Set());//布局座位数
							}
							airLineAllInfo.setGo_index(airLineAllInfo.getGo_index()+1);
							if(z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
								airLineAllInfo.setGo_zgl_dd2((double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance());//座公里
							}else{
								airLineAllInfo.setGo_zgl_dd2(0);
							}
							if(z_Airdata.getTal_Nbr_Set()>0){
								airLineAllInfo.setGo_kzl_dd2((double)z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100);//客座率
							}
						}
						//长段
						if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(arrv_Airpt_CdCode))){
							airLineAllInfo.setGo_sr_cd(z_Airdata.getTotalNumber());//收入
							airLineAllInfo.setGo_tdsr_cd(z_Airdata.getGrp_Ine().doubleValue());//团队收入
							airLineAllInfo.setGo_ftzws_cd(z_Airdata.getTal_Nbr_Set());//分摊座位数
							if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm())||"0".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm())||"0".equals(z_Airdata.getLcl_Dpt_Tm()))){
								airLineAllInfo.setGo_fxsj_cd(0.0);//飞行时间
								airLineAllInfo.setGo_xssr_cd(0.0);//小时收入
							}else{
								if(Long.parseLong(z_Airdata.getLcl_Arrv_Tm())>Long.parseLong(z_Airdata.getLcl_Dpt_Tm())){
									airLineAllInfo.setGo_fxsj_cd(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000));
								}else{
									airLineAllInfo.setGo_fxsj_cd(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000)+24);
								}
								airLineAllInfo.setGo_xssr_cd(airLineAllInfo.getGo_sr_cd()/airLineAllInfo.getGo_fxsj_cd());//小时收入
							}
							airLineAllInfo.setGo_hj_cd(z_Airdata.getSailingDistance());//航距
							airLineAllInfo.setGo_zk_cd(z_Airdata.getAvg_Dct().doubleValue());//折扣
							airLineAllInfo.setGo_tdzk_cd(z_Airdata.getGrp_Dct().doubleValue());//团队折扣
							airLineAllInfo.setGo_rs_cd(z_Airdata.getPgs_Per_Cls());//人数
							airLineAllInfo.setGo_tdrs_cd(z_Airdata.getGrp_Nbr());//团队
							if(z_Airdata.getCount_Set()>0){
								airLineAllInfo.setGo_cap(z_Airdata.getCount_Set());//布局座位数
							}
							airLineAllInfo.setGo_index(airLineAllInfo.getGo_index()+1);
							if(z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
								airLineAllInfo.setGo_zgl_cd((double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance());//座公里
							}else{
								airLineAllInfo.setGo_zgl_cd(0);
							}
							if(z_Airdata.getTal_Nbr_Set()>0){
								airLineAllInfo.setGo_kzl_cd((double)z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100);//客座率
							}
						}
					}
					//返程
					if((dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(dpt_AirPt_CdCode))||(dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode))){
						//短1
						if((dpt.equals(arrv_Airpt_CdCode)&&arr.equals(pas_stpCode))){
							airLineAllInfo.setBack_sr_dd1(z_Airdata.getTotalNumber());//收入
							airLineAllInfo.setBack_tdsr_dd1(z_Airdata.getGrp_Ine().doubleValue());//团队收入
							airLineAllInfo.setBack_ftzws_dd1(z_Airdata.getTal_Nbr_Set());//分摊座位数
							if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm())||"0".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm())||"0".equals(z_Airdata.getLcl_Dpt_Tm()))){
								airLineAllInfo.setBack_fxsj_dd1(0.0);//飞行时间
								airLineAllInfo.setBack_xssr_dd1(0.0);//小时收入
							}else{
								if(Long.parseLong(z_Airdata.getLcl_Arrv_Tm())>Long.parseLong(z_Airdata.getLcl_Dpt_Tm())){
									airLineAllInfo.setBack_fxsj_dd1(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000));
								}else{
									airLineAllInfo.setBack_fxsj_dd1(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000)+24);
								}
								airLineAllInfo.setBack_xssr_dd1(airLineAllInfo.getBack_sr_dd1()/airLineAllInfo.getBack_fxsj_dd1());//小时收入
							}
							airLineAllInfo.setBack_hj_dd1(z_Airdata.getSailingDistance());//航距
							airLineAllInfo.setBack_zk_dd1(z_Airdata.getAvg_Dct().doubleValue());//折扣
							airLineAllInfo.setBack_tdzk_dd1(z_Airdata.getGrp_Dct().doubleValue());//团队折扣
							airLineAllInfo.setBack_rs_dd1(z_Airdata.getPgs_Per_Cls());//人数
							airLineAllInfo.setBack_tdrs_dd1(z_Airdata.getGrp_Nbr());//团队
							if(z_Airdata.getCount_Set()>0){
								airLineAllInfo.setBack_cap(z_Airdata.getCount_Set());//布局座位数
							}
							airLineAllInfo.setBack_index(airLineAllInfo.getBack_index()+1);
							if(z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
								airLineAllInfo.setBack_zgl_dd1((double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance());//座公里
							}else{
								airLineAllInfo.setBack_zgl_dd1(0);
							}
							if(z_Airdata.getTal_Nbr_Set()>0){
								airLineAllInfo.setBack_kzl_dd1((double)z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100);//客座率
							}
						}
						//短2
						if((dpt.equals(pas_stpCode)&&arr.equals(dpt_AirPt_CdCode))){
							airLineAllInfo.setBack_sr_dd2(z_Airdata.getTotalNumber());//收入
							airLineAllInfo.setBack_tdsr_dd2(z_Airdata.getGrp_Ine().doubleValue());//团队收入
							airLineAllInfo.setBack_ftzws_dd2(z_Airdata.getTal_Nbr_Set());//分摊座位数
							if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm())||"0".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm())||"0".equals(z_Airdata.getLcl_Dpt_Tm()))){
								airLineAllInfo.setBack_fxsj_dd2(0.0);//飞行时间
								airLineAllInfo.setBack_xssr_dd2(0.0);//小时收入
							}else{
								if(Long.parseLong(z_Airdata.getLcl_Arrv_Tm())>Long.parseLong(z_Airdata.getLcl_Dpt_Tm())){
									airLineAllInfo.setBack_fxsj_dd2(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000));
								}else{
									airLineAllInfo.setBack_fxsj_dd2(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000)+24);
								}
								airLineAllInfo.setBack_xssr_dd2(airLineAllInfo.getBack_sr_dd2()/airLineAllInfo.getBack_fxsj_dd2());//小时收入
							}
							airLineAllInfo.setBack_hj_dd2(z_Airdata.getSailingDistance());//航距
							airLineAllInfo.setBack_zk_dd2(z_Airdata.getAvg_Dct().doubleValue());//折扣
							airLineAllInfo.setBack_tdzk_dd2(z_Airdata.getGrp_Dct().doubleValue());//团队折扣
							airLineAllInfo.setBack_rs_dd2(z_Airdata.getPgs_Per_Cls());//人数
							airLineAllInfo.setBack_tdrs_dd2(z_Airdata.getGrp_Nbr());//团队
							if(z_Airdata.getCount_Set()>0){
								airLineAllInfo.setBack_cap(z_Airdata.getCount_Set());//布局座位数
							}
							airLineAllInfo.setBack_index(airLineAllInfo.getBack_index()+1);
							if(z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
								airLineAllInfo.setBack_zgl_dd2((double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance());//座公里
							}else{
								airLineAllInfo.setBack_zgl_dd2(0);
							}
							if(z_Airdata.getTal_Nbr_Set()>0){
								airLineAllInfo.setBack_kzl_dd2((double)z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100);//客座率
							}
						}
						//长段
						if((dpt.equals(arrv_Airpt_CdCode)&&arr.equals(dpt_AirPt_CdCode))){
							airLineAllInfo.setBack_sr_cd(z_Airdata.getTotalNumber());//收入
							airLineAllInfo.setBack_tdsr_cd(z_Airdata.getGrp_Ine().doubleValue());//团队收入
							airLineAllInfo.setBack_ftzws_cd(z_Airdata.getTal_Nbr_Set());//分摊座位数
							if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm())||"0".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm())||"0".equals(z_Airdata.getLcl_Dpt_Tm()))){
								airLineAllInfo.setBack_fxsj_cd(0.0);//飞行时间
								airLineAllInfo.setBack_xssr_cd(0.0);//小时收入
							}else{
								if(Long.parseLong(z_Airdata.getLcl_Arrv_Tm())>Long.parseLong(z_Airdata.getLcl_Dpt_Tm())){
									airLineAllInfo.setBack_fxsj_cd(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000));
								}else{
									airLineAllInfo.setBack_fxsj_cd(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000)+24);
								}
								airLineAllInfo.setBack_xssr_cd(airLineAllInfo.getBack_sr_cd()/airLineAllInfo.getBack_fxsj_cd());//小时收入
							}
							airLineAllInfo.setBack_hj_cd(z_Airdata.getSailingDistance());//航距
							airLineAllInfo.setBack_zk_cd(z_Airdata.getAvg_Dct().doubleValue());//折扣
							airLineAllInfo.setBack_tdzk_cd(z_Airdata.getGrp_Dct().doubleValue());//团队折扣
							airLineAllInfo.setBack_rs_cd(z_Airdata.getPgs_Per_Cls());//人数
							airLineAllInfo.setBack_tdrs_cd(z_Airdata.getGrp_Nbr());//团队
							if(z_Airdata.getCount_Set()>0){
								airLineAllInfo.setBack_cap(z_Airdata.getCount_Set());//布局座位数
							}
							airLineAllInfo.setBack_index(airLineAllInfo.getBack_index()+1);
							if(z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
								airLineAllInfo.setBack_zgl_cd((double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance());//座公里
							}else{
								airLineAllInfo.setBack_zgl_cd(0);
							}
							if(z_Airdata.getTal_Nbr_Set()>0){
								airLineAllInfo.setBack_kzl_cd((double)z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100);//客座率
							}
						}
					}
				}
			}
		}else{
			for (Z_Airdata z_Airdata : list) {
				String dpt = z_Airdata.getDpt_AirPt_Cd();
				String arr = z_Airdata.getArrv_Airpt_Cd();
				if(day.equals(sdf.format(z_Airdata.getLcl_Dpt_Day()))){
					//去程
					if((dpt.equals(dpt_AirPt_CdCode)&&arr.equals(arrv_Airpt_CdCode))){
						airLineAllInfo.setGo_sr_cd(z_Airdata.getTotalNumber());//收入
						airLineAllInfo.setGo_tdsr_cd(z_Airdata.getGrp_Ine().doubleValue());//团队收入
						airLineAllInfo.setGo_ftzws_cd(z_Airdata.getTal_Nbr_Set());//分摊座位数
						if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm())||"0".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm())||"0".equals(z_Airdata.getLcl_Dpt_Tm()))){
							airLineAllInfo.setGo_fxsj_cd(0.0);//飞行时间
							airLineAllInfo.setGo_xssr_cd(0.0);//小时收入
						}else{
							if(Long.parseLong(z_Airdata.getLcl_Arrv_Tm())>Long.parseLong(z_Airdata.getLcl_Dpt_Tm())){
								airLineAllInfo.setGo_fxsj_cd(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000));
							}else{
								airLineAllInfo.setGo_fxsj_cd(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000)+24);
							}
							airLineAllInfo.setGo_xssr_cd(airLineAllInfo.getGo_sr_cd()/airLineAllInfo.getGo_fxsj_cd());//小时收入
						}
						airLineAllInfo.setGo_hj_cd(z_Airdata.getSailingDistance());//航距
						airLineAllInfo.setGo_zk_cd(z_Airdata.getAvg_Dct().doubleValue());//折扣
						airLineAllInfo.setGo_tdzk_cd(z_Airdata.getGrp_Dct().doubleValue());//团队折扣
						airLineAllInfo.setGo_rs_cd(z_Airdata.getPgs_Per_Cls());//人数
						airLineAllInfo.setGo_tdrs_cd(z_Airdata.getGrp_Nbr());//团队
						if(z_Airdata.getCount_Set()>0){
							airLineAllInfo.setGo_cap(z_Airdata.getCount_Set());//布局座位数
						}
						airLineAllInfo.setGo_index(airLineAllInfo.getGo_index()+1);
						if(z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
							airLineAllInfo.setGo_zgl_cd((double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance());//座公里
						}else{
							airLineAllInfo.setGo_zgl_cd(0);
						}
						if(z_Airdata.getTal_Nbr_Set()>0){
							airLineAllInfo.setGo_kzl_cd((double)z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100);//客座率
						}
					}
					//返程
					if((dpt.equals(arrv_Airpt_CdCode)&&arr.equals(dpt_AirPt_CdCode))){
						airLineAllInfo.setBack_sr_cd(z_Airdata.getTotalNumber());//收入
						airLineAllInfo.setBack_tdsr_cd(z_Airdata.getGrp_Ine().doubleValue());//团队收入
						airLineAllInfo.setBack_ftzws_cd(z_Airdata.getTal_Nbr_Set());//分摊座位数
						if((TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())||"".equals(z_Airdata.getLcl_Arrv_Tm())||"0".equals(z_Airdata.getLcl_Arrv_Tm()))||(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())||"".equals(z_Airdata.getLcl_Dpt_Tm())||"0".equals(z_Airdata.getLcl_Dpt_Tm()))){
							airLineAllInfo.setBack_fxsj_cd(0.0);//飞行时间
							airLineAllInfo.setBack_xssr_cd(0.0);//小时收入
						}else{
							if(Long.parseLong(z_Airdata.getLcl_Arrv_Tm())>Long.parseLong(z_Airdata.getLcl_Dpt_Tm())){
								airLineAllInfo.setBack_fxsj_cd(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000));
							}else{
								airLineAllInfo.setBack_fxsj_cd(((Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Arrv_Tm())?"0.0":z_Airdata.getLcl_Arrv_Tm()) - Double.parseDouble(TextUtil.isEmpty(z_Airdata.getLcl_Dpt_Tm())?"0.0":z_Airdata.getLcl_Dpt_Tm()))/3600/1000)+24);
							}
							airLineAllInfo.setBack_xssr_cd(airLineAllInfo.getBack_sr_cd()/airLineAllInfo.getBack_fxsj_cd());//小时收入
						}
						airLineAllInfo.setBack_hj_cd(z_Airdata.getSailingDistance());//航距
						airLineAllInfo.setBack_zk_cd(z_Airdata.getAvg_Dct().doubleValue());//折扣
						airLineAllInfo.setBack_tdzk_cd(z_Airdata.getGrp_Dct().doubleValue());//团队折扣
						airLineAllInfo.setBack_rs_cd(z_Airdata.getPgs_Per_Cls());//人数
						airLineAllInfo.setBack_tdrs_cd(z_Airdata.getGrp_Nbr());//团队
						if(z_Airdata.getCount_Set()>0){
							airLineAllInfo.setBack_cap(z_Airdata.getCount_Set());//布局座位数
						}
						airLineAllInfo.setBack_index(airLineAllInfo.getBack_index()+1);
						if(z_Airdata.getTal_Nbr_Set()>0&&z_Airdata.getSailingDistance()>0){
							airLineAllInfo.setBack_zgl_cd((double)z_Airdata.getTotalNumber()/(double)z_Airdata.getTal_Nbr_Set()/(double)z_Airdata.getSailingDistance());//座公里
						}else{
							airLineAllInfo.setBack_zgl_cd(0);
						}
						if(z_Airdata.getTal_Nbr_Set()>0){
							airLineAllInfo.setBack_kzl_cd((double)z_Airdata.getPgs_Per_Cls()/(double)z_Airdata.getTal_Nbr_Set()*100);//客座率
						}
					}
				}
			}
		}
		return airLineAllInfo;
	}
	
	
}
