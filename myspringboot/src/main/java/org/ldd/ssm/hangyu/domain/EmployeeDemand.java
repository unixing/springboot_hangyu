package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class EmployeeDemand implements Serializable {
    private Long demandId;

    private Long employeeId;

    public Long getDemandId() {
        return demandId;
    }

    public void setDemandId(Long demandId) {
        this.demandId = demandId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

	@Override
	public String toString() {
		return "EmployeeDemand [demandId=" + demandId + ", employeeId="
				+ employeeId + "]";
	}
}