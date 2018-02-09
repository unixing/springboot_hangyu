package org.ldd.ssm.crm.domain;
/**
 * 均每班对象
 */
public class EvenPort {
	private String hangduan;//  航段
	private String month; // 日期
	private String year; // 日期
	private int tme_Nbr; // 天数
	private int cla_Nbr; // 班次/月
	private int tme_Cla_Moh; // 日班次
	private int cla_Set; // 每班座位
	private int cla_Per; // 每班旅客
	private int cla_Grp; // 每班团队
	private double flt_Ser_Ine; // 团队收入/100
	private double lod_For; // 客座率
	private int idd_Dct; // 每班收入/1000
	private double tal_Ine; //总收入
	private int tal_count; //总客量
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getTme_Nbr() {
		return tme_Nbr;
	}
	public void setTme_Nbr(int tme_Nbr) {
		this.tme_Nbr = tme_Nbr;
	}
	public int getCla_Nbr() {
		return cla_Nbr;
	}
	public void setCla_Nbr(int cla_Nbr) {
		this.cla_Nbr = cla_Nbr;
	}
	public int getTme_Cla_Moh() {
		return tme_Cla_Moh;
	}
	public void setTme_Cla_Moh(int tme_Cla_Moh) {
		this.tme_Cla_Moh = tme_Cla_Moh;
	}
	public int getCla_Set() {
		return cla_Set;
	}
	public void setCla_Set(int cla_Set) {
		this.cla_Set = cla_Set;
	}
	public int getCla_Per() {
		return cla_Per;
	}
	public void setCla_Per(int cla_Per) {
		this.cla_Per = cla_Per;
	}
	public int getCla_Grp() {
		return cla_Grp;
	}
	public void setCla_Grp(int cla_Grp) {
		this.cla_Grp = cla_Grp;
	}
	public double getFlt_Ser_Ine() {
		return flt_Ser_Ine;
	}
	public void setFlt_Ser_Ine(double flt_Ser_Ine) {
		this.flt_Ser_Ine = flt_Ser_Ine;
	}
	public double getLod_For() {
		return lod_For;
	}
	public void setLod_For(double lod_For) {
		this.lod_For = lod_For;
	}
	public int getIdd_Dct() {
		return idd_Dct;
	}
	public void setIdd_Dct(int idd_Dct) {
		this.idd_Dct = idd_Dct;
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
	
	public double getTal_Ine() {
		return tal_Ine;
	}
	public void setTal_Ine(double tal_Ine) {
		this.tal_Ine = tal_Ine;
	}
	public int getTal_count() {
		return tal_count;
	}
	public void setTal_count(int tal_count) {
		this.tal_count = tal_count;
	}
	@Override
	public String toString() {
		return "EvenPort [hangduan=" + hangduan + ", month=" + month
				+ ", year=" + year + ", tme_Nbr=" + tme_Nbr + ", cla_Nbr="
				+ cla_Nbr + ", tme_Cla_Moh=" + tme_Cla_Moh + ", cla_Set="
				+ cla_Set + ", cla_Per=" + cla_Per + ", cla_Grp=" + cla_Grp
				+ ", flt_Ser_Ine=" + flt_Ser_Ine + ", lod_For=" + lod_For
				+ ", idd_Dct=" + idd_Dct + ", tal_Ine=" + tal_Ine
				+ ", tal_count=" + tal_count + "]";
	}
	
}
