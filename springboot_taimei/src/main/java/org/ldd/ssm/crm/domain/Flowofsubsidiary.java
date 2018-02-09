package org.ldd.ssm.crm.domain;
/**
 * 每年机场各种流量总计0714
 * @author wxm
 *
 * @date 2017-8-10
 */
public class Flowofsubsidiary {
	
	private long id;
	private String year;//年份',
	private String passengerThroughput;//旅客吞吐量',
	private String goodsThroughput;//货邮吞吐量',
	private String takeOffAndLandingFlights;//起降架次',
	private String airportinfonewthreeId;//主键id',
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPassengerThroughput() {
		return passengerThroughput;
	}
	public void setPassengerThroughput(String passengerThroughput) {
		this.passengerThroughput = passengerThroughput;
	}
	public String getGoodsThroughput() {
		return goodsThroughput;
	}
	public void setGoodsThroughput(String goodsThroughput) {
		this.goodsThroughput = goodsThroughput;
	}
	public String getTakeOffAndLandingFlights() {
		return takeOffAndLandingFlights;
	}
	public void setTakeOffAndLandingFlights(String takeOffAndLandingFlights) {
		this.takeOffAndLandingFlights = takeOffAndLandingFlights;
	}
	public String getAirportinfonewthreeId() {
		return airportinfonewthreeId;
	}
	public void setAirportinfonewthreeId(String airportinfonewthreeId) {
		this.airportinfonewthreeId = airportinfonewthreeId;
	}
	
}
