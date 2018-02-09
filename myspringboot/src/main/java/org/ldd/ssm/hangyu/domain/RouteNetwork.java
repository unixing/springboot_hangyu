package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class RouteNetwork implements Serializable {

	private String dptName;
	private String dptIata;
	private String dptLongitude;
	private String dptLatitude;
	private String arrvName;
	private String arrvIata;
	private String arrvLongitude;//经度
	private String arrvLatitude;//纬度
	public String getDptName() {
		return dptName;
	}
	public void setDptName(String dptName) {
		this.dptName = dptName;
	}
	public String getDptLongitude() {
		return dptLongitude;
	}
	public void setDptLongitude(String dptLongitude) {
		this.dptLongitude = dptLongitude;
	}
	public String getDptLatitude() {
		return dptLatitude;
	}
	public void setDptLatitude(String dptLatitude) {
		this.dptLatitude = dptLatitude;
	}
	public String getArrvName() {
		return arrvName;
	}
	public void setArrvName(String arrvName) {
		this.arrvName = arrvName;
	}
	public String getArrvLongitude() {
		return arrvLongitude;
	}
	public void setArrvLongitude(String arrvLongitude) {
		this.arrvLongitude = arrvLongitude;
	}
	public String getArrvLatitude() {
		return arrvLatitude;
	}
	public void setArrvLatitude(String arrvLatitude) {
		this.arrvLatitude = arrvLatitude;
	}
	public String getDptIata() {
		return dptIata;
	}
	public void setDptIata(String dptIata) {
		this.dptIata = dptIata;
	}
	public String getArrvIata() {
		return arrvIata;
	}
	public void setArrvIata(String arrvIata) {
		this.arrvIata = arrvIata;
	}
	@Override
	public String toString() {
		return "RouteNetwork [dptName=" + dptName + ", dptIata=" + dptIata
				+ ", dptLongitude=" + dptLongitude + ", dptLatitude="
				+ dptLatitude + ", arrvName=" + arrvName + ", arrvIata="
				+ arrvIata + ", arrvLongitude=" + arrvLongitude
				+ ", arrvLatitude=" + arrvLatitude + "]";
	}
}
