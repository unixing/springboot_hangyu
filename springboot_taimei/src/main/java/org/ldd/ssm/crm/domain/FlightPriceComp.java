package org.ldd.ssm.crm.domain;

public class FlightPriceComp {
    private Long id;

    private String compareDate;

    private String currentPrice;

    private Long tktincomeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompareDate() {
        return compareDate;
    }

    public void setCompareDate(String compareDate) {
        this.compareDate = compareDate == null ? null : compareDate.trim();
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice == null ? null : currentPrice.trim();
    }

    public Long getTktincomeId() {
        return tktincomeId;
    }

    public void setTktincomeId(Long tktincomeId) {
        this.tktincomeId = tktincomeId;
    }
}