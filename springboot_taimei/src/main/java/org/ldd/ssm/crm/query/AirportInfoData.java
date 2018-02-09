package org.ldd.ssm.crm.query;

import java.util.List;

import org.ldd.ssm.crm.domain.Flowofsubsidiary;
import org.ldd.ssm.crm.domain.Runwaythedetail;

//机场对比信息    成本测算中的机场信息
public class AirportInfoData {
	private Long id;
	
	private Long cityId;
	//机场名字
	private String airPortName;
	//三字码
	private String iata;
	//所属机场
	private String membershipGroup;
	//四字码
	private String icao;
	//是否国际
	private String inter;
	//通航时间
	private String departureTime;
	//城市
	private String city;
	//飞行区等级
	private String airfieldLvl;
	//机场专线
	private String airportShuttleMetro;
	//区域管理局
	private String areaManager;
	//省份
	private String province;
	//机场巴士
	private String airportBus;
	//战区
	private String warZone;
	//机场类别
	private String airportType;
	//距离市区距离
	private String distanceFromDowntown;
	//标高
	private String airEle;
	//口岸
	private String port;
	//国际航点
	private String international;
	private String specialAirport;//特殊机场
	private String airpotCls; //机场类型
	private String internationalTime;//国际航点录入时间
	private String releasePunctuality;//放行准点率
	private String specialAirportWhy;//特殊机场构成原因
	private String domestic;//国内航点
	private String lightingConditions;//灯光条件
	private String fireLvl;//消防等级
	private String inTheFlight;//国内在飞航班
	private String modelCanHandle;//可起降机型
	private String allowTheTakeoffAndLanding;//允许起降时间
	private String inTheFlightTime;//在飞航班和国内航点统计时间
	private String planePositionNumber;//机位数量/

	//地区 省份+城市
	private String area;
	//跑道
	private String runwayArticleNumber;
	//旅客吞吐量
	private String personThroughput;
	//货邮吞吐量
	private String goodsThroughput;
	
	private String gdp;
	//联系电话
	private String phone;
	//城市人口最近有数据
	private String cityPgeNumber;
	//旅游资源
	private String touristResources;
	//基地航司运力
	private String baseNavigationDep;
	//机场吞吐量 旅客
	private List<ThroughputOrGdp> throughputs;
	//城市GDP
	private List<ThroughputOrGdp> gdps;
	
	private List<Runwaythedetail> runwaythedetailList; 
	
	private List<Flowofsubsidiary> flowofsubsidiaryList;  
	
