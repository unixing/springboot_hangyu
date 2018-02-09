package org.ldd.ssm.crm.mapper;

import java.util.ArrayList;
import java.util.List;

import org.ldd.ssm.crm.domain.GustStrate;
import org.ldd.ssm.crm.query.BuyTicketReportQuery;

/**
 * 上客进度表对应mapper
 * @Title:BuyTicketReportMapper 
 * @Description:
 * @author taimei-gds 
 * @date 2016-5-19 上午11:58:34
 */
public interface BuyTicketReportMapper {

	public List<GustStrate> getHuangduan(BuyTicketReportQuery buyTicketReportQuery);
	public List<GustStrate> getGustStrateDataNew(BuyTicketReportQuery buyTicketReportQuery);
	public List<GustStrate> getGutDteData(BuyTicketReportQuery buyTicketReportQuery);
	public List<GustStrate> getGustStrateDataNew2(BuyTicketReportQuery buyTicketReportQuery);
	public List<String> getfltEctByFlyNum(BuyTicketReportQuery buyTicketReportQuery);
	public List<String> getAirLine(BuyTicketReportQuery buyTicketReportQuery);
	public String getNewDate(BuyTicketReportQuery buyTicketReportQuery);
	/**
	 * 判断是否某个日期内的航班号是否有销售动态
	 * @Title: getIshavingData 
	 * @Description:  
	 * @param @param buyTicketReportQuery
	 * @param @return    
	 * @return List<String>     
	 * @throws
	 */
	public List<String> getIshavingData(BuyTicketReportQuery buyTicketReportQuery);
	public List<String> getAirLineByFlyNbrs(List<String> list);
}
