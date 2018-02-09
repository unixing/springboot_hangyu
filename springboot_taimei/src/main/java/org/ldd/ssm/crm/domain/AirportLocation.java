package org.ldd.ssm.crm.domain;
/**
 * 机场位置
 * @author wxm
 *
 * @date 2017-3-30
 */
public class AirportLocation{
	private String airlnCd;//机场名字
	private String cityCoordinateJ;//经度
	private String cityCoordinateW;//纬度
	private String iATA;//   机场三字码
	public String getAirlnCd() {
		return airlnCd;
	}
	public void setAirlnCd(String airlnCd) {
		this.airlnCd = airlnCd;
	}
	public String getCityCoordinateJ() {
		return cityCoordinateJ;
	}
	public void setCityCoordinateJ(String cityCoordinateJ) {
		this.cityCoordinateJ = cityCoordinateJ;
	}
	public String getCityCoordinateW() {
		return cityCoordinateW;
	}
	public void setCityCoordinateW(String cityCoordinateW) {
		this.cityCoordinateW = cityCoordinateW;
	}
	/**
	 * @return the iATA
	 */
	public String getiATA() {
		return iATA;
	}
	/**
	 * @param iATA the iATA to set
	 */
	public void setiATA(String iATA) {
		this.iATA = iATA;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AirportLocation [airlnCd=" + airlnCd + ", cityCoordinateJ="
				+ cityCoordinateJ + ", cityCoordinateW=" + cityCoordinateW
				+ ", iATA=" + iATA + "]";
	}
	
	
}
