package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class EconomicYear implements Serializable {
    private Long id;

    private String economicyear;

    private String citygdp;

    private String nationalranking;

    private String provinceranking;

    private Long cityinfothreeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEconomicyear() {
        return economicyear;
    }

    public void setEconomicyear(String economicyear) {
        this.economicyear = economicyear == null ? null : economicyear.trim();
    }

    public String getCitygdp() {
        return citygdp;
    }

    public void setCitygdp(String citygdp) {
        this.citygdp = citygdp == null ? null : citygdp.trim();
    }

    public String getNationalranking() {
        return nationalranking;
    }

    public void setNationalranking(String nationalranking) {
        this.nationalranking = nationalranking == null ? null : nationalranking.trim();
    }

    public String getProvinceranking() {
        return provinceranking;
    }

    public void setProvinceranking(String provinceranking) {
        this.provinceranking = provinceranking == null ? null : provinceranking.trim();
    }

    public Long getCityinfothreeId() {
        return cityinfothreeId;
    }

    public void setCityinfothreeId(Long cityinfothreeId) {
        this.cityinfothreeId = cityinfothreeId;
    }
}