package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.FlyingAirRoute;
import org.ldd.ssm.crm.mapper.FlyingAirRouteMapper;
import org.ldd.ssm.crm.service.FlyingAirRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class FlyingAirRouteServiceImpl implements FlyingAirRouteService {
	@Autowired
	private FlyingAirRouteMapper routeMapper;
	public boolean add(FlyingAirRoute info) {
		boolean result = false;
		int activeLine = routeMapper.insertSelective(info);
		if(activeLine == 1){
			result = true;
		}
		return result;
	}

	public boolean update(FlyingAirRoute info) {
		boolean result = false;
		int activeLine = routeMapper.updateByPrimaryKeySelective(info);
		if(activeLine == 1){
			result = true;
		}
		return result;
	}

	public boolean batchdel(int[] ids) {
		if(ids!=null&&ids.length>0){
			for(int i=0;i<ids.length;i++){
				int activeLine = routeMapper.deleteByPrimaryKey(ids[i]);
				if(activeLine==0){
					return false;
				}
			}
		}
		return true;
	}

	public List<FlyingAirRoute> search(String airRoute) {
		return routeMapper.selectByName(airRoute);
	}

	public FlyingAirRoute load(int id) {
		return routeMapper.selectByPrimaryKey(id);
	}

}
