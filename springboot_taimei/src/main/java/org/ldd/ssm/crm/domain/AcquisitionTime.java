package org.ldd.ssm.crm.domain;


public class AcquisitionTime {
	
	private String lcl_Dpt_Day;
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

//	@DateTimeFormat(pattern="yyyy")
//	@JsonFormat(pattern="yyyy",timezone="GMT+8")
//	public Date getLcl_Dpt_Day() {
//		return lcl_Dpt_Day;
//	}
//	@DateTimeFormat(pattern="yyyy")
//	@JsonFormat(pattern="yyyy",timezone="GMT+8")
//	public void setLcl_Dpt_Day(Date lcl_Dpt_Day) {
//		this.lcl_Dpt_Day = lcl_Dpt_Day;
//	}

	public String getLcl_Dpt_Day() {
		return lcl_Dpt_Day;
	}

	public void setLcl_Dpt_Day(String lcl_Dpt_Day) {
		this.lcl_Dpt_Day = lcl_Dpt_Day;
	}

	@Override
	public String toString() {
		return "AcquisitionTime [lcl_Dpt_Day=" + lcl_Dpt_Day + ", dta_Sce="
				+ dta_Sce + ", dpt_AirPt_Cd=" + dpt_AirPt_Cd
				+ ", arrv_Airpt_Cd=" + arrv_Airpt_Cd + "]";
	}
	
}
