package org.ldd.ssm.crm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.Base;
import org.ldd.ssm.crm.service.BaseService;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class BaseAction {
	@Autowired
	private BaseService baseService;
	@RequestMapping("/base_add")
	@ResponseBody
//	@MethodNote(comment="添加基地:76")
	@MyMethodNote(comment="添加基地:3")
	public Map<String,String> add(Base base){
		Map<String,String> rootMap = new HashMap<String,String>();
		boolean result = baseService.add(base);
		if(result){
			rootMap.put("opResult","0");
			rootMap.put("msg", "添加成功");
		}else{
			rootMap.put("opResult","1");
			rootMap.put("msg", "添加失败");
		}
		return rootMap;
	}

	@RequestMapping("/base_update")
	@ResponseBody
//	@MethodNote(comment="修改基地:76")
	@MyMethodNote(comment="修改基地:3")
	public Map<String,String> update(Base base){
		Map<String,String> rootMap = new HashMap<String,String>();
		boolean result = baseService.update(base);
		if(result){
			rootMap.put("opResult","0");
			rootMap.put("msg", "修改成功");
		}else{
			rootMap.put("opResult","1");
			rootMap.put("msg", "修改失败");
		}
		return rootMap;
	}

	@RequestMapping("/base_batchdel")
	@ResponseBody
//	@MethodNote(comment="删除基地:76")
	@MyMethodNote(comment="删除基地:3")
	public Map<String,String> batchdel(String ids){
		Map<String,String> rootMap = new HashMap<String,String>();
		String[] strs = ids.split(",");
		int[] id = new int[strs.length];
		for(int i=0;i<strs.length;i++){
			id[i] = Integer.valueOf(strs[i]);
		}
		boolean result = baseService.batchdel(id);
		if(result){
			rootMap.put("opResult","0");
			rootMap.put("msg", "删除成功");
		}else{
			rootMap.put("opResult","1");
			rootMap.put("msg", "删除失败");
		}
		return rootMap;
	}
	@RequestMapping("/base_search")
	@ResponseBody
//	@MethodNote(comment="查询基地:76")
	@MyMethodNote(comment="查询基地:2")
	public Map<String,Object> search(String basename){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		List<Base> base = baseService.search(basename);
		if(base!=null){
			rootMap.put("result", 0);
			rootMap.put("info", base);
		}else{
			rootMap.put("result", 1);
			rootMap.put("msg", "没有查询到相关数据");
		}
		return rootMap;
	}
	@MyMethodNote(comment="基地主页:2")
	@RequestMapping("/base_display")
	public String display(){
		return "charts/shipinfo/shipinfo";
	}
	@RequestMapping("/base_load")
	@ResponseBody
	public Base load(int id){
		return baseService.load(id);
	}
}
