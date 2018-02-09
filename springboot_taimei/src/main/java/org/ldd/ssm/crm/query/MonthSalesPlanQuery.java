package org.ldd.ssm.crm.query;

public class MonthSalesPlanQuery implements Cloneable{
	private String lcl_Dpt_Day;//出发时间
	private String dpt_AirPt_Cd;//出发地
	private String arrv_Airpt_Cd;//到达地
	private String pas_stp;//经停地
	private String flt_Rte_Cd;
	private String flt_nbr;//航班号
	private String dta_Sce;//往返
	private Long companyId;
	private String fltDct;
	private int type;//查询类型 1.查询相关参数，2.查询列表数据
	private String flight;
	private Integer timeId;
	
	@Override  
    public Object clone() throws CloneNotSupportedException  
    {  
        return super.clone();  
    }
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPas_stp() {
		return pas_stp;
	}
	public void setPas_stp(String pas_stp) {
		this.pas_stp = pas_stp;
	}
	public String getLcl_Dpt_Day() {
		return lcl_Dpt_Day;
	}
	public void setLcl_Dpt_Day(String lcl_Dpt_Day) {
		this.lcl_Dpt_Day = lcl_Dpt_Day;
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
		this.flt_nbr = "".equals(flt_nbr)?null:flt_nbr;
	}
	
	public String getDta_Sce() {
		return dta_Sce;
	}
	public void setDta_Sce(String dta_Sce) {
		this.dta_Sce = dta_Sce;
	}
	public String getFlt_Rte_Cd() {
		return flt_Rte_Cd;
	}
	public void setFlt_Rte_Cd(String flt_Rte_Cd) {
		this.flt_Rte_Cd = flt_Rte_Cd;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public String getFlight() {
		return flight;
	}
	public void setFlight(String flight) {
		this.flight = flight;
	}
	
	public String getFltDct() {
		return fltDct;
	}
	public void setFltDct(String fltDct) {
		this.fltDct = fltDct;
	}
	
	public Integer getTimeId() {
		return timeId;
	}
	public void setTimeId(Integer timeId) {
		this.timeId = timeId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MonthSalesPlanQuery [lcl_Dpt_Day=" + lcl_Dpt_Day
				+ ", dpt_AirPt_Cd=" + dpt_AirPt_Cd + ", arrv_Airpt_Cd="
				+ arrv_Airpt_Cd + ", pas_stp=" + pas_stp + ", flt_Rte_Cd="
				+ flt_Rte_Cd + ", flt_nbr=" + flt_nbr + ", dta_Sce=" + dta_Sce
				+ ", companyId=" + companyId + ", type=" + type + "]";
	}
	
}
