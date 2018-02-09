package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.DivideTime;

public interface DivideTimeService {
	public List<DivideTime> list(String fltDirect, int seasonId);
}
