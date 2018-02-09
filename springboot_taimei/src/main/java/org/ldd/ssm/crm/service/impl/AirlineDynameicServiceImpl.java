package org.ldd.ssm.crm.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.ldd.ssm.crm.domain.Abnormal;
import org.ldd.ssm.crm.domain.AirlineDynameicGraphics;
import org.ldd.ssm.crm.domain.Yesterday_ZD;
import org.ldd.ssm.crm.domain.ZdlQueryDomin;
import org.ldd.ssm.crm.domain.ZdlQueryDominSon;
import org.ldd.ssm.crm.mapper.AirlineDynameicMapper;
import org.ldd.ssm.crm.query.HomePageQuery;
import org.ldd.ssm.crm.query.SortQuery;
import org.ldd.ssm.crm.service.IAirlineDynameicService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
@Service
public class AirlineDynameicServiceImpl implements IAirlineDynameicService {
	@Autowired
	private AirlineDynameicMapper airlineDynameicMapper;
	Logger log = Logger.getLogger(AirlineDynameicServiceImpl.class);
	
	@Override
	public Map<String,Object> getAirline_dynameic_list(String date) {
		Map<String,Object> map = new HashMap<String,Object>();
		Gson gson=new Gson();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			List<List<Yesterday_ZD>> list = new ArrayList<List<Yesterday_ZD>>();
			String getcompanyItia = UserContext.getcompanyItia();
			if(date==null||"".equals(date)){
				String city = UserContext.getCompanyName();
//				String lastDate = airlineDynameicMapper.getNewestDate(getcompanyItia, "in");
				String lastDate = "";
				//调用外部接口
				try {
					Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_airdate_by_code/").header("Accept", "*/*").ignoreContentType(true);
					if(!TextUtil.isEmpty(getcompanyItia)){
						connect.data("airport_code", getcompanyItia);
					}
					connect.data("type", "arr");
					Document doc = connect.timeout(100000).post();
					JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
					lastDate =returnData.get("result").getAsString();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(lastDate!=null&&!"".equals(lastDate)){
					String [] month = lastDate.split("-");
					List<String> dates = new ArrayList<String>();
					//调用外部接口
					try {
						Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_airdate_by_code_month/").header("Accept", "*/*").ignoreContentType(true);
						if(!TextUtil.isEmpty(getcompanyItia)){
							connect.data("airport_code", getcompanyItia);
						}
						if(!TextUtil.isEmpty(month[0])){
							connect.data("air_date_year", month[0]);
							
						}
						if(!TextUtil.isEmpty(month[1])){
							connect.data("air_date_month", month[1]);
						}
						connect.data("type", "arr");
						Document doc = connect.timeout(100000).post();
						JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
						dates = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<String>>(){}.getType());
					} catch (IOException e) {
						e.printStackTrace();
					}
//					List<String> dates = airlineDynameicMapper.getDateList(getcompanyItia, month[0],month[1], "in");
					map.put("indates", dates);
					//调用外部接口
					List<Yesterday_ZD> getAirline_dynameic_list_j = new ArrayList<Yesterday_ZD>();
					try {
						Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_data_by_code_date/").header("Accept", "*/*").ignoreContentType(true);
						if(!TextUtil.isEmpty(lastDate)){
							connect.data("air_date", lastDate);
						}
						if(!TextUtil.isEmpty(getcompanyItia)){
							connect.data("arr_airport_code", getcompanyItia);
						}
						Document doc = connect.timeout(100000).post();
						JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
						getAirline_dynameic_list_j = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<Yesterday_ZD>>(){}.getType());
					} catch (IOException e) {
						e.printStackTrace();
					}
//					List<Yesterday_ZD> getAirline_dynameic_list_j = airlineDynameicMapper.getGetAirline_dynameic_list_j(getcompanyItia,lastDate);
					
					if(getAirline_dynameic_list_j!=null&&getAirline_dynameic_list_j.size()>0){
						for (Yesterday_ZD yesterday_ZD : getAirline_dynameic_list_j) {
							if("-0".equals(yesterday_ZD.getRemark())){
								yesterday_ZD.setRemark(null);
							}
							if(!yesterday_ZD.getState().contains("取消")&&!yesterday_ZD.getState().contains("延误")){
								String regEx = "^(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])$";
								Pattern p = Pattern.compile(regEx);
								boolean flag = p.matcher(yesterday_ZD.getPlan_c()).matches()&&p.matcher(yesterday_ZD.getActual_c()).matches();//验证时间格式
								if(!flag){
									yesterday_ZD.setState("正常");
									continue;
								}
								if(yesterday_ZD.getZd_rate().replaceAll("[^0-9.]", "").length()>5){
									continue;
								}
								if(!p.matcher(yesterday_ZD.getPlan_q()).matches()){
									yesterday_ZD.setPlan_q("--");
								}
								if(!p.matcher(yesterday_ZD.getActual_q()).matches()){
									yesterday_ZD.setActual_q("--");
								}
								String plan_c = yesterday_ZD.getPlan_c().replaceAll("[^0-9:]", "");
								String[] split = plan_c.split(":");
								Integer valueOf0 = Integer.valueOf(split[0]);
								Integer valueOf1 = Integer.valueOf(split[1]);
								int time0 = (valueOf0*60*60*1000)+(valueOf1*60*1000);
								
								String actual_c = yesterday_ZD.getActual_c().replaceAll("[^0-9:]", "");
								String[] split2 = actual_c.split(":");
								Integer value0 = Integer.valueOf(split2[0]);
								Integer value1 = Integer.valueOf(split2[0]);
								int time1 = (value0*60*60*1000)+(value1*60*1000);
								if(value0==0 && time1<time0){
									time1 = time1+86400000;
								}
								if(time1-time0 > 1800000){//当实际到达时间比计划时间晚30分钟时，航班动态为延误
									yesterday_ZD.setState("延误");
								}else{
									yesterday_ZD.setState("正常");
								}
							}
						}
					}
					list.add(getAirline_dynameic_list_j);
				}else{
					list.add(new ArrayList<Yesterday_ZD>());
				}
				
//				lastDate = airlineDynameicMapper.getNewestDate(getcompanyItia, "out");
				//调用外部接口
				try {
					Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_airdate_by_code/").header("Accept", "*/*").ignoreContentType(true);
					if(!TextUtil.isEmpty(getcompanyItia)){
						connect.data("airport_code", getcompanyItia);
					}
					connect.data("type", "dep");
					Document doc = connect.timeout(100000).post();
					JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
					lastDate =returnData.get("result").getAsString();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(lastDate!=null&&!"".equals(lastDate)){
					String [] month = lastDate.split("-");
//					List<String> dates = airlineDynameicMapper.getDateList(getcompanyItia, month[0],month[1], "out");
					List<String> dates = new ArrayList<String>();
					//调用外部接口
					try {
						Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_airdate_by_code_month/").header("Accept", "*/*").ignoreContentType(true);
						if(!TextUtil.isEmpty(getcompanyItia)){
							connect.data("airport_code", getcompanyItia);
						}
						if(!TextUtil.isEmpty(month[0])){
							connect.data("air_date_year", month[0]);
						}
						if(!TextUtil.isEmpty(month[1])){
							connect.data("air_date_month", month[1]);
						}
						connect.data("type", "dep");
						Document doc = connect.timeout(100000).post();
						JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
						dates = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<String>>(){}.getType());
					} catch (IOException e) {
						e.printStackTrace();
					}
					map.put("outdates", dates);
					
//					List<Yesterday_ZD> getAirline_dynameic_list_c = airlineDynameicMapper.getGetAirline_dynameic_list_c(getcompanyItia,lastDate);
					//调用外部接口
					List<Yesterday_ZD> getAirline_dynameic_list_c = new ArrayList<Yesterday_ZD>();
					try {
						Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_data_by_code_date/").header("Accept", "*/*").ignoreContentType(true);
						if(!TextUtil.isEmpty(lastDate)){
							connect.data("air_date", lastDate);
						}
						if(!TextUtil.isEmpty(getcompanyItia)){
							connect.data("dep_airport_code", getcompanyItia);
						}
						Document doc = connect.timeout(100000).post();
						JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
						getAirline_dynameic_list_c = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<Yesterday_ZD>>(){}.getType());
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(getAirline_dynameic_list_c!=null&&getAirline_dynameic_list_c.size()>0){
						for (Yesterday_ZD yesterday_ZD : getAirline_dynameic_list_c) {
							if("-0".equals(yesterday_ZD.getRemark())){
								yesterday_ZD.setRemark(null);
							}
							if(!yesterday_ZD.getState().contains("取消")&&!yesterday_ZD.getState().contains("延误")){
								String regEx = "^(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])$";
								Pattern p = Pattern.compile(regEx);
								boolean flag = p.matcher(yesterday_ZD.getPlan_c()).matches()&&p.matcher(yesterday_ZD.getActual_c()).matches();//验证时间格式
								if(!flag){
									yesterday_ZD.setState("正常");
									continue;
								}
								if(yesterday_ZD.getZd_rate().replaceAll("[^0-9.]", "").length()>5){
									continue;
								}
								if(!p.matcher(yesterday_ZD.getPlan_q()).matches()){
									yesterday_ZD.setPlan_q("--");
								}
								if(!p.matcher(yesterday_ZD.getActual_q()).matches()){
									yesterday_ZD.setActual_q("--");
								}
								String plan_c = yesterday_ZD.getPlan_c().replaceAll("[^0-9:]", "");
								String[] split = plan_c.split(":");
								Integer valueOf0 = Integer.valueOf(split[0]);
								Integer valueOf1 = Integer.valueOf(split[1]);
								int time0 = (valueOf0*60*60*1000)+(valueOf1*60*1000);
								
								String actual_c = yesterday_ZD.getActual_c().replaceAll("[^0-9:]", "");
								String[] split2 = actual_c.split(":");
								Integer value0 = Integer.valueOf(split2[0]);
								Integer value1 = Integer.valueOf(split2[0]);
								int time1 = (value0*60*60*1000)+(value1*60*1000);
								if(value0==0 && time1<time0){
									time1 = time1+86400000;
								}
								if(time1-time0 > 1800000){//当实际到达时间比计划时间晚30分钟时，航班动态为延误
									yesterday_ZD.setState("延误");
								}else{
									yesterday_ZD.setState("正常");
								}
							}
						}
					}
					list.add(getAirline_dynameic_list_c);
				}else{
					list.add(new ArrayList<Yesterday_ZD>());
				}
				map.put("list", list);
			}else{
//				List<Yesterday_ZD> getAirline_dynameic_list_j = airlineDynameicMapper.getGetAirline_dynameic_list_j(getcompanyItia,date);
				//调用外部接口
				List<Yesterday_ZD> getAirline_dynameic_list_j = new ArrayList<Yesterday_ZD>();
				try {
					Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_data_by_code_date/").header("Accept", "*/*").ignoreContentType(true);
					if(!TextUtil.isEmpty(date)){
						connect.data("air_date", date);
					}
					if(!TextUtil.isEmpty(getcompanyItia)){
						connect.data("arr_airport_code", getcompanyItia);
					}
					Document doc = connect.timeout(100000).post();
					JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
					getAirline_dynameic_list_j = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<Yesterday_ZD>>(){}.getType());
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(getAirline_dynameic_list_j!=null&&getAirline_dynameic_list_j.size()>0){
					for (Yesterday_ZD yesterday_ZD : getAirline_dynameic_list_j) {
						if("-0".equals(yesterday_ZD.getRemark())){
							yesterday_ZD.setRemark(null);
						}
						if(!yesterday_ZD.getState().contains("取消")&&!yesterday_ZD.getState().contains("延误")){
							String regEx = "^(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])$";
							Pattern p = Pattern.compile(regEx);
							boolean flag = p.matcher(yesterday_ZD.getPlan_c()).matches()&&p.matcher(yesterday_ZD.getActual_c()).matches();//验证时间格式
							if(!flag){
								yesterday_ZD.setState("正常");
								continue;
							}
							if(yesterday_ZD.getZd_rate().replaceAll("[^0-9.]", "").length()>5){
								continue;
							}
							if(!p.matcher(yesterday_ZD.getPlan_q()).matches()){
								yesterday_ZD.setPlan_q("--");
							}
							if(!p.matcher(yesterday_ZD.getActual_q()).matches()){
								yesterday_ZD.setActual_q("--");
							}
							String plan_c = yesterday_ZD.getPlan_c().replaceAll("[^0-9:]", "");
							String[] split = plan_c.split(":");
							Integer valueOf0 = Integer.valueOf(split[0]);
							Integer valueOf1 = Integer.valueOf(split[1]);
							int time0 = (valueOf0*60*60*1000)+(valueOf1*60*1000);
							
							String actual_c = yesterday_ZD.getActual_c().replaceAll("[^0-9:]", "");
							String[] split2 = actual_c.split(":");
							Integer value0 = Integer.valueOf(split2[0]);
							Integer value1 = Integer.valueOf(split2[0]);
							int time1 = (value0*60*60*1000)+(value1*60*1000);
							if(value0==0 && time1<time0){
								time1 = time1+86400000;
							}
							if(time1-time0 > 1800000){//当实际到达时间比计划时间晚30分钟时，航班动态为延误
								yesterday_ZD.setState("延误");
							}else{
								yesterday_ZD.setState("正常");
							}
						}
					}
				}
//				List<Yesterday_ZD> getAirline_dynameic_list_c = airlineDynameicMapper.getGetAirline_dynameic_list_c(getcompanyItia,date);
				//调用外部接口
				List<Yesterday_ZD> getAirline_dynameic_list_c = new ArrayList<Yesterday_ZD>();
				try {
					Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_data_by_code_date/").header("Accept", "*/*").ignoreContentType(true);
					if(!TextUtil.isEmpty(date)){
						connect.data("air_date", date);
					}
					if(!TextUtil.isEmpty(getcompanyItia)){
						connect.data("dep_airport_code", getcompanyItia);
					}
					Document doc = connect.timeout(100000).post();
					JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
					getAirline_dynameic_list_c = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<Yesterday_ZD>>(){}.getType());
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(getAirline_dynameic_list_c!=null&&getAirline_dynameic_list_c.size()>0){
					for (Yesterday_ZD yesterday_ZD : getAirline_dynameic_list_c) {
						if("-0".equals(yesterday_ZD.getRemark())){
							yesterday_ZD.setRemark(null);
						}
						if(!yesterday_ZD.getState().contains("取消")&&!yesterday_ZD.getState().contains("延误")){
							String regEx = "^(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])$";
							Pattern p = Pattern.compile(regEx);
							boolean flag = p.matcher(yesterday_ZD.getPlan_c()).matches()&&p.matcher(yesterday_ZD.getActual_c()).matches();//验证时间格式
							if(!flag){
								yesterday_ZD.setState("正常");
								continue;
							}
							if(yesterday_ZD.getZd_rate().replaceAll("[^0-9.]", "").length()>5){
								continue;
							}
							if(!p.matcher(yesterday_ZD.getPlan_q()).matches()){
								yesterday_ZD.setPlan_q("--");
							}
							if(!p.matcher(yesterday_ZD.getActual_q()).matches()){
								yesterday_ZD.setActual_q("--");
							}
							String plan_c = yesterday_ZD.getPlan_c().replaceAll("[^0-9:]", "");
							String[] split = plan_c.split(":");
							Integer valueOf0 = Integer.valueOf(split[0]);
							Integer valueOf1 = Integer.valueOf(split[1]);
							int time0 = (valueOf0*60*60*1000)+(valueOf1*60*1000);
							
							String actual_c = yesterday_ZD.getActual_c().replaceAll("[^0-9:]", "");
							String[] split2 = actual_c.split(":");
							Integer value0 = Integer.valueOf(split2[0]);
							Integer value1 = Integer.valueOf(split2[0]);
							int time1 = (value0*60*60*1000)+(value1*60*1000);
							if(value0==0 && time1<time0){
								time1 = time1+86400000;
							}
							if(time1-time0 > 1800000){//当实际到达时间比计划时间晚30分钟时，航班动态为延误
								yesterday_ZD.setState("延误");
							}else{
								yesterday_ZD.setState("正常");
							}
						}
					}
				}
				list.add(getAirline_dynameic_list_j);
				list.add(getAirline_dynameic_list_c);
				map.put("list", list);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
			return map;
		}
		return map;
	}

