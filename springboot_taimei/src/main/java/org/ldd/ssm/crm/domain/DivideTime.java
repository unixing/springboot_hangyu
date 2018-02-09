package org.ldd.ssm.crm.domain;

public class DivideTime {
	private int divideId;
	private int seasonId;
	private String fltDirect;
	private String startDate;
	private String endDate;
	private String refSaleStartDate;
	private String refSaleEndDate;
	private String nature;
	private MonthSalePlan monthSalePlan;
	public int getDivideId() {
		return divideId;
	}
	public void setDivideId(int divideId) {
		this.divideId = divideId;
	}
	public int getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(int seasonId) {
		this.seasonId = seasonId;
	}
	public String getFltDirect() {
		return fltDirect;
	}
	public void setFltDirect(String fltDirect) {
		this.fltDirect = fltDirect;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRefSaleStartDate() {
		return refSaleStartDate;
	}
	public void setRefSaleStartDate(String refSaleStartDate) {
		this.refSaleStartDate = refSaleStartDate;
	}
	public String getRefSaleEndDate() {
		return refSaleEndDate;
	}
	public void setRefSaleEndDate(String refSaleEndDate) {
		this.refSaleEndDate = refSaleEndDate;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	
	public MonthSalePlan getMonthSalePlan() {
		return monthSalePlan;
	}
	public void setMonthSalePlan(MonthSalePlan monthSalePlan) {
		this.monthSalePlan = monthSalePlan;
	}
	@Override
	public String toString() {
		return "DevideTime [divideId=" + divideId + ", seasonId="
				+ seasonId + ", fltDirect=" + fltDirect + ", startDate="
				+ startDate + ", endDate=" + endDate
				+ ", refSaleStartDate=" + refSaleStartDate
				+ ", refSaleEndDate=" + refSaleEndDate + ", nature="
				+ nature + "]";
	}
	
}
