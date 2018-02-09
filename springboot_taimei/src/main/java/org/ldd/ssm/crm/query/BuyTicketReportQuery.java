package org.ldd.ssm.crm.query;
/**
 * 上客进度表查询条件实体
 * @Title:BuyTicketReportQuery 
 * @Description:
 * @author taimei-gds 
 * @date 2016-5-19 上午11:59:01
 */
public class BuyTicketReportQuery {
	
	private String flt_nbr_Count;//航班号
	private String flt_nbr_Counth;//航班号回 
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String Dpt_AirPt_Cd;//起机场
	private String Arrv_Airpt_Cd;//到机场
	private String dta_Sce;//数据源
	private String pst_cd;//经停机场
	private String companyId;//公司ID
	private String airLine;//航线
	
	/**
	 * @return the flt_nbr_Count
	 */
	public String getFlt_nbr_Count() {
		return flt_nbr_Count;
	}
	/**
	 * @param flt_nbr_Count the flt_nbr_Count to set
	 */
	public void setFlt_nbr_Count(String flt_nbr_Count) {
		this.flt_nbr_Count = flt_nbr_Count;
	}
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the dta_Sce
	 */
	public String getDta_Sce() {
		return dta_Sce;
	}
	/**
	 * @param dta_Sce the dta_Sce to set
	 */
	public void setDta_Sce(String dta_Sce) {
		this.dta_Sce = dta_Sce;
	}
	/**
	 * @return the dpt_AirPt_Cd
	 */
	public String getDpt_AirPt_Cd() {
		return Dpt_AirPt_Cd;
	}
	/**
	 * @param dpt_AirPt_Cd the dpt_AirPt_Cd to set
	 */
	public void setDpt_AirPt_Cd(String dpt_AirPt_Cd) {
		Dpt_AirPt_Cd = dpt_AirPt_Cd;
	}
	/**
	 * @return the arrv_Airpt_Cd
	 */
	public String getArrv_Airpt_Cd() {
		return Arrv_Airpt_Cd;
	}
	/**
	 * @param arrv_Airpt_Cd the arrv_Airpt_Cd to set
	 */
	public void setArrv_Airpt_Cd(String arrv_Airpt_Cd) {
		Arrv_Airpt_Cd = arrv_Airpt_Cd;
	}
	/**
	 * @return the flt_nbr_Counth
	 */
	public String getFlt_nbr_Counth() {
		return flt_nbr_Counth;
	}
	/**
	 * @param flt_nbr_Counth the flt_nbr_Counth to set
	 */
	public void setFlt_nbr_Counth(String flt_nbr_Counth) {
		this.flt_nbr_Counth = flt_nbr_Counth;
	}
	/**
	 * @return the pst_cd
	 */
	public String getPst_cd() {
		return pst_cd;
	}
	/**
	 * @param pst_cd the pst_cd to set
	 */
	public void setPst_cd(String pst_cd) {
		this.pst_cd = pst_cd;
	}
	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the airLine
	 */
	public String getAirLine() {
		return airLine;
	}
	/**
	 * @param airLine the airLine to set
	 */
	public void setAirLine(String airLine) {
		this.airLine = airLine;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BuyTicketReportQuery [flt_nbr_Count=" + flt_nbr_Count
				+ ", flt_nbr_Counth=" + flt_nbr_Counth + ", startTime="
				+ startTime + ", endTime=" + endTime + ", Dpt_AirPt_Cd="
				+ Dpt_AirPt_Cd + ", Arrv_Airpt_Cd=" + Arrv_Airpt_Cd
				+ ", dta_Sce=" + dta_Sce + ", pst_cd=" + pst_cd
				+ ", companyId=" + companyId + ", airLine=" + airLine + "]";
	}
	
	
}