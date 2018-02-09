package org.ldd.ssm.crm.domain;

import java.util.Date;

public class Passengerdetalisinfo {
	private String Pax_Chn_Nm;
	private String Pax_Id_Nbr;
	private String Pax_Enh_Nm;
	private Date Pax_Bdy;
	private int Pax_Sex;
	private String Pax_Eml;
	private String Pax_Pho;
	private String Pax_ads;
	private String Pax_Qq;
	private String Vip_Ind;
	private String Pax_Pat_No;
	private String Pax_Diy_Hat;
	private String country;
	public String getPax_Chn_Nm() {
		return Pax_Chn_Nm;
	}
	public void setPax_Chn_Nm(String pax_Chn_Nm) {
		Pax_Chn_Nm = pax_Chn_Nm;
	}
	public String getPax_Id_Nbr() {
		return Pax_Id_Nbr;
	}
	public void setPax_Id_Nbr(String pax_Id_Nbr) {
		Pax_Id_Nbr = pax_Id_Nbr;
	}
	public String getPax_Enh_Nm() {
		return Pax_Enh_Nm;
	}
	public void setPax_Enh_Nm(String pax_Enh_Nm) {
		Pax_Enh_Nm = pax_Enh_Nm;
	}
	public Date getPax_Bdy() {
		return Pax_Bdy;
	}
	public void setPax_Bdy(Date pax_Bdy) {
		Pax_Bdy = pax_Bdy;
	}
	public int getPax_Sex() {
		return Pax_Sex;
	}
	public void setPax_Sex(int pax_Sex) {
		Pax_Sex = pax_Sex;
	}
	public String getPax_Eml() {
		return Pax_Eml;
	}
	public void setPax_Eml(String pax_Eml) {
		Pax_Eml = pax_Eml;
	}
	public String getPax_Pho() {
		return Pax_Pho;
	}
	public void setPax_Pho(String pax_Pho) {
		Pax_Pho = pax_Pho;
	}
	public String getPax_ads() {
		return Pax_ads;
	}
	public void setPax_ads(String pax_ads) {
		Pax_ads = pax_ads;
	}
	public String getPax_Qq() {
		return Pax_Qq;
	}
	public void setPax_Qq(String pax_Qq) {
		Pax_Qq = pax_Qq;
	}
	public String getVip_Ind() {
		return Vip_Ind;
	}
	public void setVip_Ind(String vip_Ind) {
		Vip_Ind = vip_Ind;
	}
	public String getPax_Pat_No() {
		return Pax_Pat_No;
	}
	public void setPax_Pat_No(String pax_Pat_No) {
		Pax_Pat_No = pax_Pat_No;
	}
	public String getPax_Diy_Hat() {
		return Pax_Diy_Hat;
	}
	public void setPax_Diy_Hat(String pax_Diy_Hat) {
		Pax_Diy_Hat = pax_Diy_Hat;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	public String toString() {
		return "Passengerdetalisinfo [Pax_Chn_Nm=" + Pax_Chn_Nm
				+ ", Pax_Id_Nbr=" + Pax_Id_Nbr + ", Pax_Enh_Nm=" + Pax_Enh_Nm
				+ ", Pax_Bdy=" + Pax_Bdy + ", Pax_Sex=" + Pax_Sex
				+ ", Pax_Eml=" + Pax_Eml + ", Pax_Pho=" + Pax_Pho
				+ ", Pax_ads=" + Pax_ads + ", Pax_Qq=" + Pax_Qq + ", Vip_Ind="
				+ Vip_Ind + ", Pax_Pat_No=" + Pax_Pat_No + ", Pax_Diy_Hat="
				+ Pax_Diy_Hat + ", country=" + country + "]";
	}
}
