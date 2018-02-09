package org.ldd.ssm.crm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.Datasupplier;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.service.IDatasupplierService;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DatasupplierAction {
	@Autowired
	private IDatasupplierService datasupplierService;

	@RequestMapping("/datasupplier_show")
	@MyMethodNote(comment="数据源主页:2")
	public String display(){
		return "permission/datasupplier";
	}
	
	@RequestMapping("/datasupplier_add")
	@ResponseBody
//	@MethodNote(comment="添加数据源:95")
	@MyMethodNote(comment="添加数据源:3")
	public Map<String,Object> add(Datasupplier datasupplier){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = datasupplierService.add(datasupplier);
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
	
	@RequestMapping("/datasupplier_update")
	@ResponseBody
//	@MethodNote(comment="修改数据源:95")
	@MyMethodNote(comment="修改数据源:3")
	public Map<String,Object> update(Datasupplier datasupplier){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = datasupplierService.update(datasupplier);
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
	
	@RequestMapping("/datasupplier_delete")
	@ResponseBody
//	@MethodNote(comment="删除数据源:95")
	@MyMethodNote(comment="删除数据源:3")
	public Map<String,Object> delete(Long id){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			boolean result = datasupplierService.delete(id);
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
	
	@RequestMapping("/datasupplier_get")
	@ResponseBody
	public Map<String,Object> get(Long employeeId,Long companyId){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Datasupplier datasupplier = datasupplierService.load(employeeId, companyId);
			map.put("opResult", "0");
			map.put("obj", datasupplier);
		} catch (Exception e) {
			map.put("opResult", "1");
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/datasupplier_load")
	@ResponseBody
	public Map<String,Object> load(Long id){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Datasupplier datasupplier = datasupplierService.load(id);
			map.put("opResult", "0");
			map.put("obj", datasupplier);
		} catch (Exception e) {
			map.put("opResult", "1");
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/datasupplier_list")
	@ResponseBody
//	@MethodNote(comment="查询数据源:95")
	@MyMethodNote(comment="查询数据源:3")
	public Map<String,Object> list(Long companyId,String cpyNm){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Employee emp = UserContext.getUser();
			List<Datasupplier> list = null;
			if(emp.getUsrSts()==99){//系统内置管理
				list = datasupplierService.selectAll(null,cpyNm);
			}else{
				list = datasupplierService.selectAll(companyId,cpyNm);
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
	
	@RequestMapping("/datasupplier_datasource")
	@ResponseBody
	public Map<String,Object> getDataSource(Long companyId){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<String> ways = datasupplierService.getDataSource(companyId);
			map.put("opResult", "0");
			map.put("list", ways);
		} catch (Exception e) {
			map.put("message", "操作异常");
			e.printStackTrace();
		}
		return map;
	}
}
