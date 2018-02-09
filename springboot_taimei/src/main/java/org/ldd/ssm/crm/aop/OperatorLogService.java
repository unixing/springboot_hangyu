/**   
 * @Title: saveOperatorLog.java 
 * @Package org.ldd.ssm.crm.aop 
 * @Description: TODO
 * @author taimei-gds   
 * @date 2016-7-11 上午9:53:03 
 * @version V1.0   
 */ 
package org.ldd.ssm.crm.aop;

import java.util.List;

import org.ldd.ssm.crm.domain.OperatorLog;
import org.ldd.ssm.crm.mapper.OperatorLogMapper;
import org.ldd.ssm.crm.query.OperatorLogQuery;
import org.ldd.ssm.crm.query.ProcessTaskObject;
import org.ldd.ssm.crm.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Title:saveOperatorLog 
 * @Description:
 * @author taimei-gds 
 * @date 2016-7-11 上午9:53:03
 */
@Component
public class OperatorLogService {
	@Autowired
	private OperatorLogMapper operatorLogMapper;
	/**
	 * 保存日志
	 * @Title: saveOperatorLog 
	 * @Description:  
	 * @param @param operatorLog    
	 * @return void     
	 * @throws
	 */
	public void saveOperatorLog(OperatorLog operatorLog){
		//先删除多余10000条的数据，在保存
//		String minId = operatorLogMapper.getMinLog();
//		operatorLogMapper.deleteOperatorLog(minId);
		operatorLogMapper.saveOperatorLog(operatorLog);
	};
	
	public ProcessTaskObject<OperatorLog> getOperatorLogList(OperatorLogQuery operatorLogQuery){
		//记录
		List<OperatorLog> operatorLogList = operatorLogMapper.getOperatorLogList(operatorLogQuery);
		for (OperatorLog operatorLog : operatorLogList) {
			String params = operatorLog.getParams();
			if(TextUtil.isEmpty(params)&&params.contains("[")){
				operatorLog.setParams(params);
			}else{
				operatorLog.setParams("");
			}
			switch (Integer.parseInt(operatorLog.getLogRank())) {
			case 1: operatorLog.setLogRank("信息");	break;
			case 3: operatorLog.setLogRank("错误");	break;
			}
			switch (Integer.parseInt(operatorLog.getLogType())) {
			case 1: operatorLog.setLogType("登陆");	break;
			case 2: operatorLog.setLogType("功能访问");	break;
			case 3: operatorLog.setLogType("数据操作");	break;
			case 5: operatorLog.setLogType("系统信息");	break;
			}
		}
		//总条数
		int total = operatorLogMapper.getTotalCount(operatorLogQuery);
		return new ProcessTaskObject<OperatorLog>(operatorLogList, total);
	}
	
	public String deleteAllOperatorLog(){
		operatorLogMapper.deleteAllOperatorLog();
		return "删除所有日志成功";
	}
}
