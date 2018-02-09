package org.ldd.ssm.crm.service;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.SalesDataFlightInfo;
import org.ldd.ssm.crm.domain.TicketInfo;
import org.ldd.ssm.crm.domain.TravellerTicketInfo;
import org.ldd.ssm.crm.query.SalesDateQuery;
import org.ldd.ssm.crm.query.TicketInfoCompare;

public interface ISalesDataService {
	
	public List<String> findFltRteCdList(SalesDateQuery dto);
	
	public Map<String,Object> findSalesData(SalesDateQuery dto);
	
	public SalesDataFlightInfo findSalesFlightData(SalesDateQuery dto);
	
	public List<String> findSegmentList(SalesDateQuery dto);
	//根据航段获取票面信息
	public List<TravellerTicketInfo> findTravellerTicketExc(SalesDateQuery dto);
	
	//票面信息排序
	public List<TravellerTicketInfo> findTicketInfo(SalesDateQuery dto);
	
	public String getCurrentTime(SalesDateQuery dto);
	
	//查询系统飘渺信息列表
	public List<TicketInfo> findTicketInfoList(SalesDateQuery dto);
	//查询出差异数据
	public List<TicketInfoCompare> findTicketInfoCompareList(List<TicketInfo> listUpload, List<TicketInfo> listSystem);

}
