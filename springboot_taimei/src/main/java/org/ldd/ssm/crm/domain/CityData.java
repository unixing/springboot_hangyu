package org.ldd.ssm.crm.domain;

/**
 * 城市JSON实体类
 * @Title:CityData 
 * @Description:
 * @author taimei-gds 
 * @date 2017-3-29 上午9:54:08
 */
public class CityData {
	private String name;//名字
	private String code;//代码
	private String py;//拼音
	private String initial;//大写首字母
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CityData [name=" + name + ", code=" + code + ", py=" + py
				+ ", initial=" + initial + "]";
	}
	
}
