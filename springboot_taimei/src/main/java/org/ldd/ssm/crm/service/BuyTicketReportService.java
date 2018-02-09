package org.ldd.ssm.crm.service;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.BuyTicketReport;
import org.ldd.ssm.crm.query.BuyTicketReportQuery;



/**
 * 上客进度表接口
 * @Title:BuyTicketReportService 
 * @Description:
 * @author taimei-gds 
 * @date 2016-5-19 下午12:00:44
 */
public interface BuyTicketReportService {
	/**
	 * 得到上客进度表数据
	 * @Title: getBuyTicketReportData 
	 * @Description:  
	 * @param @param buyTicketReportQuery
	 * @param @return    
	 * @return Map<String,List<BuyTicketReport>>     
	 * @throws
	 */
	public Map<String,List<BuyTicketReport>> getBuyTicketReportData(BuyTicketReportQuery buyTicketReportQuery);
	/**
	 * 得到对应的航段
	 * @Title: getBuyTicketReportLine 
	 * @Description:  
	 * @param @param buyTicketReportQuery
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	public List<String> getBuyTicketReportLine(BuyTicketReportQuery buyTicketReportQuery);
	/**
	 * 得到某个航班号下对应的航线
	 * @Title: getAirLine 
	 * @Description:  
	 * @param @param buyTicketReportQuery
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	public List<String> getAirLine(BuyTicketReportQuery buyTicketReportQuery);
	/**
	 * 得到有数据的最新日期
	 * @Title: getBuyTicketReportLineDataNewDate 
	 * @Description:  
	 * @param @param buyTicketReportQuery
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String getBuyTicketReportLineDataNewDate(BuyTicketReportQuery buyTicketReportQuery);
	
	public Map<String,Object> getBuyTicketReportLineNew(BuyTicketReportQuery buyTicketReportQuery);
	/**
	 * 得到航线
	 * @Title: getBuyTicketReportLineData 
	 * @Description:  
	 * @param @param buyTicketReportQuery
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	public Map<String,Object> getBuyTicketReportLineData(BuyTicketReportQuery buyTicketReportQuery, Map<String, Object> flyListMap);
	/**
	 * 计算一个单程的航班的客座和人数
	 * @Title: getBuyTicketReportSingleLineData 
	 * @Description:  
	 * @param @param flyList
	 * @param @param retMap
	 * @param @return    
	 * @return Map<String,Object>     
	 * @throws
	 */
	public Map<String,Object> getBuyTicketReportSingleLineData(List<String> flyList, Map<String, Object> retMap);
}
