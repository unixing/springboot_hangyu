package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.DivideSeason;
import org.ldd.ssm.crm.query.MonthSalesPlanQuery;

public interface DivideSeasonService {
	public List<DivideSeason> list(MonthSalesPlanQuery query);
}
