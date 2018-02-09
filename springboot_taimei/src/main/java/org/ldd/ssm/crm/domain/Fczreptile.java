package org.ldd.ssm.crm.domain;

public class Fczreptile {
    private Long id;

    private String itia;

    private String eteAirQty;

    private String ithad;

    private String ofgIad;

    private String dysWinTwo;

    private String dysWinTwoFour;

    private String avgDysWin;

    private String month;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItia() {
        return itia;
    }

    public void setItia(String itia) {
        this.itia = itia == null ? null : itia.trim();
    }

    public String getEteAirQty() {
        return eteAirQty;
    }

    public void setEteAirQty(String eteAirQty) {
        this.eteAirQty = eteAirQty == null ? null : eteAirQty.trim();
    }

    public String getIthad() {
        return ithad;
    }

    public void setIthad(String ithad) {
        this.ithad = ithad == null ? null : ithad.trim();
    }

    public String getOfgIad() {
        return ofgIad;
    }

    public void setOfgIad(String ofgIad) {
        this.ofgIad = ofgIad == null ? null : ofgIad.trim();
    }

    public String getDysWinTwo() {
        return dysWinTwo;
    }

    public void setDysWinTwo(String dysWinTwo) {
        this.dysWinTwo = dysWinTwo == null ? null : dysWinTwo.trim();
    }

    public String getDysWinTwoFour() {
        return dysWinTwoFour;
    }

    public void setDysWinTwoFour(String dysWinTwoFour) {
        this.dysWinTwoFour = dysWinTwoFour == null ? null : dysWinTwoFour.trim();
    }

    public String getAvgDysWin() {
        return avgDysWin;
    }

    public void setAvgDysWin(String avgDysWin) {
        this.avgDysWin = avgDysWin == null ? null : avgDysWin.trim();
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }
}