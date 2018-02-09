package org.ldd.ssm.crm.domain;
/**
 * 机场跑道明细表0714
 * @author wxm
 *
 * @date 2017-8-10
 */
public class Runwaythedetail {
	private long id;
	private String	runwayNumber;//跑道编号',
	private String runwayLvl;//跑道等级',
	private String runwayLENGTH;//跑道长',
	private String runwayWidth;//跑道宽',
	private long airportinfonewthreeId;//机场主键id'
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRunwayNumber() {
		return runwayNumber;
	}
	public void setRunwayNumber(String runwayNumber) {
		this.runwayNumber = runwayNumber;
	}
	public String getRunwayLvl() {
		return runwayLvl;
	}
	public void setRunwayLvl(String runwayLvl) {
		this.runwayLvl = runwayLvl;
	}
	public String getRunwayLENGTH() {
		return runwayLENGTH;
	}
	public void setRunwayLENGTH(String runwayLENGTH) {
		this.runwayLENGTH = runwayLENGTH;
	}
	public String getRunwayWidth() {
		return runwayWidth;
	}
	public void setRunwayWidth(String runwayWidth) {
		this.runwayWidth = runwayWidth;
	}
	public long getAirportinfonewthreeId() {
		return airportinfonewthreeId;
	}
	public void setAirportinfonewthreeId(long airportinfonewthreeId) {
		this.airportinfonewthreeId = airportinfonewthreeId;
	}
}
