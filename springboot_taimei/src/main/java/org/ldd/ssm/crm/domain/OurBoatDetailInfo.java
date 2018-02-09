package org.ldd.ssm.crm.domain;

/**
 * 航司详细信息实体类
 * @Title:OurBoatDetailInfo 
 * @Description:
 * @author taimei-gds 
 * @date 2017-2-28 上午11:03:31
 */
public class OurBoatDetailInfo {
	private String id;
	private String airln_cd;			// 航司名字
	private String iata;				// IATA代码
	private String icao;				// ICAO代码
	private String headquartersLocation;			// 总部地点
	private String establishTime;		// 成立时间
	private String baseDistribution;	// 基地分布
	private String shippingCountry;		// 通航国家
	private String navigationAirport;			// 通航机场
	private String systemAirpot;				// 所属航系
	private String airlineAlliance;				// 航空联盟
	public OurBoatDetailInfo() {
		super();
	}
	/**
	 * @param id
	 * @param airln_cd
	 * @param iata
	 * @param icao
	 * @param headquartersLocation
	 * @param establishTime
	 * @param baseDistribution
	 * @param shippingCountry
	 * @param navigationAirport
	 * @param systemAirpot
	 * @param airlineAlliance
	 */
	public OurBoatDetailInfo(String id, String airln_cd, String iata,
			String icao, String headquartersLocation, String establishTime,
			String baseDistribution, String shippingCountry,
			String navigationAirport, String systemAirpot,
			String airlineAlliance) {
		super();
		this.id = id;
		this.airln_cd = airln_cd;
		this.iata = iata;
		this.icao = icao;
		this.headquartersLocation = headquartersLocation;
		this.establishTime = establishTime;
		this.baseDistribution = baseDistribution;
		this.shippingCountry = shippingCountry;
		this.navigationAirport = navigationAirport;
		this.systemAirpot = systemAirpot;
		this.airlineAlliance = airlineAlliance;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the airln_cd
	 */
	public String getAirln_cd() {
		return airln_cd;
	}
	/**
	 * @param airln_cd the airln_cd to set
	 */
	public void setAirln_cd(String airln_cd) {
		this.airln_cd = airln_cd;
	}
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
	 * @return the icao
	 */
	public String getIcao() {
		return icao;
	}
	/**
	 * @param icao the icao to set
	 */
	public void setIcao(String icao) {
		this.icao = icao;
	}
	/**
	 * @return the headquartersLocation
	 */
	public String getHeadquartersLocation() {
		return headquartersLocation;
	}
	/**
	 * @param headquartersLocation the headquartersLocation to set
	 */
	public void setHeadquartersLocation(String headquartersLocation) {
		this.headquartersLocation = headquartersLocation;
	}
	/**
	 * @return the establishTime
	 */
	public String getEstablishTime() {
		return establishTime;
	}
	/**
	 * @param establishTime the establishTime to set
	 */
	public void setEstablishTime(String establishTime) {
		this.establishTime = establishTime;
	}
	/**
	 * @return the baseDistribution
	 */
	public String getBaseDistribution() {
		return baseDistribution;
	}
	/**
	 * @param baseDistribution the baseDistribution to set
	 */
	public void setBaseDistribution(String baseDistribution) {
		this.baseDistribution = baseDistribution;
	}
	/**
	 * @return the shippingCountry
	 */
	public String getShippingCountry() {
		return shippingCountry;
	}
	/**
	 * @param shippingCountry the shippingCountry to set
	 */
	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}
	/**
	 * @return the navigationAirport
	 */
	public String getNavigationAirport() {
		return navigationAirport;
	}
	/**
	 * @param navigationAirport the navigationAirport to set
	 */
	public void setNavigationAirport(String navigationAirport) {
		this.navigationAirport = navigationAirport;
	}
	/**
	 * @return the systemAirpot
	 */
	public String getSystemAirpot() {
		return systemAirpot;
	}
	/**
	 * @param systemAirpot the systemAirpot to set
	 */
	public void setSystemAirpot(String systemAirpot) {
		this.systemAirpot = systemAirpot;
	}
	/**
	 * @return the airlineAlliance
	 */
	public String getAirlineAlliance() {
		return airlineAlliance;
	}
	/**
	 * @param airlineAlliance the airlineAlliance to set
	 */
	public void setAirlineAlliance(String airlineAlliance) {
		this.airlineAlliance = airlineAlliance;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OurBoatDetailInfo [id=" + id + ", airln_cd=" + airln_cd
				+ ", iata=" + iata + ", icao=" + icao
				+ ", headquartersLocation=" + headquartersLocation
				+ ", establishTime=" + establishTime + ", baseDistribution="
				+ baseDistribution + ", shippingCountry=" + shippingCountry
				+ ", navigationAirport=" + navigationAirport
				+ ", systemAirpot=" + systemAirpot + ", airlineAlliance="
				+ airlineAlliance + "]";
	}

	
}
