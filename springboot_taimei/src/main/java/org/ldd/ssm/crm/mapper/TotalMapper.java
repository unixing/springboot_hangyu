package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.DailyStatementTotal;

/**
 * 数据合计报表的
 */
public interface TotalMapper {
	List<DailyStatementTotal> list();
}
