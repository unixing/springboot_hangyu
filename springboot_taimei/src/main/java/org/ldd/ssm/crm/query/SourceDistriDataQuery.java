package org.ldd.ssm.crm.query;

import java.util.List;

/**
 * 高级查询加分页的封装对象
 */
public class SourceDistriDataQuery {
	private String lcl_Dpt_Tm;
	private String lcl_Arrv_Tm;
	private String dpt_AirPt_Cd;
	private String pas_stp;
	private String arrv_Airpt_Cd;
	private String flt_nbr;
	private String name;
	
	private String itia;
	private String isIncludePas;//是否包含经停
	private String isIncludeRdf;//包含直飞
	private List<String> fltnbrs;//所有的航班号以逗号分隔
	private List<String> airlines;//包含当前机场的航线
	
	public List<String> getAirlines() {
		return airlines;
	}
	public void setAirlines(List<String> airlines) {
		this.airlines = airlines;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getItia() {
		return itia;
	}
	public void setItia(String itia) {
		this.itia = itia;
	}
	public String getLcl_Dpt_Tm() {
		return lcl_Dpt_Tm;
	}
	public void setLcl_Dpt_Tm(String lcl_Dpt_Tm) {
		this.lcl_Dpt_Tm = lcl_Dpt_Tm;
	}
	public String getLcl_Arrv_Tm() {
		return lcl_Arrv_Tm;
	}
	public void setLcl_Arrv_Tm(String lcl_Arrv_Tm) {
		this.lcl_Arrv_Tm = lcl_Arrv_Tm;
	}
	public String getDpt_AirPt_Cd() {
		return dpt_AirPt_Cd;
	}
	public void setDpt_AirPt_Cd(String dpt_AirPt_Cd) {
		this.dpt_AirPt_Cd = dpt_AirPt_Cd;
	}
	public String getPas_stp() {
		return pas_stp;
	}
	public void setPas_stp(String pas_stp) {
		this.pas_stp = pas_stp;
	}
	public String getArrv_Airpt_Cd() {
		return arrv_Airpt_Cd;
	}
	public void setArrv_Airpt_Cd(String arrv_Airpt_Cd) {
		this.arrv_Airpt_Cd = arrv_Airpt_Cd;
	}
	public String getFlt_nbr() {
		return flt_nbr;
	}
	public void setFlt_nbr(String flt_nbr) {
		this.flt_nbr = flt_nbr;
	}
	
	
	/**
	 * @return the fltnbrs
	 */
	public List<String> getFltnbrs() {
		return fltnbrs;
	}
	/**
	 * @param fltnbrs the fltnbrs to set
	 */
	public void setFltnbrs(List<String> fltnbrs) {
		this.fltnbrs = fltnbrs;
	}
	/**
	 * @return the isIncludeRdf
	 */
	public String getIsIncludeRdf() {
		return isIncludeRdf;
	}
	/**
	 * @param isIncludeRdf the isIncludeRdf to set
	 */
	public void setIsIncludeRdf(String isIncludeRdf) {
		this.isIncludeRdf = isIncludeRdf;
	}
	/**
	 * @return the isIncludePas
	 */
	public String getIsIncludePas() {
		return isIncludePas;
	}
	/**
	 * @param isIncludePas the isIncludePas to set
	 */
	public void setIsIncludePas(String isIncludePas) {
		this.isIncludePas = isIncludePas;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SourceDistriDataQuery [lcl_Dpt_Tm=" + lcl_Dpt_Tm
				+ ", lcl_Arrv_Tm=" + lcl_Arrv_Tm + ", dpt_AirPt_Cd="
				+ dpt_AirPt_Cd + ", pas_stp=" + pas_stp + ", arrv_Airpt_Cd="
				+ arrv_Airpt_Cd + ", flt_nbr=" + flt_nbr + ", name=" + name
				+ ", itia=" + itia + ", isIncludePas=" + isIncludePas
				+ ", isIncludeRdf=" + isIncludeRdf + ", fltnbrs=" + fltnbrs
				+ "]";
	}
	
}