package org.ldd.ssm.crm.domain;

public class FlightManage {
    private Long id;

    private String fltNbr;

    private String dptAirptCd;

    private String arrvAirptCd;
    
    private Long currentTktId;

    private Long tktincomeId;

    private Long userId;
    
    

    public Long getCurrentTktId() {
		return currentTktId;
	}

	public void setCurrentTktId(Long currentTktId) {
		this.currentTktId = currentTktId;
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

    public Long getTktincomeId() {
        return tktincomeId;
    }

    public void setTktincomeId(Long tktincomeId) {
        this.tktincomeId = tktincomeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}