package org.ldd.ssm.crm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ldd.ssm.crm.domain.ApplyMeasureHistory;
import org.ldd.ssm.crm.domain.MeasureResult;
import org.ldd.ssm.crm.mapper.AirlineMeasureMapper;
import org.ldd.ssm.crm.mapper.ApplyMeasureHistoryMapper;
import org.ldd.ssm.crm.mapper.MeasureResultMapper;
import org.ldd.ssm.crm.query.AirCompanyInfoQuery;
import org.ldd.ssm.crm.query.AirportInfoData;
import org.ldd.ssm.crm.query.CostAnalyze;
import org.ldd.ssm.crm.query.FocastReslut;
import org.ldd.ssm.crm.query.LegProfitFocastQuery;
import org.ldd.ssm.crm.service.AirlineMeasureService;
import org.ldd.ssm.crm.utils.AirlineMeasureUtils;
import org.ldd.ssm.crm.utils.TextUtil;
import org.springframework.stereotype.Service;

@Service
public class AirlineMeasureServiceImpl implements AirlineMeasureService{
	
	@Resource
	private AirlineMeasureMapper airlineMeasureMapper;
	
	@Resource
	private ApplyMeasureHistoryMapper applyMeasureHistoryMapper;
	
	@Resource
	private MeasureResultMapper measureResultMapper;
	
	AirlineMeasureUtils utils=new AirlineMeasureUtils();
	@Override
	public List<AirportInfoData> findAirportCompareInfo(List<String> list) {
		
		List<AirportInfoData> airportList=airlineMeasureMapper.findAirportCompareInfo(list);
		//循环把近四年补全
		for(AirportInfoData airport:airportList){
			airport.setThroughputs(utils.lastFourYears(airport.getThroughputs()));
			airport.setGdps(utils.lastFourYears(airport.getGdps()));
			if(airport.getCityPgeNumber()==null){
				airport.setCityPgeNumber("-");
			}
		}
		return airportList;
	}

	@Override
	public CostAnalyze findAirports(LegProfitFocastQuery dto) {
		CostAnalyze costAnalyzeresult=new CostAnalyze();
		FocastReslut focastResult=new FocastReslut();
		
		//测算结果
		focastResult.setFltRteCd(dto.getFltRteCd().replaceAll("-",""));
		focastResult.setAirCompanyName(dto.getAircompanyName());
		focastResult.setDistance(dto.getDistance());
		focastResult.setAircraftModel(dto.getAircraftModel());
		focastResult.setSites(dto.getSites());
		focastResult.setHourCost(dto.getHourCost());
		focastResult.setSpeed(dto.getSpeed());
		focastResult.setStartTime(dto.getStartTime());
		focastResult.setEndTime(dto.getEndTime());
		
		//计算飞行时长= 距离/速度     航班成本=飞行时长*小时成本
		if(dto.getSpeed()!=0.0){
			float flyTime=dto.getDistance()/dto.getSpeed();
			focastResult.setFlyTime(((float)(Math.round(flyTime*100))/100));
			focastResult.setLdsj(dto.getLdsj());
			if(!TextUtil.isEmpty(dto.getLdsj())){
				focastResult.setCost(((float)(Math.round(dto.getHourCost()*(flyTime+Double.parseDouble(dto.getLdsj()))*100))/100));                                  
			}else{
				focastResult.setCost(((float)(Math.round(dto.getHourCost()*(flyTime)*100))/100));  
			}
		}
		costAnalyzeresult.setFocastResult(focastResult);
		//机场信息
		List<AirportInfoData> airportList=airlineMeasureMapper.findAirportInfo(dto);
		costAnalyzeresult.setAirportList(airportList);
		AirCompanyInfoQuery airCompanyInfo=airlineMeasureMapper.getAircompanyInfo(dto.getAircompanyId());
		costAnalyzeresult.setAirCompanyInfo(airCompanyInfo);
		return costAnalyzeresult;
	}
	
