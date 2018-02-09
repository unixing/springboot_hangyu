package org.ldd.ssm.crm.domain;

public class Yesterday_ZD {
	private String id;
	private String air_date;
	private String flt_nbr;
	private String plan_q;
	private String actual_q;
	private String dpt_AirPt_Cd;
	private String plan_c;
	private String actual_c;
	private String arrv_Airpt_Cd;
	private String zd_rate;
	private String state;
	private String itia;
	private String time;
	private String remark="-0";
	private String arrv_Airpt_Cd_itia;
	private String dpt_AirPt_Cd_itia;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItia() {
		return itia;
	}
	public void setItia(String itia) {
		this.itia = itia;
	}
	public String getAir_date() {
		return air_date;
	}
	public void setAir_date(String air_date) {
		this.air_date = air_date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getFlt_nbr() {
		return flt_nbr;
	}
	public void setFlt_nbr(String flt_nbr) {
		this.flt_nbr = flt_nbr;
	}
	public String getPlan_q() {
		return plan_q;
	}
	public void setPlan_q(String plan_q) {
		this.plan_q = plan_q;
	}
	public String getActual_q() {
		return actual_q;
	}
	public void setActual_q(String actual_q) {
		this.actual_q = actual_q;
	}
	public String getDpt_AirPt_Cd() {
		return dpt_AirPt_Cd;
	}
	public void setDpt_AirPt_Cd(String dpt_AirPt_Cd) {
		this.dpt_AirPt_Cd = dpt_AirPt_Cd;
	}
	public String getPlan_c() {
		return plan_c;
	}
	public void setPlan_c(String plan_c) {
		this.plan_c = plan_c;
	}
	public String getActual_c() {
		return actual_c;
	}
	public void setActual_c(String actual_c) {
		this.actual_c = actual_c;
	}
	public String getArrv_Airpt_Cd() {
		return arrv_Airpt_Cd;
	}
	public void setArrv_Airpt_Cd(String arrv_Airpt_Cd) {
		this.arrv_Airpt_Cd = arrv_Airpt_Cd;
	}
	public String getZd_rate() {
		return zd_rate;
	}
	public void setZd_rate(String zd_rate) {
		this.zd_rate = zd_rate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the arrv_Airpt_Cd_itia
	 */
	public String getArrv_Airpt_Cd_itia() {
		return arrv_Airpt_Cd_itia;
	}
	/**
	 * @param arrv_Airpt_Cd_itia the arrv_Airpt_Cd_itia to set
	 */
	public void setArrv_Airpt_Cd_itia(String arrv_Airpt_Cd_itia) {
		this.arrv_Airpt_Cd_itia = arrv_Airpt_Cd_itia;
	}
	/**
	 * @return the dpt_AirPt_Cd_itia
	 */
	public String getDpt_AirPt_Cd_itia() {
		return dpt_AirPt_Cd_itia;
	}
	/**
	 * @param dpt_AirPt_Cd_itia the dpt_AirPt_Cd_itia to set
	 */
	public void setDpt_AirPt_Cd_itia(String dpt_AirPt_Cd_itia) {
		this.dpt_AirPt_Cd_itia = dpt_AirPt_Cd_itia;
	}
	@Override
	public String toString() {
		return "Yesterday_ZD [id=" + id + ", air_date=" + air_date
				+ ", flt_nbr=" + flt_nbr + ", plan_q=" + plan_q + ", actual_q="
				+ actual_q + ", dpt_AirPt_Cd=" + dpt_AirPt_Cd + ", plan_c="
				+ plan_c + ", actual_c=" + actual_c + ", arrv_Airpt_Cd="
				+ arrv_Airpt_Cd + ", zd_rate=" + zd_rate + ", state=" + state
				+ ", itia=" + itia + ", time=" + time + ", remark=" + remark
				+ ", arrv_Airpt_Cd_itia=" + arrv_Airpt_Cd_itia
				+ ", dpt_AirPt_Cd_itia=" + dpt_AirPt_Cd_itia + "]";
	}
	
	
	
}
