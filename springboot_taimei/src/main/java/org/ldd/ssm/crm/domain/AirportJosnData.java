package org.ldd.ssm.crm.domain;

/**
 * 机场JSON实体类
 * @Title:AirportJosnData 
 * @Description:
 * @author taimei-gds 
 * @date 2017-3-29 上午10:17:47
 */
public class AirportJosnData {
	private String airportName;//名字
	private String pinyin;//全拼
	private String py;//简拼
	private String code;//代码
	private String initial;//大写首字母
	private String city;//城市
	private String citycode;//城市代码
	/**
	 * @return the airportName
	 */
	public String getAirportName() {
		return airportName;
	}
	/**
	 * @param airportName the airportName to set
	 */
	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}
	/**
	 * @return the pinyin
	 */
	public String getPinyin() {
		return pinyin;
	}
	/**
	 * @param pinyin the pinyin to set
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	/**
	 * @return the py
	 */
	public String getPy() {
		return py;
	}
	/**
	 * @param py the py to set
	 */
	public void setPy(String py) {
		this.py = py;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the initial
	 */
	public String getInitial() {
		return initial;
	}
	/**
	 * @param initial the initial to set
	 */
	public void setInitial(String initial) {
		this.initial = initial;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the citycode
	 */
	public String getCitycode() {
		return citycode;
	}
	/**
	 * @param citycode the citycode to set
	 */
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AirportJosnData [airportName=" + airportName + ", pinyin="
				+ pinyin + ", py=" + py + ", code=" + code + ", initial="
				+ initial + ", city=" + city + ", citycode=" + citycode + "]";
	}
	
}
