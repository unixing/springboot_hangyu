package org.ldd.ssm.crm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.EmployeeRole;
import org.ldd.ssm.crm.service.IEmployeeRoleService;
import org.ldd.ssm.crm.service.IEmployeeService;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmployeeRoleAction {
	
	@Autowired
	private IEmployeeRoleService iEmployeeRoleService;
	@Autowired
	private IEmployeeService employeeService;
	Logger log = Logger.getLogger(EmployeeAction.class);
	@RequestMapping("/roleEmployeeAdd")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> add(String compellation,String phone,String rolestr){
		Map<String,Object> map = new HashMap<String,Object>();
		if(compellation==null||"".equals(compellation)||phone==null||"".equals(phone)||rolestr==null||"".equals(rolestr)){
			map.put("opResult", "3");
			return map;
		}
		Employee user=employeeService.getEmployeeByPhone(phone);
		if(user!=null){
			map.put("opResult", "4");//手机已经被注册了
			return map;
		}
		try {
			Employee emp = new Employee();
			emp.setCompellation(compellation);
			emp.setPhone(phone);
			emp.setRoleId(Long.valueOf(rolestr));
			boolean result = iEmployeeRoleService.add(emp);
			if(result){
				map.put("id", emp.getId());
				map.put("opResult", "0");
			}else
				map.put("opResult", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/roleEmployeeUpdate")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> update(String empstr){
		Map<String,Object> map = new HashMap<String,Object>();
		if(empstr==null||"".equals(empstr)){
			map.put("opResult", "3");
			return map;
		}
		try {
			JSONArray array=JSONArray.fromObject(empstr);
			List<Employee> emps=JSONArray.toList(array,Employee.class);
			//验证手机号是否被注册了多次
			for(Employee emp : emps){
				Employee user = employeeService.getEmployeeByPhone(emp.getPhone());
				if(user!=null&&user.getId().longValue()!=emp.getId().longValue()){
					map.put("opResult", "4");
					return map;
				}
			}
			boolean result = iEmployeeRoleService.update(emps);
			if(result){
				map.put("opResult", "0");
			}else
				map.put("opResult", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/roleEmployeeDelete")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> delete(Long empId,Long roleId){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			EmployeeRole empRole = new EmployeeRole();
			empRole.setEmployeeId(empId);
			empRole.setRoleId(roleId);
			boolean result = iEmployeeRoleService.delete(empRole);
			if(result){
				map.put("opResult", "0");
			}else
				map.put("opResult", "1");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
}
