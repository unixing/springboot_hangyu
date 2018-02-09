package org.ldd.ssm.crm.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ldd.ssm.crm.domain.AirportFlight;
import org.ldd.ssm.crm.service.AirportFlightService;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 机场航线
 * @author wxm
 *
 * @date 2017-3-30
 */
@Controller
@RequestMapping("/airportFlight")
public class AirportFlightAction {
	@Resource 
	private AirportFlightService airportFlightService;
	
	@RequestMapping("/findAirportFlight")
	@MethodNote(comment="默认:16")
	@ResponseBody
	public Map<String,Object> findAirportFlight(String iATA,HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> map = new HashMap<String,Object>();
		if(iATA.isEmpty()){
			map.put("msg","机场三字码为空");
			map.put("success",false);
			map.put("data",null);
		}else{
			try {
				AirportFlight airportFlight=new AirportFlight();
				airportFlight=airportFlightService.getAirportFlight(iATA);
				if(airportFlight==null){
					map.put("msg","暂无航线");
					map.put("success",false);
					map.put("data",null);
				}else{
					map.put("msg","查询成功");
					map.put("success",true);
					map.put("data",airportFlight);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
}
