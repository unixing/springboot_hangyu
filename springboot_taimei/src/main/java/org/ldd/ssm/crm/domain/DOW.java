package org.ldd.ssm.crm.domain;

public class DOW {
	private String air;
	private int executeClz;//执行班次
	private int ClzPer;//均班人数
	private String method;//月份
	private String information;
	private int monTotal;//星期一
	private int tueTotal;//星期二
	private int wedTotal;//星期三
	private int thurTotal;//星期四
	private int friTotal;//星期五
	private int satTotal;//星期六
	private int sunTotal;//星期日
	

	public int getExecuteClz() {
		return executeClz;
	}

	public void setExecuteClz(int executeClz) {
		this.executeClz = executeClz;
	}

	public int getClzPer() {
		return ClzPer;
	}

	public void setClzPer(int clzPer) {
		ClzPer = clzPer;
	}

	public String getAir() {
		return air;
	}

	public void setAir(String air) {
		this.air = air;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public int getMonTotal() {
		return monTotal;
	}

	public void setMonTotal(int monTotal) {
		this.monTotal = monTotal;
	}

	public int getTueTotal() {
		return tueTotal;
	}

	public void setTueTotal(int tueTotal) {
		this.tueTotal = tueTotal;
	}

	public int getWedTotal() {
		return wedTotal;
	}

	public void setWedTotal(int wedTotal) {
		this.wedTotal = wedTotal;
	}

	public int getThurTotal() {
		return thurTotal;
	}

	public void setThurTotal(int thurTotal) {
		this.thurTotal = thurTotal;
	}

	public int getFriTotal() {
		return friTotal;
	}

	public void setFriTotal(int friTotal) {
		this.friTotal = friTotal;
	}

	public int getSatTotal() {
		return satTotal;
	}

	public void setSatTotal(int satTotal) {
		this.satTotal = satTotal;
	}

	public int getSunTotal() {
		return sunTotal;
	}

	public void setSunTotal(int sunTotal) {
		this.sunTotal = sunTotal;
	}

	@Override
	public String toString() {
		return "DOW [air=" + air + ", executeClz=" + executeClz + ", ClzPer="
				+ ClzPer + ", method=" + method + ", information="
				+ information + ", monTotal=" + monTotal + ", tueTotal="
				+ tueTotal + ", wedTotal=" + wedTotal + ", thurTotal="
				+ thurTotal + ", friTotal=" + friTotal + ", satTotal="
				+ satTotal + ", sunTotal=" + sunTotal + "]";
	}
	
	
}
