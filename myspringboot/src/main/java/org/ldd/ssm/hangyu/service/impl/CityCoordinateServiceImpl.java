package org.ldd.ssm.hangyu.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.CityCoordinate;
import org.ldd.ssm.hangyu.mapper.CityCoordinateMapper;
import org.ldd.ssm.hangyu.service.CityCoordinateService;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CityCoordinateServiceImpl implements CityCoordinateService {

	@Autowired
	private CityCoordinateMapper cityCoordinateMapper;
	Logger log = Logger.getLogger(CityCoordinateServiceImpl.class);
	
	@Override
	public CityCoordinate getCityCoordinateByCityIcao(String icao) {
		CityCoordinate cityCoordinate = null;
		if(TextUtil.isEmpty(icao)){
			return cityCoordinate;
		}
		try {
			cityCoordinate = cityCoordinateMapper.selectByCityIcao(icao);
		} catch (Exception e) {
			log.error("There is something wrong here");
			e.printStackTrace();
			return cityCoordinate;
		}
		return cityCoordinate;
	}

	@Override
	public CityCoordinate getCityCoordinateByAirIATA(String iata) {
		CityCoordinate cityCoordinate = null;
		if(TextUtil.isEmpty(iata)){
			return cityCoordinate;
		}
		try {
			cityCoordinate = cityCoordinateMapper.selectByAirIcao(iata);
		} catch (Exception e) {
			log.error("There is something wrong here");
			e.printStackTrace();
			return cityCoordinate;
		}
		return cityCoordinate;
	}

	@Override
	public List<CityCoordinate> getAllCityList() {
		List<CityCoordinate> list = null;
		try {
			list = cityCoordinateMapper.selectAllCityList();
		} catch (Exception e) {
			log.error("There is something wrong here");
			e.printStackTrace();
			return list;
		}
		return list;
	}

}
