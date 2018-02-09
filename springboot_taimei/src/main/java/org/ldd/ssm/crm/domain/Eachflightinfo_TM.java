package org.ldd.ssm.crm.domain;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
/**
 * 基本航班信息
 */
public class Eachflightinfo_TM {
	private Date Lcl_Dpt_Day; // '航班执行日期',
	private String Dpt_AirPt_Cd; // '始发地',
	private String Arrv_Airpt_Cd; // '到达地',
	private BigDecimal Yb_Pri; // 'YB全价',
	private String Flt_nbr; // '航班号',
	private String AirCrft_Typ; // '机型',
	private String Cls_Cpn_Qty; // '座位布局',
	private String AirCrft_Reg_Nbr; // '飞机号',
	private String Airln_cd; // '所属公司',
	private String Flt_Rte_Cd; // '航线',
	private Time Lcl_Dpt_Tm; // '起飞时刻',
	private Time Lcl_Arrv_Tm; // '到达时刻',
	private int Seats; // '每班座位',
	private int Gosh_Ind; // T 'goshow字段',
	private int Nosh_Ind; // ' noshow',
	private int Chile_Pax_Qty; // '儿童票',
	private int Inft_Qty; // '婴儿票',
	private String uuid; // '连接每班旅客信息表',
	private BigDecimal Dat_Gat_Tme; // '数据采集时间',
	private String Dat_Gat_Acc_Nbr; // '数据采集账号,登录Eterm的账号',
	private String Dat_Gat_Ist; // '数据收集指令',
	private Datasupplier datasupplier_id; // '数据来源id,表示数据从哪来的',
	


	public Date getLcl_Dpt_Day() {
		return Lcl_Dpt_Day;
	}

	public void setLcl_Dpt_Day(Date lcl_Dpt_Day) {
		Lcl_Dpt_Day = lcl_Dpt_Day;
	}

	public String getDpt_AirPt_Cd() {
		return Dpt_AirPt_Cd;
	}

	public void setDpt_AirPt_Cd(String dpt_AirPt_Cd) {
		Dpt_AirPt_Cd = dpt_AirPt_Cd;
	}

	public String getArrv_Airpt_Cd() {
		return Arrv_Airpt_Cd;
	}

	public void setArrv_Airpt_Cd(String arrv_Airpt_Cd) {
		Arrv_Airpt_Cd = arrv_Airpt_Cd;
	}

	public BigDecimal getYb_Pri() {
		return Yb_Pri;
	}

	public void setYb_Pri(BigDecimal yb_Pri) {
		Yb_Pri = yb_Pri;
	}

	public String getFlt_nbr() {
		return Flt_nbr;
	}

	public void setFlt_nbr(String flt_nbr) {
		Flt_nbr = flt_nbr;
	}

	public String getAirCrft_Typ() {
		return AirCrft_Typ;
	}

	public void setAirCrft_Typ(String airCrft_Typ) {
		AirCrft_Typ = airCrft_Typ;
	}

	public String getCls_Cpn_Qty() {
		return Cls_Cpn_Qty;
	}

	public void setCls_Cpn_Qty(String cls_Cpn_Qty) {
		Cls_Cpn_Qty = cls_Cpn_Qty;
	}

	public String getAirCrft_Reg_Nbr() {
		return AirCrft_Reg_Nbr;
	}

	public void setAirCrft_Reg_Nbr(String airCrft_Reg_Nbr) {
		AirCrft_Reg_Nbr = airCrft_Reg_Nbr;
	}

	public String getAirln_cd() {
		return Airln_cd;
	}

	public void setAirln_cd(String airln_cd) {
		Airln_cd = airln_cd;
	}

	public String getFlt_Rte_Cd() {
		return Flt_Rte_Cd;
	}

	public void setFlt_Rte_Cd(String flt_Rte_Cd) {
		Flt_Rte_Cd = flt_Rte_Cd;
	}

	public Time getLcl_Dpt_Tm() {
		return Lcl_Dpt_Tm;
	}

