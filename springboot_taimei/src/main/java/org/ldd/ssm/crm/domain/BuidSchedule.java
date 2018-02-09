package org.ldd.ssm.crm.domain;
public class BuidSchedule {
    private Integer id;

    private String airportName;

    private String initBuidDate;

    private String acceptDate;

    private String testFlightDate;

    private String planedShipDate;

    private String destination;

    private Integer targetThroughput;

    private String awardingPolicy;
    
    private String revisedDate;

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

    public String getInitBuidDate() {
		return initBuidDate;
	}

	public void setInitBuidDate(String initBuidDate) {
		this.initBuidDate = initBuidDate;
	}

	public String getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
	}

	public String getTestFlightDate() {
		return testFlightDate;
	}

	public void setTestFlightDate(String testFlightDate) {
		this.testFlightDate = testFlightDate;
	}

	public String getPlanedShipDate() {
		return planedShipDate;
	}

	public void setPlanedShipDate(String planedShipDate) {
		this.planedShipDate = planedShipDate;
	}

	public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }

    public Integer getTargetThroughput() {
        return targetThroughput;
    }

    public void setTargetThroughput(Integer targetThroughput) {
        this.targetThroughput = targetThroughput;
    }

    public String getAwardingPolicy() {
        return awardingPolicy;
    }

    public void setAwardingPolicy(String awardingPolicy) {
        this.awardingPolicy = awardingPolicy == null ? null : awardingPolicy.trim();
    }

	public String getRevisedDate() {
		return revisedDate;
	}

	public void setRevisedDate(String revisedDate) {
		this.revisedDate = revisedDate;
	}
    
}