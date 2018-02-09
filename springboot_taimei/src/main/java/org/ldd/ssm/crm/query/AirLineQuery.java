package org.ldd.ssm.crm.query;
/**
 * 高级查询加分页的封装对象
 */
public class AirLineQuery {
	private String lcl_Dpt_Day;
	private String pas_stp;
	private String dta_Sce;
	private String dpt_AirPt_Cd;
	private String arrv_Airpt_Cd;
	private String flt_nbr;
	private String cpy_Nm;
	private String airline1;
	private String airline2;
	
	private String Flt_Nbr;
	
	public String getFlt_Nbr() {
		return Flt_Nbr;
	}
	public void setFlt_Nbr(String flt_Nbr) {
		Flt_Nbr = flt_Nbr;
	}
	public String getAirline1() {
		return airline1;
	}
	public void setAirline1(String airline1) {
		this.airline1 = airline1;
	}
	public String getAirline2() {
		return airline2;
	}
	public void setAirline2(String airline2) {
		this.airline2 = airline2;
	}
	public String getCpy_Nm() {
		return cpy_Nm;
	}
	public void setCpy_Nm(String cpy_Nm) {
		this.cpy_Nm = cpy_Nm;
	}

	public String getFlt_nbr() {
		return flt_nbr;
	}

	public void setFlt_nbr(String flt_nbr) {
		this.flt_nbr = flt_nbr;
	}

	public String getLcl_Dpt_Day() {
		return lcl_Dpt_Day;
	}
	public void setLcl_Dpt_Day(String lcl_Dpt_Day) {
		this.lcl_Dpt_Day = lcl_Dpt_Day;
	}
	public String getDta_Sce() {
		return dta_Sce;
	}
	public void setDta_Sce(String dta_Sce) {
		this.dta_Sce = dta_Sce;
	}
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
	public String getPas_stp() {
		return pas_stp;
	}
	public void setPas_stp(String pas_stp) {
		this.pas_stp = pas_stp;
	}
	@Override
	public String toString() {
		return "AirLineQuery [lcl_Dpt_Day=" + lcl_Dpt_Day + ", pas_stp="
				+ pas_stp + ", dta_Sce=" + dta_Sce + ", dpt_AirPt_Cd="
				+ dpt_AirPt_Cd + ", arrv_Airpt_Cd=" + arrv_Airpt_Cd
				+ ", flt_nbr=" + flt_nbr + ", cpy_Nm=" + cpy_Nm + ", airline1="
				+ airline1 + ", airline2=" + airline2 + ", Flt_Nbr=" + Flt_Nbr
				+ "]";
	}
	
}