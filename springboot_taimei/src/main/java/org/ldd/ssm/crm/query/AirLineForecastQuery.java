package org.ldd.ssm.crm.query;

public class AirLineForecastQuery {
	private String startDate;
	private String endDate;
	
	private String dta_Sce;
	private String dpt_AirPt_Cd;
	private String arrv_Airpt_Cd;
	private String pas_stp;
	private String airLineForecastId;
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
		return dpt_AirPt_Cd;
	}
	/**
	 * @param dpt_AirPt_Cd the dpt_AirPt_Cd to set
	 */
	public void setDpt_AirPt_Cd(String dpt_AirPt_Cd) {
		this.dpt_AirPt_Cd = dpt_AirPt_Cd;
	}
	/**
	 * @return the arrv_Airpt_Cd
	 */
	public String getArrv_Airpt_Cd() {
		return arrv_Airpt_Cd;
	}
	/**
	 * @param arrv_Airpt_Cd the arrv_Airpt_Cd to set
	 */
	public void setArrv_Airpt_Cd(String arrv_Airpt_Cd) {
		this.arrv_Airpt_Cd = arrv_Airpt_Cd;
	}
	/**
	 * @return the pas_stp
	 */
	public String getPas_stp() {
		return pas_stp;
	}
	/**
	 * @param pas_stp the pas_stp to set
	 */
	public void setPas_stp(String pas_stp) {
		this.pas_stp = pas_stp;
	}
	/**
	 * @return the airLineForecastId
	 */
	public String getAirLineForecastId() {
		return airLineForecastId;
	}
	/**
	 * @param airLineForecastId the airLineForecastId to set
	 */
	public void setAirLineForecastId(String airLineForecastId) {
		this.airLineForecastId = airLineForecastId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AirLineForecastQuery [startDate=" + startDate + ", endDate="
				+ endDate + ", dta_Sce=" + dta_Sce + ", dpt_AirPt_Cd="
				+ dpt_AirPt_Cd + ", arrv_Airpt_Cd=" + arrv_Airpt_Cd
				+ ", pas_stp=" + pas_stp + ", airLineForecastId="
				+ airLineForecastId + "]";
	}
	
	
}