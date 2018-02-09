package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.AirportInfo;

public interface AirportInfoService {
	public boolean add(AirportInfo info);
	public boolean update(AirportInfo info);
	public boolean batchdel(int[] ids);
	public List<AirportInfo> search(String airportName);
	public AirportInfo load(int id);
}
