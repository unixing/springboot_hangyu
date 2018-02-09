package org.ldd.ssm.crm.domain;


public class DailyParameters{	
	private String id ;//id
	private String start_time;
	private String end_time;
	private String fly_site_min;
	private String fly_site;
	private String hour_price;
	private String agence_price;
	private String role_id;
	private String sitee;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the start_time
	 */
	public String getStart_time() {
		return start_time;
	}
	/**
	 * @param start_time the start_time to set
	 */
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	/**
	 * @return the end_time
	 */
	public String getEnd_time() {
		return end_time;
	}
	/**
	 * @param end_time the end_time to set
	 */
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	/**
	 * @return the fly_site_min
	 */
	public String getFly_site_min() {
		return fly_site_min;
	}
	/**
	 * @param fly_site_min the fly_site_min to set
	 */
	public void setFly_site_min(String fly_site_min) {
		this.fly_site_min = fly_site_min;
	}
	/**
	 * @return the fly_site
	 */
	public String getFly_site() {
		return fly_site;
	}
	/**
	 * @param fly_site the fly_site to set
	 */
	public void setFly_site(String fly_site) {
		this.fly_site = fly_site;
	}
	/**
	 * @return the hour_price
	 */
	public String getHour_price() {
		return hour_price;
	}
	/**
	 * @param hour_price the hour_price to set
	 */
	public void setHour_price(String hour_price) {
		this.hour_price = hour_price;
	}
	/**
	 * @return the agence_price
	 */
	public String getAgence_price() {
		return agence_price;
	}
	/**
	 * @param agence_price the agence_price to set
	 */
	public void setAgence_price(String agence_price) {
		this.agence_price = agence_price;
	}
	/**
	 * @return the role_id
	 */
	public String getRole_id() {
		return role_id;
	}
	/**
	 * @param role_id the role_id to set
	 */
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	/**
	 * @return the sitee
	 */
	public String getSitee() {
		return sitee;
	}
	/**
	 * @param sitee the sitee to set
	 */
	public void setSitee(String sitee) {
		this.sitee = sitee;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DailyParameters [id=" + id + ", start_time=" + start_time
				+ ", end_time=" + end_time + ", fly_site_min=" + fly_site_min
				+ ", fly_site=" + fly_site + ", hour_price=" + hour_price
				+ ", agence_price=" + agence_price + ", role_id=" + role_id
				+ ", sitee=" + sitee + "]";
	}
	
	
}
