package org.ldd.ssm.crm.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.Resource;
import org.ldd.ssm.crm.service.IResourceService;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResourceAction {
	//spring注解注入对象
	@Autowired
	private IResourceService resourceService;
	
	//springMVC请求的映射地址
	@RequestMapping("/resource")
	public String execut(){
		//重定向到欢迎页面,基于springMVC
		return "DOWIndex";
	}
	/**
	 * 	删除操作
	 */
	@RequestMapping("/resource_delete")
//	@MethodNote(comment="默认:18")
	@ResponseBody
	public Map<String, Object> delete(Long id){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			resourceService.delete(id);
			
			map.put("success", true);
			map.put("success", "删除成功！");
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "操作失败："+e.getMessage());
			map.put("success", e.getMessage());
		}
		//重定向到欢迎页面,基于springMVC
		return map;
	}
	
	/**
	 * 	保存操作
	 */
	@RequestMapping("/resource_save")
//	@MethodNote(comment="默认:18")
	@ResponseBody
	public Map<String, Object> save(Resource res){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if(res.getId()==null){
				resourceService.save(res);
				
				map.put("success", true);
				map.put("success", "保存成功！");
			}else{
				resourceService.update(res);
				
				map.put("success", true);
				map.put("success", "修改成功");
			}
			
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "保存失败:"+e.getMessage());
			map.put("success", e.getMessage());
		}
		//重定向到欢迎页面,基于springMVC
		return map;
	}
	
	@RequestMapping("/resource_list")
//	@MethodNote(comment="默认:18")
	@ResponseBody
	public Map<String,Object> getResourceList(Long companyId,Long roleId){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Employee emp = UserContext.getUser();
			List<Resource> list = null;
			if(emp.getUsrSts()==99){
				list = resourceService.getResourceList(null, companyId,roleId);
			}else{
				list = resourceService.getResourceList(emp.getId(), companyId,roleId);
			}
			map.put("opResult", "0");
			map.put("list", list);
		} catch (Exception e) {
			map.put("opResult", "1");
			map.put("message", e.getMessage());
		}
		return map;
	}
	@RequestMapping("/resource_scanResource")
//	@MethodNote(comment="默认:18")
	public String scanResource(){
		try {
			resourceService.scanResource();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "/index/index";
	}
	@RequestMapping("/resource_hasNoPermission")
//	@MethodNote(comment="默认:18")
	@ResponseBody
	public Map<String,Object> backToShow(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("message", "您暂时没有改操作权限");
		return map;
	}
}