	@Override
	public AirlineDynameicGraphics airline_dynameic_graphics(String date) {
		AirlineDynameicGraphics airlineDynameicGraphics = new AirlineDynameicGraphics();
		String getcompanyItia = UserContext.getcompanyItia();
		SortQuery query = new SortQuery();
		query.setItia(getcompanyItia);
		Gson gson=new Gson();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(date==null||"".equals(date)){
			String city = UserContext.getCompanyName();
			//调用外部接口
			try {
				Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_airdate_by_code/").header("Accept", "*/*").ignoreContentType(true);
				if(!TextUtil.isEmpty(getcompanyItia)){
					connect.data("airport_code", getcompanyItia);
				}
				Document doc = connect.timeout(1000000).post();
				JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
				date =returnData.get("result").getAsString();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			date = airlineDynameicMapper.getNewestDate(getcompanyItia, null);
			if(date==null||"".equals(date)){
				airlineDynameicGraphics.setMonth_zd(0+"");
				airlineDynameicGraphics.setDelay_cls(0+"");
				airlineDynameicGraphics.setCancel_cls(0+"");
				airlineDynameicGraphics.setNormal_cls(0+"");
				Abnormal[] exec = {new Abnormal("天气",0),new Abnormal("航空公司",0),new Abnormal("流量",0),new Abnormal("军事活动",0),
						new Abnormal("空管",0),new Abnormal("机场",0),new Abnormal("联检",0),new Abnormal("油料",0),
						new Abnormal("离港系统",0),new Abnormal("旅客",0),new Abnormal("公共安全",0)};
				airlineDynameicGraphics.setExec(exec);
				return airlineDynameicGraphics;
			}else{
				date = date.substring(0,date.length()-3);//获取月份
				String year = date.substring(0,4);
				List<String> months = new ArrayList<String>();
				//调用外部接口
				try {
					Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_airdate_by_code_year/").header("Accept", "*/*").ignoreContentType(true);
					if(!TextUtil.isEmpty(getcompanyItia)){
						connect.data("airport_code", getcompanyItia);
					}
					if(!TextUtil.isEmpty(year)){
						connect.data("air_date_year", year);
					}
					Document doc = connect.timeout(1000000).post();
					JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
					months = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<String>>(){}.getType());
				} catch (IOException e) {
					e.printStackTrace();
				}
//				List<String> months = airlineDynameicMapper.getMonthList(getcompanyItia, year);
				airlineDynameicGraphics.setMonths(months);
			}
		}
		query.setDate(date);
		query.setYear(date.substring(0,4));
		query.setMonth(date.substring(5,7));
