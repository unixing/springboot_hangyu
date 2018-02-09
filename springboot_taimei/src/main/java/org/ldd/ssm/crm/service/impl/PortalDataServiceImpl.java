package org.ldd.ssm.crm.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.SimpleFormatter;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.ldd.ssm.crm.domain.AirportData;
import org.ldd.ssm.crm.domain.ChinaAriLineData;
import org.ldd.ssm.crm.domain.CityCoordinate;
import org.ldd.ssm.crm.domain.Coords;
import org.ldd.ssm.crm.domain.HomePageData;
import org.ldd.ssm.crm.domain.PortalData;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.mapper.AirlineCompanyMapper;
import org.ldd.ssm.crm.mapper.HomePageMapper;
import org.ldd.ssm.crm.mapper.PortalDataMapper;
import org.ldd.ssm.crm.query.HomePageQuery;
import org.ldd.ssm.crm.service.FczreptileService;
import org.ldd.ssm.crm.service.HomePageService;
import org.ldd.ssm.crm.service.IPortalDataService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
@Service
public class PortalDataServiceImpl implements IPortalDataService {
	@Autowired
	private FczreptileService fczreptileService;
	@Autowired
	private AirlineCompanyMapper airlineCompanyMapper;
	@Autowired
	private HomePageMapper homePageMapper;
	@Autowired
	private HomePageService homePageService;
	@Autowired
	private PortalDataMapper objMapper;
	Logger log = Logger.getLogger(PortalDataServiceImpl.class);
	@Override
	public Map<String,Map<String, Object>> getPortalDataByAirports(
			List<String> airports) {
		if(airports==null||airports.size()==0){
			return null;
		}
//		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Map<String,Object>> list = new HashMap<String,Map<String,Object>>();
		try {
			//获取最新有数据日期
			String newDate = getNewDate(airports);
			HomePageQuery query = new HomePageQuery();
			query.setDate(newDate);
			for(String airport:airports){
				query.setAirPort(airport);
				Map<String,Object> map = getPortalDataByAirport(query);
				if(map!=null){
					list.put(airport,map);
				}
			}
			//清空集团账户下机场三字码
			UserContext.setcompanyItia(null);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("server is busying");
			return null;
		}
		return list;
	}
	@Override
	public Map<String, Map<String, Object>> getPortalDataByAirportsByTaimei(List<String> airports) {
		if(airports==null||airports.size()==0){
			return null;
		}
//		List<String> airlines = homePageMapper.getChinaAirLineDataList(null);
		//调用外部接口
		Gson gson=new Gson();
		List<String> flyListtep = new ArrayList<String>();
		List<String> airlines = new ArrayList<String>();
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_schedule/get_news_leg_by_airport/").header("Accept", "*/*").ignoreContentType(true);
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			flyListtep = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<String>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (String string : flyListtep) {
			airlines.add(string.replace("-", ""));
		}
		Map<String,Map<String,Object>> list = new HashMap<String,Map<String,Object>>();
		List<CityCoordinate> cityCoordinateList = homePageMapper.getCityCoordinateList();
		Map<String, AirportData> airportDataMap = homePageService.getAirportInfoMap();
		Map<String,String> cityCoordinateMap = new HashMap<String,String>();
		for (CityCoordinate cityCoordinate : cityCoordinateList) {
			cityCoordinateMap.put(cityCoordinate.getCityName(), cityCoordinate.getCityCoordinatee());
		}
		try {
			for(String airport:airports){
				List<ChinaAriLineData> chinaAriLineDataList = new ArrayList<ChinaAriLineData>();
				Map<String,Object> map = new HashMap<String,Object>();
				for (String line : airlines) {
					if(line.contains(airport)){
						ChinaAriLineData chinaAriLineData = new ChinaAriLineData();
						String fromName = line.substring(0, 3);
						String toName = line.substring(3, 6);
						if(airportDataMap.get(fromName)==null||airportDataMap.get(toName)==null){
							continue;
						}
						String namef = airportDataMap.get(fromName).getCtyChaNam();
						String namet = airportDataMap.get(toName).getCtyChaNam();
						chinaAriLineData.setFromName(namef);
						chinaAriLineData.setToName(namet);
						Coords coords = new Coords();
						String str1 = cityCoordinateMap.get(namef);
						String str2 = cityCoordinateMap.get(namet);
						String [] strsFromName = new String[2];
						String [] strsToName = new String[2];
						if(!TextUtil.isEmpty(str1)){
							strsFromName = str1.split(",");
						}
						if(!TextUtil.isEmpty(str2)){
							strsToName = str2.split(",");
						}
						coords.setFromName(strsFromName);
						coords.setToName(strsToName);
						chinaAriLineData.setCoords(coords);
						chinaAriLineDataList.add(chinaAriLineData);
					}
				}
				map.put("airlines", chinaAriLineDataList);
				if(map!=null){
					list.put(airport,map);
				}
			}
			//清空集团账户下机场三字码
			UserContext.setcompanyItia(null);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("server is busying");
			return null;
		}
		return list;
	}
	public Map<String,Object> getPortalDataByAirport(HomePageQuery query){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			//获取机场准点率
			String airport_Zd=fczreptileService.getAirportIthad(query);
			map.put("zdl", airport_Zd);
			String airport=query.getAirPort();
			String date = query.getDate();
			//获取航线列表
//			List<String> airlines = airlineCompanyMapper.getCurrentAirportAirlineList(airport);
//			map.put("airlines", airlines);
//			List<String> airlines = homePageMapper.getChinaAirLineDataList(airport);
			//调用外部接口
			Gson gson=new Gson();
			List<String> flyListtep = new ArrayList<String>();
			List<String> airlines = new ArrayList<String>();
			try {
				Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_schedule/get_news_leg_by_airport/").header("Accept", "*/*").ignoreContentType(true);
				if(!TextUtil.isEmpty(airport)){
					connect.data("dep_airport_code", airport.toUpperCase());
				}
				Document doc = connect.timeout(100000).post();
				JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
				flyListtep = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<String>>(){}.getType());
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (String string : flyListtep) {
				airlines.add(string.replace("-", ""));
			}
			if(airlines!=null&&airlines.size()>0){
//				List<CityCoordinate> cityCoordinateList = homePageMapper.getCityCoordinateList();
//				Map<String,String> cityCoordinateMap = new HashMap<String,String>();
//				for (CityCoordinate cityCoordinate : cityCoordinateList) {
//					cityCoordinateMap.put(cityCoordinate.getCityName(), cityCoordinate.getCityCoordinatee());
//				}
				Map<String, AirportData> airportDataMap = homePageService.getAirportInfoMap();
				List<ChinaAriLineData> chinaAriLineDataList = new ArrayList<ChinaAriLineData>();
				for (String airLine : airlines) {
					ChinaAriLineData chinaAriLineData = new ChinaAriLineData();
					String fromName = airLine.substring(0, 3);
					String toName = airLine.substring(3, 6);
					if(airportDataMap.get(fromName)==null||airportDataMap.get(toName)==null){
						continue;
					}
					String namef = airportDataMap.get(fromName).getAptChaNam();
					String namet = airportDataMap.get(toName).getAptChaNam();
					chinaAriLineData.setFromName(namef);
					chinaAriLineData.setToName(namet);
					Coords coords = new Coords();
					String str1 = airportDataMap.get(fromName).getCoordinate();
					String str2 = airportDataMap.get(namet).getCoordinate();
					String [] strsFromName = new String[2];
					String [] strsToName = new String[2];
					if(!TextUtil.isEmpty(str1)){
						strsFromName = str1.split(",");
					}
					if(!TextUtil.isEmpty(str2)){
						strsToName = str2.split(",");
					}
					coords.setFromName(strsFromName);
					coords.setToName(strsToName);
					chinaAriLineData.setCoords(coords);
					chinaAriLineDataList.add(chinaAriLineData);
				}
				map.put("airlines", chinaAriLineDataList);
			}
			//获取最近30天数据
			query.setStartTime(TextUtil.addDateDay(date, -29));
			query.setEndTime(date);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String startDate=TextUtil.addDateDay(date, -6);
			long startTime = sdf.parse(startDate).getTime();
			long endTime = sdf.parse(date).getTime();
			//设置sql拦截机场三字码
			UserContext.setcompanyItia(airport);
			HomePageQuery homePageQuery = new HomePageQuery();
			homePageQuery.setAirPort(airport);
			List<HomePageData> hpdata = homePageService.getAirportOnLineDataList(homePageQuery);
			//将首页的所有航线放入到seesion中
			UserContext.setHomePageDataList(hpdata);
			List<Z_Airdata> list = objMapper.getPortalDataList(query);
			if(list!=null&&list.size()>0){
				//参数声明
				//汇总-30
				//含过站甩飞
				double zsr=0.0;
				int zkl = 0;
				int zjc=0;
				//含过站
				double pzsr=0.0;
				int pzkl=0;
				int pzjc=0;
				//含甩飞
				double tzsr=0.0;
				int tzkl=0;
				int tzjc=0;
				//不含过站甩飞
				double nzsr=0.0;
				int nzkl=0;
				int nzjc=0;
				//出港
				double czsr=0;
				int czkl=0;
				int czjc=0;
				//进港
				double jzsr=0.0;
				int jzkl=0;
				int jzjc=0;
				//汇总-7
				//含过站甩飞
				double zsr7=0.0;
				int zkl7 = 0;
				int zjc7=0;
				//含过站
				double pzsr7=0.0;
				int pzkl7=0;
				int pzjc7=0;
				//含甩飞
				double tzsr7=0.0;
				int tzkl7=0;
				int tzjc7=0;
				//不含过站甩飞
				double nzsr7=0.0;
				int nzkl7=0;
				int nzjc7=0;
				//出港
				double czsr7=0;
				int czkl7=0;
				int czjc7=0;
				//进港
				double jzsr7=0.0;
				int jzkl7=0;
				int jzjc7=0;
				List<String> flts = new ArrayList<String>();
				for(Z_Airdata obj:list){
					if(flts.size()==0){
						flts.add(obj.getFlt_Nbr());
					}else{
						if(!flts.contains(obj.getFlt_Nbr())){
							flts.add(obj.getFlt_Nbr());
						}
					}
				}
				//计算总收入和总客量
				for(Z_Airdata obj:list){
					//汇总
					//甩飞过站
					zsr+=obj.getTotalNumber();
					zkl+=obj.getPgs_Per_Cls();
//					if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
//						//进出港
//						zjc++;
//					}else{
//						zjc+=2;//过站甩飞加2
//					}
					String flt=obj.getFlt_Rte_Cd();
					//过站
					if((obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport))||(flt.length()==9&&flt.substring(3,6).equals(airport))){
						pzsr+=obj.getTotalNumber();
						pzkl+=obj.getPgs_Per_Cls();
//						if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
//							pzjc++;
//						}else{
//							pzjc+=2;
//						}
					}
					//甩飞
					if((obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport))||
							(flt.length()==9&&!flt.substring(3,6).equals(airport))){
						tzsr+=obj.getTotalNumber();
						tzkl+=obj.getPgs_Per_Cls();
//						if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
//							tzjc++;
//						}else{
//							tzjc+=2;
//						}
					}
					//不含
					if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
						//进出港
						nzsr+=obj.getTotalNumber();
						nzkl+=obj.getPgs_Per_Cls();
//						nzjc++;
					}
					//进港
					if(obj.getArrv_Airpt_Cd().equals(airport)){
						jzsr+=obj.getTotalNumber();
						jzkl+=obj.getPgs_Per_Cls();
//						jzjc++;
					}
					//出港
					if(obj.getDpt_AirPt_Cd().equals(airport)){
						czsr+=obj.getTotalNumber();
						czkl+=obj.getPgs_Per_Cls();
//						czjc++;
					}
					long currTime = obj.getLcl_Dpt_Day().getTime();
					if(startTime<=currTime&&currTime<=endTime){//七天
						//汇总
						//甩飞过站
						zsr7+=obj.getTotalNumber();
						zkl7+=obj.getPgs_Per_Cls();
//						if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
//							//进出港
//							zjc7++;
//						}else{
//							zjc7+=2;//过站甩飞加2
//						}
						//过站
						if((obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport))||(flt.length()==9&&flt.substring(3,6).equals(airport))){
							pzsr7+=obj.getTotalNumber();
							pzkl7+=obj.getPgs_Per_Cls();
//							if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
//								pzjc7++;
//							}else{
//								pzjc7+=2;
//							}
						}
						//甩飞
						if((obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport))||
								(flt.length()==9&&!flt.substring(3,6).equals(airport))){
							tzsr7+=obj.getTotalNumber();
							tzkl7+=obj.getPgs_Per_Cls();
//							if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
//								tzjc7++;
//							}else{
//								tzjc7+=2;
//							}
						}
						//不含
						if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
							//进出港
							nzsr7+=obj.getTotalNumber();
							nzkl7+=obj.getPgs_Per_Cls();
