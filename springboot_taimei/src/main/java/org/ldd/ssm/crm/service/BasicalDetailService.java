package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.AirportDetailInfo;
import org.ldd.ssm.crm.domain.AirportJosnData;
import org.ldd.ssm.crm.domain.CityData;
import org.ldd.ssm.crm.domain.CityDetailInfo;
import org.ldd.ssm.crm.domain.OurBoatDetailInfo;
import org.ldd.ssm.crm.domain.ShippingData;
import org.ldd.ssm.crm.domain.ThroughPut;

/**
 * 机场、城市、航司等详细信息业务接口
 * @Title:BasicalDetailService 
 * @Description:
 * @author taimei-gds 
 * @date 2017-2-28 上午11:10:29
 */
public interface BasicalDetailService {
	/**
	 * 根据机场三字码获取机场信息
	 * @Title: getAirportDetailInfoByCode 
	 * @Description:  
	 * @param @return    
	 * @return AirportDetailInfo     
	 * @throws
	 */
	public AirportDetailInfo getAirportDetailInfoByCode(String code);
	/**
	 * 根据机场查询最新有数据的指标比较信息
	 * @Title: getThroughPutByCode 
	 * @Description:  
	 * @param @param code
	 * @param @return    
	 * @return ThroughPut     
	 * @throws
	 */
	public ThroughPut getThroughPutByCode(String code);
	/**
	 * 根据航司二字码获取航司信息
	 * @Title: getOurBoatDetailInfoByCode 
	 * @Description:  
	 * @param @return    
	 * @return OurBoatDetailInfo     
	 * @throws
	 */
	public OurBoatDetailInfo getOurBoatDetailInfoByCode(String code, String airlineName);
	/**
	 * 根据城市四字码获取城市信息
	 * @Title: getCityDetailInfoByCode 
	 * @Description:  
	 * @param @return    
	 * @return CityDetailInfo     
	 * @throws
	 */
	public CityDetailInfo getCityDetailInfoByCode(String code);
	/**
	 * 获得所有城市
	 * @Title: getCityDatas 
	 * @Description:  
	 * @param @return    
	 * @return List<CityData>     
	 * @throws
	 */
	public List<CityData> getCityDatas();
	
	/**
	 * 获得所有航司
	 * @Title: getShippingDatas 
	 * @Description:  
	 * @param @return    
	 * @return List<ShippingData>     
	 * @throws
	 */
	public List<ShippingData> getShippingDatas();
	/**
	 * 获得所有机场
	 * @Title: getAirportJosnDatas 
	 * @Description:  
	 * @param @return    
	 * @return List<AirportJosnData>     
	 * @throws
	 */
	public List<AirportJosnData> getAirportJosnDatas();
}
