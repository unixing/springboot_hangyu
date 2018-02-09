package org.ldd.ssm.hangyu.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.domain.RouteNetwork;
import org.ldd.ssm.hangyu.service.AirportInfoNewThreeService;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirportInfoNewThreeAction {

	@Autowired
	private AirportInfoNewThreeService airportInfoNewThreeService;
	/**
	 * 获取航线网络图
	 * @return
	 */
	@RequestMapping("/getAirportListByCode")
	@ResponseBody
	public Map<String,Object> getAirportListByCode(){
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Employee emp = UserContext.getUser();
			int type = Integer.valueOf(emp.getRole());//0-航司    1-机场
			List<RouteNetwork> list = airportInfoNewThreeService.getAirportListByIata(type,emp.getAirlineretrievalcondition());
			map.put("opResult", "0");
			map.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
	/**
	 * 通过机场三字码获取机场详情
	 * @param itia
	 * @return
	 */
	@RequestMapping("/loadAirportByCode")
	@ResponseBody
	public Map<String,Object> loadAirportByCode(String itia){
		return airportInfoNewThreeService.loadAirportByCode(itia);
	}
	/**
	 * 通过机场四字码获取机场情报详情
	 * @param iata
	 * @return
	 */
	@RequestMapping("/loadAirportInfomation")
	@ResponseBody
	public Map<String,Object> loadAirportInformationByIata(String iata){
		return airportInfoNewThreeService.loadAirportInformationByIata(iata);
	}
	
	@RequestMapping("/loadIndexAirportInfoByCode")
	@ResponseBody
	public Map<String,Object> loadIndexAirportInfoByCode(String itia){
		return airportInfoNewThreeService.loadIndexAirportInfoByCode(itia);
	}
}