//							nzjc7++;
						}
						//进港
						if(obj.getArrv_Airpt_Cd().equals(airport)){
							jzsr7+=obj.getTotalNumber();
							jzkl7+=obj.getPgs_Per_Cls();
//							jzjc7++;
						}
						//出港
						if(obj.getDpt_AirPt_Cd().equals(airport)){
							czsr7+=obj.getTotalNumber();
							czkl7+=obj.getPgs_Per_Cls();
//							czjc7++;
						}
					}
				}
				//30天各种架次变量
				int zjc_30=0;
				int pzjc_30=0;
				int tzjc_30=0;
				int nzjc_30=0;
				int jzjc_30=0;
				int czjc_30=0;
				//7天各种架次变量
				int zjc_7=0;
				int pzjc_7=0;
				int tzjc_7=0;
				int nzjc_7=0;
				int jzjc_7=0;
				int czjc_7=0;
				//计算总架次
				for(int i=0;i<list.size();i++){
					Z_Airdata obj = list.get(i);
					String flt=obj.getFlt_Rte_Cd();
					if(i==0){
						if(flt.length()==9){
							if(flt.substring(3, 6).equals(airport)){//经停
								if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
									pzjc_30++;
								}
							}else if(flt.subSequence(0, 3).equals(airport)){//起点
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									tzjc_30++;
								}
							}else{//终点
								if(obj.getArrv_Airpt_Cd().equals(airport)){
									tzjc_30++;
								}
							}
						}else{//直飞
							zjc_30++;
							pzjc_30++;
							tzjc_30++;
							nzjc_30++;
						}
						//进港
						if(obj.getArrv_Airpt_Cd().equals(airport)){
							jzjc_30++;
						}
						//出港
						if(obj.getDpt_AirPt_Cd().equals(airport)){
							czjc_30++;
						}
						//7天数据
						long currTime = obj.getLcl_Dpt_Day().getTime();
						if(startTime<=currTime&&currTime<=endTime){
							if(flt.length()==9){
								if(flt.substring(3, 6).equals(airport)){//经停
									if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
										pzjc_7++;
									}
								}else if(flt.subSequence(0, 3).equals(airport)){//起点
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										tzjc_7++;
									}
								}else{//终点
									if(obj.getArrv_Airpt_Cd().equals(airport)){
										tzjc_7++;
									}
								}
							}else{//直飞
								zjc_7++;
								pzjc_7++;
								tzjc_7++;
								nzjc_7++;
							}
							//进港
							if(obj.getArrv_Airpt_Cd().equals(airport)){
								jzjc_7++;
							}
							//出港
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								czjc_7++;
							}
						}
					}else if(i!=list.size()-1){
						Z_Airdata oldObj=list.get(i-1);
						String oldFlt = oldObj.getFlt_Rte_Cd();
						if(oldObj.getFlt_Nbr().equals(obj.getFlt_Nbr())){//相同航班号
							if(oldObj.getLcl_Dpt_Day().equals(obj.getLcl_Dpt_Day())){
								if(oldFlt.equals(flt)){
									if(flt.length()==9){
										if(flt.substring(3, 6).equals(airport)){//经停
											if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
												pzjc_30++;
											}
										}else if(flt.subSequence(0, 3).equals(airport)){//起点
											if(obj.getDpt_AirPt_Cd().equals(airport)){
												tzjc_30++;
											}
										}else{//终点
											if(obj.getArrv_Airpt_Cd().equals(airport)){
												tzjc_30++;
											}
										}
									}else{//直飞
										zjc_30++;
										pzjc_30++;
										tzjc_30++;
										nzjc_30++;
									}
									//进港
									if(obj.getArrv_Airpt_Cd().equals(airport)){
										jzjc_30++;
									}
									//出港
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										czjc_30++;
									}
									//7天数据
									long currTime = obj.getLcl_Dpt_Day().getTime();
									if(startTime<=currTime&&currTime<=endTime){
										if(flt.length()==9){
											if(flt.substring(3, 6).equals(airport)){//经停
												if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
													pzjc_7++;
												}
											}else if(flt.subSequence(0, 3).equals(airport)){//起点
												if(obj.getDpt_AirPt_Cd().equals(airport)){
													tzjc_7++;
												}
											}else{//终点
												if(obj.getArrv_Airpt_Cd().equals(airport)){
													tzjc_7++;
												}
											}
										}else{//直飞
											zjc_7++;
											pzjc_7++;
											tzjc_7++;
											nzjc_7++;
										}
										//进港
										if(obj.getArrv_Airpt_Cd().equals(airport)){
											jzjc_7++;
										}
										//出港
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											czjc_7++;
										}
									}
								}else{
									if(oldFlt.length()==9){
										if(oldFlt.substring(3, 6).equals(airport)){//经停
											zjc+=pzjc_30;
											pzjc+=pzjc_30;
											tzjc+=pzjc_30;
											nzjc+=pzjc_30;
											czjc+=czjc_30;
											jzjc+=jzjc_30;
										}else if(oldFlt.substring(0, 3).equals(airport)){//起点
											zjc+=(int)Math.ceil(tzjc_30/2.0);
											pzjc+=(int)Math.ceil(tzjc_30/2.0);
											nzjc+=(int)Math.ceil(tzjc_30/2.0);
											tzjc+=(int)Math.ceil(tzjc_30/2.0);
											czjc+=(int)Math.ceil(czjc_30/2.0);
										}else if(oldFlt.substring(6).equals(airport)){//终点
											zjc+=(int)Math.ceil(tzjc_30/2.0);
											nzjc+=(int)Math.ceil(tzjc_30/2.0);
											pzjc+=(int)Math.ceil(tzjc_30/2.0);
											tzjc+=(int)Math.ceil(tzjc_30/2.0);
											jzjc+=(int)Math.ceil(jzjc_30/2.0);
										}
									}else if(oldFlt.length()==6){
										zjc+=zjc_30;
										pzjc+=pzjc_30;
										tzjc+=tzjc_30;
										nzjc+=nzjc_30;
										czjc+=czjc_30;
										jzjc+=jzjc_30;
									}
									long oldTime=oldObj.getLcl_Dpt_Day().getTime();
									if(startTime<=oldTime&&oldTime<=endTime){
										if(oldFlt.length()==9){
											if(oldFlt.substring(3, 6).equals(airport)){//经停
												zjc7+=pzjc_7;
												pzjc7+=pzjc_7;
												tzjc7+=pzjc_7;
												nzjc7+=pzjc_7;
												czjc7+=czjc_7;
												jzjc7+=jzjc_7;
											}else if(oldFlt.substring(0, 3).equals(airport)){//起点
												zjc7+=(int)Math.ceil(tzjc_7/2.0);
												pzjc7+=(int)Math.ceil(tzjc_7/2.0);
												nzjc7+=(int)Math.ceil(tzjc_7/2.0);
												tzjc7+=(int)Math.ceil(tzjc_7/2.0);
												czjc7+=(int)Math.ceil(czjc_7/2.0);
											}else if(oldFlt.substring(6).equals(airport)){//终点
												zjc7+=(int)Math.ceil(tzjc_7/2.0);
												tzjc7+=(int)Math.ceil(tzjc_7/2.0);
												nzjc7+=(int)Math.ceil(tzjc_7/2.0);
												pzjc7+=(int)Math.ceil(tzjc_7/2.0);
												jzjc7+=(int)Math.ceil(jzjc_7/2.0);
											}
										}else if(oldFlt.length()==6){
											zjc7+=zjc_7;
											pzjc7+=pzjc_7;
											tzjc7+=tzjc_7;
											nzjc7+=nzjc_7;
											czjc7+=czjc_7;
											jzjc7+=jzjc_7;
										}
									}
									zjc_30=0;
									pzjc_30=0;
									tzjc_30=0;
									nzjc_30=0;
									jzjc_30=0;
									czjc_30=0;
									zjc_7=0;
									pzjc_7=0;
									tzjc_7=0;
									nzjc_7=0;
									jzjc_7=0;
									czjc_7=0;
									//新数据添加
									if(flt.length()==9){
										if(flt.substring(3, 6).equals(airport)){//经停
											if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
												pzjc_30++;
											}
										}else if(flt.subSequence(0, 3).equals(airport)){//起点
											if(obj.getDpt_AirPt_Cd().equals(airport)){
												tzjc_30++;
											}
										}else{//终点
											if(obj.getArrv_Airpt_Cd().equals(airport)){
												tzjc_30++;
											}
										}
									}else{//直飞
										zjc_30++;
										pzjc_30++;
										tzjc_30++;
										nzjc_30++;
									}
									//进港
									if(obj.getArrv_Airpt_Cd().equals(airport)){
										jzjc_30++;
									}
									//出港
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										czjc_30++;
									}
									//7天数据
									long currTime = obj.getLcl_Dpt_Day().getTime();
									if(startTime<=currTime&&currTime<=endTime){
										if(flt.length()==9){
											if(flt.substring(3, 6).equals(airport)){//经停
												if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
													pzjc_7++;
												}
											}else if(flt.subSequence(0, 3).equals(airport)){//起点
												if(obj.getDpt_AirPt_Cd().equals(airport)){
													tzjc_7++;
												}
											}else{//终点
												if(obj.getArrv_Airpt_Cd().equals(airport)){
													tzjc_7++;
												}
											}
										}else{//直飞
											zjc_7++;
											pzjc_7++;
											tzjc_7++;
											nzjc_7++;
										}
										//进港
										if(obj.getArrv_Airpt_Cd().equals(airport)){
											jzjc_7++;
										}
										//出港
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											czjc_7++;
										}
									}
								}
							}else{
								if(oldFlt.length()==9){
									if(oldFlt.substring(3, 6).equals(airport)){//经停
										zjc+=pzjc_30;
										pzjc+=pzjc_30;
										nzjc+=pzjc_30;
										tzjc+=pzjc_30;
										czjc+=czjc_30;
										jzjc+=jzjc_30;
									}else if(oldFlt.substring(0, 3).equals(airport)){//起点
										zjc+=(int)Math.ceil(tzjc_30/2.0);
										tzjc+=(int)Math.ceil(tzjc_30/2.0);
										nzjc+=(int)Math.ceil(tzjc_30/2.0);
										pzjc+=(int)Math.ceil(tzjc_30/2.0);
										czjc+=(int)Math.ceil(czjc_30/2.0);
									}else if(oldFlt.substring(6).equals(airport)){//终点
										zjc+=(int)Math.ceil(tzjc_30/2.0);
										tzjc+=(int)Math.ceil(tzjc_30/2.0);
										nzjc+=(int)Math.ceil(tzjc_30/2.0);
										pzjc+=(int)Math.ceil(tzjc_30/2.0);
										jzjc+=(int)Math.ceil(jzjc_30/2.0);
									}
								}else if(oldFlt.length()==6){
									zjc+=zjc_30;
									pzjc+=pzjc_30;
									tzjc+=tzjc_30;
									nzjc+=nzjc_30;
									czjc+=czjc_30;
									jzjc+=jzjc_30;
								}
								long oldTime=oldObj.getLcl_Dpt_Day().getTime();
								if(startTime<=oldTime&&oldTime<=endTime){
									if(oldFlt.length()==9){
										if(oldFlt.substring(3, 6).equals(airport)){//经停
											zjc7+=pzjc_7;
											pzjc7+=pzjc_7;
											nzjc7+=pzjc_7;
											tzjc7+=pzjc_7;
											czjc7+=czjc_7;
											jzjc7+=jzjc_7;
										}else if(oldFlt.substring(0, 3).equals(airport)){//起点
											zjc7+=(int)Math.ceil(tzjc_7/2.0);
											tzjc7+=(int)Math.ceil(tzjc_7/2.0);
											pzjc7+=(int)Math.ceil(tzjc_7/2.0);
											nzjc7+=(int)Math.ceil(tzjc_7/2.0);
											czjc7+=(int)Math.ceil(czjc_7/2.0);
										}else if(oldFlt.substring(6).equals(airport)){//终点
											zjc7+=(int)Math.ceil(tzjc_7/2.0);
											pzjc7+=(int)Math.ceil(tzjc_7/2.0);
											tzjc7+=(int)Math.ceil(tzjc_7/2.0);
											nzjc7+=(int)Math.ceil(tzjc_7/2.0);
											jzjc7+=(int)Math.ceil(jzjc_7/2.0);
										}
									}else if(oldFlt.length()==6){
										zjc7+=zjc_7;
										pzjc7+=pzjc_7;
										tzjc7+=tzjc_7;
										nzjc7+=nzjc_7;
										czjc7+=czjc_7;
										jzjc7+=jzjc_7;
									}
								}
								zjc_30=0;
								pzjc_30=0;
								tzjc_30=0;
								nzjc_30=0;
								jzjc_30=0;
								czjc_30=0;
								zjc_7=0;
								pzjc_7=0;
								tzjc_7=0;
								nzjc_7=0;
								jzjc_7=0;
								czjc_7=0;
								//新数据添加
								if(flt.length()==9){
									if(flt.substring(3, 6).equals(airport)){//经停
										if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
											pzjc_30++;
										}
									}else if(flt.subSequence(0, 3).equals(airport)){//起点
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											tzjc_30++;
										}
									}else{//终点
										if(obj.getArrv_Airpt_Cd().equals(airport)){
											tzjc_30++;
										}
									}
								}else{//直飞
									zjc_30++;
									pzjc_30++;
									tzjc_30++;
									nzjc_30++;
								}
								//进港
								if(obj.getArrv_Airpt_Cd().equals(airport)){
									jzjc_30++;
								}
								//出港
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									czjc_30++;
								}
								//7天数据
								long currTime = obj.getLcl_Dpt_Day().getTime();
								if(startTime<=currTime&&currTime<=endTime){
									if(flt.length()==9){
										if(flt.substring(3, 6).equals(airport)){//经停
											if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
												pzjc_7++;
											}
										}else if(flt.subSequence(0, 3).equals(airport)){//起点
											if(obj.getDpt_AirPt_Cd().equals(airport)){
												tzjc_7++;
											}
										}else{//终点
											if(obj.getArrv_Airpt_Cd().equals(airport)){
												tzjc_7++;
											}
										}
									}else{//直飞
										zjc_7++;
										pzjc_7++;
										tzjc_7++;
										nzjc_7++;
									}
									//进港
									if(obj.getArrv_Airpt_Cd().equals(airport)){
										jzjc_7++;
									}
									//出港
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										czjc_7++;
									}
								}
							}
						}else{
							if(oldFlt.length()==9){
								if(oldFlt.substring(3, 6).equals(airport)){//经停
									zjc+=pzjc_30;
									pzjc+=pzjc_30;
									tzjc+=pzjc_30;
									nzjc+=pzjc_30;
									czjc+=czjc_30;
									jzjc+=jzjc_30;
								}else if(oldFlt.substring(0, 3).equals(airport)){//起点
									zjc+=(int)Math.ceil(tzjc_30/2.0);
									tzjc+=(int)Math.ceil(tzjc_30/2.0);
									pzjc+=(int)Math.ceil(tzjc_30/2.0);
									nzjc+=(int)Math.ceil(tzjc_30/2.0);
									czjc+=(int)Math.ceil(czjc_30/2.0);
								}else if(oldFlt.substring(6).equals(airport)){//终点
									zjc+=(int)Math.ceil(tzjc_30/2.0);
									tzjc+=(int)Math.ceil(tzjc_30/2.0);
									nzjc+=(int)Math.ceil(tzjc_30/2.0);
									pzjc+=(int)Math.ceil(tzjc_30/2.0);
									jzjc+=(int)Math.ceil(jzjc_30/2.0);
								}
							}else if(oldFlt.length()==6){
								zjc+=zjc_30;
								pzjc+=pzjc_30;
								tzjc+=tzjc_30;
								nzjc+=nzjc_30;
								czjc+=czjc_30;
								jzjc+=jzjc_30;
							}
							long oldTime=oldObj.getLcl_Dpt_Day().getTime();
							if(startTime<=oldTime&&oldTime<=endTime){
								if(oldFlt.length()==9){
									if(oldFlt.substring(3, 6).equals(airport)){//经停
										zjc7+=pzjc_7;
										pzjc7+=pzjc_7;
										tzjc7+=pzjc_7;
										nzjc7+=pzjc_7;
										czjc7+=czjc_7;
										jzjc7+=jzjc_7;
									}else if(oldFlt.substring(0, 3).equals(airport)){//起点
										zjc7+=(int)Math.ceil(tzjc_7/2.0);
										tzjc7+=(int)Math.ceil(tzjc_7/2.0);
										pzjc7+=(int)Math.ceil(tzjc_7/2.0);
										nzjc7+=(int)Math.ceil(tzjc_7/2.0);
										czjc7+=(int)Math.ceil(czjc_7/2.0);
									}else if(oldFlt.substring(6).equals(airport)){//终点
										zjc7+=(int)Math.ceil(tzjc_7/2.0);
										tzjc7+=(int)Math.ceil(tzjc_7/2.0);
										pzjc7+=(int)Math.ceil(tzjc_7/2.0);
										nzjc7+=(int)Math.ceil(tzjc_7/2.0);
										jzjc7+=(int)Math.ceil(jzjc_7/2.0);
									}
								}else if(oldFlt.length()==6){
									zjc7+=zjc_7;
									pzjc7+=pzjc_7;
									tzjc7+=tzjc_7;
									nzjc7+=nzjc_7;
									czjc7+=czjc_7;
									jzjc7+=jzjc_7;
								}
							}
							zjc_30=0;
							pzjc_30=0;
							tzjc_30=0;
							nzjc_30=0;
							jzjc_30=0;
							czjc_30=0;
							zjc_7=0;
							pzjc_7=0;
							tzjc_7=0;
							nzjc_7=0;
							jzjc_7=0;
							czjc_7=0;
							//新数据添加
							if(flt.length()==9){
								if(flt.substring(3, 6).equals(airport)){//经停
									if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
										pzjc_30++;
									}
								}else if(flt.subSequence(0, 3).equals(airport)){//起点
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										tzjc_30++;
									}
								}else{//终点
									if(obj.getArrv_Airpt_Cd().equals(airport)){
										tzjc_30++;
									}
								}
							}else{//直飞
								zjc_30++;
								pzjc_30++;
								tzjc_30++;
								nzjc_30++;
							}
							//进港
							if(obj.getArrv_Airpt_Cd().equals(airport)){
								jzjc_30++;
							}
							//出港
							if(obj.getDpt_AirPt_Cd().equals(airport)){
								czjc_30++;
							}
							//7天数据
							long currTime = obj.getLcl_Dpt_Day().getTime();
							if(startTime<=currTime&&currTime<=endTime){
								if(flt.length()==9){
									if(flt.substring(3, 6).equals(airport)){//经停
										if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
											pzjc_7++;
										}
									}else if(flt.subSequence(0, 3).equals(airport)){//起点
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											tzjc_7++;
										}
									}else{//终点
										if(obj.getArrv_Airpt_Cd().equals(airport)){
											tzjc_7++;
										}
									}
								}else{//直飞
									zjc_7++;
									pzjc_7++;
									tzjc_7++;
									nzjc_7++;
								}
								//进港
								if(obj.getArrv_Airpt_Cd().equals(airport)){
									jzjc_7++;
								}
								//出港
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									czjc_7++;
								}
							}
						}
					}else{
						Z_Airdata oldObj=list.get(i-1);
						String oldFlt = oldObj.getFlt_Rte_Cd();
						if(oldObj.getFlt_Nbr().equals(obj.getFlt_Nbr())){//相同航班号
							if(oldObj.getLcl_Dpt_Day().equals(obj.getLcl_Dpt_Day())){
								if(oldFlt.equals(flt)){
									if(flt.length()==9){
										if(flt.substring(3, 6).equals(airport)){//经停
											if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
												pzjc_30++;
												//进港
												if(obj.getArrv_Airpt_Cd().equals(airport)){
													jzjc_30++;
												}
												//出港
												if(obj.getDpt_AirPt_Cd().equals(airport)){
													czjc_30++;
												}
												zjc+=pzjc_30;
												pzjc+=pzjc_30;
												tzjc+=pzjc_30;
												nzjc+=pzjc_30;
												czjc+=czjc_30;
												jzjc+=jzjc_30;
											}
										}else if(flt.subSequence(0, 3).equals(airport)){//起点
											if(obj.getDpt_AirPt_Cd().equals(airport)){
												tzjc_30++;
												czjc_30++;
												zjc+=(int)Math.ceil(tzjc_30/2.0);
												tzjc+=(int)Math.ceil(tzjc_30/2.0);
												pzjc+=(int)Math.ceil(tzjc_30/2.0);
												nzjc+=(int)Math.ceil(tzjc_30/2.0);
												czjc+=(int)Math.ceil(czjc_30/2.0);
											}
										}else{//终点
											if(obj.getArrv_Airpt_Cd().equals(airport)){
												tzjc_30++;
												//进港
												jzjc_30++;
												zjc+=(int)Math.ceil(tzjc_30/2.0);
												tzjc+=(int)Math.ceil(tzjc_30/2.0);
												pzjc+=(int)Math.ceil(tzjc_30/2.0);
												nzjc+=(int)Math.ceil(tzjc_30/2.0);
												jzjc+=(int)Math.ceil(jzjc_30/2.0);
											}
										}
									}else{//直飞
										zjc_30++;
										pzjc_30++;
										tzjc_30++;
										nzjc_30++;
										//进港
										if(obj.getArrv_Airpt_Cd().equals(airport)){
											jzjc_30++;
										}
										//出港
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											czjc_30++;
										}
										zjc+=zjc_30;
										pzjc+=pzjc_30;
										tzjc+=tzjc_30;
										nzjc+=nzjc_30;
										czjc+=czjc_30;
										jzjc+=jzjc_30;
									}
									//7天数据
									long currTime = obj.getLcl_Dpt_Day().getTime();
									if(startTime<=currTime&&currTime<=endTime){
										if(flt.length()==9){
											if(flt.substring(3, 6).equals(airport)){//经停
												if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
													pzjc_7++;
													//进港
													if(obj.getArrv_Airpt_Cd().equals(airport)){
														jzjc_7++;
													}
													//出港
													if(obj.getDpt_AirPt_Cd().equals(airport)){
														czjc_7++;
													}
													zjc7+=pzjc_7;
													pzjc7+=pzjc_7;
													tzjc7+=pzjc_7;
													nzjc7+=pzjc_7;
													czjc7+=czjc_7;
													jzjc7+=jzjc_7;
												}
											}else if(flt.subSequence(0, 3).equals(airport)){//起点
												if(obj.getDpt_AirPt_Cd().equals(airport)){
													tzjc_7++;
													//出港
													czjc_7++;
													zjc7+=(int)Math.ceil(tzjc_7/2.0);
													tzjc7+=(int)Math.ceil(tzjc_7/2.0);
													pzjc7+=(int)Math.ceil(tzjc_7/2.0);
													nzjc7+=(int)Math.ceil(tzjc_7/2.0);
													czjc7+=(int)Math.ceil(czjc_7/2.0);
												}
											}else{//终点
												if(obj.getArrv_Airpt_Cd().equals(airport)){
													tzjc_7++;
													//进港
													jzjc_7++;
													zjc7+=(int)Math.ceil(tzjc_7/2.0);
													tzjc7+=(int)Math.ceil(tzjc_7/2.0);
													pzjc7+=(int)Math.ceil(tzjc_7/2.0);
													nzjc7+=(int)Math.ceil(tzjc_7/2.0);
													jzjc7+=(int)Math.ceil(jzjc_7/2.0);
												}
											}
										}else{//直飞
											zjc_7++;
											pzjc_7++;
											tzjc_7++;
											nzjc_7++;
											//进港
											if(obj.getArrv_Airpt_Cd().equals(airport)){
												jzjc_7++;
											}
											//出港
											if(obj.getDpt_AirPt_Cd().equals(airport)){
												czjc_7++;
											}
											zjc7+=zjc_7;
											pzjc7+=pzjc_7;
											tzjc7+=tzjc_7;
											nzjc7+=nzjc_7;
											czjc7+=czjc_7;
											jzjc7+=jzjc_7;
										}
									}
								}else{
									if(oldFlt.length()==9){
										if(oldFlt.substring(3, 6).equals(airport)){//经停
											zjc+=pzjc_30;
											pzjc+=pzjc_30;
											tzjc+=pzjc_30;
											nzjc+=pzjc_30;
											czjc+=czjc_30;
											jzjc+=jzjc_30;
										}else if(oldFlt.substring(0, 3).equals(airport)){//起点
											zjc+=(int)Math.ceil(tzjc_30/2.0);
											tzjc+=(int)Math.ceil(tzjc_30/2.0);
											pzjc+=(int)Math.ceil(tzjc_30/2.0);
											nzjc+=(int)Math.ceil(tzjc_30/2.0);
											czjc+=(int)Math.ceil(czjc_30/2.0);
										}else if(oldFlt.substring(6).equals(airport)){//终点
											zjc+=(int)Math.ceil(tzjc_30/2.0);
											tzjc+=(int)Math.ceil(tzjc_30/2.0);
											pzjc+=(int)Math.ceil(tzjc_30/2.0);
											nzjc+=(int)Math.ceil(tzjc_30/2.0);
											jzjc+=(int)Math.ceil(jzjc_30/2.0);
										}
									}else if(oldFlt.length()==6){
										zjc+=zjc_30;
										pzjc+=pzjc_30;
										tzjc+=tzjc_30;
										nzjc+=nzjc_30;
										czjc+=czjc_30;
										jzjc+=jzjc_30;
									}
									long oldTime=oldObj.getLcl_Dpt_Day().getTime();
									if(startTime<=oldTime&&oldTime<=endTime){
										if(oldFlt.length()==9){
											if(oldFlt.substring(3, 6).equals(airport)){//经停
												zjc7+=pzjc_7;
												pzjc7+=pzjc_7;
												tzjc7+=pzjc_7;
												nzjc7+=pzjc_7;
												czjc7+=czjc_7;
												jzjc7+=jzjc_7;
											}else if(oldFlt.substring(0, 3).equals(airport)){//起点
												zjc7+=(int)Math.ceil(tzjc_7/2.0);
												tzjc7+=(int)Math.ceil(tzjc_7/2.0);
												pzjc7+=(int)Math.ceil(tzjc_7/2.0);
												nzjc7+=(int)Math.ceil(tzjc_7/2.0);
												czjc7+=(int)Math.ceil(czjc_7/2.0);
											}else if(oldFlt.substring(6).equals(airport)){//终点
												zjc7+=(int)Math.ceil(tzjc_7/2.0);
												tzjc7+=(int)Math.ceil(tzjc_7/2.0);
												pzjc7+=(int)Math.ceil(tzjc_7/2.0);
												nzjc7+=(int)Math.ceil(tzjc_7/2.0);
												jzjc7+=(int)Math.ceil(jzjc_7/2.0);
											}
										}else if(oldFlt.length()==6){
											zjc7+=zjc_7;
											pzjc7+=pzjc_7;
											tzjc7+=tzjc_7;
											nzjc7+=nzjc_7;
											czjc7+=czjc_7;
											jzjc7+=jzjc_7;
										}
									}
									zjc_30=0;
									pzjc_30=0;
									tzjc_30=0;
									nzjc_30=0;
									jzjc_30=0;
									czjc_30=0;
									zjc_7=0;
									pzjc_7=0;
									tzjc_7=0;
									nzjc_7=0;
									jzjc_7=0;
									czjc_7=0;
									//新数据添加
									if(flt.length()==9){
										if(flt.substring(3, 6).equals(airport)){//经停
											if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
												pzjc_30++;
												//进港
												if(obj.getArrv_Airpt_Cd().equals(airport)){
													jzjc_30++;
												}
												//出港
												if(obj.getDpt_AirPt_Cd().equals(airport)){
													czjc_30++;
												}
												zjc+=pzjc_30;
												pzjc+=pzjc_30;
												tzjc+=pzjc_30;
												nzjc+=pzjc_30;
												czjc+=czjc_30;
												jzjc+=jzjc_30;
											}
										}else if(flt.subSequence(0, 3).equals(airport)){//起点
											if(obj.getDpt_AirPt_Cd().equals(airport)){
												tzjc_30++;
												//出港
												czjc_30++;
												zjc+=(int)Math.ceil(tzjc_30/2.0);
												tzjc+=(int)Math.ceil(tzjc_30/2.0);
												pzjc+=(int)Math.ceil(tzjc_30/2.0);
												nzjc+=(int)Math.ceil(tzjc_30/2.0);
												czjc+=(int)Math.ceil(czjc_30/2.0);
											}
										}else{//终点
											if(obj.getArrv_Airpt_Cd().equals(airport)){
												tzjc_30++;
												//进港
												jzjc_30++;
												zjc+=(int)Math.ceil(tzjc_30/2.0);
												tzjc+=(int)Math.ceil(tzjc_30/2.0);
												pzjc+=(int)Math.ceil(tzjc_30/2.0);
												nzjc+=(int)Math.ceil(tzjc_30/2.0);
												jzjc+=(int)Math.ceil(jzjc_30/2.0);
											}
										}
									}else{//直飞
										zjc_30++;
										pzjc_30++;
										tzjc_30++;
										nzjc_30++;
										//进港
										if(obj.getArrv_Airpt_Cd().equals(airport)){
											jzjc_30++;
										}
										//出港
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											czjc_30++;
										}
										zjc+=zjc_30;
										pzjc+=pzjc_30;
										tzjc+=tzjc_30;
										nzjc+=nzjc_30;
										czjc+=czjc_30;
										jzjc+=jzjc_30;
									}
									//7天数据
									long currTime = obj.getLcl_Dpt_Day().getTime();
									if(startTime<=currTime&&currTime<=endTime){
										if(flt.length()==9){
											if(flt.substring(3, 6).equals(airport)){//经停
												if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
													pzjc_7++;
													//进港
													if(obj.getArrv_Airpt_Cd().equals(airport)){
														jzjc_7++;
													}
													//出港
													if(obj.getDpt_AirPt_Cd().equals(airport)){
														czjc_7++;
													}
													zjc7+=pzjc_7;
													pzjc7+=pzjc_7;
													tzjc7+=pzjc_7;
													nzjc7+=pzjc_7;
													czjc7+=czjc_7;
													jzjc7+=jzjc_7;
												}
											}else if(flt.subSequence(0, 3).equals(airport)){//起点
												if(obj.getDpt_AirPt_Cd().equals(airport)){
													tzjc_7++;
													//出港
													czjc_7++;
													zjc7+=(int)Math.ceil(tzjc_7/2.0);
													tzjc7+=(int)Math.ceil(tzjc_7/2.0);
													pzjc7+=(int)Math.ceil(tzjc_7/2.0);
													nzjc7+=(int)Math.ceil(tzjc_7/2.0);
													czjc7+=(int)Math.ceil(czjc_7/2.0);
												}
											}else{//终点
												if(obj.getArrv_Airpt_Cd().equals(airport)){
													tzjc_7++;
													//进港
													jzjc_7++;
													zjc7+=(int)Math.ceil(tzjc_7/2.0);
													tzjc7+=(int)Math.ceil(tzjc_7/2.0);
													pzjc7+=(int)Math.ceil(tzjc_7/2.0);
													nzjc7+=(int)Math.ceil(tzjc_7/2.0);
													jzjc7+=(int)Math.ceil(jzjc_7/2.0);
												}
											}
										}else{//直飞
											zjc_7++;
											pzjc_7++;
											tzjc_7++;
											nzjc_7++;
											//进港
											if(obj.getArrv_Airpt_Cd().equals(airport)){
												jzjc_7++;
											}
											//出港
											if(obj.getDpt_AirPt_Cd().equals(airport)){
												czjc_7++;
											}
											zjc7+=zjc_7;
											pzjc7+=pzjc_7;
											tzjc7+=tzjc_7;
											nzjc7+=nzjc_7;
											czjc7+=czjc_7;
											jzjc7+=jzjc_7;
										}
									}
								}
							}else{
								if(oldFlt.length()==9){
									if(oldFlt.substring(3, 6).equals(airport)){//经停
										zjc+=pzjc_30;
										pzjc+=pzjc_30;
										tzjc+=pzjc_30;
										nzjc+=pzjc_30;
										czjc+=czjc_30;
										jzjc+=jzjc_30;
									}else if(oldFlt.substring(0, 3).equals(airport)){//起点
										zjc+=(int)Math.ceil(tzjc_30/2.0);
										tzjc+=(int)Math.ceil(tzjc_30/2.0);
										pzjc+=(int)Math.ceil(tzjc_30/2.0);
										nzjc+=(int)Math.ceil(tzjc_30/2.0);
										czjc+=(int)Math.ceil(czjc_30/2.0);
									}else if(oldFlt.substring(6).equals(airport)){//终点
										zjc+=(int)Math.ceil(tzjc_30/2.0);
										tzjc+=(int)Math.ceil(tzjc_30/2.0);
										pzjc+=(int)Math.ceil(tzjc_30/2.0);
										nzjc+=(int)Math.ceil(tzjc_30/2.0);
										jzjc+=(int)Math.ceil(jzjc_30/2.0);
									}
								}else if(oldFlt.length()==6){
									zjc+=zjc_30;
									pzjc+=pzjc_30;
									tzjc+=tzjc_30;
									nzjc+=nzjc_30;
									czjc+=czjc_30;
									jzjc+=jzjc_30;
								}
								long oldTime=oldObj.getLcl_Dpt_Day().getTime();
								if(startTime<=oldTime&&oldTime<=endTime){
									if(oldFlt.length()==9){
										if(oldFlt.substring(3, 6).equals(airport)){//经停
											zjc7+=pzjc_7;
											pzjc7+=pzjc_7;
											tzjc7+=pzjc_7;
											nzjc7+=pzjc_7;
											czjc7+=czjc_7;
											jzjc7+=jzjc_7;
										}else if(oldFlt.substring(0, 3).equals(airport)){//起点
											zjc7+=(int)Math.ceil(tzjc_7/2.0);
											tzjc7+=(int)Math.ceil(tzjc_7/2.0);
											pzjc7+=(int)Math.ceil(tzjc_7/2.0);
											nzjc7+=(int)Math.ceil(tzjc_7/2.0);
											czjc7+=(int)Math.ceil(czjc_7/2.0);
										}else if(oldFlt.substring(6).equals(airport)){//终点
											zjc7+=(int)Math.ceil(tzjc_7/2.0);
											tzjc7+=(int)Math.ceil(tzjc_7/2.0);
											pzjc7+=(int)Math.ceil(tzjc_7/2.0);
											nzjc7+=(int)Math.ceil(tzjc_7/2.0);
											jzjc7+=(int)Math.ceil(jzjc_7/2.0);
										}
									}else if(oldFlt.length()==6){
										zjc7+=zjc_7;
										pzjc7+=pzjc_7;
										tzjc7+=tzjc_7;
										nzjc7+=nzjc_7;
										czjc7+=czjc_7;
										jzjc7+=jzjc_7;
									}
								}
								zjc_30=0;
								pzjc_30=0;
								tzjc_30=0;
								nzjc_30=0;
								jzjc_30=0;
								czjc_30=0;
								zjc_7=0;
								pzjc_7=0;
								tzjc_7=0;
								nzjc_7=0;
								jzjc_7=0;
								czjc_7=0;
								//新数据添加
								if(flt.length()==9){
									if(flt.substring(3, 6).equals(airport)){//经停
										if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
											pzjc_30++;
											//进港
											if(obj.getArrv_Airpt_Cd().equals(airport)){
												jzjc_30++;
											}
											//出港
											if(obj.getDpt_AirPt_Cd().equals(airport)){
												czjc_30++;
											}
											zjc+=pzjc_30;
											pzjc+=pzjc_30;
											tzjc+=pzjc_30;
											nzjc+=pzjc_30;
											czjc+=czjc_30;
											jzjc+=jzjc_30;
										}
									}else if(flt.subSequence(0, 3).equals(airport)){//起点
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											tzjc_30++;
											//出港
											czjc_30++;
											zjc+=(int)Math.ceil(tzjc_30/2.0);
											tzjc+=(int)Math.ceil(tzjc_30/2.0);
											pzjc+=(int)Math.ceil(tzjc_30/2.0);
											nzjc+=(int)Math.ceil(tzjc_30/2.0);
											czjc+=(int)Math.ceil(czjc_30/2.0);
										}
									}else{//终点
										if(obj.getArrv_Airpt_Cd().equals(airport)){
											tzjc_30++;
											//进港
											jzjc_30++;
											zjc+=(int)Math.ceil(tzjc_30/2.0);
											tzjc+=(int)Math.ceil(tzjc_30/2.0);
											pzjc+=(int)Math.ceil(tzjc_30/2.0);
											nzjc+=(int)Math.ceil(tzjc_30/2.0);
											jzjc+=(int)Math.ceil(jzjc_30/2.0);
										}
									}
								}else{//直飞
									zjc_30++;
									pzjc_30++;
									tzjc_30++;
									nzjc_30++;
									//进港
									if(obj.getArrv_Airpt_Cd().equals(airport)){
										jzjc_30++;
									}
									//出港
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										czjc_30++;
									}
									zjc+=zjc_30;
									pzjc+=pzjc_30;
									tzjc+=tzjc_30;
									nzjc+=nzjc_30;
									czjc+=czjc_30;
									jzjc+=jzjc_30;
								}
								//7天数据
								long currTime = obj.getLcl_Dpt_Day().getTime();
								if(startTime<=currTime&&currTime<=endTime){
									if(flt.length()==9){
										if(flt.substring(3, 6).equals(airport)){//经停
											if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
												pzjc_7++;
												//进港
												if(obj.getArrv_Airpt_Cd().equals(airport)){
													jzjc_7++;
												}
												//出港
												if(obj.getDpt_AirPt_Cd().equals(airport)){
													czjc_7++;
												}
												zjc7+=pzjc_7;
												pzjc7+=pzjc_7;
												tzjc7+=pzjc_7;
												nzjc7+=pzjc_7;
												czjc7+=czjc_7;
												jzjc7+=jzjc_7;
											}
										}else if(flt.subSequence(0, 3).equals(airport)){//起点
											if(obj.getArrv_Airpt_Cd().equals(airport)){
												tzjc_7++;
												//出港
												czjc_7++;
												zjc7+=(int)Math.ceil(tzjc_7/2.0);
												tzjc7+=(int)Math.ceil(tzjc_7/2.0);
												pzjc7+=(int)Math.ceil(tzjc_7/2.0);
												nzjc7+=(int)Math.ceil(tzjc_7/2.0);
												czjc7+=(int)Math.ceil(czjc_7/2.0);
											}
										}else{//终点
											if(obj.getArrv_Airpt_Cd().equals(airport)){
												tzjc_7++;
												//进港
												jzjc_7++;
												zjc7+=(int)Math.ceil(tzjc_7/2.0);
												tzjc7+=(int)Math.ceil(tzjc_7/2.0);
												pzjc7+=(int)Math.ceil(tzjc_7/2.0);
												nzjc7+=(int)Math.ceil(tzjc_7/2.0);
												jzjc7+=(int)Math.ceil(jzjc_7/2.0);
											}
										}
									}else{//直飞
										zjc_7++;
										pzjc_7++;
										tzjc_7++;
										nzjc_7++;
										//进港
										if(obj.getArrv_Airpt_Cd().equals(airport)){
											jzjc_7++;
										}
										//出港
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											czjc_7++;
										}
										zjc7+=zjc_7;
										pzjc7+=pzjc_7;
										tzjc7+=tzjc_7;
										nzjc7+=nzjc_7;
										czjc7+=czjc_7;
										jzjc7+=jzjc_7;
									}
								}
							}
						}else{
							if(oldFlt.length()==9){
								if(oldFlt.substring(3, 6).equals(airport)){//经停
									zjc+=pzjc_30;
									pzjc+=pzjc_30;
									tzjc+=pzjc_30;
									nzjc+=pzjc_30;
									czjc+=czjc_30;
									jzjc+=jzjc_30;
								}else if(oldFlt.substring(0, 3).equals(airport)){//起点
									zjc+=(int)Math.ceil(tzjc_30/2.0);
									tzjc+=(int)Math.ceil(tzjc_30/2.0);
									pzjc+=(int)Math.ceil(tzjc_30/2.0);
									nzjc+=(int)Math.ceil(tzjc_30/2.0);
									czjc+=(int)Math.ceil(czjc_30/2.0);
								}else if(oldFlt.substring(6).equals(airport)){//终点
									zjc+=(int)Math.ceil(tzjc_30/2.0);
									tzjc+=(int)Math.ceil(tzjc_30/2.0);
									pzjc+=(int)Math.ceil(tzjc_30/2.0);
									nzjc+=(int)Math.ceil(tzjc_30/2.0);
									jzjc+=(int)Math.ceil(jzjc_30/2.0);
								}
							}else if(oldFlt.length()==6){
								zjc+=zjc_30;
								pzjc+=pzjc_30;
								tzjc+=tzjc_30;
								nzjc+=nzjc_30;
								czjc+=czjc_30;
								jzjc+=jzjc_30;
							}
							long oldTime=oldObj.getLcl_Dpt_Day().getTime();
							if(startTime<=oldTime&&oldTime<=endTime){
								if(oldFlt.length()==9){
									if(oldFlt.substring(3, 6).equals(airport)){//经停
										zjc7+=pzjc_7;
										pzjc7+=pzjc_7;
										tzjc7+=pzjc_7;
										nzjc7+=pzjc_7;
										czjc7+=czjc_7;
										jzjc7+=jzjc_7;
									}else if(oldFlt.substring(0, 3).equals(airport)){//起点
										zjc7+=(int)Math.ceil(tzjc_7/2.0);
										tzjc7+=(int)Math.ceil(tzjc_7/2.0);
										pzjc7+=(int)Math.ceil(tzjc_7/2.0);
										nzjc7+=(int)Math.ceil(tzjc_7/2.0);
										czjc7+=(int)Math.ceil(czjc_7/2.0);
									}else if(oldFlt.substring(6).equals(airport)){//终点
										zjc7+=(int)Math.ceil(tzjc_7/2.0);
										tzjc7+=(int)Math.ceil(tzjc_7/2.0);
										pzjc7+=(int)Math.ceil(tzjc_7/2.0);
										nzjc7+=(int)Math.ceil(tzjc_7/2.0);
										jzjc7+=(int)Math.ceil(jzjc_7/2.0);
									}
								}else if(oldFlt.length()==6){
									zjc7+=zjc_7;
									pzjc7+=pzjc_7;
									tzjc7+=tzjc_7;
									nzjc7+=nzjc_7;
									czjc7+=czjc_7;
									jzjc7+=jzjc_7;
								}
							}
							zjc_30=0;
							pzjc_30=0;
							tzjc_30=0;
							nzjc_30=0;
							jzjc_30=0;
							czjc_30=0;
							zjc_7=0;
							pzjc_7=0;
							tzjc_7=0;
							nzjc_7=0;
							jzjc_7=0;
							czjc_7=0;
							//新数据添加
							if(flt.length()==9){
								if(flt.substring(3, 6).equals(airport)){//经停
									if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
										pzjc_30++;
										//进港
										if(obj.getArrv_Airpt_Cd().equals(airport)){
											jzjc_30++;
										}
										//出港
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											czjc_30++;
										}
										zjc+=pzjc_30;
										pzjc+=pzjc_30;
										tzjc+=pzjc_30;
										nzjc+=pzjc_30;
										czjc+=czjc_30;
										jzjc+=jzjc_30;
									}
								}else if(flt.subSequence(0, 3).equals(airport)){//起点
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										tzjc_30++;
										//出港
										czjc_30++;
										zjc+=(int)Math.ceil(tzjc_30/2.0);
										tzjc+=(int)Math.ceil(tzjc_30/2.0);
										pzjc+=(int)Math.ceil(tzjc_30/2.0);
										nzjc+=(int)Math.ceil(tzjc_30/2.0);
										czjc+=(int)Math.ceil(czjc_30/2.0);
									}
								}else{//终点
									if(obj.getArrv_Airpt_Cd().equals(airport)){
										tzjc_30++;
										//进港
										jzjc_30++;
										zjc+=(int)Math.ceil(tzjc_30/2.0);
										tzjc+=(int)Math.ceil(tzjc_30/2.0);
										pzjc+=(int)Math.ceil(tzjc_30/2.0);
										nzjc+=(int)Math.ceil(tzjc_30/2.0);
										jzjc+=(int)Math.ceil(jzjc_30/2.0);
									}
								}
							}else{//直飞
								zjc_30++;
								pzjc_30++;
								tzjc_30++;
								nzjc_30++;
								//进港
								if(obj.getArrv_Airpt_Cd().equals(airport)){
									jzjc_30++;
								}
								//出港
								if(obj.getDpt_AirPt_Cd().equals(airport)){
									czjc_30++;
								}
								zjc+=zjc_30;
								pzjc+=pzjc_30;
								tzjc+=tzjc_30;
								nzjc+=nzjc_30;
								czjc+=czjc_30;
								jzjc+=jzjc_30;
							}
							//7天数据
							long currTime = obj.getLcl_Dpt_Day().getTime();
							if(startTime<=currTime&&currTime<=endTime){
								if(flt.length()==9){
									if(flt.substring(3, 6).equals(airport)){//经停
										if(obj.getDpt_AirPt_Cd().equals(airport)||obj.getArrv_Airpt_Cd().equals(airport)){
											pzjc_7++;
											//进港
											if(obj.getArrv_Airpt_Cd().equals(airport)){
												jzjc_7++;
											}
											//出港
											if(obj.getDpt_AirPt_Cd().equals(airport)){
												czjc_7++;
											}
											zjc7+=pzjc_7;
											pzjc7+=pzjc_7;
											tzjc7+=pzjc_7;
											nzjc7+=pzjc_7;
											czjc7+=czjc_7;
											jzjc7+=jzjc_7;
										}
									}else if(flt.subSequence(0, 3).equals(airport)){//起点
										if(obj.getDpt_AirPt_Cd().equals(airport)){
											tzjc_7++;
											//出港
											czjc_7++;
											zjc7+=(int)Math.ceil(tzjc_7/2.0);
											tzjc7+=(int)Math.ceil(tzjc_7/2.0);
											pzjc7+=(int)Math.ceil(tzjc_7/2.0);
											nzjc7+=(int)Math.ceil(tzjc_7/2.0);
											czjc7+=(int)Math.ceil(czjc_7/2.0);
										}
									}else{//终点
										if(obj.getArrv_Airpt_Cd().equals(airport)){
											tzjc_7++;
											//进港
											jzjc_7++;
											zjc7+=(int)Math.ceil(tzjc_7/2.0);
											tzjc7+=(int)Math.ceil(tzjc_7/2.0);
											pzjc7+=(int)Math.ceil(tzjc_7/2.0);
											nzjc7+=(int)Math.ceil(tzjc_7/2.0);
											jzjc7+=(int)Math.ceil(jzjc_7/2.0);
										}
									}
								}else{//直飞
									zjc_7++;
									pzjc_7++;
									tzjc_7++;
									nzjc_7++;
									//进港
									if(obj.getArrv_Airpt_Cd().equals(airport)){
										jzjc_7++;
									}
									//出港
									if(obj.getDpt_AirPt_Cd().equals(airport)){
										czjc_7++;
									}
									zjc7+=zjc_7;
									pzjc7+=pzjc_7;
									tzjc7+=tzjc_7;
									nzjc7+=nzjc_7;
									czjc7+=czjc_7;
									jzjc7+=jzjc_7;
								}
							}
						}
					}
				}
				PortalData data=new PortalData();
				data.setZsr(zsr);
				data.setZkl(zkl);
				data.setZjc(zjc);
				data.setPzsr(pzsr);
				data.setPzkl(pzkl);
				data.setPzjc(pzjc);
				data.setTzsr(tzsr);
				data.setTzkl(tzkl);
				data.setTzjc(tzjc);
				data.setNzsr(nzsr);
				data.setNzkl(nzkl);
				data.setNzjc(nzjc);
				data.setJzsr(jzsr);
				data.setJzkl(jzkl);
				data.setJzjc(jzjc);
				data.setCzsr(czsr);
				data.setCzkl(czkl);
				data.setCzjc(czjc);
				PortalData data7=new PortalData();
				data7.setZsr(zsr7);
				data7.setZkl(zkl7);
				data7.setZjc(zjc7);
				data7.setPzsr(pzsr7);
				data7.setPzkl(pzkl7);
				data7.setPzjc(pzjc7);
				data7.setTzsr(tzsr7);
				data7.setTzkl(tzkl7);
				data7.setTzjc(tzjc7);
				data7.setNzsr(nzsr7);
				data7.setNzkl(nzkl7);
				data7.setNzjc(nzjc7);
				data7.setJzsr(jzsr7);
				data7.setJzkl(jzkl7);
				data7.setJzjc(jzjc7);
				data7.setCzsr(czsr7);
				data7.setCzkl(czkl7);
				data7.setCzjc(czjc7);
				List<PortalData> portalDatas=new ArrayList<PortalData>();
				portalDatas.add(data);
				portalDatas.add(data7);
				map.put("portalDatas", portalDatas);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("server is busying");
			return null;
		}
		return map;
	}
	
	public String getNewDate(List<String> airports){
		String newDate = "";
		try {
			for(String airport:airports){
				String date = objMapper.getNewDate(airport);
				if(date!=null&&!"".equals(date)){
					if("".equals(newDate)){
						newDate=date;
					}else{
						if(date.compareTo(newDate)>0){
							/**
							 * date.compareTo(newDate)
							 * 当date与newDate相等，返回0
							 * 当date比newDate大，返回正整数
							 * 当date比newDate小，返回负整数
							 */
							newDate=date;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return newDate;
		}
		return newDate;
	}
	public static void main(String[] args) {
		String date = "2015-04-13";
		String newDate = "2017-08-06";
		System.out.println(newDate.compareTo(date));
	}

	
}
