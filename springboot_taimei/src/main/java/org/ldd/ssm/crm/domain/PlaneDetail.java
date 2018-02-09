package org.ldd.ssm.crm.domain;

public class PlaneDetail {
	  private long id;
	  private String airportType;//机型',
	  private String number;//数量',
	  private long aircompenyinfothree_id;//航司主键id',
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAirportType() {
		return airportType;
	}
	public void setAirportType(String airportType) {
		this.airportType = airportType;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public long getAircompenyinfothree_id() {
		return aircompenyinfothree_id;
	}
	public void setAircompenyinfothree_id(long aircompenyinfothree_id) {
		this.aircompenyinfothree_id = aircompenyinfothree_id;
	}
	  
}
