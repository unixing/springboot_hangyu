package org.ldd.ssm.crm.domain;



public class ChinaAriLineData {
	private String fromName;//去的名字
	private String toName;//到达的名字
	private Coords coords;//坐标数组
	/**
	 * @return the fromName
	 */
	public String getFromName() {
		return fromName;
	}
	/**
	 * @param fromName the fromName to set
	 */
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	/**
	 * @return the toName
	 */
	public String getToName() {
		return toName;
	}
	/**
	 * @param toName the toName to set
	 */
	public void setToName(String toName) {
		this.toName = toName;
	}
	/**
	 * @return the coords
	 */
	public Coords getCoords() {
		return coords;
	}
	/**
	 * @param coords the coords to set
	 */
	public void setCoords(Coords coords) {
		this.coords = coords;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ChinaAriLineData [fromName=" + fromName + ", toName=" + toName
				+ ", coords=" + coords + "]";
	}
	
	
}
