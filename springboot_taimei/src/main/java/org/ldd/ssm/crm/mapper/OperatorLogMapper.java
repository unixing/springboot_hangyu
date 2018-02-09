package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.OperatorLog;
import org.ldd.ssm.crm.query.OperatorLogQuery;
/**
 *  日志操作mapper
 * @Title:OperatorLogMapper 
 * @Description:
 * @author taimei-gds 
 * @date 2016-7-11 上午9:57:42
 */
public interface OperatorLogMapper {

	void saveOperatorLog(OperatorLog operatorLog);
	
	String getMinLog();
	
	List<OperatorLog> getOperatorLogList(OperatorLogQuery operatorLogQuery);
	
	int getTotalCount(OperatorLogQuery operatorLogQuery);
	void deleteOperatorLog(String minId);
	void deleteAllOperatorLog();
}
