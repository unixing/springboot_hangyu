package org.ldd.ssm.crm.service;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.FocusFlight;

public interface IFocusFlightService {
	
	public boolean save(List<FocusFlight> flts);
	
	public boolean update(List<FocusFlight> flts, Long employeeId);
	
	public Map<String,Object> getFocusFlight(Long employeeId);
	
	public List<String> getFocusFlightList(Long employeeId);
	
	public List<String> getFlightRouteList(Long employeeId);//获取关注航线列表
}
