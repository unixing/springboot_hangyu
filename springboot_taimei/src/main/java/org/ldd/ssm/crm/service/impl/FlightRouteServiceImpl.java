package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.ldd.ssm.crm.domain.FlightRoute;
import org.ldd.ssm.crm.mapper.FlightRouteMapper;
import org.ldd.ssm.crm.service.FlightRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class FlightRouteServiceImpl implements FlightRouteService {
	@Autowired
	private FlightRouteMapper objMapper;
	Logger log = Logger.getLogger(FlightRouteServiceImpl.class);
	public List<FlightRoute> selectByEmployee(Long employeeId, String itia) {
		List<FlightRoute> list = null;
		try {
			list = objMapper.selectByEmployee(employeeId, itia);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return list;
	}

}
