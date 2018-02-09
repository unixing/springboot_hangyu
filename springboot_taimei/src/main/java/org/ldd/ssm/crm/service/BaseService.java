package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.Base;

public interface BaseService {
	public boolean add(Base info);
	public boolean update(Base info);
	public boolean batchdel(int[] ids);
	public List<Base> search(String baseName);
	public Base load(int id);
}
