package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.FlightAirline;
import org.ldd.ssm.crm.query.FlightAirlineQuery;
/**
 * 航路Mapper控制类
 * @Title:FlightAirlineMapper 
 * @Description:
 * @author taimei-gds 
 * @date 2017-3-31 上午11:55:25
 */
public interface FlightAirlineMapper {
	/**
	 * 得到航路信息数据
	 * @Title: getFlightAirlineData 
	 * @Description:  
	 * @param @param flightAirlineQuery
	 * @param @return    
	 * @return List<FlightAirline>     
	 * @throws
	 */
	List<FlightAirline> getFlightAirlineData(FlightAirlineQuery flightAirlineQuery);
	/**
	 * 查询航线关系表
	 * @Title: getGuanXiData 
	 * @Description:  
	 * @param @param flightids
	 * @param @return    
	 * @return List<FlightAirline>     
	 * @throws
	 */
	List<FlightAirline> getGuanXiData(List<String> flightids);
	/**
	 * 查询航点表
	 * @Title: getPointData 
	 * @Description:  
	 * @param @param pointids
	 * @param @return    
	 * @return List<FlightAirline>     
	 * @throws
	 */
	List<FlightAirline> getPointData(List<String> pointids);
	/**
	 * 机场信息中查询航点
	 * @Title: getAirportData 
	 * @Description:  
	 * @param @param pointids
	 * @param @return    
	 * @return List<FlightAirline>     
	 * @throws
	 */
	List<FlightAirline> getAirportData(List<String> pointids);
	/**
	 * 
	 * @Title: getAllFlightAirlineData 
	 * @Description:  
	 * @param @return    
	 * @return List<FlightAirline>     
	 * @throws
	 */
	List<FlightAirline> getAllFlightAirlineData();
	/**
	 * 将传入的三字码转为四字码 wxm
	 * @param flightAirlineQuery
	 * @return
	 */
	FlightAirlineQuery translateIataToIcao(FlightAirlineQuery flightAirlineQuery);
}
