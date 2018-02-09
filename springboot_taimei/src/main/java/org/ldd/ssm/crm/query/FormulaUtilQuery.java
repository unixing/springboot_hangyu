package org.ldd.ssm.crm.query;
/**
 * 公式计算查询条件实体类
 * @Title:FormulaUtilQuery 
 * @Description:
 * @author taimei-gds 
 * @date 2016-5-25 下午2:26:50
 */
public class FormulaUtilQuery  implements Cloneable{
	private String lcl_Dpt_Day;//日期
	private String dta_Sce;//数据源
	private String dpt_AirPt_Cd;//始发地
	private String arrv_Airpt_Cd;//目的地
	private String pas_stp;//经停地
	private String fly_num;//航班号
	private String site_num;//座位数
	private String roleId;//角色ID
	private String totalTime;//总飞行时间
	private String hourPrice;//小时成本
	private String personTeemNum;//散团人数
	private String onPerson;//上客人数
	private String personTeemIne;//散团收入
	private String flt_nbr_Count;//航班号
	private String day;//日报日期
	private String weekly;//周报日期
	private String month;//月报月份
	private String year;//年
	private String startTime;
	private String endTime;
	private String goNum;//去的航班号
	private String huiNum;//回来的航班号
	private String isIncludeExceptionData;//是否包含异常数据
	private String isIncludePasDpt;//是否包含经停
	/**
	 * @return the lcl_Dpt_Day
	 */
	public String getLcl_Dpt_Day() {
		return lcl_Dpt_Day;
	}
	/**
	 * @param lcl_Dpt_Day the lcl_Dpt_Day to set
	 */
	public void setLcl_Dpt_Day(String lcl_Dpt_Day) {
		this.lcl_Dpt_Day = lcl_Dpt_Day;
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
	 * @return the fly_num
	 */
	public String getFly_num() {
		return fly_num;
	}
	/**
	 * @param fly_num the fly_num to set
	 */
	public void setFly_num(String fly_num) {
		this.fly_num = fly_num;
	}
	/**
	 * @return the site_num
	 */
	public String getSite_num() {
		return site_num;
	}
	/**
	 * @param site_num the site_num to set
	 */
	public void setSite_num(String site_num) {
		this.site_num = site_num;
	}
	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	/**
	 * @return the totalTime
	 */
	public String getTotalTime() {
		return totalTime;
	}
	/**
	 * @param totalTime the totalTime to set
	 */
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
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
	 * @return the personTeemNum
	 */
	public String getPersonTeemNum() {
		return personTeemNum;
	}
	/**
	 * @param personTeemNum the personTeemNum to set
	 */
	public void setPersonTeemNum(String personTeemNum) {
		this.personTeemNum = personTeemNum;
	}
	/**
	 * @return the onPerson
	 */
	public String getOnPerson() {
		return onPerson;
	}
	/**
	 * @param onPerson the onPerson to set
	 */
	public void setOnPerson(String onPerson) {
		this.onPerson = onPerson;
	}
	/**
	 * @return the personTeemIne
	 */
	public String getPersonTeemIne() {
		return personTeemIne;
	}
	/**
	 * @param personTeemIne the personTeemIne to set
	 */
	public void setPersonTeemIne(String personTeemIne) {
		this.personTeemIne = personTeemIne;
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
	
	@Override  
    public Object clone() throws CloneNotSupportedException  
    {  
        return super.clone();  
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FormulaUtilQuery [lcl_Dpt_Day=" + lcl_Dpt_Day + ", dta_Sce="
				+ dta_Sce + ", dpt_AirPt_Cd=" + dpt_AirPt_Cd
				+ ", arrv_Airpt_Cd=" + arrv_Airpt_Cd + ", pas_stp=" + pas_stp
				+ ", fly_num=" + fly_num + ", site_num=" + site_num
				+ ", roleId=" + roleId + ", totalTime=" + totalTime
				+ ", hourPrice=" + hourPrice + ", personTeemNum="
				+ personTeemNum + ", onPerson=" + onPerson + ", personTeemIne="
				+ personTeemIne + ", flt_nbr_Count=" + flt_nbr_Count + ", day="
				+ day + ", weekly=" + weekly + ", month=" + month + ", year="
				+ year + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", goNum=" + goNum + ", huiNum=" + huiNum
				+ ", isIncludeExceptionData=" + isIncludeExceptionData
				+ ", isIncludePasDpt=" + isIncludePasDpt + "]";
	}  
	
	
	
}