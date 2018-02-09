package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.DivideSeason;
import org.ldd.ssm.crm.query.MonthSalesPlanQuery;

public interface DivideSeasonMapper {
	public int add(DivideSeason season);
	public int update(DivideSeason season);
	public List<DivideSeason> list(MonthSalesPlanQuery query);
	public List<DivideSeason> list2(MonthSalesPlanQuery query);
	public DivideSeason load(MonthSalesPlanQuery query);
	public DivideSeason load2(MonthSalesPlanQuery query);
}
