package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class LengthIntensity implements Serializable {
    private Long id;

    private String runno;

    private String lengthintensity;

    private String lightcolor;

    private String lamptype;

    private String engthstriplights;

    private String runwaycenterlinelightslength;

    private String runwayedgelengthlamp;

    private String runwayat;

    private String stoplamplength;

    private String iata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRunno() {
        return runno;
    }

    public void setRunno(String runno) {
        this.runno = runno == null ? null : runno.trim();
    }

    public String getLengthintensity() {
        return lengthintensity;
    }

    public void setLengthintensity(String lengthintensity) {
        this.lengthintensity = lengthintensity == null ? null : lengthintensity.trim();
    }

    public String getLightcolor() {
        return lightcolor;
    }

    public void setLightcolor(String lightcolor) {
        this.lightcolor = lightcolor == null ? null : lightcolor.trim();
    }

    public String getLamptype() {
        return lamptype;
    }

    public void setLamptype(String lamptype) {
        this.lamptype = lamptype == null ? null : lamptype.trim();
    }

    public String getEngthstriplights() {
        return engthstriplights;
    }

    public void setEngthstriplights(String engthstriplights) {
        this.engthstriplights = engthstriplights == null ? null : engthstriplights.trim();
    }

    public String getRunwaycenterlinelightslength() {
        return runwaycenterlinelightslength;
    }

    public void setRunwaycenterlinelightslength(String runwaycenterlinelightslength) {
        this.runwaycenterlinelightslength = runwaycenterlinelightslength == null ? null : runwaycenterlinelightslength.trim();
    }

    public String getRunwayedgelengthlamp() {
        return runwayedgelengthlamp;
    }

    public void setRunwayedgelengthlamp(String runwayedgelengthlamp) {
        this.runwayedgelengthlamp = runwayedgelengthlamp == null ? null : runwayedgelengthlamp.trim();
    }

    public String getRunwayat() {
        return runwayat;
    }

    public void setRunwayat(String runwayat) {
        this.runwayat = runwayat == null ? null : runwayat.trim();
    }

    public String getStoplamplength() {
        return stoplamplength;
    }

    public void setStoplamplength(String stoplamplength) {
        this.stoplamplength = stoplamplength == null ? null : stoplamplength.trim();
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata == null ? null : iata.trim();
    }
}