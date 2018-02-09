package org.ldd.ssm.crm.web;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.MenuNew;
import org.ldd.ssm.crm.domain.RoleNew;
import org.ldd.ssm.crm.service.IRoleNewService;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoleNewAction {
	@Autowired
	private IRoleNewService roleNewService;
	
	@RequestMapping("/rolenew_add")
	@ResponseBody
	@MethodNote(comment="角色管理:11")
	@MyMethodNote(comment="添加角色:3")
	public Map<String,Object> add(String sn,String name,String menus){
		Map<String,Object> map = new HashMap<String,Object>();
		if(sn==null||"".equals(sn)||name==null||"".equals(name)||menus==null||"".equals(menus)){
			map.put("opResult", "3");
			return map;
		}
		try {
			JSONArray array = JSONArray.fromObject(menus);
			List<MenuNew> menuss=JSONArray.toList(array,MenuNew.class);
			RoleNew role = new RoleNew();
			role.setSn(sn);
			role.setName(name);
			role.setMenus(menuss);
			Employee emp = UserContext.getUser();
			role.setCreateId(emp.getId());
			//防止相同名称的角色出现
			List<RoleNew> roles = roleNewService.getRolesByRoleName(role.getCreateId(), role.getName());
			if(roles==null){
				map.put("opResult", "2");
				return map;
			}else if(roles.size()>0){
				map.put("opResult", "4");//角色名称重复
				return map;
			}
			boolean result = roleNewService.add(role);
			if(result){
				map.put("opResult", "0");
				map.put("id",role.getId());
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			map.put("opResult", "2");
			e.printStackTrace();
		}
		return map;
	}
	@RequestMapping("/rolenew_delete")
	@ResponseBody
	@MethodNote(comment="角色管理:11")
	@MyMethodNote(comment="删除角色:3")
	public Map<String,Object> delete(Long id){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = roleNewService.delete(id);
			if(result){
				map.put("opResult", "0");
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			map.put("opResult", "2");
			e.printStackTrace();
		}
		return map;
	}
	@RequestMapping("/rolenew_update")
	@ResponseBody
	@MethodNote(comment="角色管理:11")
	@MyMethodNote(comment="修改角色:3")
	public Map<String,Object> update(String rolestr){
		Map<String,Object> map = new HashMap<String,Object>();
		if(rolestr==null||"".equals(rolestr)){
			map.put("opResult", "3");
			return map;
		}
		try {
			JSONArray array = JSONArray.fromObject(rolestr);
			List<RoleNew> roles = JSONArray.toList(array,RoleNew.class);
			boolean result = false;
			if(roles!=null&&roles.size()>0){
				for(int i=0;i<roles.size();i++){
					RoleNew role = roles.get(i);
					//防止相同名称的角色出现
					List<RoleNew> roleList = roleNewService.getRolesByRoleName(UserContext.getUser().getId(), role.getName());
					if(roleList==null){
						map.put("opResult", "2");//服务器报错
						return map;
					}else if(roleList.size()>1){
						map.put("opResult", "4");//角色名称重复
						return map;
					}else if(roleList.size()==1){
						if(roleList.get(0).getId().longValue()!=role.getId().longValue()){
							map.put("opResult", "4");//角色名称重复
							return map;
						}
					}
					result = roleNewService.update(role);
					if(!result)
						break;
				}
				if(result){
					map.put("opResult", "0");
				}else{
					map.put("opResult", "1");
				}
			}else{
				map.put("opResult", "3");
			}
		} catch (Exception e) {
			map.put("opResult", "2");
			e.printStackTrace();
		}
		return map;
	}
	@RequestMapping("/rolenew_getList")
	@MethodNote(comment="角色管理:11")
	@ResponseBody
	public Map<String,Object> getList(String name){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<RoleNew> roles = null;
			roles = roleNewService.selectRoles(UserContext.getUser().getId(), name);
			map.put("opResult", "0");
			map.put("list", roles);
		} catch (Exception e) {
			map.put("opResult", "1");
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
}
