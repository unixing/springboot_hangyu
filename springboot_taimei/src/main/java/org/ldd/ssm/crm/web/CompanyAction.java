package org.ldd.ssm.crm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.Company;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.service.ICompanyService;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.ClassNote;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CompanyAction {

	@Autowired
	private ICompanyService companyService;
	
	@RequestMapping("/company_show")
	public String display(){
		return "permission/company";
	}
	@RequestMapping("/company_list")
	@ResponseBody
//	@MethodNote(comment="查询公司:91")
	@MyMethodNote(comment="查询公司:2")
	public Map<String,Object> list(String cpyNm){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<Company> list = null;
			Employee emp = UserContext.getUser();
			if(emp.getUsrSts()==99){
				list = companyService.selectAll(cpyNm,null);
			}else{
				list = companyService.selectAll(cpyNm,emp.getId());
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
	
	@RequestMapping("/company_add")
	@ResponseBody
//	@MethodNote(comment="添加公司:91")
	@MyMethodNote(comment="添加公司:3")
	public Map<String,Object> add(Company company){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = companyService.add(company);
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
	
	@RequestMapping("/company_update")
	@ResponseBody
//	@MethodNote(comment="修改公司:91")
	@MyMethodNote(comment="修改公司:3")
	public Map<String,Object> update(Company company){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = companyService.update(company);
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
	
	@RequestMapping("/company_delete")
	@ResponseBody
//	@MethodNote(comment="删除公司:91")
	@MyMethodNote(comment="删除公司:3")
	public Map<String,Object> delete(Long id){
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			boolean result = companyService.delete(id);
			if(result){
				map.put("opResult", "0");
				map.put("message", "删除成功");
			}else{
				map.put("message", "删除失败");
			}
		} catch (Exception e){
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/company_load")
	@ResponseBody
	public Map<String,Object> load(Long id){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Company company = companyService.load(id);
			map.put("opResult", "0");
			map.put("company", company);
		} catch (Exception e) {
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/company_getList")
	@ResponseBody
	public Map<String,Object> getList(){
		Employee emp = UserContext.getUser();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			int status = emp.getUsrSts();//状态为99则登录账户为系统内置超级管理
			List<Company> list = null;
			if(status==99){
				list = companyService.getList(null);
				list.get(0).setType(1);
			}else{
				list = companyService.getList(emp.getId());
				for(int i=0;i<list.size();i++){
					if(list.get(i).getId().equals(emp.getDepartmentId())){
						list.get(i).setType(1);
					}
				}
			}
			map.put("opResult", "0");
			map.put("list", list);
		} catch (Exception e) {
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/company_selectList")
	@ResponseBody
	public Map<String,Object> selectCompanyList(){
		Employee emp = UserContext.getUser();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<Company> list = companyService.selectCompanyByEmployeeId(emp.getId());
			if(emp.getUsrSts()==99){
				list = companyService.selectCompanyByEmployeeId(null);
			}else{
				list = companyService.selectCompanyByEmployeeId(emp.getId());
			}
			map.put("opResult", "0");
			map.put("list", list);
		} catch (Exception e) {
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
} 
