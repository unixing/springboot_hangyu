package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.AirRoute;

/**
 * 航线添加
 * @author wxm
 *
 * @date 2017-4-5
 */
public interface AirRouteAddMapper {
	
	public List<AirRoute> findAirRouteList(AirRoute airRoute);
	
	//直接用之前无数据航线的接口
	//public List<String> findAirRoute(AirRoute airRoute);
	
	public int airRouteAdd(AirRoute airRoute);
	
	public int airRouteEdit(AirRoute airRoute);
	
	public int airRouteEditBatch(List<AirRoute> airRoute);
	
	public int airRouteDelete(long id);
	
	public List<String> findOnlineDataList(String itia);
}
