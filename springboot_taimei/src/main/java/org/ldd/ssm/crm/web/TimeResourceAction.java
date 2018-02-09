package org.ldd.ssm.crm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.TimeResource;
import org.ldd.ssm.crm.service.TimeResourceService;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class TimeResourceAction {
	@Autowired
	private TimeResourceService resourceService;
	@RequestMapping("/timeresource_add")
	@ResponseBody
//	@MethodNote(comment="添加时刻资源:76")
	public Map<String,String> add(TimeResource resource){
		Map<String,String> rootMap = new HashMap<String,String>();
		boolean result = resourceService.add(resource);
		if(result){
			rootMap.put("opResult","0");
			rootMap.put("msg", "添加成功");
		}else{
			rootMap.put("opResult","1");
			rootMap.put("msg", "添加失败");
		}
		return rootMap;
	}

	@RequestMapping("/timeresource_update")
	@ResponseBody
//	@MethodNote(comment="修改时刻资源:76")
	public Map<String,String> update(TimeResource resource){
		Map<String,String> rootMap = new HashMap<String,String>();
		boolean result = resourceService.update(resource);
		if(result){
			rootMap.put("opResult","0");
			rootMap.put("msg", "修改成功");
		}else{
			rootMap.put("opResult","1");
			rootMap.put("msg", "修改失败");
		}
		return rootMap;
	}

	@RequestMapping("/timeresource_batchdel")
	@ResponseBody
//	@MethodNote(comment="删除时刻资源:76")
	public Map<String,String> batchdel(String ids){
		Map<String,String> rootMap = new HashMap<String,String>();
		String[] strs = ids.split(",");
		int[] id = new int[strs.length];
		for(int i=0;i<strs.length;i++){
			id[i] = Integer.valueOf(strs[i]);
		}
		boolean result = resourceService.batchdel(id);
		if(result){
			rootMap.put("opResult","0");
			rootMap.put("msg", "删除成功");
		}else{
			rootMap.put("opResult","1");
			rootMap.put("msg", "删除失败");
		}
		return rootMap;
	}
	@RequestMapping("/timeresource_search")
	@ResponseBody
//	@MethodNote(comment="查询时刻资源:76")
	public Map<String,Object> search(String terminal){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		List<TimeResource> resource = resourceService.search(terminal);
		if(resource!=null){
			rootMap.put("result", 0);
			rootMap.put("info", resource);
		}else{
			rootMap.put("result", 1);
			rootMap.put("msg", "没有查询到相关数据");
		}
		return rootMap;
	}
	@RequestMapping("/timeresource_load")
	@ResponseBody
	public TimeResource load(int id){
		return resourceService.load(id);
	}
}
