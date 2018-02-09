package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class TradeNegotiation implements Serializable {
    private Long id;

    private Long employeeId;

    private String text;

    private Long responseId;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

	@Override
	public String toString() {
		return "TradeNegotiation [id=" + id + ", employeeId=" + employeeId
				+ ", text=" + text + ", responseId=" + responseId + "]";
	}
}