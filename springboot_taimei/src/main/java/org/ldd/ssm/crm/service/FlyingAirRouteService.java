package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.FlyingAirRoute;

public interface FlyingAirRouteService {
	public boolean add(FlyingAirRoute info);
	public boolean update(FlyingAirRoute info);
	public boolean batchdel(int[] ids);
	public List<FlyingAirRoute> search(String airRoute);
	public FlyingAirRoute load(int id);
}
