package org.ldd.ssm.crm.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * 航段信息
 * @author wxm
 *
 * @date 2017-3-7
 */
public class SegmentInfo {
	
	private String leg;//航段号
	private BigDecimal avgDct;//平均折扣(yb运价*容量/票面和)
	private BigDecimal ticketPriTotal;//票面和（元）
	private long personNum;//容量(人)
	private BigDecimal ticketPriAvg;//平均票价（票面和/容量）
	private List<SpaceInfo> spaceInfoList;//舱位对象集合
	private List<TravellerTicketInfo> travellerTicketInfoList;//旅客票面信息集合
	boolean flag=true;//标识该航段是否有数据  true为有数据
	public String getLeg() {
		return leg;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public void setLeg(String leg) {
		this.leg = leg;
	}
	public BigDecimal getAvgDct() {
		return avgDct;
	}
	public void setAvgDct(BigDecimal avgDct) {
		this.avgDct = avgDct;
	}
	
	public BigDecimal getTicketPriTotal() {
		return ticketPriTotal;
	}
	public void setTicketPriTotal(BigDecimal ticketPriTotal) {
		this.ticketPriTotal = ticketPriTotal;
	}
	
	public long getPersonNum() {
		return personNum;
	}
	public void setPersonNum(long personNum) {
		this.personNum = personNum;
	}
	public BigDecimal getTicketPriAvg() {
		return ticketPriAvg;
	}
	public void setTicketPriAvg(BigDecimal ticketPriAvg) {
		this.ticketPriAvg = ticketPriAvg;
	}
	public List<SpaceInfo> getSpaceInfoList() {
		return spaceInfoList;
	}
	public void setSpaceInfoList(List<SpaceInfo> spaceInfoList) {
		this.spaceInfoList = spaceInfoList;
	}
	public List<TravellerTicketInfo> getTravellerTicketInfoList() {
		return travellerTicketInfoList;
	}
	public void setTravellerTicketInfoList(
			List<TravellerTicketInfo> travellerTicketInfoList) {
		this.travellerTicketInfoList = travellerTicketInfoList;
	}
	
}
