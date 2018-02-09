package org.ldd.ssm.crm.domain;

import java.util.List;

public class FlightInfo {
    private Integer id;

    private String fltRteCd;

    private String dpt_AirPt_Cd;
    
    private String arrv_Airpt_Cd;
    
    private String pas_stp;
    
    private String fltNbr;
    
    private String airport;

    private String itia;

    private Integer status;
    
    private List<FlightRoute> flts;
    
    private Integer type;

    public List<FlightRoute> getFlts() {
		return flts;
	}

	public void setFlts(List<FlightRoute> flts) {
		this.flts = flts;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFltRteCd() {
        return fltRteCd;
    }

    public void setFltRteCd(String fltRteCd) {
        this.fltRteCd = fltRteCd == null ? null : fltRteCd.trim();
    }

    public String getFltNbr() {
        return fltNbr;
    }

    public void setFltNbr(String fltNbr) {
        this.fltNbr = fltNbr == null ? null : fltNbr.trim();
    }

    public String getItia() {
        return itia;
    }

    public void setItia(String itia) {
        this.itia = itia == null ? null : itia.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public String getDpt_AirPt_Cd() {
		return dpt_AirPt_Cd;
	}

	public void setDpt_AirPt_Cd(String dpt_AirPt_Cd) {
		this.dpt_AirPt_Cd = dpt_AirPt_Cd;
	}

	public String getArrv_Airpt_Cd() {
		return arrv_Airpt_Cd;
	}

	public void setArrv_Airpt_Cd(String arrv_Airpt_Cd) {
		this.arrv_Airpt_Cd = arrv_Airpt_Cd;
	}

	public String getPas_stp() {
		return pas_stp;
	}

	public void setPas_stp(String pas_stp) {
		this.pas_stp = pas_stp;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}
	
}