package org.ldd.ssm.crm.utils;

import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ldd.ssm.crm.domain.AllClzIncome;
import org.ldd.ssm.crm.domain.DOW;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.DOWQuery;
import org.ldd.ssm.crm.query.SystemLogQuery;

import com.mysql.jdbc.Connection;
/**
 * 数据的方法类
 */
public class DataUtils {
		/**
		 * 判断两个时间是不是同一天
		 * @param date1
		 * @param date2
		 * @return
		 */
		public static boolean isSameDate(Date date1, Date date2) {
	       Calendar cal1 = Calendar.getInstance();
	       cal1.setTime(date1);

	       Calendar cal2 = Calendar.getInstance();
	       cal2.setTime(date2);

	       boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
	               .get(Calendar.YEAR);
	       boolean isSameMonth = isSameYear
	               && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
	       boolean isSameDate = isSameMonth
	               && cal1.get(Calendar.DAY_OF_MONTH) == cal2
	                       .get(Calendar.DAY_OF_MONTH);

	       return isSameDate;
		}
	    /**
	     * 根据日期获得星期的方法
	     * @param date 年月日 2015-1-1
	     * @return
	     */
	    public static String getWeek(Date date){  
	        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};  
	        Calendar cal = Calendar.getInstance();  
	        cal.setTime(date);  
	        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;  
	        if(week_index<0){  
	            week_index = 0;  
	        }   
	        return weeks[week_index];  
	    }
	    /***
	     * 获得时分秒
	     * @param replaceAll eterm时间格式
	     * @return
	     */
		public static Time getTime(String replaceAll) {
			String rep = replaceAll.substring(0, 2)+":"+replaceAll.substring(2, 4)+":00";
			Time valueOf = Time.valueOf(rep);
			return valueOf;
		}
		
		/**
		 * 排除空数据
		 */
		public static List<String> RemovEmpty(String[] split4){
			List<String> portList = new ArrayList<String>();
			for (int i = 0; i < split4.length; i++) {
				String replaceAll2 = split4[i].replaceAll("\\s*|\t|\r|\n", "");
				if(replaceAll2!=""&&replaceAll2.length()>0){
					portList.add(replaceAll2);
				}
			}
			return portList;
		}
		public static int avg(List<String> datas) {
			int array = 0;
			for (String monthData : datas) {
				array+=Integer.valueOf(monthData);
			}
			return array;
		}
		/**
		 * 根据传入的月份装入之前的几个月
		 * @param searchKey
		 * @return
		 */
		public static List<String> getArrayMethod(SystemLogQuery searchKey) {
			List<String> arrMehtod = new ArrayList<String>();
			if("01".equals(searchKey.getSearchKey())){
				arrMehtod.add("01");
			}else if("02".equals(searchKey.getSearchKey())){
				arrMehtod.add("01");
				arrMehtod.add("02");
			}else if("02".equals(searchKey.getSearchKey())){
				arrMehtod.add("01");
				arrMehtod.add("02");
			}else if("03".equals(searchKey.getSearchKey())){
				arrMehtod.add("01");
				arrMehtod.add("02");
				arrMehtod.add("03");
			}else if("04".equals(searchKey.getSearchKey())){
				arrMehtod.add("01");
				arrMehtod.add("02");
				arrMehtod.add("03");
				arrMehtod.add("04");
			}else if("05".equals(searchKey.getSearchKey())){
				arrMehtod.add("02");
				arrMehtod.add("03");
				arrMehtod.add("04");
				arrMehtod.add("05");
			}else if("06".equals(searchKey.getSearchKey())){
				arrMehtod.add("03");
				arrMehtod.add("04");
				arrMehtod.add("05");
				arrMehtod.add("06");
			}else if("07".equals(searchKey.getSearchKey())){
				arrMehtod.add("04");
				arrMehtod.add("05");
				arrMehtod.add("06");
				arrMehtod.add("07");
			}else if("08".equals(searchKey.getSearchKey())){
				arrMehtod.add("05");
				arrMehtod.add("06");
				arrMehtod.add("07");
				arrMehtod.add("08");
			}else if("09".equals(searchKey.getSearchKey())){
				arrMehtod.add("06");
				arrMehtod.add("07");
				arrMehtod.add("08");
				arrMehtod.add("09");
			}else if("10".equals(searchKey.getSearchKey())){
				arrMehtod.add("07");
				arrMehtod.add("08");
				arrMehtod.add("09");
				arrMehtod.add("10");
			}else if("11".equals(searchKey.getSearchKey())){
				arrMehtod.add("08");
				arrMehtod.add("09");
				arrMehtod.add("10");
				arrMehtod.add("11");
			}else if("12".equals(searchKey.getSearchKey())){
				arrMehtod.add("09");
				arrMehtod.add("10");
				arrMehtod.add("11");
				arrMehtod.add("12");
			}
			return arrMehtod;
		}
		/**
		 * 将数据组装成需要的数据
		 * @param lists
		 * @return
		 */
		public static List<Map<String, String>> getNeedData(List<List<String[]>> lists) {
			List<Map<String, String>> mapList = new ArrayList<Map<String,String>>();
			List<List<String>> list = new ArrayList<List<String>>();
			List<String> one_method = new ArrayList<String>();
			List<String> two_method = new ArrayList<String>();
			List<String> three_method = new ArrayList<String>();
			List<String> four_method = new ArrayList<String>();
			List<String> five_method = new ArrayList<String>();
			List<String> six_method = new ArrayList<String>();
			List<String> seven_method = new ArrayList<String>();
			for (List<String[]> list2 : lists) {
				if(!list2.isEmpty()){
					one_method.add(list2.get(0)[1]);
					two_method.add(list2.get(1)[1]);
					three_method.add(list2.get(2)[1]);
					four_method.add(list2.get(3)[1]);
					five_method.add(list2.get(4)[1]);
					six_method.add(list2.get(5)[1]);
					seven_method.add(list2.get(6)[1]);
				}
			}
			list.add(one_method);
			list.add(two_method);
			list.add(three_method);
			list.add(four_method);
			list.add(five_method);
			list.add(six_method);
			list.add(seven_method);
			if(list.get(0).size()==1){
				for (List<String> string : list) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("one_method", string.get(0));
					mapList.add(map);
				}
			}else if(list.get(0).size()==2){
				for (List<String> string : list) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("one_method", string.get(0));
					map.put("two_method", string.get(1));
					mapList.add(map);
				}
			}else if(list.get(0).size()==3){
				for (List<String> string : list) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("one_method", string.get(0));
					map.put("two_method", string.get(1));
					map.put("three_method", string.get(2));
					mapList.add(map);
				}
			}else{
				for (List<String> string : list) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("one_method", string.get(0));
						map.put("two_method", string.get(1));
						map.put("three_method", string.get(2));
						map.put("four_method", string.get(3));
						mapList.add(map);
				}
			}
			return mapList;
		}
		public static List<Map<String, String>> getNullData() {
			List<Map<String, String>> mapList = new ArrayList<Map<String,String>>();
			List<List<String>> list = new ArrayList<List<String>>();
			List<String> one_method = new ArrayList<String>();
			List<String> two_method = new ArrayList<String>();
			List<String> three_method = new ArrayList<String>();
			List<String> four_method = new ArrayList<String>();
			List<String> five_method = new ArrayList<String>();
			List<String> six_method = new ArrayList<String>();
			List<String> seven_method = new ArrayList<String>();
//			one_method.add("0");
//			two_method.add("0");
//			three_method.add("0");
//			four_method.add("0");
//			five_method.add("0");
//			six_method.add("0");
//			seven_method.add("0");
			list.add(one_method);
			list.add(two_method);
			list.add(three_method);
			list.add(four_method);
			list.add(five_method);
			list.add(six_method);
			list.add(seven_method);
//			if(list.get(0).size()==1){
//				for (List<String> string : list) {
//					Map<String, String> map = new HashMap<String, String>();
//					map.put("one_method", string.get(0));
//					mapList.add(map);
//				}
//			}else if(list.get(0).size()==2){
//				for (List<String> string : list) {
//					Map<String, String> map = new HashMap<String, String>();
//					map.put("one_method", string.get(0));
//					map.put("two_method", string.get(1));
//					mapList.add(map);
//				}
//			}else if(list.get(0).size()==3){
//				for (List<String> string : list) {
//					Map<String, String> map = new HashMap<String, String>();
//					map.put("one_method", string.get(0));
//					map.put("two_method", string.get(1));
//					map.put("three_method", string.get(2));
//					mapList.add(map);
//				}
//			}else{
//				for (List<String> string : list) {
//						Map<String, String> map = new HashMap<String, String>();
//						map.put("one_method", string.get(0));
//						map.put("two_method", string.get(1));
//						map.put("three_method", string.get(2));
//						map.put("four_method", string.get(3));
//						mapList.add(map);
//				}
//			}
			return mapList;
		}
		public static Map<String, Object> getNullMap(SystemLogQuery searchKey, DOWQuery dowData2) {
			//最外层的json对象,用来装第二层的json数据
			Map<String, Object> rootMap = new HashMap<String, Object>();
			//第二层的json对象,用来装数据的
			List<Map<String, String>> echart2 = new ArrayList<Map<String, String>>();
			Map<String, String> echart = new HashMap<String, String>();
			//根据固定的key将值装入map中object[0]装的是分组名,objects[1],装的总数
			String indexData = UserContext.getIndexData("year");
			String[] split = indexData.split("-");
			echart.put("MonTotal", dowData2.getLcl_Dpt_Day()+"年"+searchKey.getSearchKey()+"月"+split[0]+"到"+split[1]+" DOW分析柱形图");
			//将json对象添加进数组中
			echart2.add(echart);
			//将第二层的json对象放入第一层的json对象,前台js会根据key拿到值
//			//第二层的数组, 里面装的json对象
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
//			List<String[]> list = new ArrayList<String[]>();
//			//根据分组查询条件拿到查询结果集
//			String[] str1 = {"星期一","0"};
//			String[] str2 = {"星期二","0"};
//			String[] str3 = {"星期三","0"};
//			String[] str4 = {"星期四","0"};
//			String[] str5 = {"星期五","0"};
//			String[] str6 = {"星期六","0"};
//			String[] str7 = {"星期日","0"};
//			list.add(str1);
//			list.add(str2);
//			list.add(str3);
//			list.add(str4);
//			list.add(str5);
//			list.add(str6);
//			list.add(str7);
//			//遍历结果集
//			for (String[] objects : list) {
////				//实例一个新的map
//				Map<String, String> map = new HashMap<String, String>();
//				//根据固定的key将值装入map中object[0]装的是分组名,objects[1],装的总数
//				map.put("idd_moh",objects[0]);
//				map.put("Cla_Nbr", objects[1]);
//				//将json对象添加进数组中
//				data.add(map);
//			}
//			//将数据装入第一层的json对象中
			rootMap.put("list", data);
			rootMap.put("echart2", echart2);
			//将json对象装入map栈中
			return rootMap;
		}
		public static Map<String, Object> getMap(List<DOW> rows, SystemLogQuery searchKey,
				DOWQuery dowData2) {
			//最外层的json对象,用来装第二层的json数据
			Map<String, Object> rootMap = new HashMap<String, Object>();
			//第二层的json对象,用来装数据的
			List<Map<String, String>> echart2 = new ArrayList<Map<String, String>>();
			Map<String, String> echart = new HashMap<String, String>();
			//根据固定的key将值装入map中object[0]装的是分组名,objects[1],装的总数
//			组装格式:20XX年XX月西昌到咸阳DOW执行班次统计
			echart.put("MonTotal", rows.get(0).getInformation()+" DOW执行班次统计");
			//将json对象添加进数组中
			echart2.add(echart);
			//将第二层的json对象放入第一层的json对象,前台js会根据key拿到值
//			//第二层的数组, 里面装的json对象
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			List<String[]> list = new ArrayList<String[]>();
//			//根据分组查询条件拿到查询结果集
			for (DOW dt : rows) {
				String[] str1 = {"星期一",dt.getMonTotal()+""};
				String[] str2 = {"星期二",dt.getTueTotal()+""};
				String[] str3 = {"星期三",dt.getWedTotal()+""};
				String[] str4 = {"星期四",dt.getThurTotal()+""};
				String[] str5 = {"星期五",dt.getFriTotal()+""};
				String[] str6 = {"星期六",dt.getSatTotal()+""};
				String[] str7 = {"星期日",dt.getSunTotal()+""};
				list.add(str1);
				list.add(str2);
				list.add(str3);
				list.add(str4);
				list.add(str5);
				list.add(str6);
				list.add(str7);
			}
			//遍历结果集
			for (String[] objects : list) {
//				//实例一个新的map
				Map<String, String> map = new HashMap<String, String>();
				//根据固定的key将值装入map中object[0]装的是分组名,objects[1],装的总数
				map.put("idd_moh",objects[0]);
				map.put("Cla_Nbr", objects[1]);
				//将json对象添加进数组中
				data.add(map);
			}
//			//将数据装入第一层的json对象中
			rootMap.put("list", data);
			rootMap.put("echart2", echart2);
			//将json对象装入map栈中
			return rootMap;
		}
		public static String getYearState(DOWQuery dowData) {
			StringBuffer buffer = new StringBuffer();
			if(dowData.getLcl_Dpt_Day()!=null&&dowData.getLcl_Dpt_Day()!=""){
				buffer.append(dowData.getLcl_Dpt_Day());
			}
			if(dowData.getDpt_AirPt_Cd()!=null&&dowData.getDpt_AirPt_Cd()!=""){
				buffer.append(dowData.getDpt_AirPt_Cd());
			}
			if(dowData.getArrv_Airpt_Cd()!=null&&dowData.getArrv_Airpt_Cd()!=""){
				buffer.append("-"+dowData.getArrv_Airpt_Cd());
			}
			if("1".equals(dowData.getState())){
				buffer.append("出港");
			}else if("0".equals(dowData.getState())){
				buffer.append("进港");
			}
			return buffer.toString();
		}
		public static AllClzIncome SortData(Z_Airdata z_Airdata,int yPrice,Z_Airdata grp_zAirdata) {
			AllClzIncome allClzIncome = new AllClzIncome();
			allClzIncome.setTal_Nbr_Set(z_Airdata.getTal_Nbr_Set()+"");
			allClzIncome.setDate(z_Airdata.getMethod());
			allClzIncome.setAirPort(z_Airdata.getDpt_AirPt_Cd()+z_Airdata.getArrv_Airpt_Cd());
			allClzIncome.setyPrice(yPrice+"");
			allClzIncome.setTwo_Tak_Ppt(z_Airdata.getTwo_Tak_Ppt()+"");
			allClzIncome.setFul_Pce_Ppt(z_Airdata.getFul_Pce_Ppt()+"");
			allClzIncome.setNne_Dnt_Ppt(z_Airdata.getNne_Dnt_Ppt()+"");
			allClzIncome.setEht_Five_Dnt_Ppt(z_Airdata.getEht_Five_Dnt_Ppt()+"");
			allClzIncome.setEht_Dnt_Ppt(z_Airdata.getEht_Dnt_Ppt()+"");
			allClzIncome.setSen_Five_Dnt_Ppt(z_Airdata.getSen_Five_Dnt_Ppt()+"");
			allClzIncome.setSen_Dnt_Ppt(z_Airdata.getSen_Dnt_Ppt()+"");
			allClzIncome.setSix_Five_Dnt_Ppt(z_Airdata.getSix_Five_Dnt_Ppt()+"");
			allClzIncome.setSix_Dnt_Ppt(z_Airdata.getSix_Dnt_Ppt()+"");
			allClzIncome.setFve_Fve_Dnt_Ppt(z_Airdata.getFve_Fve_Dnt_Ppt()+"");
			allClzIncome.setFve_Dnt_Ppt(z_Airdata.getFve_Dnt_Ppt()+"");
			allClzIncome.setFur_Fve_Dnt_Ppt(z_Airdata.getFur_Fve_Dnt_Ppt()+"");
			allClzIncome.setFur_Dnt_Ppt(z_Airdata.getFur_Dnt_Ppt()+"");
			allClzIncome.setThr_Fve_Dnt_Ppt(z_Airdata.getThr_Fve_Dnt_Ppt()+"");
			allClzIncome.setThr_Dnt_Ppt(z_Airdata.getThr_Dnt_Ppt()+"");
			allClzIncome.setTwo_Fve_Dnt_Ppt(z_Airdata.getTwo_Fve_Dnt_Ppt()+"");
			allClzIncome.setTwo_Dnt_Ppt(z_Airdata.getTwo_Dnt_Ppt()+"");
			allClzIncome.setSal_Tak_Ppt(z_Airdata.getSal_Tak_Ppt()+"");
			allClzIncome.setPgs_Per_Cls(z_Airdata.getPgs_Per_Cls()+"");//散人和团队人数总和
			allClzIncome.setWak_tol_Nbr(z_Airdata.getGrp_Fng_Per()+"");//散客总人数
			allClzIncome.setWak_tol_Ine(z_Airdata.getIdd_Dct().setScale(0, BigDecimal.ROUND_HALF_UP).toString());//散客总收入
			allClzIncome.setGrp_Ine(z_Airdata.getGrp_Ine().setScale(0, BigDecimal.ROUND_HALF_UP).toString());//团队总收入
			allClzIncome.setAvg_Dct(z_Airdata.getAvg_Dct().setScale(0, BigDecimal.ROUND_HALF_UP).toString());//平均折扣
			if(grp_zAirdata!=null){
				allClzIncome.setGrp_Nbr(grp_zAirdata.getGrp_Nbr()+"");
				if(grp_zAirdata.getGrp_Ine().compareTo(new BigDecimal(0))==1&&grp_zAirdata.getGrp_Nbr()>0){
					allClzIncome.setCutPriceTeam((grp_zAirdata.getGrp_Ine().divide(new BigDecimal(grp_zAirdata.getGrp_Nbr()),0,BigDecimal.ROUND_HALF_UP)).toString());//切位
				}else{
					allClzIncome.setCutPriceTeam("0");
				}
			}else{
				allClzIncome.setGrp_Nbr("0");
				allClzIncome.setCutPriceTeam("0");
			}
			return allClzIncome;
		}
		/**
		 * 
		 * @param year
		 * @param date
		 * @param type 表示年 月 日 时
		 * @return
		 */
		public static boolean isSameYear(String year,Date date,int type){
			boolean result = false;
			try {
				SimpleDateFormat format = null;
				if(type==0){
					format = new SimpleDateFormat("yyyy");
				}else if(type==1){
					format = new SimpleDateFormat("yyyy-MM");
				}else if(type==2){
					format = new SimpleDateFormat("yyyy-MM-dd");
				}else{
					format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				}
				result = year.equals(format.format(date));
			} catch (Exception e) {
				e.printStackTrace();
				return result;
			}
			return result;
		}
		
		public static Connection getConnection(){
			Connection con = null;
			
			return con;
		}
		
		public static String[] getCurrentSeasonTimes(String hasDataDate){
			String[] currentSeasonDate = new String[2];
			Date tt = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if(hasDataDate==null||"".equals(hasDataDate)){
				hasDataDate = format.format(new Date());
			}else{
				try {
					tt = format.parse(hasDataDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			String[] ydm = hasDataDate.split("-");
			Map<String,String> timeMapdc = TextUtil.getDCDate(Integer.parseInt(ydm[0]));
			Map<String,String> timeMapdc2 = TextUtil.getDCDate(Integer.parseInt(ydm[0])-1);
			Map<String,String> timeMapxq = TextUtil.getXQDate(Integer.parseInt(ydm[0]));
			String startTdc = timeMapdc.get("startTime");
			String endTdc = timeMapdc.get("endTime");
			String startTdc2 = timeMapdc2.get("startTime");
			String endTdc2 = timeMapdc2.get("endTime");
			String startTxq = timeMapxq.get("startTime");
			String endTxq = timeMapxq.get("endTime");
			try {
				Date d11 = format.parse(startTdc);
				Date d12 = format.parse(endTdc);
				Date d112 = format.parse(startTdc2);
				Date d122 = format.parse(endTdc2);
				Date d21 = format.parse(startTxq);
				Date d22 = format.parse(endTxq);
				if((tt.getTime()>=d11.getTime())&&(tt.getTime()<=d12.getTime())){
					currentSeasonDate[0] = startTdc;
					currentSeasonDate[1] = endTdc;
				}
				if((tt.getTime()>=d112.getTime())&&(tt.getTime()<=d122.getTime())){
					currentSeasonDate[0] = startTdc2;
					currentSeasonDate[1] = endTdc2;
				}
				if((tt.getTime()>=d21.getTime())&&(tt.getTime()<=d22.getTime())){
					currentSeasonDate[0] = startTxq;
					currentSeasonDate[1] = endTxq;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			return currentSeasonDate;
		}
		
		public static Set<String> formatFltNbr(List<String> flts){
			Set<String> fltlist = new HashSet<String>();
			for(int i=0;i<flts.size();i++){
				String flt=flts.get(i);
				char lastchar = flt.charAt(flt.length()-1);
				if(lastchar>=48&&lastchar<=57){
					int obj = Integer.valueOf(flt.substring(flt.length()-1));
					if(obj%2==0){
						if(obj==0){
							fltlist.add(getFltPair(0, flt.length()-2, flt));
						}else{
							fltlist.add(flt.substring(0,flt.length()-1)+(obj-1)+"/"+obj);
						}
					}else{
						if(obj==9){
							fltlist.add(getFltPair(1, flt.length()-2, flt));
						}else{
							fltlist.add(flt+"/"+(obj+1));
						}
					}
				}
			}
			return fltlist;
		}
		
		public static String getFltPair(int type,int index,String flt){
			String fltpair = "";
			int obj = Integer.valueOf(flt.substring(index,index+1));
			if(type==0){
				if(obj==0){
					fltpair = getFltPair(type,index-1,flt);
				}else{
					//拼接字符串
					String prevStr = "";
					String nextStr = "";
					for(int i=0;i<flt.length()-index-1;i++){
						prevStr += "9";
						nextStr += "0";
					}
					fltpair = flt.substring(0,index)+(obj-1)+prevStr+"/"+obj+nextStr;
				}
			}else{
				if(obj==9){
					fltpair = getFltPair(type,index-1,flt);
				}else{
					String prevStr = "";
					String nextStr = "";
					for(int i=0;i<flt.length()-index-1;i++){
						prevStr += "9";
						nextStr += "0";
					}
					fltpair = flt.substring(0,index)+obj+prevStr+"/"+(obj+1)+nextStr;
				}
			}
			return fltpair;
		}
		
		public static String[] getCurrentSeasonDate(){
			String[] currentSeasonDate = new String[2];
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date tt = new Date();
			String	hasDataDate = format.format(tt);
			String[] ydm = hasDataDate.split("-");
			Map<String,String> timeMapdc = TextUtil.getDCDate(Integer.parseInt(ydm[0]));
			Map<String,String> timeMapxq = TextUtil.getXQDate(Integer.parseInt(ydm[0]));
			String startTdc = timeMapdc.get("startTime");
			String endTdc = timeMapdc.get("endTime");
			String startTxq = timeMapxq.get("startTime");
			String endTxq = timeMapxq.get("endTime");
			try {
				Date d11 = format.parse(startTdc);
				Date d12 = format.parse(endTdc);
				Date d21 = format.parse(startTxq);
				Date d22 = format.parse(endTxq);
				if((tt.getTime()>=d11.getTime())&&(tt.getTime()<=d12.getTime())){
					currentSeasonDate[0] = startTdc;
					currentSeasonDate[1] = endTdc;
				}
				if((tt.getTime()>=d21.getTime())&&(tt.getTime()<=d22.getTime())){
					currentSeasonDate[0] = startTxq;
					currentSeasonDate[1] = endTxq;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			return currentSeasonDate;
		}
}
