package org.ldd.ssm.hangyu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.TextUtils;
import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.AirCompenyInfoThree;
import org.ldd.ssm.hangyu.domain.AirPortWeatherInfo;
import org.ldd.ssm.hangyu.domain.AirTrafficServiceAirspace;
import org.ldd.ssm.hangyu.domain.AirTrafficServiceCommunicationFacilities;
import org.ldd.ssm.hangyu.domain.AirportInfoNewThree;
import org.ldd.ssm.hangyu.domain.AirportInformation;
import org.ldd.ssm.hangyu.domain.AirportLocationAndManagementInformation;
import org.ldd.ssm.hangyu.domain.ApronTaxiwayAndCalibrationLocationData;
import org.ldd.ssm.hangyu.domain.AvailableSnowSeason;
import org.ldd.ssm.hangyu.domain.Barrier;
import org.ldd.ssm.hangyu.domain.FlowOfSubsidiary;
import org.ldd.ssm.hangyu.domain.GroundServicesAndFacilities;
import org.ldd.ssm.hangyu.domain.GuilanceAndControlSystemAndIdentifyTheGroundActivities;
import org.ldd.ssm.hangyu.domain.HelicopterLandingArea;
import org.ldd.ssm.hangyu.domain.LengthIntensity;
import org.ldd.ssm.hangyu.domain.LengthIntensityRek;
import org.ldd.ssm.hangyu.domain.OtherLightsBackupPowerSupply;
import org.ldd.ssm.hangyu.domain.PhysicalCharacteristicsOfRunway;
import org.ldd.ssm.hangyu.domain.PhysicalCharacteristicsOfRunwayRek;
import org.ldd.ssm.hangyu.domain.ProvideWeatherInformation;
import org.ldd.ssm.hangyu.domain.PublicOpinion;
import org.ldd.ssm.hangyu.domain.RadioNavigationAndLandingFacilities;
import org.ldd.ssm.hangyu.domain.RescueAndFireService;
import org.ldd.ssm.hangyu.domain.RewardPolicy;
import org.ldd.ssm.hangyu.domain.RouteNetwork;
import org.ldd.ssm.hangyu.domain.RunwayTheDetail;
import org.ldd.ssm.hangyu.domain.WeatherInfo;
import org.ldd.ssm.hangyu.mapper.AirCompenyInfoThreeMapper;
import org.ldd.ssm.hangyu.mapper.AirPortWeatherInfoMapper;
import org.ldd.ssm.hangyu.mapper.AirTrafficServiceAirspaceMapper;
import org.ldd.ssm.hangyu.mapper.AirTrafficServiceCommunicationFacilitiesMapper;
import org.ldd.ssm.hangyu.mapper.AirportInfoNewThreeMapper;
import org.ldd.ssm.hangyu.mapper.AirportLocationAndManagementInformationMapper;
import org.ldd.ssm.hangyu.mapper.ApronTaxiwayAndCalibrationLocationDataMapper;
import org.ldd.ssm.hangyu.mapper.AvailableSnowSeasonMapper;
import org.ldd.ssm.hangyu.mapper.BarrierMapper;
import org.ldd.ssm.hangyu.mapper.FlowOfSubsidiaryMapper;
import org.ldd.ssm.hangyu.mapper.GroundServicesAndFacilitiesMapper;
import org.ldd.ssm.hangyu.mapper.GuilanceAndControlSystemAndIdentifyTheGroundActivitiesMapper;
import org.ldd.ssm.hangyu.mapper.HelicopterLandingAreaMapper;
import org.ldd.ssm.hangyu.mapper.LengthIntensityMapper;
import org.ldd.ssm.hangyu.mapper.LengthIntensityRekMapper;
import org.ldd.ssm.hangyu.mapper.OtherLightsBackupPowerSupplyMapper;
import org.ldd.ssm.hangyu.mapper.PhysicalCharacteristicsOfRunwayMapper;
import org.ldd.ssm.hangyu.mapper.PhysicalCharacteristicsOfRunwayRekMapper;
import org.ldd.ssm.hangyu.mapper.ProvideWeatherInformationMapper;
import org.ldd.ssm.hangyu.mapper.RadioNavigationAndLandingFacilitiesMapper;
import org.ldd.ssm.hangyu.mapper.RescueAndFireServiceMapper;
import org.ldd.ssm.hangyu.mapper.RewardPolicyMapper;
import org.ldd.ssm.hangyu.mapper.RunwayTheDetailMapper;
import org.ldd.ssm.hangyu.mapper.WeatherInfoMapper;
import org.ldd.ssm.hangyu.service.AirportInfoNewThreeService;
import org.ldd.ssm.hangyu.utils.PublicOpinionUtil;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
@Service
public class AirportInfoNewThreeServiceImpl implements AirportInfoNewThreeService {
	
