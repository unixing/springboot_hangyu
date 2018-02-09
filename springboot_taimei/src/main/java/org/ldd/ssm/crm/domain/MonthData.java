package org.ldd.ssm.crm.domain;

/**
 * 月份数据表
 */
public class MonthData {
	//每天数据
	private String date;//日期
	private String week;//星期
	private String outward;//去程
	private String returnTrip;//返程
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getOutward() {
		return outward;
	}
	public void setOutward(String outward) {
		this.outward = outward;
	}
	public String getReturnTrip() {
		return returnTrip;
	}
	public void setReturnTrip(String returnTrip) {
		this.returnTrip = returnTrip;
	}
	@Override
	public String toString() {
		return "MonthData [date=" + date + ", week=" + week + ", outward="
				+ outward + ", returnTrip=" + returnTrip + "]";
	}
	
}
