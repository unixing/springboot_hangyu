package org.ldd.ssm.crm.domain;

public class MeasureResult {
    private Long id;

    private Long applyMeasureId;

    private String leg;//航段
    
    private String fltRteCd;//航线

    private String dptAirptCd;

    private String arrvAirptCd;

    private String startTime;

    private String endTime;

    private String flightPerMonth;//班次每月

    private String avgSites;//平均座位

    private String avgVol;

    private String plf;//客座率

    private String avgIncome;//

    private String kmIncome;

    private String avgDiscount;

    private String measureTime;
    

    public String getLeg() {
		return leg;
	}

	public void setLeg(String leg) {
		this.leg = leg;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplyMeasureId() {
        return applyMeasureId;
    }

    public void setApplyMeasureId(Long applyMeasureId) {
        this.applyMeasureId = applyMeasureId;
    }

    public String getFltRteCd() {
        return fltRteCd;
    }

    public void setFltRteCd(String fltRteCd) {
        this.fltRteCd = fltRteCd == null ? null : fltRteCd.trim();
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public String getFlightPerMonth() {
        return flightPerMonth;
    }

    public void setFlightPerMonth(String flightPerMonth) {
        this.flightPerMonth = flightPerMonth == null ? null : flightPerMonth.trim();
    }

    public String getAvgSites() {
        return avgSites;
    }

    public void setAvgSites(String avgSites) {
        this.avgSites = avgSites == null ? null : avgSites.trim();
    }

    public String getAvgVol() {
        return avgVol;
    }

    public void setAvgVol(String avgVol) {
        this.avgVol = avgVol == null ? null : avgVol.trim();
    }

    public String getPlf() {
        return plf;
    }

    public void setPlf(String plf) {
        this.plf = plf == null ? null : plf.trim();
    }

    public String getAvgIncome() {
        return avgIncome;
    }

    public void setAvgIncome(String avgIncome) {
        this.avgIncome = avgIncome == null ? null : avgIncome.trim();
    }

    public String getKmIncome() {
        return kmIncome;
    }

    public void setKmIncome(String kmIncome) {
        this.kmIncome = kmIncome == null ? null : kmIncome.trim();
    }

    public String getAvgDiscount() {
        return avgDiscount;
    }

    public void setAvgDiscount(String avgDiscount) {
        this.avgDiscount = avgDiscount == null ? null : avgDiscount.trim();
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(String measureTime) {
        this.measureTime = measureTime == null ? null : measureTime.trim();
    }
}