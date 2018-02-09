package org.ldd.ssm.crm.query;

import java.util.List;

public class FlightPriceCompDto {
    private Long id;

    private String compareDate;

    private String currentPrice;
    
    private String fltNbr;

    private String dptAirptCd;

    private String arrvAirptCd;

    private Long tktincomeId;
    
    private List<LatestAirFare> currentFlightPriceList;
    
    
    
    public String getFltNbr() {
		return fltNbr;
	}

	public void setFltNbr(String fltNbr) {
		this.fltNbr = fltNbr;
	}

	public String getDptAirptCd() {
		return dptAirptCd;
	}

	public void setDptAirptCd(String dptAirptCd) {
		this.dptAirptCd = dptAirptCd;
	}

	public String getArrvAirptCd() {
		return arrvAirptCd;
	}

	public void setArrvAirptCd(String arrvAirptCd) {
		this.arrvAirptCd = arrvAirptCd;
	}

	public List<LatestAirFare> getCurrentFlightPriceList() {
		return currentFlightPriceList;
	}

	public void setCurrentFlightPriceList(List<LatestAirFare> currentFlightPriceList) {
		this.currentFlightPriceList = currentFlightPriceList;
	}

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