package org.ldd.ssm.crm.query;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.TKTIncome;
import org.ldd.ssm.crm.domain.WatchFlight;


public class FlightSaleData {

	private String leg;
	private List<CabinData> cabinDataList;
	private List<CabinData> cabinDataYesterdayList;
	private Map<String,List<LatestAirFare>> currerntFlightPriceMap;
	private Map<String,List<LatestAirFare>> yesterdayFlightPriceList;
	private List<Map<String,List<LatestAirFare>>> flightPriceTrendList;//航班管理中的航班（以及最低价）
	private WatchFlight  watchFlight;
	private List<TKTIncome> flightManageList;
	private List<LatestAirFare> currerntFlightPriceList;
	private String selectIntimeMsg;
	private boolean selectIntimeFlag;
	
	public String getSelectIntimeMsg() {
		return selectIntimeMsg;
	}
	public void setSelectIntimeMsg(String selectIntimeMsg) {
		this.selectIntimeMsg = selectIntimeMsg;
	}
	public boolean isSelectIntimeFlag() {
		return selectIntimeFlag;
	}
	public void setSelectIntimeFlag(boolean selectIntimeFlag) {
		this.selectIntimeFlag = selectIntimeFlag;
	}
	public Map<String, List<LatestAirFare>> getCurrerntFlightPriceMap() {
		return currerntFlightPriceMap;
	}
	public void setCurrerntFlightPriceMap(Map<String, List<LatestAirFare>> currerntFlightPriceMap) {
		this.currerntFlightPriceMap = currerntFlightPriceMap;
	}
	public List<LatestAirFare> getCurrerntFlightPriceList() {
		return currerntFlightPriceList;
	}
	public void setCurrerntFlightPriceList(List<LatestAirFare> currerntFlightPriceList) {
		this.currerntFlightPriceList = currerntFlightPriceList;
	}
	public List<TKTIncome> getFlightManageList() {
		return flightManageList;
	}
	public void setFlightManageList(List<TKTIncome> flightManageList) {
		this.flightManageList = flightManageList;
	}
	private Long tktincomeId;//用于存入初始价格的时候存入tktid
	
	public Long getTktincomeId() {
		return tktincomeId;
	}
	public void setTktincomeId(Long tktincomeId) {
		this.tktincomeId = tktincomeId;
	}
	public String getLeg() {
		return leg;
	}
	public void setLeg(String leg) {
		this.leg = leg;
	}
	
	public List<CabinData> getCabinDataList() {
		return cabinDataList;
	}
	public void setCabinDataList(List<CabinData> cabinDataList) {
		this.cabinDataList = cabinDataList;
	}
	public List<CabinData> getCabinDataYesterdayList() {
		return cabinDataYesterdayList;
	}
	public void setCabinDataYesterdayList(List<CabinData> cabinDataYesterdayList) {
		this.cabinDataYesterdayList = cabinDataYesterdayList;
	}
	
	public WatchFlight getWatchFlight() {
		return watchFlight;
	}
	public void setWatchFlight(WatchFlight watchFlight) {
		this.watchFlight = watchFlight;
	}
	public Map<String, List<LatestAirFare>> getYesterdayFlightPriceList() {
		return yesterdayFlightPriceList;
	}
	public void setYesterdayFlightPriceList(Map<String, List<LatestAirFare>> yesterdayFlightPriceList) {
		this.yesterdayFlightPriceList = yesterdayFlightPriceList;
	}
	public List<Map<String, List<LatestAirFare>>> getFlightPriceTrendList() {
		return flightPriceTrendList;
	}
	public void setFlightPriceTrendList(List<Map<String, List<LatestAirFare>>> flightPriceTrendList) {
		this.flightPriceTrendList = flightPriceTrendList;
	}
	
}
