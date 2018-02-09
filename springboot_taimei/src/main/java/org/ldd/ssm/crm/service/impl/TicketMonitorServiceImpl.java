package org.ldd.ssm.crm.service.impl;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.ldd.ssm.crm.domain.FlightManage;
import org.ldd.ssm.crm.domain.FlightPriceComp;
import org.ldd.ssm.crm.domain.GuestrateFlpj;
import org.ldd.ssm.crm.domain.TKTIncome;
import org.ldd.ssm.crm.domain.WatchFlight;
import org.ldd.ssm.crm.mapper.FlightManageMapper;
import org.ldd.ssm.crm.mapper.FlightPriceCompMapper;
import org.ldd.ssm.crm.mapper.GuestrateFlpjMapper;
import org.ldd.ssm.crm.mapper.SalesReportServiceMapper;
import org.ldd.ssm.crm.mapper.TKTIncomeMapper;
import org.ldd.ssm.crm.mapper.WatchFlightMapper;
import org.ldd.ssm.crm.query.CabinData;
import org.ldd.ssm.crm.query.FlightManageInDto;
import org.ldd.ssm.crm.query.FlightSaleData;
import org.ldd.ssm.crm.query.LatestAirFare;
import org.ldd.ssm.crm.query.SalesReportQuery;
import org.ldd.ssm.crm.query.WatchFlightDto;
import org.ldd.ssm.crm.service.TicketMonitorService;
import org.ldd.ssm.crm.service.TicketPriceFromMongoService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
@Service
public class TicketMonitorServiceImpl implements TicketMonitorService{
	
	@Resource
	private GuestrateFlpjMapper guestrateFlpjMapper;
	@Resource
	private FlightManageMapper  flightManageMapper;
	@Resource
	private TKTIncomeMapper tKTIncomeMapper;
	
	@Resource
	private WatchFlightMapper watchFlightMapper;
	
	@Resource
	private FlightPriceCompMapper flightPriceCompMapper;
	
	@Resource
	private TicketPriceFromMongoService ticketPriceFromMongo;
	
	@Resource
	private SalesReportServiceMapper salesReportServiceMapper;
	@Override
	
