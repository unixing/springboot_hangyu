package org.ldd.ssm.crm.domain;

import java.util.Arrays;


public class Coords {
	private String [] fromName;
	private String [] toName;
	/**
	 * @return the fromName
	 */
	public String[] getFromName() {
		return fromName;
	}
	/**
	 * @param fromName the fromName to set
	 */
	public void setFromName(String[] fromName) {
		this.fromName = fromName;
	}
	/**
	 * @return the toName
	 */
	public String[] getToName() {
		return toName;
	}
	/**
	 * @param toName the toName to set
	 */
	public void setToName(String[] toName) {
		this.toName = toName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Coords [fromName=" + Arrays.toString(fromName) + ", toName="
				+ Arrays.toString(toName) + "]";
	}
	
}
