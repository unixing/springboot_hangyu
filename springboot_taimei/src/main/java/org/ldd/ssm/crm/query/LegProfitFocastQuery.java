package org.ldd.ssm.crm.query;

import java.util.List;

public class LegProfitFocastQuery {

	private String airline;// "['WDS','LHW']"
	
	private List<String> airlineList;//'WDS','LHW'
	
	//private List<String> legList;//CTU-WDS CTU-PEK ...
	
	private String startTime;
	
	private String endTime;
	private String fltRteCd;
	
	private String aircraftModel;//机型
	
	private String sites;//座位数
	//距离km  选择航路之后自动填入的
	private float distance;
	//航速km/h
	private float speed;
	//每小时成本（w/h）
	private float hourCost;
	
	private Long aircompanyId;//航司id
	
	private String  aircompanyName;//航司名字
	private String ldsj;//轮当时间

	public Long getAircompanyId() {
		return aircompanyId;
	}

	public void setAircompanyId(Long aircompanyId) {
		this.aircompanyId = aircompanyId;
	}

	public String getAircompanyName() {
		return aircompanyName;
	}

	public void setAircompanyName(String aircompanyName) {
		this.aircompanyName = aircompanyName;
	}

	public String getFltRteCd() {
		return fltRteCd;
	}

	public void setFltRteCd(String fltRteCd) {
		this.fltRteCd = fltRteCd;
	}

	public String getAircraftModel() {
		return aircraftModel;
	}

	public void setAircraftModel(String aircraftModel) {
		this.aircraftModel = aircraftModel;
	}

	public String getSites() {
		return sites;
	}

	public void setSites(String sites) {
		this.sites = sites;
	}

	
	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}


	public List<String> getAirlineList() {
		return airlineList;
	}

	public void setAirlineList(List<String> airlineList) {
		this.airlineList = airlineList;
	}

	/*public List<String> getLegList() {
		return legList;
	}

	public void setLegList(List<String> legList) {
		this.legList = legList;
	}
*/
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getHourCost() {
		return hourCost;
	}

	public void setHourCost(float hourCost) {
		this.hourCost = hourCost;
	}

	/**
	 * @return the ldsj
	 */
	public String getLdsj() {
		return ldsj;
	}

	/**
	 * @param ldsj the ldsj to set
	 */
	public void setLdsj(String ldsj) {
		this.ldsj = ldsj;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LegProfitFocastQuery [airline=" + airline + ", airlineList="
				+ airlineList + ", startTime=" + startTime + ", endTime="
				+ endTime + ", fltRteCd=" + fltRteCd + ", aircraftModel="
				+ aircraftModel + ", sites=" + sites + ", distance=" + distance
				+ ", speed=" + speed + ", hourCost=" + hourCost
				+ ", aircompanyId=" + aircompanyId + ", aircompanyName="
				+ aircompanyName + ", ldsj=" + ldsj + "]";
	}
	
	
	
}
