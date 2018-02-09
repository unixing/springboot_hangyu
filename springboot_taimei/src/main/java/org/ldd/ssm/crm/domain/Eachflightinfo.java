package org.ldd.ssm.crm.domain;

import java.util.Date;

/**
 * 时刻表
 */
public class Eachflightinfo {
	private String dpt_AirPt_Cd;// 始发地
	private String arrv_Airpt_Cd;// 到达地
	private String flt_nbr;// 航班号
	private String airCrft_Typ;// 机型
	private String dpt_AirPt_pot;// 出发机场
	private String lcl_Dpt_Tm;// 出发时间
	private String arrv_Airpt_pot;// 到达机场
	private String lcl_Arrv_Tm;// 到达时间
	private String days;// 班期
	private Date get_tim; //查询时间
	
	public Date getGet_tim() {
		return get_tim;
	}
	public void setGet_tim(Date get_tim) {
		this.get_tim = get_tim;
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
	public String getFlt_nbr() {
		return flt_nbr;
	}
	public void setFlt_nbr(String flt_nbr) {
		this.flt_nbr = flt_nbr;
	}
	public String getAirCrft_Typ() {
		return airCrft_Typ;
	}
	public void setAirCrft_Typ(String airCrft_Typ) {
		this.airCrft_Typ = airCrft_Typ;
	}
	public String getDpt_AirPt_pot() {
		return dpt_AirPt_pot;
	}
	public void setDpt_AirPt_pot(String dpt_AirPt_pot) {
		this.dpt_AirPt_pot = dpt_AirPt_pot;
	}
	public String getLcl_Dpt_Tm() {
		return lcl_Dpt_Tm;
	}
	public void setLcl_Dpt_Tm(String lcl_Dpt_Tm) {
		this.lcl_Dpt_Tm = lcl_Dpt_Tm;
	}
	public String getArrv_Airpt_pot() {
		return arrv_Airpt_pot;
	}
	public void setArrv_Airpt_pot(String arrv_Airpt_pot) {
		this.arrv_Airpt_pot = arrv_Airpt_pot;
	}
	public String getLcl_Arrv_Tm() {
		return lcl_Arrv_Tm;
	}
	public void setLcl_Arrv_Tm(String lcl_Arrv_Tm) {
		this.lcl_Arrv_Tm = lcl_Arrv_Tm;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	@Override
	public String toString() {
		return "Eachflightinfo [dpt_AirPt_Cd=" + dpt_AirPt_Cd
				+ ", arrv_Airpt_Cd=" + arrv_Airpt_Cd + ", flt_nbr=" + flt_nbr
				+ ", airCrft_Typ=" + airCrft_Typ + ", dpt_AirPt_pot="
				+ dpt_AirPt_pot + ", lcl_Dpt_Tm=" + lcl_Dpt_Tm
				+ ", arrv_Airpt_pot=" + arrv_Airpt_pot + ", lcl_Arrv_Tm="
				+ lcl_Arrv_Tm + ", days=" + days + ", get_tim=" + get_tim + "]";
	}
}
