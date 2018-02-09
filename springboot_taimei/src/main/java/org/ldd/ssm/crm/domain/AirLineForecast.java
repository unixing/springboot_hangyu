package org.ldd.ssm.crm.domain;

import java.util.Date;

/**
 * 收益预估航段基本信息
 * @Title:AirLineForecast 
 * @Description:
 * @author taimei-gds 
 * @date 2016-3-28 下午5:27:21
 */
public class AirLineForecast {
	//开始时间
	private Date startDate;
	//结束时间
	private Date endDate;
	//起飞地
	private String dpt_AirPt_Cd;
	//目的地
	private String arrv_Airpt_Cd;
	//经停地
	private String pas_stp;
	//飞行小时
	private Float fly_time;
	//小时成本
	private Float fly_price;
	//机型
	private String fly_type;
	//座位数
	private Integer fly_site;
	//计划执行班期
	private String fly_banqi;
	//执行班次
	private String fly_banci;
	//预编排时刻
	private String bp_time;
	//参考
	private String bark;
	//主键
	private String id;
	//数据源
	private String dta_Sce;
	//增加时间
	private Date Dta_Dte;
	//代理费
	private float daili_price;
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the dpt_AirPt_Cd
	 */
	public String getDpt_AirPt_Cd() {
		return dpt_AirPt_Cd;
	}
	/**
	 * @param dpt_AirPt_Cd the dpt_AirPt_Cd to set
	 */
	public void setDpt_AirPt_Cd(String dpt_AirPt_Cd) {
		this.dpt_AirPt_Cd = dpt_AirPt_Cd;
	}
	/**
	 * @return the arrv_Airpt_Cd
	 */
	public String getArrv_Airpt_Cd() {
		return arrv_Airpt_Cd;
	}
	/**
	 * @param arrv_Airpt_Cd the arrv_Airpt_Cd to set
	 */
	public void setArrv_Airpt_Cd(String arrv_Airpt_Cd) {
		this.arrv_Airpt_Cd = arrv_Airpt_Cd;
	}
	/**
	 * @return the pas_stp
	 */
	public String getPas_stp() {
		return pas_stp;
	}
	/**
	 * @param pas_stp the pas_stp to set
	 */
	public void setPas_stp(String pas_stp) {
		this.pas_stp = pas_stp;
	}
	/**
	 * @return the fly_time
	 */
	public Float getFly_time() {
		return fly_time;
	}
	/**
	 * @param fly_time the fly_time to set
	 */
	public void setFly_time(Float fly_time) {
		this.fly_time = fly_time;
	}
	/**
	 * @return the fly_price
	 */
	public Float getFly_price() {
		return fly_price;
	}
	/**
	 * @param fly_price the fly_price to set
	 */
	public void setFly_price(Float fly_price) {
		this.fly_price = fly_price;
	}
	/**
	 * @return the fly_type
	 */
	public String getFly_type() {
		return fly_type;
	}
	/**
	 * @param fly_type the fly_type to set
	 */
	public void setFly_type(String fly_type) {
		this.fly_type = fly_type;
	}
	/**
	 * @return the fly_site
	 */
	public Integer getFly_site() {
		return fly_site;
	}
	/**
	 * @param fly_site the fly_site to set
	 */
	public void setFly_site(Integer fly_site) {
		this.fly_site = fly_site;
	}
	/**
	 * @return the fly_banqi
	 */
	public String getFly_banqi() {
		return fly_banqi;
	}
	/**
	 * @param fly_banqi the fly_banqi to set
	 */
	public void setFly_banqi(String fly_banqi) {
		this.fly_banqi = fly_banqi;
	}
	/**
	 * @return the bp_time
	 */
	public String getBp_time() {
		return bp_time;
	}
	/**
	 * @param bp_time the bp_time to set
	 */
	public void setBp_time(String bp_time) {
		this.bp_time = bp_time;
	}
	/**
	 * @return the bark
	 */
	public String getBark() {
		return bark;
	}
	/**
	 * @param bark the bark to set
	 */
	public void setBark(String bark) {
		this.bark = bark;
	}
	
	/**
	 * @return the dta_Sce
	 */
	public String getDta_Sce() {
		return dta_Sce;
	}
	/**
	 * @param dta_Sce the dta_Sce to set
	 */
	public void setDta_Sce(String dta_Sce) {
		this.dta_Sce = dta_Sce;
	}
	/**
	 * @return the dta_Dte
	 */
	public Date getDta_Dte() {
		return Dta_Dte;
	}
	/**
	 * @param dta_Dte the dta_Dte to set
	 */
	public void setDta_Dte(Date dta_Dte) {
		Dta_Dte = dta_Dte;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the daili_price
	 */
	public float getDaili_price() {
		return daili_price;
	}
	/**
	 * @param daili_price the daili_price to set
	 */
	public void setDaili_price(float daili_price) {
		this.daili_price = daili_price;
	}
	
	
	/**
	 * @return the fly_banci
	 */
	public String getFly_banci() {
		return fly_banci;
	}
	/**
	 * @param fly_banci the fly_banci to set
	 */
	public void setFly_banci(String fly_banci) {
		this.fly_banci = fly_banci;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AirLineForecast [startDate=" + startDate + ", endDate="
				+ endDate + ", dpt_AirPt_Cd=" + dpt_AirPt_Cd
				+ ", arrv_Airpt_Cd=" + arrv_Airpt_Cd + ", pas_stp=" + pas_stp
				+ ", fly_time=" + fly_time + ", fly_price=" + fly_price
				+ ", fly_type=" + fly_type + ", fly_site=" + fly_site
				+ ", fly_banqi=" + fly_banqi + ", fly_banci=" + fly_banci
				+ ", bp_time=" + bp_time + ", bark=" + bark + ", id=" + id
				+ ", dta_Sce=" + dta_Sce + ", Dta_Dte=" + Dta_Dte
				+ ", daili_price=" + daili_price + "]";
	}
	
	
}
