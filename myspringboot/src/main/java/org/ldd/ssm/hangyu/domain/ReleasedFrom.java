package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class ReleasedFrom implements Serializable {
    private Long id;

    private String runno;

    private String runwayavailable;

    private String runwaytakedistance;

    private String runwaystoppingdistance;

    private String runwaylandingdistance;

    private String rek;

    private String iata;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRunno() {
        return runno;
    }

    public void setRunno(String runno) {
        this.runno = runno == null ? null : runno.trim();
    }

    public String getRunwayavailable() {
        return runwayavailable;
    }

    public void setRunwayavailable(String runwayavailable) {
        this.runwayavailable = runwayavailable == null ? null : runwayavailable.trim();
    }

    public String getRunwaytakedistance() {
        return runwaytakedistance;
    }

    public void setRunwaytakedistance(String runwaytakedistance) {
        this.runwaytakedistance = runwaytakedistance == null ? null : runwaytakedistance.trim();
    }

    public String getRunwaystoppingdistance() {
        return runwaystoppingdistance;
    }

    public void setRunwaystoppingdistance(String runwaystoppingdistance) {
        this.runwaystoppingdistance = runwaystoppingdistance == null ? null : runwaystoppingdistance.trim();
    }

    public String getRunwaylandingdistance() {
        return runwaylandingdistance;
    }

    public void setRunwaylandingdistance(String runwaylandingdistance) {
        this.runwaylandingdistance = runwaylandingdistance == null ? null : runwaylandingdistance.trim();
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