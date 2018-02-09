package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.SaleDptInfo;

public interface SaleDptInfoService {
	public boolean add(SaleDptInfo info);
	public boolean update(SaleDptInfo info);
	public boolean batchdel(int[] ids);
	public List<SaleDptInfo> search(String dptName);
	public SaleDptInfo load(int id);
}
