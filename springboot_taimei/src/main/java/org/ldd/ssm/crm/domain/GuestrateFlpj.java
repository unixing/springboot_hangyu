package org.ldd.ssm.crm.domain;

public class GuestrateFlpj {
    private Long id;

    private String onGutDte;//日期

    private Integer gutRae;

    private String dptAirptCd;

    private String arrvAirptCd;
    
    private String pasStp;

    private String fltNbr;//航班号

    private String countSet;

    private String dtaSce;//采集时间

    private String week;

    private String aircrftTyp;
    
    public String getPasStp() {
		return pasStp;
	}

	public void setPasStp(String pasStp) {
		this.pasStp = pasStp;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOnGutDte() {
        return onGutDte;
    }

    public void setOnGutDte(String onGutDte) {
        this.onGutDte = onGutDte == null ? null : onGutDte.trim();
    }

    public Integer getGutRae() {
        return gutRae;
    }

    public void setGutRae(Integer gutRae) {
        this.gutRae = gutRae;
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

    public String getFltNbr() {
        return fltNbr;
    }

    public void setFltNbr(String fltNbr) {
        this.fltNbr = fltNbr == null ? null : fltNbr.trim();
    }

    public String getCountSet() {
        return countSet;
    }

    public void setCountSet(String countSet) {
        this.countSet = countSet == null ? null : countSet.trim();
    }

    public String getDtaSce() {
        return dtaSce;
    }

    public void setDtaSce(String dtaSce) {
        this.dtaSce = dtaSce == null ? null : dtaSce.trim();
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week == null ? null : week.trim();
    }

    public String getAircrftTyp() {
        return aircrftTyp;
    }

    public void setAircrftTyp(String aircrftTyp) {
        this.aircrftTyp = aircrftTyp == null ? null : aircrftTyp.trim();
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GuestrateFlpj [id=" + id + ", onGutDte=" + onGutDte
				+ ", gutRae=" + gutRae + ", dptAirptCd=" + dptAirptCd
				+ ", arrvAirptCd=" + arrvAirptCd + ", pasStp=" + pasStp
				+ ", fltNbr=" + fltNbr + ", countSet=" + countSet + ", dtaSce="
				+ dtaSce + ", week=" + week + ", aircrftTyp=" + aircrftTyp
				+ "]";
	}
    
    
}