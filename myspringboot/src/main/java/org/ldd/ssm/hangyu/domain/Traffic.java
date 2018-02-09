package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class Traffic implements Serializable {
    private Long id;

    private String highwaytype;

    private String highwaycode;

    private String highwaywaypoint;

    private String railtype;

    private String railcode;

    private String railwaypoint;

    private Long cityinfothreeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHighwaytype() {
        return highwaytype;
    }

    public void setHighwaytype(String highwaytype) {
        this.highwaytype = highwaytype == null ? null : highwaytype.trim();
    }

    public String getHighwaycode() {
        return highwaycode;
    }

    public void setHighwaycode(String highwaycode) {
        this.highwaycode = highwaycode == null ? null : highwaycode.trim();
    }

    public String getHighwaywaypoint() {
        return highwaywaypoint;
    }

    public void setHighwaywaypoint(String highwaywaypoint) {
        this.highwaywaypoint = highwaywaypoint == null ? null : highwaywaypoint.trim();
    }

    public String getRailtype() {
        return railtype;
    }

    public void setRailtype(String railtype) {
        this.railtype = railtype == null ? null : railtype.trim();
    }

    public String getRailcode() {
        return railcode;
    }

    public void setRailcode(String railcode) {
        this.railcode = railcode == null ? null : railcode.trim();
    }

    public String getRailwaypoint() {
        return railwaypoint;
    }

    public void setRailwaypoint(String railwaypoint) {
        this.railwaypoint = railwaypoint == null ? null : railwaypoint.trim();
    }

    public Long getCityinfothreeId() {
        return cityinfothreeId;
    }

    public void setCityinfothreeId(Long cityinfothreeId) {
        this.cityinfothreeId = cityinfothreeId;
    }
}