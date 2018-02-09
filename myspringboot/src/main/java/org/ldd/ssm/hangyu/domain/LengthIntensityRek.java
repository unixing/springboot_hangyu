package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class LengthIntensityRek implements Serializable {
    private Long id;

    private String iata;

    private String lengthintensityrek;

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

    public String getLengthintensityrek() {
        return lengthintensityrek;
    }

    public void setLengthintensityrek(String lengthintensityrek) {
        this.lengthintensityrek = lengthintensityrek == null ? null : lengthintensityrek.trim();
    }
}