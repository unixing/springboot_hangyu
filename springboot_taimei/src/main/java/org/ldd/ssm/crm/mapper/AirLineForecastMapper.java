package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.AirLineForecast;
import org.ldd.ssm.crm.domain.AirLineForecastDetail;
import org.ldd.ssm.crm.query.AirLineForecastQuery;
/**
 * AirLineForecast映射Mapper
 * @Title:AirLineForecastMapper 
 * @Description:
 * @author taimei-gds 
 * @date 2016-3-29 上午10:45:06
 */
public interface AirLineForecastMapper {

	
	AirLineForecast getAirLineForecast(AirLineForecastQuery airLineForecastQuery);
	
	List<AirLineForecastDetail> getAirLineForecastDetailList(AirLineForecast airLineForecast);
	
	void saveAirLineForecastDetail(AirLineForecastDetail AirLineForecastDetail);
	
	void updateAirLineForecastDetail(AirLineForecastDetail AirLineForecastDetail);
	
	void saveAirLineForecast(AirLineForecast AirLineForecast);
	
	void updateAirLineForecast(AirLineForecast AirLineForecast);

}