	public void setLcl_Dpt_Tm(Time lcl_Dpt_Tm) {
		Lcl_Dpt_Tm = lcl_Dpt_Tm;
	}

	public Time getLcl_Arrv_Tm() {
		return Lcl_Arrv_Tm;
	}

	public void setLcl_Arrv_Tm(Time lcl_Arrv_Tm) {
		Lcl_Arrv_Tm = lcl_Arrv_Tm;
	}

	public int getSeats() {
		return Seats;
	}

	public void setSeats(int seats) {
		Seats = seats;
	}

	public int getGosh_Ind() {
		return Gosh_Ind;
	}

	public void setGosh_Ind(int gosh_Ind) {
		Gosh_Ind = gosh_Ind;
	}

	public int getNosh_Ind() {
		return Nosh_Ind;
	}

	public void setNosh_Ind(int nosh_Ind) {
		Nosh_Ind = nosh_Ind;
	}

	public int getChile_Pax_Qty() {
		return Chile_Pax_Qty;
	}

	public void setChile_Pax_Qty(int chile_Pax_Qty) {
		Chile_Pax_Qty = chile_Pax_Qty;
	}

	public int getInft_Qty() {
		return Inft_Qty;
	}

	public void setInft_Qty(int inft_Qty) {
		Inft_Qty = inft_Qty;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public BigDecimal getDat_Gat_Tme() {
		return Dat_Gat_Tme;
	}

	public void setDat_Gat_Tme(BigDecimal dat_Gat_Tme) {
		Dat_Gat_Tme = dat_Gat_Tme;
	}

	public String getDat_Gat_Acc_Nbr() {
		return Dat_Gat_Acc_Nbr;
	}

	public void setDat_Gat_Acc_Nbr(String dat_Gat_Acc_Nbr) {
		Dat_Gat_Acc_Nbr = dat_Gat_Acc_Nbr;
	}

	public String getDat_Gat_Ist() {
		return Dat_Gat_Ist;
	}

	public void setDat_Gat_Ist(String dat_Gat_Ist) {
		Dat_Gat_Ist = dat_Gat_Ist;
	}

	public Datasupplier getDatasupplier_id() {
		return datasupplier_id;
	}

	public void setDatasupplier_id(Datasupplier datasupplier_id) {
		this.datasupplier_id = datasupplier_id;
	}

	@Override
	public String toString() {
		return "Eachflightinfo_TM [Lcl_Dpt_Day=" + Lcl_Dpt_Day
				+ ", Dpt_AirPt_Cd=" + Dpt_AirPt_Cd + ", Arrv_Airpt_Cd="
				+ Arrv_Airpt_Cd + ", Yb_Pri=" + Yb_Pri + ", Flt_nbr=" + Flt_nbr
				+ ", AirCrft_Typ=" + AirCrft_Typ + ", Cls_Cpn_Qty="
				+ Cls_Cpn_Qty + ", AirCrft_Reg_Nbr=" + AirCrft_Reg_Nbr
				+ ", Airln_cd=" + Airln_cd + ", Flt_Rte_Cd=" + Flt_Rte_Cd
				+ ", Lcl_Dpt_Tm=" + Lcl_Dpt_Tm + ", Lcl_Arrv_Tm=" + Lcl_Arrv_Tm
				+ ", Seats=" + Seats + ", Gosh_Ind=" + Gosh_Ind + ", Nosh_Ind="
				+ Nosh_Ind + ", Chile_Pax_Qty=" + Chile_Pax_Qty + ", Inft_Qty="
				+ Inft_Qty + ", uuid=" + uuid + ", Dat_Gat_Tme=" + Dat_Gat_Tme
				+ ", Dat_Gat_Acc_Nbr=" + Dat_Gat_Acc_Nbr + ", Dat_Gat_Ist="
				+ Dat_Gat_Ist + ", datasupplier_id=" + datasupplier_id + "]";
	}
}
