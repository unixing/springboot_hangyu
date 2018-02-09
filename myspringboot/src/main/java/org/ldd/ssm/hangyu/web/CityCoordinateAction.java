package org.ldd.ssm.hangyu.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.CityCoordinate;
import org.ldd.ssm.hangyu.service.CityCoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityCoordinateAction {

	@Autowired
	private CityCoordinateService cityCoordinateService;
	Logger log = Logger.getLogger(CityCoordinateAction.class);
	@RequestMapping("/getCityAllList")
	@ResponseBody
	public Map<String,Object> getCityAllList(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			List<CityCoordinate> list = cityCoordinateService.getAllCityList();
			if(list!=null && list.size()>0){
				map.put("opResult", "0");
				map.put("list", list);
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("there is something wrong here");
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
}
