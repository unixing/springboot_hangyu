package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class PersonalCenter implements Serializable {
    private Long id;

    private Long employeeId;

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

	@Override
	public String toString() {
		return "PersonalCenter [id=" + id + ", employeeId=" + employeeId + "]";
	}
}