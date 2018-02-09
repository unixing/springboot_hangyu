package org.ldd.ssm.crm.query;
/**
 * 高级查询加分页的封装对象
 */
public class TotalFlyAnalysisQuery {
	//数据源
	private String dta_Sce;
	//起始站
	private String dpt_AirPt_Cd;
	//终点站
	private String arrv_Airpt_Cd;
	//经停站
	private String pas_stp;
	//航司
	private String hangsi;
	//开始时间
	private String startDate;
	//结束时间
	private String endDate;
	//航线去
	private String flt_Rte_Cd1;
	//航线回
	private String flt_Rte_Cd2;
	//流量汇总
	private String flowCount;
	//均班客量
	private String allAmount;
	//航班量排行
	private String flightNum;
	//座收排行
	private String raskRanking;
	//客量排行
	private String passengerRank;
	//客量占比
	private String passengerCompared;
	//均班客量排行
	private String allClassRank;
	private Integer year;
	private Integer month;
	private String isIncludeExceptionData;//包含异常数据
	private String isIncludeExceptionHuangduan;//是否包含异常航段
	private String isIncludePas;//包含经停
	private String isIncludeRdf;//包含直飞
	private String isGoAndBack;//是否包含来回
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
	 * @return the hangsi
	 */
	public String getHangsi() {
		return hangsi;
	}
	/**
	 * @param hangsi the hangsi to set
	 */
	public void setHangsi(String hangsi) {
		this.hangsi = hangsi;
	}
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
	 * @return the flowCount
	 */
	public String getFlowCount() {
		return flowCount;
	}
	/**
	 * @param flowCount the flowCount to set
	 */
	public void setFlowCount(String flowCount) {
		this.flowCount = flowCount;
	}
	/**
	 * @return the allAmount
	 */
	public String getAllAmount() {
		return allAmount;
	}
	/**
	 * @param allAmount the allAmount to set
	 */
	public void setAllAmount(String allAmount) {
		this.allAmount = allAmount;
	}
	
	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	/**
	 * @return the month
	 */
	public Integer getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(Integer month) {
		this.month = month;
	}
	/**
	 * @return the flightNum
	 */
	public String getFlightNum() {
		return flightNum;
	}
	/**
	 * @param flightNum the flightNum to set
	 */
	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}
	/**
	 * @return the raskRanking
	 */
	public String getRaskRanking() {
		return raskRanking;
	}
	/**
	 * @param raskRanking the raskRanking to set
	 */
	public void setRaskRanking(String raskRanking) {
		this.raskRanking = raskRanking;
	}
	/**
	 * @return the passengerRank
	 */
	public String getPassengerRank() {
		return passengerRank;
	}
	/**
	 * @param passengerRank the passengerRank to set
	 */
	public void setPassengerRank(String passengerRank) {
		this.passengerRank = passengerRank;
	}
	/**
	 * @return the passengerCompared
	 */
	public String getPassengerCompared() {
		return passengerCompared;
	}
	/**
	 * @param passengerCompared the passengerCompared to set
	 */
	public void setPassengerCompared(String passengerCompared) {
		this.passengerCompared = passengerCompared;
	}
	/**
	 * @return the allClassRank
	 */
	public String getAllClassRank() {
		return allClassRank;
	}
	/**
	 * @param allClassRank the allClassRank to set
	 */
	public void setAllClassRank(String allClassRank) {
		this.allClassRank = allClassRank;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * @return the flt_Rte_Cd1
	 */
	public String getFlt_Rte_Cd1() {
		return flt_Rte_Cd1;
	}
	/**
	 * @param flt_Rte_Cd1 the flt_Rte_Cd1 to set
	 */
	public void setFlt_Rte_Cd1(String flt_Rte_Cd1) {
		this.flt_Rte_Cd1 = flt_Rte_Cd1;
	}
	/**
	 * @return the flt_Rte_Cd2
	 */
	public String getFlt_Rte_Cd2() {
		return flt_Rte_Cd2;
	}
	/**
	 * @param flt_Rte_Cd2 the flt_Rte_Cd2 to set
	 */
	public void setFlt_Rte_Cd2(String flt_Rte_Cd2) {
		this.flt_Rte_Cd2 = flt_Rte_Cd2;
	}
	/**
	 * @return the isIncludeExceptionData
	 */
	public String getIsIncludeExceptionData() {
		return isIncludeExceptionData;
	}
	/**
	 * @param isIncludeExceptionData the isIncludeExceptionData to set
	 */
	public void setIsIncludeExceptionData(String isIncludeExceptionData) {
		this.isIncludeExceptionData = isIncludeExceptionData;
	}
	/**
	 * @return the isIncludePas
	 */
	public String getIsIncludePas() {
		return isIncludePas;
	}
	/**
	 * @param isIncludePas the isIncludePas to set
	 */
	public void setIsIncludePas(String isIncludePas) {
		this.isIncludePas = isIncludePas;
	}
	/**
	 * @return the isGoAndBack
	 */
	public String getIsGoAndBack() {
		return isGoAndBack;
	}
	/**
	 * @param isGoAndBack the isGoAndBack to set
	 */
	public void setIsGoAndBack(String isGoAndBack) {
		this.isGoAndBack = isGoAndBack;
	}
	/**
	 * @return the isIncludeExceptionHuangduan
	 */
	public String getIsIncludeExceptionHuangduan() {
		return isIncludeExceptionHuangduan;
	}
	/**
	 * @param isIncludeExceptionHuangduan the isIncludeExceptionHuangduan to set
	 */
	public void setIsIncludeExceptionHuangduan(String isIncludeExceptionHuangduan) {
		this.isIncludeExceptionHuangduan = isIncludeExceptionHuangduan;
	}
	/**
	 * @return the isIncludeRdf
	 */
	public String getIsIncludeRdf() {
		return isIncludeRdf;
	}
	/**
	 * @param isIncludeRdf the isIncludeRdf to set
	 */
	public void setIsIncludeRdf(String isIncludeRdf) {
		this.isIncludeRdf = isIncludeRdf;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TotalFlyAnalysisQuery [dta_Sce=" + dta_Sce + ", dpt_AirPt_Cd="
				+ dpt_AirPt_Cd + ", arrv_Airpt_Cd=" + arrv_Airpt_Cd
				+ ", pas_stp=" + pas_stp + ", hangsi=" + hangsi
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", flt_Rte_Cd1=" + flt_Rte_Cd1 + ", flt_Rte_Cd2="
				+ flt_Rte_Cd2 + ", flowCount=" + flowCount + ", allAmount="
				+ allAmount + ", flightNum=" + flightNum + ", raskRanking="
				+ raskRanking + ", passengerRank=" + passengerRank
				+ ", passengerCompared=" + passengerCompared
				+ ", allClassRank=" + allClassRank + ", year=" + year
				+ ", month=" + month + ", isIncludeExceptionData="
				+ isIncludeExceptionData + ", isIncludeExceptionHuangduan="
				+ isIncludeExceptionHuangduan + ", isIncludePas="
				+ isIncludePas + ", isIncludeRdf=" + isIncludeRdf
				+ ", isGoAndBack=" + isGoAndBack + "]";
	}
	
}