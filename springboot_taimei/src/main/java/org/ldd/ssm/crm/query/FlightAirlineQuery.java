package org.ldd.ssm.crm.query;
/**
 * 航路查询条件组装类
 * @Title:FlightAirlineQuery 
 * @Description:
 * @author taimei-gds 
 * @date 2017-3-31 上午11:40:12
 */
public class FlightAirlineQuery {
	private String stratCity;//起始地
	private String pasCity1;//经停1
	private String pasCity2;//经停2
	private String endCity;//到达地
	/**
	 * @return the stratCity
	 */
	public String getStratCity() {
		return stratCity;
	}
	/**
	 * @param stratCity the stratCity to set
	 */
	public void setStratCity(String stratCity) {
		this.stratCity = stratCity;
	}
	/**
	 * @return the pasCity1
	 */
	public String getPasCity1() {
		return pasCity1;
	}
	/**
	 * @param pasCity1 the pasCity1 to set
	 */
	public void setPasCity1(String pasCity1) {
		this.pasCity1 = pasCity1;
	}
	/**
	 * @return the pasCity2
	 */
	public String getPasCity2() {
		return pasCity2;
	}
	/**
	 * @param pasCity2 the pasCity2 to set
	 */
	public void setPasCity2(String pasCity2) {
		this.pasCity2 = pasCity2;
	}
	/**
	 * @return the endCity
	 */
	public String getEndCity() {
		return endCity;
	}
	/**
	 * @param endCity the endCity to set
	 */
	public void setEndCity(String endCity) {
		this.endCity = endCity;
	}
	@Override
	public String toString() {
		return "FlightAirlineQuery [stratCity=" + stratCity + ", pasCity1="
				+ pasCity1 + ", pasCity2=" + pasCity2 + ", endCity=" + endCity
				+ "]";
	}
	
}
