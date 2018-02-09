package org.ldd.ssm.crm.domain;

public class DivideSeason {
	private int divideId;
	private String divideMonth;
	private String dptAirptCd;
	private String arrvAirptCd;
	private String fltRteCd;
	private String fltNbr;
	private int days;
	private double yoyTrans;
	private double qoqTrans;
	private double currTrans;
	private int avgGoCust;
	private int avgReturnCust;
	private double toForwardOffset;
	private double toReverseOffset;
	private double backForwardOffset;
	private double backReverseOffset;
	private String description;
	private String refFlt;
	public int getDivideId() {
		return divideId;
	}
	public void setDivideId(int divideId) {
		this.divideId = divideId;
	}
	public String getDivideMonth() {
		return divideMonth;
	}
	public void setDivideMonth(String divideMonth) {
		this.divideMonth = divideMonth;
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
	public String getFltRteCd() {
		return fltRteCd;
	}
	public void setFltRteCd(String fltRteCd) {
		this.fltRteCd = fltRteCd;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public double getYoyTrans() {
		return yoyTrans;
	}
	public void setYoyTrans(double yoyTrans) {
		this.yoyTrans = yoyTrans;
	}
	public double getQoqTrans() {
		return qoqTrans;
	}
	public void setQoqTrans(double qoqTrans) {
		this.qoqTrans = qoqTrans;
	}
	public double getCurrTrans() {
		return currTrans;
	}
	public void setCurrTrans(double currTrans) {
		this.currTrans = currTrans;
	}
	public int getAvgGoCust() {
		return avgGoCust;
	}
	public void setAvgGoCust(int avgGoCust) {
		this.avgGoCust = avgGoCust;
	}
	public int getAvgReturnCust() {
		return avgReturnCust;
	}
	public void setAvgReturnCust(int avgReturnCust) {
		this.avgReturnCust = avgReturnCust;
	}
	public double getToForwardOffset() {
		return toForwardOffset;
	}
	public void setToForwardOffset(double toForwardOffset) {
		this.toForwardOffset = toForwardOffset;
	}
	public double getToReverseOffset() {
		return toReverseOffset;
	}
	public void setToReverseOffset(double toReverseOffset) {
		this.toReverseOffset = toReverseOffset;
	}
	public double getBackForwardOffset() {
		return backForwardOffset;
	}
	public void setBackForwardOffset(double backForwardOffset) {
		this.backForwardOffset = backForwardOffset;
	}
	public double getBackReverseOffset() {
		return backReverseOffset;
	}
	public void setBackReverseOffset(double backReverseOffset) {
		this.backReverseOffset = backReverseOffset;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFltNbr() {
		return fltNbr;
	}
	public void setFltNbr(String fltNbr) {
		this.fltNbr = fltNbr;
	}
	
	public String getRefFlt() {
		return refFlt;
	}
	public void setRefFlt(String refFlt) {
		this.refFlt = refFlt;
	}
	@Override
	public String toString() {
		return "DivideSeason [divideId=" + divideId + ", divideMonth="
				+ divideMonth + ", dptAirptCd=" + dptAirptCd + ", arrvAirptCd="
				+ arrvAirptCd + ", fltRteCd=" + fltRteCd + ", fltNbr=" + fltNbr
				+ ", days=" + days + ", yoyTrans=" + yoyTrans + ", qoqTrans="
				+ qoqTrans + ", currTrans=" + currTrans + ", avgGoCust="
				+ avgGoCust + ", avgReturnCust=" + avgReturnCust
				+ ", toForwardOffset=" + toForwardOffset + ", toReverseOffset="
				+ toReverseOffset + ", backForwardOffset=" + backForwardOffset
				+ ", backReverseOffset=" + backReverseOffset + ", description="
				+ description + ", refFlt=" + refFlt + "]";
	}
}
