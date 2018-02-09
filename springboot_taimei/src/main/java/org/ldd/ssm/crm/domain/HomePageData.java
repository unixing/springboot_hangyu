package org.ldd.ssm.crm.domain;

import java.io.Serializable;

/**
 * 首页封装到前端的数据实体
 * @Title:HomePageData 
 * @Description:
 * @author taimei-gds 
 * @date 2016-6-13 上午10:19:00
 */
public class HomePageData implements Serializable{

	/** 
	 * @Fields 
	 */ 
	private static final long serialVersionUID = 1L;
	private String date;//日期
	private String airport;//机场
	private String persons;//吞吐量
	private String kzl;//客座率
	private String zgl;//座公里
	private String zsr;//总收入
	private String flyNum;//航班号
	private String arrAirport;//到达机场
	private String dptAirport;//经停机场
	private String flt_ret_cd;//航线
	private String zdl;//准点率
	private String zbc;//总班次
	private String state;//航线状态（0在飞航线,1历史航线,2无数据航线）
	
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
	 * @return the airport
	 */
	public String getAirport() {
		return airport;
	}
	/**
	 * @param airport the airport to set
	 */
	public void setAirport(String airport) {
		this.airport = airport;
	}
	/**
	 * @return the persons
	 */
	public String getPersons() {
		return persons;
	}
	/**
	 * @param persons the persons to set
	 */
	public void setPersons(String persons) {
		this.persons = persons;
	}
	/**
	 * @return the kzl
	 */
	public String getKzl() {
		return kzl;
	}
	/**
	 * @param kzl the kzl to set
	 */
	public void setKzl(String kzl) {
		this.kzl = kzl;
	}
	/**
	 * @return the zgl
	 */
	public String getZgl() {
		return zgl;
	}
	/**
	 * @param zgl the zgl to set
	 */
	public void setZgl(String zgl) {
		this.zgl = zgl;
	}
	/**
	 * @return the zsr
	 */
	public String getZsr() {
		return zsr;
	}
	/**
	 * @param zsr the zsr to set
	 */
	public void setZsr(String zsr) {
		this.zsr = zsr;
	}
	/**
	 * @return the flyNum
	 */
	public String getFlyNum() {
		return flyNum;
	}
	/**
	 * @param flyNum the flyNum to set
	 */
	public void setFlyNum(String flyNum) {
		this.flyNum = flyNum;
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
	 * @return the dptAirport
	 */
	public String getDptAirport() {
		return dptAirport;
	}
	/**
	 * @param dptAirport the dptAirport to set
	 */
	public void setDptAirport(String dptAirport) {
		this.dptAirport = dptAirport;
	}
	
	/**
	 * @return the flt_ret_cd
	 */
	public String getFlt_ret_cd() {
		return flt_ret_cd;
	}
	/**
	 * @param flt_ret_cd the flt_ret_cd to set
	 */
	public void setFlt_ret_cd(String flt_ret_cd) {
		this.flt_ret_cd = flt_ret_cd;
	}
	/**
	 * @return the zdl
	 */
	public String getZdl() {
		return zdl;
	}
	/**
	 * @param zdl the zdl to set
	 */
	public void setZdl(String zdl) {
		this.zdl = zdl;
	}
	public String getZbc() {
		return zbc;
	}
	public void setZbc(String zbc) {
		this.zbc = zbc;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HomePageData [date=" + date + ", airport=" + airport
				+ ", persons=" + persons + ", kzl=" + kzl + ", zgl=" + zgl
				+ ", zsr=" + zsr + ", flyNum=" + flyNum + ", arrAirport="
				+ arrAirport + ", dptAirport=" + dptAirport + ", flt_ret_cd="
				+ flt_ret_cd + ", zdl=" + zdl + ", zbc=" + zbc + ", state="
				+ state + "]";
	}
	
}
