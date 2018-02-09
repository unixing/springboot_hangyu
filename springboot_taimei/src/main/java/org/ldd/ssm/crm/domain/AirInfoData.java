package org.ldd.ssm.crm.domain;

/**
 * 机场视角的机场详细信息
 * @Title:AirInfoData 
 * @Description:
 * @author taimei-gds 
 * @date 2016-12-13 上午9:54:48
 */
public class AirInfoData {
	private String iata;//三字码
	private String airInfoName;//机场名字
	private String icao;//国际民用航空组织
	private String flyLevel;//飞行区等级
	private String modelCanHandle;//可起降机型 wxm 添加 用于首页数据比对
	private String pdNum;//跑道数量
	private String onlineFlyNum;//在飞航线
	private String airportBG;// 机场标高
	private String glFlyNum;//国内航点
	private String gjFlyNum;//国际航点
	private String flyPerson;//吞吐量
	private String lkjzDate;//截止日期
	private String hxjzDate;//在飞航线截止日期
	private String city_coordinate_j;//经度
	private String  city_coordinate_w;//纬度
	private String city;//机场对应的城市
	private String isRewardPolicy;//是否有补贴
	private String airpotCls;//机场类型
	private String port;
	private String specialAirport;
	private String coordinateAirport;
	private String glhdDate;//国内航点截止日期
	private String gjhdDate;//国际航点截止日期
	
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getSpecialAirport() {
		return specialAirport;
	}
	public void setSpecialAirport(String specialAirport) {
		this.specialAirport = specialAirport;
	}
	public String getIsRewardPolicy() {
		return isRewardPolicy;
	}
	public void setIsRewardPolicy(String isRewardPolicy) {
		this.isRewardPolicy = isRewardPolicy;
	}
	public String getAirpotCls() {
		return airpotCls;
	}
	public void setAirpotCls(String airpotCls) {
		this.airpotCls = airpotCls;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getModelCanHandle() {
		return modelCanHandle;
	}
	public void setModelCanHandle(String modelCanHandle) {
		this.modelCanHandle = modelCanHandle;
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
	 * @return the airInfoName
	 */
	public String getAirInfoName() {
		return airInfoName;
	}
	/**
	 * @param airInfoName the airInfoName to set
	 */
	public void setAirInfoName(String airInfoName) {
		this.airInfoName = airInfoName;
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
	 * @return the flyLevel
	 */
	public String getFlyLevel() {
		return flyLevel;
	}
	/**
	 * @param flyLevel the flyLevel to set
	 */
	public void setFlyLevel(String flyLevel) {
		this.flyLevel = flyLevel;
	}
	/**
	 * @return the pdNum
	 */
	public String getPdNum() {
		return pdNum;
	}
	/**
	 * @param pdNum the pdNum to set
	 */
	public void setPdNum(String pdNum) {
		this.pdNum = pdNum;
	}
	/**
	 * @return the onlineFlyNum
	 */
	public String getOnlineFlyNum() {
		return onlineFlyNum;
	}
	/**
	 * @param onlineFlyNum the onlineFlyNum to set
	 */
	public void setOnlineFlyNum(String onlineFlyNum) {
		this.onlineFlyNum = onlineFlyNum;
	}
	/**
	 * @return the airportBG
	 */
	public String getAirportBG() {
		return airportBG;
	}
	/**
	 * @param airportBG the airportBG to set
	 */
	public void setAirportBG(String airportBG) {
		this.airportBG = airportBG;
	}
	/**
	 * @return the glFlyNum
	 */
	public String getGlFlyNum() {
		return glFlyNum;
	}
	/**
	 * @param glFlyNum the glFlyNum to set
	 */
	public void setGlFlyNum(String glFlyNum) {
		this.glFlyNum = glFlyNum;
	}
	/**
	 * @return the gjFlyNum
	 */
	public String getGjFlyNum() {
		return gjFlyNum;
	}
	/**
	 * @param gjFlyNum the gjFlyNum to set
	 */
	public void setGjFlyNum(String gjFlyNum) {
		this.gjFlyNum = gjFlyNum;
	}
	/**
	 * @return the flyPerson
	 */
	public String getFlyPerson() {
		return flyPerson;
	}
	/**
	 * @param flyPerson the flyPerson to set
	 */
	public void setFlyPerson(String flyPerson) {
		this.flyPerson = flyPerson;
	}
	/**
	 * @return the lkjzDate
	 */
	public String getLkjzDate() {
		return lkjzDate;
	}
	/**
	 * @param lkjzDate the lkjzDate to set
	 */
	public void setLkjzDate(String lkjzDate) {
		this.lkjzDate = lkjzDate;
	}
	/**
	 * @return the hxjzDate
	 */
	public String getHxjzDate() {
		return hxjzDate;
	}
	/**
	 * @param hxjzDate the hxjzDate to set
	 */
	public void setHxjzDate(String hxjzDate) {
		this.hxjzDate = hxjzDate;
	}
	/**
	 * @return the city_coordinate_j
	 */
	public String getCity_coordinate_j() {
		return city_coordinate_j;
	}
	/**
	 * @param city_coordinate_j the city_coordinate_j to set
	 */
	public void setCity_coordinate_j(String city_coordinate_j) {
		this.city_coordinate_j = city_coordinate_j;
	}
	/**
	 * @return the city_coordinate_w
	 */
	public String getCity_coordinate_w() {
		return city_coordinate_w;
	}
	/**
	 * @param city_coordinate_w the city_coordinate_w to set
	 */
	public void setCity_coordinate_w(String city_coordinate_w) {
		this.city_coordinate_w = city_coordinate_w;
	}
	/**
	 * @return the coordinateAirport
	 */
	public String getCoordinateAirport() {
		return coordinateAirport;
	}
	/**
	 * @param coordinateAirport the coordinateAirport to set
	 */
	public void setCoordinateAirport(String coordinateAirport) {
		this.coordinateAirport = coordinateAirport;
	}
	/**
	 * @return the glhdDate
	 */
	public String getGlhdDate() {
		return glhdDate;
	}
	/**
	 * @param glhdDate the glhdDate to set
	 */
	public void setGlhdDate(String glhdDate) {
		this.glhdDate = glhdDate;
	}
	/**
	 * @return the gjhdDate
	 */
	public String getGjhdDate() {
		return gjhdDate;
	}
	/**
	 * @param gjhdDate the gjhdDate to set
	 */
	public void setGjhdDate(String gjhdDate) {
		this.gjhdDate = gjhdDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AirInfoData [iata=" + iata + ", airInfoName=" + airInfoName
				+ ", icao=" + icao + ", flyLevel=" + flyLevel
				+ ", modelCanHandle=" + modelCanHandle + ", pdNum=" + pdNum
				+ ", onlineFlyNum=" + onlineFlyNum + ", airportBG=" + airportBG
				+ ", glFlyNum=" + glFlyNum + ", gjFlyNum=" + gjFlyNum
				+ ", flyPerson=" + flyPerson + ", lkjzDate=" + lkjzDate
				+ ", hxjzDate=" + hxjzDate + ", city_coordinate_j="
				+ city_coordinate_j + ", city_coordinate_w="
				+ city_coordinate_w + ", city=" + city + ", isRewardPolicy="
				+ isRewardPolicy + ", airpotCls=" + airpotCls + ", port="
				+ port + ", specialAirport=" + specialAirport
				+ ", coordinateAirport=" + coordinateAirport + ", glhdDate="
				+ glhdDate + ", gjhdDate=" + gjhdDate + "]";
	}
	
	
}