	/**
	 * 申请测算添加
	 */
	public boolean addApplyMeasure(ApplyMeasureHistory dto) {
		int affectRows=0;
		affectRows=applyMeasureHistoryMapper.insertApplyMeasure(dto);
		if(affectRows==1){
			return true;
		}
		return false;
	}
	/**
	 * 申请列表查询
	 */
	public List<ApplyMeasureHistory> findApplyMeasureList(Long userId) {
		return applyMeasureHistoryMapper.findApplyMeasureList(userId);
	}
	
	
	/**
	 * 
	 * @param id 测算申请历史表id
	 * @return
	 */
	public CostAnalyze getApplyMeasureDetail(Long id) {
		//根据id 查申请测算表  得到之前的查询条件  fltRteCd, distance,airlines,aircraftModel,sites,hourCost,speed,shijian 
		LegProfitFocastQuery query=applyMeasureHistoryMapper.getApplyMeasure(id);
		if(query==null){
			return null;
		}
		String s=query.getFltRteCd();
		List<String> list = new ArrayList<String>();
		for(int i=0;i<=s.length()-3;){
			list.add(s.substring(i, i+3));
			i=i+3;
		}
		query.setAirlineList(list);
		CostAnalyze costAnalyzeresult=new CostAnalyze();
		//机场
		costAnalyzeresult=findAirports(query);
		Map<String,Object> map=new HashMap<String, Object>();
		Map<String,Object> mapNew=new HashMap<String, Object>();
		List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
		//测算
		//我的测算时间
		FocastReslut focastResult=costAnalyzeresult.getFocastResult();
		String myTime=measureResultMapper.getMyMeasureTime(id);
		//我的测算结果
		List<MeasureResult> myResult=measureResultMapper.findMeasureResult(id);
		
		map.put("time",myTime);
		map.put("msg","我的预估结果");
		map.put("data",myResult);
		mapList.add(map);
		//最新测算时间
		MeasureResult resultDto=new MeasureResult();
		resultDto.setFltRteCd(query.getFltRteCd());
		resultDto.setMeasureTime(myTime);
		MeasureResult Newestresult=measureResultMapper.getNewestMeasureResult(resultDto);
		if(Newestresult!=null&&Newestresult.getApplyMeasureId()!=null){
			//最新结果
			List<MeasureResult> newestResult=measureResultMapper.findMeasureResult(Newestresult.getApplyMeasureId());
			mapNew.put("time",Newestresult.getMeasureTime());
			mapNew.put("msg","最新预估结果");
			mapNew.put("data",newestResult);
			mapList.add(mapNew);
		}
		focastResult.setMap(mapList);
		
		return costAnalyzeresult;
	}
	

	@Override
	public List<String> getModelCanHandle(List<String> iATAs) {
		List<String> data=airlineMeasureMapper.getModelCanHandle(iATAs);
		return utils.intersectList(data);
	}

	
	public boolean updateStateById(ApplyMeasureHistory dto){
		int affect=0;
		affect=applyMeasureHistoryMapper.updateState(dto);
		if(affect==1){
			return true;
		}
		return false;
	}

	@Override
	public List<AirCompanyInfoQuery> getAllAirCompany(String airType) {
		return applyMeasureHistoryMapper.getAllAirCompany(airType);
	}

	@Override
	public List<String> getAllAirType(Long aircompanyId) {
		return applyMeasureHistoryMapper.getAllAirType(aircompanyId);
	}

	//查看已有结果的数据
	public List<MeasureResult> getMeasureResult(ApplyMeasureHistory dto) {
		return airlineMeasureMapper.getMeasureResult(dto);
	}

	@Override
	public boolean isExist(ApplyMeasureHistory dto) {
		int affect=0;
		affect=airlineMeasureMapper.isExist(dto);
		if(affect>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean applyIsExist(ApplyMeasureHistory dto) {
		int affect=0;
		affect=airlineMeasureMapper.applyIsExist(dto);
		if(affect>0){
			return true;
		}
		return false;
	}
	
	
	

}
