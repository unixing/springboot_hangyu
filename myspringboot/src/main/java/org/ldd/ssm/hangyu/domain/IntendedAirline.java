package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class IntendedAirline implements Serializable {
    private Long id;

    private Long demandId;//需求主键id

    private String dpt;//意向航线起始机场三字码
    
    private String dptName;//意向航线起始机场名称

    private String pst;//意向航线经停机场三字码
    
    private String pstName;//意向航线经停机场名称

    private String arrv;//意向航线到达机场三字码
    
    private String arrvName;//意向航线到达机场名称

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDemandId() {
        return demandId;
    }

    public void setDemandId(Long demandId) {
        this.demandId = demandId;
    }

    public String getDpt() {
        return dpt;
    }

    public void setDpt(String dpt) {
        this.dpt = dpt == null ? null : dpt.trim();
    }

    public String getPst() {
        return pst;
    }

    public void setPst(String pst) {
        this.pst = pst == null ? null : pst.trim();
    }

    public String getArrv() {
        return arrv;
    }

    public void setArrv(String arrv) {
        this.arrv = arrv == null ? null : arrv.trim();
    }

	public String getDptName() {
		return dptName;
	}

	public void setDptName(String dptName) {
		this.dptName = dptName;
	}

	public String getPstName() {
		return pstName;
	}

	public void setPstName(String pstName) {
		this.pstName = pstName;
	}

	public String getArrvName() {
		return arrvName;
	}

	public void setArrvName(String arrvName) {
		this.arrvName = arrvName;
	}

	@Override
	public String toString() {
		return "IntendedAirline [id=" + id + ", demandId=" + demandId
				+ ", dpt=" + dpt + ", dptName=" + dptName + ", pst=" + pst
				+ ", pstName=" + pstName + ", arrv=" + arrv + ", arrvName="
				+ arrvName + "]";
	}
    
}