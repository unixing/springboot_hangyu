package org.ldd.ssm.crm.service;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.AirInfoData;
import org.ldd.ssm.crm.domain.AirportData;
import org.ldd.ssm.crm.domain.AllAirLineFltDetailData;
import org.ldd.ssm.crm.domain.CityCoordinate;
import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.HomePageData;
import org.ldd.ssm.crm.query.AllAirLineQuery;
import org.ldd.ssm.crm.query.FilterParamAirPortObject;
import org.ldd.ssm.crm.query.FilterParamAirPortQuery;
import org.ldd.ssm.crm.query.HavingDataQuery;
import org.ldd.ssm.crm.query.HomePageQuery;

/**
 * 首页sevice层
 * @Title:HomePageService 
 * @Description:
 * @author taimei-gds 
 * @date 2016-6-12 下午3:43:39
 */
public interface HomePageService {
	/**
	 * 昨日收益
	 * @Title: getYesterdayEarnings 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getYesterdayEarnings(HomePageQuery homePageQuery);
	
	/**
	 * 昨日班次
	 * @Title: getYesterdayClasses 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getYesterdayClasses(HomePageQuery homePageQuery);
	/**
	 * 昨日进港
	 * @Title: getYesterdayPutin 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getYesterdayPutin(HomePageQuery homePageQuery);
	/**
	 * 昨日出港
	 * @Title: getYesterdayLeave 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getYesterdayLeave(HomePageQuery homePageQuery);
	/**
	 * 昨日所有与该机场相关航线总客量
	 * @Title: getYesterdayLeave 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getYesterdayAllLineTraveller(HomePageQuery homePageQuery);
	/**
	 * 获得近10天机场吞吐量
	 * @Title: getAirportFlowData 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return List<HomePageData>     
	 * @throws
	 */
	public List<HomePageData> getAirportFlowDataList(HomePageQuery homePageQuery);
	public List<HomePageData> getAirportFlow2DataList(HomePageQuery homePageQuery);
	/**
	 * 得到客座率排名前十
	 * @Title: getAirportKzlDataList 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return List<HomePageData>     
	 * @throws
	 */
	public List<HomePageData> getAirportKzlDataList(HomePageQuery homePageQuery);
	/**
	 * 得到座公里排名前十
	 * @Title: getAirportZglDataList 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return List<HomePageData>     
	 * @throws
	 */
	public List<HomePageData> getAirportZglDataList(HomePageQuery homePageQuery);
	/**
	 * 得到总收入排名前十
	 * @Title: getAirportZsrDataList 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return List<HomePageData>     
	 * @throws
	 */
	public List<HomePageData> getAirportZsrDataList(HomePageQuery homePageQuery);
	/**
	 * 获得机场在飞航线数据
	 * @Title: getAirportOnLineData 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return List<HomePageData>     
	 * @throws
	 */
	public List<HomePageData> getAirportOnLineDataList(HomePageQuery homePageQuery);
	/**
	 * 得到最新数据的日期
	 * @Title: getNewDate 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getNewDate(HomePageQuery homePageQuery);
	/**
	 * 得到城市坐标
	 * @Title: getCityCoordinateList 
	 * @Description:  
	 * @param @return    
	 * @return List<CityCoordinate>     
	 * @throws
	 */
	public List<CityCoordinate> getCityCoordinateList();
	/**
	 * 根据选择的航线得到对应的航线 和所有的航班号
	 * @Title: getSelectAirLineData 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return List<HomePageData>     
	 * @throws
	 */
	public List<HomePageData> getSelectAirLineData(HomePageQuery homePageQuery);
	/**
	 * 得到所有机场的map信息
	 * @Title: getAirportInfoMap 
	 * @Description:  
	 * @param @return    
	 * @return Map<String,AirportData>     
	 * @throws
	 */
	public Map<String,AirportData> getAirportInfoMap();
	/**
	 * 获得机场三字码和四字码
	 * @Title: getIcaoIataList 
	 * @Description:  
	 * @param @return    
	 * @return List<AirportData>     
	 * @throws
	 */
	public List<AirportData> getIcaoIataList();
	/**
	 * 航线视图的排行与走势
	 * @Title: getAirPortKPIData 
	 * @Description:  
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	public Map<String,Object> getAirPortKPIData(HomePageQuery homePageQuery);
	/**
	 * 航线视图的排行与走势-v3.0
	 * @Title: getAirPortKPIData 
	 * @Description:  
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	public List<Map<String,Object>> getAirPortKPIDataNew(HomePageQuery homePageQuery);
	/**
	 * 得到机场视图所需的所有机场信息
	 * @Title: getAirInfoDataList 
	 * @Description:  
	 * @param @return    
	 * @return List<AirInfoData>     
	 * @throws
	 */
	public List<AirInfoData> getAirInfoDataList();
	/**
	 * 获得外国机场视图
	 * @Title: getForeAirInfoDataList 
	 * @Description:  
	 * @param @return    
	 * @return List<AirInfoData>     
	 * @throws
	 */
	public List<AirInfoData> getForeAirInfoDataList();
	/**
	 * 获取机场首页用户关注数据
	 * @param homePageQuery
	 * @return
	 */
	public EvenPort getAirportFocusData(HomePageQuery homePageQuery);
	/**
	 * 获取进港客量
	 * @param homePageQuery
	 * @return
	 */
	public int getAirportTravellerIn(HomePageQuery homePageQuery);
	/**
	 * 获取出港客量
	 * @param homePageQuery
	 * @return
	 */
	public int getAirportTravellerOUT(HomePageQuery homePageQuery);
	/**
	 * 获取一段时间内的班次
	 * @param homePageQuery
	 * @return 班次
	 */
	public int getFlightCount(HomePageQuery homePageQuery);
	/**
	 * 获取采集数据的航班号对
	 * @Title: getOnInstrutionsFlyNbrs 
	 * @Description:  
	 * @param @param ertremId
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	public List<String> getOnInstrutionsFlyNbrs(String airportCode);
	/**
	 * 得到自采航线
	 * @Title: getOnInstrutionsLinees 
	 * @Description:  
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	public List<String> getOnInstrutionsLinees();
	/**
	 * 得到中国全国航线列表
	 * @Title: getChinaAirLineDataList 
	 * @Description:  
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	public List<String> getChinaAirLineDataList();
	/**
	 * 判断传入条件下是否有30天没有数据
	 * @Title: getHavingDataByThire 
	 * @Description:  
	 * @param @param havingDataQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getHavingDataByThire(HavingDataQuery havingDataQuery);
	/**
	 * 根据航司或者机场得到所有航班号和机型和航线
	 * @Title: getAllAirLineData 
	 * @Description:  
	 * @param @param allAirLineQuery
	 * @param @return    
	 * @return List<AllAirLineFltDetailData>     
	 * @throws
	 */
	public List<AllAirLineFltDetailData> getAllAirLineData(AllAirLineQuery allAirLineQuery);
	/**
	 * 航线筛选高级查询功能
	 * @param query
	 * @return
	 */
	public List<AirInfoData> findFilterParamAirPort(FilterParamAirPortQuery query);
}
