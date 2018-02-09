package org.ldd.ssm.crm.domain;

public class ApplyMeasureHistory {
    private Long id;

    private String fltRteCd;//航线

    private String startTime;//开航时间

    private String endTime;//停航时间

    private Long userId;//用户id

    private String applyMeasureTime;//申请测算时间

    private String state;//状态 （4：已删除，1：测算中 ，2：已测算,3:已更新）

    private Float distance;//距离

    private Long  aircompanyId;//航司
    
    private String aircompanyName;//航司

    private String aircraftModel;//机型

    private String sites;//座位数

    private Float hourCost;//小时成本

    private Float speed;//航速
    private String ldsj;//轮当时间
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFltRteCd() {
        return fltRteCd;
    }

    public void setFltRteCd(String fltRteCd) {
        this.fltRteCd = fltRteCd == null ? null : fltRteCd.trim();
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getApplyMeasureTime() {
        return applyMeasureTime;
    }

    public void setApplyMeasureTime(String applyMeasureTime) {
        this.applyMeasureTime = applyMeasureTime == null ? null : applyMeasureTime.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }


	public Long getAircompanyId() {
		return aircompanyId;
	}

	public void setAircompanyId(Long aircompanyId) {
		this.aircompanyId = aircompanyId;
	}

	public String getAircompanyName() {
		return aircompanyName;
	}

	public void setAircompanyName(String aircompanyName) {
		this.aircompanyName = aircompanyName;
	}

	public String getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel == null ? null : aircraftModel.trim();
    }

    public String getSites() {
        return sites;
    }

    public void setSites(String sites) {
        this.sites = sites == null ? null : sites.trim();
    }

    public Float getHourCost() {
        return hourCost;
    }

    public void setHourCost(Float hourCost) {
        this.hourCost = hourCost;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

	/**
	 * @return the ldsj
	 */
	public String getLdsj() {
		return ldsj;
	}

	/**
	 * @param ldsj the ldsj to set
	 */
	public void setLdsj(String ldsj) {
		this.ldsj = ldsj;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApplyMeasureHistory [id=" + id + ", fltRteCd=" + fltRteCd
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", userId=" + userId + ", applyMeasureTime="
				+ applyMeasureTime + ", state=" + state + ", distance="
				+ distance + ", aircompanyId=" + aircompanyId
				+ ", aircompanyName=" + aircompanyName + ", aircraftModel="
				+ aircraftModel + ", sites=" + sites + ", hourCost=" + hourCost
				+ ", speed=" + speed + ", ldsj=" + ldsj + "]";
	}
    
}