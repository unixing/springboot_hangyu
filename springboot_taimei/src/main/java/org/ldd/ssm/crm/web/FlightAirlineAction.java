package org.ldd.ssm.crm.web;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.FlightAirline;
import org.ldd.ssm.crm.query.FlightAirlineQuery;
import org.ldd.ssm.crm.service.FlightAirlineService;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 * 航路控制类
 * @Title:FlightAirlineAction 
 * @Description:
 * @author taimei-gds 
 * @date 2017-3-31 上午11:34:52
 */
@Controller
public class FlightAirlineAction {
	@Autowired
	FlightAirlineService flightAirlineService;
	
	@RequestMapping(value="/restful/getFlightAirlineData",produces="text/plain;charset=UTF-8")
	@MethodNote(comment="默认:16")
	@MyMethodNote(comment="首页航路数据查询:2")
	@ResponseBody
	public String getFlightAirlineData(FlightAirlineQuery flightAirlineQuery){
		try {
			HttpServletRequest request = UserContext.getRequest();
			String callback = request.getParameter("callback");
			Map<String,List<FlightAirline>> retMap = new LinkedHashMap<String,List<FlightAirline>>();
			retMap = flightAirlineService.getFlightAirlineData(flightAirlineQuery);
			Gson gson=new Gson();
			String retStr = callback;
			String hjson = "";
			hjson = gson.toJson(retMap);
			retStr = retStr + "({ \"success\": "+hjson+ "})";
			return retStr ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping(value="/restful/getAllFlightAirlineData",produces="text/plain;charset=UTF-8")
	@MethodNote(comment="默认:16")
	@MyMethodNote(comment="首页所有航路数据查询:2")
	@ResponseBody
	public String getAllFlightAirlineData(){
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		Map<String,List<FlightAirline>> retMap = new HashMap<String,List<FlightAirline>>();
		retMap = flightAirlineService.getAllFlightAirlineData();
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(retMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
}
