package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.BuidSchedule;

public interface BuidScheduleService {
	public boolean add(BuidSchedule info);
	public boolean update(BuidSchedule info);
	public boolean batchdel(int[] ids);
	public List<BuidSchedule> search(String airportName);
	public BuidSchedule load(int id);
}
