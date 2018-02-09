package org.ldd.ssm.crm.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 系统日志
 */
public class SystemLog {
	
	private Long id;
	
	private Employee opUser;//操作的用户
	
	private Date opTime; //操作的时间
	
	private String opIp; //操作的ip
	
	private String function;// 操作信息

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getOpUser() {
		return opUser;
	}

	public void setOpUser(Employee opUser) {
		this.opUser = opUser;
	}
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public String getOpIp() {
		return opIp;
	}

	public void setOpIp(String opIp) {
		this.opIp = opIp;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}
}
