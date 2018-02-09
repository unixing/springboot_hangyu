package org.ldd.ssm.crm.domain;

public class TKTIncome {
    private Long id;

    private String fltNbr;

    private String dptAirptCd;

    private String arrvAirptCd;

    private String state;// 1 增  2删  3改  
    
    private String date;
    
    public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFltNbr() {
        return fltNbr;
    }

    public void setFltNbr(String fltNbr) {
        this.fltNbr = fltNbr == null ? null : fltNbr.trim();
    }

    public String getDptAirptCd() {
        return dptAirptCd;
    }

    public void setDptAirptCd(String dptAirptCd) {
        this.dptAirptCd = dptAirptCd == null ? null : dptAirptCd.trim();
    }

    public String getArrvAirptCd() {
        return arrvAirptCd;
    }

    public void setArrvAirptCd(String arrvAirptCd) {
        this.arrvAirptCd = arrvAirptCd == null ? null : arrvAirptCd.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}