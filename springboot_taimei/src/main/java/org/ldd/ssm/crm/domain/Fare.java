package org.ldd.ssm.crm.domain;
/**
 * 运价domain类
 * @author Taimei
 *
 */
public class Fare {
	private String voyageCode;
	private String voyageName;
	private int yBFare;
	private int sailingDistance;
	public String getVoyageCode() {
		return voyageCode;
	}
	public void setVoyageCode(String voyageCode) {
		this.voyageCode = voyageCode;
	}
	public String getVoyageName() {
		return voyageName;
	}
	public void setVoyageName(String voyageName) {
		this.voyageName = voyageName;
	}
	public int getyBFare() {
		return yBFare;
	}
	public void setyBFare(int yBFare) {
		this.yBFare = yBFare;
	}
	public int getSailingDistance() {
		return sailingDistance;
	}
	public void setSailingDistance(int sailingDistance) {
		this.sailingDistance = sailingDistance;
	}
	@Override
	public String toString() {
		return "Fare [voyageCode=" + voyageCode + ", voyageName=" + voyageName
				+ ", yBFare=" + yBFare + ", sailingDistance=" + sailingDistance
				+ "]";
	}
}
