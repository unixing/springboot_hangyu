package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.AirLineForecast;
import org.ldd.ssm.crm.domain.AirLineForecastDetail;
import org.ldd.ssm.crm.domain.Fare;
import org.ldd.ssm.crm.query.AirLineForecastQuery;

/**
 * 航线收益预估控制类
 * @Title:AirLineForecastService 
 * @Description:
 * @author taimei-gds 
 * @date 2016-3-25 上午10:40:11
 */
public interface AirLineForecastService {
	/**
	 * 根据起始时间和机场查询预估基本信息
	 * @Title: getAirLineForecast 
	 * @Description:  
	 * @param @param airLineForecastQuery
	 * @param @return    
	 * @return AirLineForecast     
	 * @throws
	 */
	AirLineForecast getAirLineForecast(AirLineForecastQuery airLineForecastQuery);
	/**
	 * 根据预估基本信息的ID查询每个航段详细信息
	 * @Title: getAirLineForecastDetailList 
	 * @Description:  
	 * @param @param AirLineForecast
	 * @param @return    
	 * @return List<AirLineForecastDetail>     
	 * @throws
	 */
	List<AirLineForecastDetail> getAirLineForecastDetailList(AirLineForecast airLineForecast);
	/**
	 * 保存预估航段的详细信息
	 * @Title: saveAirLineForecastDetail 
	 * @Description:  
	 * @param @param AirLineForecastDetail
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	void saveAirLineForecastDetail(AirLineForecastDetail AirLineForecastDetail);
	/**
	 * 修改预估航段的详细信息
	 * @Title: updataAirLineForecastDetail 
	 * @Description:  
	 * @param @param AirLineForecastDetail
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	void updateAirLineForecastDetail(AirLineForecastDetail AirLineForecastDetail);
	/**
	 * 保存预估基本信息
	 * @Title: saveAirLineForecast 
	 * @Description:  
	 * @param @param AirLineForecast
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	void saveAirLineForecast(AirLineForecast AirLineForecast);
	/**
	 * 修改预估信息
	 * @Title: updataAirLineForecast 
	 * @Description:  
	 * @param @param AirLineForecast
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	void updateAirLineForecast(AirLineForecast AirLineForecast);
	/**
	 * 根据航段得到航线的基本固定数据
	 * @Title: getFare 
	 * @Description:  
	 * @param @param dpt,arr
	 * @param @return    
	 * @return Fare     
	 * @throws
	 */
	Fare getFare(String dpt, String arr);
}
