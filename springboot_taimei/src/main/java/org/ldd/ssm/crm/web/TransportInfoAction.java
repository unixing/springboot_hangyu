package org.ldd.ssm.crm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.TransportInfo;
import org.ldd.ssm.crm.service.TransportInfoService;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class TransportInfoAction {
	@Autowired
	private TransportInfoService infoService;
	@RequestMapping("/transportinfo_add")
	@ResponseBody
//	@MethodNote(comment="添加交通工具:75")
	@MyMethodNote(comment="添加交通工具:3")
	public Map<String,String> add(TransportInfo info){
		Map<String,String> rootMap = new HashMap<String,String>();
		boolean result = infoService.add(info);
		if(result){
			rootMap.put("opResult","0");
			rootMap.put("msg", "添加成功");
		}else{
			rootMap.put("opResult","1");
			rootMap.put("msg", "添加失败");
		}
		return rootMap;
	}

	@RequestMapping("/transportinfo_update")
	@ResponseBody
//	@MethodNote(comment="修改交通工具:75")
	@MyMethodNote(comment="修改交通工具:3")
	public Map<String,String> update(TransportInfo info){
		Map<String,String> rootMap = new HashMap<String,String>();
		boolean result = infoService.update(info);
		if(result){
			rootMap.put("opResult","0");
			rootMap.put("msg", "修改成功");
		}else{
			rootMap.put("opResult","1");
			rootMap.put("msg", "修改失败");
		}
		return rootMap;
	}

	@RequestMapping("/transportinfo_batchdel")
	@ResponseBody
//	@MethodNote(comment="删除交通工具:75")
	@MyMethodNote(comment="删除交通工具:3")
	public Map<String,String> batchdel(String ids){
		Map<String,String> rootMap = new HashMap<String,String>();
		String[] strs = ids.split(",");
		int[] id = new int[strs.length];
		for(int i=0;i<strs.length;i++){
			id[i] = Integer.valueOf(strs[i]);
		}
		boolean result = infoService.batchdel(id);
		if(result){
			rootMap.put("opResult","0");
			rootMap.put("msg", "删除成功");
		}else{
			rootMap.put("opResult","1");
			rootMap.put("msg", "删除失败");
		}
		return rootMap;
	}
	@RequestMapping("/transportinfo_search")
	@ResponseBody
//	@MethodNote(comment="查询交通工具:75")
	@MyMethodNote(comment="查询交通工具:3")
	public Map<String,Object> search(String transportname){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		List<TransportInfo> info = infoService.search(transportname);
		if(info!=null){
			rootMap.put("result", 0);
			rootMap.put("info", info);
		}else{
			rootMap.put("result", 1);
			rootMap.put("msg", "没有查询到相关数据");
		}
		return rootMap;
	}
}
