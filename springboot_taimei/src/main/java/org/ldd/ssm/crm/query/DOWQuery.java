package org.ldd.ssm.crm.query;
/**
 * 高级查询加分页的封装对象
 */
public class DOWQuery {
	private String lcl_Dpt_Day;
	private String month;
	private String dta_Sce;
	private String dpt_AirPt_Cd;//始发地中文名
	private String china_dpt_AirPt_Cd;
	private String arrv_Airpt_Cd;//到达地中文名
	private String china_arrv_Airpt_Cd;
	private String flt_nbr;
	private String state;
	private String cpy_Nm;
	private String pas_stp;
	private String china_pas_stp;//经停中文名
	private String flt_Rte_Cd;
	private String startTime;
	private String endTime;
	private String monthOrDay;
	private String isIncludeExceptionData;//是否包含异常数据
	private String isIncludeExceptionHuangduan;//是否包含异常航段
	//查询时使用的字段
	private String china_Name;
	private String air_itia;
	
	public String getChina_Name() {
		return china_Name;
	}
	public void setChina_Name(String china_Name) {
		this.china_Name = china_Name;
	}
	public String getAir_itia() {
		return air_itia;
	}
	public void setAir_itia(String air_itia) {
		this.air_itia = air_itia;
	}
	//开始时间
	private String startDate;
	private Long companyId;
	//结束时间
	private String endDate;
	private String goNum;//去的航班号
	private String arrNum;//回的航班号
	
	//分页用的字段
	private Integer currentPage = 1;//当前页数
	private Integer pageSize = 5;//当前页数量
	
	public String getChina_pas_stp() {
		return china_pas_stp;
	}
	public void setChina_pas_stp(String china_pas_stp) {
		this.china_pas_stp = china_pas_stp;
	}
	public String getChina_dpt_AirPt_Cd() {
		return china_dpt_AirPt_Cd;
	}
	public void setChina_dpt_AirPt_Cd(String china_dpt_AirPt_Cd) {
		this.china_dpt_AirPt_Cd = china_dpt_AirPt_Cd;
	}
	public String getChina_arrv_Airpt_Cd() {
		return china_arrv_Airpt_Cd;
	}
	public void setChina_arrv_Airpt_Cd(String china_arrv_Airpt_Cd) {
		this.china_arrv_Airpt_Cd = china_arrv_Airpt_Cd;
	}
	public Integer getBeginIndex() {
		return (currentPage - 1)* pageSize;
	}
	public String getState() {
		return state;
	}
	
	public String getFlt_Rte_Cd() {
		return flt_Rte_Cd;
	}
	public void setFlt_Rte_Cd(String flt_Rte_Cd) {
		this.flt_Rte_Cd = flt_Rte_Cd;
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
	public void setState(String state) {
		this.state = state;
	}

	public String getFlt_nbr() {
		return flt_nbr;
	}

	public void setFlt_nbr(String flt_nbr) {
		this.flt_nbr = flt_nbr;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
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
	 * @return the goNum
	 */
	public String getGoNum() {
		return goNum;
	}
	/**
	 * @param goNum the goNum to set
	 */
	public void setGoNum(String goNum) {
		this.goNum = goNum;
	}
	/**
	 * @return the arrNum
	 */
	public String getArrNum() {
		return arrNum;
	}
	/**
	 * @param arrNum the arrNum to set
	 */
	public void setArrNum(String arrNum) {
		this.arrNum = arrNum;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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
	 * @return the monthOrDay
	 */
	public String getMonthOrDay() {
		return monthOrDay;
	}
	/**
	 * @param monthOrDay the monthOrDay to set
	 */
	public void setMonthOrDay(String monthOrDay) {
		this.monthOrDay = monthOrDay;
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
	@Override
	public String toString() {
		return "DOWQuery [lcl_Dpt_Day=" + lcl_Dpt_Day + ", month=" + month
				+ ", dta_Sce=" + dta_Sce + ", dpt_AirPt_Cd=" + dpt_AirPt_Cd
				+ ", china_dpt_AirPt_Cd=" + china_dpt_AirPt_Cd
				+ ", arrv_Airpt_Cd=" + arrv_Airpt_Cd + ", china_arrv_Airpt_Cd="
				+ china_arrv_Airpt_Cd + ", flt_nbr=" + flt_nbr + ", state="
				+ state + ", cpy_Nm=" + cpy_Nm + ", pas_stp=" + pas_stp
				+ ", china_pas_stp=" + china_pas_stp + ", flt_Rte_Cd="
				+ flt_Rte_Cd + ", startTime=" + startTime + ", endTime="
				+ endTime + ", monthOrDay=" + monthOrDay
				+ ", isIncludeExceptionData=" + isIncludeExceptionData
				+ ", isIncludeExceptionHuangduan="
				+ isIncludeExceptionHuangduan + ", china_Name=" + china_Name
				+ ", air_itia=" + air_itia + ", startDate=" + startDate
				+ ", companyId=" + companyId + ", endDate=" + endDate
				+ ", goNum=" + goNum + ", arrNum=" + arrNum + ", currentPage="
				+ currentPage + ", pageSize=" + pageSize + "]";
	}
	
}