package org.ldd.ssm.hangyu.web;

import java.util.Map;

import org.ldd.ssm.hangyu.service.CityinfoThreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityinfoThreeAction {
	@Autowired
	private CityinfoThreeService cityinfoThreeService;
	@RequestMapping("/getCityDetail")
	@ResponseBody
	public Map<String,Object> getCityDetail(String cityName){
		return cityinfoThreeService.getCityDetail(cityName);
	}
}
