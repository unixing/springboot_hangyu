package org.ldd.ssm.crm.domain;

public class XcAirFareComItem {
    private Long id;

    private String sourceLocal;

    private String sourceLocal3code;

    private String sourceAirport3code;

    private String sourceAirport;

    private String sourceDepart;

    private String stopoverLocal;

    private String stopoverArrive;

    private String stopoverDepart;

    private String destinationLocal;

    private String destinationLocal3code;

    private String destinationAirport;

    private String destinationAirport3code;

    private String destinationArrive;

    private String searchDate;

    private String airlineName;

    private String flightNo;

    private String aircraftCode;

    private String ontimePercents;

    private String fullPrice;

    private String lowestPrice;

    private String crawlerTime;

    private String crawlerIp;

    private String crawlerAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceLocal() {
        return sourceLocal;
    }

    public void setSourceLocal(String sourceLocal) {
        this.sourceLocal = sourceLocal == null ? null : sourceLocal.trim();
    }

    public String getSourceLocal3code() {
        return sourceLocal3code;
    }

    public void setSourceLocal3code(String sourceLocal3code) {
        this.sourceLocal3code = sourceLocal3code == null ? null : sourceLocal3code.trim();
    }

    public String getSourceAirport3code() {
        return sourceAirport3code;
    }

    public void setSourceAirport3code(String sourceAirport3code) {
        this.sourceAirport3code = sourceAirport3code == null ? null : sourceAirport3code.trim();
    }

    public String getSourceAirport() {
        return sourceAirport;
    }

    public void setSourceAirport(String sourceAirport) {
        this.sourceAirport = sourceAirport == null ? null : sourceAirport.trim();
    }

    public String getSourceDepart() {
        return sourceDepart;
    }

    public void setSourceDepart(String sourceDepart) {
        this.sourceDepart = sourceDepart == null ? null : sourceDepart.trim();
    }

    public String getStopoverLocal() {
        return stopoverLocal;
    }

    public void setStopoverLocal(String stopoverLocal) {
        this.stopoverLocal = stopoverLocal == null ? null : stopoverLocal.trim();
    }

    public String getStopoverArrive() {
        return stopoverArrive;
    }

    public void setStopoverArrive(String stopoverArrive) {
        this.stopoverArrive = stopoverArrive == null ? null : stopoverArrive.trim();
    }

    public String getStopoverDepart() {
        return stopoverDepart;
    }

    public void setStopoverDepart(String stopoverDepart) {
        this.stopoverDepart = stopoverDepart == null ? null : stopoverDepart.trim();
    }

    public String getDestinationLocal() {
        return destinationLocal;
    }

    public void setDestinationLocal(String destinationLocal) {
        this.destinationLocal = destinationLocal == null ? null : destinationLocal.trim();
    }

    public String getDestinationLocal3code() {
        return destinationLocal3code;
    }

    public void setDestinationLocal3code(String destinationLocal3code) {
        this.destinationLocal3code = destinationLocal3code == null ? null : destinationLocal3code.trim();
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport == null ? null : destinationAirport.trim();
    }

    public String getDestinationAirport3code() {
        return destinationAirport3code;
    }

    public void setDestinationAirport3code(String destinationAirport3code) {
        this.destinationAirport3code = destinationAirport3code == null ? null : destinationAirport3code.trim();
    }

    public String getDestinationArrive() {
        return destinationArrive;
    }

    public void setDestinationArrive(String destinationArrive) {
        this.destinationArrive = destinationArrive == null ? null : destinationArrive.trim();
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate == null ? null : searchDate.trim();
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName == null ? null : airlineName.trim();
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo == null ? null : flightNo.trim();
    }

    public String getAircraftCode() {
        return aircraftCode;
    }

    public void setAircraftCode(String aircraftCode) {
        this.aircraftCode = aircraftCode == null ? null : aircraftCode.trim();
    }

    public String getOntimePercents() {
        return ontimePercents;
    }

    public void setOntimePercents(String ontimePercents) {
        this.ontimePercents = ontimePercents == null ? null : ontimePercents.trim();
    }

    public String getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(String fullPrice) {
        this.fullPrice = fullPrice == null ? null : fullPrice.trim();
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice == null ? null : lowestPrice.trim();
    }

    public String getCrawlerTime() {
        return crawlerTime;
    }

    public void setCrawlerTime(String crawlerTime) {
        this.crawlerTime = crawlerTime == null ? null : crawlerTime.trim();
    }

    public String getCrawlerIp() {
        return crawlerIp;
    }

    public void setCrawlerIp(String crawlerIp) {
        this.crawlerIp = crawlerIp == null ? null : crawlerIp.trim();
    }

    public String getCrawlerAddress() {
        return crawlerAddress;
    }

    public void setCrawlerAddress(String crawlerAddress) {
        this.crawlerAddress = crawlerAddress == null ? null : crawlerAddress.trim();
    }
}