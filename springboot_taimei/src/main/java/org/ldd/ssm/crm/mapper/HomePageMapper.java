package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.AirInfoData;
import org.ldd.ssm.crm.domain.AirportData;
import org.ldd.ssm.crm.domain.AllAirLineFltDetailData;
import org.ldd.ssm.crm.domain.CityCoordinate;
import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.HomePageData;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.AllAirLineQuery;
import org.ldd.ssm.crm.query.HavingDataQuery;
import org.ldd.ssm.crm.query.HomePageQuery;

/**
 * 首页对应的mapper
 * @Title:HomePageMapper 
 * @Description:
 * @author taimei-gds 
 * @date 2016-6-12 下午3:50:59
 */
public interface HomePageMapper {
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
	 * 昨日与该机场相关所有航线客量总和
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
	
	public List<HomePageData> getAirportKzlDataList(HomePageQuery homePageQuery);
	public List<HomePageData> getAirportZglDataList(HomePageQuery homePageQuery);
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
	
	public List<HomePageData> getSelectAirLineData(HomePageQuery homePageQuery);
	/**
	 * 根据机场三字码得到城市名称
	 * @Title: getCityNameByCode 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getCityNameByCode(HomePageQuery homePageQuery);
	/**
	 * 得到最新的数据的日期
	 * @Title: getNewDate 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getNewDate(HomePageQuery homePageQuery);
	
	List<String> getFltNum(HomePageQuery homePageQuery);
	
	List<Z_Airdata> getZairdataList(HomePageQuery homePageQuery);
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
	 * 航线视图排行与趋势所需数据
	 * @Title: getKPIZAirdataList 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return List<Z_Airdata>     
	 * @throws
	 */
	List<Z_Airdata> getKPIZAirdataList(HomePageQuery homePageQuery);
	/**
	 * 航线视图排行与趋势所需数据-v3.0
	 * @Title: getKPIZAirdataList 
	 * @Description:  
	 * @param @param homePageQuery
	 * @param @return    
	 * @return List<Z_Airdata>     
	 * @throws
	 */
	List<Z_Airdata> getKPIZAirdataListNew(HomePageQuery homePageQuery);
	/**
	 * 获取仪表盘数据
	 * @param homePageQuery
	 * @return
	 */
	public EvenPort selectFocusData(HomePageQuery homePageQuery);
	/**
	 * 获取进港客量
	 * @param homePageQuery
	 * @return
	 */
	public int selectTravellerIN(HomePageQuery homePageQuery);
	/**
	 * 获取出港客量
	 * @param homePageQuery
	 * @return
	 */
	public int selectTravellerOUT(HomePageQuery homePageQuery);
	
	public List<Integer> selectFlightCount(HomePageQuery homePageQuery);
	/**
	 * 获取采集数据的航班号对
	 * @Title: getIntrutionsFlyNbr 
	 * @Description:  
	 * @param @param ertremId
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	public List<String> getIntrutionsFlyNbr(String airportCode);
	/**
	 * 获取采集数据的航线
	 * @Title: getOnInstrutionsLinees 
	 * @Description:  
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	public List<String> getOnInstrutionsLinees(String airportCode);
	/**
	 * 获取全国航线
	 * @Title: getChinaAirLineDataList 
	 * @Description:  
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	public List<String> getChinaAirLineDataList(@Param("itia") String itia);
	/**
	 * 得到时间集合
	 * @Title: getTimeData 
	 * @Description:  
	 * @param @param havingDataQuery
	 * @param @return    
	 * @return List<Z_Airdata>     
	 * @throws
	 */
	public List<Z_Airdata> getTimeData(HavingDataQuery havingDataQuery);
	/**
	 * 得到全国航线下机场或者航司下的所有航班号及机型
	 * @Title: getAllAirLineData 
	 * @Description:  
	 * @param @param allAirLineQuery
	 * @param @return    
	 * @return List<AllAirLineFltDetailData>     
	 * @throws
	 */
	public List<AllAirLineFltDetailData> getAllAirLineData(AllAirLineQuery allAirLineQuery);
	/**
	 * 得到最新采集日期
	 * @Title: getNewLineDate 
	 * @Description:  
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getNewLineDate();
	/**
	 * 航线搜索高级筛选
	 * @param selectSql
	 * @return
	 */
	public List<AirInfoData> findFilterParamAirPort(@Param("sql") String selectSql);
}