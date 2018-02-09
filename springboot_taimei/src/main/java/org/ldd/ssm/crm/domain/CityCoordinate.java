package org.ldd.ssm.crm.domain;


public class CityCoordinate {
	
	private int id;
	private String cityName;//城市名
	private String cityCoordinatee;//城市坐标
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the cityCoordinatee
	 */
	public String getCityCoordinatee() {
		return cityCoordinatee;
	}
	/**
	 * @param cityCoordinatee the cityCoordinatee to set
	 */
	public void setCityCoordinatee(String cityCoordinatee) {
		this.cityCoordinatee = cityCoordinatee;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CityCoordinate [id=" + id + ", cityName=" + cityName
				+ ", cityCoordinatee=" + cityCoordinatee + "]";
	}
	
	
}
