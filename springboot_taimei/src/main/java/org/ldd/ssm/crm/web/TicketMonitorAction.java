package org.ldd.ssm.crm.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ldd.ssm.crm.domain.FlightManage;
import org.ldd.ssm.crm.domain.GuestrateFlpj;
import org.ldd.ssm.crm.domain.TKTIncome;
import org.ldd.ssm.crm.domain.WatchFlight;
import org.ldd.ssm.crm.mapper.WatchFlightMapper;
import org.ldd.ssm.crm.query.FlightManageInDto;
import org.ldd.ssm.crm.query.FlightSaleData;
import org.ldd.ssm.crm.query.WatchFlightDto;
import org.ldd.ssm.crm.service.TicketMonitorService;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
/**
 * 客票监控接口
 */
@Controller
public class TicketMonitorAction {
	@Resource
	private TicketMonitorService ticketMonitorService;
	
	@Resource
	private WatchFlightMapper watchFlightMapper;
	
	
	
	
	@RequestMapping("/ticketMonitor")
	public String getAccountCheck() {
		return "newHtml/ticketMonitor";
	}
	

	@RequestMapping("/testStr")
	public String testStr() {
		return "basicData/test";
	}
	
	/**
	 * 客票监控
	 * 传入航班号 日期(历史日期)
	 * @return
	 */
	@RequestMapping("/restful/findTicketMonitor")
	@ResponseBody
	public Map<String,Object> findTicketMonitor(GuestrateFlpj dto,HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> map=new HashMap<>();
		//dto.setFltNbr("HU7393");
		try {
			//若无日期则查询今天开始的
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			Date date = new Date();
			String today=sdf.format(date);
			List<FlightSaleData> flightSaleList=new ArrayList<FlightSaleData>();
			if(null==dto.getOnGutDte()||dto.getOnGutDte().isEmpty()||(dto.getOnGutDte().equals(today))){
				dto.setOnGutDte(today);
				flightSaleList=ticketMonitorService.findTicketMonitor(dto);
			}//dto.getOnGutDte()<today
			else if(dto.getOnGutDte().compareTo(today)<0){
				flightSaleList=ticketMonitorService.findTicketMonitorHistory(dto);
			}
			map.put("data",flightSaleList);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 编辑涨跌提醒 航班号 出发地 到达地
	 * @param str
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/restful/upDownRemind")
	@ResponseBody
	public Map<String,Object> upDownRemind(String str,HttpServletRequest request,HttpServletResponse response){
		//response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> map=new HashMap<>();
		try {
			
			JSONObject jsonObject = JSONObject.fromObject(str);  
			
			JSONObject current=(JSONObject) jsonObject.get("currentFlight");
			FlightManage flightManage=(FlightManage) JSONObject.toBean(current, FlightManage.class);  
			flightManage.setUserId(UserContext.getUser().getId());
			
			JSONObject watchFlightObject=(JSONObject) jsonObject.get("watchFlight");
			WatchFlightDto watchFlight=(WatchFlightDto) JSONObject.toBean(watchFlightObject, WatchFlightDto.class);  
			watchFlight.setUserId(UserContext.getUser().getId());
			watchFlight.setUserTel(UserContext.getUser().getPhone());
			watchFlight.setUserEmail(UserContext.getUser().getEmail());
			JSONArray relativeJSONArray=(JSONArray)jsonObject.get("list");
		    List<TKTIncome> relativeList=JSONArray.toList(JSONArray.fromObject(relativeJSONArray), new TKTIncome(), new JsonConfig()); 
		    FlightManageInDto flightManageInDto=new FlightManageInDto();
		    
		    flightManageInDto.setCurrentFlight(flightManage);
		    flightManageInDto.setList(relativeList);
		    flightManageInDto.setWatchFlight(watchFlight);
			//获得传入参数
			//先查询航班管理的航班 如果没有则返回暂无可监控航班
			if(!relativeList.isEmpty()){
				ticketMonitorService.upDownRemind(flightManageInDto);
				map.put("succ",true);
				map.put("msg","设置成功");
			}else{
				map.put("succ",false);
				map.put("msg","没有可监控的航线");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 航班号管理
	 * @param str
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/restful/flightManage")
	@ResponseBody
	public Map<String,Object> flightManage(String str){
		Map<String,Object> map=new HashMap<>();
		try {
			if(null==str||str.isEmpty()){
				map.put("succ",false);
				map.put("msg","传入参数为空");
				return map;
			}
			System.out.println(str);
			JSONObject jsonObject = JSONObject.fromObject(str);  
			
			JSONObject current=(JSONObject) jsonObject.get("currentFlight");
			FlightManage flightManage=(FlightManage) JSONObject.toBean(current, FlightManage.class);  
			flightManage.setUserId(UserContext.getUser().getId());
			
			JSONObject watchFlightObject=(JSONObject) jsonObject.get("watchFlight");
			WatchFlightDto watchFlight=(WatchFlightDto) JSONObject.toBean(watchFlightObject, WatchFlightDto.class);  
			watchFlight.setUserId(UserContext.getUser().getId());
			watchFlight.setUserTel(UserContext.getUser().getPhone());
			watchFlight.setUserEmail(UserContext.getUser().getEmail());
			/*watchFlight.setUserId(12l);
			watchFlight.setUserTel("17780727281");
			watchFlight.setUserEmail("1363948101@qq.com");*/
			JSONArray relativeJSONArray=(JSONArray)jsonObject.get("list");
		    List<TKTIncome> relativeList=JSONArray.toList(JSONArray.fromObject(relativeJSONArray), new TKTIncome(), new JsonConfig()); 
		    FlightManageInDto flightManageInDto=new FlightManageInDto();
		    
		    flightManageInDto.setCurrentFlight(flightManage);
		    flightManageInDto.setList(relativeList);
		    flightManageInDto.setWatchFlight(watchFlight);
		    
			boolean succ=ticketMonitorService.flightManage(flightManageInDto);
			if(succ){
				map.put("succ",true);
				map.put("msg","航班管理设置成功");
			}else{
				map.put("succ",false);
				map.put("msg","航班管理设置失败");
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}


	/**
	 *根据航班号获取航段
	 * @param fltNbr
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/restful/getflightLeg")
	@ResponseBody
	public Map<String,Object> getflightLeg(String fltNbr){
		Map<String,Object> map=new HashMap<>();
		try {
			List<String> list=ticketMonitorService.getflightLeg(fltNbr);
			map.put("data",list);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 及时查询最新航班管理
	 */
	@RequestMapping("/restful/getflightManage")
	@ResponseBody
	public Map<String,Object> getflightManage(WatchFlight dto){
		Map<String,Object> map=new HashMap<>();
		try {
			List<TKTIncome> list=ticketMonitorService.findFlightManage(dto);
			map.put("data",list);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 添加航班管理时 输入航班号返回所有航段
	 * @param fltNbr
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/watchflightSetter")
	@ResponseBody
	public Map<String,Object> watchflightSetter(String fltNbr){
		Map<String,Object> map=new HashMap<>();
		try {
			List<String> list=ticketMonitorService.getflightLeg(fltNbr);
			map.put("data",list);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/findcoutTest")
	@ResponseBody
	public Map<String,Object> findcoutTest(String fltNbr,HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> map=new HashMap<>();
		try {
			WatchFlight ww=new WatchFlight();
		    ww.setArrvAirptCd("LHW");
			int a=watchFlightMapper.getWatchFlightCount(ww);
			map.put("data",a);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
