package org.ldd.ssm.crm.domain;

public class TransportInfo {
    private Integer id;

    private String transportName;

    private String transportType;

    private String airportName;

    private String hyperbolic;

    private String runningTime;

    private String offRunningTime;

    private Integer sigleTripTime;

    private Integer intervalTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransportName() {
        return transportName;
    }

    public void setTransportName(String transportName) {
        this.transportName = transportName == null ? null : transportName.trim();
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType == null ? null : transportType.trim();
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName == null ? null : airportName.trim();
    }

    public String getHyperbolic() {
        return hyperbolic;
    }

    public void setHyperbolic(String hyperbolic) {
        this.hyperbolic = hyperbolic == null ? null : hyperbolic.trim();
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime == null ? null : runningTime.trim();
    }

    public String getOffRunningTime() {
        return offRunningTime;
    }

    public void setOffRunningTime(String offRunningTime) {
        this.offRunningTime = offRunningTime == null ? null : offRunningTime.trim();
    }

    public Integer getSigleTripTime() {
        return sigleTripTime;
    }

    public void setSigleTripTime(Integer sigleTripTime) {
        this.sigleTripTime = sigleTripTime;
    }

    public Integer getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Integer intervalTime) {
        this.intervalTime = intervalTime;
    }
}