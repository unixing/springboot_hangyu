package org.ldd.ssm.crm.query;

public class MonthParameterQuery {
	private String lcl_Dpt_Day;//出发时间
	private String dpt_Airpt_Cd;//出发地
	private String arrv_Airpt_Cd;//到达地
	private String description;
	private String flt_Rte_Cd;
	private String flt_nbr;
	private String monthDays;
	private String yoy_Trans;  
	private String qoq_Trans;
	private String cur_Trans;
	private String toAvgTraveller;
	private String backAvgTraveller;
	private String toForwardOffset;
	private String toReverseOffset;
	private String backForwardOffset;
	private String backReverseOffset;
	private String startDate;
	private String endDate;
	private String saleStartDate;
	private String saleEndDate;
	private String nature;
	private String returnStartDate;
	private String returnEndDate;
	private String returnSaleStartDate;
	private String returnSaleEndDate;
	private String returnNature;
	private String refFlt;
	private Integer seasonId;
	public String getLcl_Dpt_Day() {
		return lcl_Dpt_Day;
	}
	public void setLcl_Dpt_Day(String lcl_Dpt_Day) {
		this.lcl_Dpt_Day = lcl_Dpt_Day;
	}
	public String getDpt_Airpt_Cd() {
		return dpt_Airpt_Cd;
	}
	public void setDpt_Airpt_Cd(String dpt_Airpt_Cd) {
		this.dpt_Airpt_Cd = dpt_Airpt_Cd;
	}
	public String getArrv_Airpt_Cd() {
		return arrv_Airpt_Cd;
	}
	public void setArrv_Airpt_Cd(String arrv_Airpt_Cd) {
		this.arrv_Airpt_Cd = arrv_Airpt_Cd;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFlt_Rte_Cd() {
		return flt_Rte_Cd;
	}
	public void setFlt_Rte_Cd(String flt_Rte_Cd) {
		this.flt_Rte_Cd = flt_Rte_Cd;
	}
	public String getFlt_nbr() {
		return flt_nbr;
	}
	public void setFlt_nbr(String flt_nbr) {
		this.flt_nbr = "".equals(flt_nbr)?null:flt_nbr;
	}
	public String getMonthDays() {
		return monthDays;
	}
	public void setMonthDays(String monthDays) {
		this.monthDays = monthDays;
	}
	public String getYoy_Trans() {
		return yoy_Trans;
	}
	public void setYoy_Trans(String yoy_Trans) {
		this.yoy_Trans = yoy_Trans;
	}
	public String getQoq_Trans() {
		return qoq_Trans;
	}
	public void setQoq_Trans(String qoq_Trans) {
		this.qoq_Trans = qoq_Trans;
	}
	public String getCur_Trans() {
		return cur_Trans;
	}
	public void setCur_Trans(String cur_Trans) {
		this.cur_Trans = cur_Trans;
	}
	public String getToAvgTraveller() {
		return toAvgTraveller;
	}
	public void setToAvgTraveller(String toAvgTraveller) {
		this.toAvgTraveller = toAvgTraveller;
	}
	public String getBackAvgTraveller() {
		return backAvgTraveller;
	}
	public void setBackAvgTraveller(String backAvgTraveller) {
		this.backAvgTraveller = backAvgTraveller;
	}
	public String getToForwardOffset() {
		return toForwardOffset;
	}
	public void setToForwardOffset(String toForwardOffset) {
		this.toForwardOffset = toForwardOffset;
	}
	public String getToReverseOffset() {
		return toReverseOffset;
	}
	public void setToReverseOffset(String toReverseOffset) {
		this.toReverseOffset = toReverseOffset;
	}
	public String getBackForwardOffset() {
		return backForwardOffset;
	}
	public void setBackForwardOffset(String backForwardOffset) {
		this.backForwardOffset = backForwardOffset;
	}
	public String getBackReverseOffset() {
		return backReverseOffset;
	}
	public void setBackReverseOffset(String backReverseOffset) {
		this.backReverseOffset = backReverseOffset;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getSaleStartDate() {
		return saleStartDate;
	}
	public void setSaleStartDate(String saleStartDate) {
		this.saleStartDate = saleStartDate;
	}
	public String getSaleEndDate() {
		return saleEndDate;
	}
	public void setSaleEndDate(String saleEndDate) {
		this.saleEndDate = saleEndDate;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public String getReturnStartDate() {
		return returnStartDate;
	}
	public void setReturnStartDate(String returnStartDate) {
		this.returnStartDate = returnStartDate;
	}
	public String getReturnEndDate() {
		return returnEndDate;
	}
	public void setReturnEndDate(String returnEndDate) {
		this.returnEndDate = returnEndDate;
	}
	public String getReturnSaleStartDate() {
		return returnSaleStartDate;
	}
	public void setReturnSaleStartDate(String returnSaleStartDate) {
		this.returnSaleStartDate = returnSaleStartDate;
	}
	public String getReturnSaleEndDate() {
		return returnSaleEndDate;
	}
	public void setReturnSaleEndDate(String returnSaleEndDate) {
		this.returnSaleEndDate = returnSaleEndDate;
	}
	public String getReturnNature() {
		return returnNature;
	}
	public void setReturnNature(String returnNature) {
		this.returnNature = returnNature;
	}
	public String getRefFlt() {
		return refFlt;
	}
	public void setRefFlt(String refFlt) {
		this.refFlt = refFlt;
	}
	
	public Integer getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MonthParameterQuery [lcl_Dpt_Day=" + lcl_Dpt_Day
				+ ", dpt_Airpt_Cd=" + dpt_Airpt_Cd + ", arrv_Airpt_Cd="
				+ arrv_Airpt_Cd + ", description=" + description
				+ ", flt_Rte_Cd=" + flt_Rte_Cd + ", flt_nbr=" + flt_nbr
				+ ", monthDays=" + monthDays + ", yoy_Trans=" + yoy_Trans
				+ ", qoq_Trans=" + qoq_Trans + ", cur_Trans=" + cur_Trans
				+ ", toAvgTraveller=" + toAvgTraveller + ", backAvgTraveller="
				+ backAvgTraveller + ", toForwardOffset=" + toForwardOffset
				+ ", toReverseOffset=" + toReverseOffset
				+ ", backForwardOffset=" + backForwardOffset
				+ ", backReverseOffset=" + backReverseOffset + ", startDate="
				+ startDate + ", endDate=" + endDate + ", saleStartDate="
				+ saleStartDate + ", saleEndDate=" + saleEndDate + ", nature="
				+ nature + ", returnStartDate=" + returnStartDate
				+ ", returnEndDate=" + returnEndDate + ", returnSaleStartDate="
				+ returnSaleStartDate + ", returnSaleEndDate="
				+ returnSaleEndDate + ", returnNature=" + returnNature
				+ ", refFlt=" + refFlt + ", seasonId=" + seasonId + "]";
	}
}
