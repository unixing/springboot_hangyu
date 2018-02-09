package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class FinancialAccounting implements Serializable {
    private Long id;

    private Long ordersandbillId;

    private Long responseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrdersandbillId() {
        return ordersandbillId;
    }

    public void setOrdersandbillId(Long ordersandbillId) {
        this.ordersandbillId = ordersandbillId;
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

	@Override
	public String toString() {
		return "FinancialAccounting [id=" + id + ", ordersandbillId="
				+ ordersandbillId + ", responseId=" + responseId + "]";
	}
}