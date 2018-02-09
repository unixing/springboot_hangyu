package org.ldd.ssm.crm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.Menu;
import org.ldd.ssm.crm.domain.Resource;
import org.ldd.ssm.crm.domain.Role;
import org.ldd.ssm.crm.service.IMenuService;
import org.ldd.ssm.crm.service.IResourceService;
import org.ldd.ssm.crm.service.IRoleService;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RoleAction {
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IResourceService resourceService;
	
	@RequestMapping("/role_show")
//	@MethodNote(comment="角色管理:12")
	@MyMethodNote(comment="角色主页:2")
	public String show(){
		return "permission/role";
	}
	
	@RequestMapping("/role_add")
	@ResponseBody
//	@MethodNote(comment="角色管理:12")
	@MyMethodNote(comment="添加角色:3")
	public Map<String,Object> add(Role role){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Employee emp = UserContext.getUser();
			role.setCreateId(emp.getId());
			boolean result = roleService.add(role);
			if(result){
				map.put("opResult", "0");
				map.put("message", "添加成功");
			}else{
				map.put("message", "添加失败");
			}
		} catch (Exception e) {
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
	@RequestMapping("/role_delete")
	@ResponseBody
//	@MethodNote(comment="角色管理:12")
	@MyMethodNote(comment="删除角色:3")
	public Map<String,Object> delete(Long id){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = roleService.delete(id);
			if(result){
				map.put("opResult", "0");
				map.put("message", "删除成功");
			}else{
				map.put("message", "删除失败");
			}
		} catch (Exception e) {
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
	@RequestMapping("/role_update")
	@ResponseBody
//	@MethodNote(comment="角色管理:12")
	@MyMethodNote(comment="修改角色:3")
	public Map<String,Object> update(Role role){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = roleService.update(role);
			if(result){
				map.put("opResult", "0");
				map.put("message", "修改成功");
			}else{
				map.put("message", "修改失败");
			}
		} catch (Exception e) {
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
	@RequestMapping("/role_search")
	@ResponseBody
//	@MethodNote(comment="角色管理:12")
	@MyMethodNote(comment="查询角色:2")
	public Map<String,Object> list(Long companyId,String name){
		Map<String,Object> map = new HashMap<String,Object>();
		//获取当前登录账户id
		Employee emp = UserContext.getUser();
		try {
//			companyId = 1l;//测试用
			List<Role> list = null;
			if(emp.getUsrSts()==99){//状态99为系统内置超级管理员
				list = roleService.selectRoles(null, null,name);
			}else{
				list = roleService.selectRoles(emp.getId(), companyId,name);
			}
			map.put("opResult", "0");
			map.put("list", list);
		} catch (Exception e) {
			map.put("opResult", "1");
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/role_load")
//	@MethodNote(comment="角色管理:12")
	@ResponseBody
	public Map<String,Object> load(Long id){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Role role = roleService.load(id);
			map.put("opResult", "0");
			map.put("role", role);
		} catch (Exception e) {
			map.put("opResult", "1");
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/role_getList")
//	@MethodNote(comment="角色管理:12")
	@ResponseBody
	public Map<String,Object> getList(Long companyId,Long relateId){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<Role> roles = null;
			Employee emp = UserContext.getUser();
			if(emp.getUsrSts()==99){
				roles = roleService.getList(companyId,null,relateId);
			}else{
				roles = roleService.getList(companyId,emp.getId(),relateId);
			}
			map.put("opResult", "0");
			map.put("list", roles);
		} catch (Exception e) {
			map.put("opResult", "1");
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/role_showPermission")
	@ResponseBody
//	@MethodNote(comment="角色管理:12")
	@MyMethodNote(comment="设置权限:3")
	public Map<String,Object> showPermission(Long companyId,Long roleId){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Employee emp = UserContext.getUser();
			List<Menu> menuList = null;
			List<Resource> resourceList = null;
			if(emp.getUsrSts()==99){
				menuList = menuService.showList(companyId, null, roleId);
				resourceList = resourceService.getResourceList(null, companyId,roleId);
			}else{
				menuList = menuService.showList(companyId,emp.getId(),roleId);
				resourceList = resourceService.getResourceList(emp.getId(), companyId,roleId);
			}
			if(menuList!=null&&menuList.size()>0){
				if(resourceList!=null&&resourceList.size()>0){
					for(int i=0;i<menuList.size();i++){
						Menu menu=menuList.get(i);
						List<Menu> children = menu.getChiledren();
						if(children!=null&&children.size()>0){
							for(int k=0;k<children.size();k++){
								Menu child = children.get(k);
								List<Resource> resources = new ArrayList<Resource>();
								for(int j=0;j<resourceList.size();j++){
									Resource resource = resourceList.get(j);
									if(resource.getMenuId().longValue()==child.getId().longValue()){
										resources.add(resource);
									}
								}
								child.setResources(resources);
							}
						}
					}
				}
			}
			map.put("opResult", "0");
			map.put("menuList", menuList);
		} catch (Exception e) {
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/role_savePermission")
	@ResponseBody
//	@MethodNote(comment="角色管理:12")
	@MyMethodNote(comment="保存权限:3")
	public Map<String,Object> savePermission(String menuIds,String resourceIds,Long roleId){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			String[] menus = null;
			if(menuIds!=null&&!"".equals(menuIds)){
				menus = menuIds.split(",");
			}
					
			String[] resources = null;
			if(resourceIds!=null&&!"".equals(resourceIds)){
				resources = resourceIds.split(",");
			}
			boolean result = roleService.savePermission(menus, resources, roleId);
			if(result){
				map.put("opResult", "0");
				map.put("message", "配置菜单资源成功");
			}else{
				map.put("message", "配置菜单资源失败");
			}
		} catch (Exception e) {
			map.put("message", "操作异常");
		}
		return map;
	}
	
	@RequestMapping("/role_getCurrentPermission")
//	@MethodNote(comment="角色管理:12")
	@ResponseBody
	public Map<String,Object> getCurrentPermission(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Employee emp = UserContext.getUser();
			Long companyId = Long.valueOf(UserContext.getCompanyId());
			if(emp.getUsrSts()==99){
				map = roleService.getCurrentPermission(companyId, null);
			}else{
				map = roleService.getCurrentPermission(companyId, emp.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
