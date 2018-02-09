package org.ldd.ssm.crm.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ldd.ssm.crm.domain.Fare;
import org.ldd.ssm.crm.domain.Z_Airdata;

/**
 * 验证数据合法性的工具类
 * 
 * @author Taimei
 * 
 */
public class ValidationDataUtils {
	/**
	 * 验证航程字符长度
	 * 
	 * @param str
	 * @return
	 */
	public static boolean validationVoyageLength(String str) {
		// 去除特殊字符
		String replaceAll = str.replaceAll("\\s*|\t|\r|\n", "");
		if (replaceAll.length() != 6) {
			return false;
		}
		return true;
	}

	/**
	 * 验证航班号字符长度,并且判断后四位是否为数值
	 * 
	 * @param str
	 * @return
	 */
	public static boolean validationFlightLength(String str) {
		// 去除特殊字符
		String replaceAll = str.replaceAll("\\s*|\t|\r|\n", "");
		if (replaceAll.length() == 6||replaceAll.length() == 7) {
			char[] charArray = str.substring(2, 5).toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				if (!Character.isDigit(charArray[i])) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	/***
	 * 验证航线数据长度是否符合标准
	 * 
	 * @param str
	 * @param useful 
	 * @return
	 */
	public static String validationFlightLineLength(String str, List<String[]> useful,String[] strs) {
		String replaceAll = str.replaceAll("\\s*|\t|\r|\n", "");
		if (replaceAll.length() == 6||replaceAll.length() == 9) {
			return str;
		} else {
			for (int i = 0; i < useful.size(); i++) {
				if(useful.get(i)[2].equals(strs[2])){
					return useful.get(i)[4];
				}
			}
		}
		return null;
	}

	/**
	 * 判断一个字符串是否为一个日期格式
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static boolean isDate(String date) {
		try {
			// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
			if (date.contains("/")&&date.split("\\/")[0].length()==4) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				// 设置lenient为false.
				// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
				format.setLenient(false);
				Date parse = format.parse(date);
				if (parse instanceof Date) {
					return true;
				}
			} else if(date.contains("/")&&date.split("\\/")[0].length()==2){
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				// 设置lenient为false.
				// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
				format.setLenient(false);
				Date parse = format.parse(date);
				if (parse instanceof Date) {
					return true;
				}
			}else if (date.contains("-")) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				// 设置lenient为false.
				// 否则SimpleDateFormat会比较宽松地验证日期，比如2007-02-29会被接受，并转换成2007/03/01
				format.setLenient(false);
				Date parse = format.parse(date);
				if (parse instanceof Date) {
					return true;
				}
			}
		} catch (ParseException e) {
			return false;
		}
		return false;
	}

	/***
	 * 对于每班座位数据进行验证和修正 每班座位＞0且≥每班旅客 有时候航班异常的时候座位数会变成0，如取消经停，旅客有可能保护到当天其他航班或者后续航班
	 * 按近七天正常客座率（即客座率≥O）取均值倒推；座位数=每班旅客/（均班客座/100） 需修正
	 * 
	 * @param str
	 * @param str2
	 * @param airdatas
	 * @return
	 * @throws Exception
	 */
	public static int reviseEch_Cls_Set(String str, String str2,
			List<Z_Airdata> airdatas) throws Exception {
		NumberValidationUtils.isDecimal(str);
		NumberValidationUtils.isDecimal(str2);
		Integer valueOf = Integer.valueOf(str);
		Integer valueOf2 = Integer.valueOf(str2);

		if (valueOf > 0 && valueOf >= valueOf2) {
			return valueOf;
		} else {
			int size = airdatas.size();
			double Lod = 0.00;
			int j = 1;
			// 取到近七天的数据正常数据, 思想是每行数据装在集合中, 那么我取出集合中的倒数7位数据就能解决了
			for (int i = size - 8; i < airdatas.size(); i++) {
				Z_Airdata z_Airdata = airdatas.get(i);
				int pgs_Per_Cls = z_Airdata.getPgs_Per_Cls();// 取出每班旅客
				int tal_Nbr_Set = z_Airdata.getTal_Nbr_Set();
				if (pgs_Per_Cls > 0) {
					// 客座率
					double Lod_Fts = pgs_Per_Cls / tal_Nbr_Set;
					if (Lod_Fts > 0) {
						Lod += Lod_Fts;
					}
					j++;
				}
			}
			// 四舍五入
			BigDecimal setScale = new BigDecimal((valueOf2 / (Lod / j)))
					.setScale(0, BigDecimal.ROUND_HALF_UP);
			return setScale.intValue();
		}
	}


	/**
	 * 通过比例获得人数
	 * 
	 * @param string
	 *            当前比例
	 * @param string2
	 *            每班旅客
	 * @return
	 * @throws Exception
	 */
	public static int ProportionalOfNumber(String string, String string2) {
		Double decimal = NumberValidationUtils.isDecimal(string);
		Double decimal2 = NumberValidationUtils.isDecimal(string2);
		if (decimal != null && decimal2 != null) {
			double d = decimal * decimal2;
			BigDecimal setScale = new BigDecimal(d).setScale(0,
					BigDecimal.ROUND_HALF_UP);
			return setScale.intValue();
		}
		return 0;
	}

	/**
	 * 检查团队折扣 100≥团队折扣≥0 大体情况下团队折扣会比散客折扣低2折左右
	 * "1、如前一个月或后一个月有显示团队折扣，按前一个月或后一个月的均值执行
	 * 2、如前一个月或后一个月无参考数据，根据散舱销售最低舱位来推算：明折明扣6折以下
	 * ，团队折扣按0.3；若明折明扣均折≥6折，特殊仓位按0.4,；若明折明扣均折≥8折，特殊仓位按0.5；若明折明扣均折是≥9折，特殊仓位按0.6"
	 * 需修正
	 * 
	 * @param string
	 * @return
	 */
	public static boolean reviseGrp_Dct(String string) {
		Double valueOf = Double.valueOf(string);
		if (100 >= valueOf && valueOf >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将数据表格的日期字符串给转换成日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDate(String date) {
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		try {
//			date = "20" + date;
			if (date.contains("/")) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				// 设置lenient为false.
				// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
				format.setLenient(false);
				return format.parse(date);
			} else if (date.contains("-")) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				// 设置lenient为false.
				// 否则SimpleDateFormat会比较宽松地验证日期，比如2007-02-29会被接受，并转换成2007/03/01
				format.setLenient(false);
				return format.parse(date);
			}
		} catch (ParseException e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return null;
		}
		return null;
	}

	/**
	 * 按照传入时间和传入值, 返回之前的几天, 还是之后的几天
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Calendar getStatetime(Date date, int i) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, i);
		return c;
	}

	/**
	 * 求座客率均值平均值
	 * 
	 * @param doubles
	 * @return
	 */
	public static double avg(List<Z_Airdata> doubles) {
		double avg = 0.0;
		int j = 0;
		for (int i = 0; i < doubles.size(); i++) {
			if(doubles.get(i).getTal_Nbr_Set()>0){
				avg+=(double)doubles.get(i).getPgs_Per_Cls()/(double)doubles.get(i).getTal_Nbr_Set();
				j++;
			}
		}
		return avg/j;
	}

	/**
	 * 求平均值
	 * 
	 * @param doubles
	 * @return
	 */
	public static BigDecimal avgD(List<BigDecimal> doubles) {
		BigDecimal lod = new BigDecimal(0);
		int j = 0;
		// 取到近七天的数据正常数据, 思想是每行数据装在集合中, 那么我取出集合中的倒数7位数据就能解决了
		for (int i = 0; i < doubles.size(); i++) {
			lod = lod.add(doubles.get(i));
			j++;
		}
		return lod.divide(new BigDecimal(j),5,BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 传入指定时间, 获得日历对象
	 * 
	 * @param date
	 */
	public static Calendar calendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}
	/**
	 * 获得每班座位
	 * @param z_Airdata
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static int getPassengersPerClass(Z_Airdata z_Airdata,
			List<Z_Airdata> data) throws Exception {
		int pgs_Per_Cls = z_Airdata.getPgs_Per_Cls();// 每班旅客
		// 1,当前异常时间
		Date lcl_Dpt_Day = z_Airdata.getLcl_Dpt_Day();
		// 2,异常数据的前3天时间,
		Calendar afterTime= ValidationDataUtils.getStatetime(lcl_Dpt_Day, -4);;
		// 3,异常数据的后四天数据
		Calendar beforeTime= ValidationDataUtils.getStatetime(lcl_Dpt_Day, 4);;
		// 4,传入前后两个时间, 遍历数据, 取回两个时间之内的数
		List<Z_Airdata> CurrentDate = ValidationDataUtils.getCurrentDateBeforeAndAfter(afterTime,beforeTime,data,z_Airdata);
		// 5,取出集合中当前航班同一航班的数据
		List<Z_Airdata> SameFlight = ValidationDataUtils.getSameFlightData(CurrentDate,z_Airdata);
		// 6,对于数据进行计算
		BigDecimal big = new BigDecimal(pgs_Per_Cls / ValidationDataUtils.avg(SameFlight));
		return big.setScale(0,BigDecimal.ROUND_HALF_UP).intValue();
	}
	/**
	 * 根据异常航班,遍历出和异常航班同一航班的数据
	 * @param currentDate
	 * @return
	 */
	public static List<Z_Airdata> getSameFlightData(List<Z_Airdata> currentDate,Z_Airdata z_Airdata) {
		String flt_Nbr =  z_Airdata.getFlt_Nbr();
		String dpt_AirPt_Cd = z_Airdata.getDpt_AirPt_Cd();
		String arrv_Airpt_Cd = z_Airdata.getArrv_Airpt_Cd();
		List<Z_Airdata> airdatas = new ArrayList<Z_Airdata>();
		for (Z_Airdata z_Air : currentDate) {
			// 判断:如果遍历的数据的时间是在异常数据的时间的之上的一个星期之内则判断其中的航班和异常数据的航班一样则进行累加取均值
			if (flt_Nbr.equals(z_Air.getFlt_Nbr())&& dpt_AirPt_Cd.equals(z_Air.getDpt_AirPt_Cd())&& arrv_Airpt_Cd.equals(z_Air.getArrv_Airpt_Cd())) {
				airdatas.add(z_Air);
			}
		}
		return airdatas;
	}

	/**
	 * 遍历查看所有的数据, 如果当前数据的在传入的两个时间范围之内,将数据放入临时集合中
	 * @param statetime
	 * @param statetime2
	 * @param data
	 * @return
	 */
	public static List<Z_Airdata> getCurrentDateBeforeAndAfter(
			Calendar afterTime, Calendar beforeTime, List<Z_Airdata> data,Z_Airdata airdata) {
		List<Z_Airdata> airdatas = new ArrayList<Z_Airdata>();
		String dpt_AirPt_Cd = airdata.getDpt_AirPt_Cd();
		String arrv_Airpt_Cd = airdata.getArrv_Airpt_Cd();
		for (Z_Airdata z_Airdata : data) {
			Date lcl_Dpt_Day = z_Airdata.getLcl_Dpt_Day();
			Calendar calendar = ValidationDataUtils.calendar(lcl_Dpt_Day);
			if(calendar.after(afterTime)&&calendar.before(beforeTime)&&dpt_AirPt_Cd.equals(z_Airdata.getDpt_AirPt_Cd())&&arrv_Airpt_Cd.equals(z_Airdata.getArrv_Airpt_Cd())){
				airdatas.add(z_Airdata);
			}
		}
		return airdatas;
	}

	/**
	 * 这里对异常的团队折扣
	 * 
	 * @param z_Airdata
	 * @param data
	 * @return
	 * @throws ParseException
	 * "1、如前一个月或后一个月有显示团队折扣，按前一个月或后一个月的均值执行
		2、如前一个月或后一个月无参考数据，根据散舱销售最低舱位来推算：
			明折明扣6折以下，团队折扣按0.3;
			若明折明扣均折≥6折，特殊仓位按0.4;
			若明折明扣均折≥8折，特殊仓位按0.5;
			若明折明扣均折是≥9折，特殊仓位按0.6;

	 */
	public static BigDecimal getGrpDct(Z_Airdata z_Airdata, List<Z_Airdata> data)
			throws ParseException {
		BigDecimal grpDct = new BigDecimal(0);
		Date lcl_Dpt_Day = z_Airdata.getLcl_Dpt_Day();
		BigDecimal beforeGrpDct = beforeMethod(lcl_Dpt_Day,z_Airdata,data);
		if(grpDct.compareTo(beforeGrpDct)==0){
			return afterMethod(lcl_Dpt_Day,z_Airdata,data);
		}
		return beforeGrpDct;
	}
	/**
	 * 前一个月的数据,取出前一个月的数据, 如果数据不全, 则返回0
	 * @param lcl_Dpt_Day
	 * @param z_Airdata
	 * @param data
	 * @return
	 * @throws ParseException
	 */
	private static BigDecimal beforeMethod(Date lcl_Dpt_Day,
			Z_Airdata z_Airdata, List<Z_Airdata> data) throws ParseException {
		// 2,取到当前异常数据的前一个月日历对象
		Calendar afterTime= ValidationDataUtils.getStatetime(lcl_Dpt_Day, -32);
		// 3,取到异常数据的后四天数据
		Calendar beforeTime= ValidationDataUtils.getStatetime(lcl_Dpt_Day,0);
		// 4,取到前一个月的数据
		List<Z_Airdata> currentDate = ValidationDataUtils.getCurrentDateBeforeAndAfter(afterTime,beforeTime,data,z_Airdata);
		//判断前一个月的数据是否符合要求
		if(currentDate!=null &&currentDate.size()>0){
			Calendar calendar = ValidationDataUtils.calendar(currentDate.get(0).getLcl_Dpt_Day());
			Calendar statetime = ValidationDataUtils.getStatetime(lcl_Dpt_Day,-31);
			if(statetime.compareTo(calendar) == 0){
				List<Z_Airdata> sameFlightData = ValidationDataUtils.getGrpSameFlightData(currentDate,z_Airdata);
				//6,取到数据中所有的团队折扣
				List<BigDecimal> doubles = ValidationDataUtils.getDoubleList(sameFlightData);
				if (doubles != null && doubles.size() != 0) {
					//7,取到数据的团队均折
					return ValidationDataUtils.avgD(doubles);
				}
			}
		}
		return new BigDecimal(0);
	}
	/**
	 * 后一个月的数据,取出后一个月的数据, 如果数据不全,则返回0
	 * @param lcl_Dpt_Day
	 * @param z_Airdata
	 * @param data
	 * @return
	 * @throws ParseException
	 */
	private static BigDecimal afterMethod(Date lcl_Dpt_Day, Z_Airdata z_Airdata,
			List<Z_Airdata> data) throws ParseException {
		Calendar afterTime1= ValidationDataUtils.getStatetime(lcl_Dpt_Day, +32);
		Calendar beforeTime1= ValidationDataUtils.getStatetime(lcl_Dpt_Day,0);
		List<Z_Airdata> currentDate1 = ValidationDataUtils.getCurrentDateBeforeAndAfter(beforeTime1,afterTime1,data,z_Airdata);
		if(currentDate1!=null&&currentDate1.size()>0){
			Calendar calendar1 = ValidationDataUtils.calendar(currentDate1.get(currentDate1.size()-1).getLcl_Dpt_Day());
			Calendar statetime1 = ValidationDataUtils.getStatetime(lcl_Dpt_Day,+31);
			if(statetime1.compareTo(calendar1) == 0){
				List<Z_Airdata> sameFlightData1 = ValidationDataUtils.getGrpSameFlightData(currentDate1,z_Airdata);
				List<BigDecimal> doubles = new ArrayList<BigDecimal>();
				doubles = ValidationDataUtils.getDoubleList(sameFlightData1);
				
				if (doubles != null && doubles.size() != 0) {
					return ValidationDataUtils.avgD(doubles);
				}
			}
		}
		return new BigDecimal(0);
	}

	/**
	 * 将集合中的每个符合条件的团队折扣给装进一个集合,给返回去
	 * @param sameFlightData
	 * @return
	 */
	public static List<BigDecimal> getDoubleList(List<Z_Airdata> sameFlightData) {
		List<BigDecimal> doubles = new ArrayList<BigDecimal>();
		for (Z_Airdata z_Airdata : sameFlightData) {
			doubles.add(z_Airdata.getGrp_Dct());
		}
		return doubles;
	}

	/**
	 * 取到所有符合数据的团队数量
	 * @param data
	 * @param z_Airdata
	 * @return
	 */
	private static List<Z_Airdata> getGrpSameFlightData(List<Z_Airdata> data,
			Z_Airdata z_Airdata) {
		 List<Z_Airdata> airdatas = new ArrayList<Z_Airdata>();
		 BigDecimal bigDecimal = new BigDecimal(0);
		 BigDecimal bigDecimal2 = new BigDecimal(100);
		for (Z_Airdata z_Airdata2 : data) {
			if (z_Airdata2.getFlt_Nbr().equals(z_Airdata.getFlt_Nbr())
					&&z_Airdata2.getDpt_AirPt_Cd().equals(z_Airdata.getDpt_AirPt_Cd())
					&&z_Airdata2.getArrv_Airpt_Cd().equals(z_Airdata.getArrv_Airpt_Cd())
					&& z_Airdata2.getGrp_Dct().compareTo(bigDecimal) != 0
					&& z_Airdata2.getGrp_Dct().compareTo(bigDecimal2) ==-1
					&&z_Airdata2.getGrp_Dct().compareTo(bigDecimal) == 1 ) {
				airdatas.add(z_Airdata2);
			}
		}
		return airdatas;
	}

	/**
	 * 计算团队折扣 ,思路,拿到明折明扣的所有折扣数, 按照折扣大小分别放入不同的集合, 从最低往最高进行进行查看,
	 * 找到有数的折扣,然后规则返回不同的折扣数
	 * 
	 * @param z_Airdata
	 * @return
	 */
	public static BigDecimal getGrpDct2(Z_Airdata z_Airdata) {
		// 明折明扣6折以下，团队折扣按0.3；
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(z_Airdata.getFur_Dnt_Ppt());
		list1.add(z_Airdata.getFur_Fve_Dnt_Ppt());
		list1.add(z_Airdata.getFve_Dnt_Ppt());
		list1.add(z_Airdata.getFve_Fve_Dnt_Ppt());
		for (Integer integer : list1) {
			if (integer.intValue() != 0 && integer.intValue() >= 0) {
				return new BigDecimal(0.3);
			}
		}
		// 若明折明扣均折≥6折，特殊仓位按0.4,；
		List<Integer> list2 = new ArrayList<Integer>();
		list2.add(z_Airdata.getSix_Dnt_Ppt());
		list2.add(z_Airdata.getSix_Five_Dnt_Ppt());
		list2.add(z_Airdata.getSen_Dnt_Ppt());
		list2.add(z_Airdata.getSen_Five_Dnt_Ppt());
		for (Integer integer : list2) {
			if (integer.intValue() != 0 && integer.intValue() >= 0) {
				return new BigDecimal(0.4);
			}
		}
		// 若明折明扣均折≥8折，特殊仓位按0.5；
		List<Integer> list3 = new ArrayList<Integer>();
		list3.add(z_Airdata.getEht_Dnt_Ppt());
		list3.add(z_Airdata.getEht_Five_Dnt_Ppt());
		for (Integer integer : list3) {
			if (integer.intValue() != 0 && integer.intValue() >= 0) {
				return new BigDecimal(0.5);
			}
		}
		// 若明折明扣均折是≥9折，特殊仓位按0.6
		List<Integer> list4 = new ArrayList<Integer>();
		list4.add(z_Airdata.getNne_Dnt_Ppt());
		for (Integer integer : list4) {
			if (integer.intValue() != 0 && integer.intValue() >= 0) {
				return new BigDecimal(0.6);
			}
		}
		return new BigDecimal(0);
	}

	/**
	 * （每班收入-团队人数*团队折扣*YB运价）/（每班人数-团队人数）/YB运价
	 * 
	 * @param z_Airdata
	 * @return
	 */
	public static BigDecimal getiddDct(Z_Airdata z_Airdata, double yBFare) {
		//每班收入
//		double Ech_Cls_Ime = ValidationDataUtils.getEch_Cls_Ime(z_Airdata, yBFare);
		double Ech_Cls_Ime = z_Airdata.getTotalNumber();//每班收入
		if(z_Airdata.getPgs_Per_Cls()-z_Airdata.getGrp_Nbr()==0){
			return new BigDecimal(0);
		}
		BigDecimal b = new BigDecimal(Ech_Cls_Ime);//每班收入
		BigDecimal b1 = new BigDecimal(z_Airdata.getGrp_Nbr());//团队人数
		BigDecimal b2 = z_Airdata.getGrp_Dct();//团队折扣
		BigDecimal b3 = new BigDecimal(100);
		BigDecimal b4 = new BigDecimal(yBFare);//yb价格
		BigDecimal b5 = new BigDecimal(z_Airdata.getPgs_Per_Cls());//每班人数
		BigDecimal b6 = new BigDecimal(z_Airdata.getGrp_Nbr());//团队人数
		BigDecimal b7 = new BigDecimal(yBFare);//yb价格
		BigDecimal divide = b2.divide(b3,5,BigDecimal.ROUND_HALF_UP);
		BigDecimal multiply = b1.multiply(divide);
		BigDecimal multiply2 = multiply.multiply(b4);
		BigDecimal subtract = b.subtract(multiply2);//第一个值
		BigDecimal subtract2 = b5.subtract(b6);//第二个值
		BigDecimal divide2 = subtract.divide(subtract2,5,BigDecimal.ROUND_HALF_UP);
		BigDecimal divide3 = divide2.divide(b7,5,BigDecimal.ROUND_HALF_UP);
		return divide3.multiply(b3);
	}

	/**
	 * 取到每班收入
	 * 
	 * @param z_Airdata
	 * @return
	 */
	public static int getEch_Cls_Ime(Z_Airdata z_Airdata, double yBFare) {
		// 1,获取明折明扣均折
		double sal = ValidationDataUtils.getDnt_Age_Pce(z_Airdata);
		// 2,获取每班收入
		int intValue = new BigDecimal((yBFare * (z_Airdata.getTwo_Tak_Ppt() * 2
				+ z_Airdata.getFul_Pce_Ppt() * 1
				+ z_Airdata.getNne_Dnt_Ppt() * 0.9
				+ z_Airdata.getEht_Five_Dnt_Ppt() * 0.85
				+ z_Airdata.getEht_Dnt_Ppt() * 0.8
				+ z_Airdata.getSen_Five_Dnt_Ppt() * 0.75
				+ z_Airdata.getSen_Dnt_Ppt() * 0.7
				+ z_Airdata.getSix_Five_Dnt_Ppt() * 0.65
				+ z_Airdata.getSix_Dnt_Ppt() * 0.6
				+ z_Airdata.getFve_Fve_Dnt_Ppt() * 0.55
				+ z_Airdata.getFve_Dnt_Ppt() * 0.5
				+ z_Airdata.getFur_Fve_Dnt_Ppt() * 0.45
				+ z_Airdata.getFur_Dnt_Ppt() * 0.4 + z_Airdata
				.getSal_Tak_Ppt() * sal)) - ((z_Airdata.getChildren() * yBFare) / 2))
		.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
		
		return intValue;
	}
	/**
	 * 取得特殊舱位折扣
	 * @param z_Airdata
	 * @return
	 */
	public static double getDnt_Age_Pce(Z_Airdata z_Airdata) {
		double Dnt_Age_Pce=0;
		int Pon_Nbr=0;
		if(z_Airdata.getTwo_Tak_Ppt()>0){
			Pon_Nbr+=z_Airdata.getTwo_Tak_Ppt();
			Dnt_Age_Pce+=z_Airdata.getTwo_Tak_Ppt()*2.5;
		}
		if(z_Airdata.getFul_Pce_Ppt()>0){
			Pon_Nbr+=z_Airdata.getFul_Pce_Ppt();
			Dnt_Age_Pce+=z_Airdata.getFul_Pce_Ppt()*1;
		}
		if(z_Airdata.getNne_Dnt_Ppt()>0){
			Pon_Nbr+=z_Airdata.getNne_Dnt_Ppt();
			Dnt_Age_Pce+=z_Airdata.getNne_Dnt_Ppt()*0.9;
		}
		if(z_Airdata.getEht_Five_Dnt_Ppt()>0){
			Pon_Nbr+=z_Airdata.getEht_Five_Dnt_Ppt();
			Dnt_Age_Pce+=z_Airdata.getEht_Five_Dnt_Ppt()*0.85;
		}
		if(z_Airdata.getEht_Dnt_Ppt()>0){
			Pon_Nbr+=z_Airdata.getEht_Dnt_Ppt();
			Dnt_Age_Pce+=z_Airdata.getEht_Dnt_Ppt()*8;
		}
		if(z_Airdata.getSen_Five_Dnt_Ppt()>0){
			Pon_Nbr+=z_Airdata.getSen_Five_Dnt_Ppt();
			Dnt_Age_Pce+=z_Airdata.getSen_Five_Dnt_Ppt()*0.75;
		}
		if(z_Airdata.getSen_Dnt_Ppt()>0){
			Pon_Nbr+=z_Airdata.getSen_Dnt_Ppt();
			Dnt_Age_Pce+=z_Airdata.getSen_Dnt_Ppt()*7;
		}
		if(z_Airdata.getSix_Five_Dnt_Ppt()>0){
			Pon_Nbr+=z_Airdata.getSix_Five_Dnt_Ppt();
			Dnt_Age_Pce+=z_Airdata.getSix_Five_Dnt_Ppt()*0.65;
		}
		if(z_Airdata.getSix_Dnt_Ppt()>0){
			Pon_Nbr+=z_Airdata.getSix_Dnt_Ppt();
			Dnt_Age_Pce+=z_Airdata.getSix_Dnt_Ppt()*0.6;
		}
		if(z_Airdata.getFve_Fve_Dnt_Ppt()>0){
			Pon_Nbr+=z_Airdata.getFve_Fve_Dnt_Ppt();
			Dnt_Age_Pce+=z_Airdata.getFve_Fve_Dnt_Ppt()*0.55;
		}
		if(z_Airdata.getFve_Dnt_Ppt()>0){
			Pon_Nbr+=z_Airdata.getFve_Dnt_Ppt();
			Dnt_Age_Pce+=z_Airdata.getFve_Dnt_Ppt()*0.5;
		}
		if(z_Airdata.getFur_Fve_Dnt_Ppt()>0){
			Pon_Nbr+=z_Airdata.getFur_Fve_Dnt_Ppt();
			Dnt_Age_Pce+=z_Airdata.getFur_Fve_Dnt_Ppt()*0.45;
		}
		if(z_Airdata.getFur_Dnt_Ppt()>0){
			Pon_Nbr+=z_Airdata.getFur_Dnt_Ppt();
			Dnt_Age_Pce+=z_Airdata.getFur_Dnt_Ppt()*0.4;
		}
		if (Dnt_Age_Pce/Pon_Nbr<0.6){
			return 0.3;
		}else if(Dnt_Age_Pce/Pon_Nbr<0.8){
			return 0.4;
		}else if(Dnt_Age_Pce/Pon_Nbr<0.9){
			return 0.5;
		}else{
			return 0.6;
		}
	}
	/***
	 * 验证是否符合航程规则
	 * @param string 航线
	 * @param string2 航段
	 * @return
	 */
	public static boolean isContains(String airline, String airport) {
		//1,判断第一个的长度
		if(airline.length()==6){
			//如果
			String substring = airline.substring(0, 3);
			String substring2 = airport.substring(0, 3);
			if(substring.equals(substring2)&&airline.indexOf(substring)==0&&airline.indexOf(substring2)==0){
				return true;
			}
		}else if(airline.length()==9){
			//2,
			String substring = airline.substring(0, 3);
			String substring2 = airline.substring(3, 6);
			String substring3 = airport.substring(0, 3);
			if(substring.equals(substring3)||substring2.equals(substring3)){
				if(airline.indexOf(substring)!=-1&&airline.indexOf(substring2)!=-1){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 平均折扣
	 * @param totalNumber
	 * @param pgs_Per_Cls
	 * @param yBFare
	 * @return
	 */
	public static BigDecimal avg_Dct(int totalNumber, int pgs_Per_Cls,
			double yBFare) {
		 BigDecimal b1 = new BigDecimal(totalNumber);
         BigDecimal b2 = new BigDecimal(pgs_Per_Cls);
         BigDecimal b3 = new BigDecimal(yBFare);
         BigDecimal b4 = new BigDecimal(100);
         BigDecimal divide = b1.divide(b2,5,BigDecimal.ROUND_HALF_UP);
		 BigDecimal divide2 = divide.divide(b3,5,BigDecimal.ROUND_HALF_UP);
		 BigDecimal abs = divide2.multiply(b4);
		 return abs;
	}
	/**
	 * 团队收入
	 * 计算公式  z_Airdata.getEch_Cls_Grp()*z_Airdata.getGrp_Dct()/100*yBFare)
	 * @param ech_Cls_Grp
	 * @param bigDecimal
	 * @param i
	 * @param yBFare
	 * @return
	 */
	public static BigDecimal getGroupDct(int ech_Cls_Grp, BigDecimal bigDecimal, int i,
			double yBFare) {
		BigDecimal b1 = new BigDecimal(ech_Cls_Grp);
        BigDecimal b2 = bigDecimal;
        BigDecimal b3 = new BigDecimal(i);
        BigDecimal b4 = new BigDecimal(yBFare);
        BigDecimal multiply = b1.multiply(b2);
        BigDecimal divide = multiply.divide(b3,5,BigDecimal.ROUND_HALF_UP);
        BigDecimal multiply2 = divide.multiply(b4);
		return multiply2;
	}
	/**
	 * 如果等于
	 * @param str
	 * @return
	 */
	public static int isInt(String str) {
		if(str!=null&&str!=""){
			String reg = "[0-9]*";
			Matcher matcher = Pattern.compile(reg).matcher(str);
			if(matcher.find()){
				String group = matcher.group();
				return Integer.valueOf(group);
			}
		}
		return 0;
	}
	/**
	 * 算法步骤：
	 *	1.判断儿童人数是否大于Y舱人数且小于等于Y舱加两舱人数之和。
	 *			如果是则进入第2步，否则进入第3步。
	 *	2.【两舱折扣取300%，特殊舱取80%，Y舱取100%，9折舱取100%，8.5折舱取90%……以此类推取本折扣区间的最大可能取值。儿童票在经济舱为经济舱全价一半，在两舱为两舱价格的一半】
	 *		计算如下：收入高值=（求和（对应舱位人数*各舱折扣上限）-Y舱人数*0.5-（儿童人数-Y舱人数）*1.5）*YB运价
	 *	3.计算如下：收入高值=（求和（对应舱位人数*各舱折扣上限）-儿童人数*0.5）*YB运价
	 * @param z_Airdata
	 * @param Yb 
	 * @return
	 */
	public static int getHighValue(Z_Airdata z_Airdata, int Yb) {
		return (int) (count_ppt_high(z_Airdata)*Yb);
	}
	/**
	 * 2.【两舱折扣取300%，特殊舱取80%，Y舱取100%，9折舱取100%，8.5折舱取90%……以此类推取本折扣区间的最大可能取值。儿童票在经济舱为经济舱全价一半，在两舱为两舱价格的一半】
	 * 求和（对应舱位人数*各舱折扣上限） 求总共折扣之和
	 * @param z_Airdata 
	 * @return
	 */
	private static double count_ppt_high(Z_Airdata z_Airdata) {
		double two_Tak_Ppt = z_Airdata.getTwo_Tak_Ppt()*3;// 两舱比例
		double ful_Pce_Ppt = z_Airdata.getFul_Pce_Ppt()*1;//Y舱
		double nne_Dnt_Ppt= z_Airdata.getNne_Dnt_Ppt()*1; // 9折比例
		double eht_Five_Dnt_Ppt= z_Airdata.getEht_Five_Dnt_Ppt()*0.9; // 8.5折比例
		double eht_Dnt_Ppt= z_Airdata.getEht_Dnt_Ppt()*0.85; // 8折比例
		double sen_Five_Dnt_Ppt= z_Airdata.getSen_Five_Dnt_Ppt()*0.8; // 7.5折比例
		double sen_Dnt_Ppt= z_Airdata.getSen_Dnt_Ppt()*0.75; // 7折比例
		double six_Five_Dnt_Ppt= z_Airdata.getSix_Five_Dnt_Ppt()*0.7; // 6.5折比例
		double six_Dnt_Ppt= z_Airdata.getSix_Dnt_Ppt()*0.65; // 6折比例
		double fve_Fve_Dnt_Ppt= z_Airdata.getFve_Fve_Dnt_Ppt()*0.6; // 5.5折比例
		double fve_Dnt_Ppt= z_Airdata.getFve_Dnt_Ppt()*0.55; // 5折比例
		double fur_Fve_Dnt_Ppt= z_Airdata.getFur_Fve_Dnt_Ppt()*0.5; // 4.5折比例
		double fur_Dnt_Ppt= z_Airdata.getFur_Dnt_Ppt()*0.45; // 4折比例
		double thr_Fve_Dnt_Ppt= z_Airdata.getThr_Fve_Dnt_Ppt()*0.4;	//35%
		double thr_Dnt_Ppt= z_Airdata.getThr_Dnt_Ppt()*0.35;	//30%
		double two_Fve_Dnt_Ppt= z_Airdata.getTwo_Fve_Dnt_Ppt()*0.3;	//25%
		double two_Dnt_Ppt= z_Airdata.getTwo_Dnt_Ppt()*0.25;	//20%
		double sal_Tak_Ppt= z_Airdata.getSal_Tak_Ppt()*0.8; // 特殊舱比例
		
		return two_Tak_Ppt+ful_Pce_Ppt+nne_Dnt_Ppt+eht_Five_Dnt_Ppt+eht_Dnt_Ppt+sen_Five_Dnt_Ppt+sen_Dnt_Ppt
				+six_Five_Dnt_Ppt+six_Dnt_Ppt+fve_Fve_Dnt_Ppt+fve_Dnt_Ppt+fur_Fve_Dnt_Ppt+fur_Dnt_Ppt
				+thr_Fve_Dnt_Ppt+thr_Dnt_Ppt+two_Fve_Dnt_Ppt+two_Dnt_Ppt+sal_Tak_Ppt;
	}
	/**
	 * 算法步骤：
	 * 1.判断儿童人数是否大于Y舱人数且小于等于Y舱加两舱人数之和。如果是则进入第2步，否则进入第3步。
	 * 2.【两舱折扣取80%，特殊舱取0%，Y舱取90%，9折舱取8.5%，8.5折舱取80%……以此类推取本折扣区间的最小可能取值。儿童票在经济舱为经济舱全价一半，在两舱为两舱价格的一半】
	 * 计算如下：收入低值=（求和（对应舱位人数*各舱折扣上限）-Y舱人数*0.5-（儿童人数-Y舱人数）*0.4）*YB运价
	 * 3.计算如下：收入低值=（求和（对应舱位人数*各舱折扣上限）-儿童人数*0.5）*YB运价
	 * @param z_Airdata
	 * @param getyBFare
	 * @return
	 */
	public static int getLowValue(Z_Airdata z_Airdata, int Yb) {
		return (int) (count_ppt_low(z_Airdata)*Yb);
	}
	/**
	 * 【两舱折扣取80%，特殊舱取0%，Y舱取90%，9折舱取8.5%，8.5折舱取80%……以此类推取本折扣区间的最小可能取值。儿童票在经济舱为经济舱全价一半，在两舱为两舱价格的一半】
	 * @param z_Airdata
	 * @return
	 */
	private static double count_ppt_low(Z_Airdata z_Airdata) {
		double two_Tak_Ppt = z_Airdata.getTwo_Tak_Ppt()*0.8;// 两舱比例
		double ful_Pce_Ppt = z_Airdata.getFul_Pce_Ppt()*0.9;//y舱
		double nne_Dnt_Ppt= z_Airdata.getNne_Dnt_Ppt()*0.85; // 9折比例
		double eht_Five_Dnt_Ppt= z_Airdata.getEht_Five_Dnt_Ppt()*0.8; // 8.5折比例
		double eht_Dnt_Ppt= z_Airdata.getEht_Dnt_Ppt()*0.75; // 8折比例
		double sen_Five_Dnt_Ppt= z_Airdata.getSen_Five_Dnt_Ppt()*0.7; // 7.5折比例
		double sen_Dnt_Ppt= z_Airdata.getSen_Dnt_Ppt()*0.65; // 7折比例
		double six_Five_Dnt_Ppt= z_Airdata.getSix_Five_Dnt_Ppt()*0.6; // 6.5折比例
		double six_Dnt_Ppt= z_Airdata.getSix_Dnt_Ppt()*0.55; // 6折比例
		double fve_Fve_Dnt_Ppt= z_Airdata.getFve_Fve_Dnt_Ppt()*0.5; // 5.5折比例
		double fve_Dnt_Ppt= z_Airdata.getFve_Dnt_Ppt()*0.45; // 5折比例
		double fur_Fve_Dnt_Ppt= z_Airdata.getFur_Fve_Dnt_Ppt()*0.4; // 4.5折比例
		double fur_Dnt_Ppt= z_Airdata.getFur_Dnt_Ppt()*0.35; // 4折比例
		double thr_Fve_Dnt_Ppt= z_Airdata.getThr_Fve_Dnt_Ppt()*0.3;	//35%
		double thr_Dnt_Ppt= z_Airdata.getThr_Dnt_Ppt()*0.25;	//30%
		double two_Fve_Dnt_Ppt= z_Airdata.getTwo_Fve_Dnt_Ppt()*0.2;	//25%
		double two_Dnt_Ppt= z_Airdata.getTwo_Dnt_Ppt()*0.15;	//20%
		
		return two_Tak_Ppt+ful_Pce_Ppt+nne_Dnt_Ppt+eht_Five_Dnt_Ppt+eht_Dnt_Ppt+sen_Five_Dnt_Ppt+sen_Dnt_Ppt
				+six_Five_Dnt_Ppt+six_Dnt_Ppt+fve_Fve_Dnt_Ppt+fve_Dnt_Ppt+fur_Fve_Dnt_Ppt+fur_Dnt_Ppt
				+thr_Fve_Dnt_Ppt+thr_Dnt_Ppt+two_Fve_Dnt_Ppt+two_Dnt_Ppt;
	}
	/**
	 * 2.1、团队折扣高值：
	 *	1.从全价开始累加直到人数大于团队人数。
	 *	2.根据已累加数据比例，计算其折扣均值
	 *	3.该均值
	 * @param z_Airdata
	 * @return
	 */
	public static double getHigh_ppt(Z_Airdata z_Airdata) {
		//折扣字典
		Double[] ppt = {1.0,1.0,0.9,0.85,0.8,0.75,0.7,0.65,0.6,0.55,0.5,0.45,0.4,0.35,0.3,0.25,0.2};
		//舱位人数与字典相对应
		List<Integer> lists = getHigh_InstrList(z_Airdata);
		                  
		//团队人数
		int grp_Nbr = z_Airdata.getGrp_Nbr();
		if(grp_Nbr<=0){
			return 0.0;
		}
		
		double high_ppt = 0;//高价总折扣
		for (int i = 0;i < lists.size(); i++) {
			Integer integer = lists.get(i);
			if(integer>grp_Nbr){
				high_ppt += grp_Nbr*ppt[i];
				break;
			}else if(integer == grp_Nbr){
				high_ppt += integer*ppt[i];
				break;
			}else{
				high_ppt += integer*ppt[i];
			}
			grp_Nbr -= integer ;
		}
		/**团队高值总折扣/团队人俗*/
		return (high_ppt/z_Airdata.getGrp_Nbr())*100;
	}
	/**
	 * 将舱位人数装入集合中
	 * @param z_Airdata
	 * @return
	 */
	private static List<Integer> getHigh_InstrList(Z_Airdata z_Airdata) {
		List<Integer> lists = new ArrayList<Integer>();
		lists.add(z_Airdata.getFul_Pce_Ppt());			// y舱                                     
		lists.add(z_Airdata.getNne_Dnt_Ppt());          // 9折比例                                   
		lists.add(z_Airdata.getEht_Five_Dnt_Ppt());     // 8.5折比例                               
		lists.add(z_Airdata.getEht_Dnt_Ppt());         	// 8折比例                                   
		lists.add(z_Airdata.getSen_Five_Dnt_Ppt());     // 7.5折比例                            
		lists.add(z_Airdata.getSen_Dnt_Ppt());          // 7折比例                                
		lists.add(z_Airdata.getSix_Five_Dnt_Ppt());     // 6.5折比例                         
		lists.add(z_Airdata.getSix_Dnt_Ppt());          // 6折比例                              
		lists.add(z_Airdata.getFve_Fve_Dnt_Ppt());      // 5.5折比例                           
		lists.add(z_Airdata.getFve_Dnt_Ppt());          // 5折比例                               
		lists.add(z_Airdata.getFur_Fve_Dnt_Ppt());      // 4.5折比例                            
		lists.add(z_Airdata.getFur_Dnt_Ppt());          // 4折比例                             
		lists.add(z_Airdata.getThr_Fve_Dnt_Ppt());	    // 35%                          
		lists.add(z_Airdata.getThr_Dnt_Ppt());	        // 30%                          
		lists.add(z_Airdata.getTwo_Fve_Dnt_Ppt());	    // 25%                           
		lists.add(z_Airdata.getTwo_Dnt_Ppt());	        // 20%         
		return lists;
	}

	/**
	 * 团队折扣低值
	 * 1.利用收入计算特殊舱位折扣。特殊舱位折扣=（收入-YB*(头等舱人数*2.0+经济舱全价人数*1+9折人数*0.9+85折人数*0.85+…4折人数*0.4））/特殊舱位人数
	 * 2.从全价开始从折扣高的向折扣低的累加直到人数大于散客人数（散客=乘客-团队）。（特殊舱位折扣按1中计算结果处理）
	 * 3.根据已累加数据比例，计算散客折扣均值。
	 * 4.团队折扣低值=2*平均折扣-散客折扣均值。（平均折扣=收入/YB/乘客数）
	 * @param z_Airdata
	 * @return
	 */
	public static double getlow_ppt(Z_Airdata z_Airdata) {
		/**利用收入计算特殊舱位折扣。特殊舱位折扣=（收入-YB*(头等舱人数*2.0+经济舱全价人数*1+9折人数*0.9+85折人数*0.85+…4折人数*0.4））/特殊舱位人数*/
		//将折扣,人数 一一对应的放入两个集合中, (包括特殊舱位, 特殊舱位按照折扣放入对应的舱位中)
		Map<String,Object> maps = getListDouAndInt(z_Airdata);
		@SuppressWarnings("unchecked")
		List<Double> ppt2 = (List<Double>) maps.get("ppt2");//折扣集合
		@SuppressWarnings("unchecked")
		List<Integer> lists2 = (List<Integer>) maps.get("lists2");//人数集合
		double count_pp = getCount_Pp(z_Airdata,ppt2,lists2);//总折扣人数指各舱位折扣值乘以相应人数的乘积之和
		double fng_pp = getFng_Pp(z_Airdata,ppt2,lists2);//散客折扣人数指各散客舱位折扣值乘以相应散客人数的乘积之和。
		int fng_ine = getFng_ine(z_Airdata, ppt2, lists2);//散客收入
		if(fng_ine>z_Airdata.getTotalNumber()){//如果散客收入大于总收入,团队折扣为0 
			return 0;
		}
		/**团队均折低值=（总折扣人数-散客折扣人数）/团队人数*/
		double count = count_pp-fng_pp;
		if(count!=0&&count>0){//理论上,总折扣肯定大于散客折扣,做一个异常数据判断, 
			double low_ppt_count = count/z_Airdata.getGrp_Nbr();
			DecimalFormat de = new DecimalFormat();
			de.setMaximumFractionDigits(2);
			String format = de.format(low_ppt_count*100);
			return Double.valueOf(format);
		}
		return 0;
	}
	/**
	 * 散客收入
	 */
	private static int getFng_ine(Z_Airdata z_Airdata,List<Double> ppt2,List<Integer> lists2){
		//散客人数,用于运算
		int grp_Fng_Per = z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr() ;
		//散客原始人数
		int grp_Fng_Per2 = z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr();
		double Fng_pp = 0.0;
		if(grp_Fng_Per!=0){
			double low_ppt = 0;
			//通过循环,依次从舱位中遍历出人数,直到把散客总人数减完
			for (int i = 0;i < lists2.size(); i++) {
				Integer integer = lists2.get(i);
				if(integer>grp_Fng_Per){
					low_ppt += grp_Fng_Per*ppt2.get(i);
					break;
				}else if(integer == grp_Fng_Per){
					low_ppt += integer*ppt2.get(i);
					break;
				}else{
					low_ppt += lists2.get(i)*ppt2.get(i);
					grp_Fng_Per -= integer;
				}
			}
			Fng_pp = low_ppt/grp_Fng_Per2;
		}
		int fng_ine = (int) (Fng_pp*grp_Fng_Per2*z_Airdata.getyBFare());//散客收入
		return fng_ine;
	}
	/**
	 * 总折扣人数指各舱位折扣值乘以相应人数的乘积之和
	 * @param z_Airdata
	 * @param maps
	 * @return
	 */
	private static double getCount_Pp(Z_Airdata z_Airdata,List<Double> ppt2,List<Integer> lists2) {
		double low_ppt = 0;//散客折扣人数指各散客舱位折扣值乘以相应散客人数的乘积之和
		//通过循环,依次从舱位中遍历出人数,叠加
		for (int i = 0;i < lists2.size(); i++) {
			low_ppt += lists2.get(i)*ppt2.get(i);
		}
		return low_ppt;
	}
	/**
	 * 散客折扣人数指各散客舱位折扣值乘以相应散客人数的乘积之和
	 * @param z_Airdata
	 * @param maps
	 * @return
	 */
	private static double getFng_Pp(Z_Airdata z_Airdata,List<Double> ppt2,List<Integer> lists2) {
		//散客人数,用于运算
		int grp_Fng_Per = z_Airdata.getPgs_Per_Cls() - z_Airdata.getGrp_Nbr() ;
		double low_ppt = 0;//散客折扣人数指各散客舱位折扣值乘以相应散客人数的乘积之和
		if(grp_Fng_Per!=0){
			//通过循环,依次从舱位中遍历出人数,直到把散客总人数减完
			for (int i = 0;i < lists2.size(); i++) {
				Integer integer = lists2.get(i);
				if(integer>grp_Fng_Per){
					low_ppt += grp_Fng_Per*ppt2.get(i);
					break;
				}else if(integer == grp_Fng_Per){
					low_ppt += integer*ppt2.get(i);
					break;
				}else{
					low_ppt += lists2.get(i)*ppt2.get(i);
					grp_Fng_Per -= integer;
				}
			}
		}
		return low_ppt;
	}

	private static Map<String, Object> getListDouAndInt(Z_Airdata z_Airdata) {
		//折扣字典
		Double[] ppt = {2.0,1.0,0.9,0.85,0.8,0.75,0.7,0.65,0.6,0.55,0.5,0.45,0.4,0.35,0.3,0.25,0.2};
		List<Integer> lists = getLow_InstrList(z_Airdata);//进行按序放入List中
		Map<String, Object> map = new HashMap<String, Object>();
		double ts = 0.0;
		int sal_Tak_Ppt = z_Airdata.getSal_Tak_Ppt();//特殊座位人数
		if(sal_Tak_Ppt>0){
			ts = getSal_Tak_Ppt(z_Airdata);
		}
		//两个集合, 分别装折扣字典和对应人数(因为特殊折扣不确定, 所有要重新排序)
		List<Double> ppt2 = new ArrayList<Double>();
		List<Integer> lists2 = new ArrayList<Integer>();
		//遍历排序, 把特殊舱位放入合适的位置
		boolean isMax = true;
		for (int i = 0; i < ppt.length; i++) {
			if(ts>=ppt[i]){
				if(isMax){
					isMax=false;
					ppt2.add(ts);
					i--;
					lists2.add(sal_Tak_Ppt);
				}else{
					ppt2.add(ppt[i]);
					lists2.add(lists.get(i));
				}
			}else{
				ppt2.add(ppt[i]);
				lists2.add(lists.get(i));
			}
		}
		if(isMax){
			ppt2.add(ts);
			lists2.add(sal_Tak_Ppt);
		}
		map.put("ppt2", ppt2);
		map.put("lists2", lists2);
		return map;
	}

	private static List<Integer> getLow_InstrList(Z_Airdata z_Airdata) {
		List<Integer> lists = new ArrayList<Integer>();
		lists.add(z_Airdata.getTwo_Tak_Ppt());			// 两舱比例                                        
		lists.add(z_Airdata.getFul_Pce_Ppt());			//y舱                                     
		lists.add(z_Airdata.getNne_Dnt_Ppt());          // 9折比例                                   
		lists.add(z_Airdata.getEht_Five_Dnt_Ppt());     // 8.5折比例                               
		lists.add(z_Airdata.getEht_Dnt_Ppt());         	// 8折比例                                   
		lists.add(z_Airdata.getSen_Five_Dnt_Ppt());     // 7.5折比例                            
		lists.add(z_Airdata.getSen_Dnt_Ppt());          // 7折比例                                
		lists.add(z_Airdata.getSix_Five_Dnt_Ppt());     // 6.5折比例                         
		lists.add(z_Airdata.getSix_Dnt_Ppt());          // 6折比例                              
		lists.add(z_Airdata.getFve_Fve_Dnt_Ppt());      // 5.5折比例                           
		lists.add(z_Airdata.getFve_Dnt_Ppt());          // 5折比例                               
		lists.add(z_Airdata.getFur_Fve_Dnt_Ppt());      // 4.5折比例                            
		lists.add(z_Airdata.getFur_Dnt_Ppt());          // 4折比例                             
		lists.add(z_Airdata.getThr_Fve_Dnt_Ppt());	    //35%                          
		lists.add(z_Airdata.getThr_Dnt_Ppt());	        //30%                          
		lists.add(z_Airdata.getTwo_Fve_Dnt_Ppt());	    //25%                           
		lists.add(z_Airdata.getTwo_Dnt_Ppt());	        //20%                           
		return lists;
	}

	/**
	 * 1.利用收入计算特殊舱位折扣。特殊舱位折扣=（收入-YB*(头等舱人数*2.0+经济舱全价人数*1+9折人数*0.9+85折人数*0.85+…4折人数*0.4））/特殊舱位人数/YB
	 * @param z_Airdata
	 * @return
	 */
	private static double getSal_Tak_Ppt(Z_Airdata z_Airdata) {
		double two_Tak_Ppt = z_Airdata.getTwo_Tak_Ppt()*2.0;			// 两舱比例                                        
		double ful_Pce_Ppt = z_Airdata.getFul_Pce_Ppt();			//y舱                                     
		double nne_Dnt_Ppt = z_Airdata.getNne_Dnt_Ppt()*0.9;          // 9折比例                                   
		double eht_Five_Dnt_Ppt = z_Airdata.getEht_Five_Dnt_Ppt()*0.85;     // 8.5折比例                               
		double eht_Dnt_Ppt = z_Airdata.getEht_Dnt_Ppt()*0.8;         	// 8折比例                                   
		double sen_Five_Dnt_Ppt = z_Airdata.getSen_Five_Dnt_Ppt()*0.75;     // 7.5折比例                            
		double sen_Dnt_Ppt = z_Airdata.getSen_Dnt_Ppt()*0.7;          // 7折比例                                
		double six_Five_Dnt_Ppt = z_Airdata.getSix_Five_Dnt_Ppt()*0.65;     // 6.5折比例                         
		double six_Dnt_Ppt = z_Airdata.getSix_Dnt_Ppt()*0.6;          // 6折比例                              
		double fve_Fve_Dnt_Ppt = z_Airdata.getFve_Fve_Dnt_Ppt()*0.55;     // 5.5折比例                           
		double fve_Dnt_Ppt = z_Airdata.getFve_Dnt_Ppt()*0.5;          // 5折比例                               
		double fur_Fve_Dnt_Ppt = z_Airdata.getFur_Fve_Dnt_Ppt()*0.45;      // 4.5折比例                            
		double fur_Dnt_Ppt = z_Airdata.getFur_Dnt_Ppt()*0.4;          // 4折比例                             
		double thr_Fve_Dnt_Ppt = z_Airdata.getThr_Fve_Dnt_Ppt()*0.35;	    //35%                          
		double thr_Dnt_Ppt = z_Airdata.getThr_Dnt_Ppt()*0.3;	        //30%                          
		double two_Fve_Dnt_Ppt = z_Airdata.getTwo_Fve_Dnt_Ppt()*0.25;	    //25%                           
		double two_Dnt_Ppt = z_Airdata.getTwo_Dnt_Ppt()*0.20;	        //20%                           
		int sal_Tak_Ppt = z_Airdata.getSal_Tak_Ppt();			//特殊舱位折扣
		double count_ppt = two_Tak_Ppt+ful_Pce_Ppt+nne_Dnt_Ppt+eht_Five_Dnt_Ppt+eht_Dnt_Ppt
		+sen_Five_Dnt_Ppt+sen_Dnt_Ppt+six_Five_Dnt_Ppt+six_Dnt_Ppt+fve_Fve_Dnt_Ppt+fve_Dnt_Ppt+fur_Fve_Dnt_Ppt
		+fur_Dnt_Ppt+thr_Fve_Dnt_Ppt+thr_Dnt_Ppt+two_Fve_Dnt_Ppt+two_Dnt_Ppt;
		double ts = (z_Airdata.getTotalNumber()-z_Airdata.getyBFare()*count_ppt)/sal_Tak_Ppt/z_Airdata.getyBFare();
		return ts;
	}

	/**
	 * 验证团队折扣合法性-
	 * @param z_Airdata
	 * @param data
	 * @return
	 * @throws ParseException
	 */
	public static BigDecimal paseGrp_Dct(Z_Airdata z_Airdata,List<Z_Airdata> data) throws ParseException {
		// 高位折扣
		double high_ppt = ValidationDataUtils.getHigh_ppt(z_Airdata);
		// 地位折扣
		double low_ppt = ValidationDataUtils.getlow_ppt(z_Airdata);
		//32.团队折扣值不在上述高低取值范围之间。
		BigDecimal grp_Dct = z_Airdata.getGrp_Dct();
		BigDecimal bigDecimal = new BigDecimal(high_ppt);
		BigDecimal bigDecimal1 = new BigDecimal(low_ppt);
		if(grp_Dct.compareTo(bigDecimal1)==-1|| grp_Dct.compareTo(bigDecimal)==1){
			//如果不在取值范围, 则高值+地址/2,取平均值
			BigDecimal bigDecimal2 = new BigDecimal(high_ppt+low_ppt);
			BigDecimal bigDecimal3 = new BigDecimal(2);
			return bigDecimal2.divide(bigDecimal3,5,BigDecimal.ROUND_HALF_UP);
		}
		return grp_Dct;
	}
	/**
	 * -验证收入合法性
	 * @param z_Airdata
	 * @param getfare
	 * @return
	 */
	public static int getTotalNumber(Z_Airdata z_Airdata, Fare getfare) {
		// 收入高值
		int high_value = ValidationDataUtils.getHighValue(z_Airdata,getfare.getyBFare());
		// 收入低值
		int low_value = ValidationDataUtils.getLowValue(z_Airdata,getfare.getyBFare());
		// 2.收入值不在上述高低取值范围之间 则修正
		if(z_Airdata.getTotalNumber()>=high_value || z_Airdata.getTotalNumber()<=low_value){
			//四舍五入的拿到每班收入
			return ValidationDataUtils.getEch_Cls_Ime(z_Airdata, getfare.getyBFare());
			
		}
		return z_Airdata.getTotalNumber();
	}
	/**
	 * 添加座公里收入如果 座公里收入>= 5 || 0<=座公里收入，要求座收不在（0,2）间时做修正
	 * @param z_Airdata
	 * @param getfare
	 * @return
	 */
	public static BigDecimal getSet_Ktr_Ine(Z_Airdata z_Airdata, Fare getfare) {
		if(z_Airdata.getSet_Ktr_Ine().intValue()>=5||z_Airdata.getSet_Ktr_Ine().intValue()<=0){
			BigDecimal Set_Ktr_Ine = new BigDecimal(z_Airdata.getTotalNumber()).divide(new BigDecimal(z_Airdata.getPgs_Per_Cls()),5,BigDecimal.ROUND_HALF_UP);
			BigDecimal divide = Set_Ktr_Ine.divide(new BigDecimal(getfare.getSailingDistance()),5,BigDecimal.ROUND_HALF_UP).abs();
			return divide;
		}
		return z_Airdata.getSet_Ktr_Ine();
	}
	
}
