package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class AirTrafficServiceAirspace implements Serializable {
    private Long id;

    private String name;

    private String levelrange;

    private String verticalscope;

    private String rek;

    private String iata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLevelrange() {
        return levelrange;
    }

    public void setLevelrange(String levelrange) {
        this.levelrange = levelrange == null ? null : levelrange.trim();
    }

    public String getVerticalscope() {
        return verticalscope;
    }

    public void setVerticalscope(String verticalscope) {
        this.verticalscope = verticalscope == null ? null : verticalscope.trim();
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