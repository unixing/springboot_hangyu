package org.ldd.ssm.crm.domain;

public class TimeResource {
    private Integer id;

    private String terminal;

    private String proofreadAirline;

    private String flightNbr;

    private String airRoute;

    private String planeType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal == null ? null : terminal.trim();
    }

    public String getProofreadAirline() {
        return proofreadAirline;
    }

    public void setProofreadAirline(String proofreadAirline) {
        this.proofreadAirline = proofreadAirline == null ? null : proofreadAirline.trim();
    }

    public String getFlightNbr() {
        return flightNbr;
    }

    public void setFlightNbr(String flightNbr) {
        this.flightNbr = flightNbr == null ? null : flightNbr.trim();
    }

    public String getAirRoute() {
        return airRoute;
    }

    public void setAirRoute(String airRoute) {
        this.airRoute = airRoute == null ? null : airRoute.trim();
    }

    public String getPlaneType() {
        return planeType;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType == null ? null : planeType.trim();
    }
}