package org.ldd.ssm.crm.service;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.OutPort;
import org.ldd.ssm.crm.domain.TotalFly;
import org.ldd.ssm.crm.query.TotalFlyAnalysisQuery;

/**
 * 共飞运营分析业务控制层
 * @Title:TotalFlyAnalysisService 
 * @Description:
 * @author taimei-gds 
 * @date 2016-4-7 下午3:15:07
 */
public interface TotalFlyAnalysisService {
	/**
	 * 获得条件下的流量数据
	 * @Title: getFlowCountData 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return List<List<OutPort>>     
	 * @throws
	 */
	public List<List<OutPort>> getFlowCountData(TotalFlyAnalysisQuery totalFlyAnalysisQuery);
	/**
	 * 获得均班客量
	 * @Title: getAllAmountData 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return List<List<EvenPort>>     
	 * @throws
	 */
	public List<List<EvenPort>> getAllAmountData(TotalFlyAnalysisQuery totalFlyAnalysisQuery);
	/**
	 * 航班量排行
	 * @Title: getFlightNumData 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return List<List<TotalFly>>     
	 * @throws
	 */
	public List<List<TotalFly>> getFlightNumData(TotalFlyAnalysisQuery totalFlyAnalysisQuery);
	/**
	 * 座收排行
	 * @Title: getRaskRankingData 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return List<List<TotalFly>>     
	 * @throws
	 */
	public List<List<TotalFly>> getRaskRankingData(TotalFlyAnalysisQuery totalFlyAnalysisQuery);
	/**
	 * 客量排行
	 * @Title: getPassengerRankData 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return List<List<TotalFly>>     
	 * @throws
	 */
	public List<List<TotalFly>> getPassengerRankData(TotalFlyAnalysisQuery totalFlyAnalysisQuery);
	/**
	 * 均班客量排行
	 * @Title: getAllClassRankData 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return List<List<TotalFly>>     
	 * @throws
	 */
	public List<List<TotalFly>> getAllClassRankData(TotalFlyAnalysisQuery totalFlyAnalysisQuery);
	/**
	 * 共飞分析所有数据
	 * @Title: getTotalDataNew 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return Map<String, Object>     
	 * @throws
	 */
	public Map<String, Object> getTotalDataNew(TotalFlyAnalysisQuery totalFlyAnalysisQuery);
	/**
	 * 得到最新有数据的日期
	 * @Title: getNewDate 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getNewDate(TotalFlyAnalysisQuery totalFlyAnalysisQuery);
}
