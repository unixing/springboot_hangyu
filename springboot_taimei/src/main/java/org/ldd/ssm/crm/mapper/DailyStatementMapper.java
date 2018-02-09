package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.DailyStatement;
import org.ldd.ssm.crm.domain.DailyStatementTotal;
import org.ldd.ssm.crm.query.SystemLogQuery;

public interface DailyStatementMapper {

	/**
	 * 基础功能
	 */
	
	void save(DailyStatement obj);
	
	void update(DailyStatement obj);
	
	void delete(Long id);
	
	DailyStatement get(Long id);
	
	List<DailyStatement> list(SystemLogQuery searchKey);

	/**
	 * 分页查询
	 * @param query
	 * @return
	 */
	Long count(SystemLogQuery query);
	/**
	 * 保存统计好的数据
	 */
	void saveCount(DailyStatementTotal dailyStatementTotal);

	List<DailyStatementTotal> findByGroupBy1(SystemLogQuery searchKey);

}
