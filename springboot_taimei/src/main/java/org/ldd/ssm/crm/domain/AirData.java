package org.ldd.ssm.crm.domain;

import java.util.Date;

public class AirData {
	private Date lcl_Dpt_Day;
	private double pgs_Per_Cls; // 每班旅客
	private double grp_Nbr; // 团队人数
	private int ech_Cls_Grp; // 每班团队
	private double grp_Fng_Rte; // 团队成行率
	private double grp_Ine;//团队收入
	private int totalNumber;//每班收入
	private int yBFare;//yb运价
	public Date getLcl_Dpt_Day() {
		return lcl_Dpt_Day;
	}
	public void setLcl_Dpt_Day(Date lcl_Dpt_Day) {
		this.lcl_Dpt_Day = lcl_Dpt_Day;
	}
	public double getPgs_Per_Cls() {
		return pgs_Per_Cls;
	}
	public void setPgs_Per_Cls(double pgs_Per_Cls) {
		this.pgs_Per_Cls = pgs_Per_Cls;
	}
	public double getGrp_Nbr() {
		return grp_Nbr;
	}
	public void setGrp_Nbr(double grp_Nbr) {
		this.grp_Nbr = grp_Nbr;
	}
	public int getEch_Cls_Grp() {
		return ech_Cls_Grp;
	}
	public void setEch_Cls_Grp(int ech_Cls_Grp) {
		this.ech_Cls_Grp = ech_Cls_Grp;
	}
	public double getGrp_Fng_Rte() {
		return grp_Fng_Rte;
	}
	public void setGrp_Fng_Rte(double grp_Fng_Rte) {
		this.grp_Fng_Rte = grp_Fng_Rte;
	}
	public double getGrp_Ine() {
		return grp_Ine;
	}
	public void setGrp_Ine(double grp_Ine) {
		this.grp_Ine = grp_Ine;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public int getyBFare() {
		return yBFare;
	}
	public void setyBFare(int yBFare) {
		this.yBFare = yBFare;
	}
}
