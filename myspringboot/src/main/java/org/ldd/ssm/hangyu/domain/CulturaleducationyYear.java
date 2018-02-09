package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class CulturaleducationyYear implements Serializable {
    private Long id;

    private String culturaleducationyear;

    private String passengersnumberyear;

    private String domesticpassengernumberyear;

    private String foreignpassengernumberyear;

    private String tourismincome;

    private String tourismrmbincome;

    private String tourismmyincome;

    private String exchangerate;

    private Long cityinfothreeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCulturaleducationyear() {
        return culturaleducationyear;
    }

    public void setCulturaleducationyear(String culturaleducationyear) {
        this.culturaleducationyear = culturaleducationyear == null ? null : culturaleducationyear.trim();
    }

    public String getPassengersnumberyear() {
        return passengersnumberyear;
    }

    public void setPassengersnumberyear(String passengersnumberyear) {
        this.passengersnumberyear = passengersnumberyear == null ? null : passengersnumberyear.trim();
    }

    public String getDomesticpassengernumberyear() {
        return domesticpassengernumberyear;
    }

    public void setDomesticpassengernumberyear(String domesticpassengernumberyear) {
        this.domesticpassengernumberyear = domesticpassengernumberyear == null ? null : domesticpassengernumberyear.trim();
    }

    public String getForeignpassengernumberyear() {
        return foreignpassengernumberyear;
    }

    public void setForeignpassengernumberyear(String foreignpassengernumberyear) {
        this.foreignpassengernumberyear = foreignpassengernumberyear == null ? null : foreignpassengernumberyear.trim();
    }

    public String getTourismincome() {
        return tourismincome;
    }

    public void setTourismincome(String tourismincome) {
        this.tourismincome = tourismincome == null ? null : tourismincome.trim();
    }

    public String getTourismrmbincome() {
        return tourismrmbincome;
    }

    public void setTourismrmbincome(String tourismrmbincome) {
        this.tourismrmbincome = tourismrmbincome == null ? null : tourismrmbincome.trim();
    }

    public String getTourismmyincome() {
        return tourismmyincome;
    }

    public void setTourismmyincome(String tourismmyincome) {
        this.tourismmyincome = tourismmyincome == null ? null : tourismmyincome.trim();
    }

    public String getExchangerate() {
        return exchangerate;
    }

    public void setExchangerate(String exchangerate) {
        this.exchangerate = exchangerate == null ? null : exchangerate.trim();
    }

    public Long getCityinfothreeId() {
        return cityinfothreeId;
    }

    public void setCityinfothreeId(Long cityinfothreeId) {
        this.cityinfothreeId = cityinfothreeId;
    }
}