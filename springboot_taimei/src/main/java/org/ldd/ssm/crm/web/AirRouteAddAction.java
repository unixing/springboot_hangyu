package org.ldd.ssm.crm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.ldd.ssm.crm.domain.AirRoute;
import org.ldd.ssm.crm.service.IAirRouteAddService;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 航线添加
 * @author wxm
 *
 * @date 2017-4-5
 */
@Controller
@RequestMapping("/airRoute")
public class AirRouteAddAction {
	
	@Resource
	private IAirRouteAddService airRouteAddService;
	
	@RequestMapping("/test")
	public String test(){
		return "index/test";
	}
	/**
	 * 查询无数据航线
	 */
	@RequestMapping("/findEmptyAirData")
	@MethodNote(comment="自定义航线:15")
	@ResponseBody
	public Map<String,Object> findEmptyAirData(AirRoute airRoute,HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> map=new HashMap<String, Object>();
		try {
			//查询 该companyid和状态为2 机场为当前机场的 航线 
			//根据机场三字码和集团公司id 找到所有无数据航线 和前端确认返回数据
			airRoute.setCompanyId((Long.parseLong(UserContext.getCompanyId())));//集团公司id
			airRoute.setItia(UserContext.getcompanyItia());
			airRoute.setState("2");
			List<AirRoute> list=airRouteAddService.findAirRouteList(airRoute);
			map.put("data", list);
			map.put("success", true);
			map.put("msg", "查询列表成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/add")
	@MethodNote(comment="自定义航线:15")
	@ResponseBody
	public Map<String,Object> airRouteAdd(String airlineList,HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		AirRoute airRoute=new AirRoute();
		JSONArray json=JSONArray.fromObject(airlineList);
		@SuppressWarnings("unchecked")
		List<String> list=JSONArray.toList(json,String.class);
		Map<String,Object> map=new HashMap<String, Object>();
		airRoute.setAirLineList(list);
		//判断添加的航线是否重复 有数据的航线 和无数据的航线都要比较
		try {
			if(!airRoute.getAirLineList().isEmpty()){
				
				//后台查询出有数据航线
				List<String> dataList=airRouteAddService.findOnlineDataList(UserContext.getcompanyItia());
				if(!dataList.isEmpty()){
					//求交集
					dataList.retainAll(list);
					if(dataList.size()>0){
						//和已有数据重复
						map.put("success", false);
						map.put("msg", "该航线已有航线重复，添加失败");
						return map;
					}
				}
				airRoute.setCompanyId((Long.parseLong(UserContext.getCompanyId())));//集团公司id
				airRoute.setItia(UserContext.getcompanyItia());
				boolean succ=false;
				succ=airRouteAddService.airRouteAdd(airRoute);
				if(succ){
					map.put("success", true);
					map.put("msg", "添加成功");
					
				}else{
					map.put("success", false);
					map.put("msg", "添加失败");
					
				}
			}else{
				map.put("success", false);
				map.put("msg", "传入参数为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("/edit")
	@MethodNote(comment="自定义航线:15")
	@ResponseBody
	public Map<String,Object> airRouteEdit(String airRouteList,HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONArray json=JSONArray.fromObject(airRouteList);
		@SuppressWarnings("unchecked")
		List<AirRoute> list=JSONArray.toList(json,AirRoute.class);
		Map<String,Object> map=new HashMap<String, Object>();
		if (airRouteAddService.airRouteEditBatch(list)) {
			map.put("success", true);
			map.put("msg", "修改成功");
		}else{
			map.put("success", false);
			map.put("msg", "修改失败");
		}
		return map;
	}
	
	/**
	 * 单条删除
	 */
	@RequestMapping("/delete")
	@MethodNote(comment="自定义航线:15")
	@ResponseBody
	public Map<String,Object> airRouteDelete(long id,HttpServletRequest request,HttpServletResponse response){
		response.setHeader("Access-Control-Allow-Origin", "*");
		Map<String,Object> map=new HashMap<String, Object>();
		if (airRouteAddService.airRouteDelete(id)) {
			map.put("success", true);
			map.put("msg", "删除成功");
		}else{
			map.put("success", false);
			map.put("msg", "删除失败");
		}
		
		return map;
	}
	
	
}
