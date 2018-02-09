/**   
 * @Title: TimeQuery.java 
 * @Package org.ldd.ssm.crm.domain 
 * @Description: TODO
 * @author taimei-gds   
 * @date 2016-6-30 下午3:21:35 
 * @version V1.0   
 */ 
package org.ldd.ssm.crm.domain;

/**
 * @Title:TimeQuery 
 * @Description:
 * @author taimei-gds 
 * @date 2016-6-30 下午3:21:35
 */
public class TimeQuery {
	private String allTime;//总飞时间
	private String bc;//所有次数
	/**
	 * @return the allTime
	 */
	public String getAllTime() {
		return allTime;
	}
	/**
	 * @param allTime the allTime to set
	 */
	public void setAllTime(String allTime) {
		this.allTime = allTime;
	}
	/**
	 * @return the bc
	 */
	public String getBc() {
		return bc;
	}
	/**
	 * @param bc the bc to set
	 */
	public void setBc(String bc) {
		this.bc = bc;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TimeQuery [allTime=" + allTime + ", bc=" + bc + "]";
	}
	
	
}
