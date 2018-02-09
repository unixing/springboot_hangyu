package org.ldd.ssm.crm.web;

import org.apache.commons.lang.StringUtils;
import org.ldd.ssm.crm.domain.SystemLog;
import org.ldd.ssm.crm.query.PageResult;
import org.ldd.ssm.crm.query.SystemLogQuery;
import org.ldd.ssm.crm.service.ISystemLogService;
import org.ldd.ssm.crm.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User的前台控制器层
 */
@Controller
public class SystemLogAction {
	// spring注解注入对象
	@Autowired
	private ISystemLogService systemLogService;

	// springMVC请求的映射地址
	@RequestMapping("/systemLog")
	public String index() {
		// 重定向到欢迎页面,基于springMVC
		return "systemLog";
	}

	// springMVC请求的映射地址
	@RequestMapping("/getSystemLog")
	@ResponseBody
	public PageResult<SystemLog> getAll(SystemLogQuery sQuery) {
		//将前台传入的空字符串设置 为null用于sql的if判断
		if(StringUtils.isEmpty(sQuery.getSearchKey())){
			sQuery.setSearchKey(null);
		}
		PageResult<SystemLog> query = systemLogService.query(sQuery);
		return query;
	}
	//更新和保存数据
	@RequestMapping("/saveSystemLog")
	@ResponseBody
	public AjaxResult save(SystemLog system) {
		if(system.getId()==null){
			systemLogService.save(system);
			AjaxResult ajaxResult = new AjaxResult("系统日志保存成功！！");
			return ajaxResult;
		}
		systemLogService.update(system);
		AjaxResult ajaxResult = new AjaxResult("系统日志更新成功！！");
		return ajaxResult;
	}
	//删除系统日志
	@RequestMapping("/deleteSystemLog")
	@ResponseBody
	public AjaxResult delete(Long id) {
		systemLogService.delete(id);
		AjaxResult ajaxResult = new AjaxResult("系统日志删除成功！！");
		return ajaxResult;
	}
	
}