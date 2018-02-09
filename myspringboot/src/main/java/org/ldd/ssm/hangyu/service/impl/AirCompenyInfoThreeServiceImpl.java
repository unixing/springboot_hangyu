package org.ldd.ssm.hangyu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.AirCompenyInfoThree;
import org.ldd.ssm.hangyu.domain.PlaneDetail;
import org.ldd.ssm.hangyu.domain.PublicOpinion;
import org.ldd.ssm.hangyu.mapper.AirCompenyInfoThreeMapper;
import org.ldd.ssm.hangyu.mapper.PlaneDetailMapper;
import org.ldd.ssm.hangyu.service.AirCompenyInfoThreeService;
import org.ldd.ssm.hangyu.utils.PublicOpinionUtil;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
@Service
public class AirCompenyInfoThreeServiceImpl implements
		AirCompenyInfoThreeService {
	Logger log = Logger.getLogger(AirCompenyInfoThreeServiceImpl.class);
	@Autowired
	private AirCompenyInfoThreeMapper airCompenyInfoThreeMapper;
	@Autowired
	private PlaneDetailMapper planeDetailMapper;
	@Cacheable(value = "selectAll")
	public List<AirCompenyInfoThree> selectAll() {
		try {
			return airCompenyInfoThreeMapper.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("there is something wrong here");
			return null;
		}
	}
	
	@Cacheable(value = "loadAircompenyDetail")
	public Map<String, Object> loadAircompenyDetail(String itia) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(TextUtil.isEmpty(itia)){
			log.debug("loadAircompenyDetail:itia is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
			AirCompenyInfoThree aircompeny= airCompenyInfoThreeMapper.selectAircompenyByIcao(itia);
			if(aircompeny!=null){
				//统计通航机场和国家数量
				String shippingCountry = aircompeny.getShippingcountry();
				if(TextUtil.isEmpty(shippingCountry)){
					aircompeny.setShippingcountry("0");
				}else{
					String[] countrys = shippingCountry.split("、");
					int count = 0;
					for(String country:countrys){
						if(!TextUtil.isEmpty(country)){
							count++;
						}
					}
					aircompeny.setShippingcountry(count+"");
				}
				String navigationAirport = aircompeny.getNavigationairport();
				if(TextUtil.isEmpty(navigationAirport)){
					aircompeny.setNavigationairport("0");
				}else{
					String[] airports = navigationAirport.split(",");
					int count = 0;
					for(String airprot:airports){
						if(!TextUtil.isEmpty(airprot)){
							count++;
						}
					}
					aircompeny.setNavigationairport(count+"");
				}
				//获取机型列表
				List<PlaneDetail> planeDetails = planeDetailMapper.selectPlaneDetailsByAircompenyId(aircompeny.getId());
				if(planeDetails!=null&&planeDetails.size()>0)
					aircompeny.setPlaneDetails(planeDetails);
				//获取舆情列表
				//获取机场舆情列表
				List<PublicOpinion> opinions = PublicOpinionUtil.getPublicOpinionForAirLine(itia, 1, 5);//默认页码传1，且页面上只显示5条，暂定
				if(opinions!=null&&opinions.size()>0)
					aircompeny.setPublicOpinions(opinions);
				map.put("opResult", "0");
				map.put("obj", aircompeny);
			}else{
				map.put("opResult", "0");
			}
		} catch (Exception e) {
			log.error("There are errors in code");
			e.printStackTrace();
			map.put("opResutl", "2");
			return map;
		}
		return map;
	}

	@Cacheable(value = "load")
	public AirCompenyInfoThree load(String iata) {
		AirCompenyInfoThree compeny = null;
		if(StringUtils.isEmpty(iata)){
			log.debug("load:iata is an invalid parameter");
			return compeny;
		}
		try {
			compeny = airCompenyInfoThreeMapper.selectAirCompenyByIata(iata);
		} catch (Exception e) {
			log.error("There are errors in code");
			e.printStackTrace();
			return compeny;
		}
		return compeny;
	}
}
