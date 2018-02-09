package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.AirLineForecast;
import org.ldd.ssm.crm.domain.AirLineForecastDetail;
import org.ldd.ssm.crm.domain.Fare;
import org.ldd.ssm.crm.mapper.AirLineForecastMapper;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.mapper.UpdataMapper;
import org.ldd.ssm.crm.query.AirLineForecastQuery;
import org.ldd.ssm.crm.service.AirLineForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 航线收益预估实现类
 * @Title:AirLineForecastServiceImpl 
 * @Description:
 * @author taimei-gds 
 * @date 2016-3-25 上午10:49:00
 */
@Service
public class AirLineForecastServiceImpl implements AirLineForecastService {
	@Autowired
	private AirLineForecastMapper airLineForecastMapper;
	@Autowired
	private OutPortMapper outPortMapper;
	@Autowired
	private UpdataMapper mapper;
	public AirLineForecast getAirLineForecast(AirLineForecastQuery airLineForecastQuery) {
		//得到预估航线基本信息
		AirLineForecast  airLineForecast = airLineForecastMapper.getAirLineForecast(airLineForecastQuery);
		return airLineForecast;
	}

	public List<AirLineForecastDetail> getAirLineForecastDetailList(AirLineForecast airLineForecast) {
		return airLineForecastMapper.getAirLineForecastDetailList(airLineForecast);
	}

	public void saveAirLineForecastDetail(AirLineForecastDetail airLineForecastDetail) {
		airLineForecastMapper.saveAirLineForecastDetail(airLineForecastDetail);
	}

	public void updateAirLineForecastDetail(
			AirLineForecastDetail AirLineForecastDetail) {
		airLineForecastMapper.updateAirLineForecastDetail(AirLineForecastDetail);
	}

	public void saveAirLineForecast(AirLineForecast AirLineForecast) {
		airLineForecastMapper.saveAirLineForecast(AirLineForecast);
	}

	public void updateAirLineForecast(AirLineForecast AirLineForecast) {
		airLineForecastMapper.updateAirLineForecast(AirLineForecast);
	}

	/* (non-Javadoc)
	 * @see org.ldd.ssm.crm.service.AirLineForecastService#getFare(java.lang.String)
	 */
	public Fare getFare(String dpt ,String arr) {
		Fare fare = new Fare();
		fare.setVoyageCode(outPortMapper.getAirCodeByName(dpt)+outPortMapper.getAirCodeByName(arr));
		Fare getfare = mapper.get(fare);
		return getfare;
	}
	
}
