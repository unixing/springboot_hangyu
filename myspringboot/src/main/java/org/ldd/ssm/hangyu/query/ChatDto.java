package org.ldd.ssm.hangyu.query;

import java.io.Serializable;

public class ChatDto implements Serializable {
	private long id;
	private Long fromNameId;
	private Long toNameId;
	private Long demandId;
	private String date;
	private String text;
	private String textType;//(0:普通聊天内容,1修改提示)
	private String state;//0 已读  1未读',
	private String chatFlag;//fromNameId()+"-"+toNameId()+"-"+demandId()
	//private String demandType;
	
	private int startIndex;//查询起始下标
	
	private int pageNo;//分页查询每页显示条数
	
	private int page;//查询第几页数据
	
	/*public String getDemandType() {
		return demandType;
	}
	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}
*/
	public int getStartIndex() {
		return startIndex;
	}
	public String getChatFlag() {
		return chatFlag;
	}
	public void setChatFlag(String chatFlag) {
		this.chatFlag = chatFlag;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
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
