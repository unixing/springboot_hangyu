package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class PlaneDetail implements Serializable {
    private Long id;

    private String airporttype;

    private String number;

    private Long aircompenyinfothreeId;

    private String countSet;

    private String airSpeed;

    private String hourIncome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirporttype() {
        return airporttype;
    }

    public void setAirporttype(String airporttype) {
        this.airporttype = airporttype == null ? null : airporttype.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public Long getAircompenyinfothreeId() {
        return aircompenyinfothreeId;
    }

    public void setAircompenyinfothreeId(Long aircompenyinfothreeId) {
        this.aircompenyinfothreeId = aircompenyinfothreeId;
    }

    public String getCountSet() {
        return countSet;
    }

    public void setCountSet(String countSet) {
        this.countSet = countSet == null ? null : countSet.trim();
    }

    public String getAirSpeed() {
        return airSpeed;
    }

    public void setAirSpeed(String airSpeed) {
        this.airSpeed = airSpeed == null ? null : airSpeed.trim();
    }

    public String getHourIncome() {
        return hourIncome;
    }

    public void setHourIncome(String hourIncome) {
        this.hourIncome = hourIncome == null ? null : hourIncome.trim();
    }
}