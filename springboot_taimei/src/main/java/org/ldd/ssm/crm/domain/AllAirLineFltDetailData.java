package org.ldd.ssm.crm.domain;

/**
 * 全国航线视图下的返回实体类
 * @Title:AllAirLineFltDetailData 
 * @Description:
 * @author taimei-gds 
 * @date 2017-7-27 上午11:04:01
 */
public class AllAirLineFltDetailData {
	private String flyNbr;
	private String jixing;
	private String hangxian;
	private String dpt_AirPt_Cd;
	private String arrv_Airpt_Cd;
	private String dpt_Itia;
	private String arrv_Itia;
	private String goTime;
	private String arrTime;
	private String banQi;
	
	
	public String getDpt_Itia() {
		return dpt_Itia;
	}
	public void setDpt_Itia(String dpt_Itia) {
		this.dpt_Itia = dpt_Itia;
	}
	public String getArrv_Itia() {
		return arrv_Itia;
	}
	public void setArrv_Itia(String arrv_Itia) {
		this.arrv_Itia = arrv_Itia;
	}
	/**
	 * @return the flyNbr
	 */
	public String getFlyNbr() {
		return flyNbr;
	}
	/**
	 * @param flyNbr the flyNbr to set
	 */
	public void setFlyNbr(String flyNbr) {
		this.flyNbr = flyNbr;
	}
	/**
	 * @return the jixing
	 */
	public String getJixing() {
		return jixing;
	}
	/**
	 * @param jixing the jixing to set
	 */
	public void setJixing(String jixing) {
		this.jixing = jixing;
	}
	/**
	 * @return the hangxian
	 */
	public String getHangxian() {
		return hangxian;
	}
	/**
	 * @param hangxian the hangxian to set
	 */
	public void setHangxian(String hangxian) {
		this.hangxian = hangxian;
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
	 * @return the goTime
	 */
	public String getGoTime() {
		return goTime;
	}
	/**
	 * @param goTime the goTime to set
	 */
	public void setGoTime(String goTime) {
		this.goTime = goTime;
	}
	/**
	 * @return the arrTime
	 */
	public String getArrTime() {
		return arrTime;
	}
	/**
	 * @param arrTime the arrTime to set
	 */
	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}
	/**
	 * @return the banQi
	 */
	public String getBanQi() {
		return banQi;
	}
	/**
	 * @param banQi the banQi to set
	 */
	public void setBanQi(String banQi) {
		this.banQi = banQi;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AllAirLineFltDetailData [flyNbr=" + flyNbr + ", jixing="
				+ jixing + ", hangxian=" + hangxian + ", dpt_AirPt_Cd="
				+ dpt_AirPt_Cd + ", arrv_Airpt_Cd=" + arrv_Airpt_Cd
				+ ", goTime=" + goTime + ", arrTime=" + arrTime + ", banQi="
				+ banQi + "]";
	}
	
	
}
