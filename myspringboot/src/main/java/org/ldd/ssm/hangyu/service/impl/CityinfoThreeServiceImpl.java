package org.ldd.ssm.hangyu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.CityinfoThree;
import org.ldd.ssm.hangyu.domain.CulturaleducationyYear;
import org.ldd.ssm.hangyu.domain.EconomicYear;
import org.ldd.ssm.hangyu.domain.Policy;
import org.ldd.ssm.hangyu.domain.Population;
import org.ldd.ssm.hangyu.domain.PublicOpinion;
import org.ldd.ssm.hangyu.domain.Traffic;
import org.ldd.ssm.hangyu.domain.TrafficLine;
import org.ldd.ssm.hangyu.mapper.CityinfoThreeMapper;
import org.ldd.ssm.hangyu.mapper.CulturaleducationyYearMapper;
import org.ldd.ssm.hangyu.mapper.EconomicYearMapper;
import org.ldd.ssm.hangyu.mapper.PolicyMapper;
import org.ldd.ssm.hangyu.mapper.PopulationMapper;
import org.ldd.ssm.hangyu.mapper.TrafficMapper;
import org.ldd.ssm.hangyu.service.CityinfoThreeService;
import org.ldd.ssm.hangyu.utils.PublicOpinionUtil;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CityinfoThreeServiceImpl implements CityinfoThreeService {
	@Autowired
	private CityinfoThreeMapper cityinfoThreeMapper;
	@Autowired
	private PolicyMapper policyMapper;
	@Autowired
	private CulturaleducationyYearMapper culturaleducationyYearMapper;
	@Autowired
	private EconomicYearMapper economicYearMapper;
	@Autowired
	private PopulationMapper populationMapper;
	@Autowired
	private TrafficMapper trafficMapper;
	private Logger log= Logger.getLogger(CityCoordinateServiceImpl.class);
	@Override
	public Map<String, Object> getCityDetail(String cityName) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(TextUtil.isEmpty(cityName)){
			log.debug("getCityDetail:cityName is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
			List<CityinfoThree> citys = cityinfoThreeMapper.selectCityDetailByName(cityName);
			if(citys!=null&&citys.size()>0){
				map.put("opResult", "0");
				for(int i =0;i<citys.size();i++){
					CityinfoThree city = citys.get(i);
					Long cityId = city.getId();
					//获取城市相关信息
					//1.补贴政策
					List<Policy> policys = policyMapper.selectPolicyByCityId(cityId);
					if(policys!=null&&policys.size()>0)
						city.setPolicys(policys);
					//2.旅游信息
					List<CulturaleducationyYear> culturaleducationyYears = culturaleducationyYearMapper.selectCulturaleducationyYearListByCityId(cityId);
					if(culturaleducationyYears!=null&&culturaleducationyYears.size()>0)
						city.setCulturaleducationyYears(culturaleducationyYears);
					//3.经济信息
					List<EconomicYear> economicYears = economicYearMapper.selectEconomicYearByCityId(cityId);
					if(economicYears!=null&&economicYears.size()>0)
						city.setEconomicYears(economicYears);
					//4.人口信息
					List<Population> populations = populationMapper.selectPopulationsByCityId(cityId);
					if(populations!=null&&populations.size()>0)
						city.setPopulations(populations);
					//5.交通信息
					List<Traffic> traffics = trafficMapper.selectyTrafficsByCityId(cityId);
					if(traffics!=null&&traffics.size()>0){
						List<TrafficLine> highwayList = new ArrayList<TrafficLine>();
						List<TrafficLine> railwayList = new ArrayList<TrafficLine>();
						for(Traffic traffic:traffics){
							if(!TextUtil.isEmpty(traffic.getHighwaytype())){
								TrafficLine trafficLine = new TrafficLine(traffic.getHighwaytype(), traffic.getHighwaycode(), traffic.getHighwaywaypoint());
								highwayList.add(trafficLine);
							}
							if(!TextUtil.isEmpty(traffic.getRailtype())){
								TrafficLine trafficLine = new TrafficLine(traffic.getRailtype(), traffic.getRailcode(), traffic.getRailwaypoint());
								railwayList.add(trafficLine);
							}
						}
						if(highwayList!=null&&highwayList.size()>0)
							city.setHighwayList(highwayList);
						if(railwayList!=null&&railwayList.size()>0)
							city.setRailwayList(railwayList);
					}
					//6.舆情信息
					List<PublicOpinion> publicopinions = PublicOpinionUtil.getPublicOpinionForCity(city.getCityname(), 1, 10);
					if(publicopinions!=null&&publicopinions.size()>0)
						city.setPublicopinions(publicopinions);
					citys.set(i, city);
				}
				map.put("citys", citys);
			}else{
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			log.error("There are errors in code");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

}
