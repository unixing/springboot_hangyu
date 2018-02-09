package org.ldd.ssm.hangyu.query;

import java.io.Serializable;

public class CommissionedAndCustody implements Serializable {

	private Long id;
	
	private Long demandEmployeeId;
	
	private String releaseTime;
	
	private String demandType;
	
	private String title;
	
	private String demandProgress;
	
	private String nickName;

	private Long employeeId;//登录id
	
	private String roleId;//角色
	
	private int startIndex;//查询起始下标
	
	private int pageNo;//分页查询每页显示条数
	
	private int page;//查询第几页数据
	
	private int unreadNum;//未读信息条数
	
	public int getUnreadNum() {
		return unreadNum;
	}

	public void setUnreadNum(int unreadNum) {
		this.unreadNum = unreadNum;
	}

	public Long getDemandEmployeeId() {
		return demandEmployeeId;
	}

	public void setDemandEmployeeId(Long demandEmployeeId) {
		this.demandEmployeeId = demandEmployeeId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
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

	public String getDemandProgress() {
		return demandProgress;
	}

	public void setDemandProgress(String demandProgress) {
		this.demandProgress = demandProgress;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Override
	public String toString() {
		return "CommissionedAndCustody [id=" + id + ", demandEmployeeId="
				+ demandEmployeeId + ", releaseTime=" + releaseTime
				+ ", demandType=" + demandType + ", title=" + title
				+ ", demandProgress=" + demandProgress + ", nickName="
				+ nickName + ", employeeId=" + employeeId + ", roleId="
				+ roleId + ", startIndex=" + startIndex + ", pageNo=" + pageNo
				+ ", page=" + page + ", unreadNum=" + unreadNum + "]";
	}
	
}
