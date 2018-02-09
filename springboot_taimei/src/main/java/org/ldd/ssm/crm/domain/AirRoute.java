package org.ldd.ssm.crm.domain;

import java.util.List;

/**
 * 航线
 * @author wxm
 *
 * @date 2017-4-14
 */
public class AirRoute {
	private long id;//airlineCompany id
 	private long companyId;//集团id
	private String itia;//机场三字码
	private String state="2";//'0在飞航线,1历史航线,2无数据航线',
	private String airLine;//航线
	private String fltNbr="HU1111";//可以不用写无数据航线的接口 userDefined
	private List<String> airLineList;
	
	public String getFltNbr() {
		return fltNbr;
	}
	public void setFltNbr(String fltNbr) {
		this.fltNbr = fltNbr;
	}
	public List<String> getAirLineList() {
		return airLineList;
	}
	public void setAirLineList(List<String> airLineList) {
		this.airLineList = airLineList;
	}
	public String getAirLine() {
		return airLine;
	}
	public void setAirLine(String airLine) {
		this.airLine = airLine;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getItia() {
		return itia;
	}
	public void setItia(String itia) {
		this.itia = itia;
	}
	
}
