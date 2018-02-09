package org.ldd.ssm.crm.query;

import java.util.List;
import java.util.Map;
/**
 * 预测结果
 * @author wxm
 *
 * @date 2017-7-26
 */
public class FocastReslut {
	private String fltRteCd;
	private String airCompanyName;//航司
	//距离km  选择航路之后自动填入的
	private float distance;
	private String aircraftModel;//机型
	private String sites;//座位数
	//每小时成本（w/h）
	private float hourCost;
	//航速km/h
	private float speed;
	private String startTime;
	private String endTime;
	private float flyTime;//飞行时长
	private float cost;//成本预算
	private String ldsj;//轮挡时间
	public String getFltRteCd() {
		return fltRteCd;
	}
	public void setFltRteCd(String fltRteCd) {
		this.fltRteCd = fltRteCd;
	}
	
	public String getAirCompanyName() {
		return airCompanyName;
	}
	public void setAirCompanyName(String airCompanyName) {
		this.airCompanyName = airCompanyName;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
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
	public float getHourCost() {
		return hourCost;
	}
	public void setHourCost(float hourCost) {
		this.hourCost = hourCost;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	private List<Map<String,Object>> map;
	
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
	public float getFlyTime() {
		return flyTime;
	}
	public void setFlyTime(float flyTime) {
		this.flyTime = flyTime;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public List<Map<String, Object>> getMap() {
		return map;
	}
	public void setMap(List<Map<String, Object>> map) {
		this.map = map;
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
		return "FocastReslut [fltRteCd=" + fltRteCd + ", airCompanyName="
				+ airCompanyName + ", distance=" + distance
				+ ", aircraftModel=" + aircraftModel + ", sites=" + sites
				+ ", hourCost=" + hourCost + ", speed=" + speed
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", flyTime=" + flyTime + ", cost=" + cost + ", ldsj=" + ldsj
				+ ", map=" + map + "]";
	}
	
}
