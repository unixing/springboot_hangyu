package org.ldd.ssm.crm.domain;

public class AirportInfo {
    private Integer id;

    private String airportName;

    private Integer runwayLength;

    private Integer runwayWidth;

    private String airportLevel;

    private String fireRating;

    private String canStopType;

    private String airportType;

    private String climaticChanges;

    private String clearanceCondition;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName == null ? null : airportName.trim();
    }

    public Integer getRunwayLength() {
        return runwayLength;
    }

    public void setRunwayLength(Integer runwayLength) {
        this.runwayLength = runwayLength;
    }

    public Integer getRunwayWidth() {
        return runwayWidth;
    }

    public void setRunwayWidth(Integer runwayWidth) {
        this.runwayWidth = runwayWidth;
    }

    public String getAirportLevel() {
        return airportLevel;
    }

    public void setAirportLevel(String airportLevel) {
        this.airportLevel = airportLevel == null ? null : airportLevel.trim();
    }

    public String getFireRating() {
        return fireRating;
    }

    public void setFireRating(String fireRating) {
        this.fireRating = fireRating == null ? null : fireRating.trim();
    }

    public String getCanStopType() {
        return canStopType;
    }

    public void setCanStopType(String canStopType) {
        this.canStopType = canStopType == null ? null : canStopType.trim();
    }

    public String getAirportType() {
        return airportType;
    }

    public void setAirportType(String airportType) {
        this.airportType = airportType == null ? null : airportType.trim();
    }

    public String getClimaticChanges() {
        return climaticChanges;
    }

    public void setClimaticChanges(String climaticChanges) {
        this.climaticChanges = climaticChanges == null ? null : climaticChanges.trim();
    }

    public String getClearanceCondition() {
        return clearanceCondition;
    }

    public void setClearanceCondition(String clearanceCondition) {
        this.clearanceCondition = clearanceCondition == null ? null : clearanceCondition.trim();
    }
}