package org.ldd.ssm.crm.domain;

public class FocusFlight {
    private Integer id;

    private String flightNumber;

    private Long employeeId;
    
    private String dptAirport;
    
    private String arrvAirport;
    
    private String pasAirport;
    
    private String createTime;
    
    private int state;

    public FocusFlight(){};
    
    public FocusFlight(String flightNumber,int state){
    	this.flightNumber = flightNumber;
    	this.state = state;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber == null ? null : flightNumber.trim();
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

	public String getDptAirport() {
		return dptAirport;
	}

	public void setDptAirport(String dptAirport) {
		this.dptAirport = dptAirport;
	}

	public String getArrvAirport() {
		return arrvAirport;
	}

	public void setArrvAirport(String arrvAirport) {
		this.arrvAirport = arrvAirport;
	}

	public String getPasAirport() {
		return pasAirport;
	}

	public void setPasAirport(String pasAirport) {
		this.pasAirport = pasAirport;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "FocusFlight [id=" + id + ", flightNumber=" + flightNumber
				+ ", employeeId=" + employeeId + ", dptAirport=" + dptAirport
				+ ", arrvAirport=" + arrvAirport + ", pasAirport=" + pasAirport
				+ ", createTime=" +createTime+ ", state=" + state + "]";
	}
}