package org.ldd.ssm.crm.domain;

public class MonthSalePlan {
	private int id;
	private int timeId;
	private String agreement;
	private int fITNbr;
	private double allFold;
	private double lowestSale;
	private int groupNbr;
	private int fITSaleCycle;
	private double discountReturn;
	private int groupSaleCycle;
	private int estPosiNbr;
	private String estDisposePlan;
	private int frequency;
	private String fltNbr;
	private int status;
	public int getTimeId() {
		return timeId;
	}
	public void setTimeId(int timeId) {
		this.timeId = timeId;
	}
	public String getAgreement() {
		return agreement;
	}
	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}
	public int getfITNbr() {
		return fITNbr;
	}
	public void setfITNbr(int fITNbr) {
		this.fITNbr = fITNbr;
	}
	public double getAllFold() {
		return allFold;
	}
	public void setAllFold(double allFold) {
		this.allFold = allFold;
	}
	public double getLowestSale() {
		return lowestSale;
	}
	public void setLowestSale(double lowestSale) {
		this.lowestSale = lowestSale;
	}
	public int getGroupNbr() {
		return groupNbr;
	}
	public void setGroupNbr(int groupNbr) {
		this.groupNbr = groupNbr;
	}
	public double getDiscountReturn() {
		return discountReturn;
	}
	public void setDiscountReturn(double discountReturn) {
		this.discountReturn = discountReturn;
	}
	public int getGroupSaleCycle() {
		return groupSaleCycle;
	}
	public void setGroupSaleCycle(int groupSaleCycle) {
		this.groupSaleCycle = groupSaleCycle;
	}
	public int getEstPosiNbr() {
		return estPosiNbr;
	}
	public void setEstPosiNbr(int estPosiNbr) {
		this.estPosiNbr = estPosiNbr;
	}
	public String getEstDisposePlan() {
		return estDisposePlan;
	}
	public void setEstDisposePlan(String estDisposePlan) {
		this.estDisposePlan = estDisposePlan;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public int getfITSaleCycle() {
		return fITSaleCycle;
	}
	public void setfITSaleCycle(int fITSaleCycle) {
		this.fITSaleCycle = fITSaleCycle;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFltNbr() {
		return fltNbr;
	}
	public void setFltNbr(String fltNbr) {
		this.fltNbr = fltNbr;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "MonthSalePlan [id=" + id + ", timeId=" + timeId
				+ ", agreement=" + agreement + ", fITNbr=" + fITNbr
				+ ", allFold=" + allFold + ", lowestSale=" + lowestSale
				+ ", groupNbr=" + groupNbr + ", fITSaleCycle=" + fITSaleCycle
				+ ", discountReturn=" + discountReturn + ", groupSaleCycle="
				+ groupSaleCycle + ", estPosiNbr=" + estPosiNbr
				+ ", estDisposePlan=" + estDisposePlan + ", frequency="
				+ frequency + "]";
	}
}
