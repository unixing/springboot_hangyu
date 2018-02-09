package org.ldd.ssm.crm.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * 销售数据航班信息
 * @author wxm
 *
 * @date 2017-3-7
 */
public class SalesDataFlightInfo {
	
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String fightNum;//航班号
	private BigDecimal avgDct;//平均折扣
	private long personNum;//容量(人)
	private BigDecimal ticketPri;//票面和（元）
	private List<SegmentInfo> segmentInfoList;//航段对象集合
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getFightNum() {
		return fightNum;
	}
	public void setFightNum(String fightNum) {
		this.fightNum = fightNum;
	}
	public BigDecimal getAvgDct() {
		return avgDct;
	}
	public void setAvgDct(BigDecimal avgDct) {
		this.avgDct = avgDct;
	}
	
	public long getPersonNum() {
		return personNum;
	}
	public void setPersonNum(long personNum) {
		this.personNum = personNum;
	}
	public BigDecimal getTicketPri() {
		return ticketPri;
	}
	public void setTicketPri(BigDecimal ticketPri) {
		this.ticketPri = ticketPri;
	}
	public List<SegmentInfo> getSegmentInfoList() {
		return segmentInfoList;
	}
	public void setSegmentInfoList(List<SegmentInfo> segmentInfoList) {
		this.segmentInfoList = segmentInfoList;
	}
	
	
}
