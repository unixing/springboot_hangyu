package org.ldd.ssm.crm.mapper;

import org.ldd.ssm.crm.domain.Fare;
/**
 * AirLineForecast映射Mapper
 * @Title:AirLineForecastMapper 
 * @Description:
 * @author taimei-gds 
 * @date 2016-3-29 上午10:45:06
 */
public interface IITIAMapper {

	Fare find(String str);

	void save(Fare value);
}
