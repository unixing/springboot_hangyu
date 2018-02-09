package org.ldd.ssm.crm.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.AirportDetailInfo;
import org.ldd.ssm.crm.domain.AirportJosnData;
import org.ldd.ssm.crm.domain.CityData;
import org.ldd.ssm.crm.domain.CityDetailInfo;
import org.ldd.ssm.crm.domain.OurBoatDetailInfo;
import org.ldd.ssm.crm.domain.ShippingData;
import org.ldd.ssm.crm.domain.ThroughPut;
import org.ldd.ssm.crm.service.BasicalDetailService;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

/**
 * 机场、城市、航司等详细信息控制器
 * @Title:BasicalDetailAction 
 * @Description:
 * @author taimei-gds 
 * @date 2017-2-28 上午11:07:13
 */
@Controller
public class BasicalDetailAction {
	
	@Autowired
	private BasicalDetailService basicalDetailService;
	/**
	 * 查询机场信息
	 * @Title: getAirportDetailInfoByCode 
	 * @Description:  
	 * @param @param code
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value="/restful/getAirportDetailInfoByCode",produces="text/plain;charset=UTF-8")
	@ResponseBody
	@MyMethodNote(comment="查询机场信息:3")
	public String getAirportDetailInfoByCode(String code){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
//		code = "wds";
		AirportDetailInfo result = basicalDetailService.getAirportDetailInfoByCode(code);
		if(result==null){
			rootMap.put("airportDetailInfo", "0");
		}else{
			rootMap.put("airportDetailInfo", result);
		}
		//查询指标同比排名信息
		ThroughPut throughPut = basicalDetailService.getThroughPutByCode(code);
		rootMap.put("throughPut", throughPut);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(rootMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	/**
	 * 查询航司信息
	 * @Title: getOurBoatDetailInfoByCode 
	 * @Description:  
	 * @param @param code
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value="/restful/getOurBoatDetailInfoByCode",produces="text/plain;charset=UTF-8")
	@ResponseBody
	@MyMethodNote(comment="查询航司信息:3")
	public String getOurBoatDetailInfoByCode(String code,String airlineName){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
//		code = "MU";
		OurBoatDetailInfo result = basicalDetailService.getOurBoatDetailInfoByCode(code,airlineName);
		if(result==null){
			rootMap.put("ourBoatDetailInfo", "0");
		}else{
			rootMap.put("ourBoatDetailInfo", result);
		}
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(rootMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	/**
	 * 查询城市信息
	 * @Title: getCityDetailInfoByCode 
	 * @Description:  
	 * @param @param code
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value="/restful/getCityDetailInfoByCode",produces="text/plain;charset=UTF-8")
	@ResponseBody
	@MyMethodNote(comment="查询城市信息:3")
	public String getCityDetailInfoByCode(String code){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		CityDetailInfo result = basicalDetailService.getCityDetailInfoByCode(code);
		if(result==null){
			rootMap.put("cityDetailInfo", "0");
		}else{
			rootMap.put("cityDetailInfo", result);
		}
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(rootMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	@RequestMapping(value="/restful/getCityDatas",produces="text/plain;charset=UTF-8")
	@ResponseBody
	@MyMethodNote(comment="查询城市信息:3")
	public String getCityDatas(){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		List<CityData> result = basicalDetailService.getCityDatas();
		rootMap.put("cityData", result);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(rootMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	@RequestMapping(value="/restful/getShippingDatas",produces="text/plain;charset=UTF-8")
	@ResponseBody
	@MyMethodNote(comment="查询航司信息:3")
	public String getShippingDatas(){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		List<ShippingData> result = basicalDetailService.getShippingDatas();
		rootMap.put("cityData", result);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(rootMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	@RequestMapping(value="/restful/getAirportJosnDatas",produces="text/plain;charset=UTF-8")
	@ResponseBody
	@MyMethodNote(comment="查询机场信息:3")
	public String getAirportJosnDatas(){
		Map<String,Object> rootMap = new HashMap<String,Object>();
		HttpServletRequest request = UserContext.getRequest();
		String callback = request.getParameter("callback");
		List<AirportJosnData> result = basicalDetailService.getAirportJosnDatas();
		Map<String ,List<AirportJosnData>> retMap = new HashMap<String ,List<AirportJosnData>>();
		for (AirportJosnData airportJosnData : result) {
			String cc = airportJosnData.getInitial();
			if(retMap.containsKey(cc)){
				List<AirportJosnData> AirportJosnDataList = retMap.get(cc);
				AirportJosnDataList.add(airportJosnData);
			}else{
				List<AirportJosnData> AirportJosnDataList = new ArrayList<AirportJosnData>();
				AirportJosnDataList.add(airportJosnData);
				retMap.put(cc, AirportJosnDataList);
			}
		}
		rootMap.put("cityData", retMap);
		Gson gson=new Gson();
		String retStr = callback;
		String hjson = "";
		hjson = gson.toJson(rootMap);
		retStr = retStr + "({ \"success\": "+hjson+ "})";
		return retStr ;
	}
	
	
}
