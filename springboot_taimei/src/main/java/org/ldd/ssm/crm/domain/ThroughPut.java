package org.ldd.ssm.crm.domain;

/**
 * 机场各个指标排名实体类
 * @Title:ThroughPut 
 * @Description:
 * @author taimei-gds 
 * @date 2017-3-1 下午4:20:09
 */
public class ThroughPut{
	private String iata;	//三字码
	private String this_year;//今年（当前年的上一年）
	private String last_year;//去年
	private String this_goods;//今年货物数量
	private String last_goods;//去年货物数量
	private String goods_ranking;//货物排名
	private String goods_compare;//货物增量
	
	private String this_passenger;//今年乘客
	private String last_passenger;//去年乘客
	private String passenger_ranking;//乘客排名
	private String passenger_compare;//乘客增量
	
	private String this_flights;//今年航班
	private String last_flights;//去年航班
	private String flights_ranking;//航班排名
	private String flights_compare;//航班增量
	/**
	 * @return the iata
	 */
	public String getIata() {
		return iata;
	}
	/**
	 * @param iata the iata to set
	 */
	public void setIata(String iata) {
		this.iata = iata;
	}
	/**
	 * @return the this_year
	 */
	public String getThis_year() {
		return this_year;
	}
	/**
	 * @param this_year the this_year to set
	 */
	public void setThis_year(String this_year) {
		this.this_year = this_year;
	}
	/**
	 * @return the last_year
	 */
	public String getLast_year() {
		return last_year;
	}
	/**
	 * @param last_year the last_year to set
	 */
	public void setLast_year(String last_year) {
		this.last_year = last_year;
	}
	/**
	 * @return the this_goods
	 */
	public String getThis_goods() {
		return this_goods;
	}
	/**
	 * @param this_goods the this_goods to set
	 */
	public void setThis_goods(String this_goods) {
		this.this_goods = this_goods;
	}
	/**
	 * @return the last_goods
	 */
	public String getLast_goods() {
		return last_goods;
	}
	/**
	 * @param last_goods the last_goods to set
	 */
	public void setLast_goods(String last_goods) {
		this.last_goods = last_goods;
	}
	/**
	 * @return the goods_ranking
	 */
	public String getGoods_ranking() {
		return goods_ranking;
	}
	/**
	 * @param goods_ranking the goods_ranking to set
	 */
	public void setGoods_ranking(String goods_ranking) {
		this.goods_ranking = goods_ranking;
	}
	/**
	 * @return the goods_compare
	 */
	public String getGoods_compare() {
		return goods_compare;
	}
	/**
	 * @param goods_compare the goods_compare to set
	 */
	public void setGoods_compare(String goods_compare) {
		this.goods_compare = goods_compare;
	}
	/**
	 * @return the this_passenger
	 */
	public String getThis_passenger() {
		return this_passenger;
	}
	/**
	 * @param this_passenger the this_passenger to set
	 */
	public void setThis_passenger(String this_passenger) {
		this.this_passenger = this_passenger;
	}
	/**
	 * @return the last_passenger
	 */
	public String getLast_passenger() {
		return last_passenger;
	}
	/**
	 * @param last_passenger the last_passenger to set
	 */
	public void setLast_passenger(String last_passenger) {
		this.last_passenger = last_passenger;
	}
	/**
	 * @return the passenger_ranking
	 */
	public String getPassenger_ranking() {
		return passenger_ranking;
	}
	/**
	 * @param passenger_ranking the passenger_ranking to set
	 */
	public void setPassenger_ranking(String passenger_ranking) {
		this.passenger_ranking = passenger_ranking;
	}
	/**
	 * @return the passenger_compare
	 */
	public String getPassenger_compare() {
		return passenger_compare;
	}
	/**
	 * @param passenger_compare the passenger_compare to set
	 */
	public void setPassenger_compare(String passenger_compare) {
		this.passenger_compare = passenger_compare;
	}
	/**
	 * @return the this_flights
	 */
	public String getThis_flights() {
		return this_flights;
	}
	/**
	 * @param this_flights the this_flights to set
	 */
	public void setThis_flights(String this_flights) {
		this.this_flights = this_flights;
	}
	/**
	 * @return the last_flights
	 */
	public String getLast_flights() {
		return last_flights;
	}
	/**
	 * @param last_flights the last_flights to set
	 */
	public void setLast_flights(String last_flights) {
		this.last_flights = last_flights;
	}
	/**
	 * @return the flights_ranking
	 */
	public String getFlights_ranking() {
		return flights_ranking;
	}
	/**
	 * @param flights_ranking the flights_ranking to set
	 */
	public void setFlights_ranking(String flights_ranking) {
		this.flights_ranking = flights_ranking;
	}
	/**
	 * @return the flights_compare
	 */
	public String getFlights_compare() {
		return flights_compare;
	}
	/**
	 * @param flights_compare the flights_compare to set
	 */
	public void setFlights_compare(String flights_compare) {
		this.flights_compare = flights_compare;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ThroughPut [iata=" + iata + ", this_year=" + this_year
				+ ", last_year=" + last_year + ", this_goods=" + this_goods
				+ ", last_goods=" + last_goods + ", goods_ranking="
				+ goods_ranking + ", goods_compare=" + goods_compare
				+ ", this_passenger=" + this_passenger + ", last_passenger="
				+ last_passenger + ", passenger_ranking=" + passenger_ranking
				+ ", passenger_compare=" + passenger_compare
				+ ", this_flights=" + this_flights + ", last_flights="
				+ last_flights + ", flights_ranking=" + flights_ranking
				+ ", flights_compare=" + flights_compare + "]";
	}
	
}
