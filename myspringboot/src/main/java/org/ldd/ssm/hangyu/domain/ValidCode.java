package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class ValidCode implements Serializable {
    private Integer id;

    private String code;

    private Long createTime;
    
    private String contactWay;
    
    private String state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "ValidCode [id=" + id + ", code=" + code
				+ ", createTime=" + createTime + ", contactWay=" + contactWay
				+ "]";
	}
}