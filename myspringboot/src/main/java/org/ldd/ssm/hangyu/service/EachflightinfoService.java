package org.ldd.ssm.hangyu.service;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.aliyuncs.http.HttpRequest;
import com.aliyuncs.http.HttpResponse;

public interface EachflightinfoService {
	public Map<String,Object> getAirportTimeInfo(String itia, String getTime, String orderField, Integer orderType);
	
	public Map<String,Object> getAirportTimeDistribution(String itia, String getTime);
	
	public ModelAndView viewJxlView(String itia, String getTime, String orderField, Integer orderType, HttpRequest req, HttpResponse resp);
}
