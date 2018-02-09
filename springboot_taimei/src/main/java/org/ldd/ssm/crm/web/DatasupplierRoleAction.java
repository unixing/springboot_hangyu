package org.ldd.ssm.crm.web;

import java.util.HashMap;
import java.util.Map;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.service.IDatasupplierRoleService;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DatasupplierRoleAction {
	@Autowired
	private IDatasupplierRoleService datasupplierRoleService;
	
	@RequestMapping("/datasupplierrole_add")
	@ResponseBody
//	@MethodNote(comment="角色管理:12")
	@MyMethodNote(comment="配置角色:2")
	public Map<String,Object> add(String roleString,Long datasupplierId){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String[] roleIds = roleString.split(",");
			boolean result = datasupplierRoleService.addRoles(roleIds,datasupplierId);
			if(result){
				map.put("opResult", "0");
				map.put("message", "配置角色成功");
			}else{
				map.put("message","配置角色失败");
			}
		} catch (Exception e) {
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
}
