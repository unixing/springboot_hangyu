package org.ldd.ssm.crm.query;

import java.util.Date;

public class StartDateAndEndDate {
	private Date start;
	private Date end;
	private String dta_Sce;
	private String dpt_AirPt_Cd;
	private String arrv_Airpt_Cd;
	
	public String getDpt_AirPt_Cd() {
		return dpt_AirPt_Cd;
	}
	public void setDpt_AirPt_Cd(String dpt_AirPt_Cd) {
		this.dpt_AirPt_Cd = dpt_AirPt_Cd;
	}
	public String getArrv_Airpt_Cd() {
		return arrv_Airpt_Cd;
	}
	public void setArrv_Airpt_Cd(String arrv_Airpt_Cd) {
		this.arrv_Airpt_Cd = arrv_Airpt_Cd;
	}
	public String getDta_Sce() {
		return dta_Sce;
	}
	public void setDta_Sce(String dta_Sce) {
		this.dta_Sce = dta_Sce;
	}
	public StartDateAndEndDate() {
		super();
	}
	public StartDateAndEndDate(Date start, Date end) {
		super();
		this.start = start;
		this.end = end;
	}
	public StartDateAndEndDate(Date start, Date end, String dta_Sce) {
		super();
		this.start = start;
		this.end = end;
		this.dta_Sce = dta_Sce;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	@Override
	public String toString() {
		return "StartDateAndEndDate [start=" + start + ", end=" + end
				+ ", dta_Sce=" + dta_Sce + ", dpt_AirPt_Cd=" + dpt_AirPt_Cd
				+ ", arrv_Airpt_Cd=" + arrv_Airpt_Cd + "]";
	}
	
}
