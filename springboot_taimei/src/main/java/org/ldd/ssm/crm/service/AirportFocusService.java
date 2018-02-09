package org.ldd.ssm.crm.service;

import org.ldd.ssm.crm.domain.AirportFocus;

public interface AirportFocusService {
	
	public boolean add(AirportFocus airportFocus);
	
	public boolean update(AirportFocus airportFocus);
	
	public boolean delete(Long id);
	
	public AirportFocus select(Long id);
}
