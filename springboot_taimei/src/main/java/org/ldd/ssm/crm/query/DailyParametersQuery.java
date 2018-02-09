package org.ldd.ssm.crm.query;
/**
 * 报表参数设置参数传递类
 * @Title:DailyParametersQuery 
 * @Description:
 * @author taimei-gds 
 * @date 2016-5-24 下午3:28:18
 */
public class DailyParametersQuery {
	

	private String dayTime;//日期
	private int fly_site;//座位数
	private int roleId;
	/**
	 * @return the dayTime
	 */
	public String getDayTime() {
		return dayTime;
	}
	/**
	 * @param dayTime the dayTime to set
	 */
	public void setDayTime(String dayTime) {
		this.dayTime = dayTime;
	}
	/**
	 * @return the fly_site
	 */
	public int getFly_site() {
		return fly_site;
	}
	/**
	 * @param fly_site the fly_site to set
	 */
	public void setFly_site(int fly_site) {
		this.fly_site = fly_site;
	}
	
	/**
	 * @return the roleId
	 */
	public int getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DailyParametersQuery [dayTime=" + dayTime + ", fly_site="
				+ fly_site + ", roleId=" + roleId + "]";
	}
	
}