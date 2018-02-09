package org.ldd.ssm.crm.domain;
/**
 * 收益汇总的时间参数类
 */
public class IncomeCountTime {
	private String id; 
	private String lcl_Dpt_Day; // 时间
	private String tim_Cout; // 总飞时间
	private String tim_Cot; // 小时成本
	private String agy_Rte; // 代理汇率
	private String dpt_AirPt_Cd; // 始发地
	private String arrv_Airpt_Cd; // 到达地
	private String pas_stp; // 经停地
	private String cpy_Nm; // 公司
	private String flt_nbr; // 航班号
	private String dta_Sce; // 数据源
	private String flt_Rte_Cd;
	public String getFlt_Rte_Cd() {
		return flt_Rte_Cd;
	}
	public void setFlt_Rte_Cd(String flt_Rte_Cd) {
		this.flt_Rte_Cd = flt_Rte_Cd;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLcl_Dpt_Day() {
		return lcl_Dpt_Day;
	}
	public void setLcl_Dpt_Day(String lcl_Dpt_Day) {
		this.lcl_Dpt_Day = lcl_Dpt_Day;
	}
	public String getTim_Cout() {
		return tim_Cout;
	}
	public void setTim_Cout(String tim_Cout) {
		this.tim_Cout = tim_Cout;
	}
	public String getTim_Cot() {
		return tim_Cot;
	}
	public void setTim_Cot(String tim_Cot) {
		this.tim_Cot = tim_Cot;
	}
	public String getAgy_Rte() {
		return agy_Rte;
	}
	public void setAgy_Rte(String agy_Rte) {
		this.agy_Rte = agy_Rte;
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
	public String getDta_Sce() {
		return dta_Sce;
	}
	public void setDta_Sce(String dta_Sce) {
		this.dta_Sce = dta_Sce;
	}
	@Override
	public String toString() {
		return "IncomeCountTime [id=" + id + ", lcl_Dpt_Day=" + lcl_Dpt_Day
				+ ", tim_Cout=" + tim_Cout + ", tim_Cot=" + tim_Cot
				+ ", agy_Rte=" + agy_Rte + ", dpt_AirPt_Cd=" + dpt_AirPt_Cd
				+ ", arrv_Airpt_Cd=" + arrv_Airpt_Cd + ", pas_stp=" + pas_stp
				+ ", cpy_Nm=" + cpy_Nm + ", flt_nbr=" + flt_nbr + ", dta_Sce="
				+ dta_Sce + ", flt_Rte_Cd=" + flt_Rte_Cd + "]";
	}

}
