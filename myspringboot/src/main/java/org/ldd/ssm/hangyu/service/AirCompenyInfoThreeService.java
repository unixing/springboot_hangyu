package org.ldd.ssm.hangyu.service;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.hangyu.domain.AirCompenyInfoThree;

public interface AirCompenyInfoThreeService {

	public List<AirCompenyInfoThree> selectAll();
	
	public Map<String,Object> loadAircompenyDetail(String itia);
	
	AirCompenyInfoThree load(String iata);
}
