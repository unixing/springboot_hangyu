package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.FlightRoute;

public interface FlightRouteService {
	public List<FlightRoute> selectByEmployee(Long employeeId, String itia);
}
