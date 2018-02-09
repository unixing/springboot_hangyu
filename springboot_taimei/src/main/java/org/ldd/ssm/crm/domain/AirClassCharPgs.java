package org.ldd.ssm.crm.domain;

public class AirClassCharPgs {
    private Long id;

    private String dctChr;//舱位

    private String dctPgs;//人数

    private Long guestrateflpjId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDctChr() {
        return dctChr;
    }

    public void setDctChr(String dctChr) {
        this.dctChr = dctChr == null ? null : dctChr.trim();
    }

    public String getDctPgs() {
        return dctPgs;
    }

    public void setDctPgs(String dctPgs) {
        this.dctPgs = dctPgs == null ? null : dctPgs.trim();
    }

    public Long getGuestrateflpjId() {
        return guestrateflpjId;
    }

    public void setGuestrateflpjId(Long guestrateflpjId) {
        this.guestrateflpjId = guestrateflpjId;
    }
}