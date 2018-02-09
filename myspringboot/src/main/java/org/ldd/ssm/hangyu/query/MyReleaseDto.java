package org.ldd.ssm.hangyu.query;

import java.io.Serializable;
import java.util.List;


/**
 * 我的发布列表对象
 * @author wxm
 * 2017年11月30日
 */
public class MyReleaseDto implements Serializable {
	private Long id;
	private String releaseTime;
	private String demandType;
	private String title;
	private String demandProgress;
	private List<EmployeeDto> employeeList;
	
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
	public List<EmployeeDto> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List<EmployeeDto> employeeList) {
		this.employeeList = employeeList;
	}
	
}
