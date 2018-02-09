package org.ldd.ssm.crm.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.ldd.ssm.crm.domain.AcquisitionTime;
import org.ldd.ssm.crm.domain.DOW;
import org.ldd.ssm.crm.domain.MonthData;
import org.ldd.ssm.crm.domain.SunAndCount;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.mapper.DOWMapper;
import org.ldd.ssm.crm.query.DOWObject;
import org.ldd.ssm.crm.query.DOWQuery;
import org.ldd.ssm.crm.query.MonthObject;
import org.ldd.ssm.crm.service.IDOWServicce;
import org.ldd.ssm.crm.utils.DOWDataUtils;
import org.ldd.ssm.crm.utils.DataUtils;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *DOW层的实现类
 */
@Service
public class DOWServicceImpl implements IDOWServicce{
	@Autowired
	private DOWMapper dowMapper;
	/**
	 * 拿到所有的数据源公司
	 */
	public List<Z_Airdata> getCompany() {
		return dowMapper.getCompany();
	}
	/**
	 * 拿到所有的年份
	 */
	public List<AcquisitionTime> getDOW_Date() {
		List<AcquisitionTime> dow_Date = dowMapper.getDOW_Date();
		List<AcquisitionTime> new_Date = new ArrayList<AcquisitionTime>();
		for (int i = 1; i <= dow_Date.size(); i++) {
			AcquisitionTime acquisitionTime = dow_Date.get(dow_Date.size()-i);
			acquisitionTime.setLcl_Dpt_Day("20"+acquisitionTime.getLcl_Dpt_Day()+"年");
			new_Date.add(acquisitionTime);
		}
		return new_Date;
	}
	/**
	 * 拿到年份所有的月份
	 */
	public DOWObject<DOW> getMethod(DOWQuery dta_Sce) {
		UserContext.setData("lcl",dta_Sce);
		UserContext.setData("year",dta_Sce.getDpt_AirPt_Cd()+"-"+dta_Sce.getArrv_Airpt_Cd());
		String airPort2 = dowMapper.getAirPort2(dta_Sce.getDpt_AirPt_Cd()+"-"+dta_Sce.getArrv_Airpt_Cd());
		if(airPort2==null){
			List<DOW> dows = new ArrayList<DOW>();
			int size = dows.size();
			List<DOW> total = DOWDataUtils.getTotal(dows);
			DOWObject<DOW> dowObject = new DOWObject<DOW>(dows,size,total);
			return dowObject;
		}
		dta_Sce.setDpt_AirPt_Cd(airPort2.substring(0, 3));
		dta_Sce.setArrv_Airpt_Cd(airPort2.substring(3, 6));
		//根据传入条件拿到当年的所有月份
		List<DOW> method = dowMapper.getMethod(dta_Sce);
		//创建一个存放数据的对象
		List<DOW> dows = new ArrayList<DOW>();
		for (DOW dow : method) {
			dta_Sce.setMonth(dow.getMethod());
			//在这里拿到了每个月的数据, 
			List<Z_Airdata> weekDate = dowMapper.getWeekDate(dta_Sce);
			//拿到人数和总数
			SunAndCount sunAndCount = dowMapper.getSunAndCount(dta_Sce);
			//将当前查询好的数据给添加进DOW类中
			dows.add(DOWDataUtils.getWeek(dow.getMethod(),weekDate,sunAndCount,""));
		}
		//条数总数
		int size = dows.size();
		//这里是将数据不全到12个月的数据
		List<DOW> total = DOWDataUtils.getTotal(dows);
		DOWObject<DOW> dowObject = new DOWObject<DOW>(dows,size,total);
		return dowObject;
	}
	/**
	 * 拿到每个月的数据
	 */
	public MonthObject<MonthData> getMonthData(DOWQuery dta_Sce) {
		List<MonthData> datas = new ArrayList<MonthData>();
		//思路,
		//	1,根据查询条件,查询出所有的日期
		//	2,根据日期统计出所有的数据总和
		List<String> list = dowMapper.getTime(dta_Sce);
		for (String string : list) {
			dta_Sce.setLcl_Dpt_Day(string);
			//取到每天的去程同一航段旅客总数
			String monthData = dowMapper.getMonthData(dta_Sce);
			//取到每天的返程同一航段旅客总数
			String monthData2 = dowMapper.getMonthData2(dta_Sce);
			MonthData data = new MonthData();
			data.setDate(string);
			try {
				data.setWeek((DataUtils.getWeek(new SimpleDateFormat("yyyy-MM-dd").parse(string))));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(monthData!=null&&monthData!=""){
				data.setOutward(monthData);
			}
			if(monthData2!=null&monthData2!=""){
				data.setReturnTrip(monthData2);
			}
			datas.add(data);
		}
		int i = 0;
		int i1 = 0;
		int j = 0;
		int j1 = 0;
		for (MonthData string : datas) {
			if(string.getOutward()!=null&&string.getOutward()!=""){
				i += Integer.valueOf(string.getOutward());
				i1++;
			}
			if(string.getReturnTrip()!=null&&string.getReturnTrip()!=""){
				j += Integer.valueOf(string.getOutward());
				j1++;
			}
		}
		
		List<MonthData> footer = new ArrayList<MonthData>();
		MonthData airTotal = new MonthData();
		String AirPort = UserContext.getIndexData("year");
		if(i!=0&&i1!=0){
			airTotal.setDate(AirPort+"均班人数: "+i/i1);
			airTotal.setWeek(AirPort+"执行班次: "+i1);
		}
		if(j!=0&&j1!=0){
			airTotal.setOutward(AirPort+"均班人数: "+j/j1);
			airTotal.setReturnTrip(AirPort+"执行班次: "+j1);
		}
		footer.add(airTotal);
		MonthObject<MonthData> monthObject = new MonthObject<MonthData>(datas, datas.size(), footer);
		return monthObject;
	}
	/**
	 * 拿到每个月去程的符合要求的班次
	 */
	public List<DOW> getView(DOWQuery dta_Sce) {
		//创建一个存放数据的对象
		List<DOW> dows = new ArrayList<DOW>();
		//获得当前月时间内的数据
		List<Z_Airdata> weekDate = dowMapper.getWeekDate(dta_Sce);
		//获得当前查询数据的数据总数和人数总数
		SunAndCount sunAndCount = dowMapper.getSunAndCount(dta_Sce);
		//拿到三字码的中文名
		if(weekDate!=null&&weekDate.size()!=0){
			String AirPort = dowMapper.getAirPort2(weekDate.get(0).getDpt_AirPt_Cd()+weekDate.get(0).getArrv_Airpt_Cd());
			//在这里拿到了每个月的数据, 
			//将当前查询好的数据给添加进DOW类中
			dows.add(DOWDataUtils.getWeek(dta_Sce.getMonth(),weekDate,sunAndCount,AirPort));
		}
		return dows;
	}
	/**
	 * 拿到每个月去程的符合要求的班次
	 */
	public List<DOW> getViewSumPer(DOWQuery dta_Sce) {
		//创建一个存放数据的对象
		List<DOW> dows = new ArrayList<DOW>();
		//获得当前月时间内的数据
		List<Z_Airdata> weekDate = dowMapper.getWeekDate(dta_Sce);
		//获得当前查询数据的数据总数和人数总数
		SunAndCount sunAndCount = dowMapper.getSunAndCount(dta_Sce);
		//拿到三字码的中文名
		if(weekDate!=null&&weekDate.size()!=0){
			String AirPort = UserContext.getIndexData("year");
			//在这里拿到了每个月的数据, 
			//将当前查询好的数据给添加进DOW类中
			dows.add(DOWDataUtils.getWeekSumPer(dta_Sce.getMonth(),weekDate,sunAndCount,AirPort));
		}
		return dows;
	}
	/**
	 * 拿到每个月去程的符合要求的班次
	 */
	public List<DOW> getViewSum(DOWQuery dta_Sce) {
		//创建一个存放数据的对象
		List<DOW> dows = new ArrayList<DOW>();
		//获得当前月时间内的数据
		List<Z_Airdata> weekDate = dowMapper.getWeekDate(dta_Sce);
		//获得当前查询数据的数据总数和人数总数
		SunAndCount sunAndCount = dowMapper.getSunAndCount(dta_Sce);
		//拿到三字码的中文名
		if(weekDate!=null&&weekDate.size()!=0){
			String AirPort = UserContext.getIndexData("year");
			//在这里拿到了每个月的数据, 
			//将当前查询好的数据给添加进DOW类中
			dows.add(DOWDataUtils.getWeekSum(dta_Sce.getMonth(),weekDate,sunAndCount,AirPort));
		}
		return dows;
	}
	public List<DOW> getView2(DOWQuery dta_Sce) {
		//创建一个存放数据的对象
		List<DOW> dows = new ArrayList<DOW>();
		//获得当前月时间内的数据
		List<Z_Airdata> weekDate = dowMapper.getWeekDate(dta_Sce);
		//获得当前查询数据的数据总数和人数总数
		SunAndCount sunAndCount = dowMapper.getSunAndCount(dta_Sce);
		//拿到三字码的中文名
		if(weekDate!=null&&weekDate.size()!=0){
			String AirPort = UserContext.getIndexData("year");
			//在这里拿到了每个月的数据, 
			//将当前查询好的数据给添加进DOW类中,在原有基础上将一个月切分成四个自然周的方法, 并且按照自然周将执行次数放入星期一到星期日中
			dows.add(DOWDataUtils.getWeek(dta_Sce.getMonth(),weekDate,sunAndCount,AirPort));
		}
		return dows;
	}

}
