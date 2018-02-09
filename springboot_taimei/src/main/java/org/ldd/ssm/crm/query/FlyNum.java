package org.ldd.ssm.crm.query;


public class FlyNum {
	private String date;
	private String flyNum;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FlyNum [date=" + date + ", flyNum=" + flyNum + "]";
	}
	
}
