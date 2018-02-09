package org.ldd.ssm.crm.domain;

public class AirlineCompany {
    private Long id;

    private String airLine;

    private String fltNbr;

    private String state;

    private Long company;

    private String itia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirLine() {
        return airLine;
    }

    public void setAirLine(String airLine) {
        this.airLine = airLine == null ? null : airLine.trim();
    }

    public String getFltNbr() {
        return fltNbr;
    }

    public void setFltNbr(String fltNbr) {
        this.fltNbr = fltNbr == null ? null : fltNbr.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Long getCompany() {
        return company;
    }

    public void setCompany(Long company) {
        this.company = company;
    }

    public String getItia() {
        return itia;
    }

    public void setItia(String itia) {
        this.itia = itia == null ? null : itia.trim();
    }
}