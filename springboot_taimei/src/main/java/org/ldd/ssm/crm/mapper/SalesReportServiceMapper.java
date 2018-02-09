package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.ldd.ssm.crm.domain.OtherSalesReport;
import org.ldd.ssm.crm.domain.SalesReport;
import org.ldd.ssm.crm.domain.YearSalesReport;
import org.ldd.ssm.crm.domain.Yesterday_ZD;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.FlyNum;
import org.ldd.ssm.crm.query.SalesReportQuery;

/**
 * 销售报表mapper
 * @Title:SalesReportServiceMapper 
 * @Description:
 * @author taimei-gds 
 * @date 2016-4-20 上午10:25:08
 */
public interface SalesReportServiceMapper {
	/**
	 * 得到日报
	 * @Title: getDailyReportList 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return SalesReport     
	 * @throws
	 */
	SalesReport getDailyReportList(SalesReportQuery salesReportQuery);
	List<SalesReport> getDailyReportListNew(SalesReportQuery salesReportQuery);
	SalesReport getAllTime(SalesReportQuery salesReportQuery);
	SalesReport getAllButie(SalesReportQuery salesReportQuery);
	List<SalesReport> getAllButieByDate(SalesReportQuery salesReportQuery);
	SalesReport getDataByFlyNum(SalesReportQuery salesReportQuery);
	//速度优化新方法
	List<SalesReport> getDataByFlyNumList(SalesReportQuery salesReportQuery);
	/**
	 * 得到航班号
	 * @Title: getHbh 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	List<FlyNum> getHbh(SalesReportQuery salesReportQuery);
	List<String> getHbh2(SalesReportQuery salesReportQuery);
	List<String> getHbh3(SalesReportQuery salesReportQuery);
	List<Z_Airdata> getHbhAll(SalesReportQuery salesReportQuery);
	List<String> getHbhSingle(SalesReportQuery salesReportQuery);
	List<String> getHbhSingleday(SalesReportQuery salesReportQuery);
	/**
	 * 得到周报、月报
	 * @Title: getOtherSalesReport 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return OtherSalesReport     
	 * @throws
	 */
	OtherSalesReport getOtherSalesReport(SalesReportQuery salesReportQuery);
	/**
	 * 得到年报
	 * @Title: getYearSalesReport 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return YearSalesReport     
	 * @throws
	 */
	YearSalesReport getYearSalesReport(SalesReportQuery salesReportQuery);
	
	List<Z_Airdata> getYearSalesReportNew(SalesReportQuery salesReportQuery);
	/**
	 * 得到季报根据具体日期段
	 * @Title: getYearSalesReportByDay 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return YearSalesReport     
	 * @throws
	 */
	YearSalesReport getYearSalesReportByDay(SalesReportQuery salesReportQuery);
	/**
	 * 得到季报根据年月
	 * @Title: getYearSalesReportByMonth 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return YearSalesReport     
	 * @throws
	 */
	YearSalesReport getYearSalesReportByMonth(SalesReportQuery salesReportQuery);
	//得到某段时间的数据
	List<Z_Airdata> getSalesReportsByDate(SalesReportQuery salesReportQuery);
	
	//得到月的直飞的座公里收入和平均客座率
	YearSalesReport getZglsrAndPjzklByMonthByzhifei(SalesReportQuery salesReportQuery);
	//得到月的经停的座公里收入和平均客座率
	YearSalesReport getZglsrAndPjzklByMonthByjingting(SalesReportQuery salesReportQuery);
	//得到日的直飞的座公里收入和平均客座率
	YearSalesReport getZglsrAndPjzklByDayByzhifei(SalesReportQuery salesReportQuery);
	//得到日的经停的座公里收入和平均客座率
	YearSalesReport getZglsrAndPjzklByDayByjingting(SalesReportQuery salesReportQuery);
	//得到一段时间的经停的座公里收入和平均客座率
	YearSalesReport getZglsrAndPjzklByDateByjingting(SalesReportQuery salesReportQuery);
	
	public String getNewDate(SalesReportQuery salesReportQuery);
	/**
	 * 查询有数据的天数（7天）
	 * @Title: getDaysIncomeInfo 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	List<String> getDaysIncomeInfo(SalesReportQuery salesReportQuery);
	/**
	 * 得到某段时间有数据的日期
	 * @Title: getWeekIncomeInfo 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	List<String> getWeekIncomeInfo(SalesReportQuery salesReportQuery);
	/**
	 * 得到段时间内有数据的日期
	 * @Title: getHavingDataDayList 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	List<String> getHavingDataDayList(SalesReportQuery salesReportQuery);
	
	/**
	 * 得到一个航班号计划班次
	 * @Title: getPlanClass 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public List<String> getPlanClass(SalesReportQuery salesReportQuery);
	
	/**
	 * 得到某段时间有数据的日期--单一航班号
	 * @Title: getWeekIncomeInfo 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	List<String> getHavingDataDayListBySingleFlyNum(SalesReportQuery salesReportQuery);
	/**
	 * 根据条件查询航线有数据的日期
	 * @Title: getWeekIncomeInfo 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	List<Integer> getDates(SalesReportQuery salesReportQuery);
	/**
	 * 根据条件查询航线有数据的最近月份
	 * @Title: getWeekIncomeInfo 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	String getNewestMonth(SalesReportQuery salesReportQuery);
	/**
	 * 根据条件查询航线有数据的月份
	 * @Title: getWeekIncomeInfo 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	List<Integer> getMonths(SalesReportQuery salesReportQuery);
	List<String> getYears(SalesReportQuery salesReportQuery);
	List<String> getDateByHJ(SalesReportQuery salesReportQuery);
	/**
	 * 根据条件查询航线有数据的最近年份
	 * @Title: getWeekIncomeInfo 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	String getNewestYear(SalesReportQuery salesReportQuery);
	/**
	 * 查询该航班号是否为自营航班
	 * @Title: getOurSelfAirLineList 
	 * @Description:  
	 * @param @param flyNbr
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	String getOurSelfAirLineList(String flyNbr);
	/**
	 * 获得航班动态详细数据
	 * @Title: getYesterday_ZDList 
	 * @Description:  
	 * @param @param salesReportQuery
	 * @param @return    
	 * @return List<Yesterday_ZD>     
	 * @throws
	 */
	List<Yesterday_ZD> getYesterday_ZDList(SalesReportQuery salesReportQuery);
}
