package org.ldd.ssm.crm.query;

import java.util.List;

import org.ldd.ssm.crm.domain.PlaneDetail;

/**
 * 航司信息
 * @author wxm
 *
 * @date 2017-8-11
 */
public class AirCompanyInfoQuery {

	private long id;
	private String airlnCd;//航司名称',
	private String iata;//航司二字码',
	private String icao;//航司三字码',
	private String establishTime;//成立时间',
	private String headquartersLocation;//总部地点',
	private String baseDistribution;//基地分布',
	private String shippingCountry;//通航国家',
	private String navigationAirport;//通航机场',
	private String systemAirpot;//所属航系',
	private String airlineAlliance;//航空联盟',
	private List<PlaneDetail> planeList;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getAirlnCd() {
		return airlnCd;
	}
	public void setAirlnCd(String airlnCd) {
		this.airlnCd = airlnCd;
	}
	public String getIata() {
		return iata;
	}
	public void setIata(String iata) {
		this.iata = iata;
	}
	public String getIcao() {
		return icao;
	}
	public void setIcao(String icao) {
		this.icao = icao;
	}
	public String getEstablishTime() {
		return establishTime;
	}
	public void setEstablishTime(String establishTime) {
		this.establishTime = establishTime;
	}
	public String getHeadquartersLocation() {
		return headquartersLocation;
	}
	public void setHeadquartersLocation(String headquartersLocation) {
		this.headquartersLocation = headquartersLocation;
	}
	public String getBaseDistribution() {
		return baseDistribution;
	}
	public void setBaseDistribution(String baseDistribution) {
		this.baseDistribution = baseDistribution;
	}
	public String getShippingCountry() {
		return shippingCountry;
	}
	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}
	public String getNavigationAirport() {
		return navigationAirport;
	}
	public void setNavigationAirport(String navigationAirport) {
		this.navigationAirport = navigationAirport;
	}
	public String getSystemAirpot() {
		return systemAirpot;
	}
	public void setSystemAirpot(String systemAirpot) {
		this.systemAirpot = systemAirpot;
	}
	public String getAirlineAlliance() {
		return airlineAlliance;
	}
	public void setAirlineAlliance(String airlineAlliance) {
		this.airlineAlliance = airlineAlliance;
	}
	public List<PlaneDetail> getPlaneList() {
		return planeList;
	}
	public void setPlaneList(List<PlaneDetail> planeList) {
		this.planeList = planeList;
	}
	
	
}
