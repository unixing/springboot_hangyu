package org.ldd.ssm.hangyu.web;
import java.util.Map;

import org.ldd.ssm.hangyu.service.EachflightinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.aliyuncs.http.HttpRequest;
import com.aliyuncs.http.HttpResponse;

@RestController
public class EachflightinfoAction {
	@Autowired
	private EachflightinfoService eachflightinfoService;
	@RequestMapping("/getAirportTimeInfo")
	@ResponseBody
	public Map<String,Object> getAirportTimeInfo(String itia,String getTime,String orderField,Integer orderType){
		return eachflightinfoService.getAirportTimeInfo(itia, getTime,orderField,orderType);
	}
	
	@RequestMapping("/getAirportTimeDistribution")
	@ResponseBody
	public Map<String,Object> getAirportTimeDistribution(String itia,String getTime){
		return eachflightinfoService.getAirportTimeDistribution(itia, getTime);
	}
	
	@RequestMapping("/jxlExcel")
	public ModelAndView viewJxlExcel(String itia,String getTime,String orderField,Integer orderType,HttpRequest req,HttpResponse resp) {
		return eachflightinfoService.viewJxlView(itia, getTime,orderField,orderType,req,resp);
	}
}
