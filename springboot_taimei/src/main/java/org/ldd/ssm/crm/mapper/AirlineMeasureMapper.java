package org.ldd.ssm.crm.mapper;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.ApplyMeasureHistory;
import org.ldd.ssm.crm.domain.MeasureResult;
import org.ldd.ssm.crm.query.AirCompanyInfoQuery;
import org.ldd.ssm.crm.query.AirportInfoData;
import org.ldd.ssm.crm.query.LegProfitFocastQuery;
import org.ldd.ssm.crm.query.ProfitFocast;

/**
 * 开航测算
 * @author wxm
 *
 * @date 2017-7-25
 */
public interface AirlineMeasureMapper {
	//机场对比
	List<AirportInfoData> findAirportCompareInfo(List<String> list);
	
	//机场信息
	List<AirportInfoData> findAirportInfo(LegProfitFocastQuery dto);
	
	List<ProfitFocast> getalllegs(List<Map<String, String>> list);
	
	//收益预估列表
	List<ProfitFocast> getCostCalculation(LegProfitFocastQuery dto);
		
	//返回高级筛选里面的机型 
	List<String> getModelCanHandle(List<String> iATAs);
	
	//航司信息
	AirCompanyInfoQuery getAircompanyInfo(Long id);//航司表id
	
	//根据申请测算条件查到以测算的数据
	List<MeasureResult> getMeasureResult(ApplyMeasureHistory dto);
	
	int isExist(ApplyMeasureHistory dto);
	
	int applyIsExist(ApplyMeasureHistory dto);
	
	

}
