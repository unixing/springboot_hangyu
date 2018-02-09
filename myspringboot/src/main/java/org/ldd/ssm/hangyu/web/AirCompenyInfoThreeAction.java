package org.ldd.ssm.hangyu.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.hangyu.domain.AirCompenyInfoThree;
import org.ldd.ssm.hangyu.service.AirCompenyInfoThreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirCompenyInfoThreeAction {
	@Autowired
	private AirCompenyInfoThreeService objService;
	
	@RequestMapping("/airCompenyList")
	@ResponseBody
	public Map<String,Object> getAllAirCompeny(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<AirCompenyInfoThree> list = objService.selectAll();
			if(list!=null&&list.size()>0){
				map.put("opResult", "0");
				map.put("list",list);
			}else{
				map.put("opResult","1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("opResult","2");
			return map;
		}
		return map;
	}
	
	@RequestMapping("/aircompenyDetail")
	@ResponseBody
	public Map<String,Object> loadAircompenyDetail(String itia){
		return objService.loadAircompenyDetail(itia);
	}
}
