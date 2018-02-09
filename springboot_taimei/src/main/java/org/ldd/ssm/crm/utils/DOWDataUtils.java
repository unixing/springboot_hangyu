package org.ldd.ssm.crm.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.ldd.ssm.crm.domain.DOW;
import org.ldd.ssm.crm.domain.SunAndCount;
import org.ldd.ssm.crm.domain.Z_Airdata;

public class DOWDataUtils {
	/**
	 * 此方法判断班次执行为星期几
	 * @param month
	 * @param weekDate
	 * @return
	 */
	public static DOW getWeek(String month,List<Z_Airdata> weekDate,SunAndCount sunAndCount,String ChinaName){
		//计算平均人数
		DOW dow = new DOW();
		if(sunAndCount!=null&&sunAndCount.getSum()!=0&&sunAndCount.getCount()!=0){
			int intValue = new BigDecimal(sunAndCount.getSum()).divide(new BigDecimal(sunAndCount.getCount()),0,BigDecimal.ROUND_HALF_UP).intValue();
			for (Z_Airdata z_Airdata : weekDate) {
				dow.setExecuteClz(dow.getExecuteClz()+1);
				dow.setClzPer(dow.getClzPer()+z_Airdata.getPgs_Per_Cls());
				Calendar c = Calendar.getInstance();
				c.setTime(z_Airdata.getLcl_Dpt_Day());
				//遍历集合中的数据,判断是星期几,如果是按照星期按照星期给添加数量
				dow.setInformation(new SimpleDateFormat("yyyy-MM-dd").format(weekDate.get(0).getLcl_Dpt_Day()));
				dow.setMethod(month);
				if(z_Airdata.getPgs_Per_Cls()!=0){
					c.setTime(z_Airdata.getLcl_Dpt_Day());
					if(c.get(Calendar.DAY_OF_WEEK) == 1){
						if(z_Airdata.getPgs_Per_Cls()>intValue){
							dow.setSunTotal(dow.getSunTotal()+1);
						}
					}else if(c.get(Calendar.DAY_OF_WEEK) == 2){
						if(z_Airdata.getPgs_Per_Cls()>intValue){
							dow.setMonTotal(dow.getMonTotal()+1);
						}
					}else if(c.get(Calendar.DAY_OF_WEEK) == 3){
						if(z_Airdata.getPgs_Per_Cls()>intValue){
							dow.setTueTotal(dow.getTueTotal()+1);
						}
					}else if(c.get(Calendar.DAY_OF_WEEK) == 4){
						if(z_Airdata.getPgs_Per_Cls()>intValue){
							dow.setWedTotal(dow.getWedTotal()+1);
						}
					}else if(c.get(Calendar.DAY_OF_WEEK) == 5){
						if(z_Airdata.getPgs_Per_Cls()>intValue){
							dow.setThurTotal(dow.getThurTotal()+1);
						}
					}else if(c.get(Calendar.DAY_OF_WEEK) == 6){
						if(z_Airdata.getPgs_Per_Cls()>intValue){
							dow.setFriTotal(dow.getFriTotal()+1);
						}
					}else if(c.get(Calendar.DAY_OF_WEEK) == 7){
						if(z_Airdata.getPgs_Per_Cls()>intValue){
							dow.setSatTotal(dow.getSatTotal()+1);
						}
					}
				}
			}
			dow.setClzPer(new BigDecimal(dow.getClzPer()).divide(new BigDecimal(dow.getExecuteClz()),0,BigDecimal.ROUND_HALF_UP).intValue());
		}else{
			dow.setMethod(month);
		}
		return dow;
	}
	/**
	 * 此方法为计算班次总数
	 * @param dows
	 * @return
	 */
	public static List<DOW> getTotal(List<DOW> dows) {
		List<DOW> dows2 = new ArrayList<DOW>();
		DOW dow = new DOW();
		dow.setMethod("总计");
		for (DOW d : dows) {
			dow.setExecuteClz(dow.getExecuteClz()+d.getExecuteClz());
			dow.setClzPer(dow.getClzPer()+d.getClzPer());
			dow.setMonTotal(dow.getMonTotal()+d.getMonTotal());
			dow.setTueTotal(dow.getTueTotal()+d.getTueTotal());
			dow.setWedTotal(dow.getWedTotal()+d.getWedTotal());
			dow.setThurTotal(dow.getThurTotal()+d.getThurTotal());
			dow.setFriTotal(dow.getFriTotal()+d.getFriTotal());
			dow.setSatTotal(dow.getSatTotal()+d.getSatTotal());
			dow.setSunTotal(dow.getSunTotal()+d.getSunTotal());
		}
		dows2.add(dow);
		return dows2;
	}
	/**
	 * 获得当前月的天数
	 * @param lcl_Dpt_Day 年份 如2015
	 * @param month 月份 如 01
	 * @return 
	 */
	public static int getNumbertime(String lcl_Dpt_Day, String month) {
		if(lcl_Dpt_Day!=""&&month!=""){
			try {
				lcl_Dpt_Day = lcl_Dpt_Day.substring(0, 3);
				Date parse = new SimpleDateFormat("yyyy-MM").parse(lcl_Dpt_Day+"-"+month);
				Calendar c = Calendar.getInstance();
				c.setTime(parse);
				int actualMaximum = c.getActualMaximum(Calendar.DATE);
				return actualMaximum;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	public static DOW getWeekSum(String month, List<Z_Airdata> weekDate,
			SunAndCount sunAndCount, String ChinaName) {
		DOW dow = new DOW();
		for (Z_Airdata z_Airdata : weekDate) {
		if(sunAndCount!=null&&sunAndCount.getSum()!=0&&sunAndCount.getCount()!=0){
			Calendar c = Calendar.getInstance();
			c.setTime(z_Airdata.getLcl_Dpt_Day());
			//遍历集合中的数据,判断是星期几,如果是按照星期按照星期给添加数量
			dow.setMethod(month);
			if(z_Airdata.getPgs_Per_Cls()!=0){
				
			c.setTime(z_Airdata.getLcl_Dpt_Day());
			if(c.get(Calendar.DAY_OF_WEEK) == 1){
					dow.setSunTotal(dow.getSunTotal()+1);
			}else if(c.get(Calendar.DAY_OF_WEEK) == 2){
					dow.setMonTotal(dow.getMonTotal()+1);
			}else if(c.get(Calendar.DAY_OF_WEEK) == 3){
					dow.setTueTotal(dow.getTueTotal()+1);
			}else if(c.get(Calendar.DAY_OF_WEEK) == 4){
					dow.setWedTotal(dow.getWedTotal()+1);
			}else if(c.get(Calendar.DAY_OF_WEEK) == 5){
					dow.setThurTotal(dow.getThurTotal()+1);
			}else if(c.get(Calendar.DAY_OF_WEEK) == 6){
					dow.setFriTotal(dow.getFriTotal()+1);
			}else if(c.get(Calendar.DAY_OF_WEEK) == 7){
					dow.setSatTotal(dow.getSatTotal()+1);
			}
			}
		}
//		20XX年XX月西昌到咸阳DOW执行班次统计
		Date lcl_Dpt_Day = weekDate.get(0).getLcl_Dpt_Day();
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
		format.format(lcl_Dpt_Day);
		dow.setInformation(format.format(lcl_Dpt_Day)+ChinaName);
		}
		return dow;
	}
	public static DOW getWeekSumPer(String month, List<Z_Airdata> weekDate,
			SunAndCount sunAndCount, String ChinaName) {
			DOW dow = new DOW();
			for (Z_Airdata z_Airdata : weekDate) {
			if(sunAndCount!=null&&sunAndCount.getSum()!=0&&sunAndCount.getCount()!=0){
				Calendar c = Calendar.getInstance();
				c.setTime(z_Airdata.getLcl_Dpt_Day());
				//遍历集合中的数据,判断是星期几,如果是按照星期按照星期给添加数量
				//20XX年XX月西昌到咸阳DOW客量变化对比”
				dow.setInformation(new SimpleDateFormat("yyyy年MM月").format(weekDate.get(0).getLcl_Dpt_Day())+ChinaName);
				dow.setMethod(month);
				if(z_Airdata.getPgs_Per_Cls()!=0){
					if(c.get(Calendar.DAY_OF_WEEK) == 1){
							dow.setSunTotal(dow.getSunTotal()+z_Airdata.getPgs_Per_Cls());
					}else if(c.get(Calendar.DAY_OF_WEEK) == 2){
							dow.setMonTotal(dow.getMonTotal()+z_Airdata.getPgs_Per_Cls());
					}else if(c.get(Calendar.DAY_OF_WEEK) == 3){
							dow.setTueTotal(dow.getTueTotal()+z_Airdata.getPgs_Per_Cls());
					}else if(c.get(Calendar.DAY_OF_WEEK) == 4){
							dow.setWedTotal(dow.getWedTotal()+z_Airdata.getPgs_Per_Cls());
					}else if(c.get(Calendar.DAY_OF_WEEK) == 5){
							dow.setThurTotal(dow.getThurTotal()+z_Airdata.getPgs_Per_Cls());
					}else if(c.get(Calendar.DAY_OF_WEEK) == 6){
							dow.setFriTotal(dow.getFriTotal()+z_Airdata.getPgs_Per_Cls());
					}else if(c.get(Calendar.DAY_OF_WEEK) == 7){
							dow.setSatTotal(dow.getSatTotal()+z_Airdata.getPgs_Per_Cls());
					}
				}
			}
		}
		return dow;
	}
}
