package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.SystemLog;
import org.ldd.ssm.crm.query.PageResult;
import org.ldd.ssm.crm.query.SystemLogQuery;
/**
 *系统日志的service层的高级查询对象
 */
public interface ISystemLogService {
	
	void save(SystemLog obj);
	
	void update(SystemLog obj);
	
	void delete(Long id);
	
	SystemLog get(Long id);
	
	List<SystemLog> list();
	
	PageResult<SystemLog> query(SystemLogQuery query);


}
