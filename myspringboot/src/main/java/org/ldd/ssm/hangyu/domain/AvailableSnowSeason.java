package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class AvailableSnowSeason implements Serializable {
    private Long id;

    private String iata;

    private String availableandseasontosweepdevicetype;

    private String snoworder;

    private String availableNote;

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

    public String getAvailableandseasontosweepdevicetype() {
        return availableandseasontosweepdevicetype;
    }

    public void setAvailableandseasontosweepdevicetype(String availableandseasontosweepdevicetype) {
        this.availableandseasontosweepdevicetype = availableandseasontosweepdevicetype == null ? null : availableandseasontosweepdevicetype.trim();
    }

    public String getSnoworder() {
        return snoworder;
    }

    public void setSnoworder(String snoworder) {
        this.snoworder = snoworder == null ? null : snoworder.trim();
    }

	public String getAvailableNote() {
		return availableNote;
	}

	public void setAvailableNote(String availableNote) {
		this.availableNote = availableNote;
	}
}