package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class AirTrafficServiceCommunicationFacilities implements Serializable {
    private Long id;

    private String servicename;

    private String callsign;

    private String frequency;

    private String workingtime;

    private String rek;

    private String iata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename == null ? null : servicename.trim();
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign == null ? null : callsign.trim();
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency == null ? null : frequency.trim();
    }

    public String getWorkingtime() {
        return workingtime;
    }

    public void setWorkingtime(String workingtime) {
        this.workingtime = workingtime == null ? null : workingtime.trim();
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