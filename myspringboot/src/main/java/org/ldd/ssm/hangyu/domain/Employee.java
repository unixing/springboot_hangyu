package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class Employee implements Serializable {
    private Long id;

    private String username;

    private String password;

    private String role;
    
    private String roleStr;

    private String whethersign;
    
    private String whethersignStr;

    private String airlineretrievalcondition;
    
    private String intentionMoney;
    
    private String nickName;
    
    private int chatNum;//未读消息数
    
    private String uuid;
    
    private String headPortrait;
    
    private String phone;
    
    private String email;

    private String concat;

    private String companyName;

    private String department;

    private String position;

    private String companyAddr;
    
    private Long directId;
    
    public Long getDirectId() {
		return directId;
	}

	public void setDirectId(Long directId) {
		this.directId = directId;
	}

    public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName == null ? null : nickName.trim();
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getWhethersign() {
        return whethersign;
    }

    public void setWhethersign(String whethersign) {
        this.whethersign = whethersign == null ? null : whethersign.trim();
    }

    public String getAirlineretrievalcondition() {
        return airlineretrievalcondition;
    }

    public void setAirlineretrievalcondition(String airlineretrievalcondition) {
        this.airlineretrievalcondition = airlineretrievalcondition == null ? null : airlineretrievalcondition.trim();
    }

	public String getRoleStr() {
		return roleStr;
	}

	public void setRoleStr(String roleStr) {
		this.roleStr = roleStr == null ? null : roleStr.trim();
	}

	public String getWhethersignStr() {
		return whethersignStr;
	}

	public void setWhethersignStr(String whethersignStr) {
		this.whethersignStr = whethersignStr == null ? null : whethersignStr.trim();
	}

	public String getIntentionMoney() {
		return intentionMoney;
	}

	public void setIntentionMoney(String intentionMoney) {
		this.intentionMoney = intentionMoney == null ? null : intentionMoney.trim();
	}

	public int getChatNum() {
		return chatNum;
	}

	public void setChatNum(int chatNum) {
		this.chatNum = chatNum;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConcat() {
		return concat;
	}

	public void setConcat(String concat) {
		this.concat = concat;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", username=" + username + ", password="
				+ password + ", role=" + role + ", roleStr=" + roleStr
				+ ", whethersign=" + whethersign + ", whethersignStr="
				+ whethersignStr + ", airlineretrievalcondition="
				+ airlineretrievalcondition + ", intentionMoney="
				+ intentionMoney + ", nickName=" + nickName + ", chatNum="
				+ chatNum + ", uuid=" + uuid + ", headPortrait=" + headPortrait
				+ ", phone=" + phone + ", email=" + email + ", concat="
				+ concat + ", companyName=" + companyName + ", department="
				+ department + ", position=" + position + ", companyAddr="
				+ companyAddr + "]";
	}
}