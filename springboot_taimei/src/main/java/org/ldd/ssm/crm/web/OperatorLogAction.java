package org.ldd.ssm.crm.web;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.aop.OperatorLogService;
import org.ldd.ssm.crm.domain.OperatorLog;
import org.ldd.ssm.crm.query.OperatorLogQuery;
import org.ldd.ssm.crm.query.ProcessTaskObject;
import org.ldd.ssm.crm.utils.TextUtil;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 日志操作控制类
 * @Title:OperatorLogAction 
 * @Description:
 * @author taimei-gds 
 * @date 2016-7-12 下午4:38:50
 */
@Controller
public class OperatorLogAction {
	@Autowired
	OperatorLogService operatorLogService;
	//springMVC请求的映射地址
	@MyMethodNote(comment="日志主页:2")
	@RequestMapping("/operatorLog")
	public String operatorLog() {
			return "charts/log/operatorLog";
	}
	
	/**
	 * 
	 * @Title: operatorLogList 
	 * @Description:  
	 * @param @param operatorLog
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	@MyMethodNote(comment="日志主页数据:2")
//	@MethodNote(comment="操作日志列表:98")
	@RequestMapping("/operatorLogList")
	@ResponseBody
	public ProcessTaskObject<OperatorLog> operatorLogList(OperatorLogQuery operatorLogQuery){
		if("0".equals(operatorLogQuery.getLogrank())){
			operatorLogQuery.setLogrank(null);
		}
		if("0".equals(operatorLogQuery.getLogtype())){
			operatorLogQuery.setLogtype(null);
		}
		if("0".equals(operatorLogQuery.getOpresult())){
			operatorLogQuery.setOpresult(null);
		}else if("1".equals(operatorLogQuery.getOpresult())){
			operatorLogQuery.setOpresult("成功");
		}else {
			operatorLogQuery.setOpresult("失败");
		}
		if(TextUtil.isEmpty(operatorLogQuery.getOpName())){
			operatorLogQuery.setOpName(null);
		}
		
		if(TextUtil.isEmpty(operatorLogQuery.getOpIp())){
			operatorLogQuery.setOpIp(null);
		}
		if(TextUtil.isEmpty(operatorLogQuery.getStartDate())){
			operatorLogQuery.setStartDate(null);
		}
		if(TextUtil.isEmpty(operatorLogQuery.getEndDate())){
			operatorLogQuery.setEndDate(null);
		}
		ProcessTaskObject<OperatorLog> operatorLogObject = operatorLogService.getOperatorLogList(operatorLogQuery);
		return operatorLogObject;
	}
	/**
	 * 删除日志
	 * @Title: deleteAllOperatorLog 
	 * @Description:  
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	@MyMethodNote(comment="删除日志数据:3")
//	@MethodNote(comment="删除日志:98")
	@RequestMapping("/deleteAllOperatorLog")
	@ResponseBody
	public String deleteAllOperatorLog(){
		String ret = operatorLogService.deleteAllOperatorLog();
		return ret;
	}
	
	
}
