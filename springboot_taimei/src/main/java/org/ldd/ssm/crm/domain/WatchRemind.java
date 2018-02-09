package org.ldd.ssm.crm.domain;

public class WatchRemind {
    private Long id;

    private String dptAirptCd;

    private String arrvAirptCd;

    private String changePrice;

    private String changeRate;

    private Long userId;

    private String userTel;
    
    private String userEmail;
    
    public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

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

    public String getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(String changePrice) {
        this.changePrice = changePrice == null ? null : changePrice.trim();
    }

    public String getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(String changeRate) {
        this.changeRate = changeRate == null ? null : changeRate.trim();
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
}