//		List<Yesterday_ZD> zds = new ArrayList<Yesterday_ZD>();
		ZdlQueryDomin zdstemp = new ZdlQueryDomin();
		List<ZdlQueryDominSon> remark_list = new ArrayList<ZdlQueryDominSon>();
//		List<Yesterday_ZD> zds = airlineDynameicMapper.getGetAirline_dynameic_list_month(query);
		//调用外部接口
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_data_by_code_month/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(query.getItia())){
				connect.data("airport_code", query.getItia());
			}
			if(!TextUtil.isEmpty(query.getYear())){
				connect.data("air_date_year", query.getYear());
			}
			if(!TextUtil.isEmpty(query.getMonth())){
				connect.data("air_date_month", query.getMonth());
			}
			Document doc = connect.timeout(1000000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			zdstemp = gson.fromJson(returnData.get("result"),ZdlQueryDomin.class);
			remark_list = gson.fromJson(returnData.get("result").getAsJsonObject().get("remark_list"), new TypeToken<List<ZdlQueryDominSon>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		BigDecimal month_zd = new BigDecimal(0);
//		BigDecimal delay_cls = new BigDecimal(0);
//		BigDecimal cancel_cls = new BigDecimal(0);
//		BigDecimal normal_cls = new BigDecimal(0);
//		BigDecimal bigDecimal = new BigDecimal(1);
//		BigDecimal zd_cls = new BigDecimal(1);
//		for (Yesterday_ZD yesterday_ZD : zds) {
//			try {
//				if(yesterday_ZD.getState()!=null&&(yesterday_ZD.getState().indexOf("取消")>-1||"返航".equals(yesterday_ZD.getState()))){
//					cancel_cls = cancel_cls.add(bigDecimal);
//					zd_cls = zd_cls.add(bigDecimal);//
//				}else if("到达".equals(yesterday_ZD.getState())){
//					zd_cls = zd_cls.add(bigDecimal);//
//					String regEx = "^(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])$";
//					Pattern p = Pattern.compile(regEx);
//					boolean flag = p.matcher(yesterday_ZD.getPlan_c()).matches()&&p.matcher(yesterday_ZD.getActual_c()).matches();//验证时间格式
//					if(yesterday_ZD.getZd_rate().replaceAll("[^0-9.]", "").length()>5||!flag){
//						continue;
//					}
//					String regex = "^1[0-9]{2}|[1-9][0-9]\\.[0-9]{2}$";
//					if(Pattern.matches(regex, yesterday_ZD.getZd_rate())){//当准点率中有多个小数点时验证不通过
//						continue;
//					}
//					String plan_c = yesterday_ZD.getPlan_c().replaceAll("[^0-9:]", "");
//					String[] split = plan_c.split(":");
//					Integer valueOf0 = Integer.valueOf(split[0]);
//					Integer valueOf1 = Integer.valueOf(split[1]);
//					int time0 = (valueOf0*60*60*1000)+(valueOf1*60*1000);
//					String actual_c = yesterday_ZD.getActual_c().replaceAll("[^0-9:]", "");
//					String[] split2 = actual_c.split(":");
//					Integer value0 = Integer.valueOf(split2[0]);
//					Integer value1 = Integer.valueOf(split2[0]);
//					int time1 = (value0*60*60*1000)+(value1*60*1000);
//					if(value0==0 && time1<time0){
//						time1 = time1+86400000;
//					}
//					if(time1-time0 > 1800000){
//						delay_cls = delay_cls.add(bigDecimal);
//					}else{
//						normal_cls = normal_cls.add(bigDecimal);
//					}
//				}else{
//					delay_cls = delay_cls.add(bigDecimal);
//				}
//				if(!TextUtil.isEmpty(yesterday_ZD.getZd_rate())&&yesterday_ZD.getZd_rate().length()<10){
//					month_zd = month_zd.add(new BigDecimal(yesterday_ZD.getZd_rate().replaceAll("[^0-9.]", "")));
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		airlineDynameicGraphics.setMonth_zd((zd_cls.equals(new BigDecimal(0))?0:month_zd.divide(zd_cls,0,BigDecimal.ROUND_HALF_UP))+"");
//		airlineDynameicGraphics.setDelay_cls(delay_cls+"");
//		airlineDynameicGraphics.setCancel_cls(cancel_cls+"");
//		airlineDynameicGraphics.setNormal_cls(normal_cls+"");
//		
		airlineDynameicGraphics.setMonth_zd(zdstemp.getZd_rate().replace("%", ""));
		airlineDynameicGraphics.setDelay_cls(Integer.parseInt(zdstemp.getSum_count())-Integer.parseInt(zdstemp.getOntime_count())+"");
		airlineDynameicGraphics.setCancel_cls(zdstemp.getCancel_count());
		airlineDynameicGraphics.setNormal_cls(zdstemp.getOntime_count());
		//图形   5个柱形  分别是 天气原因,机器故障,航空管制,流量控制,突发事件
//		String[] titles = new String[]{"天气","航空公司","流量","军事活动","空管","机场","联检","油料","离港系统","旅客","公共安全"};
//		int tq = 0;
//		int hkgs = 0;
//		int ll = 0;
//		int jshd = 0;
//		int kg = 0;
//		int jc = 0;
//		int lj = 0;
//		int yl = 0;
//		int lgxt = 0;
//		int lk = 0;
//		int ggaq = 0;
//		int[] vals = new int[]{tq,hkgs,ll,jshd,kg,jc,lj,yl,lgxt,lk,ggaq};
//		for (Yesterday_ZD zd : zds) {
//			if(!zd.getRemark().equals("-0")&&zd.getRemark()!=null&&!"".equals(zd.getRemark())){
//				String[] split = zd.getRemark().split("\\|");
//				for (String string : split) {
//					for(int i=0;i<titles.length;i++){
//						if(string.equals(titles[i])){
//							vals[i] += 1;
//						}
//					}
//				}
//			}
//		}
//		List<ZdlQueryDominSon> remark_list = zdstemp.getRemark_list();
		Abnormal[] exec = {new Abnormal("天气",0),new Abnormal("航空公司",0),new Abnormal("流量",0),new Abnormal("军事活动",0),
				new Abnormal("空管",0),new Abnormal("机场",0),new Abnormal("联检",0),new Abnormal("油料",0),
				new Abnormal("离港系统",0),new Abnormal("旅客",0),new Abnormal("公共安全",0)};
		if(remark_list.size()>0){
			for (ZdlQueryDominSon zdlQueryDominSon : remark_list) {
				if("天气".equals(zdlQueryDominSon.getRemark())&&!TextUtil.isEmpty(zdlQueryDominSon.getSum_count())){
					exec[0]=new Abnormal("天气",Integer.parseInt(zdlQueryDominSon.getSum_count()));
				}
				if("航空公司".equals(zdlQueryDominSon.getRemark())&&!TextUtil.isEmpty(zdlQueryDominSon.getSum_count())){
					exec[1]=new Abnormal("航空公司",Integer.parseInt(zdlQueryDominSon.getSum_count()));
				}
				if("流量".equals(zdlQueryDominSon.getRemark())&&!TextUtil.isEmpty(zdlQueryDominSon.getSum_count())){
					exec[2]=new Abnormal("流量",Integer.parseInt(zdlQueryDominSon.getSum_count()));
				}
				if("军事活动".equals(zdlQueryDominSon.getRemark())&&!TextUtil.isEmpty(zdlQueryDominSon.getSum_count())){
					exec[3]=new Abnormal("军事活动",Integer.parseInt(zdlQueryDominSon.getSum_count()));
				}
				if("空管".equals(zdlQueryDominSon.getRemark())&&!TextUtil.isEmpty(zdlQueryDominSon.getSum_count())){
					exec[4]=new Abnormal("空管",Integer.parseInt(zdlQueryDominSon.getSum_count()));
				}
				if("机场".equals(zdlQueryDominSon.getRemark())&&!TextUtil.isEmpty(zdlQueryDominSon.getSum_count())){
					exec[5]=new Abnormal("机场",Integer.parseInt(zdlQueryDominSon.getSum_count()));
				}
				if("联检".equals(zdlQueryDominSon.getRemark())&&!TextUtil.isEmpty(zdlQueryDominSon.getSum_count())){
					exec[6]=new Abnormal("联检",Integer.parseInt(zdlQueryDominSon.getSum_count()));
				}
				if("油料".equals(zdlQueryDominSon.getRemark())&&!TextUtil.isEmpty(zdlQueryDominSon.getSum_count())){
					exec[7]=new Abnormal("油料",Integer.parseInt(zdlQueryDominSon.getSum_count()));
				}
				if("离港系统".equals(zdlQueryDominSon.getRemark())&&!TextUtil.isEmpty(zdlQueryDominSon.getSum_count())){
					exec[8]=new Abnormal("离港系统",Integer.parseInt(zdlQueryDominSon.getSum_count()));
				}
				if("旅客".equals(zdlQueryDominSon.getRemark())&&!TextUtil.isEmpty(zdlQueryDominSon.getSum_count())){
					exec[9]=new Abnormal("旅客",Integer.parseInt(zdlQueryDominSon.getSum_count()));
				}
				if("公共安全".equals(zdlQueryDominSon.getRemark())&&!TextUtil.isEmpty(zdlQueryDominSon.getSum_count())){
					exec[10]=new Abnormal("公共安全",Integer.parseInt(zdlQueryDominSon.getSum_count()));
				}
			}
		}
//		Abnormal[] exec = {new Abnormal("天气",vals[0]),new Abnormal("航空公司",vals[1]),new Abnormal("流量",vals[2]),new Abnormal("军事活动",vals[3]),
//				new Abnormal("空管",vals[4]),new Abnormal("机场",vals[5]),new Abnormal("联检",vals[6]),new Abnormal("油料",vals[7]),
//				new Abnormal("离港系统",vals[8]),new Abnormal("旅客",vals[9]),new Abnormal("公共安全",vals[10])};
		
//		
		Arrays.sort(exec,new MyComparator());
		airlineDynameicGraphics.setExec(exec);
		return airlineDynameicGraphics;
	}
	class  MyComparator implements Comparator{
		@Override
		public int compare(Object o1, Object o2) {
			Abnormal ab1 = (Abnormal) o1;
			Abnormal ab2 = (Abnormal) o2;
			if(ab1.getVal() != ab2.getVal())
	            return ab1.getVal()>ab2.getVal()? -1:1;
	        else
	            return 0;
		}
		
	}
	@Override
	public void airline_dynameic_save(String index, String sp) {
		//调用外部接口
		Gson gson = new Gson();
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/upd_remark_by_id/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(index)){
				connect.data("id", index);
			}
			if(!TextUtil.isEmpty(sp)){
				connect.data("remark",sp);
			}
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			String status = returnData.get("result").getAsJsonObject().get("status").getAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		airlineDynameicMapper.airline_dynameic_save(index,sp);
	}
	@Override
	public List<Yesterday_ZD> getAirline_dynameic_list_in(String date,
			String field,String sortType) {
		Gson gson = new Gson();
		String getcompanyItia = UserContext.getcompanyItia();
		SortQuery query = new SortQuery();
		query.setItia(getcompanyItia);
		query.setDate(date);
		query.setField(field);
		query.setSortWay(sortType);
//		List<Yesterday_ZD> getAirline_dynameic_list_j = airlineDynameicMapper.getGetAirline_dynameic_list_in(query);
		List<Yesterday_ZD> getAirline_dynameic_list_j = new ArrayList<Yesterday_ZD>();
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_data_by_code_date/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(date)){
				connect.data("air_date", date);
			}
			if(!TextUtil.isEmpty(getcompanyItia)){
				connect.data("arr_airport_code", getcompanyItia);
			}
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			getAirline_dynameic_list_j = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<Yesterday_ZD>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(getAirline_dynameic_list_j!=null&&getAirline_dynameic_list_j.size()>0){
			for (Yesterday_ZD yesterday_ZD : getAirline_dynameic_list_j) {
				if("到达".equals(yesterday_ZD.getState())){
					String regEx = "^(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])$";
					Pattern p = Pattern.compile(regEx);
					boolean flag = p.matcher(yesterday_ZD.getPlan_c()).matches()&&p.matcher(yesterday_ZD.getActual_c()).matches();//验证时间格式
					if(yesterday_ZD.getZd_rate().replaceAll("[^0-9.]", "").length()>5||!flag){
						continue;
					}
					if(!p.matcher(yesterday_ZD.getPlan_q()).matches()){
						yesterday_ZD.setPlan_q("--");
					}
					if(!p.matcher(yesterday_ZD.getActual_q()).matches()){
						yesterday_ZD.setActual_q("--");
					}
					String plan_c = yesterday_ZD.getPlan_c().replaceAll("[^0-9:]", "");
					String[] split = plan_c.split(":");
					Integer valueOf0 = Integer.valueOf(split[0]);
					Integer valueOf1 = Integer.valueOf(split[1]);
					int time0 = (valueOf0*60*60*1000)+(valueOf1*60*1000);
					
					String actual_c = yesterday_ZD.getActual_c().replaceAll("[^0-9:]", "");
					String[] split2 = actual_c.split(":");
					Integer value0 = Integer.valueOf(split2[0]);
					Integer value1 = Integer.valueOf(split2[0]);
					int time1 = (value0*60*60*1000)+(value1*60*1000);
					if(value0==0 && time1<time0){
						time1 = time1+86400000;
					}
					if(time1-time0 > 1800000){//当实际到达时间比计划时间晚30分钟时，航班动态为延误
						yesterday_ZD.setState("延误");
					}else{
						yesterday_ZD.setState("正常");
					}
				}
				if("-0".equals(yesterday_ZD.getRemark())){
					yesterday_ZD.setRemark(null);
				}
			}
		}
		return getAirline_dynameic_list_j;
	}

	@Override
	public List<Yesterday_ZD> getAirline_dynameic_list_out(String date,
			String field,String sortType) {
		Gson gson = new Gson();
		String getcompanyItia = UserContext.getcompanyItia();
		SortQuery query = new SortQuery();
		query.setItia(getcompanyItia);
		query.setDate(date);
		query.setField(field);
		query.setSortWay(sortType);
//		List<Yesterday_ZD> getAirline_dynameic_list_c = airlineDynameicMapper.getGetAirline_dynameic_list_out(query);
		List<Yesterday_ZD> getAirline_dynameic_list_c = new ArrayList<Yesterday_ZD>();
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_data_by_code_date/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(date)){
				connect.data("air_date", date);
			}
			if(!TextUtil.isEmpty(getcompanyItia)){
				connect.data("dep_airport_code", getcompanyItia);
			}
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			getAirline_dynameic_list_c = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<Yesterday_ZD>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(getAirline_dynameic_list_c!=null&&getAirline_dynameic_list_c.size()>0){
			for (Yesterday_ZD yesterday_ZD : getAirline_dynameic_list_c) {
				if("到达".equals(yesterday_ZD.getState())){
					String regEx = "^(([0-1]?[0-9])|([2][0-3])):([0-5]?[0-9])$";
					Pattern p = Pattern.compile(regEx);
					boolean flag = p.matcher(yesterday_ZD.getPlan_c()).matches()&&p.matcher(yesterday_ZD.getActual_c()).matches();//验证时间格式
					if(yesterday_ZD.getZd_rate().replaceAll("[^0-9.]", "").length()>5||!flag){
						continue;
					}
					if(!p.matcher(yesterday_ZD.getPlan_q()).matches()){
						yesterday_ZD.setPlan_q("--");
					}
					if(!p.matcher(yesterday_ZD.getActual_q()).matches()){
						yesterday_ZD.setActual_q("--");
					}
					String plan_c = yesterday_ZD.getPlan_c().replaceAll("[^0-9:]", "");
					String[] split = plan_c.split(":");
					Integer valueOf0 = Integer.valueOf(split[0]);
					Integer valueOf1 = Integer.valueOf(split[1]);
					int time0 = (valueOf0*60*60*1000)+(valueOf1*60*1000);
					
					String actual_c = yesterday_ZD.getActual_c().replaceAll("[^0-9:]", "");
					String[] split2 = actual_c.split(":");
					Integer value0 = Integer.valueOf(split2[0]);
					Integer value1 = Integer.valueOf(split2[0]);
					int time1 = (value0*60*60*1000)+(value1*60*1000);
					if(value0==0 && time1<time0){
						time1 = time1+86400000;
					}
					if(time1-time0 > 1800000){//当实际到达时间比计划时间晚30分钟时，航班动态为延误
						yesterday_ZD.setState("延误");
					}else{
						yesterday_ZD.setState("正常");
					}
				}
				if("-0".equals(yesterday_ZD.getRemark())){
					yesterday_ZD.setRemark(null);
				}
			}
		}
		return getAirline_dynameic_list_c;
	}

	@Override
	public String getAirportIthad(HomePageQuery query) {
		String ithad = "";
		try {
			ithad = airlineDynameicMapper.getAirportIthad(query);
			if(ithad==null){
				ithad = "0.00%";
			}else{
				ithad += "%";
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ithad;
		}
		return ithad;
	}

	@Override
	public List<String> getDateList(String city,String cityIata,String month,String inout) {
		List<String> list = null;
		Gson gson = new Gson();
		if(city==null||"".equals(city)){
			log.debug("getDateList:city is invalid");
			return list;
		}
		try {
			if(month==null||"".equals(month)){
//				String date = airlineDynameicMapper.getNewestDate(cityIata,inout);
				String date = "";
				//调用外部接口
				try {
					Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_airdate_by_code/").header("Accept", "*/*").ignoreContentType(true);
					if(!TextUtil.isEmpty(cityIata)){
						connect.data("airport_code", cityIata);
					}
					if("in".equals(inout)){
						connect.data("type", "dep");
					}else if("out".equals(inout)){
						connect.data("type", "arr");
					}
					Document doc = connect.timeout(100000).post();
					JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
					date =returnData.get("result").getAsString();
				} catch (IOException e) {
					e.printStackTrace();
				}
				month = date.substring(0,date.length()-3);
				String [] da = month.split("-"); 
//				String [] month = date.split("-");
				if(month!=null&&!"".equals(month)){
//					list = airlineDynameicMapper.getDateList(cityIata,da[0],da[1],inout);
					//调用外部接口
					try {
						Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_airdate_by_code_month/").header("Accept", "*/*").ignoreContentType(true);
						if(!TextUtil.isEmpty(cityIata)){
							connect.data("airport_code", cityIata);
						}
						if(!TextUtil.isEmpty(da[0])){
							connect.data("air_date_year", da[0]);
							
						}
						if(!TextUtil.isEmpty(da[1])){
							connect.data("air_date_month", da[1]);
						}
						if("in".equals(inout)){
							connect.data("type", "dep");
						}else if("out".equals(inout)){
							connect.data("type", "arr");
						}
						Document doc = connect.timeout(100000).post();
						JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
						list = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<String>>(){}.getType());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}else{
				String [] da = month.split("-"); 
				//调用外部接口
				try {
					Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_rate/get_airdate_by_code_month/").header("Accept", "*/*").ignoreContentType(true);
					if(!TextUtil.isEmpty(cityIata)){
						connect.data("airport_code", cityIata);
					}
					if(!TextUtil.isEmpty(da[0])){
						connect.data("air_date_year", da[0]);
						
					}
					if(!TextUtil.isEmpty(da[1])){
						connect.data("air_date_month", da[1]);
					}
					if("in".equals(inout)){
						connect.data("type", "dep");
					}else if("out".equals(inout)){
						connect.data("type", "arr");
					}
					Document doc = connect.timeout(100000).post();
					JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
					list = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<String>>(){}.getType());
				} catch (IOException e) {
					e.printStackTrace();
				}
//				list = airlineDynameicMapper.getDateList(cityIata,da[0],da[1],inout);
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
			return list;
		}
		return list;
	}

	@Override
	public List<String> getMonthList(String year) {
		List<String> months = null;
		if(year==null||"".equals(year)){
			log.debug("getMonthList:year is invalid");
			return months;
		}
		try {
			String city = UserContext.getCompanyName();
			String getcompanyItia = UserContext.getcompanyItia();
			months = airlineDynameicMapper.getMonthList(getcompanyItia, year);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
			return months;
		}
		return months;
	}
}