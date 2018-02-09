package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class PhysicalCharacteristicsOfRunway implements Serializable {
    private Long id;

    private String runno;

    private String orientation;

    private String trackwidth;

    private String runwayintensity;

    private String runwayentrance;

    private String runwayelevation;

    private String runwayslope;

    private String runwaystoplongwidth;

    private String runwaynetlongwide;

    private String runwayliftingbeltwidth;

    private String runwayendofrunwaysafetyaspect;

    private String runwaynoobstaclearea;

    private String runwayavailable;

    private String runwaytakedistance;

    private String runwaystoppingdistance;

    private String runwaylandingdistance;

    private String rek;

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

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation == null ? null : orientation.trim();
    }

    public String getTrackwidth() {
        return trackwidth;
    }

    public void setTrackwidth(String trackwidth) {
        this.trackwidth = trackwidth == null ? null : trackwidth.trim();
    }

    public String getRunwayintensity() {
        return runwayintensity;
    }

    public void setRunwayintensity(String runwayintensity) {
        this.runwayintensity = runwayintensity == null ? null : runwayintensity.trim();
    }

    public String getRunwayentrance() {
        return runwayentrance;
    }

    public void setRunwayentrance(String runwayentrance) {
        this.runwayentrance = runwayentrance == null ? null : runwayentrance.trim();
    }

    public String getRunwayelevation() {
        return runwayelevation;
    }

    public void setRunwayelevation(String runwayelevation) {
        this.runwayelevation = runwayelevation == null ? null : runwayelevation.trim();
    }

    public String getRunwayslope() {
        return runwayslope;
    }

    public void setRunwayslope(String runwayslope) {
        this.runwayslope = runwayslope == null ? null : runwayslope.trim();
    }

    public String getRunwaystoplongwidth() {
        return runwaystoplongwidth;
    }

    public void setRunwaystoplongwidth(String runwaystoplongwidth) {
        this.runwaystoplongwidth = runwaystoplongwidth == null ? null : runwaystoplongwidth.trim();
    }

    public String getRunwaynetlongwide() {
        return runwaynetlongwide;
    }

    public void setRunwaynetlongwide(String runwaynetlongwide) {
        this.runwaynetlongwide = runwaynetlongwide == null ? null : runwaynetlongwide.trim();
    }

    public String getRunwayliftingbeltwidth() {
        return runwayliftingbeltwidth;
    }

    public void setRunwayliftingbeltwidth(String runwayliftingbeltwidth) {
        this.runwayliftingbeltwidth = runwayliftingbeltwidth == null ? null : runwayliftingbeltwidth.trim();
    }

    public String getRunwayendofrunwaysafetyaspect() {
        return runwayendofrunwaysafetyaspect;
    }

    public void setRunwayendofrunwaysafetyaspect(String runwayendofrunwaysafetyaspect) {
        this.runwayendofrunwaysafetyaspect = runwayendofrunwaysafetyaspect == null ? null : runwayendofrunwaysafetyaspect.trim();
    }

    public String getRunwaynoobstaclearea() {
        return runwaynoobstaclearea;
    }

    public void setRunwaynoobstaclearea(String runwaynoobstaclearea) {
        this.runwaynoobstaclearea = runwaynoobstaclearea == null ? null : runwaynoobstaclearea.trim();
    }

    public String getRunwayavailable() {
        return runwayavailable;
    }

    public void setRunwayavailable(String runwayavailable) {
        this.runwayavailable = runwayavailable == null ? null : runwayavailable.trim();
    }

    public String getRunwaytakedistance() {
        return runwaytakedistance;
    }

    public void setRunwaytakedistance(String runwaytakedistance) {
        this.runwaytakedistance = runwaytakedistance == null ? null : runwaytakedistance.trim();
    }

    public String getRunwaystoppingdistance() {
        return runwaystoppingdistance;
    }

    public void setRunwaystoppingdistance(String runwaystoppingdistance) {
        this.runwaystoppingdistance = runwaystoppingdistance == null ? null : runwaystoppingdistance.trim();
    }

    public String getRunwaylandingdistance() {
        return runwaylandingdistance;
    }

    public void setRunwaylandingdistance(String runwaylandingdistance) {
        this.runwaylandingdistance = runwaylandingdistance == null ? null : runwaylandingdistance.trim();
    }

    public String getRek() {
        return rek;
    }

    public void setRek(String rek) {
        this.rek = rek == null ? null : rek.trim();
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