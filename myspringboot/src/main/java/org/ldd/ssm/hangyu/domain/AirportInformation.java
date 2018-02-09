package org.ldd.ssm.hangyu.domain;

import java.util.List;

public class AirportInformation {
	private AirportLocationAndManagementInformation airportLocation;//机场地理位置和管理资料
	private ApronTaxiwayAndCalibrationLocationData apronTaxiway;//停机坪、滑行道及校正位置数据
	private AvailableSnowSeason available;//可用季节-扫雪
	private GroundServicesAndFacilities groundServices;//地勤服务和设施
	private GuilanceAndControlSystemAndIdentifyTheGroundActivities guilance;//地面活动引导和管制系统
	private HelicopterLandingArea helicopter;//直升机着陆区域
	private OtherLightsBackupPowerSupply other;//其它灯光,备份电源
	private ProvideWeatherInformation provide;//提供的气象情报
	private RescueAndFireService rescue;//援救与消防服务
	
	private List<Barrier> barriers;//障碍物
	private List<WeatherInfo> weatherInfos;//天气信息
	private List<AirPortWeatherInfo> airPortWeatherInfos;//机场天气观测和报告
	private List<PhysicalCharacteristicsOfRunway> physical;//跑道物理特征
	private PhysicalCharacteristicsOfRunwayRek runwayRek;//跑道物理特征备注
	private List<LengthIntensity> intensity;//进近和跑道灯光
	private LengthIntensityRek intensityRek;//进近和跑道灯光备注
	private List<AirTrafficServiceAirspace> airspace;//空中交通服务空域
	private List<AirTrafficServiceCommunicationFacilities> airTraffic;//空中交通服务通信设施
	private List<RadioNavigationAndLandingFacilities> radio;//无线电导航和着陆设施
	public AirportLocationAndManagementInformation getAirportLocation() {
		return airportLocation;
	}
	public void setAirportLocation(
			AirportLocationAndManagementInformation airportLocation) {
		this.airportLocation = airportLocation;
	}
	public ApronTaxiwayAndCalibrationLocationData getApronTaxiway() {
		return apronTaxiway;
	}
	public void setApronTaxiway(ApronTaxiwayAndCalibrationLocationData apronTaxiway) {
		this.apronTaxiway = apronTaxiway;
	}
	public AvailableSnowSeason getAvailable() {
		return available;
	}
	public void setAvailable(AvailableSnowSeason available) {
		this.available = available;
	}
	public GroundServicesAndFacilities getGroundServices() {
		return groundServices;
	}
	public void setGroundServices(GroundServicesAndFacilities groundServices) {
		this.groundServices = groundServices;
	}
	public GuilanceAndControlSystemAndIdentifyTheGroundActivities getGuilance() {
		return guilance;
	}
	public void setGuilance(
			GuilanceAndControlSystemAndIdentifyTheGroundActivities guilance) {
		this.guilance = guilance;
	}
	public HelicopterLandingArea getHelicopter() {
		return helicopter;
	}
	public void setHelicopter(HelicopterLandingArea helicopter) {
		this.helicopter = helicopter;
	}
	public OtherLightsBackupPowerSupply getOther() {
		return other;
	}
	public void setOther(OtherLightsBackupPowerSupply other) {
		this.other = other;
	}
	public ProvideWeatherInformation getProvide() {
		return provide;
	}
	public void setProvide(ProvideWeatherInformation provide) {
		this.provide = provide;
	}
	public RescueAndFireService getRescue() {
		return rescue;
	}
	public void setRescue(RescueAndFireService rescue) {
		this.rescue = rescue;
	}
	public List<Barrier> getBarriers() {
		return barriers;
	}
	public void setBarriers(List<Barrier> barriers) {
		this.barriers = barriers;
	}
	public List<WeatherInfo> getWeatherInfos() {
		return weatherInfos;
	}
	public void setWeatherInfos(List<WeatherInfo> weatherInfos) {
		this.weatherInfos = weatherInfos;
	}
	public List<AirPortWeatherInfo> getAirPortWeatherInfos() {
		return airPortWeatherInfos;
	}
	public void setAirPortWeatherInfos(List<AirPortWeatherInfo> airPortWeatherInfos) {
		this.airPortWeatherInfos = airPortWeatherInfos;
	}
	public List<PhysicalCharacteristicsOfRunway> getPhysical() {
		return physical;
	}
	public void setPhysical(List<PhysicalCharacteristicsOfRunway> physical) {
		this.physical = physical;
	}
	public List<AirTrafficServiceAirspace> getAirspace() {
		return airspace;
	}
	public void setAirspace(List<AirTrafficServiceAirspace> airspace) {
		this.airspace = airspace;
	}
	public List<AirTrafficServiceCommunicationFacilities> getAirTraffic() {
		return airTraffic;
	}
	public void setAirTraffic(
			List<AirTrafficServiceCommunicationFacilities> airTraffic) {
		this.airTraffic = airTraffic;
	}
	public List<RadioNavigationAndLandingFacilities> getRadio() {
		return radio;
	}
	public void setRadio(List<RadioNavigationAndLandingFacilities> radio) {
		this.radio = radio;
	}
	
	public PhysicalCharacteristicsOfRunwayRek getRunwayRek() {
		return runwayRek;
	}
	public void setRunwayRek(PhysicalCharacteristicsOfRunwayRek runwayRek) {
		this.runwayRek = runwayRek;
	}
	public List<LengthIntensity> getIntensity() {
		return intensity;
	}
	public void setIntensity(List<LengthIntensity> intensity) {
		this.intensity = intensity;
	}
	public LengthIntensityRek getIntensityRek() {
		return intensityRek;
	}
	public void setIntensityRek(LengthIntensityRek intensityRek) {
		this.intensityRek = intensityRek;
	}
	@Override
	public String toString() {
		return "AirportInformation [airportLocation=" + airportLocation
				+ ", apronTaxiway=" + apronTaxiway + ", available=" + available
				+ ", groundServices=" + groundServices + ", guilance="
				+ guilance + ", helicopter=" + helicopter + ", other=" + other
				+ ", provide=" + provide + ", rescue=" + rescue + ", barriers="
				+ barriers + ", weatherInfos=" + weatherInfos
				+ ", airPortWeatherInfos=" + airPortWeatherInfos
				+ ", physical=" + physical + ", runwayRek=" + runwayRek
				+ ", intensity=" + intensity + ", intensityRek=" + intensityRek
				+ ", airspace=" + airspace + ", airTraffic=" + airTraffic
				+ ", radio=" + radio + "]";
	}
}
