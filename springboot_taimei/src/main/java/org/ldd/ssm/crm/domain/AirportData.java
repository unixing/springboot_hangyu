package org.ldd.ssm.crm.domain;


public class AirportData implements Cloneable{
	private String iata;//三字码
	private String aptChaNam; //机场名字
	private String ctyChaNam;//城市名字
	private String icao;//四字码
	private String coordinate;
	/**
	 * @return the iata
	 */
	public String getIata() {
		return iata;
	}
	/**
	 * @param iata the iata to set
	 */
	public void setIata(String iata) {
		this.iata = iata;
	}
	/**
	 * @return the aptChaNam
	 */
	public String getAptChaNam() {
		return aptChaNam;
	}
	/**
	 * @param aptChaNam the aptChaNam to set
	 */
	public void setAptChaNam(String aptChaNam) {
		this.aptChaNam = aptChaNam;
	}
	/**
	 * @return the ctyChaNam
	 */
	public String getCtyChaNam() {
		return ctyChaNam;
	}
	/**
	 * @param ctyChaNam the ctyChaNam to set
	 */
	public void setCtyChaNam(String ctyChaNam) {
		this.ctyChaNam = ctyChaNam;
	}
	
	public Object clone() {   
        try {   
            return super.clone();   
        } catch (CloneNotSupportedException e) {   
            return null;   
        }   
    }
	/**
	 * @return the icao
	 */
	public String getIcao() {
		return icao;
	}
	/**
	 * @param icao the icao to set
	 */
	public void setIcao(String icao) {
		this.icao = icao;
	}
	
	public String getCoordinate() {
		return coordinate;
	}
	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AirportData [iata=" + iata + ", aptChaNam=" + aptChaNam
				+ ", ctyChaNam=" + ctyChaNam + ", icao=" + icao
				+ ", coordinate=" + coordinate + "]";
	}
}
