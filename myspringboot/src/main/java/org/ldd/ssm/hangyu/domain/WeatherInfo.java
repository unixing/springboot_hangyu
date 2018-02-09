package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class WeatherInfo implements Serializable {
    private Long id;

    private String month;

    private String monthmax;

    private String monthmin;

    private String avgsd;

    private String avgqy;

    private String iata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public String getMonthmax() {
        return monthmax;
    }

    public void setMonthmax(String monthmax) {
        this.monthmax = monthmax == null ? null : monthmax.trim();
    }

    public String getMonthmin() {
        return monthmin;
    }

    public void setMonthmin(String monthmin) {
        this.monthmin = monthmin == null ? null : monthmin.trim();
    }

    public String getAvgsd() {
        return avgsd;
    }

    public void setAvgsd(String avgsd) {
        this.avgsd = avgsd == null ? null : avgsd.trim();
    }

    public String getAvgqy() {
        return avgqy;
    }

    public void setAvgqy(String avgqy) {
        this.avgqy = avgqy == null ? null : avgqy.trim();
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata == null ? null : iata.trim();
    }
}