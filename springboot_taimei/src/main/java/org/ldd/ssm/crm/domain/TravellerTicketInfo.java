package org.ldd.ssm.crm.domain;

import java.math.BigDecimal;

/**
 * 旅客票面信息
 * @author wxm
 *
 * @date 2017-3-7
 */
public class TravellerTicketInfo {
	
	private String carrier;//承运人
	private String flightDate;//航班日期
	private String flightNum;//航班号
	private String leg;//航段
	private String companyName;//公司
	private String ticketNum;//票号
	private String penType;//旅客类型
	private String frtSpe;//舱位
	private BigDecimal ticketPri;//票款
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	public String getFlightNum() {
		return flightNum;
	}
	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}
	public String getLeg() {
		return leg;
	}
	public void setLeg(String leg) {
		this.leg = leg;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
	}
	public String getPenType() {
		return penType;
	}
	public void setPenType(String penType) {
		this.penType = penType;
	}
	public String getFrtSpe() {
		return frtSpe;
	}
	public void setFrtSpe(String frtSpe) {
		this.frtSpe = frtSpe;
	}
	public BigDecimal getTicketPri() {
		return ticketPri;
	}
	public void setTicketPri(BigDecimal ticketPri) {
		this.ticketPri = ticketPri;
	}
	
	
	
}
