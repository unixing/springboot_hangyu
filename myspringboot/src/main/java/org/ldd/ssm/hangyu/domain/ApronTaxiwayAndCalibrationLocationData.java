package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class ApronTaxiwayAndCalibrationLocationData implements Serializable {
    private Long id;

    private String iata;

    private String airfieldpavementandintensity;

    private String taxiwaywidthpavementandstrength;

    private String altimetercalibrationpointpositionandelevation;

    private String vororinscalibrationpoints;

    private String apronNote;

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

    public String getAirfieldpavementandintensity() {
        return airfieldpavementandintensity;
    }

    public void setAirfieldpavementandintensity(String airfieldpavementandintensity) {
        this.airfieldpavementandintensity = airfieldpavementandintensity == null ? null : airfieldpavementandintensity.trim();
    }

    public String getTaxiwaywidthpavementandstrength() {
        return taxiwaywidthpavementandstrength;
    }

    public void setTaxiwaywidthpavementandstrength(String taxiwaywidthpavementandstrength) {
        this.taxiwaywidthpavementandstrength = taxiwaywidthpavementandstrength == null ? null : taxiwaywidthpavementandstrength.trim();
    }

    public String getAltimetercalibrationpointpositionandelevation() {
        return altimetercalibrationpointpositionandelevation;
    }

    public void setAltimetercalibrationpointpositionandelevation(String altimetercalibrationpointpositionandelevation) {
        this.altimetercalibrationpointpositionandelevation = altimetercalibrationpointpositionandelevation == null ? null : altimetercalibrationpointpositionandelevation.trim();
    }

    public String getVororinscalibrationpoints() {
        return vororinscalibrationpoints;
    }

    public void setVororinscalibrationpoints(String vororinscalibrationpoints) {
        this.vororinscalibrationpoints = vororinscalibrationpoints == null ? null : vororinscalibrationpoints.trim();
    }

	public String getApronNote() {
		return apronNote;
	}

	public void setApronNote(String apronNote) {
		this.apronNote = apronNote;
	}
}