package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.TransportInfo;

public interface TransportInfoService {
	public boolean add(TransportInfo info);
	public boolean update(TransportInfo info);
	public boolean batchdel(int[] ids);
	public List<TransportInfo> search(String airportName);
}