	/**
	 * 当日客票趋势 
	 * params:航班号 当前日期
	 */
	public List<FlightSaleData> findTicketMonitor(GuestrateFlpj dto) throws UnknownHostException, IOException {
		List<FlightSaleData> FlightSaleDataList=new ArrayList<>();
		//该航班票价仓位分布  所有航段
		try {
			WatchFlight isWatch=new WatchFlight();
			isWatch.setDptAirptCd(dto.getDptAirptCd());
			isWatch.setArrvAirptCd(dto.getArrvAirptCd());
			isWatch.setFltNbr(dto.getFltNbr());
			isWatch.setUserId(UserContext.getUser().getId());
			//isWatch.setUserId(12l);
			isWatch.setState("1");
			//int id=watchFlightMapper.getWatchFlightCount(isWatch);
			//List<String> legList=guestrateFlpjMapper.findLegs(dto);
			SalesReportQuery salesReportQuery=new SalesReportQuery();
			salesReportQuery.setDpt_AirPt_Cd(dto.getDptAirptCd());
			salesReportQuery.setPas_stp(dto.getPasStp());
			salesReportQuery.setArrv_Airpt_Cd(dto.getArrvAirptCd());
			salesReportQuery.setFlt_nbr_Count(dto.getFltNbr());
			List<String> legList=findLegsByFltNbr(salesReportQuery);
		  /*	if(legList.isEmpty()){
				List<LatestAirFare> legs=  ticketPriceFromMongo.findPriceLegs(dto);
				if(!legs.isEmpty()){
					for(LatestAirFare leg:legs){
						legList.add(leg.getSource_airport_3code()+"-"+leg.getDestination_airport_3code());
					}
				}
				
			}*/
			List<CabinData> cabinDataResultList=guestrateFlpjMapper.findCabin(dto);
			GuestrateFlpj yesterdayDto=new GuestrateFlpj();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			yesterdayDto.setFltNbr(dto.getFltNbr());
			String yesterday;
			yesterday = sdf.format(sdf.parse(dto.getOnGutDte()).getTime()-24*60*60*1000);
			yesterdayDto.setOnGutDte(yesterday);
			yesterdayDto.setDtaSce(dto.getOnGutDte());//存今天的日期  昨日销售数据=累计销售（今天）-昨日累计销售
			List<CabinData> cabinDataResultyesYterdayList=guestrateFlpjMapper.findCabinYesterDay(yesterdayDto);
			//循环对每个航段分组
			for(String leg:legList){
				FlightManage flightManageDto=new FlightManage();
				flightManageDto.setUserId(UserContext.getUser().getId());
				//flightManageDto.setUserId(12l);
				flightManageDto.setFltNbr(dto.getFltNbr());
				flightManageDto.setDptAirptCd(leg.substring(0,3));
				flightManageDto.setArrvAirptCd(leg.substring(4,7));
				List<TKTIncome> flightManageList=flightManageMapper.findFlightManage(flightManageDto);
				FlightSaleData flightSale=new FlightSaleData();
				List<Map<String,List<LatestAirFare>>> flightPriceTrendList=new ArrayList<Map<String,List<LatestAirFare>>>();
				List<CabinData> cabinDataList=new ArrayList<CabinData>();
				List<CabinData> cabinDataYesterdayList=new ArrayList<CabinData>();
				
				if(!cabinDataResultList.isEmpty()){
					for(CabinData cabinData:cabinDataResultList){
						if(leg.equals(cabinData.getLeg())){
							cabinDataList.add(cabinData);
						}
					}
				}
				
				if(!cabinDataResultyesYterdayList.isEmpty()){
					for(CabinData cabinData:cabinDataResultyesYterdayList){
						if(leg.equals(cabinData.getLeg())){
							cabinDataYesterdayList.add(cabinData);
						}
					}
				}
				//每个航段中再去查询航班管理
				if(!flightManageList.isEmpty()&&flightManageList!=null){
					for(TKTIncome flightManage:flightManageList){
						GuestrateFlpj queryDto=new GuestrateFlpj();
						List<LatestAirFare> priceTrendlist=new ArrayList<>();
						queryDto.setFltNbr(flightManage.getFltNbr());
						queryDto.setDptAirptCd(flightManage.getDptAirptCd());
						queryDto.setArrvAirptCd(flightManage.getArrvAirptCd());
						priceTrendlist=ticketPriceFromMongo.findFlightManagePrice(queryDto);
						List<FlightPriceComp> flightPriceCompList=flightPriceCompMapper.findFlightPriceComp(flightManage.getId());
						if(flightPriceCompList.size()>0){
							for(LatestAirFare fare:priceTrendlist){
								for(FlightPriceComp comp:flightPriceCompList){
									if(fare.getSearch_date().equals(comp.getCompareDate())){
										fare.setChangePrice(Float.parseFloat(fare.getLowest_price())-Float.parseFloat(comp.getCurrentPrice())+"");
										//保留两位小数
										fare.setChangeRate(Float.parseFloat(fare.getChangePrice())*100/Float.parseFloat(comp.getCurrentPrice())+"");
									}
								}
							}
						}
						if(!priceTrendlist.isEmpty()){
							Map<String,List<LatestAirFare>> map=new HashMap<String,List<LatestAirFare>>();
							map.put(flightManage.getFltNbr()+"/"+flightManage.getDptAirptCd()+"-"+flightManage.getArrvAirptCd(), priceTrendlist);
							flightPriceTrendList.add(map);
						}
					}
				}
				WatchFlight watchFlightDto=new WatchFlight();
				watchFlightDto.setFltNbr(dto.getFltNbr());
				watchFlightDto.setDptAirptCd(leg.substring(0,3));
				watchFlightDto.setArrvAirptCd(leg.substring(4,7));
				watchFlightDto.setUserId(UserContext.getUser().getId());
				//watchFlightDto.setUserId(12l);
				WatchFlight watchFlight=watchFlightMapper.getWatchFlight(watchFlightDto);
				//加入当前航班
				dto.setDptAirptCd(leg.substring(0,3));
				dto.setArrvAirptCd(leg.substring(4,7));
				Map<String,List<LatestAirFare>> map=new HashMap<String,List<LatestAirFare>>();
				List<LatestAirFare> list=ticketPriceFromMongo.findFlightManagePrice(dto);
				if(null!=list&&!list.isEmpty()){
					flightSale.setSelectIntimeFlag(true);
					flightSale.setSelectIntimeMsg("监控查询成功");
				}else {
					//从张科的接口实时查询
					//TODO 根据航班号到张科哪里，张科实时查询返回票价数据
					FlightSaleData data=ticketPriceFromMongo.getRealTimeFare(leg+"-"+dto.getFltNbr());
					list=data.getCurrerntFlightPriceList();
					flightSale.setSelectIntimeFlag(data.isSelectIntimeFlag());
					flightSale.setSelectIntimeMsg(data.getSelectIntimeMsg());
				}
				map.put(dto.getFltNbr()+"/"+leg, list);
				flightSale.setCurrerntFlightPriceMap(map);
				
				//昨日最低和平均票价
				yesterdayDto.setDptAirptCd(dto.getDptAirptCd());
				yesterdayDto.setAircrftTyp(dto.getArrvAirptCd());
				Map<String,List<LatestAirFare>> mapYesterday=new HashMap<String,List<LatestAirFare>>();
				List<LatestAirFare> listYesterday=ticketPriceFromMongo.findFlightManageHistory(yesterdayDto);
				if(!listYesterday.isEmpty()){
					mapYesterday.put(dto.getFltNbr()+"/"+leg, listYesterday);
				    flightSale.setYesterdayFlightPriceList(mapYesterday);
				}
				
				
				flightSale.setLeg(leg);
				flightSale.setCabinDataList(cabinDataList);
				flightSale.setCabinDataYesterdayList(cabinDataYesterdayList);
				flightSale.setFlightPriceTrendList(flightPriceTrendList);
				flightSale.setWatchFlight(watchFlight);
				flightSale.setFlightManageList(flightManageList);
				FlightSaleDataList.add(flightSale);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return FlightSaleDataList;
	}
	/**
	 * 历史
	 * @param dto
	 * @return
	 */
	public List<FlightSaleData> findTicketMonitorHistory(GuestrateFlpj dto) {
		List<FlightSaleData> FlightSaleDataList=new ArrayList<>();
		//该航班票价仓位分布  所有航段
		try {
			//List<String> legList=guestrateFlpjMapper.findLegs(dto);
			SalesReportQuery salesReportQuery=new SalesReportQuery();
			salesReportQuery.setDpt_AirPt_Cd(dto.getDptAirptCd());
			salesReportQuery.setPas_stp(dto.getPasStp());
			salesReportQuery.setArrv_Airpt_Cd(dto.getArrvAirptCd());
			salesReportQuery.setFlt_nbr_Count(dto.getFltNbr());
			List<String> legList=findLegsByFltNbr(salesReportQuery);
			List<CabinData> cabinDataResultList=guestrateFlpjMapper.findCabin(dto);
			GuestrateFlpj yesterdayDto=new GuestrateFlpj();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			yesterdayDto.setFltNbr(dto.getFltNbr());
			String yesterday;
			yesterday = sdf.format(sdf.parse(dto.getOnGutDte()).getTime()-24*60*60*1000);
			yesterdayDto.setOnGutDte(yesterday);
			yesterdayDto.setDtaSce(dto.getOnGutDte());//存今天的日期  昨日销售数据=累计销售（今天）-昨日累计销售
			List<CabinData> cabinDataResultyesYterdayList=guestrateFlpjMapper.findCabinYesterDay(yesterdayDto);
			//循环对每个航段分组
			for(String leg:legList){
				FlightManage flightManageDto=new FlightManage();
				flightManageDto.setUserId(UserContext.getUser().getId());
				flightManageDto.setFltNbr(dto.getFltNbr());
				flightManageDto.setDptAirptCd(leg.substring(0,3));
				flightManageDto.setArrvAirptCd(leg.substring(4,7));
				List<TKTIncome> flightManageList=flightManageMapper.findFlightManage(flightManageDto);
				FlightSaleData flightSale=new FlightSaleData();
				List<Map<String,List<LatestAirFare>>> flightPriceTrendList=new ArrayList<Map<String,List<LatestAirFare>>>();
				List<CabinData> cabinDataList=new ArrayList<CabinData>();
				List<CabinData> cabinDataYesterdayList=new ArrayList<CabinData>();
				if(!cabinDataResultList.isEmpty()){
					for(CabinData cabinData:cabinDataResultList){
						if(leg.equals(cabinData.getLeg())){
							cabinDataList.add(cabinData);
						}
					}
				}
				if(!cabinDataResultyesYterdayList.isEmpty()){
					for(CabinData cabinData:cabinDataResultyesYterdayList){
						if(leg.equals(cabinData.getLeg())){
							cabinDataYesterdayList.add(cabinData);
						}
					}
				}
				//每个航段中再去查询航班管理
				if(!flightManageList.isEmpty()){
					for(TKTIncome flightManage:flightManageList){
						GuestrateFlpj queryDto=new GuestrateFlpj();
						List<LatestAirFare> priceTrendlist=new ArrayList<>();
						queryDto.setFltNbr(flightManage.getFltNbr());
						queryDto.setDptAirptCd(flightManage.getDptAirptCd());
						queryDto.setArrvAirptCd(flightManage.getArrvAirptCd());
						queryDto.setOnGutDte(dto.getOnGutDte());
						priceTrendlist=ticketPriceFromMongo.findFlightManageHistory(queryDto);
						if(!priceTrendlist.isEmpty()){
							Map<String,List<LatestAirFare>> map=new HashMap<String,List<LatestAirFare>>();
							map.put(flightManage.getFltNbr()+"/"+flightManage.getDptAirptCd()+"-"+flightManage.getArrvAirptCd(), priceTrendlist);
							flightPriceTrendList.add(map);
						}
					}
				}
				WatchFlight watchFlightDto=new WatchFlight();
				watchFlightDto.setFltNbr(dto.getFltNbr());
				watchFlightDto.setDptAirptCd(dto.getDptAirptCd());
				watchFlightDto.setArrvAirptCd(dto.getArrvAirptCd());
				watchFlightDto.setUserId(UserContext.getUser().getId());
				WatchFlight watchFlight=watchFlightMapper.getWatchFlight(watchFlightDto);
				//加入当前航班
				dto.setDptAirptCd(leg.substring(0,3));
				dto.setArrvAirptCd(leg.substring(4,7));
				Map<String,List<LatestAirFare>> map=new HashMap<String,List<LatestAirFare>>();
				List<LatestAirFare> priceTrendlist=ticketPriceFromMongo.findFlightManageHistory(dto);
				if(!priceTrendlist.isEmpty()){
					map.put(dto.getFltNbr()+"/"+leg, priceTrendlist);
					flightSale.setCurrerntFlightPriceMap(map);
				}
				
				yesterdayDto.setDptAirptCd(dto.getDptAirptCd());
				yesterdayDto.setAircrftTyp(dto.getArrvAirptCd());
				
				Map<String,List<LatestAirFare>> mapYesterday=new HashMap<String,List<LatestAirFare>>();
				List<LatestAirFare> listYesterday=ticketPriceFromMongo.findFlightManageHistory(yesterdayDto);
				if(!listYesterday.isEmpty()){
					mapYesterday.put(dto.getFltNbr()+"/"+leg, listYesterday);
				    flightSale.setYesterdayFlightPriceList(mapYesterday);
				}
				flightSale.setLeg(leg);
				flightSale.setCabinDataList(cabinDataList);
				flightSale.setCabinDataYesterdayList(cabinDataYesterdayList);
				flightSale.setFlightPriceTrendList(flightPriceTrendList);
				flightSale.setWatchFlight(watchFlight);
				flightSale.setFlightManageList(flightManageList);
				FlightSaleDataList.add(flightSale);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return FlightSaleDataList;
	}
	
	
	@Transactional
	public boolean  flightManage(FlightManageInDto dto){
		try {
			//是否开启监控
			boolean isWatch=false;
			if(null!=dto.getWatchFlight()&&dto.getWatchFlight().getState().equals("1")){
				isWatch=true;
			}
			//转大写
			dto.getCurrentFlight().setFltNbr(dto.getCurrentFlight().getFltNbr().toUpperCase());
			/***针对本航班航段的操作****/
			/*是否有航班管理*/
			 Long tktId=flightManageMapper.getCurrentTktId(dto.getCurrentFlight());
			 if(null==tktId){
				 TKTIncome tktIncomeDto=new TKTIncome();
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				 Date date = new Date();
				 tktIncomeDto.setArrvAirptCd(dto.getCurrentFlight().getArrvAirptCd());
				 tktIncomeDto.setDptAirptCd(dto.getCurrentFlight().getDptAirptCd());
				 tktIncomeDto.setFltNbr(dto.getCurrentFlight().getFltNbr());
				 tktIncomeDto.setDate(sdf.format(date));
				 tKTIncomeMapper.insert(tktIncomeDto);
				 dto.getCurrentFlight().setCurrentTktId(tktIncomeDto.getId());
				 if(isWatch){
					//从mongodb获取最新的时间的票价插入表中   这个时候可能mong还没有数据
					 GuestrateFlpj guestrateFlpjDto=new GuestrateFlpj();
					 guestrateFlpjDto.setFltNbr(dto.getCurrentFlight().getFltNbr());
					 guestrateFlpjDto.setDptAirptCd(dto.getCurrentFlight().getDptAirptCd());
					 guestrateFlpjDto.setArrvAirptCd(dto.getCurrentFlight().getArrvAirptCd());
					 List<LatestAirFare> currentPriceList=ticketPriceFromMongo.findFlightManagePrice(guestrateFlpjDto);
					 if(!currentPriceList.isEmpty()){
						 FlightSaleData flightSaleData=new FlightSaleData();
						 flightSaleData.setCurrerntFlightPriceList(currentPriceList);
						 flightSaleData.setTktincomeId(tktIncomeDto.getId());
						 flightPriceCompMapper.batchInsert(flightSaleData);
					 }
				 }
			 }else{
				 dto.getCurrentFlight().setCurrentTktId(tktId);
			 }
			/***针对本航班航段的操作****/
			 
			if(!dto.getList().isEmpty()){
				for(TKTIncome tktIncome:dto.getList()){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
					Date date = new Date();
					tktIncome.setDate(sdf.format(date));
					//航班号转为大写
					tktIncome.setFltNbr(tktIncome.getFltNbr().toUpperCase());
					if(tktIncome.getState().equals("1")){//增
						//向TKTIncome插入数据
						tKTIncomeMapper.insert(tktIncome);
						//向flight_manage插入数据
						dto.getCurrentFlight().setTktincomeId(tktIncome.getId());
						flightManageMapper.insert(dto.getCurrentFlight());
						//如果开启则插入flight_price_comp表
						if(null!=dto.getWatchFlight()&&isWatch){
							dto.getWatchFlight().setUserId(UserContext.getUser().getId());
							dto.getWatchFlight().setUserTel(UserContext.getUser().getPhone());
							dto.getWatchFlight().setUserEmail(UserContext.getUser().getEmail());
							/*dto.getWatchFlight().setUserId(12l);
							dto.getWatchFlight().setUserTel("17780727281");
							dto.getWatchFlight().setUserEmail("1363948101@qq.com");*/
							dto.getWatchFlight().setDptAirptCd(tktIncome.getDptAirptCd());
							dto.getWatchFlight().setArrvAirptCd(tktIncome.getArrvAirptCd());
							dto.getWatchFlight().setFltNbr(tktIncome.getFltNbr());
							dto.getWatchFlight().setTktincomeId((tktIncome.getId()));
							dto.getWatchFlight().setRelArrvAirptCd(dto.getCurrentFlight().getArrvAirptCd());
							dto.getWatchFlight().setRelDptAirptCd(dto.getCurrentFlight().getDptAirptCd());
								//insert watch_flight表（监控表）
							watchFlightMapper.insert(dto.getWatchFlight());
								//从mongodb获取最新的时间的票价插入表中  
							 GuestrateFlpj guestrateFlpjDto=new GuestrateFlpj();
							 guestrateFlpjDto.setFltNbr(dto.getCurrentFlight().getFltNbr());
							 guestrateFlpjDto.setDptAirptCd(dto.getCurrentFlight().getDptAirptCd());
							 guestrateFlpjDto.setArrvAirptCd(dto.getCurrentFlight().getArrvAirptCd());
							 //获得初始价格 可能有可能还没采集
							 List<LatestAirFare> currentPriceList=ticketPriceFromMongo.findFlightManagePrice(guestrateFlpjDto);
							 if(!currentPriceList.isEmpty()){
								 FlightSaleData flightSaleData=new FlightSaleData();
								 flightSaleData.setCurrerntFlightPriceList(currentPriceList);
								 flightSaleData.setTktincomeId(tktIncome.getId());
								 flightPriceCompMapper.batchInsert(flightSaleData);
							 }
						}
					}else if(tktIncome.getState().equals("2")){//删
						//删除TKTIncome的数据  数据采集的表
						tKTIncomeMapper.deleteTKTIncome(tktIncome.getId());
						//删除flight_manage表中的数据 航班管理表
						flightManageMapper.deleteFlightManage(tktIncome.getId());
						//监控表也删除
						watchFlightMapper.deleteByTktId(tktIncome.getId());
						//初始价格表也删除
						flightPriceCompMapper.deleteByTktId(tktIncome.getId());
						
					}else if(tktIncome.getState().equals("3")){//改
						//修改TKTIncome的数据  数据采集的表
						tKTIncomeMapper.updateTKTIncome(tktIncome);
						//修改flight_manage表中的数据 航班管理表 只存了关联id 不用改
						
						//监控表也修改
						watchFlightMapper.updateByTKTIncome(tktIncome);
						//初始价格表先删除
						flightPriceCompMapper.deleteByTktId(tktIncome.getId());
						//初始价格表重新查询 写入
						if(isWatch){
							 GuestrateFlpj guestrateFlpjDto=new GuestrateFlpj();
							 guestrateFlpjDto.setFltNbr(dto.getCurrentFlight().getFltNbr());
							 guestrateFlpjDto.setDptAirptCd(dto.getCurrentFlight().getDptAirptCd());
							 guestrateFlpjDto.setArrvAirptCd(dto.getCurrentFlight().getArrvAirptCd());
							 //获得初始价格 可能有可能还没采集
							 List<LatestAirFare> currentPriceList=ticketPriceFromMongo.findFlightManagePrice(guestrateFlpjDto);
							 if(!currentPriceList.isEmpty()){
								 FlightSaleData flightSaleData=new FlightSaleData();
								 flightSaleData.setCurrerntFlightPriceList(currentPriceList);
								 flightSaleData.setTktincomeId(tktIncome.getId());
								 flightPriceCompMapper.batchInsert(flightSaleData);
							 }
						}
					}
				}
				//如果当前航班的关联航班都删了则删除本航班的监控
				Long tktIdNew=flightManageMapper.getCurrentTktId(dto.getCurrentFlight());
				if(tktIdNew==null){
					//删除之前tktId的数据采集
					tKTIncomeMapper.deleteTKTIncome(dto.getCurrentFlight().getCurrentTktId());
					//初始价格表先删除
					flightPriceCompMapper.deleteByTktId(dto.getCurrentFlight().getCurrentTktId());
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<String> getflightLeg(String fltNbr) {
		//调用外部接口
		Gson gson=new Gson();
		List<String> airlines = new ArrayList<String>();
		try {
			Connection connect =  Jsoup.connect(UserContext.getInterfacePath()+"fcz_aircraft_schedule/get_news_leg_by_fltnbr/").header("Accept", "*/*").ignoreContentType(true);
			if(!TextUtil.isEmpty(fltNbr)){
				connect.data("flt_nbr", fltNbr.toUpperCase());
			}
			Document doc = connect.timeout(100000).post();
			JsonObject returnData = new JsonParser().parse(doc.body().text()).getAsJsonObject();
			airlines = gson.fromJson(returnData.get("result").getAsJsonArray(), new TypeToken<List<String>>(){}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		return tKTIncomeMapper.getflightLeg(fltNbr);
		return airlines;
	}
	
	@Override
	public   List<TKTIncome> findFlightManage(WatchFlight dto) {
		FlightManage flightManagedto=new FlightManage();
		flightManagedto.setArrvAirptCd(dto.getArrvAirptCd());
		flightManagedto.setDptAirptCd(dto.getDptAirptCd());
		flightManagedto.setFltNbr(dto.getFltNbr());
		flightManagedto.setUserId(UserContext.getUser().getId());
		return flightManageMapper.findFlightManage(flightManagedto);
	}
	
	@Override

	public boolean upDownRemind(FlightManageInDto dto) {
		//新增
		if(null==dto.getWatchFlight().getId()){
			//插入监控表 开启才插入 关闭则不插入
			watchFlightMapper.batchInsert(dto);
			if(dto.getWatchFlight().getState().equals("1")){//如果开启则插入flight_price_comp表
				//从mongodb获取最新的时间的票价插入表中  
				for(TKTIncome tktincome:dto.getList()){
					 GuestrateFlpj guestrateFlpjDto=new GuestrateFlpj();
					 guestrateFlpjDto.setFltNbr(tktincome.getFltNbr());
					 guestrateFlpjDto.setDptAirptCd(tktincome.getDptAirptCd());
					 guestrateFlpjDto.setArrvAirptCd(tktincome.getArrvAirptCd());
					 //获得初始价格 可能有可能还没采集
					 List<LatestAirFare> currentPriceList=ticketPriceFromMongo.findFlightManagePrice(guestrateFlpjDto);
					 if(!currentPriceList.isEmpty()){
						 FlightSaleData flightSaleData=new FlightSaleData();
						 flightSaleData.setCurrerntFlightPriceList(currentPriceList);
						 flightSaleData.setTktincomeId(tktincome.getId());
						 flightPriceCompMapper.batchInsert(flightSaleData);
					 }
				}
			}
		}else{//修改
			//修改监控表 不删除监控表
			watchFlightMapper.batchUpdate(dto);
			//关闭监控 直接删除
			if(dto.getWatchFlight().getState().equals("0")){
				//删除初始票价flightPriceCompMapper
				flightPriceCompMapper.batchDeleteByTktId(dto.getList());
			}else if(dto.getWatchFlight().getState().equals("1")&&dto.getWatchFlight().getOldState().equals("0")){
				//从mongodb获取最新的时间的票价插入表中  
				for(TKTIncome tktincome:dto.getList()){
					 GuestrateFlpj guestrateFlpjDto=new GuestrateFlpj();
					 guestrateFlpjDto.setFltNbr(tktincome.getFltNbr());
					 guestrateFlpjDto.setDptAirptCd(tktincome.getDptAirptCd());
					 guestrateFlpjDto.setArrvAirptCd(tktincome.getArrvAirptCd());
					 //获得初始价格 可能有可能还没采集
					 List<LatestAirFare> currentPriceList=ticketPriceFromMongo.findFlightManagePrice(guestrateFlpjDto);
					 if(!currentPriceList.isEmpty()){
						 FlightSaleData flightSaleData=new FlightSaleData();
						 flightSaleData.setCurrerntFlightPriceList(currentPriceList);
						 flightSaleData.setTktincomeId(tktincome.getId());
						 flightPriceCompMapper.batchInsert(flightSaleData);
					 }
				}
			}
		}
		return true;
	}
	 
	public List<WatchFlightDto> findWatchFlight(String currentTime){
		return watchFlightMapper.findWatchFlight(currentTime);
	}
	
	public List<String> findLegsByFltNbr(SalesReportQuery salesReportQuery){
		String dpt_AirPt_CdCode = salesReportQuery.getDpt_AirPt_Cd();
		String arrv_Airpt_CdCode = salesReportQuery.getArrv_Airpt_Cd();
		String pas_stpCode = salesReportQuery.getPas_stp();
		if(TextUtil.isEmpty(pas_stpCode)){
			salesReportQuery.setFlt_Rte_Cd(dpt_AirPt_CdCode+arrv_Airpt_CdCode);
		}else{
			salesReportQuery.setFlt_Rte_Cd(dpt_AirPt_CdCode+pas_stpCode+arrv_Airpt_CdCode);
		}
		String fltcont = salesReportQuery.getFlt_nbr_Count();
		List<String> flyNumList = salesReportServiceMapper.getHbhSingle(salesReportQuery);
		if (flyNumList!=null&&flyNumList.size()>0) {
			for (String string : flyNumList) {
				if(!(fltcont.contains(string)&&string.length()>=6)){
					salesReportQuery.setDpt_AirPt_Cd(arrv_Airpt_CdCode);
					salesReportQuery.setArrv_Airpt_Cd(dpt_AirPt_CdCode);
				}
			}
		}
		List<String> legs=new ArrayList<String>();
		if(TextUtil.isEmpty(pas_stpCode)){
			legs.add(salesReportQuery.getDpt_AirPt_Cd()+"-"+salesReportQuery.getArrv_Airpt_Cd());
		}else{
			legs.add(salesReportQuery.getDpt_AirPt_Cd()+"-"+salesReportQuery.getPas_stp());
			legs.add(salesReportQuery.getPas_stp()+"-"+salesReportQuery.getArrv_Airpt_Cd());
			legs.add(salesReportQuery.getDpt_AirPt_Cd()+"-"+salesReportQuery.getArrv_Airpt_Cd());
		}
		return legs;
	}
	
}
