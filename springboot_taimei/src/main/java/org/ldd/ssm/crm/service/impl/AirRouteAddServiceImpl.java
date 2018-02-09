package org.ldd.ssm.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.ldd.ssm.crm.domain.AirRoute;
import org.ldd.ssm.crm.mapper.AirRouteAddMapper;
import org.ldd.ssm.crm.service.IAirRouteAddService;
import org.springframework.stereotype.Service;

@Service
public class AirRouteAddServiceImpl implements IAirRouteAddService{
	@Resource
	private AirRouteAddMapper airRouteAddMapper;
	
	/**
	 * 返回自定义航线列表 用于添加修改航线
	 */
	@Override
	public List<AirRoute> findAirRouteList(AirRoute airRoute) {
		return airRouteAddMapper.findAirRouteList(airRoute);
	}

	
	@Override
	public boolean airRouteAdd(AirRoute airRoute) {
		int affectRows=0;
		affectRows=airRouteAddMapper.airRouteAdd(airRoute);
		if(affectRows>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean airRouteEditBatch(List<AirRoute> airRoute) {
		int affectRows=0;
		affectRows=airRouteAddMapper.airRouteEditBatch(airRoute);
		if(affectRows>0){
			return true;
		}
		return false;
		
	}

	@Override
	public boolean airRouteDelete(long id) {
		int affectRow=0;
		affectRow=airRouteAddMapper.airRouteDelete(id);
		if(affectRow==1){
			return true;
		}
		return false;
	}


	@Override
	public List<String> findOnlineDataList(String itia) {
		return airRouteAddMapper.findOnlineDataList(itia);
	}

}
