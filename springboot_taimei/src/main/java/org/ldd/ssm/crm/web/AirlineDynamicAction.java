package org.ldd.ssm.crm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.aop.MyMethodNote;
import org.ldd.ssm.crm.domain.AirlineDynameicGraphics;
import org.ldd.ssm.crm.domain.Yesterday_ZD;
import org.ldd.ssm.crm.service.IAirlineDynameicService;
import org.ldd.ssm.crm.utils.AjaxResult;
import org.ldd.ssm.crm.utils.UserContext;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AirlineDynamicAction {
	@Autowired
	private IAirlineDynameicService IAirlineDynameicServiceImpl;
	@RequestMapping("/airline_dynamic")
	@MyMethodNote(comment="航班动态主页:2")
	@MethodNote(comment="航班动态:6")
	public String airline_dynamic(){
//		return "airline_dynamic/airline_dynamic";
		return "newHtml/flightDynamic";
	}
	@RequestMapping("/airline_dynameic_list")
	@MyMethodNote(comment="航班动态查询:2")
	@MethodNote(comment="航班动态:6")
	@ResponseBody
	public Map<String,Object> airline_dynameic_list(String date){
		
		Map<String,Object> map = IAirlineDynameicServiceImpl.getAirline_dynameic_list(date);
		return map;
	}
	
	@RequestMapping("/airline_dynameic_list_in")
	@MyMethodNote(comment="航班动态查询:2")
	@MethodNote(comment="航班动态:6")
	@ResponseBody
	public Map<String,Object> airline_dynameic_list_in(String date,String field,String sortType){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Yesterday_ZD> airline_dynameic_list = IAirlineDynameicServiceImpl.getAirline_dynameic_list_in(date,field,sortType);
		map.put("data", airline_dynameic_list);
		return map;
	}
	
	@RequestMapping("/airline_dynameic_list_out")
	@MyMethodNote(comment="航班动态查询:2")
	@MethodNote(comment="航班动态:6")
	@ResponseBody
	public Map<String,Object> airline_dynameic_list_out(String date,String field,String sortType){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Yesterday_ZD> airline_dynameic_list = IAirlineDynameicServiceImpl.getAirline_dynameic_list_out(date,field,sortType);
		map.put("data", airline_dynameic_list);
		return map;
	}
	
	@RequestMapping("/airline_dynameic_graphics")
	@MyMethodNote(comment="航班动态查询:2")
	@MethodNote(comment="航班动态:6")
	@ResponseBody
	public AirlineDynameicGraphics airline_dynameic_graphics(String date){
		AirlineDynameicGraphics graphics = IAirlineDynameicServiceImpl.airline_dynameic_graphics(date);
		return graphics;
	}
	@RequestMapping("/airline_dynameic_save")
	@MethodNote(comment="航班动态异常原因编辑:14")
	@ResponseBody
	@MyMethodNote(comment="更新航班异常原因:3")
	public AjaxResult airline_dynameic_save(String index,String sp){
		IAirlineDynameicServiceImpl.airline_dynameic_save(index,sp);
		return new AjaxResult();
	}
	
	@RequestMapping("/getDatesList")
	@MethodNote(comment="航班动态:6")
	@ResponseBody
	public Map<String,Object> getDateList(String inout,String month){
		Map<String,Object> map = new HashMap<String,Object>();
		if(month==null||"".equals(month)){//当首次进入或者切换进出港
			if(inout==null||inout=="in"){
				inout = "in";//设置参数为进港类型
				List<String> dates = IAirlineDynameicServiceImpl.getDateList(UserContext.getCompanyName(),UserContext.getcompanyItia(),month,inout);
				map.put("dates", dates);
				int size = dates.size();
				if(dates!=null&&size>0){
					String date = dates.get(size-1);
					map.put("month", date.substring(0, size-3));
					List<Yesterday_ZD> airline_dynameic_list = IAirlineDynameicServiceImpl.getAirline_dynameic_list_in(date, null, null);
					map.put("data", airline_dynameic_list);
				}
			}else{
				List<String> dates = IAirlineDynameicServiceImpl.getDateList(UserContext.getCompanyName(),UserContext.getcompanyItia(),month,inout);
				map.put("dates", dates);
				int size = dates.size();
				if(dates!=null&&size>0){
					String date = dates.get(size-1);
					map.put("month", date.substring(0, size-3));
					List<Yesterday_ZD> airline_dynameic_list = IAirlineDynameicServiceImpl.getAirline_dynameic_list_out(date, null, null);
					map.put("data", airline_dynameic_list);
				}
			}
		}else{
			List<String> dates = IAirlineDynameicServiceImpl.getDateList(UserContext.getCompanyName(),UserContext.getcompanyItia(),month,inout);
			map.put("dates", dates);
		}
		return map;
	}
	
	@RequestMapping("/getMonthList")
	@MethodNote(comment="航班动态异常原因编辑:14")
	@ResponseBody
	public Map<String,Object> getMonthList(String year){
		Map<String,Object> map = new HashMap<String,Object>();
		List<String> months = IAirlineDynameicServiceImpl.getMonthList(year);
		map.put("months", months);
		return map;
	}
}
