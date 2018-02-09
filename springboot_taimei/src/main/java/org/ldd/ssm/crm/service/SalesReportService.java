package org.ldd.ssm.crm.service;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.FltDyDetial;
import org.ldd.ssm.crm.domain.OtherSalesReport;
import org.ldd.ssm.crm.domain.SalesReport;
import org.ldd.ssm.crm.domain.SalesReportDayInfo;
import org.ldd.ssm.crm.domain.YearSalesReport;
import org.ldd.ssm.crm.query.FlyNum;
import org.ldd.ssm.crm.query.SalesReportQuery;

/**
 * 销售报表service
 * @Title:SalesReportService 
 * @Description:
 * @author taimei-gds 
 * @date 2016-4-20 上午10:22:31
 */
public interface SalesReportService {
	/**
	 * 得到日报数据
	 * @Title: getDailyReportList 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<SalesReport>     
	 * @throws
	 */
	public List<SalesReport> getDailyReportList(SalesReportQuery salesReportQuery);
	/**
	 * 周报
	 * @Title: getWeeklyReportData 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,List<OtherSalesReport>>     
	 * @throws
	 */
	public Map<String,List<OtherSalesReport>> getWeeklyReportData(SalesReportQuery salesReportQuery);
	/**
	 * 获得月报
	 * @Title: getMonthlyReportData 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<OtherSalesReport>     
	 * @throws
	 */
	public List<OtherSalesReport> getMonthlyReportData(SalesReportQuery salesReportQuery);
	/**
	 * 航季报
	 * @Title: getHalfYearReportData 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<List<YearSalesReport>>     
	 * @throws
	 */
	public List<List<YearSalesReport>> getHalfYearReportData(SalesReportQuery salesReportQuery);
	/**
	 * 年报
	 * @Title: getYearlyReportData 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<List<YearSalesReport>>    
	 * @throws
	 */
	public List<List<YearSalesReport>> getYearlyReportData(SalesReportQuery salesReportQuery);
	/**
	 * 得到航班号
	 * @Title: getHbh 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	public List<FlyNum> getHbh(SalesReportQuery salesReportQuery);
	/**
	 * 得到最新数据的时间
	 * @Title: getNewDate 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getNewDate(SalesReportQuery salesReportQuery);
	/**
	 * 根据航班号是否为奇数来确定去和回
	 * @Title: getSalesReportQueryChange 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return SalesReportQuery     
	 * @throws
	 */
	public SalesReportQuery getSalesReportQueryChange(SalesReportQuery salesReportQuery);
	public SalesReportQuery getSalesReportQueryChangeDay(SalesReportQuery salesReportQuery);
	/**
	 * 根据条件查询日期列表
	 * @Title: getSalesReportQueryChange 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return SalesReportQuery     
	 * @throws
	 */
	public Map<String,Object> getDates(SalesReportQuery salesReportQuery);
	/**
	 * 根据条件查询月份列表
	 * @Title: getSalesReportQueryChange 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return SalesReportQuery     
	 * @throws
	 */
	public Map<String,Object> getMonths(SalesReportQuery salesReportQuery);
	public Map<String,Object> getSeasons(SalesReportQuery salesReportQuery);
	public Map<String,Object> getYears(SalesReportQuery salesReportQuery);
	/**
	 * （日报）
	 * @Title: getDailyReportIncomeInfo 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,SalesReport>     
	 * @throws
	 */
	public  Map<String,Object> getDailyReportIncomeInfo(SalesReportQuery salesReportQuery);
	/**
	 * 获得销售日报每段数据集合
	 * @Title: getDailyReportIncomeInfo_New 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String ,Object>     
	 * @throws
	 */
	public Map<String ,Object> getDailyReportIncomeInfo_New(SalesReportQuery salesReportQuery);
	/**
	 * 周报
	 * @Title: getWeekReportIncomeInfo 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	public  Map<String,Object> getWeekReportIncomeInfo(SalesReportQuery salesReportQuery);
	/**
	 * 销售周报分航段数据集合
	 * @Title: getWeekReportIncomeInfo_New 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	public  Map<String,Object> getWeekReportIncomeInfo_New(SalesReportQuery salesReportQuery);
	/**
	 * 月报
	 * @Title: getMonthReportIncomeInfo 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	public  Map<String,Object> getMonthReportIncomeInfo(SalesReportQuery salesReportQuery);
	/**
	 * 销售月报分段数据集合
	 * @Title: getMonthReportIncomeInfo_New 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	public  Map<String,Object> getMonthReportIncomeInfo_New(SalesReportQuery salesReportQuery);
	/**
	 * 航季报
	 * @Title: getHalfYearReportIncomeInfo 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	public  Map<String,Object> getHalfYearReportIncomeInfo(SalesReportQuery salesReportQuery);
	/**
	 * 销售航季报分段数据集合
	 * @Title: getHalfYearReportIncomeInfo_New 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	public  Map<String,Object> getHalfYearReportIncomeInfo_New(SalesReportQuery salesReportQuery);
	
	/**
	 * 年报
	 * @Title: getYearReportIncomeInfo 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	public  Map<String,Object> getYearReportIncomeInfo(SalesReportQuery salesReportQuery);
	/**
	 * 销售年报分段数据集合
	 * @Title: getYearReportIncomeInfo_New 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	public  Map<String,Object> getYearReportIncomeInfo_New(SalesReportQuery salesReportQuery);
	/**
	 * 得到段时间内有数据的日期
	 * @Title: getHavingDataDayList 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	public List<String> getHavingDataDayList(SalesReportQuery salesReportQuery);
	/**
	 * 得到计划班次
	 * @Title: getPlanClass 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getPlanClass(SalesReportQuery salesReportQuery);
	/**
	 * 得到实际班次
	 * @Title: getExecutiveClass 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getExecutiveClass(SalesReportQuery salesReportQuery);
	/**
	 * 根据航班号，飞行时间等查询航班动态
	 * @Title: getFltDyDetialList 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @param datesList
	 * @param @return    
	 * @return List<FltDyDetial>     
	 * @throws
	 */
	public List<FltDyDetial> getFltDyDetialList(SalesReportQuery salesReportQuery, List<String> datesList);
}
