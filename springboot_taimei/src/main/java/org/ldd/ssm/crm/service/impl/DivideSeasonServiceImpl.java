package org.ldd.ssm.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.ldd.ssm.crm.domain.DivideSeason;
import org.ldd.ssm.crm.mapper.DivideSeasonMapper;
import org.ldd.ssm.crm.query.MonthSalesPlanQuery;
import org.ldd.ssm.crm.service.DivideSeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DivideSeasonServiceImpl implements DivideSeasonService {
	@Autowired
	private DivideSeasonMapper seasonMapper;

	public List<DivideSeason> list(MonthSalesPlanQuery query) {
		List<DivideSeason> list = new ArrayList<DivideSeason>();
		list = seasonMapper.list(query);
		return list;
	}
}
