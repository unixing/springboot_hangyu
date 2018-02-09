package org.ldd.ssm.crm.service;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.FlightAirline;
import org.ldd.ssm.crm.query.FlightAirlineQuery;
/**
 * 航路视图接口类
 * @Title:FlightAirlineService 
 * @Description:
 * @author taimei-gds 
 * @date 2017-3-31 上午11:49:48
 */
public interface FlightAirlineService {
	/**
	 * 根据查询条件得到航路数据
	 * @Title: getFlightAirlineData 
	 * @Description:  
	 * @param @param flightAirlineQuery
	 * @param @return    
	 * @return Map<String,List<FlightAirline>>    
	 * @throws
	 */
	public Map<String,List<FlightAirline>> getFlightAirlineData(FlightAirlineQuery flightAirlineQuery);
	/**
	 * 全国航路视图数据
	 * @Title: getAllFlightAirlineData 
	 * @Description:  
	 * @param @return    
	 * @return Map<String,List<FlightAirline>>     
	 * @throws
	 */
	public Map<String,List<FlightAirline>> getAllFlightAirlineData();
}
