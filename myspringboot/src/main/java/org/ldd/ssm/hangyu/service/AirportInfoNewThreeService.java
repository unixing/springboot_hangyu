package org.ldd.ssm.hangyu.service;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.hangyu.domain.RouteNetwork;

public interface AirportInfoNewThreeService {
	public List<RouteNetwork> getAirportListByIata(int type, String iata);
	public Map<String,Object> loadAirportByCode(String itia);
	public Map<String,Object> loadAirportInformationByIata(String iata);
	public Map<String, Object> loadIndexAirportInfoByCode(String itia);
}
