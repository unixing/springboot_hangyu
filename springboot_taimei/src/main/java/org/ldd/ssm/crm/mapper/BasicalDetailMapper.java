package org.ldd.ssm.crm.mapper;


import java.util.List;

import org.ldd.ssm.crm.domain.AirportDetailInfo;
import org.ldd.ssm.crm.domain.AirportJosnData;
import org.ldd.ssm.crm.domain.CityData;
import org.ldd.ssm.crm.domain.CityDetailInfo;
import org.ldd.ssm.crm.domain.OurBoatDetailInfo;
import org.ldd.ssm.crm.domain.ShippingData;
import org.ldd.ssm.crm.domain.ThroughPut;

/**
 * 机场、城市、航司等详细信息业务Mapper
 * @Title:BasicalDetailMapper 
 * @Description:
 * @author taimei-gds 
 * @param <AirportDetailInfo>
 * @date 2017-2-28 上午11:12:49
 */
public interface BasicalDetailMapper{
	/**
	 * 根据机场三字码获取机场信息
	 * @Title: getAirportDetailInfoByCode 
	 * @Description:  
	 * @param @return    
	 * @return AirportDetailInfo     
	 * @throws
	 */
	AirportDetailInfo getAirportDetailInfoByCode(String code);
	/**
	 * 根据航司二字码获取航司信息
	 * @Title: getOurBoatDetailInfoByCode 
	 * @Description:  
	 * @param @return    
	 * @return OurBoatDetailInfo     
	 * @throws
	 */
	OurBoatDetailInfo getOurBoatDetailInfoByCode(String code, String airlineName);
	/**
	 * 根据城市四字码获取城市信息
	 * @Title: getCityDetailInfoByCode 
	 * @Description:  
	 * @param @return    
	 * @return CityDetailInfo     
	 * @throws
	 */
	CityDetailInfo getCityDetailInfoByCode(String code);
	/**
	 * 根据年和代码查询货量比较信息
	 * @Title: getGoodsByCodeAndYear 
	 * @Description:  
	 * @param @param code
	 * @param @param year
	 * @param @return    
	 * @return ThroughPut     
	 * @throws
	 */
	ThroughPut getGoodsByCodeAndYear(String code, String year);
	/**
	 * 根据年和代码查询人数比较信息
	 * @Title: getPassengerByCodeAndYear 
	 * @Description:  
	 * @param @param code
	 * @param @param year
	 * @param @return    
	 * @return ThroughPut     
	 * @throws
	 */
	ThroughPut getPassengerByCodeAndYear(String code, String year);
	/**
	 * 根据年和代码查询航班比较信息
	 * @Title: getFlightsByCodeAndYear 
	 * @Description:  
	 * @param @param code
	 * @param @param year
	 * @param @return    
	 * @return ThroughPut     
	 * @throws
	 */
	ThroughPut getFlightsByCodeAndYear(String code, String year);
	/**
	 * 获得货物最大年份
	 * @Title: getGoodsMaxYearByCode 
	 * @Description:  
	 * @param @param code
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	String getGoodsMaxYearByCode(String code);
	/**
	 * 获得乘客最大年份
	 * @Title: getPassengerMaxYearByCode 
	 * @Description:  
	 * @param @param code
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	String getPassengerMaxYearByCode(String code);
	/**
	 * 获得航班最大年份
	 * @Title: getFlightsMaxYearByCode 
	 * @Description:  
	 * @param @param code
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	String getFlightsMaxYearByCode(String code);
	/**
	 * 获得所有城市
	 * @Title: getCityDatas 
	 * @Description:  
	 * @param @return    
	 * @return List<CityData>     
	 * @throws
	 */
	List<CityData> getCityDatas();
	/**
	 * 获得所有航司
	 * @Title: getShippingDatas 
	 * @Description:  
	 * @param @return    
	 * @return List<ShippingData>     
	 * @throws
	 */
	List<ShippingData> getShippingDatas();
	/**
	 * 获得所有机场
	 * @Title: getAirportJosnDatas 
	 * @Description:  
	 * @param @return    
	 * @return List<AirportJosnData>     
	 * @throws
	 */
	List<AirportJosnData> getAirportJosnDatas();
	
}
