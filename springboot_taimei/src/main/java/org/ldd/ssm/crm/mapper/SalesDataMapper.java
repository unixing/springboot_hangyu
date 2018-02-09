package org.ldd.ssm.crm.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.ldd.ssm.crm.domain.FlightSpaceNum;
import org.ldd.ssm.crm.domain.SegmentInfo;
import org.ldd.ssm.crm.domain.SpaceAirData;
import org.ldd.ssm.crm.domain.SpaceInfo;
import org.ldd.ssm.crm.domain.TicketInfo;
import org.ldd.ssm.crm.domain.TravellerTicketInfo;
import org.ldd.ssm.crm.query.SalesDateQuery;

/**
 * 销售数据
 * @author wxm
 *
 * @date 2017-3-8
 */
public interface SalesDataMapper {
	
	//根据时间 航班号获取航线
	public List<String> findFltRteCdList(SalesDateQuery dto);
	
	//获取航段汇总信息 taveller airinfo z_airData
	public List<SegmentInfo> findSegmentInfo(SalesDateQuery dto);
	
	//获取舱位信息
	public List<SpaceInfo> findSpaceInfo(SalesDateQuery dto);
	//获取票面信息
	public List<TravellerTicketInfo> findTravellerTicket(SalesDateQuery dto);
	
	//获取航段汇总信息 z_airData 在traveller没有数据的情况下 用这个方法查汇总数据
	public List<SegmentInfo> findSegmentInfoByAirData(SalesDateQuery dto);
	//z_airdata 获得舱位人数 航段
	public List<SpaceAirData> findSpaceAirData(SalesDateQuery dto);
	//获得每个航司折扣比例对应的舱位号
	public List<FlightSpaceNum> findFlightSpaceNum(String CpyNm);
	
	//导出
	//获得航段集合
	public List<String> findSegmentList(SalesDateQuery dto);
	//根据航段获取票面信息
	public List<TravellerTicketInfo> findTravellerTicketExc(SalesDateQuery dto);
	//排序 分页
	public List<TravellerTicketInfo> findTicketInfo(SalesDateQuery dto);
	
	public String getCurrentTime(SalesDateQuery dto);
	//系统对比票面
	public List<TicketInfo> findTicketInfoList(SalesDateQuery dto);

}
