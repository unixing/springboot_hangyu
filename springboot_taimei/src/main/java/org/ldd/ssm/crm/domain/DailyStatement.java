package org.ldd.ssm.crm.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 省会通贵阳六盘水销售日报表基础数据
 */
public class DailyStatement {
	private Date flightDate;
	private String leg;
	private BigDecimal yPrice;
	private BigDecimal priceTeam;
	private Integer f = 0;
	private Integer y = 0;
	private Integer b = 0;
	private Integer h = 0;
	private Integer k = 0;
	private Integer l = 0;
	private Integer m = 0;
	private Integer q = 0;
	private Integer x = 0;
	private Integer u = 0;
	private Integer e = 0;
	private Integer t = 0;
	private Integer z = 0;
	private Integer g = 0;
	private Integer o = 0;
	private Integer s = 0;
	private Integer v = 0;
	private Integer yElse = 0;
	private Integer combinedGroup = 0;
	private Integer totalNumber = 0;
	private BigDecimal individualIncome = new BigDecimal(0);
	private BigDecimal teamTotalRevenue = new BigDecimal(0);
	private BigDecimal kilometerIncome = new BigDecimal(0);
	private BigDecimal averageDiscount = new BigDecimal(0);
	private BigDecimal scatteredRegimentTotalRevenue = new BigDecimal(0);
	private BigDecimal HourlyEarnings = new BigDecimal(0);
	private Integer averageLoadFactors = 0;
	private BigDecimal incomeGoalHours = new BigDecimal(0);
	private BigDecimal SubsidiesOfClass = new BigDecimal(0);
	private BigDecimal monthAccumulativetotalsubsidy = new BigDecimal(0);
	private Integer combinedTheShippingSpace = 0;
	/**
	 * 航班日期
	 * */
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getFlightDate() {
		return flightDate;
	}
	
	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * 航段
	 */
	public String getLeg() {
		return leg;
	}

	public void setLeg(String leg) {
		this.leg = leg;
	}
	/**
	 * Y舱价格
	 */
	public BigDecimal getyPrice() {
		return yPrice;
	}

	public void setyPrice(BigDecimal yPrice) {
		this.yPrice = yPrice;
	}
	/**
	 * 切位团队价格
	 */
	public BigDecimal getPriceTeam() {
		return priceTeam;
	}

	public void setPriceTeam(BigDecimal priceTeam) {
		this.priceTeam = priceTeam;
	}

	public Integer getF() {
		return f;
	}

	public void setF(Integer f) {
		this.f = f;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
		this.b = b;
	}

	public Integer getH() {
		return h;
	}

	public void setH(Integer h) {
		this.h = h;
	}

	public Integer getK() {
		return k;
	}

	public void setK(Integer k) {
		this.k = k;
	}

	public Integer getL() {
		return l;
	}

	public void setL(Integer l) {
		this.l = l;
	}

	public Integer getM() {
		return m;
	}

	public void setM(Integer m) {
		this.m = m;
	}

	public Integer getQ() {
		return q;
	}

