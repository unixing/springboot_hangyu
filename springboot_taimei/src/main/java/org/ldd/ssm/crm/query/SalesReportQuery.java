package org.ldd.ssm.crm.query;
/**
 * 销售报表查询对象
 * @Title:SalesReportQuery 
 * @Description:
 * @author taimei-gds 
 * @date 2016-4-20 下午3:17:11
 */
public class SalesReportQuery {
	
	private String dpt_AirPt_Cd;// 出发
	private String Arrv_Airpt_Cd;//到达
	private String pas_stp;//经停
	private String flt_nbr_Count;//航班号
	private String day;//日报日期
	private String weekly;//周报日期
	private String month;//月报月份
	private String year;//年
	private String dta_Sce;//数据源
	private String flt_Rte_Cd;//航线
	private String flt_Rte_Cd2;//航线
	private String flyTime;//总飞行时间
	private String hourPrice;//小时成本
	private String startTime;
	private String endTime;
	private String goNum;//去的航班号
	private String huiNum;//回来的航班号
	private String hangsi;//航司
	private String isIncludeExceptionData;//是否包含异常数据
	private String isIncludeExceptionHuangduan;//是否包含异常航段
	private String isIncludePasDpt;//是否包含经停
	private String tempNum;//班次查询临时航班号
	private String eclass;//实际执行班次
	private String isFltAir;//是否是航线（区分经停）
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
		return Arrv_Airpt_Cd;
	}
	/**
	 * @param arrv_Airpt_Cd the arrv_Airpt_Cd to set
	 */
	public void setArrv_Airpt_Cd(String arrv_Airpt_Cd) {
		Arrv_Airpt_Cd = arrv_Airpt_Cd;
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
	 * @return the day
	 */
	public String getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}
	/**
	 * @return the weekly
	 */
	public String getWeekly() {
		return weekly;
	}
	/**
	 * @param weekly the weekly to set
	 */
	public void setWeekly(String weekly) {
		this.weekly = weekly;
	}
	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
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
	 * @return the flt_Rte_Cd
	 */
	public String getFlt_Rte_Cd() {
		return flt_Rte_Cd;
	}
	/**
	 * @param flt_Rte_Cd the flt_Rte_Cd to set
	 */
	public void setFlt_Rte_Cd(String flt_Rte_Cd) {
		this.flt_Rte_Cd = flt_Rte_Cd;
	}
	/**
	 * @return the flyTime
	 */
	public String getFlyTime() {
		return flyTime;
	}
	/**
	 * @param flyTime the flyTime to set
	 */
	public void setFlyTime(String flyTime) {
		this.flyTime = flyTime;
	}
	/**
	 * @return the hourPrice
	 */
	public String getHourPrice() {
		return hourPrice;
	}
	/**
	 * @param hourPrice the hourPrice to set
	 */
	public void setHourPrice(String hourPrice) {
		this.hourPrice = hourPrice;
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
	 * @return the huiNum
	 */
	public String getHuiNum() {
		return huiNum;
	}
	/**
	 * @param huiNum the huiNum to set
	 */
	public void setHuiNum(String huiNum) {
		this.huiNum = huiNum;
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
	 * @return the isIncludePasDpt
	 */
	public String getIsIncludePasDpt() {
		return isIncludePasDpt;
	}
	/**
	 * @param isIncludePasDpt the isIncludePasDpt to set
	 */
	public void setIsIncludePasDpt(String isIncludePasDpt) {
		this.isIncludePasDpt = isIncludePasDpt;
	}
	/**
	 * @return the tempNum
	 */
	public String getTempNum() {
		return tempNum;
	}
	/**
	 * @param tempNum the tempNum to set
	 */
	public void setTempNum(String tempNum) {
		this.tempNum = tempNum;
	}
	/**
	 * @return the eclass
	 */
	public String getEclass() {
		return eclass;
	}
	/**
	 * @param eclass the eclass to set
	 */
	public void setEclass(String eclass) {
		this.eclass = eclass;
	}
	/**
	 * @return the isFltAir
	 */
	public String getIsFltAir() {
		return isFltAir;
	}
	/**
	 * @param isFltAir the isFltAir to set
	 */
	public void setIsFltAir(String isFltAir) {
		this.isFltAir = isFltAir;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SalesReportQuery [dpt_AirPt_Cd=" + dpt_AirPt_Cd
				+ ", Arrv_Airpt_Cd=" + Arrv_Airpt_Cd + ", pas_stp=" + pas_stp
				+ ", flt_nbr_Count=" + flt_nbr_Count + ", day=" + day
				+ ", weekly=" + weekly + ", month=" + month + ", year=" + year
				+ ", dta_Sce=" + dta_Sce + ", flt_Rte_Cd=" + flt_Rte_Cd
				+ ", flt_Rte_Cd2=" + flt_Rte_Cd2 + ", flyTime=" + flyTime
				+ ", hourPrice=" + hourPrice + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", goNum=" + goNum + ", huiNum="
				+ huiNum + ", hangsi=" + hangsi + ", isIncludeExceptionData="
				+ isIncludeExceptionData + ", isIncludeExceptionHuangduan="
				+ isIncludeExceptionHuangduan + ", isIncludePasDpt="
				+ isIncludePasDpt + ", tempNum=" + tempNum + ", eclass="
				+ eclass + ", isFltAir=" + isFltAir + "]";
	}
	
}