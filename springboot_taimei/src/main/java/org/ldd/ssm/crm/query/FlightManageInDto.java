package org.ldd.ssm.crm.query;

import java.util.List;

import org.ldd.ssm.crm.domain.FlightManage;
import org.ldd.ssm.crm.domain.TKTIncome;
import org.ldd.ssm.crm.domain.WatchFlight;

public class FlightManageInDto {
	List<TKTIncome> list;
	FlightManage currentFlight;
	WatchFlightDto watchFlight;
	
	public List<TKTIncome> getList() {
		return list;
	}
	public void setList(List<TKTIncome> list) {
		this.list = list;
	}
	public FlightManage getCurrentFlight() {
		return currentFlight;
	}
	public void setCurrentFlight(FlightManage currentFlight) {
		this.currentFlight = currentFlight;
	}
	public WatchFlightDto getWatchFlight() {
		return watchFlight;
	}
	public void setWatchFlight(WatchFlightDto watchFlight) {
		this.watchFlight = watchFlight;
	}
	
	
	
	
}
