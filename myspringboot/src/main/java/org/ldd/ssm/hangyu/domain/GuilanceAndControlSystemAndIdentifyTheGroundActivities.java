package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class GuilanceAndControlSystemAndIdentifyTheGroundActivities implements Serializable {
    private Long id;

    private String iata;

    private String aircraftflightnumbermarkcard;

    private String runwaysandtaxiwayssignsandlights;

    private String stoppingdischarginglamp;

    private String guilanceNote;

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

    public String getAircraftflightnumbermarkcard() {
        return aircraftflightnumbermarkcard;
    }

    public void setAircraftflightnumbermarkcard(String aircraftflightnumbermarkcard) {
        this.aircraftflightnumbermarkcard = aircraftflightnumbermarkcard == null ? null : aircraftflightnumbermarkcard.trim();
    }

    public String getRunwaysandtaxiwayssignsandlights() {
        return runwaysandtaxiwayssignsandlights;
    }

    public void setRunwaysandtaxiwayssignsandlights(String runwaysandtaxiwayssignsandlights) {
        this.runwaysandtaxiwayssignsandlights = runwaysandtaxiwayssignsandlights == null ? null : runwaysandtaxiwayssignsandlights.trim();
    }

    public String getStoppingdischarginglamp() {
        return stoppingdischarginglamp;
    }

    public void setStoppingdischarginglamp(String stoppingdischarginglamp) {
        this.stoppingdischarginglamp = stoppingdischarginglamp == null ? null : stoppingdischarginglamp.trim();
    }

	public String getGuilanceNote() {
		return guilanceNote;
	}

	public void setGuilanceNote(String guilanceNote) {
		this.guilanceNote = guilanceNote;
	}
}