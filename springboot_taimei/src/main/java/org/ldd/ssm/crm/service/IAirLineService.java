package org.ldd.ssm.crm.service;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.AllClzIncome;
import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.IncomeCount;
import org.ldd.ssm.crm.domain.IncomeCountTime;
import org.ldd.ssm.crm.domain.OutPort;
import org.ldd.ssm.crm.domain.SalePlanData;
import org.ldd.ssm.crm.domain.TimeCount;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.AirLineQueryNew;
import org.ldd.ssm.crm.query.CabinSeatSetQuery;
import org.ldd.ssm.crm.query.DOWQuery;
import org.ldd.ssm.crm.query.MonthParameterQuery;
import org.ldd.ssm.crm.query.MonthSalesPlanQuery;

public interface IAirLineService {

	List<OutPort> getOutPort(DOWQuery dowData, List<Z_Airdata> airdatas);

	List<EvenPort> getEvenPort(List<Z_Airdata> airdatas, DOWQuery dta_Sce);

	List<DOWQuery> getArrayParameter(DOWQuery dowData);

	List<String> getFlt_Nbr(DOWQuery dowData);
	
	List<String> getMonthFltNum(DOWQuery dowData);

	List<DOWQuery> getParameter(DOWQuery dowData);

	String getTimeCount(DOWQuery dowData);

	TimeCount getTimeCountData(DOWQuery dowData);

	String save(TimeCount timecount);

	String updata(TimeCount timecount);

	List<List<AllClzIncome>> getTableData(DOWQuery dowData);

	IncomeCountTime getDateAndCost(IncomeCountTime countTime);

	void updataParameter(IncomeCountTime countTime);

	void saveParameter(IncomeCountTime countTime);

	List<IncomeCount> getDataTable(IncomeCountTime countTime);
	
	Map<String,Object> getMonthData(MonthSalesPlanQuery query);
	
	Map<String,Object> saveMonthParam(MonthParameterQuery query);
	
	Map<String,Object> getMonthSalePlanParam(MonthSalesPlanQuery query);
	
	Map<String,Object> saveSalePlanData(SalePlanData data);
	
	Map<String,Object> deleteSalePlanData(Integer seasonId, String fltNbr);
	
	Map<String,Object> getCabinSeatSetData(MonthSalesPlanQuery query);
	
	Map<String,Object> saveCabinSeatSetData(CabinSeatSetQuery query);
	
	Map<String,Object> deleteCabinSeatSetData(Integer seasonId, String fltNbr);
	
	Map<String,Object> getCurrentFlightSalePlan(MonthSalesPlanQuery query);

	DOWQuery getAll_Itia(DOWQuery dowData);

	List<Z_Airdata> getAll_Para_Data(String lcl_Dpt_Day, String airline1, String airline2);
	
	/**
	 * 查询历史运营统计数据
	 * @Title: getAirportHistroyData 
	 * @Description:  
	 * @param @param airLineQueryNew
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	Map<String,Object> getAirportHistroyData(AirLineQueryNew airLineQueryNew);
	/**
	 * 得到最新有数据的日期
	 * @Title: getNewDate 
	 * @Description:  
	 * @param @param airLineQueryNew
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getNewDate(AirLineQueryNew airLineQueryNew);
	Map<String,Object> getCurrentFltData(String startDate, String endDate, String itia);
}
