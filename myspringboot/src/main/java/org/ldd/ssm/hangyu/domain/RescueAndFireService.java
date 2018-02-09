package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class RescueAndFireService implements Serializable {
    private Long id;

    private String iata;

    private String airportfirerating;

    private String rescuefacilities;

    private String abilitytomovethedamagedaircraft;

    private String rescueNote;

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

    public String getAirportfirerating() {
        return airportfirerating;
    }

    public void setAirportfirerating(String airportfirerating) {
        this.airportfirerating = airportfirerating == null ? null : airportfirerating.trim();
    }

    public String getRescuefacilities() {
        return rescuefacilities;
    }

    public void setRescuefacilities(String rescuefacilities) {
        this.rescuefacilities = rescuefacilities == null ? null : rescuefacilities.trim();
    }

    public String getAbilitytomovethedamagedaircraft() {
        return abilitytomovethedamagedaircraft;
    }

    public void setAbilitytomovethedamagedaircraft(String abilitytomovethedamagedaircraft) {
        this.abilitytomovethedamagedaircraft = abilitytomovethedamagedaircraft == null ? null : abilitytomovethedamagedaircraft.trim();
    }

	public String getRescueNote() {
		return rescueNote;
	}

	public void setRescueNote(String rescueNote) {
		this.rescueNote = rescueNote;
	}
}