package org.ldd.ssm.crm.web;

import org.apache.commons.lang.StringUtils;
import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.Permission;
import org.ldd.ssm.crm.query.PermissionObject;
import org.ldd.ssm.crm.query.PermissionQuery;
import org.ldd.ssm.crm.service.IPermissionService;
import org.ldd.ssm.crm.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 权限的控制层
 */
@Controller
public class PermissionAction {

	@Autowired
	private IPermissionService permissionService;
	//定位到页面
	@RequestMapping("/permission")
	public String index(){
		return "permission/role";
	}
	//获得数据
//	@RequestMapping("/listPermission")
//	@ResponseBody
//	public List<Permission> list(){
//		return permissionService.getAll();
//	}
	//保存和更新
	@RequestMapping("/savePermission")
	@ResponseBody
	@MyMethodNote(comment="权限控制保存:3")
	public AjaxResult save(Permission system){
		if(system.getId()==null){
			permissionService.save(system);
			AjaxResult ajaxResult = new AjaxResult("数据字典目录保存成功！！");
			return ajaxResult;
		}
		permissionService.update(system);
		AjaxResult ajaxResult = new AjaxResult("数据字典目录更新成功！！");
		return ajaxResult;
	}
	// 高级查询
	@RequestMapping("/listPermission")
	@ResponseBody
	@MyMethodNote(comment="权限控制列表:2")
	public PermissionObject<Permission> getAll(PermissionQuery sQuery) {
		//将前台传入的空字符串设置 为null用于sql的if判断
		if(StringUtils.isEmpty(sQuery.getName())){
			sQuery.setName(null);
		}
		if(StringUtils.isEmpty(sQuery.getResource())){
			sQuery.setResource(null);
		}
		PermissionObject<Permission> query = permissionService.query(sQuery);
		return query;
	}
	//删除
	@RequestMapping("/deletePermission")
	@ResponseBody
	@MyMethodNote(comment="权限控制删除:3")
	public AjaxResult delete(Long id) {
		permissionService.delete(id);
		AjaxResult ajaxResult = new AjaxResult("数据字典目录删除成功！！");
		return ajaxResult;
	}
}
