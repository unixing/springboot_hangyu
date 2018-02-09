package org.ldd.ssm.hangyu.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.TextUtils;
import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.EmployeeDemand;
import org.ldd.ssm.hangyu.service.EmployeeDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeDemandAction {
	@Autowired
	private EmployeeDemandService employeeDemandService;
	Logger log = Logger.getLogger(EmployeeDemandAction.class);
	@RequestMapping("/employeeDemandAdd")
	@ResponseBody
	public Map<String,Object> add(String employeeDemandIds){
		Map<String,Object> map = new HashMap<String,Object>();
		if(TextUtils.isEmpty(employeeDemandIds)){
			log.debug("employeeDemandAdd:employeeDemandId is a invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
			boolean result = employeeDemandService.batchAdd(employeeDemandIds);
			if(result){
				map.put("opResult", "0");
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("There is something wrong here");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/employeeDemandDel")
	@ResponseBody
	public Map<String,Object> employeeDemandDel(Long employeeDemandId){
		Map<String,Object> map = new HashMap<String,Object>();
		if(employeeDemandId==null||employeeDemandId==0){
			map.put("opResult", "3");
			return map;
		}
		try {
			boolean result = employeeDemandService.employeeDemandDel(employeeDemandId);
			if(result){
				map.put("opResult", "0");
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("There is something wrong here.");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/employeeDemandFind")
	@ResponseBody
	public Map<String,Object> employeeDemandFind(Long employeeDemandId){
		Map<String,Object> map = new HashMap<String,Object>();
		if(employeeDemandId==null||employeeDemandId==0){
			map.put("opResult", "3");
			return map;
		}
		try {
			List<EmployeeDemand> employeeDemands = employeeDemandService.employeeDemandFind(employeeDemandId);
			if(employeeDemands!=null&&employeeDemands.size()>0){
				map.put("data", employeeDemands);
				map.put("opResult", "0");
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("There is something wrong here.");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
}
