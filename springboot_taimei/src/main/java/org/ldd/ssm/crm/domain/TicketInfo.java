package org.ldd.ssm.crm.domain;

import java.math.BigDecimal;

/**
 * 
 * @author wxm
 *
 * @date 2017-5-19
 */
public class TicketInfo {
	private String flightDate;//航班日期
	private String flightNum;//航班号
	private String leg;//航段
	private String ticketNum;//票号 完整
	private String ticketNumDetail;//票号 有*
	public String getTicketNumDetail() {
		return ticketNumDetail;
	}
	public void setTicketNumDetail(String ticketNumDetail) {
		this.ticketNumDetail = ticketNumDetail;
	}
	private BigDecimal ticketPri;//票款
	private String flag;//0,票价不一样;1,联程  ；
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
	public String getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
	}
	public BigDecimal getTicketPri() {
		return ticketPri;
	}
	public void setTicketPri(BigDecimal ticketPri) {
		this.ticketPri = ticketPri;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public TicketInfo(String flightDate, String flightNum, String leg,
			String ticketNum, BigDecimal ticketPri, String flag) {
		super();
		this.flightDate = flightDate;
		this.flightNum = flightNum;
		this.leg = leg;
		this.ticketNum = ticketNum;
		this.ticketPri = ticketPri;
		this.flag = flag;
	}
	
	public TicketInfo() {
		super();
	}
	@Override
	public int hashCode() {
		String in = flag + flightDate+flightNum+ticketNum+ticketPri;  
		return in.hashCode();  
		/*final int prime = 31;
		int result = 1;
		result = prime * result + ((flag == null) ? 0 : flag.hashCode());
		result = prime * result
				+ ((flightDate == null) ? 0 : flightDate.hashCode());
		result = prime * result
				+ ((flightNum == null) ? 0 : flightNum.hashCode());
		result = prime * result + ((leg == null) ? 0 : leg.hashCode());
		result = prime * result
				+ ((ticketNum == null) ? 0 : ticketNum.hashCode());
		result = prime * result
				+ ((ticketPri == null) ? 0 : ticketPri.hashCode());
		return result;*/
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TicketInfo other = (TicketInfo) obj;
		if (flag == null) {
			if (other.flag != null)
				return false;
		} else if (!flag.equals(other.flag))
			return false;
		if (flightDate == null) {
			if (other.flightDate != null)
				return false;
		} else if (!flightDate.equals(other.flightDate))
			return false;
		if (flightNum == null) {
			if (other.flightNum != null)
				return false;
		} else if (!flightNum.equals(other.flightNum))
			return false;
		if (leg == null) {
			if (other.leg != null)
				return false;
		} else if (!leg.equals(other.leg))
			return false;
		if (ticketNum == null) {
			if (other.ticketNum != null)
				return false;
		} else if (!ticketNum.equals(other.ticketNum))
			return false;
		if (ticketPri == null) {
			if (other.ticketPri != null)
				return false;
		} else if (!ticketPri.equals(other.ticketPri))
			return false;
		return true;
	}
	public boolean equalsExceptPrice(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TicketInfo other = (TicketInfo) obj;
		if (flightDate == null) {
			if (other.flightDate != null)
				return false;
		} else if (!flightDate.equals(other.flightDate))
			return false;
		if (flightNum == null) {
			if (other.flightNum != null)
				return false;
		} else if (!flightNum.equals(other.flightNum))
			return false;
		if (leg == null) {
			if (other.leg != null)
				return false;
		} else if (!leg.equals(other.leg))
			return false;
		if (ticketNum == null) {
			if (other.ticketNum != null)
				return false;
		} else if (!ticketNum.equals(other.ticketNum))
			return false;
		return true;
	}
	
	
}