	public String getInTheFlight() {
		return inTheFlight;
	}
	public void setInTheFlight(String inTheFlight) {
		this.inTheFlight = inTheFlight;
	}
	public String getMembershipGroup() {
		return membershipGroup;
	}
	public void setMembershipGroup(String membershipGroup) {
		this.membershipGroup = membershipGroup;
	}
	public String getIcao() {
		return icao;
	}
	public void setIcao(String icao) {
		this.icao = icao;
	}
	public String getInter() {
		return inter;
	}
	public void setInter(String inter) {
		this.inter = inter;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getAirportShuttleMetro() {
		return airportShuttleMetro;
	}
	public void setAirportShuttleMetro(String airportShuttleMetro) {
		this.airportShuttleMetro = airportShuttleMetro;
	}
	public String getAreaManager() {
		return areaManager;
	}
	public void setAreaManager(String areaManager) {
		this.areaManager = areaManager;
	}
	public String getAirportBus() {
		return airportBus;
	}
	public void setAirportBus(String airportBus) {
		this.airportBus = airportBus;
	}
	public String getWarZone() {
		return warZone;
	}
	public void setWarZone(String warZone) {
		this.warZone = warZone;
	}
	public String getDistanceFromDowntown() {
		return distanceFromDowntown;
	}
	public void setDistanceFromDowntown(String distanceFromDowntown) {
		this.distanceFromDowntown = distanceFromDowntown;
	}
	public String getAirEle() {
		return airEle;
	}
	public void setAirEle(String airEle) {
		this.airEle = airEle;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getInternational() {
		return international;
	}
	public void setInternational(String international) {
		this.international = international;
	}
	public String getSpecialAirport() {
		return specialAirport;
	}
	public void setSpecialAirport(String specialAirport) {
		this.specialAirport = specialAirport;
	}
	public String getAirpotCls() {
		return airpotCls;
	}
	public void setAirpotCls(String airpotCls) {
		this.airpotCls = airpotCls;
	}
	public String getInternationalTime() {
		return internationalTime;
	}
	public void setInternationalTime(String internationalTime) {
		this.internationalTime = internationalTime;
	}
	public String getReleasePunctuality() {
		return releasePunctuality;
	}
	public void setReleasePunctuality(String releasePunctuality) {
		this.releasePunctuality = releasePunctuality;
	}
	public String getSpecialAirportWhy() {
		return specialAirportWhy;
	}
	public void setSpecialAirportWhy(String specialAirportWhy) {
		this.specialAirportWhy = specialAirportWhy;
	}
	public String getDomestic() {
		return domestic;
	}
	public void setDomestic(String domestic) {
		this.domestic = domestic;
	}
	public String getLightingConditions() {
		return lightingConditions;
	}
	public void setLightingConditions(String lightingConditions) {
		this.lightingConditions = lightingConditions;
	}
	public String getFireLvl() {
		return fireLvl;
	}
	public void setFireLvl(String fireLvl) {
		this.fireLvl = fireLvl;
	}
	
	public String getModelCanHandle() {
		return modelCanHandle;
	}
	public void setModelCanHandle(String modelCanHandle) {
		this.modelCanHandle = modelCanHandle;
	}
	public String getAllowTheTakeoffAndLanding() {
		return allowTheTakeoffAndLanding;
	}
	public void setAllowTheTakeoffAndLanding(String allowTheTakeoffAndLanding) {
		this.allowTheTakeoffAndLanding = allowTheTakeoffAndLanding;
	}
	public String getInTheFlightTime() {
		return inTheFlightTime;
	}
	public void setInTheFlightTime(String inTheFlightTime) {
		this.inTheFlightTime = inTheFlightTime;
	}
	public String getPlanePositionNumber() {
		return planePositionNumber;
	}
	public void setPlanePositionNumber(String planePositionNumber) {
		this.planePositionNumber = planePositionNumber;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getIata() {
		return iata;
	}
	public void setIata(String iata) {
		this.iata = iata;
	}
	public String getGdp() {
		return gdp;
	}
	public void setGdp(String gdp) {
		this.gdp = gdp;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	public String getAirPortName() {
		return airPortName;
	}
	public void setAirPortName(String airPortName) {
		this.airPortName = airPortName;
	}
	public String getAirfieldLvl() {
		return airfieldLvl;
	}
	public void setAirfieldLvl(String airfieldLvl) {
		this.airfieldLvl = airfieldLvl;
	}
	public String getAirportType() {
		return airportType;
	}
	public void setAirportType(String airportType) {
		this.airportType = airportType;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getRunwayArticleNumber() {
		return runwayArticleNumber;
	}
	public void setRunwayArticleNumber(String runwayArticleNumber) {
		this.runwayArticleNumber = runwayArticleNumber;
	}
	public String getPersonThroughput() {
		return personThroughput;
	}
	public void setPersonThroughput(String personThroughput) {
		this.personThroughput = personThroughput;
	}
	public String getGoodsThroughput() {
		return goodsThroughput;
	}
	public void setGoodsThroughput(String goodsThroughput) {
		this.goodsThroughput = goodsThroughput;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCityPgeNumber() {
		return cityPgeNumber;
	}
	public void setCityPgeNumber(String cityPgeNumber) {
		this.cityPgeNumber = cityPgeNumber;
	}
	public String getTouristResources() {
		return touristResources;
	}
	public void setTouristResources(String touristResources) {
		this.touristResources = touristResources;
	}
	
	public String getBaseNavigationDep() {
		return baseNavigationDep;
	}
	public void setBaseNavigationDep(String baseNavigationDep) {
		this.baseNavigationDep = baseNavigationDep;
	}
	public List<ThroughputOrGdp> getThroughputs() {
		return throughputs;
	}
	public void setThroughputs(List<ThroughputOrGdp> throughputs) {
		this.throughputs = throughputs;
	}
	public List<ThroughputOrGdp> getGdps() {
		return gdps;
	}
	public void setGdps(List<ThroughputOrGdp> gdps) {
		this.gdps = gdps;
	}
	public List<Runwaythedetail> getRunwaythedetailList() {
		return runwaythedetailList;
	}
	public void setRunwaythedetailList(List<Runwaythedetail> runwaythedetailList) {
		this.runwaythedetailList = runwaythedetailList;
	}
	public List<Flowofsubsidiary> getFlowofsubsidiaryList() {
		return flowofsubsidiaryList;
	}
	public void setFlowofsubsidiaryList(List<Flowofsubsidiary> flowofsubsidiaryList) {
		this.flowofsubsidiaryList = flowofsubsidiaryList;
	}
}
