package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.SystemLog;
import org.ldd.ssm.crm.query.SystemLogQuery;
/**
 *系统日志的dao接口
 */
public interface SystemLogMapper {

	
	void save(SystemLog obj);
	
	void update(SystemLog obj);
	
	void delete(Long id);
	
	SystemLog get(Long id);
	
	List<SystemLog> list();

	List<SystemLog> query(SystemLogQuery query);

	Long count(SystemLogQuery query);
}
