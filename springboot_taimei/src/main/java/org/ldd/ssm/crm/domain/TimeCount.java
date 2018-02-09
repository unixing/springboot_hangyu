package org.ldd.ssm.crm.domain;

/**
 * 均班收益的时间参数类
 * @author Taimei
 *
 */
public class TimeCount {
	private int id;
	private String lcl_dpt_day;
	private String tim_cout;
	private String dpt_airpt_cd;
	private String arrv_airpt_cd;
	private String pas_stp;
	private String cpy_nm;
	private String flt_nbr;
	private String dta_sce;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getLcl_dpt_day() {
		return lcl_dpt_day;
	}
	public void setLcl_dpt_day(String lcl_dpt_day) {
		this.lcl_dpt_day = lcl_dpt_day;
	}
	public String getTim_cout() {
		return tim_cout;
	}
	public void setTim_cout(String tim_cout) {
		this.tim_cout = tim_cout;
	}
	public String getDpt_airpt_cd() {
		return dpt_airpt_cd;
	}
	public void setDpt_airpt_cd(String dpt_airpt_cd) {
		this.dpt_airpt_cd = dpt_airpt_cd;
	}
	public String getArrv_airpt_cd() {
		return arrv_airpt_cd;
	}
	public void setArrv_airpt_cd(String arrv_airpt_cd) {
		this.arrv_airpt_cd = arrv_airpt_cd;
	}
	public String getPas_stp() {
		return pas_stp;
	}
	public void setPas_stp(String pas_stp) {
		this.pas_stp = pas_stp;
	}
	public String getCpy_nm() {
		return cpy_nm;
	}
	public void setCpy_nm(String cpy_nm) {
		this.cpy_nm = cpy_nm;
	}
	public String getFlt_nbr() {
		return flt_nbr;
	}
	public void setFlt_nbr(String flt_nbr) {
		this.flt_nbr = flt_nbr;
	}
	public String getDta_sce() {
		return dta_sce;
	}
	public void setDta_sce(String dta_sce) {
		this.dta_sce = dta_sce;
	}
	@Override
	public String toString() {
		return "TimeCount [id=" + id + ", lcl_dpt_day=" + lcl_dpt_day
				+ ", tim_cout=" + tim_cout + ", dpt_airpt_cd=" + dpt_airpt_cd
				+ ", arrv_airpt_cd=" + arrv_airpt_cd + ", pas_stp=" + pas_stp
				+ ", cpy_nm=" + cpy_nm + ", flt_nbr=" + flt_nbr + ", dta_sce="
				+ dta_sce + "]";
	}
}
