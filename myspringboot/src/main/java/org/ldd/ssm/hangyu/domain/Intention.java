package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class Intention implements Serializable {
    private Long id;

    private Long employeeId;

    private Long demandId;

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

    public Long getDemandId() {
        return demandId;
    }

    public void setDemandId(Long demandId) {
        this.demandId = demandId;
    }

	@Override
	public String toString() {
		return "Intention [id=" + id + ", employeeId=" + employeeId
				+ ", demandId=" + demandId + "]";
	}
}