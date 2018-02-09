package org.ldd.ssm.crm.domain;

public class EmailValidStr {
    private Integer id;

    private String validStr;

    private Long employeeId;

    private Long createTime;
    
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValidStr() {
        return validStr;
    }

    public void setValidStr(String validStr) {
        this.validStr = validStr == null ? null : validStr.trim();
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "EmailValidStr [id=" + id + ", validStr=" + validStr
				+ ", employeeId=" + employeeId + ", createTime=" + createTime
				+ ", email=" + email + "]";
	}
    
}