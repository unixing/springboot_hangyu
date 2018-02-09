package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class ProvideWeatherInformation implements Serializable {
    private Long id;

    private String iata;

    private String namerelatedmeteorologicalstation;

    private String meteorologicalservicetime;

    private String responsiblefortheediting;

    private String trendforecastinterval;

    private String providedconsultingservices;

    private String flightfileuselanguage;

    private String chartsotherinformation;

    private String provideauxiliaryequipmentmeteorologicalinformation;

    private String provideweatherinformationairtrafficservicesunit;

    private String receivemeteorologicalinformationofairtrafficservicesunit;

    private String receivetheauxiliaryequipmentofmeteorologicalinformation;

    private String otherinformation;

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

    public String getNamerelatedmeteorologicalstation() {
        return namerelatedmeteorologicalstation;
    }

    public void setNamerelatedmeteorologicalstation(String namerelatedmeteorologicalstation) {
        this.namerelatedmeteorologicalstation = namerelatedmeteorologicalstation == null ? null : namerelatedmeteorologicalstation.trim();
    }

    public String getMeteorologicalservicetime() {
        return meteorologicalservicetime;
    }

    public void setMeteorologicalservicetime(String meteorologicalservicetime) {
        this.meteorologicalservicetime = meteorologicalservicetime == null ? null : meteorologicalservicetime.trim();
    }

    public String getResponsiblefortheediting() {
        return responsiblefortheediting;
    }

    public void setResponsiblefortheediting(String responsiblefortheediting) {
        this.responsiblefortheediting = responsiblefortheediting == null ? null : responsiblefortheediting.trim();
    }

    public String getTrendforecastinterval() {
        return trendforecastinterval;
    }

    public void setTrendforecastinterval(String trendforecastinterval) {
        this.trendforecastinterval = trendforecastinterval == null ? null : trendforecastinterval.trim();
    }

    public String getProvidedconsultingservices() {
        return providedconsultingservices;
    }

    public void setProvidedconsultingservices(String providedconsultingservices) {
        this.providedconsultingservices = providedconsultingservices == null ? null : providedconsultingservices.trim();
    }

    public String getFlightfileuselanguage() {
        return flightfileuselanguage;
    }

    public void setFlightfileuselanguage(String flightfileuselanguage) {
        this.flightfileuselanguage = flightfileuselanguage == null ? null : flightfileuselanguage.trim();
    }

    public String getChartsotherinformation() {
        return chartsotherinformation;
    }

    public void setChartsotherinformation(String chartsotherinformation) {
        this.chartsotherinformation = chartsotherinformation == null ? null : chartsotherinformation.trim();
    }

    public String getProvideauxiliaryequipmentmeteorologicalinformation() {
        return provideauxiliaryequipmentmeteorologicalinformation;
    }

    public void setProvideauxiliaryequipmentmeteorologicalinformation(String provideauxiliaryequipmentmeteorologicalinformation) {
        this.provideauxiliaryequipmentmeteorologicalinformation = provideauxiliaryequipmentmeteorologicalinformation == null ? null : provideauxiliaryequipmentmeteorologicalinformation.trim();
    }

    public String getProvideweatherinformationairtrafficservicesunit() {
        return provideweatherinformationairtrafficservicesunit;
    }

    public void setProvideweatherinformationairtrafficservicesunit(String provideweatherinformationairtrafficservicesunit) {
        this.provideweatherinformationairtrafficservicesunit = provideweatherinformationairtrafficservicesunit == null ? null : provideweatherinformationairtrafficservicesunit.trim();
    }

    public String getReceivemeteorologicalinformationofairtrafficservicesunit() {
        return receivemeteorologicalinformationofairtrafficservicesunit;
    }

    public void setReceivemeteorologicalinformationofairtrafficservicesunit(String receivemeteorologicalinformationofairtrafficservicesunit) {
        this.receivemeteorologicalinformationofairtrafficservicesunit = receivemeteorologicalinformationofairtrafficservicesunit == null ? null : receivemeteorologicalinformationofairtrafficservicesunit.trim();
    }

    public String getReceivetheauxiliaryequipmentofmeteorologicalinformation() {
        return receivetheauxiliaryequipmentofmeteorologicalinformation;
    }

    public void setReceivetheauxiliaryequipmentofmeteorologicalinformation(String receivetheauxiliaryequipmentofmeteorologicalinformation) {
        this.receivetheauxiliaryequipmentofmeteorologicalinformation = receivetheauxiliaryequipmentofmeteorologicalinformation == null ? null : receivetheauxiliaryequipmentofmeteorologicalinformation.trim();
    }

    public String getOtherinformation() {
        return otherinformation;
    }

    public void setOtherinformation(String otherinformation) {
        this.otherinformation = otherinformation == null ? null : otherinformation.trim();
    }
}