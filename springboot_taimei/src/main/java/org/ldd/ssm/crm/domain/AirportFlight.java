package org.ldd.ssm.crm.domain;

import java.util.List;
/**
 * 机场航线
 * @author wxm
 *
 * @date 2017-3-30
 */
public class AirportFlight {

	private AirportLocation airportLocation;//机场
	
	private List<AirportLocation> airportLocationList;//有航线的其他机场 连接形成航线图

	public AirportLocation getAirportLocation() {
		return airportLocation;
	}

	public void setAirportLocation(AirportLocation airportLocation) {
		this.airportLocation = airportLocation;
	}

	public List<AirportLocation> getAirportLocationList() {
		return airportLocationList;
	}

	public void setAirportLocationList(List<AirportLocation> airportLocationList) {
		this.airportLocationList = airportLocationList;
	}
	
	
}
