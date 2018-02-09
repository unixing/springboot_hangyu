package org.ldd.ssm.crm.domain;

import java.math.BigDecimal;

/**
 * 航班舱位信息
 * @author wxm
 *
 * @date 2017-3-7
 */
public class SpaceInfo  {
	
	private String leg;//航段
	private String FrtSpe;//舱位
	private int personNum;//人数
	private BigDecimal ticketPri;//收入
	
	public String getLeg() {
		return leg;
	}
	public void setLeg(String leg) {
		this.leg = leg;
	}
	public String getFrtSpe() {
		return FrtSpe;
	}
	public void setFrtSpe(String frtSpe) {
		FrtSpe = frtSpe;
	}
	public int getPersonNum() {
		return personNum;
	}
	public void setPersonNum(int personNum) {
		this.personNum = personNum;
	}
	public BigDecimal getTicketPri() {
		return ticketPri;
	}
	public void setTicketPri(BigDecimal ticketPri) {
		this.ticketPri = ticketPri;
	}
	
}
