package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class Chat implements Serializable {
	private long id;
	private Long fromNameId;
	private Long toNameId;
	private Long demandId;
	private String date;
	private String text;
	private String textType;
	private String title;
	private String state;
	private String demandState;
	private String demandType;
	private Long demandEmployeeId;
	
	public Long getDemandEmployeeId() {
		return demandEmployeeId;
	}
	public void setDemandEmployeeId(Long demandEmployeeId) {
		this.demandEmployeeId = demandEmployeeId;
	}
	public String getDemandState() {
		return demandState;
	}
	public void setDemandState(String demandState) {
		this.demandState = demandState;
	}
	public String getDemandType() {
		return demandType;
	}
	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTextType() {
		return textType;
	}
	public void setTextType(String textType) {
		this.textType = textType;
	}
	public Long getFromNameId() {
		return fromNameId;
	}
	public void setFromNameId(Long fromNameId) {
		this.fromNameId = fromNameId;
	}
	public Long getToNameId() {
		return toNameId;
	}
	public void setToNameId(Long toNameId) {
		this.toNameId = toNameId;
	}
	public Long getDemandId() {
		return demandId;
	}
	public void setDemandId(Long demandId) {
		this.demandId = demandId;
	}
	@Override
	public String toString() {
		return "Chat [id=" + id + ", fromNameId=" + fromNameId + ", toNameId=" + toNameId + ", demandId=" + demandId
				+ ", date=" + date + ", text=" + text + ", textType=" + textType + ", state=" + state + "]";
	}
	
	
}
