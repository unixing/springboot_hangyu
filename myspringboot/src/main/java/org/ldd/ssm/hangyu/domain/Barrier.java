package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class Barrier implements Serializable {
    private Long id;

    private String no;

    private String barriername;

    private String magneticbearing;

    private String distance;

    private String coordinates;

    private String altitude;

    private String leg;

    private String rek;

    private String iata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getBarriername() {
        return barriername;
    }

    public void setBarriername(String barriername) {
        this.barriername = barriername == null ? null : barriername.trim();
    }

    public String getMagneticbearing() {
        return magneticbearing;
    }

    public void setMagneticbearing(String magneticbearing) {
        this.magneticbearing = magneticbearing == null ? null : magneticbearing.trim();
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance == null ? null : distance.trim();
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates == null ? null : coordinates.trim();
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude == null ? null : altitude.trim();
    }

    public String getLeg() {
        return leg;
    }

    public void setLeg(String leg) {
        this.leg = leg == null ? null : leg.trim();
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