package org.ldd.ssm.crm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.BuidSchedule;
import org.ldd.ssm.crm.service.BuidScheduleService;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class BuidScheduleAction {
	@Autowired
	private BuidScheduleService buidService;
	@RequestMapping("/buidschedule_add")
	@ResponseBody
//	@MethodNote(comment="添加机场建设:75")
	@MyMethodNote(comment="添加机场建设:3")
	public Map<String,String> add(BuidSchedule buid){
		Map<String,String> rootMap = new HashMap<String,String>();
		boolean result = buidService.add(buid);
		if(result){
			rootMap.put("opResult","0");
			rootMap.put("msg", "添加成功");
		}else{
			rootMap.put("opResult","1");
			rootMap.put("msg", "添加失败");
		}
		return rootMap;
	}

	@RequestMapping("/buidschedule_update")
	@ResponseBody
//	@MethodNote(comment="修改机场建设:75")
	@MyMethodNote(comment="修改机场建设:3")
	public Map<String,String> update(BuidSchedule buid){
		Map<String,String> rootMap = new HashMap<String,String>();
		boolean result = buidService.update(buid);
		if(result){
			rootMap.put("opResult","0");
			rootMap.put("msg", "修改成功");
		}else{
			rootMap.put("opResult","1");
			rootMap.put("msg", "修改失败");
		}
		return rootMap;
	}

	@RequestMapping("/buidschedule_batchdel")
	@ResponseBody
//	@MethodNote(comment="删除机场建设:75")
	@MyMethodNote(comment="删除机场建设:3")
	public Map<String,String> batchdel(String ids){
		Map<String,String> rootMap = new HashMap<String,String>();
		String[] strs = ids.split(",");
		int[] id = new int[strs.length];
		for(int i=0;i<strs.length;i++){
			id[i] = Integer.valueOf(strs[i]);
		}
		boolean result = buidService.batchdel(id);
		if(result){
			rootMap.put("opResult","0");
			rootMap.put("msg", "删除成功");
		}else{
			rootMap.put("opResult","1");
			rootMap.put("msg", "删除失败");
		}
		return rootMap;
	}
	@RequestMapping("/buidschedule_search")
	@ResponseBody
//	@MethodNote(comment="查询机场建设:75")
	@MyMethodNote(comment="查询机场建设:2")
	public Map<String,Object> search(String airportname){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		List<BuidSchedule> buid = buidService.search(airportname);
		if(buid!=null){
			rootMap.put("result", 0);
			rootMap.put("info", buid);
		}else{
			rootMap.put("result", 1);
			rootMap.put("msg", "没有查询到相关数据");
		}
		return rootMap;
	}
	@RequestMapping("/buidschedule_load")
	@ResponseBody
	@MyMethodNote(comment="查询机场建设详情:2")
	public BuidSchedule load(int id){
		return buidService.load(id);
	}
}
