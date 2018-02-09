package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;
import java.util.Date;

public class Eachflightinfo implements Serializable {
    private Long id;

    private String dptAirptCd;

    private String arrvAirptCd;

    private String fltNbr;

    private String aircrftTyp;

    private String dptAirptPot;

    private String lclDptTm;

    private String arrvAirptPot;

    private String lclArrvTm;

    private String days;

    private Date getTim;

    private String dptAirptCdItia;

    private String arrvAirptCdItia;

    private String airLineKm;

    private Long xcurlId;

    private Date crwlCreatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAircrftTyp() {
        return aircrftTyp;
    }

    public void setAircrftTyp(String aircrftTyp) {
        this.aircrftTyp = aircrftTyp == null ? null : aircrftTyp.trim();
    }

    public String getDptAirptPot() {
        return dptAirptPot;
    }

    public void setDptAirptPot(String dptAirptPot) {
        this.dptAirptPot = dptAirptPot == null ? null : dptAirptPot.trim();
    }

    public String getLclDptTm() {
        return lclDptTm;
    }

    public void setLclDptTm(String lclDptTm) {
        this.lclDptTm = lclDptTm == null ? null : lclDptTm.trim();
    }

    public String getArrvAirptPot() {
        return arrvAirptPot;
    }

    public void setArrvAirptPot(String arrvAirptPot) {
        this.arrvAirptPot = arrvAirptPot == null ? null : arrvAirptPot.trim();
    }

    public String getLclArrvTm() {
        return lclArrvTm;
    }

    public void setLclArrvTm(String lclArrvTm) {
        this.lclArrvTm = lclArrvTm == null ? null : lclArrvTm.trim();
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days == null ? null : days.trim();
    }

    public Date getGetTim() {
        return getTim;
    }

    public void setGetTim(Date getTim) {
        this.getTim = getTim;
    }

    public String getDptAirptCdItia() {
        return dptAirptCdItia;
    }

    public void setDptAirptCdItia(String dptAirptCdItia) {
        this.dptAirptCdItia = dptAirptCdItia == null ? null : dptAirptCdItia.trim();
    }

    public String getArrvAirptCdItia() {
        return arrvAirptCdItia;
    }

    public void setArrvAirptCdItia(String arrvAirptCdItia) {
        this.arrvAirptCdItia = arrvAirptCdItia == null ? null : arrvAirptCdItia.trim();
    }

    public String getAirLineKm() {
        return airLineKm;
    }

    public void setAirLineKm(String airLineKm) {
        this.airLineKm = airLineKm == null ? null : airLineKm.trim();
    }

    public Long getXcurlId() {
        return xcurlId;
    }

    public void setXcurlId(Long xcurlId) {
        this.xcurlId = xcurlId;
    }

    public Date getCrwlCreatedTime() {
        return crwlCreatedTime;
    }

    public void setCrwlCreatedTime(Date crwlCreatedTime) {
        this.crwlCreatedTime = crwlCreatedTime;
    }
}