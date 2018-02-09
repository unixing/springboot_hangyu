package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.TimeResource;

public interface TimeResourceService {
	public boolean add(TimeResource resource);
	public boolean update(TimeResource resource);
	public boolean batchdel(int[] ids);
	public List<TimeResource> search(String terminal);
	public TimeResource load(int id);
}
