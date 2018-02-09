package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class OrdersAndBill implements Serializable {
    private Long id;

    private Long employeeId;

    private String ordersstate;

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

    public String getOrdersstate() {
        return ordersstate;
    }

    public void setOrdersstate(String ordersstate) {
        this.ordersstate = ordersstate == null ? null : ordersstate.trim();
    }

	@Override
	public String toString() {
		return "OrdersAndBill [id=" + id + ", employeeId=" + employeeId
				+ ", ordersstate=" + ordersstate + "]";
	}
}