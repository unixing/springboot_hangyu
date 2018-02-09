package org.ldd.ssm.crm.web;

import org.ldd.ssm.crm.domain.SystemDictionaryItem;
import org.ldd.ssm.crm.query.SystemDictionaryItemObject;
import org.ldd.ssm.crm.query.SystemDictionaryItemQuery;
import org.ldd.ssm.crm.service.ISystemDictionaryItemService;
import org.ldd.ssm.crm.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User的前台控制器层
 */
@Controller
public class SystemDictionaryItemAction {
	// spring注解注入对象
	@Autowired
	private ISystemDictionaryItemService systemDictionaryItemService;
	//根据数据字典目录查询明细
	@RequestMapping("/getItemSystemDictionaryItem")
	@ResponseBody
	public SystemDictionaryItemObject<SystemDictionaryItem> getItem(SystemDictionaryItemQuery iQuery){
		SystemDictionaryItemObject<SystemDictionaryItem> systemDictionaryItemObject =systemDictionaryItemService.getItem(iQuery);
		return systemDictionaryItemObject;
	}
	@RequestMapping("/saveSystemDictionaryItem")
	@ResponseBody
	public AjaxResult save(SystemDictionaryItem system,Long did){
		system.setParent_id(did);
		if(system.getId()==null){
			systemDictionaryItemService.save(system);
			AjaxResult ajaxResult = new AjaxResult("数据字典明细保存成功！！");
			return ajaxResult;
		}
		systemDictionaryItemService.update(system);
		AjaxResult ajaxResult = new AjaxResult("数据字典明细更新成功！！");
		return ajaxResult;
	}
	@RequestMapping("/deleteSystemDictionaryItem")
	@ResponseBody
	public AjaxResult delete(Long did){
		systemDictionaryItemService.delete(did);
		AjaxResult ajaxResult = new AjaxResult("数据字典明细删除成功！！");
		return ajaxResult;
	}
	
}