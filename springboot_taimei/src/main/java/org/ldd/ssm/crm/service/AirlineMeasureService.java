package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.ApplyMeasureHistory;
import org.ldd.ssm.crm.domain.MeasureResult;
import org.ldd.ssm.crm.query.AirCompanyInfoQuery;
import org.ldd.ssm.crm.query.AirportInfoData;
import org.ldd.ssm.crm.query.CostAnalyze;
import org.ldd.ssm.crm.query.LegProfitFocastQuery;

public interface AirlineMeasureService {
	//机场对比
	public List<AirportInfoData> findAirportCompareInfo(List<String> list);
	//航线所对应的机场信息
	public CostAnalyze findAirports(LegProfitFocastQuery dto);
	//添加申请测算
	public boolean addApplyMeasure(ApplyMeasureHistory dto);
	
	public List<ApplyMeasureHistory> findApplyMeasureList(Long userId);
	//查看测算详情
	public CostAnalyze getApplyMeasureDetail(Long id);
	
	public List<String> getModelCanHandle(List<String> iTATs);
	
	public boolean updateStateById(ApplyMeasureHistory dto);
	
	//根据机型获得航司
	List<AirCompanyInfoQuery> getAllAirCompany(String airType);
	//根据航司获得机型
	List<String> getAllAirType(Long aircompanyId);
	
	List<MeasureResult> getMeasureResult(ApplyMeasureHistory dto);
	
	boolean isExist(ApplyMeasureHistory dto);
	
	boolean applyIsExist(ApplyMeasureHistory dto);
	
	
}
