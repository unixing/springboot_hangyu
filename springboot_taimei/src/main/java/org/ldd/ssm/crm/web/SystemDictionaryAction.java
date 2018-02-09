package org.ldd.ssm.crm.web;

import org.apache.commons.lang.StringUtils;
import org.ldd.ssm.crm.domain.SystemDictionary;
import org.ldd.ssm.crm.query.SystemDictionaryObject;
import org.ldd.ssm.crm.query.SystemDictionaryQuery;
import org.ldd.ssm.crm.service.ISystemDictionaryService;
import org.ldd.ssm.crm.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User的前台控制器层
 */
@Controller
public class SystemDictionaryAction {
	// spring注解注入对象
	@Autowired
	private ISystemDictionaryService systemDictionaryService;

	// springMVC请求的映射地址
	@RequestMapping("/systemDictionary")
	public String index() {
		// 重定向到欢迎页面,基于springMVC
		return "systemDictionary";
	}

	// springMVC请求的映射地址
	@RequestMapping("/getSystemDictionary")
	@ResponseBody
	public SystemDictionaryObject<SystemDictionary> getAll(SystemDictionaryQuery sQuery) {
		//将前台传入的空字符串设置 为null用于sql的if判断
		if(StringUtils.isEmpty(sQuery.getName())){
			sQuery.setName(null);
		}
		if(StringUtils.isEmpty(sQuery.getSn())){
			sQuery.setSn(null);
		}
		SystemDictionaryObject<SystemDictionary> query = systemDictionaryService.query(sQuery);
		return query;
	}
	//更新和保存数据
	@RequestMapping("/saveSystemDictionary")
	@ResponseBody
	public AjaxResult save(SystemDictionary system) {
		if(system.getId()==null){
			systemDictionaryService.save(system);
			AjaxResult ajaxResult = new AjaxResult("数据字典目录保存成功！！");
			return ajaxResult;
		}
		systemDictionaryService.update(system);
		AjaxResult ajaxResult = new AjaxResult("数据字典目录更新成功！！");
		return ajaxResult;
	}
	//删除数据字典目录
	@RequestMapping("/deleteSystemDictionary")
	@ResponseBody
	public AjaxResult delete(Long id) {
		systemDictionaryService.delete(id);
		AjaxResult ajaxResult = new AjaxResult("数据字典目录删除成功！！");
		return ajaxResult;
	}
	
}