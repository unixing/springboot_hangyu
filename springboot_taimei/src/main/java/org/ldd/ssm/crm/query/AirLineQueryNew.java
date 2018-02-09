package org.ldd.ssm.crm.query;
/**
 * 历史运营统计查询条件实体
 * @Title:AirLineQueryNew 
 * @Description:
 * @author taimei-gds 
 * @date 2016-10-19 上午11:39:17
 */
public class AirLineQueryNew {
	
	private String dpt_AirPt_Cd;
	private String pas_stp;
	private String arrv_Airpt_Cd;
	private String flt_nbr;
	private String goNum;
	private String huiNum;
	private String hangsi;
	private String isIncludeExceptionData;//是否包含异常数据
	private String isIncludeExceptionHuangduan;//是否包含异常航段
	private String isIncludePas;//是否包含经停
	private String isIncludeRdf;//包含直飞
	private String isDayOrMonth;//按月份查询还是日查询
	private String startTime;
	private String endTime;
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
	 * @return the flt_nbr
	 */
	public String getFlt_nbr() {
		return flt_nbr;
	}
	/**
	 * @param flt_nbr the flt_nbr to set
	 */
	public void setFlt_nbr(String flt_nbr) {
		this.flt_nbr = flt_nbr;
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
	 * @return the isDayOrMonth
	 */
	public String getIsDayOrMonth() {
		return isDayOrMonth;
	}
	/**
	 * @param isDayOrMonth the isDayOrMonth to set
	 */
	public void setIsDayOrMonth(String isDayOrMonth) {
		this.isDayOrMonth = isDayOrMonth;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AirLineQueryNew [dpt_AirPt_Cd=" + dpt_AirPt_Cd + ", pas_stp="
				+ pas_stp + ", arrv_Airpt_Cd=" + arrv_Airpt_Cd + ", flt_nbr="
				+ flt_nbr + ", goNum=" + goNum + ", huiNum=" + huiNum
				+ ", hangsi=" + hangsi + ", isIncludeExceptionData="
				+ isIncludeExceptionData + ", isIncludeExceptionHuangduan="
				+ isIncludeExceptionHuangduan + ", isIncludePas="
				+ isIncludePas + ", isIncludeRdf=" + isIncludeRdf
				+ ", isDayOrMonth=" + isDayOrMonth + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}
	
	
}