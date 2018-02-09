package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.DailyStatement;
import org.ldd.ssm.crm.domain.DailyStatementTotal;
import org.ldd.ssm.crm.query.PageResult;
import org.ldd.ssm.crm.query.SystemLogQuery;

/**
 * 日报表的服务接口
 */
public interface IDailyStatementService {

	PageResult<DailyStatement> list(SystemLogQuery searchKey);

	void save(DailyStatement dailyStatement, DailyStatementTotal dailyStatementTotal);

	List<DailyStatementTotal> findByGroupBy1(SystemLogQuery searchKey);

}
