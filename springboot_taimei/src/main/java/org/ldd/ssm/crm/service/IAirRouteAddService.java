package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.AirRoute;

/**
 * 航线添加
 * @author wxm
 *
 * @date 2017-4-5
 */
public interface IAirRouteAddService {
	
	public List<AirRoute> findAirRouteList(AirRoute airRoute);
	
	public boolean airRouteAdd(AirRoute airRoute);
	
	public boolean airRouteEditBatch(List<AirRoute> airRoute);
	
	public boolean airRouteDelete(long id);
	
	public List<String> findOnlineDataList(String airport);
}
