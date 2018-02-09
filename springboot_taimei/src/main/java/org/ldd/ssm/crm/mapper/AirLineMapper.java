package org.ldd.ssm.crm.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.AirData;
import org.ldd.ssm.crm.domain.IncomeCountTime;
import org.ldd.ssm.crm.domain.TimeCount;
import org.ldd.ssm.crm.domain.TimeQuery;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.AirLineQuery;
import org.ldd.ssm.crm.query.AirLineQueryNew;
import org.ldd.ssm.crm.query.DOWQuery;
import org.ldd.ssm.crm.query.MonthSalesPlanQuery;
import org.ldd.ssm.crm.query.SalesReportQuery;

public interface AirLineMapper {

	List<String> getFlt_Nbr(DOWQuery dowData);
	
	List<String> getFlt_Month_Nbr(DOWQuery dowData);
	
	List<String> getMonthFltNum(DOWQuery dowData);

	TimeQuery getTimeCount(DOWQuery dowData);

	TimeCount getTimeCountData(DOWQuery dowData);

	void save(TimeCount timecount);

	void updata(TimeCount timecount);

	Z_Airdata getData(DOWQuery dowQuery);
	
	//修改算法后的计算方式
	List<Z_Airdata> getDataNew(DOWQuery dowQuery);

	Z_Airdata getgrp_Data(DOWQuery dowQuery2);
	
	//修改算法后的计算方式
	List<Z_Airdata> getgrp_DataNew(DOWQuery dowQuery2);

	IncomeCountTime getDateAndCost(IncomeCountTime countTime);

	void updataParameter(IncomeCountTime countTime);

	void saveParameter(IncomeCountTime countTime);

	List<Integer> getCountClz(DOWQuery dowQuery);
	
	List<Z_Airdata> getMonthData(MonthSalesPlanQuery query);
	
	List<Z_Airdata> getMonthData2(MonthSalesPlanQuery query);
	
//	Integer getMonthDataCount(MonthSalesPlanQuery query);
	
	Z_Airdata getMonthPlanParam(@Param("query") MonthSalesPlanQuery query, @Param("startDate") String startDate, @Param("endDate") String endDate);
	
	AirData getCabinSeatSetData(MonthSalesPlanQuery query);
	
	List<Z_Airdata> getCabinSeatSetData2(MonthSalesPlanQuery query);
	
	int getSiteCount(MonthSalesPlanQuery query);
	
	List<DOWQuery> getCheckPara(DOWQuery dowData);

	List<Z_Airdata> getAll_Para_Data(AirLineQuery airLineQuery);
	
	List<Z_Airdata> getYearSalesReportNew(SalesReportQuery salesReportQuery);
	/**
	 * 得到条件下的所有数据
	 * @Title: getZAirDataList 
	 * @Description:  
	 * @param @param airLineQueryNew
	 * @param @return    
	 * @return List<Z_Airdata>     
	 * @throws
	 */
	List<Z_Airdata> getZAirDataList(AirLineQueryNew airLineQueryNew);
	/**
	 * 得到最新有数据的日期
	 * @Title: getNewDate 
	 * @Description:  
	 * @param @param airLineQueryNew
	 * @param @return    
	 * @return Date     
	 * @throws
	 */
	Date getNewDate(AirLineQueryNew airLineQueryNew);
	
	List<Z_Airdata> getCurrtSeasonFltData(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("itia") String itia);
}
