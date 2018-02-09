package org.ldd.ssm.crm.domain;

public class AirportFocus {
    private Long id;

    private String timeDemension;

    private String flightRange;

    private String dataRange;

    private String focusTarget;

    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTimeDemension() {
        return timeDemension;
    }

    public void setTimeDemension(String timeDemension) {
        this.timeDemension = timeDemension == null ? null : timeDemension.trim();
    }

    public String getFlightRange() {
        return flightRange;
    }

    public void setFlightRange(String flightRange) {
        this.flightRange = flightRange == null ? null : flightRange.trim();
    }

    public String getDataRange() {
        return dataRange;
    }

    public void setDataRange(String dataRange) {
        this.dataRange = dataRange == null ? null : dataRange.trim();
    }

    public String getFocusTarget() {
        return focusTarget;
    }

    public void setFocusTarget(String focusTarget) {
        this.focusTarget = focusTarget == null ? null : focusTarget.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}