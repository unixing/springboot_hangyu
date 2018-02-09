package org.ldd.ssm.crm.service.impl;


import java.util.List;

import org.ldd.ssm.crm.domain.AirportDetailInfo;
import org.ldd.ssm.crm.domain.AirportJosnData;
import org.ldd.ssm.crm.domain.CityData;
import org.ldd.ssm.crm.domain.CityDetailInfo;
import org.ldd.ssm.crm.domain.OurBoatDetailInfo;
import org.ldd.ssm.crm.domain.ShippingData;
import org.ldd.ssm.crm.domain.ThroughPut;
import org.ldd.ssm.crm.mapper.BasicalDetailMapper;
import org.ldd.ssm.crm.service.BasicalDetailService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 机场、城市、航司等详细信息业务实现类
 * @Title:BasicalDetailServiceImpl 
 * @Description:
 * @author taimei-gds 
 * @date 2017-2-28 上午11:13:14
 */
@Service
public class BasicalDetailServiceImpl implements BasicalDetailService {
	@Autowired
	private BasicalDetailMapper basicalDetailMapper;

	@Override
	public AirportDetailInfo getAirportDetailInfoByCode(String code) {
		return basicalDetailMapper.getAirportDetailInfoByCode(code);
	}

	@Override
	public OurBoatDetailInfo getOurBoatDetailInfoByCode(String code,String airlineName) {
		return basicalDetailMapper.getOurBoatDetailInfoByCode(code,airlineName);
	}

	@Override
	public CityDetailInfo getCityDetailInfoByCode(String code) {
		return basicalDetailMapper.getCityDetailInfoByCode(code);
	}

	@Override
	public ThroughPut getThroughPutByCode(String code) {
		//得到三个表中（客量比较表、货量比较表、起降班次比较表）最大的年份
		int maxYear = 0;
		Integer[] arr = new Integer[3];
		String goodMaxYear = basicalDetailMapper.getGoodsMaxYearByCode(code);
		String passengerMaxYear = basicalDetailMapper.getPassengerMaxYearByCode(code);
		String flightsMaxYear = basicalDetailMapper.getFlightsMaxYearByCode(code);
		if(!TextUtil.isEmpty(goodMaxYear)){
			arr[0] = Integer.parseInt(goodMaxYear);
		}
		if(!TextUtil.isEmpty(passengerMaxYear)){
			arr[1] = Integer.parseInt(passengerMaxYear);
		}
		if(!TextUtil.isEmpty(flightsMaxYear)){
			arr[2] = Integer.parseInt(flightsMaxYear);
		}
		for(int n=0;n<arr.length;n++) { 
			if(maxYear<arr[n]){
				maxYear=arr[n]; 
			}
		} 
		ThroughPut throughPutGoods = basicalDetailMapper.getGoodsByCodeAndYear(code,maxYear+"");
		ThroughPut throughPutPassenger = basicalDetailMapper.getPassengerByCodeAndYear(code,maxYear+"");
		ThroughPut throughPutFlights = basicalDetailMapper.getFlightsByCodeAndYear(code,maxYear+"");
		ThroughPut tp = new ThroughPut();
		tp.setIata(code);
		tp.setThis_year(maxYear+"");
		tp.setLast_year(maxYear-1+"");
		tp.setThis_goods(throughPutGoods.getThis_goods());
		tp.setLast_goods(throughPutGoods.getLast_goods());
		tp.setGoods_ranking(throughPutGoods.getGoods_ranking());
		tp.setGoods_compare(throughPutGoods.getGoods_compare());
		tp.setThis_passenger(throughPutPassenger.getThis_passenger());
		tp.setLast_passenger(throughPutPassenger.getLast_passenger());
		tp.setPassenger_ranking(throughPutPassenger.getPassenger_ranking());
		tp.setPassenger_compare(throughPutPassenger.getPassenger_compare());
		tp.setThis_flights(throughPutFlights.getThis_flights());
		tp.setLast_flights(throughPutFlights.getLast_flights());
		tp.setFlights_ranking(throughPutFlights.getFlights_ranking());
		tp.setFlights_compare(throughPutFlights.getFlights_compare());
		return tp;
	}

	@Override
	public List<CityData> getCityDatas() {
		List<CityData> retList = basicalDetailMapper.getCityDatas();
		for (CityData cityData : retList) {
			if(!TextUtil.isEmpty(cityData.getPy())){
				cityData.setInitial(cityData.getPy().substring(0, 1));
			}
		}
		return retList;
	}

	@Override
	public List<ShippingData> getShippingDatas() {
		List<ShippingData> retList = basicalDetailMapper.getShippingDatas();
		return retList;
	}

	@Override
	public List<AirportJosnData> getAirportJosnDatas() {
		List<AirportJosnData> retList = basicalDetailMapper.getAirportJosnDatas();
		for (AirportJosnData airportJosnData : retList) {
			if(TextUtil.isEmpty(airportJosnData.getCitycode())){
				airportJosnData.setCitycode("");
			}
			if(!TextUtil.isEmpty(airportJosnData.getPy())){
				airportJosnData.setInitial(airportJosnData.getPy().substring(0, 1));
			}
		}
		return retList;
	}
	
}
