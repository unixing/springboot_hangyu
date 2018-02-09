package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.AirportLocation;

public interface AirportFlightMapper {
	//获得该机场的位置
	 AirportLocation getAirportLocation(String iATA);
	 //该机场飞往的其他机场的位置集合
	 List<AirportLocation> getAirportLocations(String iATA);
	
}