	@Autowired
	private AirCompenyInfoThreeMapper compenyInfoMapper;
	@Autowired
	private AirportInfoNewThreeMapper airportInfoNewThreeMapper;
	@Autowired
	private FlowOfSubsidiaryMapper flowOfSubsidiaryMapper;
	@Autowired
	private RunwayTheDetailMapper runwayTheDetailMapper;
	@Autowired
	private RewardPolicyMapper rewardPolicyMapper;
	@Autowired
	private AirportLocationAndManagementInformationMapper airportLocationAndManagementInformationMapper;
	@Autowired
	private AirPortWeatherInfoMapper airPortWeatherInfoMapper;
	@Autowired
	private AirTrafficServiceAirspaceMapper airTrafficServiceAirspaceMapper;
	@Autowired
	private AirTrafficServiceCommunicationFacilitiesMapper airTrafficServiceCommunicationFacilitiesMapper;
	@Autowired
	private ApronTaxiwayAndCalibrationLocationDataMapper apronTaxiwayAndCalibrationLocationDataMapper;
	@Autowired
	private AvailableSnowSeasonMapper availableSnowSeasonMapper;
	@Autowired
	private BarrierMapper barrierMapper;
	@Autowired
	private GroundServicesAndFacilitiesMapper groundServicesAndFacilitiesMapper;
	@Autowired
	private GuilanceAndControlSystemAndIdentifyTheGroundActivitiesMapper guilanceAndControlSystemAndIdentifyTheGroundActivitiesMapper;
	@Autowired
	private HelicopterLandingAreaMapper helicopterLandingAreaMapper;
	@Autowired
	private OtherLightsBackupPowerSupplyMapper otherLightsBackupPowerSupplyMapper;
	@Autowired
	private PhysicalCharacteristicsOfRunwayMapper physicalCharacteristicsOfRunwayMapper;
	@Autowired
	private PhysicalCharacteristicsOfRunwayRekMapper physicalCharacteristicsOfRunwayRekMapper;
	@Autowired
	private LengthIntensityMapper lengthIntensityMapper;
	@Autowired
	private LengthIntensityRekMapper lengthIntensityRekMapper;
	@Autowired
	private ProvideWeatherInformationMapper provideWeatherInformationMapper;
	@Autowired
	private RadioNavigationAndLandingFacilitiesMapper radioNavigationAndLandingFacilitiesMapper;
	@Autowired
	private RescueAndFireServiceMapper rescueAndFireServiceMapper;
	@Autowired
	private WeatherInfoMapper weatherInfoMapper;
	
	Logger log = Logger.getLogger(AirportInfoNewThreeServiceImpl.class);
	
	@Override
	public List<RouteNetwork> getAirportListByIata(int type,String iata) {
		List<RouteNetwork> list = null;
		if(TextUtil.isEmpty(iata)){
			log.debug("iata is an invalid parameter");
			return list;
		}
		try {
			if(type==0){//航司网络
				List<RouteNetwork> newList =  TextUtil.distinct(airportInfoNewThreeMapper.selectAirlineAirportList(iata));
				if(newList!=null&&newList.size()>0){
					list = new ArrayList<RouteNetwork>();
					for(RouteNetwork airport:newList){
						airport = airportInfoNewThreeMapper.selectAirlineAirport(airport.getDptIata(), airport.getArrvIata());
						if(airport!=null)
							list.add(airport);
					}
				}
			}else if(type ==1){//机场网络
				list =  airportInfoNewThreeMapper.selectAirportRouteNetwork(iata);
			}
		} catch (Exception e) {
			log.error("There is something wrong here,please check code");
			e.printStackTrace();
			return list;
		}
		return list;
	}

