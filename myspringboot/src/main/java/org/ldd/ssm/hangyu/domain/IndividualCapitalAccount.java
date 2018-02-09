package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class IndividualCapitalAccount implements Serializable {
    private Long id;//账户id

    private Long employeeId;//用户id

    private String number;//可提现资金
    
    private String freezeNumber;//冻结资金
    
    private String totalNumber;//总资金= 可提现资金 + 冻结资金
    
    private String withdrawNumber;//用户欲提现金额

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }
    
	public String getFreezeNumber() {
		return freezeNumber;
	}

	public void setFreezeNumber(String freezeNumber) {
		this.freezeNumber = freezeNumber;
	}

	public String getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(String totalNumber) {
		this.totalNumber = totalNumber;
	}
	
	public String getWithdrawNumber() {
		return withdrawNumber;
	}

	public void setWithdrawNumber(String withdrawNumber) {
		this.withdrawNumber = withdrawNumber;
	}

	@Override
	public String toString() {
		return "IndividualCapitalAccount [id=" + id + ", employeeId="
				+ employeeId + ", number=" + number + ", freezeNumber="
				+ freezeNumber + ", totalNumber=" + totalNumber
				+ ", withdrawNumber=" + withdrawNumber + "]";
	}

	

}