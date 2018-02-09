package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class GroundServicesAndFacilities  implements Serializable {
    private Long id;

    private String iata;

    private String cargohandlingfacilities;

    private String thefueloroilbrand;

    private String refuelingfacilitiesorability;

    private String deicingfacilities;

    private String standingaircrafthangar;

    private String inaircraftmaintenancefacilities;

    private String groundNote;

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

    public String getCargohandlingfacilities() {
        return cargohandlingfacilities;
    }

    public void setCargohandlingfacilities(String cargohandlingfacilities) {
        this.cargohandlingfacilities = cargohandlingfacilities == null ? null : cargohandlingfacilities.trim();
    }

    public String getThefueloroilbrand() {
        return thefueloroilbrand;
    }

    public void setThefueloroilbrand(String thefueloroilbrand) {
        this.thefueloroilbrand = thefueloroilbrand == null ? null : thefueloroilbrand.trim();
    }

    public String getRefuelingfacilitiesorability() {
        return refuelingfacilitiesorability;
    }

    public void setRefuelingfacilitiesorability(String refuelingfacilitiesorability) {
        this.refuelingfacilitiesorability = refuelingfacilitiesorability == null ? null : refuelingfacilitiesorability.trim();
    }

    public String getDeicingfacilities() {
        return deicingfacilities;
    }

    public void setDeicingfacilities(String deicingfacilities) {
        this.deicingfacilities = deicingfacilities == null ? null : deicingfacilities.trim();
    }

    public String getStandingaircrafthangar() {
        return standingaircrafthangar;
    }

    public void setStandingaircrafthangar(String standingaircrafthangar) {
        this.standingaircrafthangar = standingaircrafthangar == null ? null : standingaircrafthangar.trim();
    }

    public String getInaircraftmaintenancefacilities() {
        return inaircraftmaintenancefacilities;
    }

    public void setInaircraftmaintenancefacilities(String inaircraftmaintenancefacilities) {
        this.inaircraftmaintenancefacilities = inaircraftmaintenancefacilities == null ? null : inaircraftmaintenancefacilities.trim();
    }

	public String getGroundNote() {
		return groundNote;
	}

	public void setGroundNote(String groundNote) {
		this.groundNote = groundNote;
	}
}