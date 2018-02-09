package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class DemandSearch implements Serializable {

	private Long employeeId;//登陆用户id
	
	private String itia;//机场三字码
	
	private String roleId; //0.航司 1.机场 2.太美
	
	private String demandType; //需求种类：0-航线需求 1-运力投放 2-运营托管
	
	private int startIndex;//查询起始下标
	
	private int pageNo;//分页查询每页显示条数
	
	private String itiaType;//0-机场三字码         2- 城市三字码
	
	private int page;//查询第几页数据
	
	private int type;//1-查询当前用户在该机场发布的需求（运力或航线）0-查询当前机场的所有需求

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getItia() {
		return itia;
	}

	public void setItia(String itia) {
		this.itia = itia;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDemandType() {
		return demandType;
	}

	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}

	public int getStartIndex() {
		return startIndex;
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
	public String getItiaType() {
		return itiaType;
	}
	public void setItiaType(String itiaType) {
		this.itiaType = itiaType;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "DemandSearch [employeeId=" + employeeId + ", itia=" + itia
				+ ", roleId=" + roleId + ", demandType=" + demandType
				+ ", startIndex=" + startIndex + ", pageNo=" + pageNo
				+ ", itiaType=" + itiaType + ", page=" + page + ", type="
				+ type + "]";
	}
}
