package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.CityInfo;

public interface CityInfoService {
	public boolean add(CityInfo info);
	public boolean update(CityInfo info);
	public boolean batchdel(int[] ids);
	public List<CityInfo> search(String cityName);
	public CityInfo load(int id);
}