	@Override
	public Map<String, Object> loadAirportByCode(String itia) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(TextUtil.isEmpty(itia)){
			log.debug("loadAirportByCode:itia is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
			AirportInfoNewThree airport = airportInfoNewThreeMapper.selectByIcao(itia);
			
			if(airport==null){
				map.put("opResult", "1");
			}else{
				map.put("opResult", "0");
				Long airportId = airport.getId();
				//获取当前机场的吞吐信息
				List<FlowOfSubsidiary> list = flowOfSubsidiaryMapper.selectFlowOfSubsidiaryForLastThreeYears(airportId);
				if(list!=null&&list.size()>0){
					int[] passengerThroughputs = new int[list.size()];//旅客吞吐量列表
					Double[] goodsThroughputs = new Double[list.size()];//货邮吞吐量列表
					Double[] takeOffAndLandingFlights = new Double[list.size()];//起降架次列表
					String[] years = new String[list.size()];
					for(int i=list.size()-1;i>-1;i--){
						FlowOfSubsidiary diary = list.get(i);
						years[i] = diary.getYear();
						passengerThroughputs[i] = diary.getPassengerthroughput()==null?0:"".equals(diary.getPassengerthroughput())?0:Integer.valueOf(diary.getPassengerthroughput());
						goodsThroughputs[i] = diary.getGoodsthroughput()==null?0:"".equals(diary.getGoodsthroughput())?0:Double.valueOf(diary.getGoodsthroughput());
						takeOffAndLandingFlights[i] = diary.getTakeoffandlandingflights()==null?0:"".equals(diary.getTakeoffandlandingflights())?0:Double.valueOf(diary.getTakeoffandlandingflights());
					}
					airport.setYears(years);
					airport.setPassengerThroughputs(passengerThroughputs);
					airport.setGoodsThroughputs(goodsThroughputs);
					airport.setTakeOffAndLandingFlights(takeOffAndLandingFlights);
				}
				//获取当前机场的跑道信息
				if(!TextUtil.isEmpty(airport.getRunwayarticlenumber())&&!"0".equals(airport.getRunwayarticlenumber())){
					List<RunwayTheDetail> runwayList = runwayTheDetailMapper.selectRunwaysForAirport(airportId);
					if(runwayList!=null&&runwayList.size()>0){
						airport.setRunwayList(runwayList);
					}
				}
				//获取当前机场政策信息
				List<RewardPolicy> rewardPolicies = rewardPolicyMapper.selectRecordsByAirportId(airportId);
				if(rewardPolicies!=null&&rewardPolicies.size()>0)
					airport.setRewardPolicyList(rewardPolicies);	
				//获取机场舆情列表
				List<PublicOpinion> opinions = PublicOpinionUtil.getPublicOpinionForAirport(itia, 1, 5);//默认页码传1，且页面上只显示5条，暂定
				if(opinions!=null&&opinions.size()>0)
					airport.setOpinions(opinions);
				map.put("obj", airport);
			}
		} catch (Exception e) {
			log.error("There is something wrong here,please check code");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> loadAirportInformationByIata(String iata) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(TextUtils.isEmpty(iata)){
			log.debug("loadAirportInformationByIata:iata is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
			AirportInformation ai = new AirportInformation();
			//机场地理位置和管理资料
			List<AirportLocationAndManagementInformation> selectAirportLocationByIatas = airportLocationAndManagementInformationMapper.selectAirportLocationByIata(iata);
			if(selectAirportLocationByIatas!=null&&selectAirportLocationByIatas.size()>0)
				ai.setAirportLocation(selectAirportLocationByIatas.get(0));
			//停机坪、滑行道及校正位置数据
			List<ApronTaxiwayAndCalibrationLocationData> selectApronTaxiwayAndCalibrationLocationByIatas = apronTaxiwayAndCalibrationLocationDataMapper.selectApronTaxiwayAndCalibrationLocationByIata(iata);
			if(selectApronTaxiwayAndCalibrationLocationByIatas!=null&&selectApronTaxiwayAndCalibrationLocationByIatas.size()>0)
				ai.setApronTaxiway(selectApronTaxiwayAndCalibrationLocationByIatas.get(0));
			//可用季节-扫雪
			List<AvailableSnowSeason> selectAvailableSnowSeasonByIatas = availableSnowSeasonMapper.selectAvailableSnowSeasonByIata(iata);
			if(selectAvailableSnowSeasonByIatas!=null&&selectAvailableSnowSeasonByIatas.size()>0)
				ai.setAvailable(selectAvailableSnowSeasonByIatas.get(0));
			//地勤服务和设施
			List<GroundServicesAndFacilities> selectGroundServicesAndFacilities = groundServicesAndFacilitiesMapper.selectGroundServicesAndFacilitiesByIata(iata);
			if(selectGroundServicesAndFacilities!=null&&selectGroundServicesAndFacilities.size()>0)
				ai.setGroundServices(selectGroundServicesAndFacilities.get(0));
			//地面活动引导和管制系统
			List<GuilanceAndControlSystemAndIdentifyTheGroundActivities> selectGuilanceAndControlSystemAndIdentifyTheGroundActivitiesByIatas = guilanceAndControlSystemAndIdentifyTheGroundActivitiesMapper.selectGuilanceAndControlSystemAndIdentifyTheGroundActivitiesByIata(iata);
			if(selectGuilanceAndControlSystemAndIdentifyTheGroundActivitiesByIatas!=null&&selectGuilanceAndControlSystemAndIdentifyTheGroundActivitiesByIatas.size()>0)
				ai.setGuilance(selectGuilanceAndControlSystemAndIdentifyTheGroundActivitiesByIatas.get(0));
			//直升机着陆区域
			List<HelicopterLandingArea> selectHelicopterLandingAreaByIatas = helicopterLandingAreaMapper.selectHelicopterLandingAreaByIata(iata);
			if(selectHelicopterLandingAreaByIatas!=null&&selectHelicopterLandingAreaByIatas.size()>0)
				ai.setHelicopter(selectHelicopterLandingAreaByIatas.get(0));
			//其它灯光,备份电源
			List<OtherLightsBackupPowerSupply> selectOtherLightsBackupPowerSupplyByIatas = otherLightsBackupPowerSupplyMapper.selectOtherLightsBackupPowerSupplyByIata(iata);
			if(selectOtherLightsBackupPowerSupplyByIatas!=null&&selectOtherLightsBackupPowerSupplyByIatas.size()>0)
			ai.setOther(selectOtherLightsBackupPowerSupplyByIatas.get(0));
			//提供的气象情报
			List<ProvideWeatherInformation> selectProvideWeatherInformationByIatas = provideWeatherInformationMapper.selectProvideWeatherInformationByIata(iata);
			if(selectProvideWeatherInformationByIatas!=null&&selectProvideWeatherInformationByIatas.size()>0)
				ai.setProvide(selectProvideWeatherInformationByIatas.get(0));
			//援救与消防服务
			List<RescueAndFireService> selectRescueAndFireServiceByIatas = rescueAndFireServiceMapper.selectRescueAndFireServiceByIata(iata);
			if(selectRescueAndFireServiceByIatas!=null&&selectRescueAndFireServiceByIatas.size()>0)
				ai.setRescue(selectRescueAndFireServiceByIatas.get(0));
			//机场天气观测和报告
			List<AirPortWeatherInfo> selectAirPortWeatherInfosByIata = airPortWeatherInfoMapper.selectAirPortWeatherInfosByIata(iata);
			if(selectAirPortWeatherInfosByIata!=null&&selectAirPortWeatherInfosByIata.size()>0)
				ai.setAirPortWeatherInfos(selectAirPortWeatherInfosByIata);
			//空中交通服务空域
			List<AirTrafficServiceAirspace> selectAirTrafficServiceAirspacesByIata = airTrafficServiceAirspaceMapper.selectAirTrafficServiceAirspacesByIata(iata);
			if(selectAirTrafficServiceAirspacesByIata!=null&&selectAirTrafficServiceAirspacesByIata.size()>0)
				ai.setAirspace(selectAirTrafficServiceAirspacesByIata);
			//空中交通服务通信设施
			List<AirTrafficServiceCommunicationFacilities> selectAirTrafficServiceCommunicationFacilitiesByIata = airTrafficServiceCommunicationFacilitiesMapper.selectAirTrafficServiceCommunicationFacilitiesByIata(iata);
			if(selectAirTrafficServiceCommunicationFacilitiesByIata!=null&&selectAirTrafficServiceCommunicationFacilitiesByIata.size()>0)
				ai.setAirTraffic(selectAirTrafficServiceCommunicationFacilitiesByIata);
			//障碍物
			List<Barrier> selectBarriersByIata = barrierMapper.selectBarriersByIata(iata);
			if(selectBarriersByIata!=null&&selectBarriersByIata.size()>0)
				ai.setBarriers(selectBarriersByIata);
			//跑道物理特征
			List<PhysicalCharacteristicsOfRunway> selectPhysicalCharacteristicsOfRunwaysByIata = physicalCharacteristicsOfRunwayMapper.selectPhysicalCharacteristicsOfRunwaysByIata(iata);
			if(selectPhysicalCharacteristicsOfRunwaysByIata!=null&&selectPhysicalCharacteristicsOfRunwaysByIata.size()>0)
				ai.setPhysical(selectPhysicalCharacteristicsOfRunwaysByIata);
			//跑道物理特征备注
			List<PhysicalCharacteristicsOfRunwayRek> runwayReks = physicalCharacteristicsOfRunwayRekMapper.selectPhysicalCharacteristicsOfRunwayRekByIata(iata);
			if(runwayReks!=null&&runwayReks.size()>0)
				ai.setRunwayRek(runwayReks.get(0));
			//进近和跑道灯光
			List<LengthIntensity> intensity = lengthIntensityMapper.selectLengthIntensitysByIata(iata);
			if(intensity!=null&&intensity.size()>0)
				ai.setIntensity(intensity);
			//进近和跑道灯光注释
			List<LengthIntensityRek> intensityReks = lengthIntensityRekMapper.selectLengthIntensityRekByIata(iata);
			if(intensityReks!=null&&intensityReks.size()>0)
				ai.setIntensityRek(intensityReks.get(0));
			//无线电导航和着陆设施
			List<RadioNavigationAndLandingFacilities> selectRadioNavigationAndLandingFacilitiesByIata = radioNavigationAndLandingFacilitiesMapper.selectRadioNavigationAndLandingFacilitiesByIata(iata);
			if(selectRadioNavigationAndLandingFacilitiesByIata!=null&&selectRadioNavigationAndLandingFacilitiesByIata.size()>0)
				ai.setRadio(selectRadioNavigationAndLandingFacilitiesByIata);
			//天气信息
			List<WeatherInfo> selectWeatherInfosByIata = weatherInfoMapper.selectWeatherInfosByIata(iata);
			if(selectWeatherInfosByIata!=null&&selectWeatherInfosByIata.size()>0)
				ai.setWeatherInfos(selectWeatherInfosByIata);
			map.put("opResult", "0");
			map.put("obj", ai);
		} catch (Exception e) {
			log.error("There are errors in code");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	@Override
	@Cacheable(value = "loadIndexAirportInfoByCode",key = "'itia_'+#root.args[0]")
	public Map<String, Object> loadIndexAirportInfoByCode(String itia) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(TextUtil.isEmpty(itia)){
			log.debug("loadAirportByCode:itia is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
			AirportInfoNewThree airport = airportInfoNewThreeMapper.selectByIcao(itia);
			if(airport==null){
				map.put("opResult", "1");
			}else{
				map.put("opResult", "0");
				Long airportId = airport.getId();
				//关联航司列表
				List<AirCompenyInfoThree> compenys = compenyInfoMapper.selectAirCompenysByIcao(itia);
				if(compenys!=null&&compenys.size()>0){
					airport.setCompenys(compenys);
				}
				//获取当前机场政策信息
				List<RewardPolicy> rewardPolicies = rewardPolicyMapper.selectRecordsByAirportId(airportId);
				if(rewardPolicies!=null&&rewardPolicies.size()>0)
					airport.setRewardPolicyList(rewardPolicies);	
				//获取机场舆情列表
				List<PublicOpinion> opinions = PublicOpinionUtil.getPublicOpinionForAirport(itia, 1, 5);//默认页码传1，且页面上只显示5条，暂定
				if(opinions!=null&&opinions.size()>0)
					airport.setOpinions(opinions);
				map.put("obj", airport);
			}
		} catch (Exception e) {
			log.error("There is something wrong here,please check code");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
}
