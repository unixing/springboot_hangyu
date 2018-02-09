package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class PlatformUser implements Serializable {
    private Long id;

    private String userType;

    private String concat;

    private String companyName;

    private String tel;

    private String department;

    private String position;

    private String email;

    private String companyAddr;

    private String comment;
    
    private String state;
    
    private String validCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getConcat() {
        return concat;
    }

    public void setConcat(String concat) {
        this.concat = concat == null ? null : concat.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr == null ? null : companyAddr.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	@Override
	public String toString() {
		return "PlatformUser [id=" + id + ", userType=" + userType
				+ ", concat=" + concat + ", companyName=" + companyName
				+ ", tel=" + tel + ", department=" + department + ", position="
				+ position + ", email=" + email + ", companyAddr="
				+ companyAddr + ", comment=" + comment + ", state=" + state
				+ "]";
	}
}