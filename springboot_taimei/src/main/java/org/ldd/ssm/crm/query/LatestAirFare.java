package org.ldd.ssm.crm.query;

public class LatestAirFare {

    private String source_airport_3code;

    private String destination_airport_3code;

    private String search_date;

    private String flight_no;

    private String lowest_price;

    private String avg_price;

    private String daily_time;
    
    private String changeRate;//变化率
    
	private String changePrice;//变化值
	
	private String start_date;
	
	private String code;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getAvg_price() {
		return avg_price;
	}

	public void setAvg_price(String avg_price) {
		this.avg_price = avg_price;
	}

	public String getSource_airport_3code() {
		return source_airport_3code;
	}

	public void setSource_airport_3code(String source_airport_3code) {
		this.source_airport_3code = source_airport_3code;
	}

	public String getDestination_airport_3code() {
		return destination_airport_3code;
	}

	public void setDestination_airport_3code(String destination_airport_3code) {
		this.destination_airport_3code = destination_airport_3code;
	}

	public String getSearch_date() {
		return search_date;
	}

	public void setSearch_date(String search_date) {
		this.search_date = search_date;
	}

	public String getFlight_no() {
		return flight_no;
	}

	public void setFlight_no(String flight_no) {
		this.flight_no = flight_no;
	}

	public String getLowest_price() {
		return lowest_price;
	}

	public void setLowest_price(String lowest_price) {
		this.lowest_price = lowest_price;
	}

	public String getDaily_time() {
		return daily_time;
	}

	public void setDaily_time(String daily_time) {
		this.daily_time = daily_time;
	}

	public String getChangeRate() {
		return changeRate;
	}

	public void setChangeRate(String changeRate) {
		this.changeRate = changeRate;
	}

	public String getChangePrice() {
		return changePrice;
	}

	public void setChangePrice(String changePrice) {
		this.changePrice = changePrice;
	}
    
}