package org.ldd.ssm.crm.domain;

public class WatchFlight {
    private Long id;

    private String fltNbr;

    private String dptAirptCd;

    private String arrvAirptCd;

    private Long tktincomeId;
    
    private String startWatchTime;

    private String endWatchTime;

    private String upPrice;

    private String downPrice;

    private Long userId;

    private String userTel;

    private String state;
    
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

    public String getStartWatchTime() {
        return startWatchTime;
    }

    public void setStartWatchTime(String startWatchTime) {
        this.startWatchTime = startWatchTime == null ? null : startWatchTime.trim();
    }

    public String getEndWatchTime() {
        return endWatchTime;
    }

    public void setEndWatchTime(String endWatchTime) {
        this.endWatchTime = endWatchTime == null ? null : endWatchTime.trim();
    }

    public String getUpPrice() {
        return upPrice;
    }

    public void setUpPrice(String upPrice) {
        this.upPrice = upPrice == null ? null : upPrice.trim();
    }

    public String getDownPrice() {
        return downPrice;
    }

    public void setDownPrice(String downPrice) {
        this.downPrice = downPrice == null ? null : downPrice.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel == null ? null : userTel.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}