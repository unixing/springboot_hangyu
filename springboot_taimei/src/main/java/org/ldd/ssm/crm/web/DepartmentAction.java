package org.ldd.ssm.crm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.Department;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.service.IDepartmentService;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DepartmentAction {
	@Autowired
	private IDepartmentService departmentService;
	
	@RequestMapping("/department_show")
//	@MethodNote(comment="部门管理:10")
	@MyMethodNote(comment="部门主页:2")
	public String display(){
		return "permission/department";
	}
	
	@RequestMapping("/department_add")
	@ResponseBody
//	@MethodNote(comment="部门管理:10")
	@MyMethodNote(comment="添加部门:3")
	public Map<String,Object> add(Department department){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = departmentService.add(department);
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
	
	@RequestMapping("/department_update")
	@ResponseBody
//	@MethodNote(comment="部门管理:10")
	@MyMethodNote(comment="修改部门:3")
	public Map<String,Object> update(Department department){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = departmentService.update(department);
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
	
	@RequestMapping("/department_delete")
	@ResponseBody
//	@MethodNote(comment="部门管理:10")
	@MyMethodNote(comment="删除部门:3")
	public Map<String,Object> delete(Long id){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = departmentService.delete(id);
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
	
	@RequestMapping("/department_list")
	@ResponseBody
//	@MethodNote(comment="部门管理:10")
	@MyMethodNote(comment="查询部门:3")
	public Map<String,Object> list(Long companyId,String dptNm){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<Department> list = null;
			Employee user = UserContext.getUser();
			if(user!=null&&user.getUsrSts()==99){
				list = departmentService.list(null,dptNm);
			}else{
				list = departmentService.list(companyId,dptNm);
			}
			map.put("opResult", "0");
			map.put("list", list);
		} catch (Exception e) {
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/department_load")
//	@MethodNote(comment="部门管理:10")
	@ResponseBody
	public Map<String,Object> load(Long id){
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			Department department = departmentService.load(id);
			map.put("opResult", "0");
			map.put("obj", department);
		} catch (Exception e){
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
}