	public void setQ(Integer q) {
		this.q = q;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getU() {
		return u;
	}

	public void setU(Integer u) {
		this.u = u;
	}

	public Integer getE() {
		return e;
	}

	public void setE(Integer e) {
		this.e = e;
	}

	public Integer getT() {
		return t;
	}

	public void setT(Integer t) {
		this.t = t;
	}

	public Integer getZ() {
		return z;
	}

	public void setZ(Integer z) {
		this.z = z;
	}

	public Integer getG() {
		return g;
	}

	public void setG(Integer g) {
		this.g = g;
	}

	public Integer getO() {
		return o;
	}

	public void setO(Integer o) {
		this.o = o;
	}

	public Integer getS() {
		return s;
	}

	public void setS(Integer s) {
		this.s = s;
	}

	public Integer getV() {
		return v;
	}

	public void setV(Integer v) {
		this.v = v;
	}

	public Integer getyElse() {
		return yElse;
	}

	public void setyElse(Integer yElse) {
		this.yElse = yElse;
	}
	/**
	 * 散团人数合计
	 */
	public Integer getCombinedGroup() {
		return combinedGroup;
	}
	/**
	 * 散团人数合计
	 */
	public void setCombinedGroup(Integer combinedGroup) {
		this.combinedGroup = combinedGroup;
	}
	/**
	 * 散客总人数
	 */
	public Integer getTotalNumber() {
		return totalNumber;
	}
	/**
	 * 散客总人数
	 */
	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}
	/**
	 * 散客总收入
	 */
	public BigDecimal getIndividualIncome() {
		return individualIncome;
	}
	/**
	 * 散客总收入
	 */
	public void setIndividualIncome(BigDecimal individualIncome) {
		this.individualIncome = individualIncome;
	}
	/**
	 * 团队总收入
	 */
	public BigDecimal getTeamTotalRevenue() {
		return teamTotalRevenue;
	}
	/**
	 * 团队总收入
	 */
	public void setTeamTotalRevenue(BigDecimal teamTotalRevenue) {
		this.teamTotalRevenue = teamTotalRevenue;
	}
	/**
	 * 座公里收入
	 */
	public BigDecimal getKilometerIncome() {
		return kilometerIncome;
	}
	/**
	 * 座公里收入
	 */
	public void setKilometerIncome(BigDecimal kilometerIncome) {
		this.kilometerIncome = kilometerIncome;
	}
	/**
	 * 平均 折扣
	 */
	public BigDecimal getAverageDiscount() {
		return averageDiscount;
	}
	/**
	 * 平均 折扣
	 */
	public void setAverageDiscount(BigDecimal averageDiscount) {
		this.averageDiscount = averageDiscount;
	}
	/**
	 * 散团总收入
	 */
	public BigDecimal getScatteredRegimentTotalRevenue() {
		return scatteredRegimentTotalRevenue;
	}
	 /**
	 * 散团总收入
	 */
	public void setScatteredRegimentTotalRevenue(
			BigDecimal scatteredRegimentTotalRevenue) {
		this.scatteredRegimentTotalRevenue = scatteredRegimentTotalRevenue;
	}
	/**
	 * 小时收入
	 */
	public BigDecimal getHourlyEarnings() {
		return HourlyEarnings;
	}
	/**
	 * 小时收入
	 */
	public void setHourlyEarnings(BigDecimal hourlyEarnings) {
		HourlyEarnings = hourlyEarnings;
	}
	/**
	 * 平均客座率
	 */
	public Integer getAverageLoadFactors() {
		return averageLoadFactors;
	}
	/**
	 * 平均客座率
	 */
	public void setAverageLoadFactors(Integer averageLoadFactors) {
		this.averageLoadFactors = averageLoadFactors;
	}
	/**
	 * 目标小时收入
	 */
	public BigDecimal getIncomeGoalHours() {
		return incomeGoalHours;
	}
	/**
	 * 目标小时收入
	 */
	public void setIncomeGoalHours(BigDecimal incomeGoalHours) {
		this.incomeGoalHours = incomeGoalHours;
	}
	/**
	 * 本班补贴
	 */
	public BigDecimal getSubsidiesOfClass() {
		return SubsidiesOfClass;
	}
	/**
	 * 本班补贴
	 */
	public void setSubsidiesOfClass(BigDecimal subsidiesOfClass) {
		SubsidiesOfClass = subsidiesOfClass;
	}
	/**
	 * 本月累计补贴
	 */
	public BigDecimal getMonthAccumulativetotalsubsidy() {
		return monthAccumulativetotalsubsidy;
	}
	/**
	 * 本月累计补贴
	 */
	public void setMonthAccumulativetotalsubsidy(
			BigDecimal monthAccumulativetotalsubsidy) {
		this.monthAccumulativetotalsubsidy = monthAccumulativetotalsubsidy;
	}

	public Integer getCombinedTheShippingSpace() {
		return combinedTheShippingSpace;
	}

	public void setCombinedTheShippingSpace(Integer combinedTheShippingSpace) {
		this.combinedTheShippingSpace = combinedTheShippingSpace;
	}

	@Override
	public String toString() {
		return "DailyStatement [flightDate=" + flightDate + ", leg=" + leg
				+ ", yPrice=" + yPrice + ", priceTeam=" + priceTeam + ", f="
				+ f + ", y=" + y + ", b=" + b + ", h=" + h + ", k=" + k
				+ ", l=" + l + ", m=" + m + ", q=" + q + ", x=" + x + ", u="
				+ u + ", e=" + e + ", t=" + t + ", z=" + z + ", g=" + g
				+ ", o=" + o + ", s=" + s + ", v=" + v + ", yElse=" + yElse
				+ ", combinedGroup=" + combinedGroup + ", totalNumber="
				+ totalNumber + ", individualIncome=" + individualIncome
				+ ", teamTotalRevenue=" + teamTotalRevenue
				+ ", kilometerIncome=" + kilometerIncome + ", averageDiscount="
				+ averageDiscount + ", scatteredRegimentTotalRevenue="
				+ scatteredRegimentTotalRevenue + ", HourlyEarnings="
				+ HourlyEarnings + ", averageLoadFactors=" + averageLoadFactors
				+ ", incomeGoalHours=" + incomeGoalHours
				+ ", SubsidiesOfClass=" + SubsidiesOfClass
				+ ", monthAccumulativetotalsubsidy="
				+ monthAccumulativetotalsubsidy + ", combinedTheShippingSpace="
				+ combinedTheShippingSpace + "]";
	}

}
