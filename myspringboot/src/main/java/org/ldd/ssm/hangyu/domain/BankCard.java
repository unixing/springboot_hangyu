package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class BankCard implements Serializable {
	
	private Long id;
	
	private String cardPerson;//开户人
	
	private String cardName;//开户银行名
	
	private String cardNumber;//开户号
	
	private Long employeeId;//所属用户id

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardPerson() {
		return cardPerson;
	}

	public void setCardPerson(String cardPerson) {
		this.cardPerson = cardPerson;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String toString() {
		return "BankCard [id=" + id + ", cardPerson=" + cardPerson
				+ ", cardName=" + cardName + ", cardNumber=" + cardNumber
				+ ", employeeId=" + employeeId + "]";
	}

	
}
