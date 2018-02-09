package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class PhysicalCharacteristicsOfRunwayRek implements Serializable {
    private Long id;

    private String iata;

    private String physicalcharacteristicsofrunwayrek;

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

    public String getPhysicalcharacteristicsofrunwayrek() {
        return physicalcharacteristicsofrunwayrek;
    }

    public void setPhysicalcharacteristicsofrunwayrek(String physicalcharacteristicsofrunwayrek) {
        this.physicalcharacteristicsofrunwayrek = physicalcharacteristicsofrunwayrek == null ? null : physicalcharacteristicsofrunwayrek.trim();
    }
}