package org.ldd.ssm.hangyu.service;

import java.util.List;

import org.ldd.ssm.hangyu.domain.CityCoordinate;

public interface CityCoordinateService {
	
	public CityCoordinate getCityCoordinateByCityIcao(String icao);
	
	public CityCoordinate getCityCoordinateByAirIATA(String iata);
	
	public List<CityCoordinate> getAllCityList();
}
