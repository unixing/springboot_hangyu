package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class AirPortWeatherInfo implements Serializable {
    private Long id;

    private String stationname;

    private String stationcode;

    private String frequency;

    private String equipment;

    private String type;

    private String installationposition;

    private String worktime;

    private String climatedata;

    private String iata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname == null ? null : stationname.trim();
    }

    public String getStationcode() {
        return stationcode;
    }

    public void setStationcode(String stationcode) {
        this.stationcode = stationcode == null ? null : stationcode.trim();
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency == null ? null : frequency.trim();
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment == null ? null : equipment.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getInstallationposition() {
        return installationposition;
    }

    public void setInstallationposition(String installationposition) {
        this.installationposition = installationposition == null ? null : installationposition.trim();
    }

    public String getWorktime() {
        return worktime;
    }

    public void setWorktime(String worktime) {
        this.worktime = worktime == null ? null : worktime.trim();
    }

    public String getClimatedata() {
        return climatedata;
    }

    public void setClimatedata(String climatedata) {
        this.climatedata = climatedata == null ? null : climatedata.trim();
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata == null ? null : iata.trim();
    }
}