package org.ldd.ssm.crm.domain;

import java.util.List;

public class InformationIssued {
    private Long id;

    private String lclDptDay;

    private String logInf;
    
    private int type;//公告
    
    private List<String> logInfList;//公告内容列表

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLclDptDay() {
        return lclDptDay;
    }

    public void setLclDptDay(String lclDptDay) {
        this.lclDptDay = lclDptDay == null ? null : lclDptDay.trim();
    }

    public String getLogInf() {
        return logInf;
    }

    public void setLogInf(String logInf) {
        this.logInf = logInf == null ? null : logInf.trim();
    }

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<String> getLogInfList() {
		return logInfList;
	}

	public void setLogInfList(List<String> logInfList) {
		this.logInfList = logInfList;
	}

	@Override
	public String toString() {
		return "InformationIssued [id=" + id + ", lclDptDay=" + lclDptDay
				+ ", logInf=" + logInf + ", type=" + type + ", logInfList="
				+ logInfList + "]";
	}
}