package org.ldd.ssm.crm.query;

/**
 * 每个航段的收益预估列表
 * * @author wxm
 *
 * @date 2017-7-26
 */
public class ProfitFocast {
	private String leg;//三字码-三字码  前端只需要这个 就可以转为中文名字
    private String flighPerMonth;//班次/月
    private String avgSites;//均班座位
    private String avgVol;//均班客量(团/散)
    private String plf;//客座率
    private Float avgIncome;//均班收入(团/散)
    private String KmIncome;//座公里收入
    private String avgDiscount;//平均折扣(团/散)
	public String getLeg() {
		return leg;
	}
	public void setLeg(String leg) {
		this.leg = leg;
	}
	public String getFlighPerMonth() {
		return flighPerMonth;
	}
	public void setFlighPerMonth(String flighPerMonth) {
		this.flighPerMonth = flighPerMonth;
	}
	public String getAvgSites() {
		return avgSites;
	}
	public void setAvgSites(String avgSites) {
		this.avgSites = avgSites;
	}
	public String getAvgVol() {
		return avgVol;
	}
	public void setAvgVol(String avgVol) {
		this.avgVol = avgVol;
	}
	public String getPlf() {
		return plf;
	}
	public void setPlf(String plf) {
		this.plf = plf;
	}
	public Float getAvgIncome() {
		return avgIncome;
	}
	public void setAvgIncome(Float avgIncome) {
		this.avgIncome = avgIncome;
	}
	public String getKmIncome() {
		return KmIncome;
	}
	public void setKmIncome(String kmIncome) {
		KmIncome = kmIncome;
	}
	public String getAvgDiscount() {
		return avgDiscount;
	}
	public void setAvgDiscount(String avgDiscount) {
		this.avgDiscount = avgDiscount;
	}

	
}
