package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class FlowOfSubsidiary  implements Serializable {
    private Long id;

    private String year;

    private String passengerthroughput;

    private String goodsthroughput;

    private String takeoffandlandingflights;

    private Long airportinfonewthreeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getPassengerthroughput() {
        return passengerthroughput;
    }

    public void setPassengerthroughput(String passengerthroughput) {
        this.passengerthroughput = passengerthroughput == null ? null : passengerthroughput.trim();
    }

    public String getGoodsthroughput() {
        return goodsthroughput;
    }

    public void setGoodsthroughput(String goodsthroughput) {
        this.goodsthroughput = goodsthroughput == null ? null : goodsthroughput.trim();
    }

    public String getTakeoffandlandingflights() {
        return takeoffandlandingflights;
    }

    public void setTakeoffandlandingflights(String takeoffandlandingflights) {
        this.takeoffandlandingflights = takeoffandlandingflights == null ? null : takeoffandlandingflights.trim();
    }

    public Long getAirportinfonewthreeId() {
        return airportinfonewthreeId;
    }

    public void setAirportinfonewthreeId(Long airportinfonewthreeId) {
        this.airportinfonewthreeId = airportinfonewthreeId;
    }
}