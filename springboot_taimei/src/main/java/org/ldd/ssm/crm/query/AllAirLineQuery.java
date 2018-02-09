package org.ldd.ssm.crm.query;
/**
 * 全国航线查询信息实体类
 * @Title:AllAirLineQuery 
 * @Description:
 * @author taimei-gds 
 * @date 2017-7-27 上午10:26:41
 */
public class AllAirLineQuery {
	private String airport;//机场
	private String hangSi;//航司
	private String newTime;//最新有数据的日期
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
	 * @return the hangSi
	 */
	public String getHangSi() {
		return hangSi;
	}
	/**
	 * @param hangSi the hangSi to set
	 */
	public void setHangSi(String hangSi) {
		this.hangSi = hangSi;
	}
	/**
	 * @return the newTime
	 */
	public String getNewTime() {
		return newTime;
	}
	/**
	 * @param newTime the newTime to set
	 */
	public void setNewTime(String newTime) {
		this.newTime = newTime;
	}
	@Override
	public String toString() {
		return "AllAirLineQuery [airport=" + airport + ", hangSi=" + hangSi
				+ ", newTime=" + newTime + "]";
	}
	
}
