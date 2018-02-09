package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class RewardPolicy implements Serializable {
    private Long id;

    private String rewardpolicydate;

    private String rewardpolicytext;

    private Long airportinfonewthreeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRewardpolicydate() {
        return rewardpolicydate;
    }

    public void setRewardpolicydate(String rewardpolicydate) {
        this.rewardpolicydate = rewardpolicydate == null ? null : rewardpolicydate.trim();
    }

    public String getRewardpolicytext() {
        return rewardpolicytext;
    }

    public void setRewardpolicytext(String rewardpolicytext) {
        this.rewardpolicytext = rewardpolicytext == null ? null : rewardpolicytext.trim();
    }

    public Long getAirportinfonewthreeId() {
        return airportinfonewthreeId;
    }

    public void setAirportinfonewthreeId(Long airportinfonewthreeId) {
        this.airportinfonewthreeId = airportinfonewthreeId;
    }
}