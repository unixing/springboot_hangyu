package org.ldd.ssm.crm.domain;

public class CityID {
	private String city;
	private String number;
	private String city_x;
	private String city_y;
	private String county;

	public String getCity_y() {
		return city_y;
	}

	public void setCity_y(String city_y) {
		this.city_y = city_y;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity_x() {
		return city_x;
	}

	public void setCity_x(String city_x) {
		this.city_x = city_x;
	}

	/**
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}

	/**
	 * @param county the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CityID [city=" + city + ", number=" + number + ", city_x="
				+ city_x + ", city_y=" + city_y + ", county=" + county + "]";
	}


}
