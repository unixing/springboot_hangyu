package org.ldd.ssm.crm.service;

import org.ldd.ssm.crm.domain.AirportFlight;

/**
 * 机场航线
 * @author wxm
 *
 * @date 2017-3-30
 */
public interface AirportFlightService {
	public AirportFlight getAirportFlight(String iATA);
}
