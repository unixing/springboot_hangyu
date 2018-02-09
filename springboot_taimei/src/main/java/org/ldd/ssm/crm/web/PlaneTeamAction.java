package org.ldd.ssm.crm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.PlaneTeam;
import org.ldd.ssm.crm.service.PlaneTeamService;
import org.ldd.ssm.crm.web.interceptor.ClassNote;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class PlaneTeamAction {
	@Autowired
	private PlaneTeamService teamService;
	@RequestMapping("/planeteam_add")
	@ResponseBody
//	@MethodNote(comment="添加机队:76")
	@MyMethodNote(comment="添加机队:3")
	public Map<String,String> add(PlaneTeam team){
		Map<String,String> rootMap = new HashMap<String,String>();
		boolean result = teamService.add(team);
		if(result){
			rootMap.put("opResult","0");
			rootMap.put("msg", "添加成功");
		}else{
			rootMap.put("opResult","1");
			rootMap.put("msg", "添加失败");
		}
		return rootMap;
	}

	@RequestMapping("/planeteam_update")
	@ResponseBody
//	@MethodNote(comment="修改机队")
	@MyMethodNote(comment="修改机队:3")
	public Map<String,String> update(PlaneTeam team){
		Map<String,String> rootMap = new HashMap<String,String>();
		boolean result = teamService.update(team);
		if(result){
			rootMap.put("opResult","0");
			rootMap.put("msg", "修改成功");
		}else{
			rootMap.put("opResult","1");
			rootMap.put("msg", "修改失败");
		}
		return rootMap;
	}

	@RequestMapping("/planeteam_batchdel")
	@ResponseBody
//	@MethodNote(comment="删除机队:76")
	@MyMethodNote(comment="删除机队:3")
	public Map<String,String> batchdel(String ids){
		Map<String,String> rootMap = new HashMap<String,String>();
		String[] strs = ids.split(",");
		int[] id = new int[strs.length];
		for(int i=0;i<strs.length;i++){
			id[i] = Integer.valueOf(strs[i]);
		}
		boolean result = teamService.batchdel(id);
		if(result){
			rootMap.put("opResult","0");
			rootMap.put("msg", "删除成功");
		}else{
			rootMap.put("opResult","1");
			rootMap.put("msg", "删除失败");
		}
		return rootMap;
	}
	@RequestMapping("/planeteam_search")
	@ResponseBody
//	@MethodNote(comment="查询机队:76")
	@MyMethodNote(comment="查询机队:3")
	public Map<String,Object> search(String teamname){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		List<PlaneTeam> team = teamService.search(teamname);
		if(team!=null){
			rootMap.put("result", 0);
			rootMap.put("info", team);
		}else{
			rootMap.put("result", 1);
			rootMap.put("msg", "没有查询到相关数据");
		}
		return rootMap;
	}
	@RequestMapping("/planeteam_load")
	@ResponseBody
	public PlaneTeam load(int id){
		return teamService.load(id);
	}
}
