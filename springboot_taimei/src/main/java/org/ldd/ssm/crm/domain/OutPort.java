package org.ldd.ssm.crm.domain;
/**
 * 历史运营对象
 */
public class OutPort {
	private String hangduan;//  航段
	private String year; // 月份
	private String month; // 月份
	private int Cla_Nbr; // 班次/月
	private int tal_Nbr_Set; // 座位/月
	private int idd_moh; // 客流/月
	private int grp_moh; // 团队/月
	private int grp_Ine; // 团队收入/月/100
	private int tol_Ine; // 收入/月/1000
	private double flt_Ser_Ine; // 座公里收入*100
	private double avg_Dct; // 平均折扣
	private double idd_Dct; // 散客折扣
	private double grp_Dct; // 团队折扣
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getCla_Nbr() {
		return Cla_Nbr;
	}
	public void setCla_Nbr(int cla_Nbr) {
		Cla_Nbr = cla_Nbr;
	}
	public int getTal_Nbr_Set() {
		return tal_Nbr_Set;
	}
	public void setTal_Nbr_Set(int tal_Nbr_Set) {
		this.tal_Nbr_Set = tal_Nbr_Set;
	}
	public int getIdd_moh() {
		return idd_moh;
	}
	public void setIdd_moh(int idd_moh) {
		this.idd_moh = idd_moh;
	}
	public int getGrp_moh() {
		return grp_moh;
	}
	public void setGrp_moh(int grp_moh) {
		this.grp_moh = grp_moh;
	}
	public int getGrp_Ine() {
		return grp_Ine;
	}
	public void setGrp_Ine(int grp_Ine) {
		this.grp_Ine = grp_Ine;
	}
	public int getTol_Ine() {
		return tol_Ine;
	}
	public void setTol_Ine(int tol_Ine) {
		this.tol_Ine = tol_Ine;
	}
	
	public double getFlt_Ser_Ine() {
		return flt_Ser_Ine;
	}
	public void setFlt_Ser_Ine(double flt_Ser_Ine) {
		this.flt_Ser_Ine = flt_Ser_Ine;
	}
	public double getAvg_Dct() {
		return avg_Dct;
	}
	public void setAvg_Dct(double avg_Dct) {
		this.avg_Dct = avg_Dct;
	}
	public double getIdd_Dct() {
		return idd_Dct;
	}
	public void setIdd_Dct(double idd_Dct) {
		this.idd_Dct = idd_Dct;
	}
	public double getGrp_Dct() {
		return grp_Dct;
	}
	public void setGrp_Dct(double grp_Dct) {
		this.grp_Dct = grp_Dct;
	}
	
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * @return the hangduan
	 */
	public String getHangduan() {
		return hangduan;
	}
	/**
	 * @param hangduan the hangduan to set
	 */
	public void setHangduan(String hangduan) {
		this.hangduan = hangduan;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OutPort [hangduan=" + hangduan + ", year=" + year + ", month="
				+ month + ", Cla_Nbr=" + Cla_Nbr + ", tal_Nbr_Set="
				+ tal_Nbr_Set + ", idd_moh=" + idd_moh + ", grp_moh=" + grp_moh
				+ ", grp_Ine=" + grp_Ine + ", tol_Ine=" + tol_Ine
				+ ", flt_Ser_Ine=" + flt_Ser_Ine + ", avg_Dct=" + avg_Dct
				+ ", idd_Dct=" + idd_Dct + ", grp_Dct=" + grp_Dct + "]";
	}
	
	
}
