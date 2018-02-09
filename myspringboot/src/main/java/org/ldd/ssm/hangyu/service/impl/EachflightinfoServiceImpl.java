package org.ldd.ssm.hangyu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.EachflightDemo;
import org.ldd.ssm.hangyu.domain.Eachflightinfo;
import org.ldd.ssm.hangyu.mapper.EachflightinfoMapper;
import org.ldd.ssm.hangyu.service.EachflightinfoService;
import org.ldd.ssm.hangyu.utils.ListUtils;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.ldd.ssm.hangyu.web.JXLExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.aliyuncs.http.HttpRequest;
import com.aliyuncs.http.HttpResponse;
@Service
public class EachflightinfoServiceImpl implements EachflightinfoService{
	@Autowired
	private EachflightinfoMapper eachflightinfoMapper;
	Logger log = Logger.getLogger(EachflightinfoServiceImpl.class);
	
	@Override
	public Map<String, Object> getAirportTimeInfo(String itia, String getTime,String orderField,Integer orderType) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(TextUtil.isEmpty(itia)){
			log.debug("getAirportTimeInfo:itia is an invalid parameter");
			map.put("opResult", "3");//输入参数无效
			return map;
		}
		//转换字符串为数据中对应字段
		if("lclDptTm".equals(orderField)){
			orderField = "lcl_Dpt_Tm";
		}else if("lclArrvTm".equals(orderField)){
			orderField = "lcl_Arrv_Tm";
		}else{
			orderField = "flt_nbr";
		}
		try {
			//根据getTime判断是否首次输入条件查询
			if(TextUtil.isEmpty(getTime)){
				//获取时间列表(最新5次采集时间)
				List<String> timeList = eachflightinfoMapper.getTimeList();
				if(timeList!=null && timeList.size()>0){
					List<Eachflightinfo> list = eachflightinfoMapper.selectAirportTimeInfo(itia,timeList.get(0),orderField,orderType);//获取最新一次采集的数据
					//格式化数据
					List<String> newTimeList = new ArrayList<String>();
					for(String time:timeList){
						newTimeList.add(time.replaceAll("-", "\\."));
					}
					if(list!=null&&list.size()>0){
						for(Eachflightinfo eachflightinfo:list){
							String days = eachflightinfo.getDays();
							if(!TextUtil.isEmpty(days)){
								days = days.replace("一", "1/").replace("二", "2/").replace("三", "3/")
								.replace("四", "4/").replace("五", "5/").replace("六", "6/")
								.replace("日", "7/");
								eachflightinfo.setDays(days.substring(0, days.length()-1));
							}
						}
					}
					map.put("opResult", "0");
					map.put("timeList", newTimeList);
					map.put("list", list);
					return map;
				}else{
					//数据库没有数据可以查询-直接返回空
					map.put("opResult", "1");
					map.put("timeList", null);
					map.put("list", null);
					return map;
				}
			}else{
				//格式化日期
				getTime = getTime.replaceAll("\\.", "-");
				List<Eachflightinfo> list = eachflightinfoMapper.selectAirportTimeInfo(itia,getTime,orderField,orderType);
				//格式化数据
				if(list!=null&&list.size()>0){
					for(Eachflightinfo eachflightinfo:list){
						String days = eachflightinfo.getDays();
						if(!TextUtil.isEmpty(days)){
							days = days.replace("一", "1/").replace("二", "2/").replace("三", "3/")
							.replace("四", "4/").replace("五", "5/").replace("六", "6/")
							.replace("日", "7/");
							eachflightinfo.setDays(days.substring(0, days.length()-1));
						}
					}
				}
				map.put("opResult", "0");
				map.put("list", list);
				return map;
			}
		} catch (Exception e) {
			log.error("There are errors in the program");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
	}
public static void main(String[] args) {
	String date = "一二三";
	System.out.println(date.replace("一", "1/").replace("二", "2/"));
}
	@Override
	public Map<String, Object> getAirportTimeDistribution(String itia,
			String getTime) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(TextUtil.isEmpty(itia)||TextUtil.isEmpty(getTime)){
			log.debug("getAirportTimeDistribution:itia or getTime is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		//格式化日期
		getTime = getTime.replaceAll("\\.", "-");
		try {
			List<EachflightDemo> list = eachflightinfoMapper.selectAirportTimeDistribution(itia, getTime);
			if(list!=null&&list.size()>0){
				map.put("opResult", "0");
				//数据格式化
				list = ListUtils.formatEachflightTimeDistribution(list);
				map.put("list", list);
			}else{
				map.put("opResult", "1");
				map.put("list", null);
			}
		} catch (Exception e) {
			log.error("There are errors in the program");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	@Override
	public ModelAndView viewJxlView(String itia, String getTime,String orderField,Integer orderType,HttpRequest req,HttpResponse resp) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(TextUtil.isEmpty(itia)||TextUtil.isEmpty(getTime)){
			map.put("titles", "没有数据");
			return new ModelAndView(new JXLExcelView(map), null);
		}
		//转换字符串为数据中对应字段
		if("lclDptTm".equals(orderField)){
			orderField = "lcl_Dpt_Tm";
		}else if("lclArrvTm".equals(orderField)){
			orderField = "lcl_Arrv_Tm";
		}else{
			orderField = "flt_nbr";
		}
		//格式化日期
		getTime = getTime.replaceAll("\\.", "-");
		try {
			map.put("titles", itia);
			Map<String,List<Eachflightinfo>> model = new HashMap<String,List<Eachflightinfo>>();
			// 构造数据
			List<Eachflightinfo> list = eachflightinfoMapper.selectAirportTimeInfo(itia, getTime,orderField,orderType);
			//格式化班期
			if(list!=null&&list.size()>0){
				for(Eachflightinfo eachflightinfo:list){
					String days = eachflightinfo.getDays();
					if(!TextUtil.isEmpty(days)){
						days = days.replace("一", "1/").replace("二", "2/").replace("三", "3/")
						.replace("四", "4/").replace("五", "5/").replace("六", "6/")
						.replace("日", "7/");
						eachflightinfo.setDays(days.substring(0, days.length()-1));
					}
				}
			}
			model.put("list", list);		
			return new ModelAndView(new JXLExcelView(map), model);
		} catch (Exception e) {
			map.put("titles", "错误数据");
			return new ModelAndView(new JXLExcelView(map), null);
		}
	}
}
