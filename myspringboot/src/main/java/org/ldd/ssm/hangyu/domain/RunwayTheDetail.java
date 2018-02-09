package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class RunwayTheDetail implements Serializable {
    private Long id;

    private String runwaynumber;

    private String runwaylvl;

    private String runwaylength;

    private String runwaywidth;

    private Long airportinfonewthreeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRunwaynumber() {
        return runwaynumber;
    }

    public void setRunwaynumber(String runwaynumber) {
        this.runwaynumber = runwaynumber == null ? null : runwaynumber.trim();
    }

    public String getRunwaylvl() {
        return runwaylvl;
    }

    public void setRunwaylvl(String runwaylvl) {
        this.runwaylvl = runwaylvl == null ? null : runwaylvl.trim();
    }

    public String getRunwaylength() {
        return runwaylength;
    }

    public void setRunwaylength(String runwaylength) {
        this.runwaylength = runwaylength == null ? null : runwaylength.trim();
    }

    public String getRunwaywidth() {
        return runwaywidth;
    }

    public void setRunwaywidth(String runwaywidth) {
        this.runwaywidth = runwaywidth == null ? null : runwaywidth.trim();
    }

    public Long getAirportinfonewthreeId() {
        return airportinfonewthreeId;
    }

    public void setAirportinfonewthreeId(Long airportinfonewthreeId) {
        this.airportinfonewthreeId = airportinfonewthreeId;
    }
}