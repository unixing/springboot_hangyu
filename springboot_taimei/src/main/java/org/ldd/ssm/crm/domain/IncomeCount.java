package org.ldd.ssm.crm.domain;

public class IncomeCount {
	private String method;//月份
	private String clazz = "0";//班次
	private String costClz = "0";//成本班
	private String fixedCose = "0";//固定成本
	private String hour = "0";//小时数
	private String costHour = "0";//成本小时
	private String incomeHour = "0";//营收
	private String income = "0";//经营收入
	private String subsidyClz = "0";//补贴班
	private String subsidyMethod = "0";//补贴月
	private String parlor = "0";//客座
	private String agencePrice;//代理费率
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getCostClz() {
		return costClz;
	}
	public void setCostClz(String costClz) {
		this.costClz = costClz;
	}
	public String getFixedCose() {
		return fixedCose;
	}
	public void setFixedCose(String fixedCose) {
		this.fixedCose = fixedCose;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getCostHour() {
		return costHour;
	}
	public void setCostHour(String costHour) {
		this.costHour = costHour;
	}
	public String getIncomeHour() {
		return incomeHour;
	}
	public void setIncomeHour(String incomeHour) {
		this.incomeHour = incomeHour;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getSubsidyClz() {
		return subsidyClz;
	}
	public void setSubsidyClz(String subsidyClz) {
		this.subsidyClz = subsidyClz;
	}
	public String getSubsidyMethod() {
		return subsidyMethod;
	}
	public void setSubsidyMethod(String subsidyMethod) {
		this.subsidyMethod = subsidyMethod;
	}
	public String getParlor() {
		return parlor;
	}
	public void setParlor(String parlor) {
		this.parlor = parlor;
	}
	
	/**
	 * @return the agencePrice
	 */
	public String getAgencePrice() {
		return agencePrice;
	}
	/**
	 * @param agencePrice the agencePrice to set
	 */
	public void setAgencePrice(String agencePrice) {
		this.agencePrice = agencePrice;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IncomeCount [method=" + method + ", clazz=" + clazz
				+ ", costClz=" + costClz + ", fixedCose=" + fixedCose
				+ ", hour=" + hour + ", costHour=" + costHour + ", incomeHour="
				+ incomeHour + ", income=" + income + ", subsidyClz="
				+ subsidyClz + ", subsidyMethod=" + subsidyMethod + ", parlor="
				+ parlor + ", agencePrice=" + agencePrice + "]";
	}
	
}
