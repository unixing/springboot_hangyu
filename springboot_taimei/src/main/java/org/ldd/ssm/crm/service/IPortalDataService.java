package org.ldd.ssm.crm.service;

import java.util.List;
import java.util.Map;

public interface IPortalDataService {

	public Map<String,Map<String,Object>> getPortalDataByAirports(List<String> airports);
	/**
	 * 太美集团特地写的功能
	 * @Title: getPortalDataByAirportsByTaimei 
	 * @Description:  
	 * @param @param airports
	 * @param @return    
	 * @return Map<String,Map<String,Object>>     
	 * @throws
	 */
	public Map<String,Map<String,Object>> getPortalDataByAirportsByTaimei(List<String> airports);
	
}
