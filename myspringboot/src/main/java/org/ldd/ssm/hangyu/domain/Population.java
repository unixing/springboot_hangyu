package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class Population implements Serializable {
    private Long id;

    private String populationyear;

    private String citypgenumber;

    private String ruralpgenumber;

    private String residentpgenumber;

    private String flowpgenumber;

    private String nationalranking;

    private String provinceranking;

    private Long cityinfothreeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPopulationyear() {
        return populationyear;
    }

    public void setPopulationyear(String populationyear) {
        this.populationyear = populationyear == null ? null : populationyear.trim();
    }

    public String getCitypgenumber() {
        return citypgenumber;
    }

    public void setCitypgenumber(String citypgenumber) {
        this.citypgenumber = citypgenumber == null ? null : citypgenumber.trim();
    }

    public String getRuralpgenumber() {
        return ruralpgenumber;
    }

    public void setRuralpgenumber(String ruralpgenumber) {
        this.ruralpgenumber = ruralpgenumber == null ? null : ruralpgenumber.trim();
    }

    public String getResidentpgenumber() {
        return residentpgenumber;
    }

    public void setResidentpgenumber(String residentpgenumber) {
        this.residentpgenumber = residentpgenumber == null ? null : residentpgenumber.trim();
    }

    public String getFlowpgenumber() {
        return flowpgenumber;
    }

    public void setFlowpgenumber(String flowpgenumber) {
        this.flowpgenumber = flowpgenumber == null ? null : flowpgenumber.trim();
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