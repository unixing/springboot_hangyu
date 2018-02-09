package org.ldd.ssm.crm.query;

import java.util.List;


public class CabinData {
	private String onGutDte;
	private String dctChr;
	private String dctPgs;
	private String leg;
	private List<CabinData> cabinDataList;
	
	public List<CabinData> getCabinDataList() {
		return cabinDataList;
	}
	public void setCabinDataList(List<CabinData> cabinDataList) {
		this.cabinDataList = cabinDataList;
	}
	public String getOnGutDte() {
		return onGutDte;
	}
	public void setOnGutDte(String onGutDte) {
		this.onGutDte = onGutDte;
	}
	public String getDctChr() {
		return dctChr;
	}
	public void setDctChr(String dctChr) {
		this.dctChr = dctChr;
	}
	public String getDctPgs() {
		return dctPgs;
	}
	public void setDctPgs(String dctPgs) {
		this.dctPgs = dctPgs;
	}
	public String getLeg() {
		return leg;
	}
	public void setLeg(String leg) {
		this.leg = leg;
	}
	
}
