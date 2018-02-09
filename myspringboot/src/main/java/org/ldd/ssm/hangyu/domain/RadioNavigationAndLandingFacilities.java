package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class RadioNavigationAndLandingFacilities implements Serializable {
    private Long id;

    private String nameandtypeoffacilities;

    private String identify;

    private String frequency;

    private String coordinates;

    private String relativeposition;

    private String transmittingantennaelevation;

    private String rek;

    private String iata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameandtypeoffacilities() {
        return nameandtypeoffacilities;
    }

    public void setNameandtypeoffacilities(String nameandtypeoffacilities) {
        this.nameandtypeoffacilities = nameandtypeoffacilities == null ? null : nameandtypeoffacilities.trim();
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify == null ? null : identify.trim();
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency == null ? null : frequency.trim();
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates == null ? null : coordinates.trim();
    }

    public String getRelativeposition() {
        return relativeposition;
    }

    public void setRelativeposition(String relativeposition) {
        this.relativeposition = relativeposition == null ? null : relativeposition.trim();
    }

    public String getTransmittingantennaelevation() {
        return transmittingantennaelevation;
    }

    public void setTransmittingantennaelevation(String transmittingantennaelevation) {
        this.transmittingantennaelevation = transmittingantennaelevation == null ? null : transmittingantennaelevation.trim();
    }

    public String getRek() {
        return rek;
    }

    public void setRek(String rek) {
        this.rek = rek == null ? null : rek.trim();
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata == null ? null : iata.trim();
    }
}