package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class AirportLocationAndManagementInformation implements Serializable {
    private Long id;

    private String iata;

    private String airportcoordinatesorattheairport;

    private String citylocation;

    private String airportelevationorreferencetemperature;

    private String airportelevationorgeoidbandposition;

    private String airportelevationorheightanomaly;

    private String variationorannualvariationrate;

    private String openinghoursattheairport;

    private String airpormanagementdepartmentinfo;

    private String allowedtoflyspecies;

    private String natureoftheairportorairfieldindicators;

    private String airportnoteNote;

    private String pdfPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata == null ? null : iata.trim();
    }

    public String getAirportcoordinatesorattheairport() {
        return airportcoordinatesorattheairport;
    }

    public void setAirportcoordinatesorattheairport(String airportcoordinatesorattheairport) {
        this.airportcoordinatesorattheairport = airportcoordinatesorattheairport == null ? null : airportcoordinatesorattheairport.trim();
    }

    public String getCitylocation() {
        return citylocation;
    }

    public void setCitylocation(String citylocation) {
        this.citylocation = citylocation == null ? null : citylocation.trim();
    }

    public String getAirportelevationorreferencetemperature() {
        return airportelevationorreferencetemperature;
    }

    public void setAirportelevationorreferencetemperature(String airportelevationorreferencetemperature) {
        this.airportelevationorreferencetemperature = airportelevationorreferencetemperature == null ? null : airportelevationorreferencetemperature.trim();
    }

    public String getAirportelevationorgeoidbandposition() {
        return airportelevationorgeoidbandposition;
    }

    public void setAirportelevationorgeoidbandposition(String airportelevationorgeoidbandposition) {
        this.airportelevationorgeoidbandposition = airportelevationorgeoidbandposition == null ? null : airportelevationorgeoidbandposition.trim();
    }

    public String getAirportelevationorheightanomaly() {
        return airportelevationorheightanomaly;
    }

    public void setAirportelevationorheightanomaly(String airportelevationorheightanomaly) {
        this.airportelevationorheightanomaly = airportelevationorheightanomaly == null ? null : airportelevationorheightanomaly.trim();
    }

    public String getVariationorannualvariationrate() {
        return variationorannualvariationrate;
    }

    public void setVariationorannualvariationrate(String variationorannualvariationrate) {
        this.variationorannualvariationrate = variationorannualvariationrate == null ? null : variationorannualvariationrate.trim();
    }

    public String getOpeninghoursattheairport() {
        return openinghoursattheairport;
    }

    public void setOpeninghoursattheairport(String openinghoursattheairport) {
        this.openinghoursattheairport = openinghoursattheairport == null ? null : openinghoursattheairport.trim();
    }

    public String getAirpormanagementdepartmentinfo() {
        return airpormanagementdepartmentinfo;
    }

    public void setAirpormanagementdepartmentinfo(String airpormanagementdepartmentinfo) {
        this.airpormanagementdepartmentinfo = airpormanagementdepartmentinfo == null ? null : airpormanagementdepartmentinfo.trim();
    }

    public String getAllowedtoflyspecies() {
        return allowedtoflyspecies;
    }

    public void setAllowedtoflyspecies(String allowedtoflyspecies) {
        this.allowedtoflyspecies = allowedtoflyspecies == null ? null : allowedtoflyspecies.trim();
    }

    public String getNatureoftheairportorairfieldindicators() {
        return natureoftheairportorairfieldindicators;
    }

    public void setNatureoftheairportorairfieldindicators(String natureoftheairportorairfieldindicators) {
        this.natureoftheairportorairfieldindicators = natureoftheairportorairfieldindicators == null ? null : natureoftheairportorairfieldindicators.trim();
    }

    public String getAirportnoteNote() {
        return airportnoteNote;
    }

    public void setAirportnoteNote(String note) {
        this.airportnoteNote = airportnoteNote == null ? null : airportnoteNote.trim();
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath == null ? null : pdfPath.trim();
    }
}