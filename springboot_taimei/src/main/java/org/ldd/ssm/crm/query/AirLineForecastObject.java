package org.ldd.ssm.crm.query;

import java.util.List;

import org.ldd.ssm.crm.domain.AirLineForecast;

/**
 * 页面模型组装
 * @Title:AirLineForecastObject 
 * @Description:
 * @author taimei-gds 
 * @date 2016-3-30 上午10:13:02
 */
public class AirLineForecastObject {
	private AirLineForecast airLineForecast;
	private List<AirLineForecastDetailObject> airLineForecastDetailObjectList;
	/**
	 * @return the airLineForecast
	 */
	public AirLineForecast getAirLineForecast() {
		return airLineForecast;
	}
	/**
	 * @param airLineForecast the airLineForecast to set
	 */
	public void setAirLineForecast(AirLineForecast airLineForecast) {
		this.airLineForecast = airLineForecast;
	}
	/**
	 * @return the airLineForecastDetailObjectList
	 */
	public List<AirLineForecastDetailObject> getAirLineForecastDetailObjectList() {
		return airLineForecastDetailObjectList;
	}
	/**
	 * @param airLineForecastDetailObjectList the airLineForecastDetailObjectList to set
	 */
	public void setAirLineForecastDetailObjectList(
			List<AirLineForecastDetailObject> airLineForecastDetailObjectList) {
		this.airLineForecastDetailObjectList = airLineForecastDetailObjectList;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AirLineForecastObject [airLineForecast=" + airLineForecast
				+ ", airLineForecastDetailObjectList="
				+ airLineForecastDetailObjectList + "]";
	}
	
}