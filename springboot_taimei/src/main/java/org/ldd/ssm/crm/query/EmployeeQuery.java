package org.ldd.ssm.crm.query;

import java.util.Date;


public class EmployeeQuery extends BaseQuery{
	
	private Integer status;
	
	private Date inputTimeStart;
	
	private Date inputTimeEnd;
	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getInputTimeStart() {
		return inputTimeStart;
	}

	public void setInputTimeStart(Date inputTimeStart) {
		this.inputTimeStart = inputTimeStart;
	}

	public Date getInputTimeEnd() {
		return inputTimeEnd;
	}

	public void setInputTimeEnd(Date inputTimeEnd) {
		this.inputTimeEnd = inputTimeEnd;
	}

	@Override
	public String toString() {
		return "EmployeeQuery [status=" + status + ", inputTimeStart="
				+ inputTimeStart + ", inputTimeEnd=" + inputTimeEnd
				+ ", getStatus()=" + getStatus() + ", getInputTimeStart()="
				+ getInputTimeStart() + ", getInputTimeEnd()="
				+ getInputTimeEnd() + "]";
	}
	
	
}
