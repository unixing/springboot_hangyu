package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class OtherLightsBackupPowerSupply implements Serializable {
    private Long id;

    private String iata;

    private String airportcharacteristicsworki;

    private String landingdirection;

    private String glideroadside;

    private String backuppowerorconversiontime;

    private String stoppingdischarginlamp;

    private String therunwayblinker;

    private String couldomnidirectionalguidinglight;

    private String otherNote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata == null ? null : iata.trim();
    }

    public String getAirportcharacteristicsworki() {
        return airportcharacteristicsworki;
    }

    public void setAirportcharacteristicsworki(String airportcharacteristicsworki) {
        this.airportcharacteristicsworki = airportcharacteristicsworki == null ? null : airportcharacteristicsworki.trim();
    }

    public String getLandingdirection() {
        return landingdirection;
    }

    public void setLandingdirection(String landingdirection) {
        this.landingdirection = landingdirection == null ? null : landingdirection.trim();
    }

    public String getGlideroadside() {
        return glideroadside;
    }

    public void setGlideroadside(String glideroadside) {
        this.glideroadside = glideroadside == null ? null : glideroadside.trim();
    }

    public String getBackuppowerorconversiontime() {
        return backuppowerorconversiontime;
    }

    public void setBackuppowerorconversiontime(String backuppowerorconversiontime) {
        this.backuppowerorconversiontime = backuppowerorconversiontime == null ? null : backuppowerorconversiontime.trim();
    }

    public String getStoppingdischarginlamp() {
        return stoppingdischarginlamp;
    }

    public void setStoppingdischarginlamp(String stoppingdischarginlamp) {
        this.stoppingdischarginlamp = stoppingdischarginlamp == null ? null : stoppingdischarginlamp.trim();
    }

    public String getTherunwayblinker() {
        return therunwayblinker;
    }

    public void setTherunwayblinker(String therunwayblinker) {
        this.therunwayblinker = therunwayblinker == null ? null : therunwayblinker.trim();
    }

    public String getCouldomnidirectionalguidinglight() {
        return couldomnidirectionalguidinglight;
    }

    public void setCouldomnidirectionalguidinglight(String couldomnidirectionalguidinglight) {
        this.couldomnidirectionalguidinglight = couldomnidirectionalguidinglight == null ? null : couldomnidirectionalguidinglight.trim();
    }

	public String getOtherNote() {
		return otherNote;
	}

	public void setOtherNote(String otherNote) {
		this.otherNote = otherNote;
	}
}