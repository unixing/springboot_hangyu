package org.ldd.ssm.crm.query;

import java.util.List;

/**
 * 
 * @Title:HomePageQuery 
 * @Description:
 * @author taimei-gds 
 * @date 2016-6-12 下午3:46:15
 */
public class HomePageQuery {
	private String szm;
	//机场
	private String airPort;
	//日期
	private String date;
	//数据源
	private String date_sce;
	//开始时间
	private String startTime;
	//结束时间
	private String endTime;
	
	private String arrAirport;//到达机场
	
	private String companyId;//公司ID
	
	private List<String> flightRange;//关注航班号列表
	
	private String dataRange;
	
	private List<String> fltRoutes;//航线列表
	
	/**
	 * @return the airPort
	 */
	public String getAirPort() {
		return airPort;
	}
	/**
	 * @param airPort the airPort to set
	 */
	public void setAirPort(String airPort) {
		this.airPort = airPort;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the date_sce
	 */
	public String getDate_sce() {
		return date_sce;
	}
	/**
	 * @param date_sce the date_sce to set
	 */
	public void setDate_sce(String date_sce) {
		this.date_sce = date_sce;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	
	
	/**
	 * @return the szm
	 */
	public String getSzm() {
		return szm;
	}
	/**
	 * @param szm the szm to set
	 */
	public void setSzm(String szm) {
		this.szm = szm;
	}
	/**
	 * @return the arrAirport
	 */
	public String getArrAirport() {
		return arrAirport;
	}
	/**
	 * @param arrAirport the arrAirport to set
	 */
	public void setArrAirport(String arrAirport) {
		this.arrAirport = arrAirport;
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
	
	public List<String> getFlightRange() {
		return flightRange;
	}
	public void setFlightRange(List<String> flightRange) {
		this.flightRange = flightRange;
	}
	public String getDataRange() {
		return dataRange;
	}
	public void setDataRange(String dataRange) {
		this.dataRange = dataRange;
	}
	
	public List<String> getFltRoutes() {
		return fltRoutes;
	}
	public void setFltRoutes(List<String> fltRoutes) {
		this.fltRoutes = fltRoutes;
	}
	@Override
	public String toString() {
		return "HomePageQuery [szm=" + szm + ", airPort=" + airPort + ", date="
				+ date + ", date_sce=" + date_sce + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", arrAirport=" + arrAirport
				+ ", companyId=" + companyId + ", flightRange=" + flightRange
				+ ", dataRange=" + dataRange + ", fltRoutes=" + fltRoutes + "]";
	}
	
}
