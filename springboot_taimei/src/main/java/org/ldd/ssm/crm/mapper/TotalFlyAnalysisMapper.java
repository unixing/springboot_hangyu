/**   
 * @Title: TotalFlyAnalysisMapper.java 
 * @Package org.ldd.ssm.crm.mapper 
 * @Description: TODO
 * @author taimei-gds   
 * @date 2016-4-7 下午3:16:44 
 * @version V1.0   
 */ 
package org.ldd.ssm.crm.mapper;



import java.util.Date;
import java.util.List;

import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.OutPort;
import org.ldd.ssm.crm.domain.TotalFly;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.TotalFlyAnalysisQuery;

/**
 * @Title:TotalFlyAnalysisMapper 
 * @Description:
 * @author taimei-gds 
 * @date 2016-4-7 下午3:16:44
 */
public interface TotalFlyAnalysisMapper {
	public List<OutPort> getFlowCountData(TotalFlyAnalysisQuery totalFlyAnalysisQuery);
	public List<EvenPort> getAllAmountData(TotalFlyAnalysisQuery totalFlyAnalysisQuery);
	public List<TotalFly> getFlightNumData(TotalFlyAnalysisQuery totalFlyAnalysisQuery);
	public TotalFly getPJFlightNumData(TotalFlyAnalysisQuery totalFlyAnalysisQuery);
	public List<TotalFly> getRaskRankingData(TotalFlyAnalysisQuery totalFlyAnalysisQuery) ;
	public TotalFly getPJRaskRankingData(TotalFlyAnalysisQuery totalFlyAnalysisQuery) ;
	public List<TotalFly> getPassengerRankData(TotalFlyAnalysisQuery totalFlyAnalysisQuery) ;
	public TotalFly getPJPassengerRankData(TotalFlyAnalysisQuery totalFlyAnalysisQuery) ;
	public List<TotalFly> getAllClassRankData(TotalFlyAnalysisQuery totalFlyAnalysisQuery) ;
	
	public TotalFly getPJAllClassRankData(TotalFlyAnalysisQuery totalFlyAnalysisQuery) ;
	/**
	 * 获得所有数据
	 * @Title: getTotalFlyDataNewList 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return List<Z_Airdata>     
	 * @throws
	 */
	public List<Z_Airdata> getTotalFlyDataNewList(TotalFlyAnalysisQuery totalFlyAnalysisQuery) ;
	/**
	 * 得到最新有数据的日期
	 * @Title: getNewDate 
	 * @Description:  
	 * @param @param totalFlyAnalysisQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public Date getNewDate(TotalFlyAnalysisQuery totalFlyAnalysisQuery);
}
