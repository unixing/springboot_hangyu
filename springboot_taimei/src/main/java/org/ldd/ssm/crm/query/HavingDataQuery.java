package org.ldd.ssm.crm.query;
/**
 * 有无30天数据的查询条件实体类
 * @Title:HavingDataQuery 
 * @Description:
 * @author taimei-gds 
 * @date 2017-4-14 下午2:20:47
 */
public class HavingDataQuery {
	private String dpt_AirPt_Cd;// 出发
	private String Arrv_Airpt_Cd;//到达
	private String pas_stp;//经停
	private String isGoAndBack;//是否包含返回
	private String startTime;//开始日期
	private String endTime;//结束日期
	private String flt_ectq;//航线去
	private String flt_ecth;//航线回
	private String fltNbr;//航班号
	private String fltNbrq;//航班号去
	private String fltNbrh;//航班号回
	private String isIncludeExceptionData;//是否包含异常数据
	private String isIncludeExceptionHuangduan;//是否包含异常航段
	private String isIncludePas;//是否包含经停
	private String isIncludeRdf;//包含直飞
	private String lineFlag;//直飞经停航线标识（1表示直飞，2表示经停）
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
	 * @return the fltNbr
	 */
	public String getFltNbr() {
		return fltNbr;
	}
	/**
	 * @param fltNbr the fltNbr to set
	 */
	public void setFltNbr(String fltNbr) {
		this.fltNbr = fltNbr;
	}
	
	/**
	 * @return the flt_ectq
	 */
	public String getFlt_ectq() {
		return flt_ectq;
	}
	/**
	 * @param flt_ectq the flt_ectq to set
	 */
	public void setFlt_ectq(String flt_ectq) {
		this.flt_ectq = flt_ectq;
	}
	/**
	 * @return the flt_ecth
	 */
	public String getFlt_ecth() {
		return flt_ecth;
	}
	/**
	 * @param flt_ecth the flt_ecth to set
	 */
	public void setFlt_ecth(String flt_ecth) {
		this.flt_ecth = flt_ecth;
	}
	/**
	 * @return the fltNbrq
	 */
	public String getFltNbrq() {
		return fltNbrq;
	}
	/**
	 * @param fltNbrq the fltNbrq to set
	 */
	public void setFltNbrq(String fltNbrq) {
		this.fltNbrq = fltNbrq;
	}
	/**
	 * @return the fltNbrh
	 */
	public String getFltNbrh() {
		return fltNbrh;
	}
	/**
	 * @param fltNbrh the fltNbrh to set
	 */
	public void setFltNbrh(String fltNbrh) {
		this.fltNbrh = fltNbrh;
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
	 * @return the lineFlag
	 */
	public String getLineFlag() {
		return lineFlag;
	}
	/**
	 * @param lineFlag the lineFlag to set
	 */
	public void setLineFlag(String lineFlag) {
		this.lineFlag = lineFlag;
	}
	@Override
	public String toString() {
		return "HavingDataQuery [dpt_AirPt_Cd=" + dpt_AirPt_Cd
				+ ", Arrv_Airpt_Cd=" + Arrv_Airpt_Cd + ", pas_stp=" + pas_stp
				+ ", isGoAndBack=" + isGoAndBack + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", flt_ectq=" + flt_ectq
				+ ", flt_ecth=" + flt_ecth + ", fltNbr=" + fltNbr
				+ ", fltNbrq=" + fltNbrq + ", fltNbrh=" + fltNbrh
				+ ", isIncludeExceptionData=" + isIncludeExceptionData
				+ ", isIncludeExceptionHuangduan="
				+ isIncludeExceptionHuangduan + ", isIncludePas="
				+ isIncludePas + ", isIncludeRdf=" + isIncludeRdf
				+ ", lineFlag=" + lineFlag + "]";
	}
	
	
}
